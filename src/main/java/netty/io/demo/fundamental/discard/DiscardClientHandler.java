package netty.io.demo.fundamental.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;



/**
 * 
 * Handles a client-side channel.
 * 
 * @author yuezh2   2016年10月17日 下午3:36:46
 *
 */
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object>{
	
	private ByteBuf content;
	
	private ChannelHandlerContext ctx;
	
	long counter;
	
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// Server is supposed to send nothing, but if it sends something, discard it.
	}




	/**
	 * 通道开始连接
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
		
		// Initialize the message.
		content = ctx.alloc().directBuffer(DiscardClient.SIZE).writeZero(DiscardClient.SIZE);
		
		//send the initial message
		generateTraffic();
	}




	/**
	 * 通道关闭
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		content.release();
	}





	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
	}
	
	
	
	
	
	
	private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
		
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			if(future.isSuccess()){
				generateTraffic();
			}else{
				future.cause().printStackTrace();
				future.channel().close();
			}
		}
	};
	
	
	
	
	private void generateTraffic(){
		//flush the outbound buffer to the socket
		//once flushed , generate the same amount of traffic again
		ctx.writeAndFlush(content.retainedDuplicate()).addListener(trafficGenerator);
	}
	
	

}
