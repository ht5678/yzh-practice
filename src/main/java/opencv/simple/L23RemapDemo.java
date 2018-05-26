package opencv.simple;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 像素重映射
 * 
 * 简单点说就是把输入图像中各个像素按照一定的规则映射到
 * 另一张图像的对应位置上去 , 形成一张新的图像
 * 
 * API: remap
 * 
 * -InputArray src , //输入图像
 * -OutputArray dst,//输出图像
 * -InputArray map1,//x映射表CV_32FC1 /CV_32FC2(float , CV_32FC2 - double , CV_16FC2-int , CV_8FC2-uchar)
 * -InputArray map2,//y映射表
 * -int interpolation,//选择的插值方法 , 常见线性插值 , 可选择立方等
 * -int borderMode,//BORDER_CONSTANT
 * -const Scalar borderValue//color
 * 
 *  
 * 
 * 
 * @author sdwhy
 *
 */
public class L23RemapDemo {
	
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    Mat src = Imgcodecs.imread("d://pics/151515.jpg");
	    
	    if(src.empty()){
	    	System.out.println("图片地址不存在");
	    	return;
	    }
	    
	    Mat matX = new Mat(src.size() ,CvType.CV_32FC1);
	    Mat matY = new Mat(src.size() ,CvType.CV_32FC1);
	    
	    Mat dst = new Mat(src.size() ,src.type());
	    
	    updateMap(matX, matY, src, 0);
	    Imgproc.remap(src, dst, matX, matY, Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(255, 255, 255));
	    Imgcodecs.imwrite("d://pics/remapIndex0.jpg", dst);
	    
	    updateMap(matX, matY, src, 1);
	    Imgproc.remap(src, dst, matX, matY, Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(255, 255, 255));
	    Imgcodecs.imwrite("d://pics/remapIndex1.jpg", dst);
	    
	    updateMap(matX, matY, src, 2);
	    Imgproc.remap(src, dst, matX, matY, Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(255, 255, 255));
	    Imgcodecs.imwrite("d://pics/remapIndex2.jpg", dst);
	    
	    updateMap(matX, matY, src, 3);
	    Imgproc.remap(src, dst, matX, matY, Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(255, 255, 255));
	    Imgcodecs.imwrite("d://pics/remapIndex3.jpg", dst);
	    
	}
	
	/**
	 * 更新map
	 */
	private static void updateMap(Mat matX , Mat matY , Mat src , int index){
		for(int row =0;row<src.rows();row++){
			for(int col=0;col<src.cols();col++){
				switch (index) {
				case 0:
					if(col>(src.cols()*0.25) && col < (src.cols()*0.75) && row > (src.rows()*0.25) && row<(src.rows()*0.75)){
						matX.put(row, col,2 * (col - (src.cols()*0.25)+0.5));
						matY.put(row, col,2 * (row - (src.rows()*0.25)+0.5));
					}else{
						matX.put(row, col, 0);
						matY.put(row, col, 0);
					}
					break;
				case 1:
					matX.put(row, col, (src.cols()-col-1));
					matY.put(row, col, row);
					break;
				case 2:
					matX.put(row, col, col);
					matY.put(row, col, (src.rows()-row-1));
					break;
				case 3:
					matX.put(row, col, (src.cols()-col-1));
					matY.put(row, col, (src.rows()-row-1));
					break;
				}
			}
		}
	}

}
