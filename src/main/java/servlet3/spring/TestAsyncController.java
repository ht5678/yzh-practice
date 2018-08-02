package servlet3.spring;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
 * @author yuezh2   2018年8月2日 上午11:15:53
 *
 */
@Controller
@RequestMapping("/async/test")
public class TestAsyncController {
	
	
    @ResponseBody
    @RequestMapping("/{testUrl}")
    public DeferredResult<String> testProcess(@PathVariable String testUrl) {
//        final DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<ResponseEntity<String>>();
        final DeferredResult<String> deferredResult = new DeferredResult<String>();
 
        // 业务逻辑异步处理,将处理结果 set 到 DeferredResult
        new Thread(new TestAsyncTask(deferredResult)).start();
 
        return deferredResult;
    }

}
