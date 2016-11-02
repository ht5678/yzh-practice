package netty.io.demo.http.snoop2;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

/**
 * 
 * @author yuezh2   2016年10月31日 下午2:30:54
 *
 */
public class HttpSnoopServerHandler extends SimpleChannelInboundHandler<Object>{

	private HttpRequest request;
	
	/** buffer that stores the response content */
	private final StringBuilder buf = new StringBuilder();
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof HttpRequest){
			HttpRequest request = this.request = (HttpRequest)msg;
			
			if(HttpUtil.is100ContinueExpected(request)){
				send100Continue(ctx);
			}
			
			buf.setLength(0);
			buf.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
			buf.append("===================================\r\n");
			
			buf.append("VERSION: ").append(request.protocolVersion()).append("\r\n");
			buf.append("HOSTNAME: ").append(request.headers().get(HttpHeaderNames.HOST,"unknow")).append("\r\n");
			buf.append("REQUEST_URI: ").append(request.uri()).append("\r\n\r\n");
			
			HttpHeaders headers = request.headers();
			if(!headers.isEmpty()){
				for(Map.Entry<String, String> h : headers){
					CharSequence key = h.getKey();
					CharSequence value = h.getValue();
					buf.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
				}
				buf.append("\r\n");
			}
			
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
			Map<String, List<String>> params = 	queryStringDecoder.parameters();
			if(!params.isEmpty()){
				for(Entry<String, List<String>> p : params.entrySet()){
					String key = p.getKey();
					List<String> vals = p.getValue();
					for(String val : vals){
						buf.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
					}
				}
				
				buf.append("\r\n");
				
			}
			
			appendDecoderResult(buf, request);
			
		}
		
		
		if(msg instanceof HttpContent){
			HttpContent httpContent = (HttpContent)msg;
			
			ByteBuf content = httpContent.content();
			if(content.isReadable()){
				buf.append("CONTENT: ");
				buf.append(content.toString(CharsetUtil.UTF_8));
				buf.append("\r\n");
				appendDecoderResult(buf, request);
			}
			
			if(msg instanceof LastHttpContent){
				buf.append("END OF CONTENT\r\n");
				
				LastHttpContent trailer = (LastHttpContent)msg;
				if(!trailer.trailingHeaders().isEmpty()){
					buf.append("\r\n");
					for(CharSequence name : trailer.trailingHeaders().names()){
						for(CharSequence value : trailer.trailingHeaders().getAll(name)){
							buf.append("TRAILING HEADER: ");
							buf.append(name).append(" = ").append(value).append("\r\n");
						}
					}
					buf.append("\r\n");
				}
				
				if(!writeResponse(trailer, ctx)){
					//if keep-alive is off , close the connection once the content is fully written
					ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
				}
				
			}
			
		}
		
	}
	
	
	
	
	
	private boolean writeResponse(HttpObject currentObj , ChannelHandlerContext ctx){
		//decide whether to close the connection or not
		boolean keepAlive = HttpUtil.isKeepAlive(request);
		//build the response object
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
				currentObj.decoderResult().isSuccess()?HttpResponseStatus.OK:HttpResponseStatus.BAD_REQUEST,
				Unpooled.copiedBuffer(buf.toString(),CharsetUtil.UTF_8));
		
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");
		
		if(keepAlive){
			//add 'Content-length' header only for a keep-alive connection
			response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
			//add keep-alive header as per : 
			//-http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
			response.headers().set(HttpHeaderNames.CONNECTION , HttpHeaderValues.KEEP_ALIVE);
		}
		
		
		//encode the cookie
		String cookieString = request.headers().get(HttpHeaderNames.COOKIE);
		if(cookieString != null){
			Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieString);
			if(!cookies.isEmpty()){
				//reset the cookies if necessary
				for(Cookie cookie : cookies){
					response.headers().add(HttpHeaderNames.SET_COOKIE , ServerCookieEncoder.STRICT.encode(cookie));
				}
			}
		}else{
			//browser sent no cookie  , add some
			response.headers().add(HttpHeaderNames.SET_COOKIE , ServerCookieEncoder.STRICT.encode("key1","value1"));
			response.headers().add(HttpHeaderNames.SET_COOKIE,ServerCookieEncoder.STRICT.encode("key2","value2"));
		}
		
		//write the response
		ctx.write(response);
		
		
		return keepAlive;
		
	}
	
	
	
	
	
	private static void appendDecoderResult(StringBuilder buf , HttpObject o){
		DecoderResult result = o.decoderResult();
		if(result.isSuccess()){
			return;
		}
		
		buf.append(".. WITH DECODER FAILURE: ");
        buf.append(result.cause());
        buf.append("\r\n");
	}
	
	
	
	
	private static void send100Continue(ChannelHandlerContext ctx){
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.write(response);
	}
	


	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
