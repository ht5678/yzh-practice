package netty.io.demo.fundamental.uptime;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;



/**
 * 
 * Keep reconnecting to the server while printing out the current uptime and
 * connection attempt getStatus.
 * 
 * @author yuezh2   2016年10月13日 下午5:49:26
 *
 */
//必须加上这个注解,否则报错
//io.netty.channel.ChannelPipelineException: netty.io.demo.fundamental.uptime.UptimeClientHandler is not a @Sharable handler, so can't be added or removed multiple times.
@Sharable
public class UptimeClientHandler extends SimpleChannelInboundHandler<Object> {
	
	
	long startTime = -1;
	
	
	/**
	 * 接收数据处理
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// Discard received data
		System.out.println(msg);
	}

	/**
	 * 通道注销
	 */
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		println("Sleeping for : "+UptimeClient.RECONNECT_DELAY+"s");
		
		final EventLoop loop = ctx.channel().eventLoop();
		loop.schedule(new Runnable() {
			public void run() {
				println("Reconnecting to :"+UptimeClient.HOST+":"+UptimeClient.PORT);
				UptimeClient.connect(UptimeClient.configureBootstrap(new Bootstrap(), loop));
			}
		}, UptimeClient.RECONNECT_DELAY, TimeUnit.SECONDS);
	}

	/**
	 * 通道开始连接
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if(startTime<0){
			startTime = System.currentTimeMillis();
		}
		println("Connected to : "+ctx.channel().remoteAddress());
	}

	
	/**
	 * 通道失去连接
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		println("Disconnected from : " +ctx.channel().remoteAddress());
	}

	
	/**
	 * 用户事件触发
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(!(evt instanceof IdleStateEvent)){
			return;
		}
		
		IdleStateEvent e = (IdleStateEvent)evt;
		
		if(e.state() == IdleState.READER_IDLE){
			//the connection was ok but there was no traffic for last period
			println("Disconnecting due to inbound traffic");
			ctx.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	
	
	
    void println(String msg) {
        if (startTime < 0) {
            System.err.format("[SERVER IS DOWN] %s%n", msg);
        } else {
            System.err.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis() - startTime) / 1000, msg);
        }
    }
    
    

}
