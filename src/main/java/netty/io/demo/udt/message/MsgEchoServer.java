package netty.io.demo.udt.message;

import java.util.concurrent.ThreadFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * 
 * @author yuezh2   2016年11月21日 下午2:48:25
 *
 */
public class MsgEchoServer {

	static final int PORT = Integer.parseInt(System.getProperty("port" , "8007"));
	
	
	
	public static void main(String[] args) throws Exception{
		final ThreadFactory acceptFactory = new DefaultThreadFactory("accept");
		final ThreadFactory connectFactory = new DefaultThreadFactory("connect");
		
		final NioEventLoopGroup acceptGroup = 
				new NioEventLoopGroup(1 , acceptFactory , NioUdtProvider.MESSAGE_PROVIDER);
		
		final NioEventLoopGroup connectGroup =
				new NioEventLoopGroup(1 , connectFactory , NioUdtProvider.MESSAGE_PROVIDER);
		
		
		//configure the server
		try{
			final ServerBootstrap b = new ServerBootstrap();
			
			b.group(acceptGroup, connectGroup)
			  .channelFactory(NioUdtProvider.MESSAGE_ACCEPTOR)
			  .option(ChannelOption.SO_BACKLOG, 10)
			  .handler(new LoggingHandler(LogLevel.INFO))
			  .childHandler(new ChannelInitializer<UdtChannel>() {

				@Override
				protected void initChannel(UdtChannel ch) throws Exception {
					ch.pipeline().addLast(
							new LoggingHandler(LogLevel.INFO)
						   ,new MsgEchoServerHandler());
				}
				  
			});
			
			//start the server
			final ChannelFuture future = b.bind(PORT).sync();
			
			//wait until the server socket is closed
			future.channel().closeFuture().sync();
			
		}finally{
			acceptGroup.shutdownGracefully();
			connectGroup.shutdownGracefully();
		}
		
	}
	
	
	
}
