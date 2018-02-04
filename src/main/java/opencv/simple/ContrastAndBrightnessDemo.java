package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 第七课:调整图像亮度和对比度
 * 
 * 
 * 图像变换可以看做如下:
 * -像素变换 - 点操作
 * -邻域操作 - 区域
 * 
 * 调整图像亮度和对比度属于像素变换- 点操作
 * 
 * @author yuezh2   2018年2月4日 下午4:20:49
 *
 */
public class ContrastAndBrightnessDemo {

	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread("d://pics/121212.jpg");
		
		if(src.empty()){
			System.out.println("没有图片");
			return;
		}
		
		//contrast and brightness changes
		int height = src.rows();
		int width = src.cols();
		
		Mat dst = Mat.zeros(src.size(), src.type());
		
		float alpha = 1.2F;	//提高对比度
		float beta = 30;		//提高亮度
		
		//测试单通道1   1 , 2 , 3
		Mat gray = Mat.zeros(src.size(), src.type());
		Mat dst2 = Mat.zeros(src.size(), CvType.CV_8UC1);
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		
		for(int row = 0 ; row < height ; row++){
			for(int col =  0 ; col < width ; col++){
				double[] item =  src.get(row, col);
//				double[] item =  gray.get(row, col);//测试单通道2
				//三通道
				if(item.length == 3){
					double b = item[0];	//blue
					double g = item[1];//green
					double r = item[2];//red
					
					dst.put(row, col, OpenCvSimpleUtils.saturateCast((b*alpha+beta)) , OpenCvSimpleUtils.saturateCast((g*alpha+beta)) ,OpenCvSimpleUtils.saturateCast((r*alpha+beta)));
				}
				//单通道
				else if(item.length==1){
					double v = item[0];	
					dst2.put(row, col, OpenCvSimpleUtils.saturateCast((v*alpha+beta)));
				}else{
					//其他
					System.out.println(item.length);
					return;
				}
			}
		}
		
		
//		Imgcodecs.imwrite("d://pics/cb.jpg", dst);
		Imgcodecs.imwrite("d://pics/cb.jpg", dst2);//测试单通道3
		
	}
	
	
}
