package netty.io.demo.mqtt;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import zookeeper.zkclient.balance.client.Client;

/**
 * 断线重连
 * @author yuezh2   2018年2月27日 下午1:53:58
 *
 */
public class ReconnectionListener implements ChannelFutureListener {  
    
	private MqttClient client;  
    
	 public ReconnectionListener(MqttClient client) {  
	   this.client = client;  
	 }  
    
	 
	 @Override  
	 public void operationComplete(ChannelFuture channelFuture) throws Exception {  
		   if (!channelFuture.isSuccess()) {
		     System.out.println("Reconnect");  
		     final EventLoop loop = channelFuture.channel().eventLoop();  
		     loop.schedule(new Runnable() {  
		       @Override  
		       public void run() {  
		         client.createBootstrap(new Bootstrap(), loop);  
		       }  
		    
		     }, 1L, TimeUnit.SECONDS);  
		    
		   }  
    
	 }  
    
	 
	 
	 public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {  
	     
	     if (bootstrap != null) {  
	       final MyInboundHandler handler = new MyInboundHandler(this);  
	       bootstrap.group(eventLoop);  
	       bootstrap.channel(NioSocketChannel.class);  
	       bootstrap.option(ChannelOption.SO_KEEPALIVE, true);  
	       bootstrap.handler(new ChannelInitializer<SocketChannel>() {  
	         @Override  
	         protected void initChannel(SocketChannel socketChannel) throws Exception {  
	     
	           socketChannel.pipeline().addLast(handler);  
	     
	         }  
	     
	       });  
	     
	       bootstrap.remoteAddress("localhost", 8888);
	     
	       bootstrap.connect().addListener(new ConnectionListener(this)); 
	     
	     }  
	     
	     return bootstrap;  
	     
	   } 
}
