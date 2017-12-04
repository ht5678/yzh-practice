package opencv.simple;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;  
  
public class GEivView implements MatchingView{  
//    UESI UES;  
//    Obj srcPicObj,dstPicObj;  
    public GEivView(){  
//        UES = new R();  
//          
//        srcPicObj = UES.creatObj(UESI.BGIndex);  
//        srcPicObj.addGLImage(0, 0,TextureController.SYSTEM_DEBUG_TEXTURE);  
//          
//        dstPicObj = UES.creatObj(UESI.BGIndex);  
//        dstPicObj.addGLImage(0, 0,TextureController.SYSTEM_DEBUG_TEXTURE);  
    }  
    @Override  
    public int showView(MatOfDMatch matches, MatOfKeyPoint srcKP,  
            MatOfKeyPoint dstKP) {  
        System.out.println(matches.rows() + " Match Point(s)");  
  
        double maxDist = Double.MIN_VALUE;  
        double minDist = Double.MAX_VALUE;  
          
        DMatch[] mats = matches.toArray();  
        for(int i = 0;i < mats.length;i++){  
            double dist = mats[i].distance;  
            if (dist < minDist) {  
                minDist = dist;  
            }  
            if (dist > maxDist) {  
                maxDist = dist;  
            }  
        }  
        System.out.println("Min Distance:" + minDist);  
        System.out.println("Max Distance:" + maxDist);  
        //将“好”的关键点记录，即距离小于3倍最小距离，可依据实际情况调整  
        List<DMatch> goodMatch = new LinkedList<>();  
          
        for (int i = 0; i < mats.length; i++) {  
            double dist = mats[i].distance;  
            if(dist < 3*minDist&&dist < 0.2f){  
                goodMatch.add(mats[i]);  
            }  
        }  
        System.out.println(goodMatch.size() + " GoodMatch Found");  
          
        DMatch[] goodmats = goodMatch.toArray(new DMatch[]{});  
        KeyPoint[] srcKPs = srcKP.toArray();//train  
        KeyPoint[] dstKPs = dstKP.toArray();//query  
          
        for(int i = 0;i < goodmats.length;i++){  
            Point crtD = dstKPs[goodmats[i].queryIdx].pt;  
            Point crtS = srcKPs[goodmats[i].trainIdx].pt;  
//            showMap(dstPicObj.getDx() + crtD.x,dstPicObj.getDy() + crtD.y,srcPicObj.getDx() + crtS.x,srcPicObj.getDy() + crtS.y);  
            System.out.println("MAP :("+(int)crtD.x+","+(int)crtD.y+") --->("+(int)crtS.x+","+(int)crtS.y+")");  
        }  
        return goodmats.length;  
    }  
    @Override  
    public void setDstPic(String dstPath) {  
//        dstPicObj.setPath(dstPath,true);  
//        dstPicObj.setPosition(CANExPos.POS_X_LEFT,50.0f);  
//        dstPicObj.setPosition(CANExPos.POS_Y_CENTER);  
//          
//        dstPicObj.show();  
    }  
    @Override  
    public void setSrcPic(String picPath) {  
//        srcPicObj.setPath(picPath,true);  
//        srcPicObj.setPosition(CANExPos.POS_X_RIGHT,50.0f);  
//        srcPicObj.setPosition(CANExPos.POS_Y_CENTER);  
//  
//        srcPicObj.show();  
    }  
    private void showMap(double x,double y,double x1,double y1){  
//        Color dstColor = RandomSet.getRandomColor();  
//          
//        Obj oval = UES.creatObj(UESI.UIIndex);  
//        oval.addGLOval("FFFFFF",0,0,5,5,12);  
//        oval.setColor(dstColor);  
//        oval.setCentralX((float)x1);  
//        oval.setCentralY((float)y1);  
//        oval.show();  
//          
//        oval = UES.creatObj(UESI.UIIndex);  
//        oval.addGLOval("FFFFFF",0,0,5,5,12);  
//        oval.setColor(dstColor);  
//        oval.setCentralX((float)x);  
//        oval.setCentralY((float)y);  
//        oval.show();  
//          
//        Obj line = UES.creatObj(UESI.UIIndex);  
//        line.addGLLine("FFFFFF",(float)x,(float)y,(float)x1,(float)y1);  
//        line.setLineWidth(2.0f);  
//        line.setColor(dstColor);  
//        line.setAlph(0.5f);  
//        line.show();  
    }  
}  