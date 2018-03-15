package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 13:形态学操作应用:提取水平与垂直线
 * 
 * 还有一个应用场景是 , 去除验证码的细线和点干扰
 * 
 * @author sdwhy
 *
 */
public class HLineAndVLine {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    Mat graydst = new Mat(src.size(), src.type());
	    Mat threDst = new Mat(src.size(), src.type());
	    Mat dst = new Mat(src.size(), src.type());
	    Mat hdst = new Mat(src.size(), src.type());
	    Mat hbwDst = new Mat(src.size(), src.type());
	    Mat vbwDst = new Mat(src.size(), src.type());
	    Mat hresult = new Mat(src.size(), src.type());
	    Mat vresult = new Mat(src.size(), src.type());
	    
	    //1.转成灰度图
	    Imgproc.cvtColor(src, graydst, Imgproc.COLOR_BGR2GRAY);
	    //2.转成二值图
	    Imgproc.adaptiveThreshold(graydst, threDst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, -2);
	    
	    
	    //水平结构元素
	    Mat hline = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(src.rows()/16, 1) , new Point(-1,-1));
	    //垂直结构元素
	    Mat vline = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, src.cols()/16) , new Point(-1,-1));
	    
	    //验证码 识别
//	    Mat ocr = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5) , new Point(-1,-1));
	    
	    //3.先腐蚀后膨胀 , 开操作
	    Imgproc.morphologyEx(threDst, dst, Imgproc.MORPH_OPEN, vline);
	    Imgproc.morphologyEx(threDst, hdst, Imgproc.MORPH_OPEN, hline);
	    //4.背景色取反
	    Core.bitwise_not(dst, vbwDst);
	    Core.bitwise_not(hdst, hbwDst);
	    //5.去除椒盐噪声 , 验证码识别的时候不用这个操作
	    Imgproc.blur(hbwDst, hresult, new Size(3,3));
	    Imgproc.blur(vbwDst, vresult, new Size(3,3));
	    //6.输出
	    Imgcodecs.imwrite("d://pics/vline.jpg", vresult);
	    Imgcodecs.imwrite("d://pics/hline.jpg", hresult);
	    
	    
	}

}
