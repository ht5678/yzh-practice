package netty.io.demo.fundamental.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * 
 * @author yuezh2   2016年7月20日 上午11:51:50
 *
 */
public class EchoClient {
	
	
	private static final boolean SSL = System.getProperty("ssl") != null;
	
	private static final String HOST = System.getProperty("host","127.0.0.1");
	
	private static final int PORT = Integer.parseInt(System.getProperty("port","8007"));
	
	public static final int SIZE = Integer.parseInt(System.getProperty("size","256"));
	
	
	
	
	
	
	public static void main(String[] args) throws Exception{
		//配置 ssl.git
		final SslContext sslCtx;
		if(SSL){
			sslCtx = SslContextBuilder.forClient()
					.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		}else{
			sslCtx = null;
		}
		
		//configure the client
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group)
			  .channel(NioSocketChannel.class)
			  .option(ChannelOption.TCP_NODELAY, true)
			  .handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					if(sslCtx!=null){
						p.addLast(sslCtx.newHandler(ch.alloc(),HOST,PORT));
					}
					//
					p.addLast(new EchoClientHandler());
				}
				  
			});
			
			//start the client
			ChannelFuture f = b.connect(HOST, PORT);
			
			//wait until the connection is closed
			f.channel().closeFuture().sync();
			
		}finally{
			//shut down the event loop to terminate all threads
			group.shutdownGracefully();
		}
		
	}
	
	
	
	

}
