package netty.io.demo.binaryprotocols.objectecho;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * 
 * @author yuezh2   2016年10月24日 下午4:54:05
 *
 */
public class ObjectEchoClient {

	static final boolean SSL = System.getProperty("ssl") != null;
	
	static final String HOST = System.getProperty("host","127.0.0.1");
	
	static final int PORT = Integer.parseInt(System.getProperty("port","8007"));
	
	static final int SIZE = Integer.parseInt(System.getProperty("size","256"));
	
	
	
	
	public static void main(String[] args) throws Exception{
		//configure ssl
		final SslContext sslCtx;
		
		if(SSL){
			sslCtx = SslContextBuilder.forClient()
					.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		}else{
			sslCtx = null;
		}
		
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try{
			Bootstrap b = new Bootstrap();
			b.group(group)
			  .channel(NioSocketChannel.class)
			  .handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					if(sslCtx != null){
						p.addLast(sslCtx.newHandler(ch.alloc(), HOST , PORT));
					}
					
					p.addLast(
							new ObjectEncoder()
							,new ObjectDecoder(ClassResolvers.cacheDisabled(null))
							,new ObjectEchoClientHandler());
					
				}
				  
			});
			
			
			//start the connection attempt
			b.connect(HOST, PORT).sync().channel().closeFuture().sync();
			
		}finally{
			group.shutdownGracefully();
		}
		
	}
	
	
	
	
	
}
