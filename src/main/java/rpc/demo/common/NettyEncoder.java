package rpc.demo.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author sdwhy
 *
 */
public class NettyEncoder extends MessageToByteEncoder<Object>{

	
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		byte[] bytes = Serialize.serialize(msg);
		out.writeInt(bytes.length);
		out.writeBytes(bytes);
	}

	
}
