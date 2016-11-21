package netty.io.demo.udt.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.udt.UdtMessage;
import io.netty.channel.udt.nio.NioUdtProvider;

/**
 * 
 * Handler implementation for the echo client. It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server on activation.
 * 
 * @author yuezh2   2016年11月21日 下午3:06:39
 *
 */
public class MsgEchoClientHandler extends SimpleChannelInboundHandler<UdtMessage>{

	private final UdtMessage message;
	
	
	
	public MsgEchoClientHandler(){
		super(false);
		final ByteBuf byteBuf = Unpooled.buffer(MsgEchoClient.SIZE);
		for(int i = 0 ; i<byteBuf.capacity() ; i++){
			byteBuf.writeByte((byte)i);
		}
		message = new UdtMessage(byteBuf);
	}
	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UdtMessage msg) throws Exception {
		ctx.write(message);
	}



	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("echo active : "+NioUdtProvider.socketUDT(ctx.channel()).toStringOptions());
		ctx.writeAndFlush(message);
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
