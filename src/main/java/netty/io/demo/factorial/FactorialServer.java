package netty.io.demo.factorial;

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
 * @author yuezh2   2016年7月21日 下午3:45:29
 *
 */
public class FactorialServer {

	
	private static final boolean SSL = System.getProperty("ssl") != null;
	
	private static final int PORT = Integer.parseInt(System.getProperty("port","8322"));
	
	
	
	
	
	public static void main(String[] args) throws Exception{
		//配置ssl
		final SslContext sslCtx;
		
		if(SSL){
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate() , ssc.privateKey()).build();
		}else{
			sslCtx = null;
		}
		
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workderGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap b = new ServerBootstrap();
//			b.group(bossGroup,workderGroup)
//			  .channel(NioServerSocketChannel.class)
//			  .handler(new LoggingHandler(LogLevel.INFO))
//			  .childHandler(new fa)
		}finally{
			
		}
		
	}
	
	
	
}
