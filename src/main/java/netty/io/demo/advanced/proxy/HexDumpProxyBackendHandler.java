package netty.io.demo.advanced.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author yuezh2   2016年11月16日 下午2:47:05
 *
 */
public class HexDumpProxyBackendHandler extends ChannelInboundHandlerAdapter{

	private final Channel inboundChannel;
	
	
	
	public HexDumpProxyBackendHandler(Channel inboundChannel){
		this.inboundChannel = inboundChannel;
	}



	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.read();
	}



	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		HexDumpProxyFrontendHandler.closeOnFlush(inboundChannel);
	}



	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		inboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()){
					ctx.channel().read();
				}else{
					future.channel().close();
				}
			}
		});
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		HexDumpProxyFrontendHandler.closeOnFlush(ctx.channel());
	}
	
	
	
	
	
	
	
	
}
