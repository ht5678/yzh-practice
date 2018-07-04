package tree.bstree;

/**
 * 二叉树
 * 
 * 二叉查找树(Binary Search Tree)，又被称为二叉搜索树。
 *	它是特殊的二叉树：对于二叉树，假设x为二叉树中的任意一个结点，x节点包含关键字key，节点x的key值记为key[x]。
 *	如果y是x的左子树中的一个结点，则key[y] <= key[x]；如果y是x的右子树的一个结点，则key[y] >= key[x]。那么，这棵树就是二叉查找树。如下图所示：
 *
 * 
 * 在二叉查找树中：
	(01) 若任意节点的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
	(02) 任意节点的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
	(03) 任意节点的左、右子树也分别为二叉查找树。
	(04) 没有键值相等的节点（no duplicate nodes）。
 * 
 * 
 * 
 * BSTree是二叉树，它保护了二叉树的根节点mRoot；mRoot是BSTNode类型，而BSTNode是二叉查找树的节点，它是BSTree的内部类。BSTNode包含二叉查找树的几个基本信息：
(01) key -- 它是关键字，是用来对二叉查找树的节点进行排序的。
(02) left -- 它指向当前节点的左孩子。
(03) right -- 它指向当前节点的右孩子。
(04) parent -- 它指向当前节点的父结点。


 *
 * 
 *  遍历

这里讲解前序遍历、中序遍历、后序遍历3种方式。

2.1 前序遍历
若二叉树非空，则执行以下操作：
(01) 访问根结点；
(02) 先序遍历左子树；
(03) 先序遍历右子树。

---------------------------------------------------------------------------------

中序遍历

若二叉树非空，则执行以下操作：
(01) 中序遍历左子树；
(02) 访问根结点；
(03) 中序遍历右子树。

中序遍历代码

---------------------------------------------------------------------------------

后序遍历

若二叉树非空，则执行以下操作：
(01) 后序遍历左子树；
(02) 后序遍历右子树；
(03) 访问根结点。

 * 
 * 
 * 看看下面这颗树的各种遍历方式：
 * 							3
 * 			1							5
 * 				
 * 				2				4				6
 * 
 * 
 * 对于上面的二叉树而言，
(01) 前序遍历结果： 3 1 2 5 4 6
(02) 中序遍历结果： 1 2 3 4 5 6 
(03) 后序遍历结果： 2 1 4 6 5 3

 *
 * 
 * 
  前序遍历：根结点 ---> 左子树 ---> 右子树

  中序遍历：左子树---> 根结点 ---> 右子树

  后序遍历：左子树 ---> 右子树 ---> 根结点

  层次遍历：仅仅需按层次遍历就可以
 * 
 * 
 * 前驱和后继
节点的前驱：是该节点的左子树中的最大节点。("二叉树中数据值大于该结点"的"最小结点")
节点的后继：是该节点的右子树中的最小节点。("二叉树中数据值小于该结点"的"最大结点")
 * 
 * 
 * 
 * 
 * 
 * 
 * @author yuezh2   2018年7月2日 下午6:22:00
 *
 */
public class BinarySearchTree <T extends Comparable<T>>{

	private BSTNode<T> mRoot;		//根节点
	
	
	/**
	 * 
	 */
	public BinarySearchTree(){
		mRoot = null;
	}
	
	
	/**
	 * 前序遍历二叉树
	 * @param tree
	 */
	private void preOrder(BSTNode<T> tree){
		if(tree!=null){
			System.out.println(tree.key);
			preOrder(tree.left);
			preOrder(tree.right);
		}
	}
	
	
	public void preOrder(){
		preOrder(mRoot);
	}
	
	
	/**
	 * 中序遍历二叉树
	 * @param tree
	 */
	private void inOrder(BSTNode<T> tree){
		if(tree!=null){
			inOrder(tree.left);
			System.out.println(tree.key);
			inOrder(tree.right);
		}
	}
	
	
	public void inOrder(){
		inOrder(mRoot);
	}
	
	
	/**
	 * 后序遍历二叉树
	 * @param tree
	 */
	private void postOrder(BSTNode<T> tree){
		if(tree!=null){
			postOrder(tree.left);
			postOrder(tree.right);
			System.out.println(tree.key);
		}
	}
	
	
	public void postOrder(){
		postOrder(mRoot);
	}
	
