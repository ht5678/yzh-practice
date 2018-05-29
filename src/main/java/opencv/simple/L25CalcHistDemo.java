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
 * 直方图计算:
 * 
 * *直方图概念是基于图像像素值,其实对于图像梯度,每个像素的角度,
 * 等一切图像的属性值,我们都可以建立直方图. 这个才是直方图真正的意义,
 * 不过是基于图像像素灰度直方图是最常见的.
 * 
 * *直方图常见的几个属性.
 * -dims表示维度,对灰度图像来说只有一个通道,dims=1
 * -bins表示在维度中子区域大小划分,bins=256,划分256个级别
 * -range表示值得范围,灰度值范围是[0-255]之间
 * 
 * 
 * 把多通道图像分为多个单通道图像
 * split(
 * -Mat image, //输入图像指针
 * -int images, //图像数目
 * -int channels,//通道数
 * -InputArray mask,//输入mask,可选,不用
 * -OutputArray hist,//输出的直方图数据
 * -int dims,//维数
 * -int histSize,	//直方图级数
 * -float ranges,		//值域范围
 * -boolean uniform , 	//true by default
 * -boolean accumulate,	//false by default
 * )
 * 
 * 
 * 
 * @author sdwhy
 *
 */
public class L25CalcHistDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    //分通道显示
	    List<Mat> bgrPlanets = new ArrayList<>();
	    Core.split(src , bgrPlanets);
	    
	    List<Mat> bPlanets = new ArrayList<Mat>();
	    bPlanets.add(bgrPlanets.get(0));
	    
	    List<Mat> gPlanets = new ArrayList<Mat>();
	    gPlanets.add(bgrPlanets.get(1));
	    
	    List<Mat> rPlanets = new ArrayList<Mat>();
	    rPlanets.add(bgrPlanets.get(2));
	    
	    List<Mat> list = new ArrayList<Mat>();
	    list.add(src);
	    
	    int histSize = 256;
	    float[] range = {0,256};
	    float[][] histRanges = {range};
//
	    Mat bHist = new Mat();
		Mat gHist = new Mat();
		Mat rHist = new Mat();
//		
//		Mat mat = bPlanets.get(0);
//		for(int i = 0 ;i <mat.rows();i++){
//			for(int m=0;m<mat.cols();m++){
//				double[] items = mat.get(i, m);
//				System.out.println(items[0]);	
//				if(items[0]<0){
//					System.out.println();
//				}
//			}
//		}
		
		Imgproc.calcHist(bgrPlanets, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), new MatOfFloat(range), false);
		Imgproc.calcHist(bgrPlanets, new MatOfInt(1), new Mat(), gHist, new MatOfInt(histSize), new MatOfFloat(range), false);
		Imgproc.calcHist(bgrPlanets, new MatOfInt(2), new Mat(), rHist, new MatOfInt(histSize), new MatOfFloat(range), false);
		
		
		int histH = 400;
		int histW = 512;
		int binW = histW/histSize;
		Mat histImage = new Mat(histW, histH, CvType.CV_8UC3, new Scalar(0,0,0));
		
		Core.normalize(bHist, bHist, 0, histH, Core.NORM_MINMAX,-1 , new Mat());
		Core.normalize(gHist, gHist, 0, histH, Core.NORM_MINMAX,-1 , new Mat());
		Core.normalize(rHist, rHist, 0, histH, Core.NORM_MINMAX,-1 , new Mat());
		
		
		//render histogram chart
		for(int i =0;i<histSize;i++){
			Imgproc.line(histImage, new Point((i-1)*binW , histH-Math.round(bHist.get(i-1, 0)[0])), 
					new Point(i*binW , histH-Math.round(bHist.get(i, 0)[0])), new Scalar(255,0,0), 2, Core.LINE_AA, 0);
			
			Imgproc.line(histImage, new Point((i-1)*binW , histH-Math.round(gHist.get(i-1, 0)[0])), 
					new Point(i*binW , histH-Math.round(gHist.get(i, 0)[0])), new Scalar(0,255,0), 2, Core.LINE_AA, 0);
			
			Imgproc.line(histImage, new Point((i-1)*binW , histH-Math.round(rHist.get(i-1, 0)[0])), 
					new Point(i*binW , histH-Math.round(rHist.get(i, 0)[0])), new Scalar(0,0,255), 2, Core.LINE_AA, 0);
		}
		Imgcodecs.imwrite("d://pics/histImage.jpg", histImage);
	}

}
