package netty.io.demo.http.websocketx.test;

public class TestFrameRSV {

	
	
	public static void main(String[] args) throws Exception{
//		byte b = in.readByte();
		int frameRSV = (0 & 0x70) >> 4;
		System.out.println(frameRSV);
		byte[] bytes = "ff".getBytes();
		
		System.out.println(new String(new byte[]{-63, 4, 114, 115, 3, 0},"utf8"));
	}
	
	
}
