package netty.io.demo.mqtt;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * 
 * @author yuezh2   2016年12月21日 下午5:46:50
 *
 */
public class MqttServerInitializer  extends ChannelInitializer<SocketChannel> {

	
	private final SslContext sslCtx;
	
	
	/**
	 * 构造函数
	 * @param sslCtx
	 */
	public MqttServerInitializer(SslContext sslCtx){
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
//		p.addLast(MqttEncoder.INSTANCE);
//		p.addLast(new MqttDecoder());
		
		//and then business logic
		//please note we create a handler for every new channel
		//because it has stateful properties
		p.addLast(new MqttServerHandler());
		
	}
	
	
}
