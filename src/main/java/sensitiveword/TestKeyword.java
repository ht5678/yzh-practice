package sensitiveword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 多模式匹配
 * 
 * @author yuezh2   2016年3月22日 下午6:56:56
 *
 */
public class TestKeyword {

	
	
	
	public static void main(String[] args) throws Exception {
		
		long curTime = System.currentTimeMillis();
		
		Map<String, Keyword> map = new HashMap<>();
		
		Map<String, Keyword> keyMap = new HashMap<>();
		
		String[] keywords = new String[]{"没有","建议","网址","需求","短信","短时间","原来","自定义","自己","四叶草","四百","四百万"};
		
		//1.将关键字分解
		for(String keyword : keywords){
			byte[] bytes = keyword.getBytes();
			map = keyMap;
			String tmp = "";
			for(int i = 0 ; i <bytes.length ; i = i+3){
				String key = new String(bytes, i, 3);
				if(map.get(key)==null){
					map.put(key, new Keyword());
				}
				tmp = tmp + key;
				Keyword kw = map.get(key);
				kw.setKeyword(tmp);
				if((i+3)==bytes.length){
					kw.setLast(true);
				}else{
					kw.setLast(false);
				}
				
				map = kw.getChildrens();
			}
		}
		
//		System.out.println(keyMap);
		
		//1
//		FileInputStream fis=new FileInputStream("d://contents.txt");
//		byte[] b=new byte[1024*1024];
//		while(fis.read(b)!=-1){
//			new String(b);
//		}
//		fis.close();
		
		//2
//		FileInputStream fis=new FileInputStream("d://contents.txt");
//		FileChannel channel = fis.getChannel();
//		ByteBuffer bytedata = ByteBuffer.allocate(1024*1024);
//		Charset cs = Charset.forName("utf8");
//		 while(channel.read(bytedata)!= -1){  
//            bytedata.flip();  
//            byte[] bytes = bytedata.array();
//            char[] ca = new char[bytes.length];
//            CharBuffer cb = CharBuffer.wrap(ca);
//            
//            CharsetDecoder cd = cs.newDecoder()
//                    .onMalformedInput(CodingErrorAction.REPLACE)
//                    .onUnmappableCharacter(CodingErrorAction.REPLACE);
//            CoderResult cr = cd.decode(bytedata, cb, true);
//            cr = cd.flush(cb);
//            for(int i = 0 ; i < ca.length ;i++){
//            	Keyword kw = keyMap.get(ca[i]);
//            	if(kw!=null){
//            		System.out.println(kw);
//            	}
//            }
            
//            String line = new String(bytedata.array());
//			for(int i = 0 ; i < line.length() ;i++){
//				String word = line.substring(i, i+1);
//				Keyword kw = keyMap.get(word);
//				if(kw!=null){
//					i = matchWord(keyMap, line, i, lineNumber, kw);
//				}
//			}
//            bytedata.clear();  
//		 }  
//		 
//		 fis.close();
//		 channel.close();
		
		//搜索关键字
		BufferedReader br = new BufferedReader(new FileReader(new File("d://contents.txt")));
		String line = "";
		int lineNumber = 0;
		while((line = br.readLine()) != null){
			lineNumber++;
			for(int i = 0 ; i < line.length() ;i++){
				String word = line.substring(i, i+1);
				Keyword kw = keyMap.get(word);
				if(kw!=null){
					i = matchWord(keyMap, line, i, lineNumber, kw);
				}
			}
		}
		
		br.close();
		System.out.println("costTime:"+(System.currentTimeMillis()-curTime));
	}
	
	
	
	private static int matchWord(Map<String, Keyword> keyMap , String line , int i , int lineNumber,Keyword kw){
		Map<String, Keyword> map = new HashMap<>();
		for(int index = i+1 ; index<line.length();index++){
			if(kw == null){
				return i;
			}
			if(kw.isLast()){
				System.out.println(lineNumber);
				System.out.println(kw.getKeyword());
				return index;
			}
			map = kw.getChildrens();
			String word = line.substring(index, index+1);
			kw = map.get(word);
		}
		return i;
	}
	
	
}







class Keyword{
	
	private String keyword;
	
	private Map<String, Keyword> childrens = new HashMap<>();
	
	
	private boolean last;



	public boolean isLast() {
		return last;
	}


	public void setLast(boolean last) {
		this.last = last;
	}


	public Map<String, Keyword> getChildrens() {
		return childrens;
	}


	public void setChildrens(Map<String, Keyword> childrens) {
		this.childrens = childrens;
	}


	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}



}