	/**
	 * (递归实现)查找"二叉树x"中键值为key的节点
	 * @param x
	 * @param key
	 * @return
	 */
	private BSTNode<T> search(BSTNode<T> x , T key){
		if(x==null)
			return x;
		
		int cmp = key.compareTo(x.key);
		
		if(cmp<0){
			return search(x.left, key);
		}else if(cmp>0){
			return search(x.right, key);
		}else{
			return x;
		}
	}
	
	
	/**
	 * (非递归实现)查找'二叉树x'中键值为key的节点
	 * @param x
	 * @param key
	 * @return
	 */
	public BSTNode<T> iterativeSearch(BSTNode<T> x , T key){
		while(x!=null){
			int cmp = key.compareTo(x.key);
			if(cmp<0){
				x = x.left;
			}else if(cmp>0){
				x = x.right;
			}else{
				return x;
			}
		}
		return x;
	}
	
	
	
	public BSTNode<T> iterativeSearch(T key){
		return iterativeSearch(mRoot, key);
	}
	
	
	
	/**
	 * 查找最小节点,返回tree为根节点的二叉树的最小节点
	 * @param tree
	 * @return
	 */
	private BSTNode<T> minimum(BSTNode<T> tree){
		if(tree==null){
			return null;
		}
		
		while(tree.left!=null){
			tree = tree.left;
		}
		
		return tree;
	}
	
	
	public T minimum(){
		BSTNode<T> p = minimum(mRoot);
		if(p!=null){
			return p.key;
		}
		return null;
	}
	
	
	
	
	/**
	 * 查找最大节点,返回tree为根节点的二叉树的最大节点
	 * @param tree
	 * @return
	 */
	private BSTNode<T> maximum(BSTNode<T> tree){
		if(tree==null){
			return null;
		}
		
		while(tree.right!=null){
			tree = tree.right;
		}
		
		return tree;
	}

	
	public T maximum(){
		BSTNode<T> p = maximum(mRoot);
		if(p!=null){
			return p.key;
		}
		return null;
	}
	
	
	/**
	 * 找结点(x)的后继结点。即，查找"二叉树中数据值大于该结点"的"最小结点"。
	 * @param x
	 * @return
	 */
	public BSTNode<T> successor(BSTNode<T> x){
		//如果x存在右孩子,则'x的后继节点'为'以其有孩子的子树的最小节点'
		if(x.right!=null){
			return minimum(x.right);
		}
		//如果没有右孩子,则x有以下两种可能:
		//1.x是'一个左孩子',则'x的后继节点'为'它的父节点'
		//2.x是'一个右孩子',则查找'x的最低的父节点,并且该父节点要具有左孩子' , 找到的这个'最低的父节点'就是'x的后继节点'
		BSTNode<T> y = x.parent;
		while( (y!=null) && (x==y.right) ){//等同于查找根节点
			x = y;
			y = y.parent;
		}
		return y;
	}
	
	
	/**
	 * 找节点(x)的前驱节点,即查找'二叉树中数据值小于该节点'的'最大节点'
	 * @param x
	 * @return
	 */
	public BSTNode<T> predecessor(BSTNode<T> x){
		//如果x存在左孩子,则'x的前驱节点'为'以其左孩子为根的子树的最大节点'
		if(x.left!=null){
			return maximum(x.left);
		}
		
		//如果x没有左孩子,则x有两种情况:
		//1.x是'一个右孩子',则'x的前驱节点'为'它的父节点'
		//2.x是'一个左孩子',则查找'x的最低父节点,并且该父节点要有右孩子',找到的这个'最低的父节点'就是x的前驱节点
		BSTNode<T> y = x.parent;
		while( (y!=null) && (x==y.left) ){//等同于查找根节点
			x=y;
			y=y.parent;
		}
		return y;
	}
	
	
	
