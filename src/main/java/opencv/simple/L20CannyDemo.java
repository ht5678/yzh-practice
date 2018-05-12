package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * canny是边缘检测算法 ,在1986年提出 , 是一个很好的边缘检测器 , 很常用很常用很实用
 * canny算法介绍:
 * 1.高斯模糊 - gaussianblur
 * 2.灰度转换 - cvtColor
 * 3.计算梯度 - Sobel/Scharr
 * 4.非最大信号抑制
 * 5.高低阈值输出二值图像
 * 
 * API;
 * canny
 * -InputArray src , //8-bit的输入图像 , 灰度图像
 * -OuputArray edges , //输出边缘图像, 一般都是二值图像, 背景是黑色
 * -double threshold1 //低阈值 , 常取高阈值的1/2 , 1/3
 * -double threshold2 //高阈值
 * -int  aptertureSize , //sobel算子的size , 通常3X3 , 取值3
 * -bool L2gradient , 	//选择true表示是L2来归一化 , 否则用L1归一化 , 一般用L1 , 不用L2
 * 
 * 
 * 
 * @author sdwhy
 *
 */
public class L20CannyDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/161616.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    Mat gray = new Mat(src.size() ,src.type());
	    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGRA2GRAY);
	    
	    Mat edgeOut = new Mat(src.size(),src.type());
	    Imgproc.blur(gray, gray, new Size(3,3) ,new Point(-1, -1) , Core.BORDER_DEFAULT);
	    
	    //t1Value的值越大 , 保留的细节越少 , 受噪声影响越小
	    int t1Value = 100;
	    int t2Value=255;//用于有滑动条的时候 , 方便看效果
	    Imgproc.Canny(gray, edgeOut, t1Value, t1Value*2, 3, false);
	    
	    Mat dst = new Mat(src.size(),src.type());
	    src.copyTo(dst, edgeOut);//这个操作是 , 将计算出来的边缘点的像素赋值回去 , 变成彩色
	    Imgcodecs.imwrite("d://pics/canny.jpg", dst);
	    
	}

}
