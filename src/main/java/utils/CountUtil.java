package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;



/**
 * 
 * 类CountUtil.java的实现描述：统计工具
 * @author yuezhihua 2015年9月6日 下午3:37:00
 */
public class CountUtil {
    
    
    
    public static void main(String[] args) {
        BufferedReader br = null;
        try{
            File file = new File("e://tmp.log");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            HashSet<String> count = new HashSet<>(50 * 10000);
            List<String> list = new ArrayList<>(50 * 10000);
            HashSet<String> test = new HashSet<>(50 * 10000);
            while((line = br.readLine()) !=null){
                count.add(line);
                list.add(line);
            }
            System.out.println(count.size());
            System.out.println(list.size());
            test.addAll(list);
            System.out.println(test.size());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

}
