package opencv.simple;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 *  #反向投影:
	#
	#*反向投影是反应直方图模型在目标图像中的分布情况
	#*简单点说就是用直方图模型去目标图像中寻找是否有相似的对象 . 通常用HSV色彩空间的HS两个通道直方图模型
	#
	#步骤:
	#*建立直方图模型
	#*计算机待测图像直方图并映射到模型中
	#*从模型反向计算生成图像
	#
	#
	#
	#相关API:
	#*加载图片imread
	#*将图像从RGB色彩空间转换到HSV色彩空间  cvtColor
	#*计算直方图和归一化 calcHist与normalize
	#*Mat和MatND其中Mat表示二维数组 , MatND表示三维或者多维数据 , 此处均可以用Mat表示
	#*计算反向投影图像  calcBackProject
 * 
 * 
 * 
 * @author yuezh2   2018年6月18日 下午4:34:48
 *
 */
public class L27BackProjectDemo {

	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/222222.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    Mat hsv = new Mat(src.size(),src.type());
	    Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);
	    
	    List<Mat> srcs = new ArrayList<>();
	    srcs.add(src);
	    List<Mat> hsvs = new ArrayList<>();
	    hsvs.add(hsv);
	    
	    Core.mixChannels(srcs, hsvs, new MatOfInt(0,0));
	   
	    int bins = 20;	//bins可以通过tracker滚动条动态修改看效果
	    Mat histBase = new Mat(hsv.size(),hsv.type());
	    Imgproc.calcHist(hsvs, new MatOfInt(0), new Mat(), histBase, new MatOfInt(bins), new MatOfFloat(0,180));
	    Mat hue = new Mat(src.size(),src.type());
	    Core.normalize(histBase, hue, 0, 256, Core.NORM_MINMAX,-1);
	    
	    Imgproc.calcBackProject(hsvs, new MatOfInt(0), hue, histBase, new MatOfFloat(0,180) , 1);
	    
	    Imgcodecs.imwrite("d://pics/backProject.jpg", histBase);
	    
	    int histH = 400;
	    int histW=400;
	    Mat histImage = new Mat(histW, histH, CvType.CV_8UC3, new Scalar(0,0,0));
	    int binW = histW/bins;
	    System.out.println(hue.size());
	    System.out.println(hue.channels());
	    for(int i = 1 ; i<bins;i++ ){
	    	Imgproc.rectangle(
	    			histImage, 
	    			new Point((i-1)*binW, (histH-Math.round(hue.get(i-1, 0)[0])*(400/255))), 
	    			new Point(i*binW, (histH-Math.round(hue.get(i, 0)[0])*(400/255))), 
	    			new Scalar(0, 0, 255),
	    			-1);
	    }
	    
	    Imgcodecs.imwrite("d://pics/backProjectHistImage.jpg", histImage);
	}
	
	
}
