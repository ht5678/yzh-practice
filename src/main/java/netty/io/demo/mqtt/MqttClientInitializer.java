package netty.io.demo.mqtt;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 
 * @author yuezh2   2016年12月21日 下午5:35:04
 *
 */
public class MqttClientInitializer  extends ChannelInitializer<SocketChannel>{

	
	private final SslContext sslCtx;

	private static MqttClientHandler handler = new MqttClientHandler();
	
	
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
		//重新连接
		pipeline.addLast(new IdleStateHandler(MqttConstant.READ_TIMEOUT, 0, 0) );
		//and then business logic
		pipeline.addLast(new MqttClientHandler());
	}

	
	/**
	 * 连接通道
	 * @param b
	 */
	static void connect(Bootstrap b){
		b.connect().addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.cause() != null){
					handler.startTime = -1;
					handler.println("Failed to connect : "+future.cause());
				}
			}
		});
	}
}
