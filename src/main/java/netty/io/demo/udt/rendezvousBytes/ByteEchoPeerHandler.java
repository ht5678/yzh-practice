package netty.io.demo.udt.rendezvousBytes;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.udt.nio.NioUdtProvider;

/**
 * 
 * Handler implementation for the echo client. It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server on activation.
 * 
 * 
 * @author yuezh2   2016年11月21日 下午5:37:42
 *
 */
public class ByteEchoPeerHandler extends SimpleChannelInboundHandler<ByteBuf>{

	
	private final ByteBuf message;
	
	
	
	
	public ByteEchoPeerHandler(final int messageSize){
		super(false);
		message = Unpooled.buffer(messageSize);
		for(int i =0 ; i <message.capacity() ; i++){
			message.writeByte((byte)i);
		}
	}
	
	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		ctx.write(msg);
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
