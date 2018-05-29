package opencv.simple;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


/**
 * 
 * 直方图比较方法:
 * 对输入的两张图像计算得到的直方图H1和H2,归一化到相同的尺度空间,
 * 然后可以通过计算H1和H2之间的距离得到两个直方图的相似程度进而
 * 比较图像本身的相似程度.opencv提供的比较方法有4中,
 * -Correlation  相关性比较						***计算结果越大,相似度越高
 * -Chi-Square	卡方比较
 * -Intersection	 十字交叉性
 * -Bhattacharyya distance 巴氏距离		***计算结果越小,相似度越高  ,  效果比较好
 * 
 * 
 * 步骤:
 * *首先把图像从RGB色彩空间转换到HSV色彩空间cvtColor
 * *计算图像的直方图,然后归一化到[0~1]之间calcHist和normalize
 * *使用上述四种比较方法之一进行比较compareHist
 * 
 * 
 * compareHist:
 * -InputArray h1 , //直方图数据 , 下同
 * -InputArray h2,	//
 * -int method,		//比较方法,上述四种方法之一
 * 
 * 
 * @author sdwhy
 *
 */
public class L26CompareHistDemo {

	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/161616.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    Mat src2 = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2HSV);
	    Imgproc.cvtColor(src2, src2, Imgproc.COLOR_BGR2HSV);
	    
		int hBins = 50;
		int sBins = 60;
		
		int[] histSize = {hBins , sBins};
		float[] hRanges = {0,180};
		float[] sRanges = {0,256};
		float[][] ranges = {hRanges,sRanges};
		
		List<Mat> list1 = new ArrayList<>();
		list1.add(src);
		Imgproc.calcHist(list1, new MatOfInt(0), new Mat(), hist, histSize, ranges);
		
		
	}
	
	
}
