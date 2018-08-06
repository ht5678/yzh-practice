package io.nio2push;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


/**
 * 
 * @author sdwhy
 *
 */
public class NioSocketProcessor implements Runnable {
	
	private SocketChannel socketChannel;
	
	/**
	 * 
	 * @param socketChannel
	 */
	public NioSocketProcessor(SocketChannel socketChannel){
		this.socketChannel = socketChannel;
	}
	

	@Override
	public void run() {
		//给客户端,返回简单字符串
		try {
			ByteBuffer dst = ByteBuffer.allocate(1024);
			//nio buffer写入
			socketChannel.read(dst);
			//将缓冲区转为读取的模式
			dst.flip();
			//转换为字符串
			byte[] array = dst.array();
			String request = new String(array);
			System.out.println("收到新数据,当前线程数:"+TonyNioServer.threadPoolExecutor.getActiveCount()+"  , 请求内容:"+request);
			dst.clear();
			
			//响应客户端
			byte[] response = ("tony"+System.currentTimeMillis()).getBytes();
			ByteBuffer resp = ByteBuffer.wrap(response);
			resp.clear();
			//响应
			socketChannel.write(resp);
			resp.clear();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//长连接,响应以后,不关闭
			try {
				socketChannel.configureBlocking(false);
				socketChannel.register(TonyNioServer.selector, SelectionKey.OP_READ);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
