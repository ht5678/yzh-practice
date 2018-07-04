package tree.avl;

/**
 * 
 * @author yuezh2   2018年7月4日 下午6:01:37
 *
 */
public class AVLTreeNode <T extends Comparable<T>>{

	//键值
	T key;
	//高度
	int height;
	//左孩子
	AVLTreeNode<T> left;
	//右孩子
	AVLTreeNode<T> right;
	
	
	/**
	 * 
	 * @param key
	 * @param left
	 * @param right
	 */
	public AVLTreeNode(T key , AVLTreeNode<T> left , AVLTreeNode<T> right){
		this.key = key;
		this.left = left;
		this.right = right;
	} 
	
	
	
	public T getKey() {
		return key;
	}
	public void setKey(T key) {
		this.key = key;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public AVLTreeNode<T> getLeft() {
		return left;
	}
	public void setLeft(AVLTreeNode<T> left) {
		this.left = left;
	}
	public AVLTreeNode<T> getRight() {
		return right;
	}
	public void setRight(AVLTreeNode<T> right) {
		this.right = right;
	}
	
	
	
}
