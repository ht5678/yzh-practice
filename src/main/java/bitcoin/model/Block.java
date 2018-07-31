package bitcoin.model;

import java.util.List;

import com.alibaba.fastjson.JSON;

import bitcoin.util.CryptoUtil;

/**
 * 区块结构
 * @author yuezh2   2018年7月26日 下午8:14:31
 *
 */
public class Block {

	/** 区块索引号 */
	private int index;
	/** 当前区块的hash值,区块唯一标识 */
	private String hash;
	/** 生成区块的时间戳 */
	private long timestamp;
	/** 当前区块的交易集合 */
	private List<Transaction> transactions;
	/** 工作量证明,计算hash值得次数 */
	private int nonce;
	/** 前一个区块的hash值 */
	private String previousHash;
	
	
	
	public Block(){
		super();
	}
	
	
	public Block(int index,long timestamp,List<Transaction> transactions,int nonce,String previousHash,String hash){
		super();
		this.index=index;
		this.timestamp=timestamp;
		this.transactions=transactions;
		this.nonce=nonce;
		this.previousHash=previousHash;
		this.hash=hash;
	}
	
	
	
	/**
	 * 查询余额
	 * @param blockChain
	 * @param address
	 * @return
	 */
	public static int getWalletBalance(List<Block> blockChain , String address){
		int balance = 0;
		for(Block block : blockChain){
			List<Transaction> transactions = block.getTransactions();
			for(Transaction transaction : transactions){
				if(address.equals(transaction.getRecipient())){
					balance = balance+transaction.getAmount();
				}
				if(address.equals(transaction.getSender())){
					balance = balance-transaction.getAmount();
				}
			}
		}
		return balance;
	}


	
	
	/**
	 * 挖矿
	 * @param blockChain	整个区块链
	 * @param txs				需记账交易记录
	 * @param address		矿工钱包地址
	 */
	public static void mineBlock(List<Block> blockChain , List<Transaction> txs , String address){
		//加入系统奖励的交易,默认挖矿10个比特币
		Transaction sysTx = new Transaction(CryptoUtil.UUID(),"",address,10);
		txs.add(sysTx);
		//获取当前区块链的最后一个区块
		Block latestBlock = blockChain.get(blockChain.size()-1);
		//随机数
		int nonce = 1;
		String hash = "";
		while(true){
			hash = CryptoUtil.SHA256(latestBlock.getHash()+JSON.toJSONString(txs)+nonce);
			if(hash.startsWith("0000")){
				System.out.println("======计算结果正确，计算次数为："+nonce+",hash:"+hash);
				break;
			}
			nonce++;
			System.out.println("计算错误，hash:"+hash);
		}
		
		//解出难题，可以构造新区块并加入到区块链
		Block newBlock = new Block(latestBlock.getIndex()+1, System.currentTimeMillis(), txs, nonce, latestBlock.getHash(), hash);
		blockChain.add(newBlock);
		System.out.println("挖矿后的区块链："+JSON.toJSONString(blockChain));
	}
	
	
	
	
	

	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}


	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public List<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}


	public int getNonce() {
		return nonce;
	}


	public void setNonce(int nonce) {
		this.nonce = nonce;
	}


	public String getPreviousHash() {
		return previousHash;
	}


	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	
	
}
