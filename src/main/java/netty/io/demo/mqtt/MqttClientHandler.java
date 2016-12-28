package netty.io.demo.mqtt;

import java.util.LinkedList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

/**
 * 
 * @author yuezh2   2016年12月20日 下午2:03:27
 *
 */
public class MqttClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

	
	
	private final MqttDecoder mqttDecoder = new MqttDecoder();
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		
		final List<Object> out = new LinkedList<Object>();
        mqttDecoder.decode(ctx, msg, out);

//        if(out.size()>0){
	        final MqttPublishMessage decodedMessage = (MqttPublishMessage) out.get(0);
			
			System.out.println(decodedMessage.variableHeader().toString());
//        }
	}
	
	
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
        ctx.close();
	}
	
	

}
