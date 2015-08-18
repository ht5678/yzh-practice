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
        int index = hash & (table.length-1);
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
        if((size+1)>=table.length){
            Entry<K,V>[] newTable = (Entry<K,V>[]) new Entry<?,?>[table.length*2];
            for(int i = 0 ; i < size;i++){
                newTable[i] = table[i];
            }
            table = newTable;
        }
        //直接添加
//        table[size++] = new Entry<K, V>(key, value);
        
        return value;
    }
    
    
    
    /**
     * 重新hash分布
     * @param newCapacity
     */
    private void resize(int newCapacity){
        
    }
    
    
    
    /**
     * 移除元素
     * @param key
     * @return
     */
    public V remove(Object key) {
      //是否有重复key
      Entry<K,V>[] tmpTable = (Entry<K,V>[]) new Entry<?,?>[table.length];
      for(int i = 0 ; i < size;i++){
          if(key.equals(table[i].key)){
              System.arraycopy(table, 0, tmpTable, 0, i);
              System.arraycopy(table, i+1, tmpTable, i, size);
              table = tmpTable;
              size--;
              return table[i].value;
          }
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
        for(int i = 0 ; i < size;i++){
            sb.append("key:").append(table[i].key).append("  ---  value:").append(table[i].value).append(" ## ");
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
