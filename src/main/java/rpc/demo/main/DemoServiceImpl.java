package rpc.demo.main;


/**
 * 
 * @author sdwhy
 *
 */
public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHi(String name) {
		return name+"你好";
	}

	
	
}
