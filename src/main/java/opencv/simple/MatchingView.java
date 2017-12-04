package opencv.simple;
import org.opencv.core.MatOfDMatch;  
import org.opencv.core.MatOfKeyPoint;  
  
  
public interface MatchingView {  
    public void setDstPic(String dstPath);  
    public void setSrcPic(String picPath);  
    public int showView(MatOfDMatch matches,MatOfKeyPoint srcKP,MatOfKeyPoint dstKP);  
}  