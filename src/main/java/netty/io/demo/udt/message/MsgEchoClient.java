package netty.io.demo.udt.message;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;


/**
 * 
 * 
 * UDT Message Flow client
 * <p>
 * Sends one message when a connection is open and echoes back any received data
 * to the server. Simply put, the echo client initiates the ping-pong traffic
 * between the echo client and server by sending the first message to the
 * server.
 * 
 * @author yuezh2   2016年11月21日 下午3:08:21
 *
 */
public class MsgEchoClient {

	private static final Logger log = Logger.getLogger(MsgEchoClient.class.getName());
	
	static final String HOST = System.getProperty("host" , "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port" , "8007"));
	static final int SIZE = Integer.parseInt(System.getProperty("size" , "256"));
			
	
	
	public static void main(String[] args) throws Exception{
		//confugre the client
		final ThreadFactory connectFactory = new DefaultThreadFactory("connect");
		final NioEventLoopGroup connectGroup = new NioEventLoopGroup(1 , 
				connectFactory  , NioUdtProvider.MESSAGE_PROVIDER);
		
		try{
			
			final Bootstrap b = new Bootstrap();
			b.group(connectGroup)
			  .channelFactory(NioUdtProvider.MESSAGE_CONNECTOR)
			  .handler(new ChannelInitializer<UdtChannel>() {

				@Override
				protected void initChannel(UdtChannel ch) throws Exception {
					ch.pipeline().addLast(
							new LoggingHandler(LogLevel.INFO),
							new MsgEchoClientHandler());
				}
				  
			});
			
			
			//start the client
			final ChannelFuture f = b.connect(HOST , PORT).sync();
			
		    //wait until the connection is closed
			f.channel().closeFuture().sync();
			
		}finally{
			//shut donw the event loop to terminate all threads
			connectGroup.shutdownGracefully();
		}
		
	}
	
	
	
}
