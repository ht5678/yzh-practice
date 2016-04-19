package jvm;


/**
 * 
 * #打印日志简单信息
 * -server -Xmx15m -Xms15m
	-verbose:gc
 * 
 * 其中verbose:gc和PrintGC的效果都是一样的
 * 
 * -------------------------------------------------------
 * 
 * #打印日志详细信息
 * 
 * -server -Xmx15m -Xms15m
	-XX:+PrintGCDetails
    -XX:+PrintGCTimeStamps
 *
 * ---------------------------------------------
 * 
 * 日志文件转储：
 * -Xloggc:e://gc.log
 * 
 * ---------------------------------------
 * 
 * 每次GC后，都打印堆信息
 * 
 * -XX:+PrintHeapAtGC
 * 
 * ------------------------------------------------------
 * 
 * 监控类的加载
 * -XX:+TraceClassLoading
 * 
 * 
 * @author sdwhy
 *
 */
public class TestVerboseGC {
	
	
	
	public static void main(String[] args) {
		byte[] bytes = new byte[10000000];
		for(int i = 0 ; i < bytes.length ;i++){
			bytes[i] = 3;
		}
		System.gc();
	}
	

}
