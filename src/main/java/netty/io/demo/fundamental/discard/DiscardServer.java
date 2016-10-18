package netty.io.demo.fundamental.discard;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * 
 * Discards any incoming data.
 * 
 * @author yuezh2   2016年10月17日 上午10:51:03
 *
 */
public class DiscardServer {
	
	
	static final boolean SSL = System.getProperty("ssl")!=null;
	
	static final int PORT = Integer.parseInt(System.getProperty("port","8009"));
	
	
	
	public static void main(String[] args) throws Exception{
		//configure ssl
		
		final SslContext sslCtx ; 
		
		if(SSL){
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(),ssc.privateKey()).build();
		}else{
			sslCtx = null;
		}
		
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup)
			  .channel(NioServerSocketChannel.class)
			  .handler(new LoggingHandler(LogLevel.INFO))
			  .childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					if(sslCtx!=null){
						p.addLast(sslCtx.newHandler(ch.alloc()));
					}
					p.addLast(new DiscardServerHandler());
				}
				  
			});
			
			
			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(PORT).sync();
			
			
			// Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
			f.channel().closeFuture().sync();
			
			
		}finally{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
		
	}
	

}