	private void insert(BinarySearchTree<T> bst , BSTNode<T> z){
		int cmp;
		BSTNode<T> y = null;
		BSTNode<T> x = bst.mRoot;
		
		//查找z的插入位置
		while(x!=null){
			y = x;
			cmp = z.key.compareTo(x.key);
			if(cmp<0){
				x = x.left;
			}else{
				x = x.right;
			}
			
		}
		
		z.parent = y;
		if(y==null){
			bst.mRoot = z;
		}else{
			cmp = z.key.compareTo(y.key);
			if(cmp<0){
				y.left=z;
			}else{
				y.right=z;
			}
		}
		
	}
	
	
	/**
	 * 新建节点(key), 将其插入到二叉树中
	 * 
	 * 参数说明:
	 * 		tree	二叉树的根节点
	 * 		key	插入节点的键值
	 * 
	 * @param key
	 */
	public void insert(T key){
		BSTNode<T> z = new BSTNode<T>(key, null, null, null);
		//如果新建节点失败,返回
		if(z!=null){
			insert(this,z);
		}
	}
	
	
	/**
	 * 删除节点z,并返回被删除的节点
	 * 
	 * 
	 * @param bst		二叉树
	 * @param z			删除的节点
	 * @return
	 */
	private BSTNode<T> remove(BinarySearchTree<T> bst , BSTNode<T> z){
		//真正删除节点的子树：左右子树合体的抽象
		BSTNode<T> x = null;
		//真正删除的节点
		BSTNode<T> y = null;
		
		//获取真正删除节点
		if( (z.left==null) || (z.right==null) ){
			y=z;
		}else{
			y = successor(z);
		}
		
		//获取真正删除节点的子树：左右子树合体的抽象
		if(y.left!=null){
			x = y.left;
		}else{
			x = y.right;
		}
		
		//删除 真正删除节点 
		if(x!=null){
			x.parent = y.parent;
		}
		
		//删除之后把子树折断了，准备焊接
		if(y.parent==null){
			bst.mRoot = x;
		}else if(y == y.parent.left){
			y.parent.left = x;
		}else{
			y.parent.right = x;
		}
		
		//针对情况三的删除转移、做值替换
		if(y!=z){
			z.key = y.key;
		}
		
		return y;
		
	}
	
	
	/**
	 * 删除节点z , 并且返回被删除的节点
	 * 
	 * @param key
	 */
	public void remove(T key){
		BSTNode<T> z , node;
		if((z = search(mRoot, key)) != null){
			if( (node=remove(this, z)) != null ){
				node = null;
			}
		}
	}
	
	
	
	/**
	 * 销毁二叉树
	 * @param tree
	 */
	private void destroy(BSTNode<T> tree){
		if(tree == null){
			return;
		}
		
		if(tree.left!=null){
			destroy(tree.left);
		}else if(tree.right!=null){
			destroy(tree.right);
		}
		
		tree = null;
	}
	
	
	
	
	public void clear(){
		destroy(mRoot);
		mRoot = null;
	}
	
	
	/**
	 * 打印二叉树
	 * 
	 * @param tree				
	 * @param key				节点的键值
	 * @param direction		 0:表示根节点
	 * 									 1:表示该节点是它的父节点的左孩子
	 * 									 2:表示该节点是它的父节点的右孩子
	 * 
	 */
	private void print(BSTNode<T> tree , T key , int direction){
		if(tree != null){
			if(direction==0){	//tree是根节点
				System.out.printf("%2d is root\n",tree.key);
			}else{
				System.out.printf("%2d is %2d's %6s child\n ",tree.key,key,direction==1?"right":"left");
			}
			
			print(tree.left, tree.key, -1);
			print(tree.right, tree.key, 1);
		}
	}
	
	
	public void print(){
		if(mRoot!=null){
			print(mRoot, mRoot.key, 0);
		}
	}
	
	
	
	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		bst.insert(20);
		bst.insert(15);
		bst.insert(25);
		bst.insert(10);
		bst.insert(18);
		bst.insert(30);
		bst.insert(12);
		bst.insert(24);
		bst.insert(9);
		bst.insert(19);
		bst.insert(17);
		
//		bst.inOrder();
//		BSTNode<Integer> node = bst.iterativeSearch(24);
//		BSTNode<Integer> pre =  bst.predecessor(node);
//		System.out.println(pre.key);
//		bst.remove(15);
//		bst.print();
		bst.preOrder();
		System.out.println("-----");
		bst.inOrder();
		System.out.println("-----");
		bst.postOrder();
	}
	
}
