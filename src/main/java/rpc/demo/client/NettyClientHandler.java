package rpc.demo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.demo.server.RemoteResponse;

/**
 * 
 * @author yuezh2   2017年6月7日 下午5:37:38
 *
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RemoteResponse>{

	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemoteResponse msg) throws Exception {
		System.out.println(msg.toString());
	}

}
