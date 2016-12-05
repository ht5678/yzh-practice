package openfire.smack.basic;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * 
 * @author yuezh2   2016年11月30日 下午3:46:10
 *
 */
public class FilterDemo {
	
	
	
	public static void main(String[] args) {
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				  .setUsernameAndPassword("yuezh2", "admin")
				  .setServiceName("test.com")
				  .setSecurityMode(SecurityMode.disabled)
//				  .setDebuggerEnabled(true)
				  .setHost("test.com")
				  .setPort(5222)
				  .build();

				//连接
				AbstractXMPPConnection conn2 = new XMPPTCPConnection(config);
				
				
				
				try{
					
					conn2.connect().login();
					
					// Create a packet filter to listen for new messages from a particular
					// user. We use an AndFilter to combine two other filters._
//					StanzaFilter filter = new AndFilter(new StanzaTypeFilter(Message.class),
//							new FromContainsFilter("mary@jivesoftware.com"));
//					// Assume we've created an XMPPConnection name "connection".
//
//					// First, register a packet collector using the filter we created.
//					PacketCollector myCollector = conn2.createPacketCollector(filter);
//					// Normally, you'd do something with the collector, like wait for new packets.
//
//					// Next, create a packet listener. We use an anonymous inner class for brevity.
//					PacketListener myListener = new PacketListener() {
//							public void processPacket(Packet packet) {
//								// Do something with the incoming packet here._
//							}
//						};
//					// Register the listener._
//					conn2.addPacketListener(myListener, filter);
					
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					conn2.disconnect();
				}
	}
	

}
