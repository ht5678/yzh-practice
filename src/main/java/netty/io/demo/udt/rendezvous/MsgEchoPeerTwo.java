package netty.io.demo.udt.rendezvous;

import java.net.InetSocketAddress;

/**
 * 
 * UDT Message Flow Peer
 * <p>
 * Sends one message when a connection is open and echoes back any received data
 * to the other peer.
 * 
 * @author yuezh2   2016年11月21日 下午4:44:23
 *
 */
public class MsgEchoPeerTwo extends MsgEchoPeerBase{


	
	/**
	 * 		
	 * @param self
	 * @param peer
	 * @param messageSize
	 */
	protected MsgEchoPeerTwo(InetSocketAddress self, InetSocketAddress peer, int messageSize) {
		super(self, peer, messageSize);
	}
	
	
	
	
	public static void main(String[] args) throws Exception{
		final int messageSize = 64 * 1024;
		final InetSocketAddress self = new InetSocketAddress(Config.hostTwo, Config.portTwo);
		final InetSocketAddress peer = new InetSocketAddress(Config.hostOne, Config.portOne);
		
		new MsgEchoPeerTwo(self, peer, messageSize).run();
	}
	

}
