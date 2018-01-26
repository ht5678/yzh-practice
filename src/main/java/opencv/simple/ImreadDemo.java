package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * imread:
 * CV_LOAD_IMAGE_UNCHANGED = -1,	表示加载原图,不做任何改变
    CV_LOAD_IMAGE_GRAYSCALE = 0,		表示把原图作为灰度图加载
    CV_LOAD_IMAGE_COLOR = 1,				表示把原图作为RGB图像加载
 * 
 * imwrite:
 * 保存图像到指定目录
 * 只有8位,16位的png,jpg,tiff文件格式而且是单通道或者三通道的BGR的图像才可以通过这种方式保存
 * 保存PNG格式的时候可以保存透明通道的图片
 * 可以指定压缩参数
 * 
 * opencv支持jpg , png , tiff等常见的格式图像文件加载
 * 
 * @author yuezh2   2018年1月26日 下午10:08:38
 *
 */
public class ImreadDemo {

	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat mat = Imgcodecs.imread("d://pics/121212.jpg" , Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    
	    Imgcodecs.imwrite("d://pics/gray.jpg", mat);
	    
	}
	
}
