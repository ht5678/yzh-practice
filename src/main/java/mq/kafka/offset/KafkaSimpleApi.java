package mq.kafka.offset;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;



/**
 * 
 * 1.读取指定offset范围的消息
 * 
 * 2.也可以通过ZnodeSetData来设置topic的offset，重新指定读取的开始索引
 * 
 * @author yuezh2   2016年1月18日 下午2:14:32
 *
 */
public class KafkaSimpleApi {
    private List<String> m_replicaBrokers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        KafkaSimpleApi api = new KafkaSimpleApi();
        long startOffset = 10;
        long endOffset   = 20;
        String topic     = "posts_topic";
        int partition    = 2;
        List<String> seedBrokers   = new ArrayList<>();
        seedBrokers.add("127.0.0.1");
        int port         = 9092;
        Map<Long, byte[]> map = api.getMessagesAndOffsetsBetween(
                startOffset, endOffset, topic, partition, seedBrokers, port);

        for (Map.Entry<Long, byte[]> kv : map.entrySet()) {
            System.out.println(kv.getKey() + "\t" + new String(kv.getValue()));
        }
    }

    private PartitionMetadata findLeader(List<String> a_seedBrokers, int a_port, String a_topic, int a_partition) {
        PartitionMetadata returnMetaData = null;
        loop:
        for (String seed : a_seedBrokers) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer(seed, a_port, 10000, 64 * 2014, "leaderLookup");

                List<String> topics = Collections.singletonList(a_topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);

                TopicMetadataResponse resp = consumer.send(req);

                List<TopicMetadata> metaData = resp.topicsMetadata();

                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        if (part.partitionId() == a_partition) {
                            returnMetaData = part;
                            break loop;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error communicating with Broker [" + seed + "] to find Leader for [" + a_topic
                        + ", " + a_partition + "] Reason: " + e);
            } finally {
                if (consumer != null)
                    consumer.close();
            }
        }
        if (returnMetaData != null) {
            m_replicaBrokers.clear();
            for (kafka.cluster.Broker replica : returnMetaData.replicas())
                m_replicaBrokers.add(replica.host());
        }
        return returnMetaData;
    }

    public long getLastOffset(List<String> seedBrokers, int port, String topic, int partition, long whichTime, String clientName) throws IOException {
        //找到目标topic和partition的leader broker
        PartitionMetadata metadata = findLeader(seedBrokers, port, topic, partition);
        //判断查找的结果是否有异常
        if (metadata == null) {
            throw new IOException("Can't find metadata for Topic and Partition.");
        } else if (metadata.leader() == null) {
            throw new IOException("Can't find Leader for Topic and Partition. Exiting");
        }
        //获取leader broker, 来构造SimpleConsumer
        String leadBroker = metadata.leader().host();
//        System.out.println("find leader broker" + leadBroker);

        //SimpleConsumer的参数分别为host, port, timeout, buffersize, clientName
        SimpleConsumer consumer = null;
        try {
            consumer = new SimpleConsumer(leadBroker, port, 100000, 64 * 1024, clientName);
            return getLastOffset(consumer, topic, partition, whichTime, clientName);
        } finally {
            if (consumer != null)
                consumer.close();
        }
    }


    public static long getLastOffset(SimpleConsumer consumer, String topic, int partition, long whichTime, String clientName) {
        long biggestOffsetAvailable = getMaxAvailableOffset(consumer, topic, partition, clientName);
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);
        if (response.hasError()) {
            System.out.println("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition));
            return -1;
        }
        long[] offsets = response.offsets(topic, partition);
        if(offsets.length == 0)
            throw new RuntimeException("can't fetch offset for time " + new Timestamp(whichTime).toString());
        long lastOffset = offsets[0];
        if(lastOffset > biggestOffsetAvailable)
            return biggestOffsetAvailable;
        return offsets[0];
    }

    public static long getMaxAvailableOffset(SimpleConsumer consumer, String topic, int partition, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime(), 1));
        OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);
        if (response.hasError()) {
            System.out.println("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition));
            return -1;
        }
        long[] offsets = response.offsets(topic, partition);
        if(offsets.length == 0)
            throw new RuntimeException("can't fetch offset for kafka.api.OffsetRequest.LatestTime()");
        long lastOffset = offsets[0];
        return offsets[0] - 1;
    }


    /**
     * 当fetch返回错误码的时候，we log the reason, close the consumer,then try to fingure out who the new leader is
     * This method uses the findLeader() logic we defined earlier to find the new leader, except here we only try to connect to one of the replicas for the topic/partition. This way if we can’t reach any of the Brokers with the data we are interested in we give up and exit hard.
     * Since it may take a short time for ZooKeeper to detect the leader loss and assign a new leader, we sleep if we don’t get an answer. In reality ZooKeeper often does the failover very quickly so you never sleep.
     *
     * @param a_oldLeader
     * @param a_topic
     * @param a_partition
     * @param a_port
     * @return
     * @throws Exception
     */
    private String findNewLeader(String a_oldLeader, String a_topic, int a_partition, int a_port) throws Exception {
        for (int i = 0; i < 3; i++) {
            boolean goToSleep;
            PartitionMetadata metadata = findLeader(m_replicaBrokers, a_port, a_topic, a_partition);
            if (metadata == null) {
                goToSleep = true;
            } else if (metadata.leader() == null) {
                goToSleep = true;
            } else if (a_oldLeader.equalsIgnoreCase(metadata.leader().host()) && i == 0) {
                // first time through if the leader hasn't changed give ZooKeeper a second to recover
                // second time, assume the broker did recover before failover, or it was a non-Broker issue
                goToSleep = true;
            } else {
                return metadata.leader().host();
            }
            if (goToSleep) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    //ignore
                }
            }
        }
        System.out.println("Unable to find new leader after Borker failure. Exiting");
        throw new Exception("Unable to find new leader after Borker failure. Exiting");
    }

    /**
     * @param a_maxReads
     * @param a_topic
     * @param a_partition
     * @param a_seedBrokers
     * @param a_port
     * @throws Exception
     */
    public void run(long a_maxReads, String a_topic, int a_partition, List<String> a_seedBrokers, int a_port) throws Exception {
        // find the meta data about the topic and partition we are interested in
        //
        PartitionMetadata metadata = findLeader(a_seedBrokers, a_port, a_topic, a_partition);
        if (metadata == null) {
            System.out.println("Can't find metadata for Topic and Partition. Exiting");
            return;
        }
        if (metadata.leader() == null) {
            System.out.println("Can't find Leader for Topic and Partition. Exiting");
            return;
        }
        String leadBroker = metadata.leader().host();
        String clientName = "Client_" + a_topic + "_" + a_partition;

        SimpleConsumer consumer = null;
        try {
            consumer = new SimpleConsumer(leadBroker, a_port, 100000, 64 * 1024, clientName);
            long readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.EarliestTime(), clientName);

            int numErrors = 0;
            while (a_maxReads > 0) {
                if (consumer == null) {
                    consumer = new SimpleConsumer(leadBroker, a_port, 100000, 64 * 1024, clientName);
                }
                FetchRequest req = new FetchRequestBuilder()
                        .clientId(clientName)
                        .addFetch(a_topic, a_partition, readOffset, 100000) // Note: this fetchSize of 100000 might need to be increased if large batches are written to Kafka
                        .build();
                FetchResponse fetchResponse = consumer.fetch(req);

                if (fetchResponse.hasError()) {
                    numErrors++;
                    // Something went wrong!
                    short code = fetchResponse.errorCode(a_topic, a_partition);
                    System.out.println("Error fetching data from the Broker:" + leadBroker + " Reason: " + code);
                    if (numErrors > 5) break;
                    if (code == ErrorMapping.OffsetOutOfRangeCode()) {
                        // We asked for an invalid offset. For simple case ask for the last element to reset
                        readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.LatestTime(), clientName);
                        continue;
                    }
                    consumer.close();
                    consumer = null;
                    leadBroker = findNewLeader(leadBroker, a_topic, a_partition, a_port);
                    continue;
                }
                numErrors = 0;

                long numRead = 0;
                for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(a_topic, a_partition)) {
                    long currentOffset = messageAndOffset.offset();
                    if (currentOffset < readOffset) {
                        System.out.println("Found an old offset: " + currentOffset + " Expecting: " + readOffset);
                        continue;
                    }
                    readOffset = messageAndOffset.nextOffset();
                    ByteBuffer payload = messageAndOffset.message().payload();

                    byte[] bytes = new byte[payload.limit()];
                    payload.get(bytes);
                    System.out.println(String.valueOf(messageAndOffset.offset()) + ": " + new String(bytes, "UTF-8"));
                    numRead++;
                    a_maxReads--;
                }

                if (numRead == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                    }
                }
            }
        } finally {
            if (consumer != null) consumer.close();
        }
    }

    public List<String> getMessagesBetween(long a_start, long a_end, String topic, int partition, List<String> seedBrokers, int port) throws IOException {
        List<String> messages = new ArrayList<String>(); //结果
        //找到目标topic和partition的leader broker
        PartitionMetadata metadata = findLeader(seedBrokers, port, topic, partition);
        //判断查找的结果是否有异常
        if (metadata == null) {
            throw new IOException("Can't find metadata for Topic and Partition.");
        } else if (metadata.leader() == null) {
            throw new IOException("Can't find Leader for Topic and Partition. Exiting");
        }
        //获取leader broker, 来构造SimpleConsumer
        String leadBroker = metadata.leader().host();
//        System.out.println("find leader broker" + leadBroker);
        String clientName = "Client_" + topic + "_" + partition;

        //SimpleConsumer的参数分别为host, port, timeout, buffersize, clientName
        SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000, 64 * 1024, clientName);
        try {
            //获取最老的offset和新最新的offset
            long oldestOffset = a_start;
            long latestOffset = a_end;
            long numberErrors = 0;
            long numberRead = 0;//表示读取了多少消息
            long numberToRead = latestOffset - oldestOffset;
            FetchRequest req = new FetchRequestBuilder()
                    .clientId(clientName)
                    .addFetch(topic, partition, oldestOffset, 100000) // Note: this fetchSize of 100000 might need to be increased if large batches are written to Kafka
                    .build();
            FetchResponse fetchResponse = null;
            while (numberToRead != 0) {
//                System.out.println("numberToRead is: " + numberToRead);
                numberErrors = 0; //重置numberErrors 为 0
                while (numberErrors < 5) { //尝试5次, 有成功就推出循环，然后继续处理，5次全失败就报异常
                    numberErrors++;
                    fetchResponse = consumer.fetch(req);
                    if (fetchResponse.hasError()) {
                        short code = fetchResponse.errorCode(topic, partition);
                        System.out.println("Error fetching data from the Broker:" + leadBroker + " Reason: " + code);
                    } else {
                        break;
                    }
                }
                if (fetchResponse.hasError()) {
                    throw new IOException("failed in fetch messages.Reason is " + fetchResponse.errorCode(topic, partition));
                }
                long nextOffset = Long.MAX_VALUE;
                System.out.println("received a batch of messages");
                for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(topic, partition)) {
                    long currentOffset = messageAndOffset.offset();
                    //因为可能要fetch多次，并且考虑到batch，因此消息的offset可能会大于之前确定的latest offset
                    if (currentOffset < oldestOffset || currentOffset >= latestOffset) {
//                        System.out.println("Found an old offset: " + currentOffset + " Expecting: " + oldestOffset);
                        continue;
                    }
                    nextOffset = messageAndOffset.nextOffset();
                    ByteBuffer payload = messageAndOffset.message().payload();

                    byte[] bytes = new byte[payload.limit()];
                    payload.get(bytes);
//                    System.out.println(String.valueOf(messageAndOffset.offset()) + ": " + new String(bytes, "UTF-8"));
                    messages.add(new String(bytes, "UTF-8"));
                    numberRead++; //只有当前消息的offset大于或等于oldest offset,并且小于或等于latest offset的时候，才把它算成一个已读取的消息。这是因为batch的作用。see kafka docs
                    numberToRead--;
                }
                req = new FetchRequestBuilder()
                        .clientId(clientName)
                        .addFetch(topic, partition, nextOffset, 100000) // Note: this fetchSize of 100000 might need to be increased if large batches are written to Kafka
                        .build();
            }
            return messages;
        } finally {
            consumer.close(); //make sure consumer is closed finally
        }
    }
    public Map<Long, byte[]> getMessagesAndOffsetsBetween(long startOffset, long endOffset, String topic, int partition, List<String> seedBrokers, int port) throws IOException {
//        List<String> messages = new ArrayList<String>(); //结果
        Map<Long, byte[]> messages = new TreeMap<>();
        //找到目标topic和partition的leader broker
        PartitionMetadata metadata = findLeader(seedBrokers, port, topic, partition);
        //判断查找的结果是否有异常
        if (metadata == null) {
            throw new IOException("Can't find metadata for Topic and Partition.");
        } else if (metadata.leader() == null) {
            throw new IOException("Can't find Leader for Topic and Partition. Exiting");
        }
        //获取leader broker, 来构造SimpleConsumer
        String leadBroker = metadata.leader().host();
        System.out.println("find leader broker " + leadBroker);
        String clientName = "Client_" + topic + "_" + partition;

        //SimpleConsumer的参数分别为host, port, timeout, buffersize, clientName
        SimpleConsumer consumer = new SimpleConsumer(leadBroker, port, 100000, 64 * 1024, clientName);
        try {
            //获取最老的offset和新最新的offset
            long oldestOffset = getLastOffset(consumer, topic, partition, kafka.api.OffsetRequest.EarliestTime(), "SimpleConsumer");
            long latestOffset = getLastOffset(consumer, topic, partition, kafka.api.OffsetRequest.LatestTime(), "SimpleConsumer");
            System.out.println("Oldest offset is " + oldestOffset + ", latest offset is " + (latestOffset));
            if(startOffset < oldestOffset || endOffset > latestOffset){
                throw new RuntimeException("not available offsets. Make sure start offset >= " + oldestOffset + " and end offset <= " + latestOffset);
            }
            int numberErrors = 0;
            //尝试5次，然后退出
            while (numberErrors < 5 && (oldestOffset == -1 || latestOffset == -1)) {
                System.out.println("retry find offset");
                numberErrors++;
                //retry certain times
                String newLeader = null;
                try {
                    newLeader = findNewLeader(leadBroker, topic, partition, port);
                } catch (Exception e) {
                    System.out.println("error when find leader ");
                    throw new IOException("can't find new leader ", e);
                }
                //construct new consumer from mew leader
                consumer = new SimpleConsumer(newLeader, port, 100000, 64 * 1024, clientName);
            }
            if (oldestOffset == -1 || latestOffset == -1) {
                throw new IOException("failed to get oldest offset and latest offset");
            }
            //现在有了最老的offset和最新的offset，就可以构造请求，去获取消息
            long numberRead = 0;//表示读取了多少消息
            long numberToRead = endOffset - startOffset + 1;
            FetchRequest req = new FetchRequestBuilder()
                    .clientId(clientName)
                    .addFetch(topic, partition, startOffset, 100000) // Note: this fetchSize of 1 00000 might need to be increased if large batches are written to Kafka
                    .build();
            FetchResponse fetchResponse = null;
            while (numberToRead != 0) {
                numberErrors = 0; //重置numberErrors 为 0
                while (numberErrors < 5) { //尝试5次, 有成功就推出循环，然后继续处理，5次全失败就报异常
                    numberErrors++;
                    fetchResponse = consumer.fetch(req);
                    if (fetchResponse.hasError()) {
                        short code = fetchResponse.errorCode(topic, partition);
                        System.out.println("Error fetching data from the Broker:" + leadBroker + " Reason: " + code);
                    } else {
                        break;
                    }
                }
                if (fetchResponse.hasError()) {
                    throw new IOException("failed in fetch messages.Reason is " + fetchResponse.errorCode(topic, partition));
                }
                long nextOffset = 0;
                System.out.println("received a batch of messages");
                for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(topic, partition)) {
                    long currentOffset = messageAndOffset.offset();
                    //因为可能要fetch多次，并且考虑到batch，因此消息的offset可能会大于之前确定的latest offset
                    if (currentOffset < startOffset || currentOffset > endOffset) {
//                        System.out.println("Found an old offset: " + currentOffset + " Expecting: " + oldestOffset);
                        continue;
                    }
                    nextOffset = messageAndOffset.nextOffset();
                    ByteBuffer payload = messageAndOffset.message().payload();
                    Long offsetOfPayload = messageAndOffset.offset();

                    byte[] bytes = new byte[payload.limit()];
                    payload.get(bytes);
                    messages.put(offsetOfPayload, bytes);
                    // 只有当前消息的offset大于或等于oldest offset,
                    // 并且小于或等于latest offset的时候，才把它算成一个已读取的消息。这是因为batch的作用。
                    numberRead++;
                    numberToRead--;
                }
                req = new FetchRequestBuilder()
                        .clientId(clientName)
                        .addFetch(topic, partition, nextOffset, 100000)
                        .build();
            }
            return messages;
        } finally {
            consumer.close(); //make sure consumer is closed finally
        }
    }
}
