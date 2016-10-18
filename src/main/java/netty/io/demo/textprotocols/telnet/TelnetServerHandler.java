package netty.io.demo.textprotocols.telnet;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 
 * Handles a server-side channel.
 * 
 * @author yuezh2   2016年10月17日 下午4:45:16
 *
 */
@Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String>{

	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
		// Generate and write a response.
		String response;
		boolean close = false;
		if(request.isEmpty()){
			response = "Please type something .\r\n";
		}else if("bye".equals(request.toLowerCase())){
			response = "Have a good day!\r\n";
			close = true;
		}else{
			response = "Did you say "+request+ "'?\r\n";
		}
		
		// We do not need to write a ChannelBuffer here.
        // We know the encoder inserted at TelnetPipelineFactory will do the conversion.
		ChannelFuture future = ctx.write(response);
		
		// Close the connection after sending 'Have a good day!'
        // if the client has sent 'bye'.
		
		if(close){
			future.addListener(ChannelFutureListener.CLOSE);
		}
		
	}
	
	
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send greeting for a new connection.
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
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
