package netty.io.demo.textprotocols.telnet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;




/**
 * 
 * @author yuezh2   2016年10月17日 下午4:55:09
 *
 */
public class TelnetServerInitializer extends ChannelInitializer<SocketChannel> {
	
	
	private static final StringDecoder DECODER = new StringDecoder();
	
	private static final StringEncoder ENCODER = new StringEncoder();
	
	private static final TelnetServerHandler SERVER_HANDLER = new TelnetServerHandler();
	
	private final SslContext sslCtx;
	
	
	/**
	 * 构造函数
	 * @param sslCtx
	 */
	public TelnetServerInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	
	
	/**
	 * 初始化通道
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		if(sslCtx!=null){
			pipeline.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		//add the text line codec combination first
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		//the encoder and decoder are static as these are sharable
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);
		
		//and then business logic
		pipeline.addLast(SERVER_HANDLER);
	}
	
	
	

}
