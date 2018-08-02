package servlet3;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 1、声明Servlet，增加asyncSupported属性，开启异步支持。@WebServlet(urlPatterns = "/AsyncLongRunningServlet", asyncSupported = true)
 * 	2、通过request获取异步上下文AsyncContext。AsyncContext asyncCtx = request.startAsync();
 * 	3、开启业务逻辑处理线程，并将AsyncContext 传递给业务线程。executor.execute(new AsyncRequestProcessor(asyncCtx, secs));
 * 	4、在异步业务逻辑处理线程中，通过asyncContext获取request和response，处理对应的业务。
 * 	5、业务逻辑处理线程处理完成逻辑之后，调用AsyncContext 的complete方法。asyncContext.complete();从而结束该次异步线程处理。
 * 
 * 
 * 
 * 测试url:
 * http://localhost:8081/yzh-practice/servlet3/async/demo?time=100
 * 
 * 
 * @author yuezh2   2018年8月1日 下午8:39:06
 *
 */
@WebServlet(urlPatterns="/servlet3/async/demo",asyncSupported=true)
public class AsyncServletDemo extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3945105718438423532L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		System.out.println("AsyncLongRunningServlet start:Name="+Thread.currentThread().getName()+" , ID="+Thread.currentThread().getId());
		
		request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
		
		String time = request.getParameter("time");
		int secs = Integer.valueOf(time);
		//max 10 seconds
		if(secs>10000){
			secs = 10000;
		}
		
		AsyncContext asyncCtx = request.startAsync();
		asyncCtx.addListener(new AsyncServletListener());
		//异步servlet的超时时间,同步servlet有对应的超时时间,如果在指定时间内没有执行完操作,response会依然走原来servlet的结束逻辑,
		//后续的异步操作执行完再写回的时候,可能会遇到异常
		asyncCtx.setTimeout(9000);	
		
		
		ThreadPoolExecutor executor = (ThreadPoolExecutor)request.getServletContext().getAttribute("executor");
		executor.execute(new AsyncRequestProcessor(asyncCtx,secs));
		
		long endTime = System.currentTimeMillis();
		System.out.println("AsyncLongRunningServlet End::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId() + "::Time Taken="
                + (endTime - startTime) + " ms.");
		
	}
	
	
	

}
