package bitcoin.p2p.gossip;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ExceptionsHelper;
import org.elasticsearch.Version;
import org.elasticsearch.common.io.stream.BytesStreamOutput;
import org.elasticsearch.common.io.stream.HandlesStreamOutput;
import org.elasticsearch.common.io.stream.StreamOutput;

/**
 * 
 * 
 *         ┏┓　　　┏┓
 *      ┏┛┻━━━┛┻┓
 *      ┃　　　　　　　┃ 　
 *      ┃　　　━　　　┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　　　┃
 *      ┃　　　┻　　　┃
 *      ┃　　　　　　　┃
 *      ┗━┓　　　┏━┛
 *         ┃　　　┃　　　　　　　　　
 *         ┃　　　┃
 *         ┃　　　┗━━━┓
 *         ┃　　　　　　　┣┓
 *         ┃　　　　　　　┏┛
 *         ┗┓┓┏━┳┓┏┛
 *　　      ┃┫┫　┃┫┫
 *　        ┗┻┛　┗┻┛
 *
 *-------------------- 神兽保佑 永无bug --------------------
 * 
 * 
 * 
 * @author yuezh2   2018年8月1日 下午3:07:40
 *
 */
public class MulticastZenPing {

	private static final byte[] INTERNAL_HEADER = new byte[]{1, 9, 8, 4};
	
    private final int port=54328;
    private final String group="224.2.2.4";
    private final int ttl=3;
	private final int bufferSize = 2048;//默认2048 , es从配置文件中读取配置
	
	private volatile Receiver receiver;
    private volatile Thread receiverThread;
	private volatile MulticastSocket multicastSocket;
	
	private final Object sendMutex = new Object();
    private final Object receiveMutex = new Object();
    
    
    private DatagramPacket datagramPacketSend;
    private DatagramPacket datagramPacketReceive;
    
    
    /**
     * 初始化
     */
    public MulticastZenPing(String address)throws Exception{
    	try {
            this.datagramPacketReceive = new DatagramPacket(new byte[bufferSize], bufferSize);
            this.datagramPacketSend = new DatagramPacket(new byte[bufferSize], bufferSize, InetAddress.getByName(group), port);
        } catch (Exception e) {
        	e.printStackTrace();
            return;
        }
    	
    	
        InetAddress multicastInterface = null;
        try {
            MulticastSocket multicastSocket;
            multicastSocket = new MulticastSocket(port);

            multicastSocket.setTimeToLive(ttl);

            // set the send interface
            multicastInterface = InetAddress.getByName(address);
            multicastSocket.setInterface(multicastInterface);
            multicastSocket.joinGroup(InetAddress.getByName(group));

            multicastSocket.setReceiveBufferSize(bufferSize);
            multicastSocket.setSendBufferSize(bufferSize);
            multicastSocket.setSoTimeout(60000);

            this.multicastSocket = multicastSocket;
            
            
            this.receiver = new Receiver();
            this.receiverThread = new Thread(receiver);
            this.receiverThread.start();
            
        }catch(Exception e){
        	e.printStackTrace();
        }
    	
    	
    }
	
	
    private void sendPingRequest(int id) {
        if (multicastSocket == null) {
            return;
        }
        synchronized (sendMutex) {
            try {
//                BytesStreamOutput bStream = new BytesStreamOutput();
//                StreamOutput out = new HandlesStreamOutput(bStream);
//                out.writeBytes(INTERNAL_HEADER);
//                Version.writeVersion(version, out);
//                out.writeInt(id);
//                clusterName.writeTo(out);
//                nodesProvider.nodes().localNode().writeTo(out);
//                out.close();
//            	String str = "this is a test message";
//            	byte[] bytes = str.getBytes();
//            	
//            	byte[] msgs = new byte[bytes.length+INTERNAL_HEADER.length];
//            	
//            	List<Byte> list = new ArrayList<>();
//            	list.addAll(bytes);
            	
                datagramPacketSend.setData(INTERNAL_HEADER);
                multicastSocket.send(datagramPacketSend);
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
    }
    
    
    
    private class Receiver implements Runnable {

        private volatile boolean running = true;

        public void stop() {
            running = false;
        }

        @Override
        public void run() {
        	
        }
    }
	
	
}
