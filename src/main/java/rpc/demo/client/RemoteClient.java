package rpc.demo.client;

import com.google.common.util.concurrent.SettableFuture;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import rpc.demo.common.NettyDecoder;
import rpc.demo.common.NettyEncoder;
import rpc.demo.common.ProviderInfo;
import rpc.demo.server.RemoteRequest;
import rpc.demo.server.RemoteResponse;
import scala.remote;

/**
 * 
 * @author sdwhy
 *
 */
public class RemoteClient {

	private ProviderInfo providerInfo;
	
	
	public RemoteClient(ProviderInfo providerInfo){
		this.providerInfo = providerInfo;
	}
	
	
	//客户端发送一个远程request请求
	public RemoteResponse send(RemoteRequest remoteRequest)throws Exception{
		final SettableFuture<RemoteResponse> future = SettableFuture.create();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(workGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel arg0) throws Exception {
					arg0.pipeline().addLast(new NettyDecoder(), new NettyEncoder() , new nettyclienthandler)
				}
				
			});//保持长连接
			
			ChannelFuture f = bootstrap.connect(providerInfo.getAddress(),providerInfo.getPort()).sync();
			f.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture arg0) throws Exception {
					if(arg0.isSuccess()){
						System.out.println("客户端成功连接到远程服务器......");
					}
				}
			});
			
			//客户端需要向服务端发送一个request请求
			ChannelFuture writeRequest = f.channel().writeAndFlush(remoteRequest);
			writeRequest.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture arg0) throws Exception {
					if(arg0.isSuccess()){
						
					}
				}
			});
			
		}catch(Exception e){
			
		}
		return null;
	}
	
	
	
	
	
}
