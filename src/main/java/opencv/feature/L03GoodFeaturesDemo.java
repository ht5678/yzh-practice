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
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * goodFeaturesToTrack(
 * InputArray image,
 * OutputArray corners,	
 * int maxCorners,			表示返回角点的数目,如果检测角点数目大于最大数则返回响应值最强前规定数目
 * double qualityLevel,		表示最小可接受的向量值,1500 , 0.01 , 15 
 * double minDistance,	两个角点之间的最小距离(欧几里得距离)
 * InputArray mask = noArray,
 * int blockSize=3,			表示计算导数微分不同的窗口大小
 * bool useHarrisDetector=false,		是否使用harris角点检测
 * double k=0.4
 * )
 * 
 * @author sdwhy
 *
 */
public class L03GoodFeaturesDemo {


	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private JFrame frmjavaSwing;
	
	
	public L03GoodFeaturesDemo(){
		initialize();
	}
	
	
	/**
	 * 初始化
	 */
	private void initialize(){
		Mat source = Imgcodecs.imread("d://pics//lena.png");
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
	
	
	public Mat findAndDrawPolygon(int maxCorners){
		Mat src = Imgcodecs.imread("d://pics//lena.png");
		
		if(maxCorners<5){
			maxCorners=5;
		}
		
		MatOfPoint corners = new MatOfPoint();
		double qualityLevel = 0.01;
		double minDistance=10;
		int blockSize=3;
		boolean useHarris = false;
		double k =0.04;
		
		Mat graySrc = new Mat(src.size(),src.type());
		Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY);
		Imgproc.goodFeaturesToTrack(graySrc, corners, maxCorners, qualityLevel, minDistance, new Mat(), blockSize, useHarris, k);
		
		MatOfPoint2f pts = new MatOfPoint2f();
		corners.convertTo(pts, CvType.CV_32FC2);
		Point[] allPoints = pts.toArray();
		System.out.println(maxCorners);
		for(Point px : allPoints){
			Imgproc.circle(src, px, 6, new Scalar(0,0,255));
		}
		
		return src;
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
					L03GoodFeaturesDemo demo = new L03GoodFeaturesDemo();
					demo.frmjavaSwing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}



