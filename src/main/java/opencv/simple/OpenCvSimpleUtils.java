package opencv.simple;


/**
 * 
 * @author yuezh2   2018年2月4日 下午4:28:35
 *
 */
public class OpenCvSimpleUtils {

	
	
	/**
	 * 
	 * @param num
	 * @return
	 */
	public static double saturateCast(double num){
		if(num>255){
			return 255;
		}
		if(num<0){
			return 0;
		}
		
		return num;
	}
	
	
	
}
