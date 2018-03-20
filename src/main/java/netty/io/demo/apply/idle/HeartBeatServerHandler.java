package netty.io.demo.apply.idle;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

public class HeartBeatServerHandler
//						extends SimpleChannelInboundHandler<MqttMessage> {
						extends ChannelInboundHandlerAdapter {

	
	private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
	

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("server channelRead..");
//        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
        try{
	        CharBuffer charBuffer = decoder.decode(((MqttPublishMessage)msg).payload().nioBuffer().asReadOnlyBuffer());
			System.out.println(charBuffer.toString());
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
//        System.out.println("server channelRead..");
////      System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
//      try{
//	        CharBuffer charBuffer = decoder.decode(((MqttPublishMessage)msg).payload().nioBuffer().asReadOnlyBuffer());
//			System.out.println(charBuffer.toString());
//      }catch(Exception e){
//      	e.printStackTrace();
//      }
//		
//	}

}
