package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 理论:
 * 		在二阶导数的时候 , 最大变化处的值为零即边缘是零值. 通过二阶导数计算 ,
 * 依据此理论我们可以计算图像二阶导数 , 提取边缘. 
 * 
 * 处理流程:
 * 	*高斯模糊 - 去噪声(GaussianBlur())
 * 	*转换为灰度图像	cvtColor
 * 	*拉普拉斯-二阶导数计算(Laplacian)
 * 	*取绝对值convertScaleAbs()
 * 	*显示结果
 * 
 * @author sdwhy
 *
 */
public class L19LaplanceDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/161616.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    Mat dst  = new Mat(src.size() , src.type());
	    Mat gray_src = new Mat(src.size() , src.type());
	    Mat edge_image = new Mat(src.size() , src.type());
	    
	    Imgproc.GaussianBlur(src, dst, new Size(3,3), 0, 0);
	    Imgproc.cvtColor(dst, gray_src, Imgproc.COLOR_BGR2GRAY);
	    
	    Imgproc.Laplacian(gray_src, edge_image, CvType.CV_16S);
	    Core.convertScaleAbs(edge_image, edge_image);
	    
	    Imgproc.threshold(edge_image, edge_image, 0, 255, Imgproc.THRESH_OTSU|Imgproc.THRESH_BINARY);
	    
	    Imgcodecs.imwrite("d://pics/L19laplance.jpg", edge_image);
	}
	
	

}
