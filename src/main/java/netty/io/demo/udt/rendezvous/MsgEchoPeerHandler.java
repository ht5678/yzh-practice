package netty.io.demo.udt.rendezvous;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.udt.UdtMessage;
import io.netty.channel.udt.nio.NioUdtProvider;

/**
 * 
 * Handler implementation for the echo peer. It initiates the ping-pong traffic
 * between the echo peers by sending the first message to the other peer on
 * activation.
 * 
 * @author yuezh2   2016年11月21日 下午4:09:21
 *
 */
public class MsgEchoPeerHandler extends SimpleChannelInboundHandler<UdtMessage>{

	
	private final UdtMessage message;
	
	
	public MsgEchoPeerHandler(final int messageSize){
		super(false);
		final ByteBuf byteBuf = Unpooled.buffer(messageSize);
		for(int i = 0 ; i <byteBuf.capacity() ; i++){
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
