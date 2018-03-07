package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 12 : 开操作
 * 
 * *先腐蚀后膨胀
 * *可以去掉小的对象,  假设对象是前景色 , 背景是黑色
 * 
 * 闭操作:
 * *先膨胀后腐蚀
 * *可以填充小的洞(fill hole) , 假设对象是前景色 , 背景是黑色
 * 
 * 
 * 形态学梯度:
 * *膨胀减去腐蚀
 * *又称为基本梯度(其他还包括  内部梯度 , 方向梯度)
 * 
 * 
 * top hat :
 * 顶帽是原图像与操作之间的差值图像
 * 
 * 
 * 黑帽:
 * 黑冒是闭操作图像和原图像的差值图像
 * 
 * @author sdwhy
 *
 */
public class MorhOpenDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    Mat dst = new Mat(src.size(), src.type());
	    
	    //如果不能去掉小的对象 , 可以增加size的大小
	    Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(11, 11), new Point(-1,-1));
	    
	    //参数
	    //Mat src : 输入图像
	    //Mat dst : 输出图像
	    //int OPT  : CV_MOP_OPEN/CV_MOP_CLOSE/CV_MOP_GRADIENT/CV_MOP_TOPHAT/CV_MOP_BLACKHAT
	    //Mat kernel : 结构元素
	    //int Iteration : 迭代次数 , 默认是1
	    Imgproc.morphologyEx(src, dst, Imgproc.MORPH_OPEN, kernel);
	    Imgcodecs.imwrite("d://pics/morphopen.jpg", dst);
	    
	    //闭操作
	    Imgproc.morphologyEx(src, dst, Imgproc.MORPH_CLOSE, kernel);
	    Imgcodecs.imwrite("d://pics/morphclose.jpg", dst);
	    
	    //梯度
	    Imgproc.morphologyEx(src, dst, Imgproc.MORPH_GRADIENT, kernel);
	    Imgcodecs.imwrite("d://pics/morphgradient.jpg", dst);
	    
	    //顶帽
	    Imgproc.morphologyEx(src, dst, Imgproc.MORPH_TOPHAT, kernel);
	    Imgcodecs.imwrite("d://pics/morphtophat.jpg", dst);
	    
	    //黑帽
	    Imgproc.morphologyEx(src, dst, Imgproc.MORPH_BLACKHAT, kernel);
	    Imgcodecs.imwrite("d://pics/morphblackhat.jpg", dst);
	}
	
	

}
