package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 模板匹配介绍:
 * *模板匹配就是在整个图像区域发现与给定子图像匹配的小块区域
 * *所以模板匹配首先需要一个模板图像T(给定的子图像)
 * *另外需要一个待检测的图像 - 图像S
 * *工作方法,在待检测图像上,从左到右,从上到下计算模板图像与重叠子图像的匹配度,
 * 	匹配程度越大,两者相同的可能性越大.
 * 
 * 
 * 匹配算法介绍:
 * opencv中提供了6中常见的匹配算法如下:
 * *计算平方不同			TM_SQDIFF
 * *计算相关性				TM_CCORR
 * *计算相关系数			TM_CCOEFF
 * *计算归一化平方不同	TM_SQDIFF_NORMED
 * *计算归一化相关性		TM_CCORR_NORMED
 * *计算归一化相关系数	TM_CCOEFF_NORMED
 * 
 * 
 * API:
 * matchTemplate(
 * InputArray image , //源图像 , 必须是8-bit或者32-bit浮点数图像
 * InputArray templ,	//模板图像,类型与输入图像一致
 * OutputArray result,//输出结果,必须是单通道32位浮点数,假设源图像WxH,模板图像wxh , 则结果必须为W-w+1,H-h+1的大小
 * int method,				//使用的匹配方法
 * InputArray mask=noArray()//(optional)
 * )
 * 
 * 
 * enums{
 * TIM_SQDIFF						0
 * TIM_CCORR							1
 * TIM_CCOEFF						2			算法问题, 有时候会导致失真,匹配不到
 * TIM_SQDIFF_NORMED		3
 * TIM_CCORR_NORMED			4
 * TIM_CCOEFF_NORMED		5
 * }
 * 
 * 
 * TM_SQDIFF 平方差匹配法：该方法采用平方差来进行匹配；最好的匹配值为0；匹配越差，匹配值越大。 
	TM_CCORR 相关匹配法：该方法采用乘法操作；数值越大表明匹配程度越好。 
	TM_CCOEFF 相关系数匹配法：1表示完美的匹配；-1表示最差的匹配。 
	TM_SQDIFF_NORMED 归一化平方差匹配法。 
	TM_CCORR_NORMED 归一化相关匹配法。 
	TM_CCOEFF_NORMED 归一化相关系数匹配法。
 * 
 * 
 * @author yuezh2   2018年6月18日 下午8:18:36
 *
 */
public class L28TemplateMatchDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/words.jpg");
	    Mat temp = Imgcodecs.imread("d://pics/word3.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    int width = src.cols()-temp.cols()+1;
	    int height = src.rows()-temp.rows()+1;
	    Mat result = new Mat(width,height,CvType.CV_32FC1);
	    
//	    int matchMethod = Imgproc.TM_SQDIFF;
//	    int matchMethod = Imgproc.TM_SQDIFF_NORMED;
	    int matchMethod = Imgproc.TM_CCOEFF;
//	    int matchMethod = Imgproc.TM_CCOEFF_NORMED;
//	    int matchMethod = Imgproc.TM_CCORR;
//	    int matchMethod = Imgproc.TM_CCORR_NORMED;
	    
	    Imgproc.matchTemplate(src, temp, result, matchMethod , new Mat());
	    Core.normalize(result, result, 0, 1, Core.NORM_MINMAX,-1,new Mat());
	    
	    MinMaxLocResult locResult = Core.minMaxLoc(result);
	    Point maxLoc = locResult.maxLoc;
	    Point minLoc = locResult.minLoc;
	    Point tempLoc = null;
	    
	    if(matchMethod==Imgproc.TM_SQDIFF || matchMethod==Imgproc.TM_SQDIFF_NORMED){
	    	tempLoc = minLoc;
	    }else{
	    	tempLoc = maxLoc;
	    }
	    
	    //绘制矩形 , thickness表示线条宽度
	    Imgproc.rectangle(src, new Point(tempLoc.x,tempLoc.y) , new Point(tempLoc.x+temp.cols()  , tempLoc.y+temp.rows()) , new Scalar(0,0,255), 2, Core.LINE_8, 0);
	    Imgproc.rectangle(result, new Point(tempLoc.x,tempLoc.y) , new Point(tempLoc.x+temp.cols()  , tempLoc.y+temp.rows()) , new Scalar(0,0,255), 2, Core.LINE_8, 0);
	    
	    Imgcodecs.imwrite("d://pics/templateMatchSrc.jpg", src);
	    Imgcodecs.imwrite("d://pics/templateMatchResult.jpg", result);
	}
	

}
