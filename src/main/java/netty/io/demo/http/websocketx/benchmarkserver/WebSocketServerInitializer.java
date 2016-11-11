package netty.io.demo.http.websocketx.benchmarkserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * 
 * @author yuezh2   2016年11月7日 下午5:37:06
 *
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel>{
	
	
	private final SslContext sslCtx;
	
	
	
	public WebSocketServerInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if(sslCtx != null){
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(65536));
		p.addLast(new WebSocketServerHandler());
		
	}

}
