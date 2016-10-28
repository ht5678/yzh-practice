package netty.io.demo.binaryprotocols.worldclock;

import java.util.Arrays;
import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * 
 * @author yuezh2   2016年10月27日 下午5:08:34
 *
 */
public class WorldClockClient {

	static final boolean SSL = System.getProperty("ssl") != null;
	
	static final String HOST = System.getProperty("host","127.0.0.1");
	
	static final int PORT = Integer.parseInt(System.getProperty("port","8463"));
	
	static final List<String> CITIES = Arrays.asList(System.getProperty(
            "cities", "Asia/Seoul,Europe/Berlin,America/Los_Angeles").split(","));
	
	
	
	
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
			  .handler(new WorldClockClientInitializer(sslCtx));
			
			//make a new connection
			Channel ch = b.connect(HOST, PORT).sync().channel();
			
			//get the handler instance to initate the request
			WorldClockClientHandler handler = ch.pipeline().get(WorldClockClientHandler.class);
			
			//request and get the response
			List<String> response = handler.getLocalTimes(CITIES);
			
			//close the connection
			ch.close();
			
			//print the response at last but not least
			for(int i = 0 ; i < CITIES.size() ;i++){
				System.out.format("%28s: %s%n", CITIES.get(i), response.get(i));
			}
			
			
		}finally{
			group.shutdownGracefully();
		}
		
	}
	
	
	
}
