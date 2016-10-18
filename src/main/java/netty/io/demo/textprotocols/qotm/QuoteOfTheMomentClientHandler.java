package netty.io.demo.textprotocols.qotm;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;




/**
 * 
 * @author yuezh2   2016年10月18日 下午2:46:35
 *
 */
public class QuoteOfTheMomentClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		String response = msg.content().toString(CharsetUtil.UTF_8);
		if(response.startsWith("QOTM: ")){
			System.out.println("Quote of the moment : "+response.substring(8));
			ctx.close();
		}
	}

	
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	
	
	
}
