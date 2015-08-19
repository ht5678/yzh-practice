package collection;




/**
 * 
 * 类MyHashMap.java的实现描述：hashmap山寨实现
 * @author yuezhihua 2015年8月18日 下午5:51:05
 */
public class MyHashMap<K,V> {

    /** 默认1000 **/
    Entry<K,V>[] table = (Entry<K,V>[]) new Entry<?,?>[999];
    
    int size;
    
    float loadFactor = 0.75f;
    
    //当前size*loadFactor=threshold
    int threshold;
    
    
    public MyHashMap(){
    }
    
    
    public MyHashMap(int number){
        table = (Entry<K,V>[]) new Entry<?,?>[number];
        threshold = (int)(size*loadFactor);
    }
    
    
    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public V put(K key , V value){
        //计算hash值
        int hash = key.hashCode();
//        int index = hash & (table.length-1);
        int index = Math.abs(hash) % table.length;
        //是否有重复key,替换
        for (Entry<K,V> e = table[index]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        //扩容 size*2
        if(size>=threshold){
            resize(table.length*2);
            index = hash & (table.length-1);
        }
        //直接添加
        Entry<K,V> e = table[index];
        table[index] = new Entry<K, V>(key, value, e, hash);
        size++;
        return value;
    }
    
    
    
    /**
     * 重新hash分布
     * @param newCapacity
     */
    private void resize(int newCapacity){
        Entry<K,V>[] newTable = (Entry<K,V>[]) new Entry<?,?>[newCapacity];
        for (Entry<K,V> e : table) {
            while(null != e) {
                Entry<K,V> next = e.next;
                int hash = e.key.hashCode();
                int index = hash & (newCapacity-1);
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
        table = newTable;
        threshold = (int)(newCapacity*loadFactor);
    }
    
    
    
    /**
     * 移除元素
     * @param key
     * @return
     */
    public V remove(Object key) {
      //是否有重复key
        int hash = key.hashCode();
        int index = hash & (table.length-1);
        Entry<K,V> prev = table[index];
        Entry<K,V> e = prev;
        while(null != e) {
            Entry<K,V> next = e.next;
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k)))) {
                size--;
                if (prev == e)
                    table[index] = next;
                else
                    prev.next = next;
                return e.value;
            }
            e = next;
        }
        return null;
    }
    
    
    /**
     * 返回map大小
     * @return
     */
    public int size(){
        return size;
    }
    
    
    
    /**
     * 打印所有元素
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Entry<K,V> e : table) {
            while(null != e) {
                Entry<K,V> next = e.next;
                sb.append("key:").append(e.key).append("  ---  value:").append(e.value).append(" ## ");
                e = next;
            }
            System.out.println(" ** ");
        }
        return sb.toString();
    }
    
    
    
    private static class Entry<K,V>{
        K key;
        V value;
        Entry<K, V> next;
        int hash;
        
        
        Entry(K key , V value,Entry<K, V> next,int hash){
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = hash;
        }
        
    }

}
