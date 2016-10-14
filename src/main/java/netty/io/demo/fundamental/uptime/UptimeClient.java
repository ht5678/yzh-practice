package netty.io.demo.fundamental.uptime;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 
 * @author yuezh2   2016年10月13日 下午5:42:13
 *
 */
public class UptimeClient {
	
	/** host地址 */
	public static final String HOST = System.getProperty("host","127.0.0.1");
	/** 端口号 */
	public static final int PORT = Integer.parseInt(System.getProperty("port","8080"));
	/** 重新连接时间 */
	public static final int RECONNECT_DELAY = Integer.parseInt(System.getProperty("reconnectDelay","5"));
	/** 重新连接超时 */
	public static final int READ_TIMEOUT = Integer.parseInt(System.getProperty("readTimeout","10"));
	
	private static final UptimeClientHandler handler = new UptimeClientHandler();
	
	
	
	/**
	 * 默认初始化配置
	 * @param b
	 * @return
	 */
	static Bootstrap configureBootstrap(Bootstrap b){
		return configureBootstrap(b, new NioEventLoopGroup());
	}
	
	
	
	/**
	 * 初始化配置
	 * @param b
	 * @param g
	 * @return
	 */
	static Bootstrap configureBootstrap(Bootstrap b , EventLoopGroup g){
		b.group(g)
		  .channel(NioSocketChannel.class)
		  .remoteAddress(HOST, PORT)
		  .handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new IdleStateHandler(READ_TIMEOUT, 0, 0),handler);
			}
			  
		});
		
		return b;
	}
	
	
	
	/**
	 * 连接通道
	 * @param b
	 */
	static void connect(Bootstrap b){
		b.connect().addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.cause() != null){
					handler.startTime = -1;
					handler.println("Failed to connect : "+future.cause());
				}
			}
		});
	}
	

	
}
