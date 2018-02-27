package netty.io.demo.mqtt;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.LinkedList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

/**
 * 
 * @author yuezh2   2016年12月20日 下午2:03:27
 *
 */
public class MqttClientHandler extends SimpleChannelInboundHandler<MqttMessage>{

	
	private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
	private final MqttDecoder mqttDecoder = new MqttDecoder();
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
		CharBuffer charBuffer = decoder.decode(((MqttPublishMessage)msg).payload().nioBuffer().asReadOnlyBuffer());
		
		System.out.println(charBuffer.toString());
//		final List<Object> out = new LinkedList<Object>();
//        mqttDecoder.decode(ctx, msg, out);

//        if(out.size()>0){
//	        final MqttPublishMessage decodedMessage = (MqttPublishMessage) out.get(0);
//			
//			System.out.println(decodedMessage.variableHeader().toString());
//        }
	}
	
	
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
        ctx.close();
	}
	
	

}
