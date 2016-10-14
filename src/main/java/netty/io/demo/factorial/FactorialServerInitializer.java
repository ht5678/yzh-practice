package netty.io.demo.factorial;

import com.alibaba.fastjson.serializer.BigIntegerCodec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.ssl.SslContext;
import scala.sys.process.ProcessImpl.PipedProcesses;

/**
 * 
 * @author yuezh2   2016年7月21日 下午3:50:24
 *
 */
public class FactorialServerInitializer extends ChannelInitializer<SocketChannel>{
	
	private final SslContext sslCtx;
	
	
	
	public FactorialServerInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}


	
	
	
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		if(sslCtx != null){
			pipeline.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		
		//enable stream compression (you can remove these two if unnecessary)
		pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
		pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
		
		
		//add the number codec first
		pipeline.addLast(new BigIntegerDecoder());
		pipeline.addLast(new NumberEncoder());
		
		//and then business logic
		//please note we create a handler for every new channel
		//because it has stateful properties
//		pipeline.addLast(new fa)
		
	}
	
	
	
	
	

}
