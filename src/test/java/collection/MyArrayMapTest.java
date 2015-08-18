package collection;

import org.junit.Before;
import org.junit.Test;


/**
 * 
 * 类MyArrayMapTest.java的实现描述：TODO 类实现描述 
 * @author yuezhihua 2015年8月18日 下午5:33:36
 */
public class MyArrayMapTest {
    
    private MyArrayMap<String, String> map = new MyArrayMap<>(2);
    
    
    @Before
    public void before(){
        map.put("zhangsan", "zhangsan");
        map.put("wangwu", "wangwu");
        map.put("zhaoliu", "zhaoliu");
    }
    
    
    
    
    
    /**
     * 测试删除
     */
    @Test
    public void testRemove(){
        map.remove("wangwu");
        System.out.println(map.toString());
    }
    
    
    /**
     * 测试添加
     */
    @Test
    public void testAdd(){
        map.put("yachishanliang", "wahaha");
        System.out.println(map.toString());
    }
    
    
    /**
     * 测试重复的key
     */
    @Test
    public void testRepeatKey(){
        map.put("wangwu", "heheda");
        System.out.println(map.toString());
    }
    
    
    /**
     * 测试打印所有元素
     */
    @Test
    public void testToString(){
        System.out.println(map.toString());
    }
    

}
