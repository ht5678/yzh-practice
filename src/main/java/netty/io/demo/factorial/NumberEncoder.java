package netty.io.demo.factorial;

import java.math.BigInteger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 *  * Encodes a {@link Number} into the binary representation prepended with
 * a magic number ('F' or 0x46) and a 32-bit length prefix.  For example, 42
 * will be encoded to { 'F', 0, 0, 0, 1, 42 }.
 * 
 * @author yuezh2   2016年7月21日 下午3:59:43
 *
 */
public class NumberEncoder extends MessageToByteEncoder<Number>{

	
	
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Number msg, ByteBuf out) throws Exception {
		//convert to a biginteger first for easier implementation
		BigInteger v ;
		if(msg instanceof BigInteger){
			v = (BigInteger)msg;
		}else{
			v = new BigInteger(String.valueOf(msg));
		}
		
		
		//convert the number into a byte array
		byte[] data = v.toByteArray();
		int dataLength = data.length;
		
		//write a message
		out.writeByte((byte) 'F');
		out.writeInt(dataLength);
		out.writeBytes(data);
		
	}

	
	
}
