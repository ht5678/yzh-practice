package zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

public class SubscribeDataChanges {
	
	private static class ZkDataListener implements IZkDataListener{

		public void handleDataChange(String dataPath, Object data)
				throws Exception {
			// TODO Auto-generated method stub
			System.out.println(dataPath+":"+data.toString());
		}

		public void handleDataDeleted(String dataPath) throws Exception {
			// TODO Auto-generated method stub
			System.out.println(dataPath);
			
		}

		
		
		
	}

	public static void main(String[] args) throws InterruptedException {
		//SerializableSerializer对输入输出内容会有变化
		//BytesPushThroughSerializer对输入输出内容不会有更改，输入=输出
		//**使用SerializableSerializer的话，用命令行对数据内容进行修改，不会打印出数据变化的
		ZkClient zc = new ZkClient("192.168.1.105:2181",10000,10000,new BytesPushThroughSerializer());
		System.out.println("conneted ok!");
		
		zc.subscribeDataChanges("/jike20", new ZkDataListener());
		Thread.sleep(Integer.MAX_VALUE);
		
		
	}
	
}
