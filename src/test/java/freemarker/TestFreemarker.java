package freemarker;

import org.junit.Test;

/**
 * 
 * @author sdwhy
 *
 */
public class TestFreemarker {
	
	
	
	@Test
	public void testFreemarker(){
		System.out.println(FreemarkerUtil.getContent("/mail/spring", "report.ftl", null));
	}

}
