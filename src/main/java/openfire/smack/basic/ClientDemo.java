package openfire.smack.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2   2016年12月6日 下午2:55:45
 *
 */
public class ClientDemo {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientDemo.class);
	
	
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
			//登录
			conn2.connect().login();
			
			ChatManager chatmanager = ChatManager.getInstanceFor(conn2);
			//接收消息
			chatmanager.addChatListener(new ChatManagerListener() {
				
				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
					if (!createdLocally){
						chat.addMessageListener(new MyNewMessageListener());
					}
				}
			});
			
			
			//发送消息
			Chat writeChat = chatmanager.createChat("test@test.com");
			
			
			
			
			while(true){
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				String line = br.readLine();
				if(line != null){
					try {
						writeChat.sendMessage(line);
					} catch (NotConnectedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}finally{
			conn2.disconnect();
		}
		
		
	}
	
	
	
}
