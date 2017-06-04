package rpc.demo.server;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 
 * 服务端负责接收客户端请求并且处理客户端请求 , 然后给出响应
 * 
 * @author sdwhy
 *
 */
public class RemoteServer {

	//
	private static volatile boolean start = false;
	//用于注册服务
	private static ConcurrentHashMap<String, Object> serverImplMap = new ConcurrentHashMap<>();
	
	
	
	public static void addServices(String serviceName  , Object serviceImpl){
		serverImplMap.putIfAbsent(serviceName, serviceImpl);
	}
	
	
	
//	static{
//		bootStrap();
//	}
	
	
	
	public static void bootStrap(){
		if(!start){
			synchronized (RemoteServer.class) {
				if(!start){
					start();
				}
			}
		}
	}
	
	
	
	
	
	//rpc   server逻辑
	public static void start(){
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();		//IO线程组
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();	//业务线程组
		
		ServerBootstrap bootstrap = new ServerBootstrap();
		
		bootstrap.group(bossGroup,workerGroup)
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel arg0) throws Exception {
				//pipeline处理请求
				arg0.pipeline().addLast(new StringEncoder(), new StringDecoder() , new NettyServerHandler());
			}
			
		})
		.option(ChannelOption.SO_BACKLOG, 1024)				//默认值为50
		.option(ChannelOption.SO_KEEPALIVE, false);			//是否启用心跳保活机制 , 需要两个小时没有数据传输才会断
		
		
		try {
			ChannelFuture f = bootstrap.bind(8081).sync();	//服务端绑定8081端口 , 建立长连接
			f.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture arg0) throws Exception {
					if(arg0.isSuccess()){
						start = true;
						System.out.println("服务端已经启动...");
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static Object getOnLineServiceImpl(String serviceName){
		return serverImplMap.get(serviceName);
	}
	
	
	
	
}
