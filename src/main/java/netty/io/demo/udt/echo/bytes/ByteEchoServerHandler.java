package netty.io.demo.udt.echo.bytes;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * Handler implementation for the echo server.
 * 
 * @author yuezh2   2016年11月17日 上午11:42:06
 *
 */
@Sharable
public class ByteEchoServerHandler extends ChannelInboundHandlerAdapter{

	
	
	
	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		System.err.println("ECHO active " + NioUdtProvider.socketUDT(ctx.channel()).toStringOptions());
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
