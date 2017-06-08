package rpc.demo.client;

import com.google.common.util.concurrent.SettableFuture;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.demo.server.RemoteResponse;

/**
 * 
 * @author yuezh2   2017年6月7日 下午5:37:38
 *
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RemoteResponse>{

	SettableFuture<RemoteResponse> future = SettableFuture.create(); 
	
	public NettyClientHandler(SettableFuture<RemoteResponse> future){
		this.future=future;
	}
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemoteResponse msg) throws Exception {
		future.set(msg);
	}

}
