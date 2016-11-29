package openfire.smack.basic;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

public class MyNewMessageListener implements ChatMessageListener {

	@Override
	public void processMessage(Chat arg0, Message message) {
		System.out.println("Received message: " + message);
	}

}
