package collection;

import org.junit.Before;
import org.junit.Test;


/**
 * 
 * 类MyLinkedListTest.java的实现描述：TODO 类实现描述 
 * @author yuezhihua 2015年8月18日 上午11:42:25
 */
public class MyLinkedListTest {
    
    private MyLinkedList<String> list = new MyLinkedList<>();
    
    
    @Before
    public void before(){
        list.add("zhangsan");
        list.add("lisi");
        list.add("wangwu");
    }
    
    
    /**
     * 删除元素
     */
    @Test
    public void testRemoveObj(){
        list.remove("lisi");
        System.out.println(list.toString());
    }
    
    
    /**
     * 测试删除元素
     */
    @Test
    public void testRemove(){
        list.remove(2);
        System.out.println(list.toString());
    }
    
    
    /**
     * 获取最后一个元素
     */
    @Test
    public void testGetLast(){
        System.out.println(list.getLast());
    }

    
    /**
     * 获取第一个元素
     */
    @Test
    public void testGetFirst(){
        System.out.println(list.getFirst());
    }
    
    
    /**
     * 测试通过index获取元素
     */
    @Test
    public void testGetByIndex(){
        System.out.println(list.get(2));
    }
    
    
    /**
     * 测试toString方法
     */
    @Test
    public void testToString(){
        System.out.println(list.toString());
    }
    
}
