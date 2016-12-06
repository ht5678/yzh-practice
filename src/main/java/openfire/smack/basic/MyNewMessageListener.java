package openfire.smack.basic;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.PacketExtension;

public class MyNewMessageListener implements ChatMessageListener {

	@Override
	public void processMessage(Chat chat, Message message) {
//		System.out.println("from:"+message.getFrom()+"  to:"+message.getTo()+"  body: " + message.getBody() + "  event:"+message.getType().name());
		String messageBody = message.getBody();
		ExtensionElement pe;  
	      
	    pe = message.getExtension("composing","http://jabber.org/protocol/chatstates");  
	    if(pe != null){  
	        System.out.println("对方正在输入......");  
	    }  
	      
	    pe = message.getExtension("active","http://jabber.org/protocol/chatstates");  
	    if(pe != null){  
	        System.out.println("接收到信息："+messageBody);  
	    }  
	  
	    pe = message.getExtension("paused","http://jabber.org/protocol/chatstates");  
	    if(pe != null){  
	        System.out.println("对方已暂停输入");  
	    }  
	  
	    pe = message.getExtension("inactive","http://jabber.org/protocol/chatstates");  
	    if(pe != null){  
	        System.out.println("对方聊天窗口失去焦点");  
	    }  
	  
	    pe = message.getExtension("gone","http://jabber.org/protocol/chatstates");  
	    if(pe != null){  
	        System.out.println("对方聊天窗口被关闭");  
	    }  
	}

}
