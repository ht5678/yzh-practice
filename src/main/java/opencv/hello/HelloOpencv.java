package opencv.hello;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 
 * @author yuezh2   2018年1月26日 下午9:43:19
 *
 */
public class HelloOpencv {

	
	public static void main(String[] args) {
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat rgba = Imgcodecs.imread("d://pics/121212.jpg");
	    
//	    Imgcodecs.
	    
	}
	
	
}
