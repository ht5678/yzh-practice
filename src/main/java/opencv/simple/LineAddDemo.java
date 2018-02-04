package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 6 : 线性融合
 * @author yuezh2   2018年1月31日 下午9:59:06
 *
 */
public class LineAddDemo {

	
	public static void main(String[] args) throws Exception{
		//加载本地的OpenCV库，这样就可以用它来调用Java API  
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		Mat src1 = Imgcodecs.imread("d://pics/1010.jpg");
		Mat src2  = Imgcodecs.imread("d://pics/1111.jpg");
		
		if(src1.empty() || src2.empty()){
			System.out.println("image is empty");
			return;
		}
		
		
		double alpha = 0.5;
		Mat dst = new Mat(src1.size() , src1.type());
		//线性融合要求size和type都是一样的 
		if(src1.rows()==src2.rows() && src1.cols()==src2.cols() && src1.type()==src2.type()){
			Core.addWeighted(src1, alpha, src2, (1-alpha), 0.0, dst);
//			Core.add(src1, src2 , dst,new Mat());
//			Core.multiply(src1, src2 , dst,1.0);
		}
		
		Imgcodecs.imwrite("d://pics/lineadd.jpg", dst);
	}
	
	
}
