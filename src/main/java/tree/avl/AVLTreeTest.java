package tree.avl;



/**
 * 
 * @author yuezh2   2018年7月9日 下午5:49:46
 *
 */
public class AVLTreeTest {

	private static int arr[] = {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
	
	
	public static void main(String[] args) {
		AVLTreeDemo<Integer> tree = new AVLTreeDemo<>();
		
		System.out.printf("==依次添加:  ");
		for(int i = 0 ; i < arr.length ; i++){
			System.out.printf(" %d ",arr[i]);
			tree.insert(arr[i]);
		}
		
		System.out.printf("\n == 前序遍历: ");
		tree.preOrder();
		
		System.out.printf("\n == 中虚遍历: ");
		tree.inOrder();
		
		System.out.printf("\n == 后序遍历: ");
		tree.postOrder();
		
		System.out.println();
		
		System.out.printf("== 高度:%d\n" , tree.height());
		System.out.printf("== 最小值:%d\n" , tree.minumum());
		System.out.printf("== 最大值:%d\n" , tree.maximum());
		System.out.println("== 树的详细信息: \n");
		tree.print();
		
		int i =8;
		System.out.printf("\n== 删除根节点: %d", i);
		tree.remove(i);

		System.out.printf("\n== 高度: %d", tree.height());
		System.out.printf("\n== 中序遍历: ");
		tree.inOrder();
		System.out.printf("\n== 树的详细信息: \n");
		tree.print();

		// 销毁二叉树
		tree.destroy();
		
	}
	
	
}
