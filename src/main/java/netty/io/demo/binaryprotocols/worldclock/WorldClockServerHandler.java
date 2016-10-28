package netty.io.demo.binaryprotocols.worldclock;


import java.util.Calendar;
import java.util.TimeZone;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.Continent;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.DayOfWeek;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.LocalTime;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.LocalTimes;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.Location;
import netty.io.demo.binaryprotocols.worldclock.WorldClockProtocol.Locations;

/**
 * 
 * @author yuezh2   2016年10月27日 下午2:33:56
 *
 */
public class WorldClockServerHandler extends SimpleChannelInboundHandler<Locations>{

	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Locations locations) throws Exception {
		long currentTime = System.currentTimeMillis();
		
		LocalTimes.Builder builder = LocalTimes.newBuilder();
		for(Location l : locations.getLocationList()){
			TimeZone tz = TimeZone.getTimeZone(
					toString(l.getContinent()) + '/' + l.getCity());
			
			Calendar calendar = java.util.Calendar.getInstance(tz);
			calendar.setTimeInMillis(currentTime);
			
			builder.addLocalTime(LocalTime.newBuilder()
					  .setYear(calendar.get(Calendar.YEAR))
					  .setMonth(calendar.get(Calendar.MONTH) + 1)
					  .setDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH))
					  .setDayOfWeek(DayOfWeek.valueOf(calendar.get(Calendar.DAY_OF_WEEK)))
					  .setHour(calendar.get(Calendar.HOUR_OF_DAY))
					  .setMinute(calendar.get(Calendar.MINUTE))
					  .setSecond(calendar.get(Calendar.SECOND))
					  .build()
					  );
			
		}
		
		ctx.write(builder.build());
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

	
	
	private static String toString(Continent c){
		return c.name().charAt(0)+c.name().toLowerCase().substring(1);
	}
	
	
}
