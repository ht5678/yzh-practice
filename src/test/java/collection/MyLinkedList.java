package collection;

import java.util.NoSuchElementException;




/**
 * 
 * 类MyLinkedList.java的实现描述：linkedlist原理实现
 * @author yuezhihua 2015年8月17日 下午4:31:31
 */
public class MyLinkedList<E> {

    
    Node<E> first;
    
    Node<E> last;
    
    int size;
    
    
    public MyLinkedList(){
        
    }
    
    
    
    /**
     * 添加元素
     * @param e
     * @return
     */
    public boolean add(E e){
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, null, e);
        last = newNode;
        if(l == null){
            first = newNode;
        }else{
            l.next = newNode;
        }
        size++;
        return true;
    }
    
    
    /**
     * 获取元素
     * @param index
     * @return
     */
    public E get(int index) {
        return node(index).item;
    }
    
    
    /**
     * 删除元素
     * @param index
     * @return
     */
    public E remove(int index) {
        Node<E> x = node(index);
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return x.item;
    }
    
    
    /**
     * 遍历获取索引处的元素
     * @param index
     * @return
     */
    Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
    
    
    /**
     * 获取第一个元素
     * @return
     */
    public E getFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }
    
    
    /**
     * 获取最后一个元素
     * @return
     */
    public E getLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }
    
    
    /**
     * 删除元素
     * @param e
     * @return
     */
    public boolean remove(Object e){
        for(Node<E> i = first ; i !=null ; i=i.next){
            //如果相等，移除元素
            if(e.equals(i.item)){
                unlink(i);
                return true;
            }
        }
        return false;
    }
    
    
    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }
    
    
    /**
     * 重写toString方法， 获取所有的元素
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> f = first;
        while(true){
            sb.append(f.item).append("  -  ");
            if(f.next==null){
                break;
            }
            f = f.next;
        }
        
        return sb.toString();
    }






    private static class Node<E>{
        Node<E> prev;
        Node<E> next;
        E item;
        
        
        Node(Node<E> prev, Node<E> next, E item) {
            super();
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
        
    }
    
    
    
    
    
}