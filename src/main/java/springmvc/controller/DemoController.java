package springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import springmvc.annotation.Controller;
import springmvc.annotation.Qualifier;
import springmvc.service.DemoService;

/**
 * 
 * @author yuezh2   2017年4月7日 下午3:38:38
 *
 */
@Controller
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
	@RequestMapping(value="update")
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
	public String query(HttpServletRequest request , HttpServletResponse response , String param){
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
