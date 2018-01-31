package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 
 * 第四课:mat对象
 * 
 * 
 * ptr:
 * 
 * 
 * // assume, that Mats with one channel
	byte[] sourceBuffer = new byte[srcMat.total()];
	byte[] destinationBuffer = new byte[dstMat.total()];
	srcMat.get(0, 0, sourceBuffer);
	int cols = srcMat.cols();
	int rows = srcMat.rows();
	for (int y = 0; y < rows; y++){
	  for (int x = 0; x < cols; x++){
	    for (int i = 0; i <= 2; i++){
	          destinationBuffer[y * cols + 3 * x + i] = sourceBuffer(vy * cols + 3 * vx + i) 
	    }
	  }
	}
	dstMat.put(0, 0, destinationBuffer);
	
	
	
 * 
 * 
 * 
 * CV_8UC3
 * 8表示每个通道占8位 , U表示无符号 , C表示char类型 , 3表示通道的数量是3
 * 
 * 
 * 
 * 
 * @author yuezh2   2018年1月29日 下午10:18:20
 *
 */
public class MatDemo {

	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/121212.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    //方法一
	    Mat dst = new Mat(src.size() , src.type() , new Scalar(127 , 0 , 255));
//	    dst.setTo(scalar)
	    
	    //方法二
//	    dst = src.clone();
	    
	    //方法三
//	    src.copyTo(dst);
	    
	    //看src有多少个通道
//	    src.channels();
	    
	    
//	    Mat m = new Mat(100, 100, CvType.CV_8UC3, new Scalar(0, 0 , 255));
	    Mat m = new Mat(100, 100, CvType.CV_8UC1, new Scalar(127));
	    /*
	     * eye方法的作用是对角线的地方都会变成1
	     * [1 , 0]
	     * [0 , 1]
	     */
//	    Mat.eye(size, type)
	    
	    Imgcodecs.imwrite("d://pics/mat.jpg", dst);
	    Imgcodecs.imwrite("d://pics/mat2.jpg", m);
	}
	
	
}
