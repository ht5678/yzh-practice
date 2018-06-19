package opencv.simple;


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

}
