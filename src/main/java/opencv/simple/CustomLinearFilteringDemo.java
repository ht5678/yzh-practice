package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 16:自定义线性滤波
 * 
 * 卷积:
 * *卷积是图像处理中的一个操作,是kernel在图像上每个像素上的操作
 * *kernel本质上一个固定大小的矩阵数组,其中心点称为描点
 * 
 * 基础知识:
 * 卷积 , 傅里叶变换 , 卷积模糊,锐化,边缘检测,查找
 * 
 * 
 * 常见卷积算子:
 * Robert算子
 * 		+1,0			x方向梯度 , 就是0的方向 / 
 * 		0,-1
 * 
 * 		0,+1			y方向梯度 , 就是0的方向 \ 
 * 		-1,0
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
 * 拉普拉斯算子:
 * 		0,-1,0
 * 		-1,4,-1
 * 		0,-1,0
 * 
 * @author sdwhy
 *
 */
public class CustomLinearFilteringDemo {

	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/161616.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    
	    //robert x方向
	    Mat roberxDst = new Mat(src.size(),src.type());
	    int xdata[] = {  
	    		1, 0, 
	    		0, -1 };
	    Mat kernelX = new Mat( 2, 2, CvType.CV_32S );
	    kernelX.put( 0, 0, xdata );
	    //\new Point(-1,-1)表示自己寻找锚点
	    Imgproc.filter2D(src, roberxDst, -1, kernelX, new Point(-1,-1), 0.0);
	    Imgcodecs.imwrite("d://pics/robertx.jpg", roberxDst);
	    
	    
	  //robert y方向
	    Mat roberyDst = new Mat(src.size(),src.type());
	    int ydata[] = {  
	    		0, 1, 
	    		-1, 0 };
	    Mat kernelY = new Mat( 2, 2, CvType.CV_32S );
	    kernelX.put( 0, 0, ydata );
	    //\new Point(-1,-1)表示自己寻找锚点
	    Imgproc.filter2D(src, roberyDst, -1, kernelY, new Point(-1,-1), 0.0);
	    Imgcodecs.imwrite("d://pics/roberty.jpg", roberyDst);
	    
	  //sobel x方向
	    Mat sobelxDst = new Mat(src.size(),src.type());
	    int sobelxdata[] = {  
	    		-1, -2,1 ,
	    		0, 0 ,0,
	    		1,2,1
	    		};
	    Mat sobelxkernel = new Mat( 3, 3, CvType.CV_32S );
	    sobelxkernel.put( 0, 0, sobelxdata );
	    //\new Point(-1,-1)表示自己寻找锚点
	    Imgproc.filter2D(src, sobelxDst, -1, sobelxkernel, new Point(-1,-1), 0.0);
	    Imgcodecs.imwrite("d://pics/sobelx.jpg", sobelxDst);
	    
	    
	  //sobel y方向
	    Mat sobelyDst = new Mat(src.size(),src.type());
	    int sobelydata[] = {  
	    		0, -1, 0,
	    		-1, 4,-1,
	    		0,-1,0};
	    Mat sobelyKernel = new Mat( 3, 3, CvType.CV_32F );
	    sobelxkernel.put( 0, 0, sobelydata );
	    //\new Point(-1,-1)表示自己寻找锚点
	    Imgproc.filter2D(src, sobelyDst, -1, sobelyKernel, new Point(-1,-1), 0.0);
	    Imgcodecs.imwrite("d://pics/sobely.jpg", sobelyDst);
	
	    //拉普拉斯算子
	    Mat laDst = new Mat(src.size(),src.type());
	    int laData[] = {  
	    		0, -1, 0,
	    		-1, 4,-1,
	    		0,-1,0};
	    Mat laKernel = new Mat( 3, 3, CvType.CV_32S );
	    sobelxkernel.put( 0, 0, laData );
	    //\new Point(-1,-1)表示自己寻找锚点
	    Imgproc.filter2D(src, laDst, -1, laKernel, new Point(-1,-1), 0.0);
	    Imgcodecs.imwrite("d://pics/la.jpg", laDst);
	    
	    
	    //自定义卷积模糊
	    //src:输入图像
	    //dst:模糊图像
	    //depth:图像深度32/8
	    //kernel:卷积核/模板
	    //anchor:锚点位置
	    //delta:计算出来的像素+delta
//	    Imgproc.filter2D(src, laDst, -1, laKernel, new Point(-1,-1), 0.0);
	    int c=0;
	    int index=0;
	    int ksize=0;
	    Mat dst = new Mat(src.size() ,src.type());
	    for(int i =0;i<5;i++){
	    	ksize = 4+(i%5)*2+1;
	    	System.out.println(ksize);
	    	Mat kernel = Mat.ones(ksize, ksize, CvType.CV_32F);
//	    			/ (float)(ksize*ksize);
	    	Imgproc.filter2D(src, dst, -1, kernel,new Point(-1,-1),0.0);
	    
	    	Imgcodecs.imwrite("d://pics/cline"+i+".jpg", laDst);
	    }
	}
	
	
}

