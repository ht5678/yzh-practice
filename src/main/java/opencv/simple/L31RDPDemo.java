package opencv.simple;


/**
 * 
 * 轮廓周围绘制矩形框和圆形框
 * 
 * approxPolyDP(InputArray curve , OutputArray approxCurve , double epsilon , bool closed)
 * 
 * 基于RDP算法实现,目的是减少多边形轮廓点数
 * 
 * 轮廓周围绘制矩形:
 * boundingRect(InputArray points)得到的轮廓周围最小矩形左上交点坐标和右下角点坐标,
 * 绘制一个矩形
 * minAreaRect(InputArray points)得到一个旋转的矩形,返回旋转矩形
 * 
 * 轮廓周围绘制圆和椭圆
 * minEnclosing(
 * 	-InputArray points,	//得到最小区域圆形
 * -Point2f center,			//圆心位置
 * -float radius				//圆的半径
 * )
 * 
 * fitEllipse(
 * -InputArray	points,	//得到最小椭圆
 * )
 * 
 * 
 * 步骤:
 * *首先将图像变成二值图像
 * *发现轮廓,找到图像轮廓
 * *通过相关API在轮廓点上找到最小的包含矩形和椭圆,旋转矩形和椭圆
 * *绘制它们
 * 
 * 
 * 
 * @author sdwhy
 *
 */
public class L31RDPDemo {

}
