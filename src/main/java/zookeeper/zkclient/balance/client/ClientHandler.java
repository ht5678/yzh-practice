package zookeeper.zkclient.balance.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author yuezh2   2016年5月1日 上午12:37:14
 *
 */
public class ClientHandler extends ChannelHandlerAdapter{

	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}

	
}
