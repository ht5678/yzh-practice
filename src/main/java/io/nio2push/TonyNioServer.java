package io.nio2push;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



/**
 * 
 * @author sdwhy
 *
 */
public class TonyNioServer {
	
	public static ServerSocketChannel serverSocketChannel;
	public static Selector selector;
	
	//处理请求的线程池
	public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(25, 50, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(100));
	
	
	
	public static void main(String[] args) throws Exception{
		//channel
		//buffer
		//selector
		
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		//绑定端口
		serverSocketChannel.bind(new InetSocketAddress(8080));
		System.out.println("NIO启动:"+8080);
		
		//selector 
		//socket操作系统层面保存的
		selector = Selector.open();
		
		//查询有哪些客户端和服务器建立连接
		//查询条件是OP_ACCESS 建立连接的意思
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true){
			//让他查数据已有条件去绑定的channel
			//查有没有结果, 超时时间1s
			selector.select(1000L);
			
			//获取选择器中的结果
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			
			while(iterator.hasNext()){
				//返回值
				SelectionKey result = iterator.next();
				
				if(result.isAcceptable()){//如果isaccessable,新的连接建立了
					//从通道里边获取连接
					SocketChannel connect = serverSocketChannel.accept();
					connect.configureBlocking(false);
					//接下来如何处理
					//丢到线程池?no
					//不知道有没有数据请求,不需要创建线程,问题 , 怎么知道有数据了呢?
					connect.register(selector, SelectionKey.OP_READ);
				}else if(result.isReadable()){//如果isreadable,有数据请求过来了 , 
					//从结果中,取出socket连接
					SocketChannel connect = (SocketChannel)result.channel();
					//告诉selector , 接下来这个socket连接,你不要帮我去查了,因为我已经在处理它的数据了
					result.cancel();
					//设置非阻塞模式
					connect.configureBlocking(false);
					//接下来把业务处理  丢给线程池
					threadPoolExecutor.execute(new NioSocketProcessor(connect));
					
				}
			}
			
			//清空上次的查询结果
			selectedKeys.clear();
			//清楚正在处理的不需要被查询的记录
			selector.selectNow();
			
		}
		
	}

}
