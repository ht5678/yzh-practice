package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 点多边形测试:
 * 
 * 概念介绍:
 * 测试一个点是否在给定的多边形内部,边缘或者外部
 * 
 * 
 * API:
 * pointPolygonTest(
 * InputArray contour,	//输入的轮廓
 * Point2f pt,		//测试点
 * bool measureDist		//是否返回距离值,如果是false,1表示在内面,0表示在边界上,-1表示在外部 , true返回实际距离
 * )
 * 
 * 返回数据是double类型
 * 
 * 
 * 步骤:
 * *构建一张400*400大小的图片, Mat::zero(400,400,CV_8uC1)
 * *画上一个六边形的闭合区域line
 * *发现轮廓
 * *对图像中所有像素点做点多边形测试,得到距离,归一化后显示
 * 
 * 
 * 
 * @author yuezh2   2018年7月6日 下午4:47:29
 *
 */
public class L33PointPolygonTest {
	
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		int r = 100;
	    Mat src = Mat.zeros(r*4, r*4 ,CvType.CV_8UC1);
	    Point[] vert = new Point[6];
	    
	    vert[0] = new Point(3 * r / 2,(int)(1.34*r));
	    vert[1] = new Point(1 * r, 2 * r);
	    vert[2] = new Point(3 * r / 2, (int)(2.866*r));
	    vert[3] = new Point(5 * r / 2, (int)(2.866*r));
	    vert[4] = new Point(3 * r, 2 * r);
	    vert[5] = new Point(5 * r / 2, (int)(1.34*r));
	    
	    for(int i = 0 ; i<6 ;i++){
	    	Imgproc.line(src, vert[i], vert[(i+1)%6], new Scalar(255),3,8,0);
	    }
	    
	    
	    Imgcodecs.imwrite("d://pics//pointPolygon.jpg", src);
	}
	
	

}
