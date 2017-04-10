package springmvc.service;

import java.util.Map;

/**
 * 
 * @author yuezh2   2017年4月7日 下午3:45:52
 *
 */
public interface DemoService {

	public int insert(Map map);
	
	public int update(Map map);
	
	public int delete(Map map);
	
	public int query(Map map);
	
}
