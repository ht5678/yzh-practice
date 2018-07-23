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
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 
 * 自定义角点检测器
 * 
 * -基于harris和shi-tomasi角点检测
 * -首先通过计算矩阵M得到λ1λ2两个特征值根据他们得到角点响应值
 * -然后自己设置阈值实现计算出阈值得到有效响应值的角点位置
 * 
 * API:
 * 
 * harris
 * cornerEigenValsAndVecs(
 * -InputArray src,
 * -OutputArray dst,
 * -int blockSize,
 * -int ksize,
 * -int borderType=BORDER_DEFAULT
 * )
 * 
 * 
 * shi-tomasi
 * cornerMinEigenVal(
 * -InputArray src,
 * -OutputArray dst,
 * -int blockSize,
 * -int ksize=3,
 * -int borderType=BORDER_DEFAULT
 * )
 * 
 * 
 * @author sdwhy
 *
 */
public class L04CustomCornerDemo {



	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private JFrame frmjavaSwing;
	
	private static Mat src = null;
	
	private static MinMaxLocResult result = null;
	
	private static int maxCount = 300;
	
	private static Mat harrisRspImg = null;
	
	
	public L04CustomCornerDemo(){
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
		slider_cth1.setMaximum(maxCount);
		slider_cth1.setBounds(15, 21, 110, 25);
		frmjavaSwing.getContentPane().add(slider_cth1);
		
		JLabel lblBeta = new JLabel("Canny_threshold1");
		lblBeta.setBounds(15, 10, 110, 15);
		frmjavaSwing.getContentPane().add(lblBeta);
		
		slider_cth1.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				showShapeValue.setText(slider_cth1.getValue()+"");
				
				src = Imgcodecs.imread("d://pics//lena.png");
				
				Mat graySrc = new Mat(src.size(),src.type());
				Mat harrisDst = Mat.zeros(src.size(), src.type());
				harrisRspImg = Mat.zeros(src.size(), src.type());
				
				Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY);
				//计算特征值
				int blockSize=3;
				int ksize=3;
				double k = 0.04;
				
				Imgproc.cornerEigenValsAndVecs(graySrc, harrisDst, blockSize, ksize, Core.BORDER_DEFAULT);
				//计算响应
				for(int row=0;row<harrisDst.rows();row++){
					for(int col=0;col<harrisDst.cols();col++){
						double lambda1 = harrisDst.get(row, col)[0];
						double lambda2 = harrisDst.get(row, col)[1];
						
						harrisRspImg.put(row, col, (lambda1*lambda2 - k*Math.pow((lambda1+lambda2), 2)));
					}
				}
				
				result = Core.minMaxLoc(graySrc);
				
				Mat mat = CustomHarrisDemo(slider_cth1.getValue());
				BufferedImage newImage = matToBufferedImage(mat);
				
				
				lblNewLabel.setIcon(new ImageIcon(newImage));
			}
		});
		
	}
	
	
	public Mat CustomHarrisDemo(double qualityLevel){
		if(qualityLevel<10){
			qualityLevel=10;
		}
		
		Mat resultImg = src.clone();
		double t = result.minVal + ((qualityLevel/maxCount) * (result.maxVal-result.minVal));
		for(int row = 0 ; row < src.rows();row++){
			for(int col=0;col<src.cols();col++){
				double v = harrisRspImg.get(row, col)[0];
				if(v>t){
					Imgproc.circle(resultImg, new Point(col,row), 2, new Scalar(0,0,255), 2,8,0);
				}
			}
		}
		
		return resultImg;
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
					L04CustomCornerDemo demo = new L04CustomCornerDemo();
					demo.frmjavaSwing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}




