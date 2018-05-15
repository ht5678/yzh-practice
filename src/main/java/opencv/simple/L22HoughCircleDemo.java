package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * *因为霍夫圆检测对噪声比较敏感 , 所以首先要对图像做中值滤波
 * *基于效率考虑 , opencv中实现的霍夫变换圆检测是基于图像梯度的实现 , 分为两步:
 * 		-检测边缘 , 可能发现圆心
 * 		-基于第一步的基础上从候选圆心开始计算最佳半径大小
 * 
 * 
 * API:
 * HoughCircles
 * 	-InputArray	image , //输入图像 , 必须是8位的单通道灰度图像
 * 	-OutputArray circles , //输出结果, 发现的圆信息
 * 	-int method,				//方法 - HOUGH-GRADIENT
 * 	-Double dp,				//dp=1
 * 	-Double mindist,			//10 , 最短距离 , 可以分辨是两个圆的 , 否则认为是同心圆
 * 	-Double	param1,			//canny edge detection high threshold
 * 	-Double param2,		//中心点累积器阈值,  - 候选圆心
 * 	-int	minradius,			//最小半径
 * 	-int 	maxradius,			//最大半径
 * 
 * 
 * 
 * @author sdwhy
 *
 */
public class L22HoughCircleDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/houghcircle.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    //中值滤波 , 去掉椒盐噪声效果好 ,, 否则可以尝试其他滤波
	    Mat moutput = new Mat(src.size(),src.type());
	    Imgproc.medianBlur(src, moutput, 3);
	    Imgproc.cvtColor(moutput, moutput, Imgproc.COLOR_BGR2GRAY);
	    
	    //霍夫圆检测
	    Mat circles = new Mat(src.size(),src.type());
	    Mat dst = new Mat(src.size(),src.type());
	    src.copyTo(dst);
	    Imgproc.HoughCircles(moutput, circles, Imgproc.CV_HOUGH_GRADIENT, 1, 10, 100, 30, 5, 50);
	    
	    int height = circles.rows();
	    int width = circles.cols();
	    for(int row = 0 ; row < height ; row++){
	    	for(int col = 0 ; col<width ; col++){
	    		double[] element = circles.get(row, col);
	    		Imgproc.circle(dst, new Point(element[0] , element[1]),  (int)element[2], new Scalar(0, 0, 255),2);
	    		Imgproc.circle(dst, new Point(element[0] , element[1]),  2, new Scalar(93, 23, 255),2);
	    	}
	    }
	    
	    
	    Imgcodecs.imwrite("d://pics/houghCircleResult.jpg", dst);
	    
	}
	

}
