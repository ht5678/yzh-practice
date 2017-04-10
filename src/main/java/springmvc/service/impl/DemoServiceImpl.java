package springmvc.service.impl;

import java.util.Map;

import springmvc.annotation.Service;
import springmvc.service.DemoService;

/**
 * 
 * @author yuezh2   2017年4月7日 下午3:43:24
 *
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService{

	
	
	@Override
	public int insert(Map map) {
		System.out.println("调用了DemoServiceImpl服务类的insert方法");
		return 0;
	}

	@Override
	public int update(Map map) {
		System.out.println("调用了DemoServiceImpl服务类的update方法");
		return 0;
	}

	@Override
	public int delete(Map map) {
		System.out.println("调用了DemoServiceImpl服务类的delete方法");
		return 0;
	}

	@Override
	public int query(Map map) {
		System.out.println("调用了DemoServiceImpl服务类的query方法");
		return 0;
	}

	
	
}
