package netty.io.demo.http.snoop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
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
 * @author yuezh2   2016年10月31日 下午3:43:25
 *
 */
public class HttpSnoopServer {

	static final boolean SSL = System.getProperty("ssl")!=null;
	
	static final int PORT = Integer.parseInt(System.getProperty("port",SSL?"8443":"8080"));
	
	
	
	
	public static void main(String[] args) throws Exception{
		//configure ssl
		final SslContext sslCtx;
		
		if(SSL){
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate() , ssc.privateKey() ).build();
		}else{
			sslCtx = null;
		}
		
		//configure the server
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup , workerGroup)
			  .channel(NioServerSocketChannel.class)
			  .handler(new LoggingHandler(LogLevel.INFO))
			  .childHandler(new HttpSnoopServerInitializer(sslCtx));
			
			Channel ch = b.bind(PORT).sync().channel();
			
			System.err.println("Open your web browser and navigate to " +
                    (SSL? "https" : "http") + "://127.0.0.1:" + PORT + '/');
			
			ch.closeFuture().sync();
			
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
	
	
}
