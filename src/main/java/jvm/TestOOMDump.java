package jvm;


/**
 * 
 * OOM的时候导出堆到文件：HeapDumpOnOutOfMemoryError
 * 导出OOM的路径：XX:HeapDumpPath
 * 
 * -server -Xmx10m -Xms10m
	-XX:+HeapDumpOnOutOfMemoryError
	-XX:HeapDumpPath=e:/test.dump
	
 * 
 * ------------------------------------------------------------------
 * 
 * 在OOM的时候，执行一个脚本
 * -server -Xmx10m -Xms10m
	-XX:OnOutOfMemoryError=e:/test.bat
 * 
 * @author sdwhy
 *
 */
public class TestOOMDump {
	
	
	public static void main(String[] args) {
		byte[] bytes = new byte[10000000];
		for(int i = 0 ; i < bytes.length ;i++){
			bytes[i] = 3;
		}
	}

}
