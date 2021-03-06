package netty.io.demo.http.websocketx.benchmarkserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * 
 * A Benchmark application for websocket which is served at:
 *
 * http://localhost:8080/websocket
 *
 * Open your browser at http://localhost:8080/, then the benchmark page will be loaded and a Web Socket connection will
 * be made automatically.
 *
 *
 * @author yuezh2   2016年11月7日 下午5:39:41
 *
 */
public class WebSocketServer {

	
	static final boolean SSL = System.getProperty("ssl") !=null;
	static final int PORT = Integer.parseInt(System.getProperty("port",SSL?"8443":"8080"));
	
	
	 
	
	public static void main(String[] args) throws Exception{
		
		//configure ssl
		final SslContext sslCtx;
		
		if(SSL){
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate() , ssc.privateKey()).build();
		}else{
			sslCtx = null;
		}
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			  .channel(NioServerSocketChannel.class)
			  .childHandler(new WebSocketServerInitializer(sslCtx));
			
			Channel ch = b.bind(PORT).sync().channel();
			
			System.out.println("Open your web browser and navigate to " +
                    (SSL? "https" : "http") + "://127.0.0.1:" + PORT + '/');
			
			ch.closeFuture().sync();
			
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
		
	}
	
	
	
}
