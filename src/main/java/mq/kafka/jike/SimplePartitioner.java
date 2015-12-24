package mq.kafka.jike;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;
 


/**
 * 自定义分区算法，实现Partitioner接口
 * @author yuezhihua
 *
 */
public class SimplePartitioner implements Partitioner {
	
	
	/**
	 * 
	 * @param props
	 */
    public SimplePartitioner (VerifiableProperties props) {
 
    }
 
    
    /**
     *	会根据key和 a_numPartitions计算一个返回值，返回一个整数在0-a_numPartitions之间
     */
    public int partition(Object key, int a_numPartitions) {
        int partition = 0;
        String stringKey = (String) key;
        int offset = stringKey.lastIndexOf('.');
        if (offset > 0) {
           partition = Integer.parseInt( stringKey.substring(offset+1)) % a_numPartitions;
        }
       return partition;
  }
 
}
