package netty.io.demo.http.file;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 
 * @author yuezh2   2016年11月3日 下午4:17:58
 *
 */
public class HttpStaticFileServerInitializer extends ChannelInitializer<SocketChannel>{

	
	private final SslContext sslCtx;
	
	
	
	public HttpStaticFileServerInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if(sslCtx != null){
			pipeline.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpStaticFileServerHandler());
	}

	
	
	
}
