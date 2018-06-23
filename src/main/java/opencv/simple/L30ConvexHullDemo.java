package opencv.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 凸包:
 * *什么事凸包,在一个多边形边缘或者内部任意两个点的连线都包含在多边形边界或者内部.
 * *正式定义:包含点集合S中所有点的最小凸多边形成为凸包
 * *检测算法:Graham扫描法
 * 
 * 概念介绍 - Graham扫描算法:
 * *首先选择Y方向最低的点作为起始点P0,
 * *从P0开始极坐标扫描,依次添加p1...pn(排序顺序是根据极坐标的角度大小,逆时针方向)
 * *对每个p[i]来说,如果添加p[i]点到凸包中导致一个左转向(逆时针方向)则添加该点到凸包,
 * 	反之如果导致一个右转向(顺时针方向)删除该点从凸包中
 * 
 * 
 * API说明:
 * convexHull(
 * InputArray points,//输入候选点,来自findContours
 * OutputArray hull,//凸包
 * bool clockwise,//default true , 顺时针方向
 * bool returnPoints,//true表示返回点个数,如果第二个参数是vector<Point>则自动忽略
 * )
 * 
 * 
 * 步骤:
 * *首先把图像从RGB转成灰度
 * *然后再转为二值图像
 * *再通过发现轮廓得到候选点
 * *凸包API调用
 * *绘制显示
 * 
 * 
 * 
 * @author sdwhy
 *
 */
public class L30ConvexHullDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/convex.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    Mat gray = new Mat(src.size(),src.type());
	    Mat binOut = new Mat(src.size(),src.type());
	    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
	    
	    Imgproc.blur(gray, gray, new Size(3,3), new Point(-1,-1), Core.BORDER_DEFAULT);
	    
	    
	    int thresholdValue = 115;
	    int thresholdValueMax = 255;
	    Imgproc.threshold(gray, binOut, thresholdValue, thresholdValueMax, Imgproc.THRESH_BINARY);
	    
	    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
	    Mat hierarchy =  new Mat();
	    Imgproc.findContours(binOut, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));
	    
	    List<MatOfInt> convexs = new ArrayList<>(contours.size());
	    for(int i =0 ; i < contours.size();i++){
	    	convexs.add(new MatOfInt());
	    	Imgproc.convexHull(contours.get(i), convexs.get(i), false);
	    }
	    
	    List<MatOfPoint> hulls = new ArrayList<>();
	    for(int i =0;i<convexs.size();i++){
	    	Point[] points = contours.get(i).toArray();
	    	MatOfInt convex = convexs.get(i);
	    	int[] indexs = convex.toArray();
	    	MatOfPoint mop = new MatOfPoint();
	    	
	    	List<Point> list = new ArrayList<>();
	    	for(int index : indexs){
	    		list.add(points[index]);
	    	}
	    	mop.fromList(list);
	    	hulls.add(mop);
	    }
	    
//	    for(int j=0; j < convexs.toList().size(); j++){
//	    	hulls.add(contours.get(k).toList().get(hullInt.toList().get(j)));
//	    }
	    
//	    System.out.println(hulls.size());
//	    System.out.println(contours.size());
//	    System.out.println(convex.elemSize());
//	    System.out.println(convex.get(3, 0)[0]);
	    //绘制
	    Mat dst = Mat.zeros(src.size(), CvType.CV_8UC3);
	    Random rd = new Random(255);
	    for(int i=0;i<contours.size();i++){
	    	Scalar color = new Scalar(rd.nextInt(),rd.nextInt(),rd.nextInt());
//	    	Imgproc.drawContours(dst, contours, i, color, 2,Imgproc.LINE_8,hierarchy,0,new Point(0,0));
	    	Imgproc.drawContours(dst, hulls, i, color, 2,Imgproc.LINE_8,new Mat(),0,new Point(0,0));
	    }
	    
	    Imgcodecs.imwrite("d://pics/convexHull.jpg", dst);
	}

}
