package servlet3.simple;

import java.io.PrintWriter;

import javax.servlet.AsyncContext;

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
 * @author yuezh2   2018年8月1日 下午8:42:52
 *
 */
public class AsyncRequestProcessor implements Runnable {
	
	private AsyncContext asyncContext;
	
	private int secs;
	
	
	public AsyncRequestProcessor(){
		
	}
	
	
	public AsyncRequestProcessor(AsyncContext asyncContext , int secs){
		this.asyncContext = asyncContext;
		this.secs = secs;
	}
	
	
	

	@Override
	public void run() {
		//do business work
		//async context
		//get response
		
		System.out.println("AsyncSupported?"+asyncContext.getRequest().isAsyncSupported());
		longProcessing(secs);
		try {
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write("processing done for "+secs+" milliseconds!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//complete the processing
		asyncContext.complete();
		
	}

	
	
	
	private void longProcessing(int secs){
		//wait for given time before finishing
		try {
			Thread.sleep(secs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
