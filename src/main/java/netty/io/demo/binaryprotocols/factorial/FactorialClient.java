package netty.io.demo.binaryprotocols.factorial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * 
 * Sends a sequence of integers to a {@link FactorialServer} to calculate
 * the factorial of the specified integer. 
 * 
 * 
 * @author yuezh2   2016年10月25日 下午5:14:58
 *
 */
public class FactorialClient {
	
	static final boolean SSL = System.getProperty("ssl") != null;
	static final String HOST = System.getProperty("host","127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port","8322"));
	static final int COUNT = Integer.parseInt(System.getProperty("count","1000"));
	
	
	
	
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
			  .handler(new FactorialClientInitializer(sslCtx));
			
			
			//make a new connection
			ChannelFuture f = b.connect(HOST, PORT).sync();
			
			//get the handler instance to retrieve the answer
			FactorialClientHandler handler = 
					(FactorialClientHandler)f.channel().pipeline().last();
			
			//print out the answer
			System.err.format("Factorial of %,d is: %,d", COUNT, handler.getFactorial());
			
		}finally{
			group.shutdownGracefully();
		}
		
	}
	

}
