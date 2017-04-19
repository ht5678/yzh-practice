package springmvc.template;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 
 * @author yuezh2   2017年4月19日 下午5:29:26
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/springmvc/template.xml"})
public class TransactionTemplateTest {

	
	@Autowired
	private TransactionTemplate template;
	
	@Autowired
	private DataSource datasource;
	
	
	@Test
	public void test1() throws Exception{
		final NamedParameterJdbcTemplate t = new NamedParameterJdbcTemplate(datasource);
		final String sql = "insert into test.test(uname,passwd) values (:uname,:passwd)";
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uname", "zhangsan");
		paramMap.put("passwd", "zhangsan");
		
		
		template.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				int result = t.update(sql, paramMap);
				status.setRollbackOnly();
				return result;
			}
			
		});
		
	}
	
	
	
}
