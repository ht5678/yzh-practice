package freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 类FreemarkerUtil.java的实现描述：freemarker工具类
 * @author yuezhihua 2014年11月25日 下午5:22:21
 */
public class FreemarkerUtil {
    
    /** 日志 */
    private static final Logger logger = Logger.getLogger(FreemarkerUtil.class);
    private static Configuration cfg;
    
   static{
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/");
        cfg.setDefaultEncoding("utf-8");
    }
   
   
   /**
    * 将信息输出到流
    * @param name
    * @param root
    */
   public static String getContent(String name, Map<String,Object> root){
       return getContent("/", name, root);
   }

    /**
     * 将信息输出到流
     * @param name
     * @param root
     */
    public static String getContent(String packageName , String name, Map<String,Object> root){
        Template temp = FreemarkerUtil.getTemplate(packageName , name);
        try {
            temp.process(root, new PrintWriter(System.out));
            StringWriter sw = new StringWriter();
            temp.process(root, sw);
            String result = sw.toString();
            sw.close();
            return result;
        } catch (TemplateException e) {
            logger.error(String.format("when print template info occurs template exception,%s",e.getMessage()));
        } catch (IOException e) {
            logger.error(String.format("when print template info occurs io exception ,%s",e.getMessage()));
        }
        return "";
    }
    
    /**
     * 创建HTML文件
     * @param context
     * @param sh
     * @param ftlName
     * @param root
     * @param targetHtml
     */
    public static void createHtml(String rootPath,SimpleHash sh,String ftlName,String targetHtmlPath){
        try {
            //指定模版路径
            Template template = cfg.getTemplate(ftlName);
            //静态页面路径
            String htmlPath = rootPath+targetHtmlPath;
            File htmlFile = new File(htmlPath);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
            //处理模版  
            template.process(sh, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 创建HTML文件
     * @param context
     * @param sh
     * @param ftlName
     * @param root
     * @param targetHtml
     */
    public static void processFtl(String rootPath,SimpleHash sh,String ftlName){
        try {
            //指定模版路径
            Template template = cfg.getTemplate(ftlName);
            //静态页面路径
            String ftlPath = rootPath+ftlName;
            File ftlFile = new File(ftlPath);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ftlFile), "UTF-8"));
            //处理模版  
            template.process(sh, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取freemarker的模板
     * @return
     */
    public static Template getTemplate(String name){
        return getTemplate("/", name);
    }
    
    
    /**
     * 获取freemarker的模板
     * @return
     */
    public static Template getTemplate(String packageName , String name){
        try{
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(FreemarkerUtil.class, packageName);
            cfg.setDefaultEncoding("utf-8");
            Template temp = cfg.getTemplate(name);
            return temp;
        }catch(Exception e){
            logger.error(String.format("when get template occurs error ,%s",e.getMessage()));
        }
        return null;
    }
    
}

