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
 * @author yuezh2   2016年12月21日 下午5:35:04
 *
 */
public class MqttClientInitializer  extends ChannelInitializer<SocketChannel>{

	
	private final SslContext sslCtx;
	
	
	
	public MqttClientInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	
	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if(sslCtx!=null){
			pipeline.addLast(sslCtx.newHandler(ch.alloc(),MqttClient.HOST,MqttClient.PORT));
		}
		
		//enable stream compression (you can remove these two if necessary)
		pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
		pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
		
		//add the number codec first
		pipeline.addLast(MqttEncoder.INSTANCE);
		pipeline.addLast(new MqttDecoder());
		
		//and then business logic
		pipeline.addLast(new MqttClientHandler());
	}

	
}
