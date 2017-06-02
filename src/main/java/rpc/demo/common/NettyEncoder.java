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
	protected void encode(ChannelHandlerContext arg0, Object arg1, ByteBuf arg2) throws Exception {
//		byte[] bytes = Serialize
	}

	
}
