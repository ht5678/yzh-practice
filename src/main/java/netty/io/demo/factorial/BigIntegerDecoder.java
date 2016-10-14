package netty.io.demo.factorial;

import java.math.BigInteger;
import java.util.List;

import org.elasticsearch.common.netty.handler.codec.frame.CorruptedFrameException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 
 * 
 *  * Decodes the binary representation of a {@link BigInteger} prepended
 * with a magic number ('F' or 0x46) and a 32-bit integer length prefix into a
 * {@link BigInteger} instance.  For example, { 'F', 0, 0, 0, 1, 42 } will be
 * 
 * 
 * @author yuezh2   2016年7月21日 下午3:54:26
 *
 */
public class BigIntegerDecoder extends ByteToMessageDecoder{

	
	
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//wait until the length prefix is available
		if(in.readableBytes() < 5){
			return;
		}
		
		in.markReaderIndex();
		
		//check the magic number
		int magicNumber = in.readUnsignedByte();
		if(magicNumber != 'F'){
			in.resetReaderIndex();
			throw new CorruptedFrameException("Invalid magic number : "+magicNumber);
		}
		
		
		//wait until the whole data is avaible
		int dataLength = in.readInt();
		if(in.readableBytes() < dataLength){
			in.resetReaderIndex();
			return;
		}
		
		
		//convert the received data into a new BigInteger
		byte[] decoded = new byte[dataLength];
		in.readBytes(decoded);
		
		out.add(new BigInteger(decoded));
		
	}

	
	
}
