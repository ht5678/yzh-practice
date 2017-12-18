package opencv.simple;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;  
  
public class FeatureMatching {  
    private Mat src;  
    private MatOfKeyPoint srcKeyPoints;  
    private Mat srcDes;  
      
    private FeatureDetector detector;  
    private DescriptorExtractor extractor;  
    private DescriptorMatcher matcher;  
  
    private MatchingView view;  
    public FeatureMatching(MatchingView view) {  
        this.view = view;  
        srcKeyPoints = new MatOfKeyPoint();  
        srcDes = new Mat();  
        detector = FeatureDetector.create(FeatureDetector.AKAZE);  
        extractor = DescriptorExtractor.create(DescriptorExtractor.AKAZE);  
        matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);  
    }  
  
    public int doMaping(String dstPath) {  
        view.setDstPic(dstPath);  
        // 读入待测图像  
        Mat dst = Imgcodecs.imread(dstPath);  
        System.out.println("DST W:"+dst.cols()+" H:" + dst.rows());  
        // 待测图像的关键点  
        MatOfKeyPoint dstKeyPoints = new MatOfKeyPoint();  
        detector.detect(dst, dstKeyPoints);  
        // 待测图像的特征矩阵  
        Mat dstDes = new Mat();  
        extractor.compute(dst, dstKeyPoints, dstDes);  
        // 与原图匹配  
          
        MatOfDMatch matches = new MatOfDMatch();  
        matcher.match(dstDes, srcDes, matches);  
        //将结果输入到视图 并得到“匹配度”  
        return view.showView(matches, srcKeyPoints, dstKeyPoints);  
    }  
  
    public void setSource(String srcPath) {  
        view.setSrcPic(srcPath);  
        // 读取图像 写入矩阵  
        src = Imgcodecs.imread(srcPath);  
        System.out.println("SRC W:"+src.cols()+" H:" + src.rows());  
        // 检测关键点  
        detector.detect(src, srcKeyPoints);  
        // 根据源图像、关键点产生特征矩阵数值  
        extractor.compute(src, srcKeyPoints, srcDes);  
    }  
  
    public static void main(String[] args) {  
//        System.loadLibrary("opencv_java249");  
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	
        FeatureMatching mather = new FeatureMatching(new ConsoleView());  
        //FeatureMatching mather = new FeatureMatching(new GEivView());  
        mather.setSource("d://222.jpg");  
        mather.doMaping("d://333.jpg");  
    }  
}  