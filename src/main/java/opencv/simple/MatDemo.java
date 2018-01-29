package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 3 . 矩阵的掩膜操作
 * 
 * 函数:
 * 
 * 这个函数的功能是保证RGB值得范围在0-255之间
 * saturate_cast<uchar> (-100)  , 返回0
 * saturate_cast<uchar> (288)  , 返回255
 * saturate_cast<uchar> (100)  , 返回100
 * 
 * 
 * 
 * 掩膜操作实现图像的对比度调整
 * 
 * 
 * 
 * int row = 0, col = 0;
	int data[] = {  0, -1, 0, -1, 5, -1, 0, -1, 0 };
	//allocate Mat before calling put
	Mat img = new Mat( 3, 3, CvType.CV_32S );
	img.put( row, col, data );
 * 
 * @author yuezh2   2018年1月29日 下午8:44:52
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
	    
	    int data[] = {  0, -1, 0, -1, 5, -1, 0, -1, 0 };
		//allocate Mat before calling put
		Mat kernel = new Mat( 3, 3, CvType.CV_32S );
		kernel.put( 0, 0, data );
		
		//掩膜
		Mat dst = new Mat().zeros(src.size(), src.type());
		Imgproc.filter2D(src, dst, src.depth(), kernel);
	    
	    Imgcodecs.imwrite("d://pics/mask.jpg", dst);
	}
	
	
}
