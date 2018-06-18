package opencv.simple;


/**
 * 
 * 模板匹配介绍:
 * *模板匹配就是在整个图像区域发现与给定子图像匹配的小块区域
 * *所以模板匹配首先需要一个模板图像T(给定的子图像)
 * *另外需要一个待检测的图像 - 图像S
 * *工作方法,在待检测图像上,从左到右,从上到下计算模板图像与重叠子图像的匹配度,
 * 	匹配程度越大,两者相同的可能性越大.
 * 
 * 
 * 匹配算法介绍:
 * opencv中提供了6中常见的匹配算法如下:
 * *计算平方不同			TIM_SQDIFF
 * *计算相关性				TIM_CCORR
 * *计算相关系数			TIM_CCOEFF
 * *计算归一化平方不同	TIM_SQDIFF_NORMED
 * *计算归一化相关性		TIM_CCORR_NORMED
 * *计算归一化相关系数	TIM_CCOEFF_NORMED
 * 
 * 
 * API:
 * matchTemplate(
 * InputArray image , //源图像 , 必须是8-bit或者32-bit浮点数图像
 * InputArray templ,	//模板图像,类型与输入图像一致
 * OutputArray result,//输出结果,必须是单通道32位浮点数,假设源图像WxH,模板图像wxh , 则结果必须为W-w+1,H-h+1的大小
 * int method,				//使用的匹配方法
 * InputArray mask=noArray()//(optional)
 * )
 * 
 * 
 * enums{
 * TIM_SQDIFF						0
 * TIM_CCORR							1
 * TIM_CCOEFF						2
 * TIM_SQDIFF_NORMED		3
 * TIM_CCORR_NORMED			4
 * TIM_CCOEFF_NORMED		5
 * }
 * 
 * 
 * @author yuezh2   2018年6月18日 下午8:18:36
 *
 */
public class L28TemplateMatchDemo {

}
