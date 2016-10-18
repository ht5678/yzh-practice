package netty.io.demo.textprotocols.securechat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * 
 * @author yuezh2   2016年10月18日 下午5:17:30
 *
 */
public class SecureChatClient {

	
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));
    
	
    
    public static void main(String[] args) throws Exception{
		//configure ssl
    	final SslContext sslCtx = SslContextBuilder.forClient()
    			.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
    	
    	
    	EventLoopGroup group = new NioEventLoopGroup();
    	
    	try{
    		Bootstrap b = new Bootstrap();
    		b.group(group)
    		  .channel(NioSocketChannel.class)
    		  .handler(new SecureChatClientInitializer(sslCtx));
    		
    		// Start the connection attempt.
    		Channel ch = b.connect(HOST,PORT).sync().channel();
    		
    		// Read commands from the stdin.
    		ChannelFuture lastWriteFuture = null;
    		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    		for(;;){
    			String line = in.readLine();
    			if(line == null){
    				break;
    			}
    			
    			// Sends the received line to the server.
    			lastWriteFuture = ch.writeAndFlush(line+"\r\n");
    			
    			// If user typed the 'bye' command, wait until the server closes
                // the connection.
    			if("bye".equals(line.toLowerCase())){
    				ch.closeFuture().sync();
    				break;
    			}
    		}
    		
    		
    		// Wait until all messages are flushed before closing the channel.
    		if(lastWriteFuture!=null){
    			lastWriteFuture.sync();
    		}
    		
    	}finally{
    		group.shutdownGracefully();
    	}
    	
	}
    
    
	
	
}
