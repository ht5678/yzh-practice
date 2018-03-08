package netty.io.demo.mqtt;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author yuezh2   2018年2月27日 下午4:47:40
 *
 */
public class ReconnectionInboundHandler extends SimpleChannelInboundHandler {  
    
	private MqttClient client;  
    
	  public ReconnectionInboundHandler(MqttClient client) {  
	    this.client = client;  
	  }  
    
	  @Override  
	  public void channelInactive(ChannelHandlerContext ctx) throws Exception {  
	    
	    final EventLoop eventLoop = ctx.channel().eventLoop();  
	    
	    eventLoop.schedule(new Runnable() {  
	    
	      @Override  
	      public void run() {  
	        client.createBootstrap(new Bootstrap(), eventLoop);  
	      }  
	    
	    }, 1L, TimeUnit.SECONDS);  
	    
	    super.channelInactive(ctx);  
	    
	  }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		
	}  
    
}
