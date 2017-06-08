package rpc.demo.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 
 * @author yuezh2   2017年6月2日 下午4:11:47
 *
 */
public class NettyDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		if(in.readableBytes()<4){		//TCP报头以4字节为单位
			return;
		}
		
		int dataLength = in.readInt();
		if(dataLength<0){
			ctx.close();
		}
		
		if(in.readableBytes()<dataLength){
			in.resetReaderIndex();
		}
		
		
		
		byte[] bytes = new byte[in.readableBytes()];
		in.readBytes(bytes); 
		Object obj = Serialize.deserialize(bytes);
		out.add(obj);
	}


}
