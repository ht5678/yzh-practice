package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 15:基本阈值操作
 * 
 * 图像阈值:
 * 图像阈值 , 简单点说就是吧图像分割的标尺 , 这个标尺是根据什么产生的 , 阈值产生算法 , 阈值类型
 * 
 * 
 * 阈值类型(只有灰度图像用):
 * *阈值二值化		-THRESH_BINARY			-0
 * *阈值反二值化	-THRESH_BINARY_INV	-1
 * *截断					-THRESH_TRUNC				-2
 * *阈值取零			-THRESH_TOZERO			-3
 * *阈值反取零		-THRESH_TOZERO_INV	-4
 * 
 * 获取阈值算法:
 * *THRESH_OSTU
 * *THRESH_TRAINGLE
 * @author sdwhy
 *
 */
public class ThresholdDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    //灰度图
	    Mat gray = new Mat(src.size(),src.type());
	    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
	    
	    
	    //**手动设置阈值
	    int thresholdValue = 127;
	    int thresholdMax = 255;
	    Mat dst = new Mat(src.size(),src.type());
	    
	    
//	    //THRESH_BINARY
//	    Imgproc.threshold(gray, dst, thresholdValue, thresholdMax, Imgproc.THRESH_BINARY);
//	    Imgcodecs.imwrite("d://pics/THRESH_BINARY.jpg", dst);
//	    //THRESH_BINARY_INV
//	    Imgproc.threshold(gray, dst, thresholdValue, thresholdMax, Imgproc.THRESH_BINARY_INV);
//	    Imgcodecs.imwrite("d://pics/THRESH_BINARY_INV.jpg", dst);
//	    //THRESH_TRUNC
//	    Imgproc.threshold(gray, dst, thresholdValue, thresholdMax, Imgproc.THRESH_TRUNC);
//	    Imgcodecs.imwrite("d://pics/THRESH_TRUNC.jpg", dst);
//	    //THRESH_TOZERO
//	    Imgproc.threshold(gray, dst, thresholdValue, thresholdMax, Imgproc.THRESH_TOZERO);
//	    Imgcodecs.imwrite("d://pics/THRESH_TOZERO.jpg", dst);
//	    //THRESH_TOZERO_INV
//	    Imgproc.threshold(gray, dst, thresholdValue, thresholdMax, Imgproc.THRESH_TOZERO_INV);
//	    Imgcodecs.imwrite("d://pics/THRESH_TOZERO_INV.jpg", dst);
	    
	    
	    //算法自动阈值THRESH_OTSU和THRESH_TRIANGLE
	    //THRESH_BINARY
	    Imgproc.threshold(gray, dst, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_BINARY);
	    Imgcodecs.imwrite("d://pics/THRESH_BINARY.jpg", dst);
	    //THRESH_BINARY_INV
	    Imgproc.threshold(gray, dst, 0, 255, Imgproc.THRESH_TRIANGLE | Imgproc.THRESH_BINARY_INV);
	    Imgcodecs.imwrite("d://pics/THRESH_BINARY_INV.jpg", dst);
	    //THRESH_TRUNC
	    Imgproc.threshold(gray, dst, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_TRUNC);
	    Imgcodecs.imwrite("d://pics/THRESH_TRUNC.jpg", dst);
	    //THRESH_TOZERO
	    Imgproc.threshold(gray, dst, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_TOZERO);
	    Imgcodecs.imwrite("d://pics/THRESH_TOZERO.jpg", dst);
	    //THRESH_TOZERO_INV
	    Imgproc.threshold(gray, dst, 0, 255, Imgproc.THRESH_OTSU | Imgproc.THRESH_TOZERO_INV);
	    Imgcodecs.imwrite("d://pics/THRESH_TOZERO_INV.jpg", dst);
	    
	}

}
