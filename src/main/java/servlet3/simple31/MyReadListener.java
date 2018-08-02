package servlet3.simple31;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

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
 * @author yuezh2   2018年8月2日 下午7:30:39
 *
 */
public class MyReadListener implements ReadListener{
	
	private ServletInputStream inputStream;
	
	private AsyncContext asyncContext;
	
	
	/**
	 * 
	 * @param input
	 * @param context
	 */
	public MyReadListener(ServletInputStream input , AsyncContext context){
		this.inputStream = input;
		this.asyncContext = context;
	}
	
	
	
	
	
	
	@Override
	public void onDataAvailable() throws IOException {
		System.out.println("数据可用时触发执行");
	}

	
	/**
	 * 数据读完时触发调用
	 */
	@Override
	public void onAllDataRead() throws IOException {
		try {
			Thread.sleep(3000);//暂停3秒,模拟耗时处理数据
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write("数据读完了");
			out.flush();
			System.out.println("数据读完了");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 数据出错触发调用
	 */
	@Override
	public void onError(Throwable t) {
		System.out.println("数据出错");
		t.printStackTrace();
	}

}
