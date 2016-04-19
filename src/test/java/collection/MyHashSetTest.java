package collection;

import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author sdwhy
 *
 */
public class MyHashSetTest {
	
	MyHashSet<String> set = new MyHashSet<>();
	
	
	@Before
	public void before(){
		set.add("zhangsan");
		set.add("lisi");
		set.add("wangwu");
		set.add("zhaoliu");
	}
	
	
	
	/**
	 * 测试删除
	 */
	@Test
	public void testRemove(){
		System.out.println(set.size());
		set.remove("wangwu");
		System.out.println(set.size());
		System.out.println(set.toString());
	}
	
	
	/**
	 * 测试添加元素
	 */
	@Test
	public void testAdd(){
		set.add("haha");
		System.out.println(set.toString());
	}

}
