package zookeeper.zkclient.balance.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;



/**
 * 
 * @author yuezh2   2016年4月30日 下午11:05:37
 *
 */
public class ServerHandler extends ChannelHandlerAdapter {

	
	private final BalanceUpdateProvider balanceUpdater ;
	
	private static final Integer BALANCE_STEP = 1;
	
	
	
	public ServerHandler(BalanceUpdateProvider balanceUpdater){
		this.balanceUpdater = balanceUpdater;
	}
	
	
	
	public BalanceUpdateProvider getBalanceUpdater(){
		return this.balanceUpdater;
	}



	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("one client connect...");
		balanceUpdater.addBalance(BALANCE_STEP);
	}



	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		balanceUpdater.reduceBalance(BALANCE_STEP);
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
	
	
	
}
