package collection;

import org.junit.Test;


/**
 * 
 * 类MyHashMapTest.java的实现描述：myhashmap测试用例
 * @author yuezhihua 2015年8月18日 下午8:02:29
 */
public class MyHashMapTest {
    
    
    
    
    
    
    
    
    
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
