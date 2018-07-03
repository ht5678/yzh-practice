package tree.bstree;

/**
 * 
 * @author yuezh2   2018年7月3日 上午10:41:00
 *
 */
public class BSTNode <T extends Comparable<T>>{
	
	
	T key;		//键值
	BSTNode<T> left;
	BSTNode<T> right;
	BSTNode<T> parent;
	
	
	/**
	 * 
	 * @param key
	 * @param parent
	 * @param left
	 * @param right
	 */
	public BSTNode(T key , BSTNode<T> parent,BSTNode<T> left,BSTNode<T> right) {
		this.key = key;
		this.parent=parent;
		this.left=left;
		this.right=right;
	}
	
	
	public T getKey(){
		return this.key;
	}
	
	public String toString(){
		return "key:"+key;
	}

}
