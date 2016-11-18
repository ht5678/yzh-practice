package netty.io.demo.udt.echo.bytes;

import java.util.concurrent.ThreadFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * 
 * 
 * UDT Byte Stream Client
 * <p>
 * Sends one message when a connection is open and echoes back any received data
 * to the server. Simply put, the echo client initiates the ping-pong traffic
 * between the echo client and server by sending the first message to the
 * server.
 * 
 * @author yuezh2   2016年11月18日 下午3:06:37
 *
 */
public class ByteEchoClient {
	
	
	static final String HOST = System.getProperty("host" , "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port","8007"));
	static final int SIZE = Integer.parseInt(System.getProperty("size" , "256"));
	
	
	
	public static void main(String[] args) throws Exception{
		//configure the client
		final ThreadFactory connectFactory = new DefaultThreadFactory("connect");
		final NioEventLoopGroup connectGroup = new NioEventLoopGroup(1 , connectFactory , NioUdtProvider.BYTE_PROVIDER);
		
		
		try{
			final Bootstrap b = new Bootstrap();
			b.group(connectGroup)
			  .channelFactory(NioUdtProvider.BYTE_CONNECTOR)
			  .handler(new ChannelInitializer<UdtChannel>() {

				@Override
				protected void initChannel(UdtChannel ch) throws Exception {
					ch.pipeline().addLast(
							new LoggingHandler(LogLevel.INFO) ,
							new ByteEchoClientHandler());
				}
				  
			});
			
			
			//start the client
			final ChannelFuture f = b.connect(HOST, PORT).sync();
			
			//wait until the connnection is closed
			f.channel().closeFuture().sync();
			
		}finally{
			//shut down the event loop to terminate all thread
			connectGroup.shutdownGracefully();
		}
	}
	
	

}
