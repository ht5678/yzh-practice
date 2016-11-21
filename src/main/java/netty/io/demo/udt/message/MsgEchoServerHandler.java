package netty.io.demo.udt.message;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.channel.ChannelHandlerContext;

/**
 * Handler implementation for the echo server.
 * 
 * @author yuezh2   2016年11月18日 下午4:37:22
 *
 */
@Sharable
public class MsgEchoServerHandler extends ChannelInboundHandlerAdapter{

	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("echo active   "+NioUdtProvider.socketUDT(ctx.channel()).toStringOptions());
		System.out.println();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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
