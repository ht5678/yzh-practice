package opencv.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 基于距离变换和分水岭的图像分割(image segmentation)
 * 
 * 什么事图像分割:
 * *图像分割是图像处理最重要的处理手段之一
 * *图像分割的目标是将图像中像素根据一定的规则分为若干N个cluster集合,每个集合包含一类像素
 * *根据算法分为监督学习方法和无监督学习方法,图像分割的算法多数都是无监督学习方法 - Kmeans
 * 
 * 
 * 距离变换和分水岭介绍:
 * *距离变换常见算法有两种
 * 		-不断膨胀/腐蚀得到
 * 		-基于倒角距离
 * 
 * *分水岭变换常见的算法
 * 		-基于浸泡理论实现
 * 
 * 
 * 
 * API:
 * 
 * 距离变换
 * *distanceTransform(
 * 		-InputArray src , 
 * 		-OutputArray dst,			输出8位或者32位的浮点数,单一通道,大小与输入图像一致
 * 		-OutputArray labels,		离散维诺图输出
 * 		-int distanceType,			DIST_L1/DIST_L2
 * 		-int maskSize,				3*3,最新的支持5*5,推荐3*3
 * 		-int labelType,				DIST_LABEL_CCOMP
 * )
 * 
 * 
 * 分水岭
 * watershed(
 * 		-InputArray image,
 * 		-InputOutputArray markers
 * )
 * 
 * 
 * 
 * 处理流程:
 * *将白色背景编程黑色-目的是为后面的变换做准备
 * *使用filter2D与拉普拉斯算子实现图像对比度提高,			sharp
 * *转为二值图像通过threshold
 * *距离变换
 * *对距离变换结果进行归一化到[0-1]之间
 * *使用阈值,再次二值化,得到标记
 * *赋值,得到每个Peak-erode
 * *发现轮廓-findContours
 * *绘制轮廓-drawContours
 * *分水岭变换watershed
 * *对每个分割区域着色输出结果
 * 
 * 
 * 
 * @author yuezh2   2018年7月10日 下午2:55:38
 *
 */
public class L34ImageSegmentation {
	
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/171717.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    //1.把白色背景改成黑色背景
	    for(int i =0 ; i < src.rows();i++){
	    	for(int j=0;j<src.cols();j++){
	    		double[] item = new double[]{255,255,255};
	    		if(Arrays.equals(src.get(i, j) , item)){
	    			item[0]=0;
	    			item[1]=0;
	    			item[2]=0;
	    			src.put(i, j, item);
	    		}
	    	}
	    }
	    System.out.println("black background");
	    
	    //sharpen锐化
		Mat kernel =new Mat(3,3, CvType.CV_32F);
		kernel.put(0, 0, new float[]{ 1, 1, 1});
		kernel.put(1, 0, new float[]{1, -8, 1});
		kernel.put(2, 0, new float[]{1, 1, 1});
	    
		Mat resultImg = new Mat(src.size(),src.type());
		Mat imgLaplance = new Mat(src.size(),src.type());
		Mat sharpenImg = new Mat(src.size(),src.type());
		src.copyTo(sharpenImg);
		Imgproc.filter2D(src, imgLaplance, CvType.CV_32F, kernel, new Point(-1,-1) , 0, Core.BORDER_DEFAULT);
		src.convertTo(sharpenImg, CvType.CV_32F);
		Core.subtract(sharpenImg, imgLaplance, resultImg);
		System.out.println("sharpen image");
		
		resultImg.convertTo(resultImg, CvType.CV_8UC3);
		imgLaplance.convertTo(imgLaplance, CvType.CV_8UC3);
		
		//convert to binary
		Imgproc.cvtColor(src, resultImg, Imgproc.COLOR_BGR2GRAY);
		Mat binaryImg = new Mat(src.size(),src.type());
		Imgproc.threshold(resultImg, binaryImg, 40, 255, Imgproc.THRESH_BINARY|Imgproc.THRESH_OTSU);
		System.out.println("binary image");
		
		Mat distImg = new Mat(src.size(),src.type());
		Imgproc.distanceTransform(binaryImg, distImg, Imgproc.DIST_L1 , 3 , 5);
		Core.normalize(distImg, distImg, 0, 1, Core.NORM_MINMAX);
		System.out.println("distance result");
		
		//binary again
		Imgproc.threshold(distImg, distImg, 0.4, 1, Imgproc.THRESH_BINARY);
		Mat k1 = Mat.zeros(13, 13, CvType.CV_8UC1);
//		Imgproc.erode(distImg, distImg, k1,new Point(-1,-1));
		Imgproc.erode(distImg, distImg, k1);
		System.out.println("distance binary image ");
		
		//markers
		Mat dist8u = new Mat(src.size(),src.type());
		distImg.convertTo(dist8u, CvType.CV_8U);
		Mat hierarchy = new Mat(dist8u.rows(),dist8u.cols(),CvType.CV_8UC1,new Scalar(0));
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
       
        //尋找外圍輪廓
        Imgproc.findContours(dist8u, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      
        Mat markers = Mat.zeros(src.size(), CvType.CV_32SC1);
        Random rd = new Random(255);
        for(int i =0;i<contours.size();i++){
	    	Scalar color = new Scalar(rd.nextInt(),rd.nextInt(),rd.nextInt());
	    	Imgproc.drawContours(markers, contours, i, color,-1);
	    }
        Imgproc.circle(markers, new Point(5,5), 3, new Scalar(255,255,255),-1);
        System.out.println("my markers");
        
        //perform watershed
        Imgproc.watershed(src, markers);
        Mat mark = Mat.zeros(markers.size(), CvType.CV_8UC1);
        markers.convertTo(mark, CvType.CV_8UC1);
        Core.bitwise_not(mark, mark,new Mat());
        System.out.println("watershed image");
        
        //generate random color
        Scalar[] colors = new Scalar[contours.size()];
        for(int i = 0 ; i < contours.size();i++){
        	Scalar color = new Scalar(rd.nextInt(),rd.nextInt(),rd.nextInt());
        	colors[i]=color;
        }
		
        //fill with color and display final result
        Mat dst = Mat.zeros(markers.size(), CvType.CV_8UC3);
        for (int row = 0; row < markers.rows(); row++) {
    		for (int col = 0; col < markers.cols(); col++) {
    			int index = (int)markers.get(row, col)[0];
    			if (index > 0 && index <= contours.size()) {
    				dst.put(row, col, colors[index - 1].val);
    			}
    			else {
    				dst.put(row, col, new double[]{0,0,0});
    			}
    		}
    	}
        
	    Imgcodecs.imwrite("d://pics/imageSegmentation.jpg", dst);
		
	}
	

}
