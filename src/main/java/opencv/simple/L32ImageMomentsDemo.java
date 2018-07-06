package opencv.simple;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import scala.languageFeature.higherKinds;

/**
 * 图像矩
 * 
 *     图像识别的一个核心问题是图像的特征提取，简单描述即为用一组简单的数据（图像描述量）来描述整个图像，这组数据越简单越有代表性越好。
 * 良好的特征不受光线、噪点、几何形变的干扰。图像识别发展几十年，不断有新的特征提出，而图像不变矩就是其中一个。
 * 
 * 不变矩(Invariant Moments)是一处高度浓缩的图像特征，具有平移、灰度、尺度、旋转不变性。
 * M.K.Hu在1961年首先提出了不变矩的概念。1979年M.R.Teague根据正交多项式理论提出了Zernike矩。
 * 
 * 
 * 
 * API:
 * moments(
 * -InputArray array , //输入数据
 * -bool binaryImage=false		//是否为二值图像
 * )
 * 
 * contourArea(
 * -InputArray contour,//输入轮廓数据
 * -bool oriented 		//默认false,返回绝对值
 * )
 * 
 * 
 * srcLength(
 * -InputArray curve,//输入曲线数据
 * -bool	 closed	//是否是封闭曲线
 * )
 * 
 * 
 * 步骤:
 * *提取图像边缘
 * *发现轮廓
 * *计算每个轮廓对象的矩
 * *计算每个对象的中心, 弧长,面积
 * 
 * 
 * 
 * 
 * @author yuezh2   2018年7月6日 上午10:47:31
 *
 */
public class L32ImageMomentsDemo {
	
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	
	private JFrame frmRr;
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					L32ImageMomentsDemo demo = new L32ImageMomentsDemo();
					demo.frmRr.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	/**
	 * 构造函数
	 */
	public L32ImageMomentsDemo(){
		initialize();
	}
	
	
	/**
	 * initialize the contents of the frame
	 */
	private void initialize(){
		Mat source = Imgcodecs.imread("d://pics//rqq.jpg" , Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		BufferedImage image = matToBufferedImage(source);
		
		frmRr = new JFrame();
		frmRr.setTitle("空间矩 , 中心距 , 标准中心距和不变矩");
		frmRr.setBounds(100, 100, 637, 529);
		frmRr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRr.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("轮廓: ");
		lblNewLabel.setBounds(10, 10, 112, 15);
		frmRr.getContentPane().add(lblNewLabel);
		
		final JSlider slider_jpg = new JSlider();
		slider_jpg.setMaximum(255);
		
		slider_jpg.setValue(0);
		slider_jpg.setBounds(41, 10, 200, 25);
		frmRr.getContentPane().add(slider_jpg);
		
		final JLabel lblNewLabel_jpg = new JLabel("1");
		lblNewLabel_jpg.setBounds(317, 10, 46, 15);
		frmRr.getContentPane().add(lblNewLabel_jpg);
		
		final JLabel label_img = new JLabel("");
		label_img.setBounds(10, 277, 587, 130);
		frmRr.getContentPane().add(label_img);
		
		JLabel label1 = new JLabel("");
		label1.setBounds(10, 70, 587, 160);
		frmRr.getContentPane().add(label1);
		label1.setIcon(new ImageIcon(image));
		
		JLabel lblNewLabel_1 = new JLabel("原图:");
		lblNewLabel_1.setBounds(10, 45, 46, 15);
		frmRr.getContentPane().add(lblNewLabel_1);
		
		JLabel label = new JLabel("轮廓图:");
		label.setBounds(20, 240, 46, 15);
		frmRr.getContentPane().add(label);
		slider_jpg.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				lblNewLabel_jpg.setText((slider_jpg.getValue()+1)+"");
				System.out.println("选择第:"+(slider_jpg.getValue()+1)+"个轮廓");
				BufferedImage newImage = matToBufferedImage(demoMoments(slider_jpg.getValue()));
				label_img.setIcon(new ImageIcon(newImage));
			}
		});
		
		
	}
	
	
	
	public Mat demoMoments(int threshold){
		Mat source = Imgcodecs.imread("d://pics//rqq.jpg" , Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		Mat drawing = Mat.zeros(source.size(), source.type());
		List<MatOfPoint> srcContinours = new ArrayList<>();
		Mat srcHierarchy = new Mat(source.rows() , source.cols(),CvType.CV_8UC1,new Scalar(0));
		
		Imgproc.Canny(source, drawing, threshold, threshold*2,3,false);
		Imgproc.findContours(drawing, srcContinours, srcHierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE,new Point(0,0));
		
		Moments[] contoursMoments = new Moments[srcContinours.size()];
		Point[] ccs = new Point[srcContinours.size()];
		for(int i =0;i<srcContinours.size();i++){
			contoursMoments[i] = Imgproc.moments(srcContinours.get(i));
			ccs[i] = new Point( (contoursMoments[i].m10/contoursMoments[i].m00) , (contoursMoments[i].m01/contoursMoments[i].m00) );
		}
		
		
		Random rd = new Random();
		for(int i =0 ; i<srcContinours.size();i++){
			if(srcContinours.get(i).toList().size()<100){
				continue;
			}
			
			Scalar color = new Scalar(rd.nextInt(),rd.nextInt(),rd.nextInt());
			System.out.printf("center point x : %.2f y : %.2f\n",ccs[i].x , ccs[i].y);
			System.out.printf("contours %d area , %.2f  arc length :%.2f\n" , i , Imgproc.contourArea(srcContinours.get(i)) , Imgproc.arcLength(new MatOfPoint2f(srcContinours.get(i).toArray()), true));
			
			Imgproc.drawContours(drawing, srcContinours, i, color , 2,8,srcHierarchy,0,new Point(0, 0));
			Imgproc.circle(drawing, ccs[i], 2, color,2);
			
		}
		
		return drawing;
	}
	

	
	
	public BufferedImage matToBufferedImage(Mat matrix){
		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int)matrix.elemSize();
		byte[] data = new byte[cols*rows*elemSize];
		int type;
		matrix.get(0, 0, data);
		switch (matrix.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			//bgr to rgb
			byte b;
			for(int i=0;i<data.length;i=i+3){
				b = data[i];
				data[i]=data[i+2];
				data[i+2]=b;
			}
			break;
		default:
			return null;
		}
		
		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0, 0, cols, rows, data);
		return image2;
	}
	

}
