package opencv.simple;

import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 17:处理边缘
 * 
 * 在卷积开始之前增加边缘像素 , 填充的像素值为0或者RGB黑色 , 比如3*3 在四周
 * 各填充1各像素的边缘 , 这样就确保图像的边缘被处理,在卷积处理之后再去掉这些
 * 边缘 ,opencv中默认的处理方法是:BORDER_DEFAULT(*** 最常用 ***) , 此外常用的还有:
 * BORDER_CONSTANT : 填充边缘用指定像素值
 * BORDER_REPLICATE: 填充边缘像素用已知的边缘像素值
 * BORDER_WRAP:用另外一边的像素来补偿填充
 * 
 * 
 * @author sdwhy
 *
 */
public class EdgeFillDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/161616.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    
	    int top = (int)(0.05*src.rows());
	    int bottom=(int)(0.05*src.rows());
	    int left = (int)(0.05*src.cols());
	    int right = (int)(0.05*src.cols());
	    
	    Mat dst = new Mat(src.size() , src.type());
	    Random ran = new Random(12345);
	    Scalar color = new Scalar(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
	    //说明:
	    //src:输入图像
	    //dst:输出图像
	    //top:边缘长度 , 一般上下左右都取想同值
	    //bottom
	    //left:
	    //right:
	    //bordertype:边缘类型 , color参数只会用BORDER_CONSTANT的时候生效
	    Core.copyMakeBorder(src, dst, top, bottom, left, right, Core.BORDER_DEFAULT , color);
	    Imgcodecs.imwrite("d://pics/BORDER_DEFAULT.jpg", dst);
	    
	    Core.copyMakeBorder(src, dst, top, bottom, left, right, Core.BORDER_CONSTANT , color);
	    Imgcodecs.imwrite("d://pics/BORDER_CONSTANT.jpg", dst);
	    
	    Core.copyMakeBorder(src, dst, top, bottom, left, right, Core.BORDER_REPLICATE, color);
	    Imgcodecs.imwrite("d://pics/BORDER_REPLICATE.jpg", dst);
	    
	    Core.copyMakeBorder(src, dst, top, bottom, left, right, Core.BORDER_WRAP, color);
	    Imgcodecs.imwrite("d://pics/BORDER_WRAP.jpg", dst);
	    
	    //高斯模糊也可以使用border类型
//	    Imgproc.GaussianBlur(src, dst, ksize, sigmaX, sigmaY, borderType);
	}
	

}
