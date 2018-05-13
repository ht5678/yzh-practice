package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 霍夫变换 - 直线
 * 
 * Hough Line Transform用来做直线检测
 * 前提条件 - 边缘检测已经完成
 * 平面空间到极坐标空间转换
 * 
 * 
 * 霍夫直线变换介绍
 * *对于任意一条直线上的所有点来说
 * *变换到极坐标中, 从[0-360]空间, 可以得到r的大小
 * *属于同一条直线点在极坐标空(r , theta)必然在一个点上有最强的信号出现,
 * 根据此反算到平面坐标中就可以得到直线上各点的像素坐标 , 从而得到直线
 * 
 * 
 * 相关API:
 * cv::HoughLines
 * -InputArray src , //输入图像 , 必须8-bit的灰度图像
 * -OutputArray lines, //输出的极坐标来表示直线
 * -double rho , 	//生成极坐标时候的像素扫描步长 , 一般取1
 * -double theta , //生成极坐标时的角度步长, 一般取值CV_P1/180
 * -int threshold , //阈值 , 只有获取足够交点的极坐标点才被看成是直线
 * -double srn=0,	//是否应用多尺度的霍夫变换, 如果不是设置0表示经典霍夫变换 , =0即可
 * -double stn=0,	//是否应用多尺度的霍夫变换, 如果不是设置0表示经典霍夫变换 , =0即可
 * -double min_theta = 0 , //表示角度扫描范围 0~180之间 , 默认即可
 * -double max_theta=CV_PI
 * //一般有经验的开发者使用这个API , 需要自己反变换到平面空间 , 一般不用这个API , 太麻烦了
 * 
 * 
 * 常用:
 * cv::HoughLineP
 * -InputArray src , //输入图像 ,必须8-bit的灰度图像
 * -OutputArray lines , //输出的极坐标表示直线
 * -double rho , //生成极坐标时候的像素扫描步长
 * -double theta , //生成极坐标时候的角度步长 , 一般取值 CV_PI/180
 * -int   threshold , //阈值 , 只有获得足够交点的极坐标点才能被看成直线
 * -double  minLineLength=0 , //最小直线长度
 * -double 	maxLineGap =0 , //最大间隔
 * 
 * 
 * @author sdwhy
 *
 */
public class L21HoughDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/line.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    Mat gray_src = new Mat(src.size() , src.type());
	    Mat dst = new Mat(src.size() , src.type());
	    //extract edge
	    Imgproc.Canny(src, gray_src, 100, 200);
	    Imgproc.cvtColor(gray_src, dst, Imgproc.COLOR_GRAY2BGR);
	    
	    Imgcodecs.imwrite("d://pics/houghLineGray.jpg", dst);
	    
	    Mat lines = new Mat();
	    Imgproc.HoughLinesP(gray_src, lines, 1D, Math.PI/180.0 , 10, 0, 10);
	    Scalar color = new Scalar(0,0,255);
	    int height = lines.rows();
	    int width = lines.cols();
	    
	    for(int row = 0 ; row < height ; row++){
	    	for(int col = 0 ; col<width ; col++){
	    		double[] element = lines.get(row, col);
	    		Imgproc.line(dst, new Point(element[0], element[1]), new Point(element[2], element[3]), color, 3 , Core.LINE_AA , 0);
	    	}
	    }
	    Imgcodecs.imwrite("d://pics/houghLineDst.jpg", dst);
	}
	
	

}
