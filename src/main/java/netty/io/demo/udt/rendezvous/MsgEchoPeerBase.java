package netty.io.demo.udt.rendezvous;

import java.net.InetSocketAddress;
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
 * UDT Message Flow Peer
 * <p>
 * Sends one message when a connection is open and echoes back any received data
 * to the other peer.
 * 
 * @author yuezh2   2016年11月21日 下午4:02:51
 *
 */
public abstract class MsgEchoPeerBase {

	protected final int messageSize;
	protected final InetSocketAddress self;
	protected final InetSocketAddress peer;
	
	
	
	protected MsgEchoPeerBase(final InetSocketAddress self , final InetSocketAddress peer , final int messageSize){
		this.messageSize = messageSize;
		this.self = self;
		this.peer = peer;
	}
	
	
	
	
	public void run()throws Exception{
		//configgure the peer
		final ThreadFactory connectFactory = new DefaultThreadFactory("rendezvous");
		final NioEventLoopGroup connectGroup = new NioEventLoopGroup( 1 , 
				connectFactory , NioUdtProvider.MESSAGE_PROVIDER);
		
		try{
			
			final Bootstrap b = new Bootstrap();
			b.group(connectGroup)
			  .channelFactory(NioUdtProvider.MESSAGE_RENDEZVOUS)
			  .handler(new ChannelInitializer<UdtChannel>() {

				@Override
				protected void initChannel(UdtChannel ch) throws Exception {
					ch.pipeline().addLast(
							new LoggingHandler(LogLevel.INFO)
							,new MsgEchoPeerHandler(messageSize));
				}
				  
			});
			  
			//start the peer
			final ChannelFuture f = b.connect(peer, self).sync();
			//wait until the connection closed
			f.channel().closeFuture().sync();
			
		}finally{
			//shutdown the event loop to terminate all threads
			connectGroup.shutdownGracefully();
		}
	}
	
}
