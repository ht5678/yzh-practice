package tree.avl;


/**
 * 
 *  AVL树是根据它的发明者G.M. Adelson-Velsky和E.M. Landis命名的。
 *它是最先发明的自平衡二叉查找树，也被称为高度平衡树。相比于"二叉查找树"，它的特点是：AVL树中任何节点的两个子树的高度最大差别为1。
 * 
 * AVL树的查找、插入和删除在平均和最坏情况下都是O(logn)。
 * 
 * 
 * 
 * @author yuezh2   2018年7月4日 下午5:08:22
 *
 */
public class AVLTreeDemo <T extends Comparable<T>>{
	
	//根节点
	private AVLTreeNode<T> mRoot;

	
	/**
	 * 
	 */
	public AVLTreeDemo(){
		
	}
	
	
	/**
	 * 获取树的高度
	 * @param tree
	 * @return
	 */
	private int height(AVLTreeNode<T> tree){
		if(tree!=null){
			return tree.height;
		}
		return 0;
	}
	
	
	
	public int height(){
		return height(mRoot);
	}
	
	
	/**
	 * 比较两个值的大小
	 * @param a
	 * @param b
	 * @return
	 */
	private int max(int a, int b){
		return a>b?a:b;
	}
	
	
	/**
	 * 前序遍历'avl树'
	 * @param tree
	 */
	private void preOrder(AVLTreeNode<T> tree){
		if(tree!=null){
			System.out.println(tree.key+"");
			preOrder(tree.left);
			preOrder(tree.right);
		}
	}
	
	
	public void preOrder(){
		preOrder(mRoot);
	}
	
	
	/**
	 * 中序遍历 'AVL树'
	 * @param tree
	 */
	private void inOrder(AVLTreeNode<T> tree){
		if(tree!=null){
			inOrder(tree.left);
			System.out.println(tree.key+"");
			inOrder(tree.right);
		}
	}
	
	
	public void inOrder(){
		inOrder(mRoot);
	}
	
	
	/**
	 * 后序遍历'AVL树'
	 * @param tree
	 */
	private void postOrder(AVLTreeNode<T> tree){
		if(tree!=null){
			postOrder(tree.left);
			postOrder(tree.right);
			System.out.println(tree.key+"");
		}
	}
	
	
	public void postOrder(){
		postOrder(mRoot);
	}
	
	
	
	
	

}
