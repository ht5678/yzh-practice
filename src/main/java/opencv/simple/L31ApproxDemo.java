package opencv.simple;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 轮廓周围绘制矩形框和圆形框
 * 
 * API:
 * 
 * 
 * 
 * @author yuezh2   2018年6月29日 下午5:12:51
 *
 */
public class L31ApproxDemo {
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private JFrame frmjavaSwing;
	
	
	public L31ApproxDemo(){
		initialize();
	}
	
	
	/**
	 * 初始化
	 */
	private void initialize(){
		Mat source = Imgcodecs.imread("d://pics//samples//palm_b.jpg");
		BufferedImage image = matToBufferedImage(source);
		frmjavaSwing = new JFrame();
		frmjavaSwing.setTitle("轮廓周围绘制矩形框和圆形框");
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
		Mat source = Imgcodecs.imread("d://pics//samples//palm_b.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		Mat destination = new Mat(source.rows(),source.cols(),source.type());
		Imgproc.GaussianBlur(source, source, new Size(3,3), 10,0);
		Imgproc.threshold(source, destination, threshold1, 255, 0);
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat(source.rows(),source.cols(),CvType.CV_8UC1,new Scalar(0));
		Imgproc.findContours(destination, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
		Mat drawing = Mat.zeros(destination.size(), CvType.CV_8UC3);
		for(int i =0 ; i < contours.size();i++){
			MatOfPoint2f mMOP2f1 = new MatOfPoint2f();
			MatOfPoint2f mMOP2f2 = new MatOfPoint2f();
			
			contours.get(i).convertTo(mMOP2f1, CvType.CV_32FC2);
			Imgproc.approxPolyDP(mMOP2f1, mMOP2f2, 8, true);
			
			mMOP2f2.convertTo(contours.get(i), CvType.CV_32S);
			Imgproc.drawContours(drawing, contours, i, new Scalar(255,0,0,255),2);
		}
		return drawing;
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
					L31ApproxDemo demo = new L31ApproxDemo();
					demo.frmjavaSwing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
