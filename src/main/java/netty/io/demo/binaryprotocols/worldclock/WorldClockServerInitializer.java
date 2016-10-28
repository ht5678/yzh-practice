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
 * @author yuezh2   2016年10月27日 下午3:37:27
 *
 */
public class WorldClockServerInitializer extends ChannelInitializer<SocketChannel>{

	private final SslContext sslCtx;
	
	
	/**
	 * 构造函数
	 * @param sslCtx
	 */
	public WorldClockServerInitializer(SslContext sslCtx){
		this.sslCtx = sslCtx;
	}
	
	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if(sslCtx!=null){
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		
		p.addLast(new ProtobufVarint32FrameDecoder());
		p.addLast(new ProtobufDecoder(WorldClockProtocol.Locations.getDefaultInstance()));
		
		
		p.addLast(new ProtobufVarint32LengthFieldPrepender());
		p.addLast(new ProtobufEncoder());
		
		p.addLast(new WorldClockServerHandler());
		
	}

	
	
}
