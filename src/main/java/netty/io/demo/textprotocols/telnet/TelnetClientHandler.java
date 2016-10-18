package netty.io.demo.textprotocols.telnet;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * Handles a client-side channel.
 * 
 * @author yuezh2   2016年10月17日 下午5:27:09
 *
 */
@Sharable
public class TelnetClientHandler extends SimpleChannelInboundHandler<String>{
	
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.err.println(msg);
	}

	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
        ctx.close();
	}
	

}
