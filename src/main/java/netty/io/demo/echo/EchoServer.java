package netty.io.demo.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * 打印客户端所有的消息到控制台
 * @author yuezh2   2016年7月19日 下午2:06:06
 *
 */
public class EchoServer {
	
	//是否开启ssl
	private static final boolean SSL = System.getProperty("ssl")!=null;
	//端口号
	private static final int PORT = Integer.parseInt(System.getProperty("port","8007"));
	
	
	
	
	
	
	public static void main(String[] args) throws Exception{
		//配置  ssl
		final SslContext sslCtx;
		
		if(SSL){
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		}else{
			sslCtx = null;
		}
		
		//配置 server
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			  .channel(NioServerSocketChannel.class)
			  .option(ChannelOption.SO_BACKLOG, 100)
			  .handler(new LoggingHandler(LogLevel.INFO))
			  .childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					if(sslCtx!=null){
						p.addLast(sslCtx.newHandler(ch.alloc()));
					}
					//
					p.addLast(new EchoServerHandler());
				}
			});
			
			//start the server
			ChannelFuture f = b.bind(PORT).sync();
			
			//wait until the server is closed
			f.channel().closeFuture().sync();
			
		}finally{
			//shut down all event loops to terminate all threads
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
		
	}
	

}
