package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 第九课:模糊图像
 * 
 * new Size(11,11)表示正方形的大小
 * 
 * 均值滤波
 * 高斯模糊
 * 中值滤波
 * 双边滤波
 * 
 * 
 * ksize卷积和的大小必须是奇数 , 1,3,5 , 偶数不好找中心点 ，表示矩阵的大小
 * 
 * 应用场景:
 * 去除椒盐噪声 , 用来美容 , 去除白点黑点
 * 
 * @author sdwhy
 *
 */
public class BlurDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    //均值模糊
	    Mat dst = new Mat(src.size() , src.type());
	    Imgproc.blur(src, dst, new Size(11,11) , new Point(-1,-1));
	    Imgcodecs.imwrite("d://pics/blur.jpg", dst);
	    
	    //高斯模糊
	    Mat gDst = new Mat(src.size() , src.type());
	    Imgproc.GaussianBlur(src, gDst, new Size(11,11), 11,11);
	    Imgcodecs.imwrite("d://pics/gblur.jpg", gDst);
	    
	    //中值模糊
	    Mat mDst = new Mat(src.size() , src.type());
	    Imgproc.medianBlur(src, mDst, 3);
	    Imgcodecs.imwrite("d://pics/mblur.jpg", mDst);
	    
	    //双边模糊
	    //15 - d , 计算的半径,半径之内的像素都会被纳入计算 , 如果提供-1 , 则根据sigma space参数取值
	    //150 - sigma color  决定多少差值之内的像素会被计算
	    //3 -  sigma space  , 如果d的值大于0则声明无效 , 否则根据他计算d的值
	    Mat bDst = new Mat(src.size() , src.type());
	    Imgproc.bilateralFilter(src, bDst, 15, 150, 3);
	    Imgcodecs.imwrite("d://pics/bblur.jpg", bDst);
	    
	    //提升对比度
	    int data[] = {  0, -1, 0, -1, 5, -1, 0, -1, 0 };
	    Mat kernel = new Mat( 3, 3, CvType.CV_32S );
		kernel.put( 0, 0, data );
		
	    Mat filterDst = new Mat().zeros(src.size(), src.type());
		Imgproc.filter2D(bDst, filterDst, -1, kernel , new Point(-1,-1) , 0);		//src.depth()表示位图深度 , 有32 , 24 ,8 等  . 默认-1 , 表示和原图深度一样 , 不一样可能会出错
		Imgcodecs.imwrite("d://pics/filterBlur.jpg", filterDst);
	    
	    
	    
	}
	

}
