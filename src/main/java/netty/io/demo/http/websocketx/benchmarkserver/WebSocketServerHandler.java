package netty.io.demo.http.websocketx.benchmarkserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import netty.io.demo.http.websocketx.server.WebSocketServer;

/**
 * 
 * Handles handshakes and messages
 * 
 * @author yuezh2   2016年11月7日 下午4:48:56
 *
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>{

	
	private static final String WEBSOCKET_PATH = "/websocket";
	private WebSocketServerHandshaker handshaker;
	
	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof FullHttpRequest){ 
			handleHttpRequest(ctx, (FullHttpRequest)msg);
		}else if(msg instanceof WebSocketFrame){
			handleWebSocketFrame(ctx, (WebSocketFrame)msg);
		}
	}

	
	
	
	
	



	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	
	
	
	
	
	private void handleWebSocketFrame(ChannelHandlerContext ctx , WebSocketFrame frame){
		//check for closing frame
		if(frame instanceof CloseWebSocketFrame){
			handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame.retain());
			return;
		}
		
		if(frame instanceof PingWebSocketFrame){
			ctx.write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		
		if(frame instanceof TextWebSocketFrame){
			//echo the frame
			ctx.write(frame.retain());
			return;
		}
		
		if(frame instanceof BinaryWebSocketFrame){
			//echo the frame
			ctx.write(frame.retain());
			return;
		}
		
		
	}
	
	


	
	
	
	private void handleHttpRequest(ChannelHandlerContext ctx , FullHttpRequest req){
		//handle a bad request
		if(!req.decoderResult().isSuccess()){
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		
		
		//allow only get method
		if(req.method() != HttpMethod.GET){
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
			return;
		}
		
		
		//send the demo page and favicon.ico
		if("/".equals(req.uri())){
			ByteBuf content = WebSocketServerBenchmarkPage.getContent(getWebSocketLocation(req));
			FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK , content);
			
			res.headers().set(HttpHeaderNames.CONTENT_TYPE , "text/html ; charset=UTF-8");
			HttpUtil.setContentLength(res, content.readableBytes());
			
			sendHttpResponse(ctx, req, res);
			return;
		}
		
		if("/favicon.ico".equals(req.uri())){
			FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
			sendHttpResponse(ctx, req, res);
			return;
		}
		
		
		
		//handshake
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				getWebSocketLocation(req), null, true , 5*1024*1024);
		handshaker = wsFactory.newHandshaker(req);
		if(handshaker == null){
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		}else{
			handshaker.handshake(ctx.channel(), req);
		}
		
	}
	
	
	
	
	
	
	private static void sendHttpResponse(
			ChannelHandlerContext ctx , FullHttpRequest req , FullHttpResponse res){
		//generate an error page if response getStatus code is not OK(200)
		if(res.status().code() != 200){
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString() , CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			HttpUtil.setContentLength(res , res.content().readableBytes());
		}
		
		
		//send the response and close the connection if necessary
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if(!HttpUtil.isKeepAlive(req) || res.status().code() != 200){
			f.addListener(ChannelFutureListener.CLOSE);
		}
		
		
	}
	
	
	
	
	
	private static String getWebSocketLocation(FullHttpRequest req){
		String location = req.headers().get(HttpHeaderNames.HOST) + WEBSOCKET_PATH;
		if(WebSocketServer.SSL){
			return "wss://"+location;
		}else{
			return "ws://"+location;
		}
	}
	
	
	


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	
}
