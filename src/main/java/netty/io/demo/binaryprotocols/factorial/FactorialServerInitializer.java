package netty.io.demo.binaryprotocols.factorial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.ssl.SslContext;
import netty.io.demo.binaryprotocols.factorial.BigIntegerDecoder;
import netty.io.demo.binaryprotocols.factorial.NumberEncoder;



/**
 * 
 * Creates a newly configured {@link ChannelPipeline} for a server-side channel.
 * 
 * 
 * @author yuezh2   2016年10月25日 下午4:41:06
 *
 */
public class FactorialServerInitializer extends ChannelInitializer<SocketChannel> {
	
	
	private final SslContext sslCtx;
	
	
	/**
	 * 构造函数
	 * @param sslCtx
	 */
	public FactorialServerInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		
		if(sslCtx != null){
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		
		//enable stream compression  (you can remove these two if unnecessary)
		p.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
		p.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
		
		
		//add the number codec first
		p.addLast(new BigIntegerDecoder());
		p.addLast(new NumberEncoder());
		
		//and then business logic
		//please note we create a handler for every new channel
		//because it has stateful properties
		p.addLast(new FactorialServerHandler());
		
	}
	
	
	

}
