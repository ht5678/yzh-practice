package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
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
 * java:
 * 
 * private final static int MAX_UCHAR = 255;

	public static int saturateCastUchar(int x) {
	  return x > MAX_UCHAR ? MAX_UCHAR : (x < 0 ? 0 : x);
	}
	
	public static int saturateCastUchar(float x) {
	  return (int) (x > MAX_UCHAR ? MAX_UCHAR : (x < 0 ? 0 : x));
	}
 * 
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
 * 
 * 我们用这个函数实现一个平滑滤波的功能，我们只要把矩阵核写成下面这样：
 * int data[] = {  1, 1, 1, 1, 1, 1, 1, 1, 1 };
 * 
 * 
 * Core.getTickCount()
 * 
 * 
 * @author yuezh2   2018年1月29日 下午8:44:52
 *
 */
public class Filter2DDemo {

	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    int data[] = {  
	    		0, -1, 0, 
	    		-1, 5, -1, 
	    		0, -1, 0 };
		//allocate Mat before calling put
		Mat kernel = new Mat( 3, 3, CvType.CV_32S );
		kernel.put( 0, 0, data );
		

		long t = Core.getTickCount();
		//掩膜
		Mat dst = new Mat().zeros(src.size(), src.type());
		Imgproc.filter2D(src, dst, src.depth(), kernel);		//src.depth()表示位图深度 , 有32 , 24 ,8 等  . 默认-1 , 表示和原图深度一样 , 不一样可能会出错
		
		double time = (Core.getTickCount()-t)/Core.getTickFrequency();
		System.out.println(time);//秒
		
	    Imgcodecs.imwrite("d://pics/mask.jpg", dst);
	}
	
	
}
