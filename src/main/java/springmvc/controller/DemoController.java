package springmvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import springmvc.annotation.Controller;
import springmvc.annotation.Qualifier;
import springmvc.annotation.RequestMapping;
import springmvc.annotation.RequestParam;
import springmvc.service.DemoService;

/**
 * 
 * http://localhost:8081/yzh-practice/demo/query?username=zhangsan
 * 
 * @author yuezh2   2017年4月7日 下午3:38:38
 *
 */
@Controller("demo")
public class DemoController {

	@Qualifier("demoService")
	private DemoService service;
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request , HttpServletResponse response , String param){
		service.update(null);
		return null;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value="delete")
	public String delete(HttpServletRequest request , HttpServletResponse response , String param){
		service.delete(null);
		return null;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value="query")
	public String query(HttpServletRequest request , HttpServletResponse response , HttpSession session
			,@RequestParam("username")String uname , @RequestParam("password")String password,Map map){
		service.query(null);
		return null;
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value="insert")
	public String insert(HttpServletRequest request , HttpServletResponse response , String param){
		service.insert(null);
		return null;
	}
	
	
	
}
