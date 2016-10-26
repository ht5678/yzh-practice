package netty.io.demo.binaryprotocols.factorial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.ssl.SslContext;





/**
 * 
 * Creates a newly configured {@link ChannelPipeline} for a client-side channel.
 * 
 * @author yuezh2   2016年10月26日 下午2:54:21
 *
 */
public class FactorialClientInitializer extends ChannelInitializer<SocketChannel>{
	
	
	private final SslContext sslCtx;
	
	
	
	public FactorialClientInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if(sslCtx!=null){
			pipeline.addLast(sslCtx.newHandler(ch.alloc(),FactorialClient.HOST,FactorialClient.PORT));
		}
		
		//enable stream compression (you can remove these two if necessary)
		pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
		pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
		
		//add the number codec first
		pipeline.addLast(new BigIntegerDecoder());
		pipeline.addLast(new NumberEncoder());
		
		//and then business logic
		pipeline.addLast(new FactorialClientHandler());
		
	}

}
