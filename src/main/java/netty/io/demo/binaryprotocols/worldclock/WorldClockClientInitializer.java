package netty.io.demo.binaryprotocols.worldclock;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;

/**
 * 
 * @author yuezh2   2016年10月27日 下午4:59:48
 *
 */
public class WorldClockClientInitializer extends ChannelInitializer<SocketChannel>{

	private final SslContext sslCtx;
	
	
	/**
	 * 构造方法
	 * @param sslCtx
	 */
	public WorldClockClientInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	
	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if(sslCtx!=null){
			p.addLast(sslCtx.newHandler(ch.alloc() , WorldClockClient.HOST , WorldClockClient.PORT));
		}
		
		p.addLast(new ProtobufVarint32FrameDecoder());
		p.addLast(new ProtobufDecoder(WorldClockProtocol.LocalTimes.getDefaultInstance()));
		
		p.addLast(new ProtobufVarint32LengthFieldPrepender());
		p.addLast(new ProtobufEncoder());
		
		p.addLast(new WorldClockClientHandler());
	}

	
	
}
