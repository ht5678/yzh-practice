package opencv.simple;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author yuezh2   2018年6月25日 下午1:44:39
 *
 */
public class L01Slider {

	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					L01Slider window = new L01Slider();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public L01Slider() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JSlider slider = new JSlider();
		slider.setValue(1);
		slider.setMinimum(1);
		slider.setBounds(82, 10, 200, 25);
		frame.getContentPane().add(slider);
		
		JLabel lblNewLabel = new JLabel("Test");
		lblNewLabel.setBounds(10, 10, 46, 15);
		frame.getContentPane().add(lblNewLabel);
		
		final JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(304, 10, 46, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblNewLabel_1.setText(slider.getValue()+"");
			}
		});
	}
}

