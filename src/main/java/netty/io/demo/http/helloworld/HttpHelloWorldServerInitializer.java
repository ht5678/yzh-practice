package netty.io.demo.http.helloworld;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * 
 * @author yuezh2   2016年10月31日 下午1:51:51
 *
 */
public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel>{
	
	
	private final SslContext sslCtx;
	
	
	/**
	 * 
	 * @param sslCtx
	 */
	public HttpHelloWorldServerInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if(sslCtx != null){
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpHelloWorldServerHandler());
	}

}
