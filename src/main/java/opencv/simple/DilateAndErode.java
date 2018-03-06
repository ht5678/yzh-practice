package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 11 , 腐蚀和膨胀
 * 
 * 形态学有四个基本操作:腐蚀 , 膨胀 , 开 , 闭
 * 腐蚀和膨胀是图像处理中最常用的形态学操作手段
 * 
 * 可以用来消除小的噪声块 , 提取大对象
 * 
 * @author sdwhy
 *
 */
public class DilateAndErode {
	
	private static int elementSize =7;
	private static int maxSize=3;

	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    callbackDemo(src);
	    
	}
	
	
	/**
	 * 调整elementSize可以让效果更明显
	 * @param src
	 */
	private static void callbackDemo(Mat src){
		int s = elementSize*2+1;
		Mat dst = new Mat(src.size(), src.type());
		
		//参数:
		//形状(MORPH_RECT/MORPH_CROSS/MORPH_ELLIPSE)
		//大小 , 要是奇数
		//描点 , 默认是Point(-1,-1) , 意思是中心像素
		Mat structureElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(new Point(s,s)), new Point(-1 , -1));
		//膨胀
//		Imgproc.dilate(src, dst, structureElement , new Point(-1,-1),1);
//		Imgcodecs.imwrite("d://pics/dilate.jpg", dst);
		
		//腐蚀
		Imgproc.erode(src, dst, structureElement);
		Imgcodecs.imwrite("d://pics/erode.jpg", dst);
	}
	
	
	

}
