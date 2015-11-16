package mq.kafka.quickstart;

import java.util.Properties;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * 
 * @author yuezhihua
 *
 */
public class Producer extends Thread{
	
	//kafka消息生产者
	private final kafka.javaapi.producer.Producer<Integer, String> producer;
	//话题
	private final String topic;
	//kafka配置
	private final Properties props = new Properties();
	
	/**
	 * 初始化配置
	 * @param topic
	 */
	public Producer(String topic){
		props.put("serializer.class", "kafka.serializer.StringEncoder");
	    props.put("metadata.broker.list", "10.99.205.22:2281");
	    producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
	    this.topic = topic;
	}
	
	
	
	public void run() {
	    int messageNo = 1;
	    while(true)
	    {
	      String messageStr = new String("Message_" + messageNo);
	      producer.send(new KeyedMessage<Integer, String>(topic, messageStr));
	      messageNo++;
	    }
	  }

}
