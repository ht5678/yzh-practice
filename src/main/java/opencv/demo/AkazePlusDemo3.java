package opencv.demo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * opencv2
 * @author yuezh2   2018年1月2日 下午4:51:44
 *
 */
public class AkazePlusDemo3 {
	
	
	public static void main(String[] args) {
		//加载本地的OpenCV库，这样就可以用它来调用Java API  
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		Mat img_object_src = Highgui.imread("d://888.jpg");
		Mat img_scene_src  = Highgui.imread("d://999.jpg");
		
		Mat img_object = new Mat(img_object_src.rows(),img_object_src.cols(),CvType.CV_8UC1);
		Mat img_scene = new Mat(img_scene_src.rows(),img_scene_src.cols(),CvType.CV_8UC1);
		
		Imgproc.cvtColor(img_object_src, img_object, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(img_scene_src, img_scene, Imgproc.COLOR_RGB2GRAY);
		
		//-- Step 1: Detect the keypoints using SURF Detector  
	    MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
	    MatOfKeyPoint keypoints_scene = new MatOfKeyPoint();
	    
	    FeatureDetector surfDetector =FeatureDetector.create(FeatureDetector.SURF);
	    surfDetector.detect(img_object, keypoints_object);
	    surfDetector.detect(img_scene, keypoints_scene);
	    
	    //-- Step 2: Calculate descriptors (feature vectors)
	    Mat descriptors_object = new Mat();
	    Mat descriptors_scene = new Mat();
	    DescriptorExtractor extractor=DescriptorExtractor.create(DescriptorExtractor.SURF);
	    
		extractor.compute(img_object, keypoints_object, descriptors_object);
		extractor.compute(img_scene, keypoints_scene, descriptors_scene);
	    
		
		//-- Step 3: Matching descriptor vectors using FLANN matcher
		DescriptorMatcher matcher=DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);  
		MatOfDMatch matches = new MatOfDMatch();
		matcher.match(descriptors_object, descriptors_scene, matches);
		 
		double max_dist = 0; double min_dist = 100;

		DMatch[] matchArr = matches.toArray();
		  //-- Quick calculation of max and min distances between keypoints
		  for( int i = 0; i < descriptors_object.rows(); i++ ){ 
			double dist = matchArr[i].distance;
		    if( dist < min_dist ) min_dist = dist;
		    if( dist > max_dist ) max_dist = dist;
		  }
		
		  System.out.println("min distance : "+min_dist);
		  System.out.println("max distance : "+max_dist);
		  
		  //-- Draw only "good" matches (i.e. whose distance is less than 3*min_dist )
		  MatOfDMatch good_matches = new MatOfDMatch();
		  List<DMatch> list = new ArrayList<>();
		  for( int i = 0; i < descriptors_object.rows(); i++ ){ 
			  if( matchArr[i].distance < 3*min_dist ){
				  list.add(matchArr[i]); 
			  }
		  }
		  
		  Mat img_matches = new Mat();
		  DMatch[] matchArr2 = new DMatch[list.size()];
		  good_matches.fromArray(list.toArray(matchArr2));
		  Features2d.drawMatches(img_object, keypoints_object, img_scene, keypoints_scene, good_matches, img_matches, 
				  Scalar.all(-1), Scalar.all(-1), new MatOfByte(), Features2d.NOT_DRAW_SINGLE_POINTS);
		  
		  
		  MatOfPoint2f obj = new MatOfPoint2f();
		  MatOfPoint2f scene = new MatOfPoint2f();
		  
		  Point[] objPoints = new Point[list.size()];
		  Point[] scenePoints = new Point[list.size()];
//		  MatOfDMatch obj = new MatOfDMatch();
//		  MatOfDMatch scene = new MatOfDMatch();
		  KeyPoint[] keypoints_objectArr = keypoints_object.toArray();
		  KeyPoint[] keypoints_sceneArr = keypoints_scene.toArray();
		  for( int i = 0; i < list.size(); i++ ){
		    //-- Get the keypoints from the good matches
			  objPoints[i] = keypoints_objectArr[ matchArr2[i].queryIdx ].pt;
			  scenePoints[i] = keypoints_sceneArr[ matchArr2[i].trainIdx ].pt;
		  }
		  
		  obj.fromArray(objPoints);
		  scene.fromArray(scenePoints);
//		  Calib3d.findHomography(obj, scene, 0, 3L);
		  Mat H = Calib3d.findHomography(obj, scene,Calib3d.RANSAC,5 );
		  
		  Mat tmp_corners = new Mat(4,1,CvType.CV_32FC2);
		  Mat scene_corners = new Mat(4,1,CvType.CV_32FC2);

		  //get corners from object
		  tmp_corners.put(0, 0, new double[] {0,0});
		  tmp_corners.put(1, 0, new double[] {img_scene.cols(),0});
		  tmp_corners.put(2, 0, new double[] {img_scene.cols(),img_scene.rows()});
		  tmp_corners.put(3, 0, new double[] {0,img_scene.rows()});

		  Core.perspectiveTransform(tmp_corners,scene_corners, H);

		  //output image
//		    Mat outputImg = new Mat();
//		    MatOfByte drawnMatches = new MatOfByte();
//		    Features2d.drawMatches(img1, keypoints1, img2, keypoints2, gm, outputImg, Scalar.all(-1), Scalar.all(-1), drawnMatches,Features2d.NOT_DRAW_SINGLE_POINTS);

		    //边界线
		    Core.line(img_matches, new Point(scene_corners.get(0,0)), new Point(scene_corners.get(1,0)), new Scalar(0, 255, 0),4);
		    Core.line(img_matches, new Point(scene_corners.get(1,0)), new Point(scene_corners.get(2,0)), new Scalar(0, 255, 0),4);
		    Core.line(img_matches, new Point(scene_corners.get(2,0)), new Point(scene_corners.get(3,0)), new Scalar(0, 255, 0),4);
		    Core.line(img_matches, new Point(scene_corners.get(3,0)), new Point(scene_corners.get(0,0)), new Scalar(0, 255, 0),4);
		  
		    Highgui.imwrite("d://compare.jpg", img_matches);
		  
	}

}
