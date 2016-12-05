package openfire.smack.basic;

import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * 好友列表demo
 * @author yuezh2   2016年11月30日 上午11:27:25
 *
 */
public class RosterDemo {

	
	
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
					
					Roster roster = Roster.getInstanceFor(conn2);
					
					int count = roster.getEntryCount();
					System.out.println(count);
					
					
					List<Presence> presences = roster.getAllPresences("test.com");
					for(Presence p : presences){
						System.out.println(p.getFrom() + " : "+p.getTo()+" : "+p.getStatus());
					}
					
					
					Collection<RosterGroup> groups = roster.getGroups();
					for(RosterGroup group : groups){
						System.out.println(group.getName()+" : "+group.getEntryCount());
					}
					
					while(true){
						
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					conn2.disconnect();
				}
	}
	
	
	
}
