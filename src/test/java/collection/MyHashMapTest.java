package collection;

import org.junit.Before;
import org.junit.Test;


/**
 * 
 * 类MyHashMapTest.java的实现描述：myhashmap测试用例
 * @author yuezhihua 2015年8月18日 下午8:02:29
 */
public class MyHashMapTest {
    
    
    private MyHashMap<String, String> map = new MyHashMap<>(8);
    
    
    @Before
    public void before(){
        map.put("zhangsan", "zhangsan");
        map.put("xianka", "xianka");
        map.put("neicun", "neicun");
        map.put("yingpan", "yingpan");
        map.put("cpu", "cpu");
    }
    
    
    /**
     * 测试删除
     */
    @Test
    public void testRemvoe(){
    	System.out.println(map.size());
    	map.remove("xianka");
    	System.out.println(map.size());
    	System.out.println(map.toString());
    }
    
    
    /**
     * 测试添加相同数据
     */
    @Test
    public void testRepeatPut(){
    	System.out.println(map.size());
    	map.put("yingpan", "yingpan");
    	System.out.println(map.size());
    	System.out.println(map.toString());
    }
    
    
    /**
     * 测试扩容
     */
    @Test
    public void testResize(){
    	map.put("shanliang", "善良");
    	map.put("shanliang1", "善良1");
    }
    
    
    
    /**
     * 测试打印所有元素
     * 
     * **代表分隔数组
     * ##代表分隔链表元素
     * 
     */
    @Test
    public void testToString(){
        System.out.println(map.toString());
    }
    
    
    
    /**
     * hashcode练习
     */
    @Test
    public void test(){
        System.out.println("zhangsan".hashCode());
        System.out.println("张三".hashCode());
        
        //源码方式分配
        System.out.println("zhangsan".hashCode() & 15);
        System.out.println("张三".hashCode() & 15);
        
        //hash分配位置
        System.out.println(Math.abs("zhangsan".hashCode()) % 15);
        System.out.println(Math.abs("张三".hashCode()) % 15);
        
    }

}
