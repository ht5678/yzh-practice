package opencv.simple;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 第八课:绘制图形与文字
 * 
 * 绘制线  , 矩形  , 圆 , 椭圆 , 等基本几何形状
 * 
 * 画线:cv::line(LINE_4 \ LINE_8 \ LINE_AA)
 * 画椭圆:cv::eclipse
 * 画矩形:cv::rectangle
 * 画圆:cv::circle
 * 画填充:cv::fillPoly
 * 
 * @author yuezh2   2018年2月4日 下午5:58:47
 *
 */
public class ShapeDemo {
	
	
	
	private Mat bgImage ;
	
	
	public void execute(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		bgImage = Imgcodecs.imread("d://pics/151515.jpg");
		if(bgImage.empty()){
			System.out.println("文件为空");
			return;
		}
		
		myLines();
		myRectAngle();
		myEllipse();
		myCircle();
		myPolygon();
		
		Imgproc.putText(bgImage, "Hello OpenCV", new Point(300 ,300), Core.FONT_HERSHEY_COMPLEX, 1.0, new Scalar(12,23,200), 1 , Core.LINE_8, false);
		
		Imgcodecs.imwrite("d://pics/shape.jpg", bgImage);
	}
	
	
	/**
	 * 画一条线
	 */
	public void myLines(){
		Point p1 = new Point(20, 30);
		Point p2 = new Point();
		p2.x = 400;
		p2.y = 400;
		
		Scalar color = new Scalar(0,0,255);
		Imgproc.line(bgImage, p1, p2, color , 1 , Core.LINE_8,0);
	}
	
	
	/**
	 * 画一个正方形 , 长方形
	 */
	public void myRectAngle(){
//		Rect rect = new Rect(200, 100, 300, 300);
		Point p1 = new Point(200, 100);
		Point p2 = new Point();
		p2.x = 600;
		p2.y = 500;
		Scalar color = new Scalar(255,0,0);
		Imgproc.rectangle(bgImage, p1, p2, color, 2, Core.LINE_8, 1);
//		Imgproc.rectangle(bgImage, rect, color , 2 , Core.LINE_8 , 1);
	}
	
	
	/**
	 * 椭圆
	 */
	public void myEllipse(){
		Scalar color = new Scalar(0,255,0);
		Imgproc.ellipse(bgImage, new Point(bgImage.cols()/2 , bgImage.rows()/2), new Size(bgImage.cols()/4 , bgImage.rows()/8), 90, 0, 360, color, 1, Core.LINE_8, 0);
	}
	
	
	/**
	 * 圆
	 */
	public void myCircle(){
		Scalar color = new Scalar(0, 255 , 255);
		Point center = new Point(bgImage.cols()/2 , bgImage.rows()/2);
		Imgproc.circle(bgImage, center, 150, color , 2, Core.LINE_8 , 0);
	}
	
	
	
	/**
	 * 多边形
	 */
	public void myPolygon(){
		Point[] pts = new Point[5];
		pts[0] = new Point(100,100);
		pts[1] = new Point(100,200);
		pts[2] = new Point(200,200);
		pts[3] = new Point(200,100);
		pts[4] = new Point(100,100);
		
		MatOfPoint mop = new MatOfPoint();
		mop.fromArray(pts);
		
		List<MatOfPoint> list = new ArrayList<>();
		list.add(mop);
		
//		Point[][][] ppts = new Point[][][]{pts};
		int[] npt = {5};
		Scalar color = new Scalar(255,12,255);
		Imgproc.fillPoly(bgImage, list, color);
//		Imgproc.fillPoly(bgImage, list, color, Core.LINE_8, 0,new Point());
		
 	}
	

	
	public static void main(String[] args) {
		ShapeDemo demo = new ShapeDemo();
		demo.execute();
	}
	
	
}
