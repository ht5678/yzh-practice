package netty.io.demo.udt.echo.bytes;

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
 * UDT Byte Stream Server
 * <p>
 * Echoes back any received data from a client.
 * 
 * @author yuezh2   2016年11月17日 下午4:28:47
 *
 */
public class ByteEchoServer {

	static final int PORT = Integer.parseInt(System.getProperty("port" , "8007"));
	
	
	
	public static void main(String[] args) throws Exception{
		final ThreadFactory acceptFactory = new DefaultThreadFactory("accept");
		final ThreadFactory connectFactory = new DefaultThreadFactory("connect");
		final NioEventLoopGroup acceptGroup = new NioEventLoopGroup(1 , acceptFactory , NioUdtProvider.BYTE_PROVIDER);
		final NioEventLoopGroup connectGroup = new NioEventLoopGroup(1,connectFactory , NioUdtProvider.BYTE_PROVIDER);
		
		
		//configure the server
		try{
			
			final ServerBootstrap b = new ServerBootstrap();
			b.group(acceptGroup, connectGroup)
			  .channelFactory(NioUdtProvider.BYTE_ACCEPTOR)
			  .option(ChannelOption.SO_BACKLOG, 10)
			  .handler(new LoggingHandler(LogLevel.INFO))
			  .childHandler(new ChannelInitializer<UdtChannel>() {

				@Override
				protected void initChannel(UdtChannel ch) throws Exception {
					ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO),new ByteEchoServerHandler());
				}
				  
			});
			
			
			//start the server
			final ChannelFuture future = b.bind(PORT).sync();
			
			//wait until the server socket is closed
			future.channel().closeFuture().sync();
			
		}finally{
			// Shut down all event loops to terminate all threads.
			acceptGroup.shutdownGracefully();
			connectGroup.shutdownGracefully();
		}
		
	}
	
}
