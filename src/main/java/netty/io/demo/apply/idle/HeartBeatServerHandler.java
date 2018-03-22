package netty.io.demo.apply.idle;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.util.concurrent.GlobalEventExecutor;

public class HeartBeatServerHandler
//						extends SimpleChannelInboundHandler<MqttMessage> {
						extends ChannelInboundHandlerAdapter {

	
	private CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
	
	private int count = 0;
	
	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	

    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		channels.add(ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("连接断线");
	}

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
        try{
        	count++;
        	if(count==100){
        		System.out.println("server channelRead..");
        		count=0;
    	        CharBuffer charBuffer = decoder.decode(((MqttPublishMessage)msg).payload().nioBuffer().asReadOnlyBuffer());
    			System.out.println("共有"+channels.size()+"个客户端连接 : "+charBuffer.toString());
        	}
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
