package zookeeper.zkclient.queue;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.I0Itec.zkclient.ExceptionUtil;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * 
 * @author yuezh2   2016年5月17日 下午8:04:47
 *
 */
public class DistributedSimpleQueue<T> {

	protected final ZkClient zkClient;
	
	protected final String root;
	
	protected static final String NODE_NAME="n_";
	
	
	
	/**
	 * 构造函数
	 * @param zkClient
	 * @param root
	 */
	public DistributedSimpleQueue(ZkClient zkClient , String root){
		this.zkClient = zkClient;
		this.root = root;
	}
	
	
	/**
	 * 未消费的消息数量
	 * @return
	 */
	public int size(){
		return zkClient.getChildren(root).size();
	}
	
	
	/**
	 * 是否有没有消费的消息
	 * @return
	 */
	public boolean isEmpty(){
		return zkClient.getChildren(root).size()==0;
	}
	
	
	
	/**
	 * 生产消息
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public boolean offer(T element)throws Exception{
		String nodeFullPath = root.concat("/").concat(NODE_NAME);
		try{//创建 持久 顺序 节点
			zkClient.createPersistentSequential(nodeFullPath, element);
		}catch(ZkNoNodeException e){
			zkClient.createPersistent(root);
			offer(element);
		}catch(Exception e){
			throw ExceptionUtil.convertToRuntimeException(e);
		}
		return true;
	}
	
	
	
	
	
	/**
	 * 消费消息
	 * @return
	 * @throws Exception
	 */
	public T poll()throws Exception{
		
		try{
			//获取子节点并且排序
			List<String> list = zkClient.getChildren(root);
			if(list.size() ==0){
				return null;
			}
			Collections.sort(list,new Comparator<String>() {

				@Override
				public int compare(String lhs, String rhs) {
					return getNodeNumber(lhs, NODE_NAME).compareTo(getNodeNumber(rhs, NODE_NAME));
				}
			});
			
			//消费消息顺序
			//读取节点的消息,删除节点,返回消费的消息内容
			for(String nodeName : list){
				String nodeFullPath = root.concat("/").concat(nodeName);
				try{
					T node = (T) zkClient.readData(nodeFullPath);
					zkClient.delete(nodeFullPath);
					return node;
				}catch(ZkNoNodeException e){
					//ignore
				}
			}
			return null;
		}catch(Exception e){
			throw ExceptionUtil.convertToRuntimeException(e);
		}
		
	}
	
	
	
	
	/**
	 * 获取顺序节点的数字顺序
	 * @param str
	 * @param nodeName
	 * @return
	 */
	private String getNodeNumber(String str , String nodeName){
		int index = str.lastIndexOf(nodeName);
		if(index>=0){
			index = index + NODE_NAME.length();
			return index<=str.length()?str.substring(index):"";
		}
		return str;
	}
	
	
	
}
