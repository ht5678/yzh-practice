package zookeeper.zkclient.balance.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import zookeeper.zkclient.balance.server.ServerData;

/**
 * 
 * @author yuezh2   2016年5月1日 上午12:39:46
 *
 */
public class ClientImpl implements Client{

	
	private final BalanceProvider<ServerData> provider;
	
	private EventLoopGroup group = null;
	
	private Channel channel = null;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public ClientImpl(BalanceProvider<ServerData> provider){
		this.provider = provider;
	}
	
	
	public BalanceProvider<ServerData> getProvider() {
		return provider;
	}
	
	
	
	
	@Override
	public void connect() throws Exception {
		// TODO Auto-generated method stub
		try {
			ServerData serverData = provider.getBalanceItem();
			
			System.out.println("connecting to "+serverData.getHost()+":"+serverData.getPort()+", its a balance :"+serverData.getBalance());
			
			group = new NioEventLoopGroup();
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						ChannelPipeline p = sc.pipeline();
						p.addLast(new ClientHandler());
					}
					
				});
			
			ChannelFuture f = b.connect(serverData.getHost(), serverData.getPort()).syncUninterruptibly();
			channel = f.channel();
			
			System.out.println("start success");
			
		} catch (Exception e) {
			System.out.println("链接异常:"+e.getMessage());
		}
	}

	
	
	
	
	@Override
	public void disConnect() throws Exception {
		// TODO Auto-generated method stub
		try{
			if(channel!=null){
				channel.close().syncUninterruptibly();
			}
			group.shutdownGracefully();
			group = null;
			log.debug("disconnected ! ");
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}

	
	
}
