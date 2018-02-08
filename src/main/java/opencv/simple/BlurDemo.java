package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 第九课:模糊图像
 * 
 * new Size(11,11)表示正方形的大小
 * 
 * 均值滤波
 * 高斯模糊
 * 中值滤波
 * 双边滤波
 * 
 * 
 * ksize卷积和的大小要是奇数 , 1,3,5 , 偶数不好处理
 * 
 * @author sdwhy
 *
 */
public class BlurDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    //均值模糊
	    Mat dst = new Mat(src.size() , src.type());
	    Imgproc.blur(src, dst, new Size(11,11) , new Point(-1,-1));
	    Imgcodecs.imwrite("d://pics/blur.jpg", dst);
	    
	    //高斯模糊
	    Mat gDst = new Mat(src.size() , src.type());
	    Imgproc.GaussianBlur(src, gDst, new Size(11,11), 11,11);
	    Imgcodecs.imwrite("d://pics/gblur.jpg", dst);
	}
	

}
