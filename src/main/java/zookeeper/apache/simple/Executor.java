package zookeeper.apache.simple;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author yuezh2   2016年1月15日 上午11:39:04
 *
 */
public class Executor implements Watcher,Runnable,DataMonitor.DataMonitorListener{
	
	private String znode;
	
	private DataMonitor dm;
	
	private ZooKeeper zk;
	
	private String filename;
	
	private String[] exec;
	
	private Process child;
	
	
	
	/**
	 * 构造函数
	 * @param host
	 * @param znode
	 * @param filename
	 * @param exec
	 * @throws IOException 
	 */
	public Executor(String hostPort , String znode , String filename , String[] exec) throws IOException{
		this.filename = filename;
		this.exec = exec;
		zk = new ZooKeeper(hostPort,10000 , this);
		dm = new DataMonitor(zk, znode, null, this);
	}
	
	

	@Override
	public void exists(byte[] data) {
		if(data == null){
			if(child != null){
				System.out.println("Killing process");
				child.destroy();
				try{
					child.waitFor();
				}catch(InterruptedException e){
				}
			}
			child = null;
		}else{
			if(child != null){
				System.out.println("stopping child");
				child.destroy();
				try {
					child.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try{
				FileOutputStream fos = new FileOutputStream(filename);
				fos.write(data);
				fos.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			
			try{
				System.out.println("starting child");
				child = Runtime.getRuntime().exec(exec);
				new StreamWriter(child.getInputStream(), System.out);
				new StreamWriter(child.getErrorStream(), System.err);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void closing(int rc) {
		synchronized (this) {
			notifyAll();
		}
		
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				while(!dm.dead){
					wait();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void process(WatchedEvent event) {
		dm.process(event);
		
	}
	
	
	
	
	static class StreamWriter extends Thread{
		OutputStream os ;
		InputStream is;
		
		public StreamWriter(InputStream is , OutputStream os) {
			this.is = is;
			this.os = os;
			start();
		}
		
		@Override
		public void run() {
			byte b[] = new byte[80];
			int rc;
			try{
				while((rc = is.read(b)) > 0){
					os.write(b, 0, rc);
				}
			}catch(IOException e){
				
			}
		}
		
		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		String hostPort = "10.99.205.17:2281";
		String znode = "/test";
		String filename = "/test";
		String[] exec =null;
		
		try {
			new Executor(hostPort, znode, filename, exec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
