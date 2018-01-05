package apache.math.kmeans;

import java.util.LinkedList;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;

/**
 * 单应性demo
 * @author yuezh2   2017年12月26日 下午2:04:23
 *
 */ 
public class HomographyDemo {

	
	public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // needed by OpenCV

        String rfileoutput = "/home/will/dev/Homog.jpg";
        String ofileoutput = "/home/will/dev/HomogOutput.jpg";

        Point SEShedCornerDst = new Point(49, 74);
        Point CloseForsythiaDst = new Point(41, 41);
        Point CornerHazelDst = new Point(111, 157);
        Point FarForsythiaDst = new Point(175, 21);
        Point FirstLiquidAmberDst = new Point(235, 164);
        Point SecondLiquidAmberDst = new Point(282, 721);
        Point ThirdLiquidAmberDst = new Point(317, 544);

        Point SEShedCornerSrc = new Point(30, 231);
        Point CloseForsythiaSrc = new Point(160, 290);
        Point CornerHazelSrc = new Point(50, 125);
        Point FarForsythiaSrc = new Point(628, 146);
        Point FirstLiquidAmberSrc = new Point(299, 64);
        Point SecondLiquidAmberSrc = new Point(146, 37);
        Point ThirdLiquidAmberSrc = new Point(48,34);

        Point [] srcArray = new Point[7];
        srcArray[0] = SEShedCornerSrc;
        srcArray[1] = CloseForsythiaSrc;
        srcArray[2] = CornerHazelSrc;
        srcArray[3] = FarForsythiaSrc;
        srcArray[4] = FirstLiquidAmberSrc;
        srcArray[5] = SecondLiquidAmberSrc;
        srcArray[6] = ThirdLiquidAmberSrc;

        Mat OutputMat = new Mat();
        LinkedList<Point> dstArray = new LinkedList<Point>();

        dstArray.add(SEShedCornerDst);
        dstArray.add(CloseForsythiaDst);        
        dstArray.add(CornerHazelDst);
        dstArray.add(FarForsythiaDst);
        dstArray.add(FirstLiquidAmberDst);
        dstArray.add(SecondLiquidAmberDst);
        dstArray.add(ThirdLiquidAmberDst);

        MatOfPoint2f dst = new MatOfPoint2f();
        dst.fromList(dstArray);

        MatOfPoint2f src = new MatOfPoint2f();
        src.fromArray(srcArray);

        Mat Homog;

//        Homog = Calib3d.findHomography(src, dst, Calib3d.RANSAC, 10, OutputMat);
        Homog = Calib3d.findHomography(src, dst, Calib3d.RANSAC, 10);

        System.out.println("Columns = " + Homog.cols());
        System.out.println("Rows = " + Homog.rows());
        System.out.println("Width = " + Homog.width());
        System.out.println("Dims = " + Homog.dims());

        for (int i=1; i<= Homog.cols();i++){
            for (int j=1; j<=Homog.rows();j++){
                System.out.println("Row, column " + i + "," + j + " = " + Homog.get(j, i));
            }
            System.out.println();
        }
        System.out.println(Homog.toString());
        System.out.println(OutputMat.toString());
//        Highgui.imwrite(rfileoutput, Homog);
//        Highgui.imwrite(ofileoutput, OutputMat);
    }
	
	
}
