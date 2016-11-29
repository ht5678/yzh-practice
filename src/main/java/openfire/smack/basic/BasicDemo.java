package openfire.smack.basic;

import java.util.Collection;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * 
 * @author yuezh2   2016年11月28日 下午3:03:39
 *
 */
public class BasicDemo {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicDemo.class);
	
	
	public static void main(String[] args) {
		//测试的时候注意要修改host文件 ,  xxx.xxx.xx test.com
		
		// Create a connection to the jabber.org server.
//		AbstractXMPPConnection conn1 = new XMPPTCPConnection("username", "password","test.com");
//		conn1.connect();
		
		
		// Create a connection to the jabber.org server on a specific port.
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
		  .setUsernameAndPassword("yuezh2", "admin")
		  .setServiceName("test.com")
		  .setSecurityMode(SecurityMode.disabled)
//		  .setDebuggerEnabled(true)
		  .setHost("test.com")
		  .setPort(5222)
		  .build();

		//连接
		AbstractXMPPConnection conn2 = new XMPPTCPConnection(config);
		
		
		try{
			
			//连接
			conn2.connect();
			
			System.out.println(conn2.isConnected());
			
			//登录
			conn2.login();
			
			
			// 1 . 发送消息
			
			// Assume we've created an XMPPConnection name "connection"._
			ChatManager chatmanager = ChatManager.getInstanceFor(conn2);//取得聊天管理器
//			Chat newChat = chatmanager.createChat("test", new ChatMessageListener() {
//				
//				@Override
//				public void processMessage(Chat arg0, Message message) {
//					System.out.println("Received message: " + message);
//				}
//			});
	
	//		try {
//				newChat.sendMessage("Howdy!");
	//		}
	//		catch (XMPPException e) {
	//			System.out.println("Error Delivering block");
	//		}
			
			//test是用户名 , @是分隔符 , test.com是openfire的域名
			Chat writeChat = chatmanager.createChat("test@test.com");
			
			try {
				writeChat.sendMessage("Howdy!");
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			//--------------------------------------------------------------------------------
			// 2 . 接受消息
			chatmanager.addChatListener(new ChatManagerListener() {
				
				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
					if (!createdLocally){
						chat.addMessageListener(new MyNewMessageListener());
					}
				}
			});
			
			
			
			
			
			//3 . roster    发送状态相关
			// Create a new presence. Pass in false to indicate we're unavailable._
//			Presence presence = new Presence(Presence.Type.unavailable);
//			presence.setStatus("Gone fishing");
//			// Send the packet (assume we have an XMPPConnection instance called "con").
//			conn2.sendStanza(presence);
			
			
			
			//4 roster  获取所有好友的状态
			Roster roster = Roster.getInstanceFor(conn2);
			roster.addRosterListener(new RosterListener() {
				
				@Override
				public void presenceChanged(Presence presence) {
					// TODO Auto-generated method stub
					System.out.println(presence.getFrom() + " ,,  "+presence.getTo() + "  ,,  "+ presence.getStatus());
				}
				
				@Override
				public void entriesUpdated(Collection<String> addresses) {
					// TODO Auto-generated method stub
					for(String address : addresses){
						System.out.println(" update: "+address);
					}
				}
				
				@Override
				public void entriesDeleted(Collection<String> addresses) {
					// TODO Auto-generated method stub
					for(String address : addresses){
						System.out.println(" delete : "+address);
					}
				}
				
				@Override
				public void entriesAdded(Collection<String> addresses) {
					// TODO Auto-generated method stub
					for(String address : addresses){
						System.out.println("add : "+address);
					}
				}
			});
			Collection<RosterEntry> entries = roster.getEntries();
			for (RosterEntry entry : entries) {
				System.out.println(entry.getName());
//				System.out.println(entry.getStatus().toString());
			}
			
			
			while(true);
				
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			
		}finally{

			//关闭链接
			conn2.disconnect();
			
		}
		
	}
	
	
}
