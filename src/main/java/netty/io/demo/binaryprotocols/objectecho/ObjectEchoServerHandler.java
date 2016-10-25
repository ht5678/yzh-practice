package netty.io.demo.binaryprotocols.objectecho;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * 
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 * 
 * @author yuezh2   2016年10月19日 下午4:51:45
 *
 */
public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter{
	
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//echo back the received object to the client
//		System.out.println(msg);
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
