package netty.io.demo.mqtt;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import netty.io.demo.fundamental.uptime.UptimeClient;

/**
 * 
 * @author yuezh2   2016年12月20日 下午2:03:27
 *
 */
public class MqttClientHandler extends SimpleChannelInboundHandler<MqttMessage>{

	
	private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
	private final MqttDecoder mqttDecoder = new MqttDecoder();
	long startTime = -1;
	
	
	
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
	
	
	

	/**
	 * 通道开始连接
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if(startTime<0){
			startTime = System.currentTimeMillis();
		}
		println("Connected to : "+ctx.channel().remoteAddress());
	}

	
	/**
	 * 通道失去连接
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		println("Disconnected from : " +ctx.channel().remoteAddress());
	}


	/**
	 * 通道注销
	 */
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		println("Sleeping for : "+UptimeClient.RECONNECT_DELAY+"s");
		
		final EventLoop loop = ctx.channel().eventLoop();
		loop.schedule(new Runnable() {
			public void run() {
				println("Reconnecting to :"+UptimeClient.HOST+":"+UptimeClient.PORT);
				Bootstrap b = new Bootstrap();
				MqttClient.initBootstrap(b , loop);
//				MqttClientInitializer.connect(b);
			}
		}, MqttConstant.RECONNECT_DELAY, TimeUnit.SECONDS);
	}



	
	/**
	 * 
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(!(evt instanceof IdleStateEvent)){
			return;
		}
		
		IdleStateEvent e = (IdleStateEvent)evt;
		
		if(e.state() == IdleState.READER_IDLE){
			//the connection was ok but there was no traffic for last period
			println("Disconnecting due to inbound traffic");
			ctx.close();
		}
	}




	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
        ctx.close();
	}
	
	
	
    void println(String msg) {
        if (startTime < 0) {
            System.err.format("[SERVER IS DOWN] %s%n", msg);
        } else {
            System.err.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis() - startTime) / 1000, msg);
        }
    }

}
