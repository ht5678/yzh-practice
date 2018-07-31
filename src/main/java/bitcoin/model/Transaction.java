package bitcoin.model;


/**
 * 交易
 * @author yuezh2   2018年7月26日 下午8:16:35
 *
 */
public class Transaction {

	/** 交易唯一标识 */
	private String id;
	/** 交易发送方 */
	private String sender;
	/** 交易接收方 */
	private String recipient;
	/** 交易金额 */
	private int amount;
	
	
	public Transaction(){
		super();
	}
	
	
	public Transaction(String id,String sender,String recipient , int amount){
		super();
		this.id=id;
		this.sender=sender;
		this.recipient=recipient;
		this.amount=amount;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getRecipient() {
		return recipient;
	}


	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
