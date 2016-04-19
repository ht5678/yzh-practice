package sensitiveword;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

/**
 * 
 * elasticsearch搜索
 * 
 * 
 * es结构脚本:
 * curl -XPUT 'localhost:9200/sensitiveword?pretty' -d '
{
}'




curl -XPUT 'http://localhost:9200/sensitiveword/_mapping/sensitive_word' -d '
{
    "sensitive_word" : {
        "properties" : {
            "word" : {"type" : "string", "store" : true, "index":"not_analyzed"}
        }
    }
}
'


curl -XPOST 'http://localhost:9200/_aliases' -d '
{
    "actions" : [
        { "add" : { "index" : "sensitiveword", "alias" : "wordsdict" } }
    ]
}'
 * 
 * 
 * @author yuezh2   2016年4月18日 下午9:59:25
 *
 */
public class TestKeywordElastic {
	
	
	
	private static String template = "{"
			+ "\"template\":{"
			+"    \"query\": {"
			+"        \"query_string\": {"
			+"            \"query\": \"{{_search}}\""
			+"        }"
			+"    }"
			+"}}";
	
	
	public static void main(String[] args) throws Exception{
		long curTime = System.currentTimeMillis();
		
		String content = "";
		
		//读取文件
		FileInputStream fis=new FileInputStream("d://contents.txt");
		FileChannel channel = fis.getChannel();
		ByteBuffer bytedata = ByteBuffer.allocate(8*1024*1024);
		Charset cs = Charset.forName("utf8");
		 while(channel.read(bytedata)!= -1){  
            bytedata.flip();  
            byte[] bytes = bytedata.array();
            char[] ca = new char[bytes.length];
            CharBuffer cb = CharBuffer.wrap(ca);
            
            CharsetDecoder cd = cs.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPLACE)
                    .onUnmappableCharacter(CodingErrorAction.REPLACE);
            CoderResult cr = cd.decode(bytedata, cb, true);
            cr = cd.flush(cb);
            
            content = new String(bytedata.array());
            //获取敏感词
    		getWords(content);
    		
            bytedata.clear();  
		 }  
		 
		 fis.close();
		 channel.close();
		
		
		System.out.println("costTime:"+(System.currentTimeMillis()-curTime));
	}


	private static void getWords(String content) {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "lenovo-club-es")
		        .put("client.transport.ping_timeout", 5000)
		        .put("client.transport.nodes_sampler_interval", 5000).build();
		
		Client client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(
						"10.250.0.30",
						9300))
				.addTransportAddress(new InetSocketTransportAddress(
						"10.250.1.11",
						9300))
				.addTransportAddress(new InetSocketTransportAddress(
						"10.250.1.13",
						9300));
		
		
		
		
		Map<String, Object> params = new HashMap<>();
		params.put("_search", content);
		
		QueryBuilder qb = QueryBuilders.templateQuery(template, params);
		
		SearchRequestBuilder builder = client.prepareSearch("wordsdict")
				.setTypes("sensitive_word").setQuery(qb).setFrom(0).setSize(1000);
		
		SearchResponse response = builder.get();//执行查询
		
		if(null!=response){
			for(SearchHit hit : response.getHits().getHits()){
				System.out.println(hit.getSource().get("word"));
			}
		}
		
		client.close();
	}
	

}
