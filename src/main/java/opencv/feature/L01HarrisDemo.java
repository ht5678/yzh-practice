package opencv.feature;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 图像的特征:
 * 边缘
 * 角点
 * 纹理
 * 
 * 必须用灰度图像
 * cornorHarris(
 * InputArray src,
 * OutputArray dst,
 * int blockSize,
 * int ksize,
 * double k,
 * int borderType=BORDER_DEFAULT
 * )
 * 
 * 
 * blocksize计算λ1λ2时候的矩阵大小
 * ksize窗口大小
 * k表示计算角度响应时候的参数大小
 * 默认在0.04-0.06之间
 * -阈值t,用来过滤角度响应
 * 
 * 
 * @author sdwhy
 *
 */
public class L01HarrisDemo {

	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private JFrame frmjavaSwing;
	
	
	public L01HarrisDemo(){
		initialize();
	}
	
	
	/**
	 * 初始化
	 */
	private void initialize(){
		Mat source = Imgcodecs.imread("d://pics//171717.jpg");
		BufferedImage image = matToBufferedImage(source);
		frmjavaSwing = new JFrame();
		frmjavaSwing.setTitle("harris角点检测");
		frmjavaSwing.setBounds(100, 100, 687, 639);
		frmjavaSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmjavaSwing.getContentPane().setLayout(null);
		
		final JLabel showShapeValue = new JLabel("1");
		showShapeValue.setBounds(135, 21, 25, 15);
		frmjavaSwing.getContentPane().add(showShapeValue);
		
		final JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(15, 60, 558, 535);
		lblNewLabel.setIcon(new ImageIcon(image));
		frmjavaSwing.getContentPane().add(lblNewLabel);
		
		final JSlider slider_cth1 = new JSlider();
		slider_cth1.setValue(1);
		slider_cth1.setMinimum(1);
		slider_cth1.setMaximum(300);
		slider_cth1.setBounds(15, 21, 110, 25);
		frmjavaSwing.getContentPane().add(slider_cth1);
		
		JLabel lblBeta = new JLabel("Canny_threshold1");
		lblBeta.setBounds(15, 10, 110, 15);
		frmjavaSwing.getContentPane().add(lblBeta);
		
		slider_cth1.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				showShapeValue.setText(slider_cth1.getValue()+"");
				BufferedImage newImage = matToBufferedImage(findAndDrawPolygon(slider_cth1.getValue()));
				lblNewLabel.setIcon(new ImageIcon(newImage));
			}
		});
		
	}
	
	
	public Mat findAndDrawPolygon(double threshold1){
		Mat src = Imgcodecs.imread("d://pics//171717.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		Mat dst = new Mat(src.rows(),src.cols(),CvType.CV_32FC1);
		Mat normDst = new Mat(src.rows(),src.cols(),src.type());
		Mat scalarDst = new Mat(src.rows(),src.cols(),src.type());
		
//		Imgproc.cvtColor(src, dst, CvType.CV_32FC1);
		
		int blockSize=2;
		int ksize=3;
		double k = 0.04;
		
		Imgproc.cornerHarris(src, dst, blockSize, ksize, k, Core.BORDER_DEFAULT);
		Core.normalize(dst, normDst, 0, 255, Core.NORM_MINMAX,CvType.CV_32FC1,new Mat());
		Core.convertScaleAbs(normDst, scalarDst);
		
		Mat resultImg = src.clone();
		for(int row = 0 ; row <resultImg.rows();row++){
			for(int col = 0 ; col<resultImg.cols();col++){
				if(scalarDst.get(row, col)[0]>threshold1){
					Imgproc.circle(scalarDst, new Point(row,col), 6, new Scalar(0,0,255),1,8,0);
				}
			}
		}
		
		return scalarDst;
	}
	
	
	
	
	public BufferedImage matToBufferedImage(Mat matrix){
		int cols = matrix.cols();
		int rows = matrix.rows();
		int eleSize = (int)matrix.elemSize();
		
		byte[] data = new byte[cols*rows*eleSize];
		int type;
		
		matrix.get(0, 0,data);
		switch (matrix.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			//bgr to rgb
			byte b;
			for(int i =0 ; i<data.length;i=i+3){
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
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					L01HarrisDemo demo = new L01HarrisDemo();
					demo.frmjavaSwing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}


