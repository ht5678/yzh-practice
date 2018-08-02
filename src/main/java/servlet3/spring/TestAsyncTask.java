package servlet3.spring;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 
 * 
 *         ┏┓　　　┏┓
 *      ┏┛┻━━━┛┻┓
 *      ┃　　　　　　　┃ 　
 *      ┃　　　━　　　┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　　　┃
 *      ┃　　　┻　　　┃
 *      ┃　　　　　　　┃
 *      ┗━┓　　　┏━┛
 *         ┃　　　┃　　　　　　　　　
 *         ┃　　　┃
 *         ┃　　　┗━━━┓
 *         ┃　　　　　　　┣┓
 *         ┃　　　　　　　┏┛
 *         ┗┓┓┏━┳┓┏┛
 *　　      ┃┫┫　┃┫┫
 *　        ┗┻┛　┗┻┛
 *
 *-------------------- 神兽保佑 永无bug --------------------
 * 
 * 
 * 
 * @author yuezh2   2018年8月2日 上午11:21:47
 *
 */
public class TestAsyncTask implements Runnable{
	
//	private DeferredResult<ResponseEntity<String>> result;
	private DeferredResult<String> result;
	
	
	public TestAsyncTask(DeferredResult<String> result){
		this.result = result;
	}
	

	@Override
	public void run() {
		//业务逻辑start
		//......
		//业务逻辑end
		result.setResult("response ok");
	}

}
