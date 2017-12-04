package opencv.demo;
import org.opencv.core.*;  
import org.opencv.imgcodecs.Imgcodecs;  
import org.opencv.features2d.*;  
  
public class akaze {  
  
    public static void main(String[] args) {  
        try{  
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
              
            Mat src1=Imgcodecs.imread("f://999.jpg");  
            Mat src2=Imgcodecs.imread("f://888.jpg");  
            if(src1.empty()||src2.empty()){  
                throw new Exception("no file");  
            }  
              
            MatOfKeyPoint keypoint1=new MatOfKeyPoint();  
            MatOfKeyPoint keypoint2=new MatOfKeyPoint();  
            FeatureDetector siftDetector =FeatureDetector.create(FeatureDetector.AKAZE);  
              
            siftDetector.detect(src1,keypoint1);  
            siftDetector.detect(src2,keypoint2);  
              
            DescriptorExtractor extractor=DescriptorExtractor.create(DescriptorExtractor.AKAZE);  
              
            Mat descriptor1=new Mat(src1.rows(),src1.cols(),src1.type());  
            extractor.compute(src1, keypoint1, descriptor1);  
            Mat descriptor2=new Mat(src2.rows(),src2.cols(),src2.type());  
            extractor.compute(src2, keypoint2, descriptor2);  
              
            MatOfDMatch matches=new MatOfDMatch();  
            DescriptorMatcher matcher=DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);  
              
            matcher.match(descriptor1,descriptor2,matches);  
            
            double maxDist = Double.MIN_VALUE;  
            double minDist = Double.MAX_VALUE;  
              
            DMatch[] mats = matches.toArray();  
            for(int i = 0;i < mats.length;i++){  
                double dist = mats[i].distance;  
                if (dist < minDist) {  
                    minDist = dist;  
                }  
                if (dist > maxDist) {  
                    maxDist = dist;  
                }  
            }  
            System.out.println("Min Distance:" + minDist);  
            System.out.println("Max Distance:" + maxDist);  
              
            Mat dst=new Mat();  
            Features2d.drawMatches(src1, keypoint1, src2, keypoint2, matches, dst);  
              
            Imgcodecs.imwrite("f://akaze.jpg", dst);  
        }catch(Exception e){  
            System.out.println("例外:"+e);  
        }  
  
    }  
  
}  