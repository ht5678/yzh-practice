package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 第五课:	图像操作
 * 
 * 
 * 
 * 
 * @author yuezh2   2018年1月30日 下午9:28:47
 *
 */
public class PixelDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/202020.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    //把图片置灰 , 三通道变成单通道
	    Mat gray = new Mat(src.size() , src.type());
	    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
	    
	    Imgcodecs.imwrite("d://pics/pixel.jpg", gray);
	    
	    
	    //像素颜色反转 , 结果类似于X片 , 或者胶卷里边的照片
	    int height = gray.rows();
	    int width = gray.cols();
	    
	    for(int row = 0 ; row < height ; row++){
	    	for(int col = 0 ; col<width ; col++){
	    		double[] element = gray.get(row, col);
	    		gray.put(row, col, 255 - element[0]);
	    	}
	    }
	    
	    
	    Imgcodecs.imwrite("d://pics/pixel2.jpg", gray);
	    
	    //三通道像素颜色反转
	    Mat p1 = new Mat(src.size() , src.type());
	    src.copyTo(p1);
	    
	    height = p1.rows();
	    width = p1.cols();
	    int channels = p1.channels();
	    
	    for(int row = 0 ; row < height ; row++){
	    	for(int col = 0 ; col<width ; col++){
	    		double[] element = p1.get(row, col);
	    		double[] ae = new double[channels];
	    		for(int c =0 ; c < channels ;c++){
	    			ae[c] = 255 - element[c];
	    		}
	    		p1.put(row, col, ae);
	    	}
	    }
	    Imgcodecs.imwrite("d://pics/pixel3.jpg", p1);
	    
	    
	    
	    //上面的行为可以用opencv的Core模块自带的方法实现
	    Mat p2 = new Mat(src.size() , src.type());
	    Core.bitwise_not(src, p2);
	    Imgcodecs.imwrite("d://pics/pixel4.jpg", p2);
	    
	}
	

}
