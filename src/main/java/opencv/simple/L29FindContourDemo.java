package opencv.simple;



/**
 * 轮廓发现:
 * 
 * *轮廓发现是基于图像边缘提取的基础寻找对象轮廓的方法
 * 	所以边缘提取的阈值决定回影响最终轮廓发现结果
 * *API介绍:
 * findContours发现轮廓
 * drawContours绘制轮廓
 * 
 * 在二值图像上发现轮廓使用API:
 * findContours(
 * InputOutputArray binImg , //输入图像,非0的像素被看成1,0的像素值保持不变,8bit
 * OutputArrayOfArrays contours,//全部发现的轮廓对象
 * OutputArray hierachy,//该图的拓扑结构,可选,该轮廓发现算法正式基于图像拓扑结构发现
 * int mode,//轮廓返回的模式
 * int method,.//发现方法
 * Point offset = Point() // 轮廓像素的位移,默认(0,0)没有位移
 * )
 * 
 * 
 * drawContours(
 * InputOutputArray binImg,//输出图像
 * OutputArrayOfArrays contours , //全部发现的轮廓对象
 * int contourIdx,//轮廓索引号
 * const Scalar&color,//绘制时候颜色
 * int thickness,//绘制线宽
 * int lineType,//线的类型LINE_8
 * InputArray hierachy,//拓扑结构图
 * int maxLevel,//最大层数, 0只绘制当前的,1表示绘制当前机器内嵌的轮廓
 * Point offset=Point()//轮廓位移,可选
 * )
 * 
 * 
 * 步骤:
 * *输入图像转为灰度图像,cvtColor
 * *使用Canny进行边缘提取,得到二值图像
 * *使用findContours寻找轮廓
 * *使用drawContours绘制轮廓
 * 
 * 
 * 
 * @author yuezh2   2018年6月19日 下午6:09:08
 *
 */
public class L29FindContourDemo {

}
