package netty.io.demo.echo;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author yuezh2   2016年7月20日 上午11:29:15
 *
 */
@Sharable		//注解@Sharable可以让它在channels间共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

	
	/**
	   *此方法会在连接到服务器后被调用 
	   * */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.write(msg);
	}

	
	
	/**
	   *此方法会在接收到服务器数据后调用 
	   * */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}



	  /**
	   *捕捉到异常 
	   * */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// close the connection when an exception is raised
		cause.printStackTrace();
		ctx.close();
	}
	
	
	

}
