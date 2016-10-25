package netty.io.demo.binaryprotocols.objectecho;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;




/**
 * 
 * @author yuezh2   2016年10月24日 下午4:53:05
 *
 */
public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {

	private final List<Integer> firstMessage;
	
	
   /**
     * Creates a client-side handler.
     */
    public ObjectEchoClientHandler() {
    	firstMessage = new ArrayList<>(ObjectEchoClient.SIZE);
    	for(int i = 0 ; i < ObjectEchoClient.SIZE ; i++){
    		firstMessage.add(Integer.valueOf(i));
    	}
    }


    
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//send the first message if this handler is a client-side handler
		ctx.writeAndFlush(firstMessage);
	}
	
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// echo back the received object to the server
		ctx.write(msg);
	}
	
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	
    
    
    
    
    
    
}
