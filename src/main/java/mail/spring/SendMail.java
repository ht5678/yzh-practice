package mail.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import freemarker.FreemarkerUtil;

/**
 * 
 * @author sdwhy
 *
 */
public class SendMail {
	
	
	public static void main(String[] args) throws Exception{
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
		  
        // 设定mail server  
        senderImpl.setHost("smtp.qq.com");  
        senderImpl.setPort(587);
  
        // 建立邮件消息,发送简单邮件和html邮件的区别  
        MimeMessage mailMessage = senderImpl.createMimeMessage();  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);  
  
        // 设置收件人，寄件人  
        messageHelper.setTo("yuezh2@lenovo.com");  
        messageHelper.setFrom("1040475249@qq.com");  
        messageHelper.setSubject("联想社区APP统计报告");  
        // true 表示启动HTML格式的邮件  
        Map model = new HashMap();
        model.put("user", "zhangsan");
        String text = FreemarkerUtil.getContent("/mail/spring", "report.ftl", model);
        messageHelper.setText(text, true);
  
        senderImpl.setUsername("1040475249@qq.com"); // 根据自己的情况,设置username  
        senderImpl.setPassword("qkjjykazlpiqbgaa"); // 根据自己的情况, 设置password  
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
        prop.put("mail.smtp.timeout", "25000");  
        senderImpl.setJavaMailProperties(prop);  
        // 发送邮件  
        senderImpl.send(mailMessage);  
  
        System.out.println("邮件发送成功.."); 
	}
	
	

}
