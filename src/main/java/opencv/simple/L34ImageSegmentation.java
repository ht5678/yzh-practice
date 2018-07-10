package opencv.simple;

import org.opencv.core.Core;

/**
 * 基于距离变换和分水岭的图像分割(image segmentation)
 * 
 * 什么事图像分割:
 * *图像分割是图像处理最重要的处理手段之一
 * *图像分割的目标是将图像中像素根据一定的规则分为若干N个cluster集合,每个集合包含一类像素
 * *根据算法分为监督学习方法和无监督学习方法,图像分割的算法多数都是无监督学习方法 - Kmeans
 * 
 * 
 * 距离变换和分水岭介绍:
 * *距离变换常见算法有两种
 * 		-不断膨胀/腐蚀得到
 * 		-基于倒角距离
 * 
 * *分水岭变换常见的算法
 * 		-基于浸泡理论实现
 * 
 * 
 * 
 * API:
 * 
 * 距离变换
 * *distanceTransform(
 * 		-InputArray src , 
 * 		-OutputArray dst,			输出8位或者32位的浮点数,单一通道,大小与输入图像一致
 * 		-OutputArray labels,		离散维诺图输出
 * 		-int distanceType,			DIST_L1/DIST_L2
 * 		-int maskSize,				3*3,最新的支持5*5,推荐3*3
 * 		-int labelType,				DIST_LABEL_CCOMP
 * )
 * 
 * 
 * 分水岭
 * watershed(
 * 		-InputArray image,
 * 		-InputOutputArray markers
 * )
 * 
 * 
 * 
 * 处理流程:
 * *将白色背景编程黑色-目的是为后面的变换做准备
 * *使用filter2D与拉普拉斯算子实现图像对比度提高,			sharp
 * *转为二值图像通过threshold
 * *距离变换
 * *对距离变换结果进行归一化到[0-1]之间
 * *使用阈值,再次二值化,得到标记
 * *赋值,得到每个Peak-erode
 * *发现轮廓-findContours
 * *绘制轮廓-drawContours
 * *分水岭变换watershed
 * *对每个分割区域着色输出结果
 * 
 * 
 * 
 * @author yuezh2   2018年7月10日 下午2:55:38
 *
 */
public class L34ImageSegmentation {
	
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	
	
	
	public static void main(String[] args) {
		
	}
	

}
