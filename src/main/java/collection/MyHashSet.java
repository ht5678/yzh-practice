package collection;



/**
 * 
 * @author sdwhy
 *
 */
public class MyHashSet<K> {
	
	
	MyHashMap<K, Object> map = new MyHashMap<>();
	
	
	/**
	 * 添加元素
	 */
	public void add(K key){
		map.put(key, null);
	}
	
	
	/**
	 * 删除元素
	 */
	public void remove(K key){
		map.remove(key);
	}
	
	
	/**
	 * 大小
	 * @return
	 */
	public int size(){
		return map.size();
	}

	
	public String toString(){
		return map.toString();
	}

}
