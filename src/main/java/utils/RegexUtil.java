package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author yuezh2   2018年5月29日 下午4:58:49
 *
 */
public class RegexUtil {

	
	
	public static void main(String[] args) {
		String str = "abfoewqajfiaf<a href=\"www.google.com\">google</a>is the bestest search engine .<font color='red'>text button</font> i like it ,[[ubb]][[\\ubb]],texst mall price , [ttt] , fdasfdsafd[tt][/tt],,,发送<a href='https://bbs.thinkpad.com/thread-4469186-1-1.html' target=_blank>https://bbs.thinkpad.com/thread-4469186-1-1.html</a>mmmmhttp://www.baidu.com/index.php xxhttp://clubimg.lenovo.com.cn/pic/10596075443072/0法官";
//     Pattern pattern = Pattern.compile("(<[^>]+>)");					//<>标签
//		Pattern pattern = Pattern.compile("(\\[[^\\]]+\\])");		//[]ubb标签
        Pattern pattern = Pattern.compile("(<[^>]+>|\\[[^\\]]+\\]|(https|http)://[\\d\\w\\.\\/\\-\\?\\=]+)");		//http,https请求
        Matcher matcher = pattern.matcher(str);

        List<MatchModel> matchList = new ArrayList<>();
        while (matcher.find()) {
        	MatchModel mm = new MatchModel();
			mm.setStart(matcher.start());
			mm.setEnd(matcher.end());
			
			matchList.add(mm);
			
			System.out.println(matcher.group());
		}
		
	}
	

	
}

class MatchModel{
	private int start;
	
	private int end;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
	
}
