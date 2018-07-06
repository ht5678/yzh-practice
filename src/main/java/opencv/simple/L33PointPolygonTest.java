package opencv.simple;



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
		
	}
	
	

}
