package netty.io.demo.mqtt;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.CharsetUtil;

/**
 * 
 * @author yuezh2   2016年12月21日 下午5:37:28
 *
 */
public class MqttClient {

	static final boolean SSL = System.getProperty("ssl") != null;
	static final String HOST = System.getProperty("host","127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port","8322"));
	static final int COUNT = Integer.parseInt(System.getProperty("count","1000"));
	
	
	private static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);
	
	
	
	
	
	public static void main(String[] args) throws Exception{
		
		MqttClient client = new MqttClient();
		client.initBootstrap(new Bootstrap() , new NioEventLoopGroup());
		
	}
	
	public static Bootstrap initBootstrap(Bootstrap b , EventLoopGroup group ){
		try{
			
			//configure ssl
			final SslContext sslCtx;
			
			if(SSL){
				sslCtx = SslContextBuilder.forClient()
						.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
			}else{
				sslCtx = null;
			}
			
			
			
			b.group(group)
			  .channel(NioSocketChannel.class)
			  .handler(new MqttClientInitializer(sslCtx));
			
			
			//make a new connection
			Channel ch = b.connect(HOST, PORT).sync().channel();
			
			
			// Read commands from the stdin.
    		ChannelFuture lastWriteFuture = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    		for(;;){
    			String line = in.readLine();
    			if(line == null){
    				break;
    			}
    			
    			Thread.sleep(1000);
    			
//    			ByteBuf byteBuf = MqttEncoder.INSTANCE.doEncode(ALLOCATOR, createPublishMessage(line));
    			
    			// Sends the received line to the server.
    			lastWriteFuture = ch.writeAndFlush(createPublishMessage(line));
    			
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
    		
//    		ch.close().sync();
    		
			//print out the answer
//			System.err.format("mqtt of %,d is: %,d", COUNT, handler);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		finally{
//			group.shutdownGracefully();
//		}
		
		return b;
	}
	
	
//	   public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {  
//		     if (bootstrap != null) {
//		       final ReconnectionInboundHandler handler = new ReconnectionInboundHandler(this);  
//		       bootstrap.group(eventLoop);  
//		       bootstrap.channel(NioSocketChannel.class);  
//		       bootstrap.option(ChannelOption.SO_KEEPALIVE, true);  
//		       bootstrap.handler(new ChannelInitializer<SocketChannel>() {  
//		         @Override  
//		         protected void initChannel(SocketChannel socketChannel) throws Exception {  
//		           socketChannel.pipeline().addLast(handler);  
//		         }  
//		       });  
//		       bootstrap.remoteAddress("localhost", 8888);
//		       bootstrap.connect().addListener(new ReconnectionListener(this)); 
//		     }  
//		     
//		     return bootstrap;  
//		     
//		   } 
	
	
	
	private static MqttPublishMessage createPublishMessage(String message) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(MqttMessageType.PUBLISH, false, MqttQoS.AT_LEAST_ONCE, true, 0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader("/abc", 1234);
        ByteBuf payload =  ALLOCATOR.buffer();
        payload.writeBytes(message.getBytes(CharsetUtil.UTF_8));
        return new MqttPublishMessage(mqttFixedHeader, mqttPublishVariableHeader, payload);
    }
	
	
	
	
	
}
