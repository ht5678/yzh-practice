package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 
 * 第四课:mat对象
 * 
 * @author yuezh2   2018年1月29日 下午10:18:20
 *
 */
public class MatDemo {

	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/121212.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    Mat dst = new Mat(src.size() , src.type() , new Scalar(127 , 0 , 255));
//	    dst.setTo(scalar)
	    
	    Imgcodecs.imwrite("d://pics/mat.jpg", dst);
	}
	
	
}
