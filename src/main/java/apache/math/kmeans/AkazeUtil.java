package apache.math.kmeans;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 
 * @author yuezh2   2017年12月25日 下午4:09:47
 *
 */
public class AkazeUtil {
	
	
	/**
	 * 获取图片的点
	 * @param imagePath
	 * @return
	 */
	public static List<Location> getImageLocations(String imagePath1 , String imagePath2)throws Exception{
		List<Location> locations = new ArrayList<>();
		
		Mat src1=Imgcodecs.imread(imagePath1);
		Mat src2=Imgcodecs.imread(imagePath2);
		if(src1.empty()){  
            throw new Exception("no file");  
        }
		
		MatOfKeyPoint keypoint1=new MatOfKeyPoint();
		MatOfKeyPoint keypoint2=new MatOfKeyPoint();
		
		DescriptorExtractor extractor=DescriptorExtractor.create(DescriptorExtractor.AKAZE);  
        
        Mat descriptor1=new Mat(src1.rows(),src1.cols(),src1.type());  
        extractor.compute(src1, keypoint1, descriptor1);  
        Mat descriptor2=new Mat(src2.rows(),src2.cols(),src2.type());  
        extractor.compute(src2, keypoint2, descriptor2);  
          
        MatOfDMatch matches=new MatOfDMatch();  
        DescriptorMatcher matcher=DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);  
          
        matcher.match(descriptor1,descriptor2,matches);
        
        
        
        
		
		return locations;
	}
	
	

}
