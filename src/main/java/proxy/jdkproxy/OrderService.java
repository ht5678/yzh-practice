package proxy.jdkproxy;



/**
 * 
 * @author yuezh2   2017年1月16日 下午6:41:34
 *
 */
public interface OrderService {
	
	
	public String generateOrder();
	
	
	public int getOrderStatus(String orderId);
	
	
}
