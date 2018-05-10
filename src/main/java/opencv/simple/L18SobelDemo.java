package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 18:sobel算子
 * 
 * 卷积应用-图像边缘提取
 * 
 * *边缘是什么 , 是像素值发生跃迁的地方,是图像的显著特征之一 , 在图像特征提取 , 
 * 		对象检测 , 模式识别等方面都有重要的作用	
 * *如何捕捉 / 提取 边缘 - 对图像求他的一阶导数
 * 		delta = f(x) - f(x-1) ,delta越大 , 说明像素在x方向变化越大 , 边缘信号越强
 * *我已经忘记啦 , 不要担心 , 用sobel算子就好 , 卷积操作
 * 
 * 
 * Sobel算子:
 * *是离散微积分算子 , 用来计算图像灰度的近似梯度 
 * *sobel算子功能集合高斯平滑和微分求导
 * *又被称为一阶微分算子 , 求导算子 , 在水平和垂直两个方向求导 , 
 * 得到图像X方向和Y方向梯度 图像
 * 
 * Sobel算子
 * 		-1,0,1
 * 		-2,0,2
 * 		-1,0,1
 * 
 * 		-1,-2,-1
 * 		0 , 0 , 0
 * 		1 , 2 , 1
 * 
 * 
 * G = 根号(G1+G2)
 * 近似值(节省性能):
 * G = |G1| + |G2|
 *
 * 求取导数的近似值 , kernel=3的时候不是很准确 , opencv使用改进版本Scharr函数 , 算子如下:
 * 
 * 		-3 , 0 , +3
 * 		-10, 0 , +10
 * 		-3 , 0 , +3
 * 
 * 
 * 		-3 , -10 , -3
 * 		0  ,   0  ,  0
 * 		+3 , +10 , +3
 * 
 * 
 * Sobel的API文档:
 * inputarray		src:输入图像
 *	outputarray	dst:输出图像 . 大小和输入图像一致
 *	int				 depth:输出图像深度
 *	int 			dx		 :x方向, 几阶导数
 * int				dy		 :y方向 , 几阶导数
 * int				ksize  :sobel算子的kernel大小 , 必须是1,3,5,7,9
 * double		scale=1
 * double		delta=0
 * int		  bordertype:BORDER_DEFAULT
 * 
 * 
 * input depth					output depth								(-1表示位图深度和src是一样的 , output的位图深度一定要>=src的为徒深度)
 * CV_8U						-1/CV_16S/CV_32F/CV_64F
 * CV_16U/CV_16S		-1/CV_32F/CV_64F
 * CV_32F						-1/CV_32F/CV_64F
 * CV_64F						-1/CV_64F
 * 
 * 
 * 过程:
 * 高斯平滑
 * 转灰度
 * 求梯度X和Y
 * 振幅图像
 * 
 * @author sdwhy
 *
 */
public class L18SobelDemo {
	
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/161616.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    //sobel算子对噪声敏感 , 容易受到影响 , 所以使用高斯模糊平滑降噪
	    Mat blur = new Mat(src.size(),src.type());
	    Mat gray = new Mat(src.size(),src.type());
	    Imgproc.GaussianBlur(src, blur, new Size(3, 3), 0,0);
	    
	    Imgproc.cvtColor(blur, gray, Imgproc.COLOR_BGR2GRAY);
	    Imgcodecs.imwrite("d://pics/sobel_gray.jpg", gray);
	    
	    Mat xgrad = new Mat(src.size(),src.type());
	    Mat ygrad = new Mat(src.size(),src.type());
	    
	    //Sobel算子
//	    Imgproc.Sobel(src, dst, ddepth, dx, dy, ksize, scale, delta);
//	    Imgproc.Sobel(gray, xgrad,CvType.CV_16S, 1, 0);
//	    Imgproc.Sobel(gray, ygrad,CvType.CV_16S, 0, 1);
	    
	    //sobel加强版 , 可以不害怕噪声影响
	    Imgproc.Scharr(gray, xgrad,CvType.CV_16S, 1, 0);
	    Imgproc.Scharr(gray, ygrad,CvType.CV_16S, 0, 1);
	    
	    //全变成正的
	    Core.convertScaleAbs(xgrad, xgrad);
	    Core.convertScaleAbs(ygrad, ygrad);
	    
	    Imgcodecs.imwrite("d://pics/sobel_xgray.jpg", xgrad);//x方向梯度
	    Imgcodecs.imwrite("d://pics/sobel_ygray.jpg", ygrad);//y方向梯度
	    
	    Mat xygrad = new Mat(src.size(),src.type());
	    Core.addWeighted(xgrad, 0.5, ygrad, 0.5, 0, xygrad);
	    Imgcodecs.imwrite("d://pics/sobel_xygray.jpg", xygrad);
	}
	
	

}
 