package netty.io.demo.binaryprotocols.worldclock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.Continent;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.LocalTime;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.LocalTimes;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.Location;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.Locations;

/**
 * 
 * 
 * @author yuezh2   2016年10月27日 下午4:35:09
 *
 */
public class WorldClockClientHandler extends SimpleChannelInboundHandler<LocalTimes>{

	private static final Pattern DELIM = Pattern.compile("/");
	
	//stateful properties
	private volatile Channel channel;
	
	private final BlockingQueue<LocalTimes> answer = new LinkedBlockingQueue<>();
	
	
	
	public WorldClockClientHandler(){
		super(false);
	}
	
	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LocalTimes times) throws Exception {
		answer.add(times);
	}

	
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		channel = ctx.channel();
	}

	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	
	
	
	
	public List<String> getLocalTimes(Collection<String> cities){
		Locations.Builder builder = Locations.newBuilder();
		
		for(String c : cities){
			String[] components = DELIM.split(c);
			builder.addLocation(Location.newBuilder()
					  .setContinent(Continent.valueOf(components[0].toUpperCase()))
					  .setCity(components[1]).build());
		}
		
		channel.writeAndFlush(builder.build());
		
		LocalTimes localTimes;
		boolean interrupted = false;
		for(;;){
			try {
				localTimes = answer.take();
				break;
			} catch (InterruptedException e) {
				interrupted = true;
			}
		}
		
		if(interrupted){
			Thread.currentThread().interrupt();
		}
		
		List<String> result = new ArrayList<>();
		for(LocalTime lt : localTimes.getLocalTimeList()){
			result.add(
					new Formatter().format(
                            "%4d-%02d-%02d %02d:%02d:%02d %s",
                            lt.getYear(),
                            lt.getMonth(),
                            lt.getDayOfMonth(),
                            lt.getHour(),
                            lt.getMinute(),
                            lt.getSecond(),
                            lt.getDayOfWeek().name()).toString()
					);
		}
		
		
		return result;
		
	}
	
	
	
}
