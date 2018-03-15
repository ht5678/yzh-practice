package netty.io.demo.mqtt;

import java.net.InetAddress;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 
 * @author yuezh2   2016年12月21日 下午5:45:25
 *
 */
public class MqttServerHandler extends SimpleChannelInboundHandler<MqttMessage>{

	private final MqttDecoder mqttDecoder = new MqttDecoder();
	
	private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
	
	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	private static int count = 0;
	
	private static final  List<Channel> list = new ArrayList<>(); 
	
	private static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);
	
	
	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		
		// Send greeting for a new connection.
        ctx.writeAndFlush(createPublishMessage("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n"   +   "It is " + new Date() + " now.\r\n"));
//        ctx.flush();
		
//		channels.add(ctx.channel());
//		
//		if(list.size()<10){
//			list.add(ctx.channel());
//		}
//		
//		System.out.println("在线用户数:"+channels.size()+" : 消息数 : "+count + "");
	}
	
	
	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
		
		
//		final List<Object> out = new LinkedList<Object>();
//        mqttDecoder.decode(ctx, msg, out);
//        final MqttPublishMessage decodedMessage = (MqttPublishMessage) out.get(0);
        
//		System.out.println(decodedMessage.variableHeader().toString());
//		System.out.println(decodedMessage.fixedHeader().toString());
		
//		CharBuffer charBuffer = decoder.decode(decodedMessage.payload().nioBuffer().asReadOnlyBuffer());
		
		CharBuffer charBuffer = decoder.decode(((MqttPublishMessage)msg).payload().nioBuffer().asReadOnlyBuffer());
		System.out.println(charBuffer.toString());
		count++;
		
		if(channels.size()>0){
			for(Channel c : channels){
				if(c!=null && c.isActive() && c.isWritable()){
					c.writeAndFlush(msg);
//					c.write(msg);
				}
			}
		}
		
//		msg.release();
//		ctx.write(msg);
		// Close the connection if the client has sent 'bye'.
		if("bye".equals(charBuffer.toString()) && ctx.channel().isActive()){
			ctx.close();
		}
		
	}


	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	
	
	private static MqttPublishMessage createPublishMessage(String message) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(MqttMessageType.PUBLISH, false, MqttQoS.AT_LEAST_ONCE, true, 0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader("/abc", 1234);
        ByteBuf payload =  ALLOCATOR.buffer();
        payload.writeBytes(message.getBytes(CharsetUtil.UTF_8));
        return new MqttPublishMessage(mqttFixedHeader, mqttPublishVariableHeader, payload);
    }
	
}
