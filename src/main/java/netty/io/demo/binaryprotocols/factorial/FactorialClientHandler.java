package netty.io.demo.binaryprotocols.factorial;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author yuezh2   2016年10月25日 下午5:07:26
 *
 */
public class FactorialClientHandler extends SimpleChannelInboundHandler<BigInteger>{

	
	private ChannelHandlerContext ctx;
	private int receivedMessages;
	private int next = 1;
	final BlockingQueue<BigInteger> answer = new LinkedBlockingQueue<>();
	
	
	
	public BigInteger getFactorial(){
		boolean interrupted = false;
		try{
			for(;;){
				try {
					return answer.take();
				} catch (InterruptedException e) {
					interrupted = true;
				}
			}
		}finally{
			if(interrupted){
				Thread.currentThread().interrupt();
			}
		}
	}
	
	
	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, final BigInteger msg) throws Exception {
		receivedMessages++;
		if(receivedMessages == FactorialClient.COUNT){
			//ofer the answer after closing the connection
			ctx.channel().close().addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					boolean offered = answer.offer(msg);
					assert offered;
				}
			});
		}
	}





	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
		sendNumbers();
	}





	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	
	
	private void sendNumbers(){
		//do not send more than 4096 numbers
		ChannelFuture future = null;
		for(int i = 0 ; i < 4096 && next<=FactorialClient.COUNT ; i++){
			future = ctx.write(Integer.valueOf(next));
			next++;
		}
		
		if(next <= FactorialClient.COUNT){
			assert future != null;
			future.addListener(numberSender);
		}
		ctx.flush();
	}
	
	
	
	private final ChannelFutureListener numberSender = new ChannelFutureListener() {
		
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			if(future.isSuccess()){
				sendNumbers();
			}else{
				future.cause().printStackTrace();
				future.channel().close();
			}
		}
	};
	
	
	
}
