package opencv.demo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * @author yuezh2   2018年1月8日 下午5:44:12
 *
 */
public class KMeansDemo {

	
	
	public static void main(String[] args) {
		
		//加载本地的OpenCV库，这样就可以用它来调用Java API  
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    
	    Mat rgba = Imgcodecs.imread("d://202020.jpg");
		
		Mat mHSV = new Mat(rgba.rows(),rgba.cols(),CvType.CV_8UC1);
		
		Imgproc.cvtColor(rgba, mHSV, Imgproc.COLOR_RGBA2RGB,3);
        Imgproc.cvtColor(rgba, mHSV, Imgproc.COLOR_RGB2HSV,3);
        List<Mat> hsv_planes = new ArrayList<Mat>(3);
        Core.split(mHSV, hsv_planes);


        Mat channel = hsv_planes.get(2);
        channel = Mat.zeros(mHSV.rows(),mHSV.cols(),CvType.CV_8UC1);
        hsv_planes.set(2,channel);
        Core.merge(hsv_planes,mHSV);



        Mat clusteredHSV = new Mat();
        mHSV.convertTo(mHSV, CvType.CV_32FC3);
        TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER,100,0.1);
        double result = Core.kmeans(mHSV, 2, clusteredHSV, criteria, 10, Core.KMEANS_PP_CENTERS);
        System.out.println(result);
        Imgcodecs.imwrite("d://kmeans.jpg", mHSV);
	}
	
	
}
