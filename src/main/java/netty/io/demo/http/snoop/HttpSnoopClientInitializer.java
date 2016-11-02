package netty.io.demo.http.snoop;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.ssl.SslContext;

/**
 * 
 * @author yuezh2   2016年10月31日 下午4:43:57
 *
 */
public class HttpSnoopClientInitializer extends ChannelInitializer<SocketChannel>{
	
	
	private final SslContext sslCtx;
	
	
	public HttpSnoopClientInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		
		//enable https if necessary
		if(sslCtx != null){
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		p.addLast(new HttpClientCodec());
		
		//remove the following line if you dont want automatic content decompression
		p.addLast(new HttpContentDecompressor());
		
		//uncomment the following line if you dont want to handle HttpContents
//		p.addLast(new HttpObjectAggregator(1048576));
		p.addLast(new HttpSnoopClientHandler());
		
		
	}

	
	
}
