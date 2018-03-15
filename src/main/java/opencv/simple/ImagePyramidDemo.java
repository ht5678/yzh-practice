package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 14:图像金字塔 - 图像上采样和降采样
 * 
 * 图像金字塔概念:
 * 高斯金字塔-用来对图像进行降采样
 * 拉普拉斯金字塔-用来重建一张图片根据他的上层降采样图片
 * 
 * 高斯金字塔:
 * *高斯金字塔从低向上 , 逐层降采样得到
 * *降采样之后图像大小是原图像M*N的M/2*N/2 , 就是对原图像删除偶数行与列 , 
 * 	 即得到降采样之后上一层图片
 * *高斯金字塔的生成分两步:
 * 对当前层进行高斯模糊
 * 删除当前层的偶数行与列
 * 即可得到上一层的图像, 这样上一层下一层对比 , 都只有他的1/4大小
 * 
 * 
 * 
 * 高斯不同(DOG)
 * *定义 , 就是把同一张图像在不同的参数下做高斯模糊之后 的结果相减 , 得到的输出图像 , 成为搞死不同
 * *高斯不同是图像的内在特征 , 在灰度图像增强 , 角点检测中经常用到 ,   非常重要 . 
 * 
 * 
 * 
 * @author sdwhy
 *
 */
public class ImagePyramidDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    //高斯金字塔
	    //上采样
	    Mat upDst = new Mat(src.size(), src.type());
	    Imgproc.pyrUp(src, upDst, new Size(src.cols()*2 , src.rows()*2));
	    Imgcodecs.imwrite("d://pics/pyrup.jpg", upDst);
	    //降采样
	    Mat downDst = new Mat(src.size(), src.type());
	    Imgproc.pyrDown(src, downDst, new Size(src.cols()/2 , src.rows()/2));
	    Imgcodecs.imwrite("d://pics/pyrDown.jpg", downDst);
	    
	    
	    //DOG
	    Mat gray_src = new Mat(src.size(), src.type());
	    Mat g1 = new Mat(src.size(), src.type());
	    Mat g2 = new Mat(src.size(), src.type());
	    Mat doImg = new Mat(src.size(), src.type());
	    Imgproc.cvtColor(src, gray_src, Imgproc.COLOR_BGR2GRAY);
	    
	    //ksize越大 , 差异化月明显
	    Imgproc.GaussianBlur(gray_src, g1, new Size(5,5), 0, 0);
	    Imgproc.GaussianBlur(g1, g2, new Size(5,5), 0);
	    
	    Core.subtract(g1, g2, doImg , new Mat());
	    
	    //归一化显示
	    Core.normalize(doImg, doImg, 255, 0, Core.NORM_MINMAX);
	    Imgcodecs.imwrite("d://pics/DOG.jpg", doImg);
	}

}
