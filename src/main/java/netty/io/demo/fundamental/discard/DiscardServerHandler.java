package netty.io.demo.fundamental.discard;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;



/**
 * 
 * 处理server端通道
 * 
 * @author yuezh2   2016年10月14日 下午4:31:44
 *
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {
	
	
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		//discard
	}
	
	
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	

}
