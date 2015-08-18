package collection;

import java.util.Arrays;
import java.util.Collections;



/**
 * 
 * 类MyArrayMap.java的实现描述：数组方式实现map
 * @author yuezhihua 2015年8月18日 下午1:50:06
 */
public class MyArrayMap<K,V> {
    
    /** 默认1000 **/
    Entry<K,V>[] table = (Entry<K,V>[]) new Entry<?,?>[999];
    
    int size;
    
    
    public MyArrayMap(){
    }
    
    
    public MyArrayMap(int number){
        table = (Entry<K,V>[]) new Entry<?,?>[number];
    }
    
    
    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public V put(K key , V value){
        //扩容 size*2+1
        if((size+1)>table.length){
            Entry<K,V>[] newTable = (Entry<K,V>[]) new Entry<?,?>[table.length*2+1];
            for(int i = 0 ; i < size;i++){
                newTable[i] = table[i];
            }
            table = newTable;
        }
        //是否有重复key
        for(int i = 0 ; i < size;i++){
            if(key.equals(table[i].key)){
                table[i].value = value;
                return value;
            }
        }
        //直接添加
        table[size++] = new Entry<K, V>(key, value);
        
        return value;
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
        
        
        
        Entry(K key , V value){
            this.key = key;
            this.value = value;
        }
        
    }

}
