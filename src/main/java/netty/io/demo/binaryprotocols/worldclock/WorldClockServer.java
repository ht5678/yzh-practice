package netty.io.demo.binaryprotocols.worldclock;

import io.netty.bootstrap.ServerBootstrap;
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
 * @author yuezh2   2016年10月27日 下午4:23:48
 *
 */
public class WorldClockServer {
	
	static final boolean SSL = System.getProperty("ssl") != null;
	
	static final int PORT = Integer.parseInt(System.getProperty("port","8463"));
	
	
	
	public static void main(String[] args) throws Exception{
		//configure ssl
		final SslContext sslCtx;
		
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
			b.group(bossGroup, workerGroup)
			  .channel(NioServerSocketChannel.class)
			  .handler(new LoggingHandler(LogLevel.INFO))
			  .childHandler(new WorldClockServerInitializer(sslCtx));
			
			b.bind(PORT).sync().channel().closeFuture().sync();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	

}