package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 直方图
 * 
 * 什么是直方图:
 * 图像直方图 , 是指对整个图像在灰度范围内的像素值(0-255)统计出现频率次数,
 * 据此生成的直方图 , 成为图像直方图-直方图, 直方图反应了图像灰度的分布情况,
 * 是图像的统计学特征.
 * 
 * 直方图均衡化:
 * 是一种提高图像对比度的方法 ,拉伸图像灰度值范围
 * 
 * 
 * 直方图均衡化:
 * equalizeHist{
 * -InputArray src , //输入图像, 必须是8bit单通道图像
 * -OutputArray dst,//输出结果
 * }
 * 
 * 
 * @author sdwhy
 *
 */
public class L24EqualizeHistDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    Mat dst = new Mat(src.size(),src.type());
	    Mat gray = new Mat(src.size(),src.type());
	    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
	    //直方图均衡化
	    Imgproc.equalizeHist(gray, dst);
	    
	    Imgcodecs.imwrite("d://pics/equalizeHist.jpg", dst);
	    
	}
	

}
