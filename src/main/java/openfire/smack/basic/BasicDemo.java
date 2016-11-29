package openfire.smack.basic;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
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
			
			
			
			// Assume we've created an XMPPConnection name "connection"._
			ChatManager chatmanager = ChatManager.getInstanceFor(conn2);
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
			
			chatmanager.addChatListener(new ChatManagerListener() {
				
				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
					if (!createdLocally){
						chat.addMessageListener(new MyNewMessageListener());
					}
				}
			});
				
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
