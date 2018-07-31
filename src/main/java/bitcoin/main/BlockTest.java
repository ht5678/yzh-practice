package bitcoin.main;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import bitcoin.model.Block;
import bitcoin.model.Transaction;
import bitcoin.util.CryptoUtil;

/**
 * 
 * 
 *         ┏┓　　　┏┓
 *      ┏┛┻━━━┛┻┓
 *      ┃　　　　　　　┃ 　
 *      ┃　　　━　　　┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　　　┃
 *      ┃　　　┻　　　┃
 *      ┃　　　　　　　┃
 *      ┗━┓　　　┏━┛
 *         ┃　　　┃　　　　　　　　　
 *         ┃　　　┃
 *         ┃　　　┗━━━┓
 *         ┃　　　　　　　┣┓
 *         ┃　　　　　　　┏┛
 *         ┗┓┓┏━┳┓┏┛
 *　　      ┃┫┫　┃┫┫
 *　        ┗┻┛　┗┻┛
 *
 *-------------------- 神兽保佑 永无bug --------------------
 * 
 * 
 * 
 * @author yuezh2   2018年7月31日 下午8:45:48
 *
 */
public class BlockTest {
	
	
	public static void main(String[] args) {
		//创建一个空的区块链
		List<Block> blockChain = new ArrayList<>();
		//生成创建区块
		Block block = new Block(1, System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1", "1");
		//加入创建区块到区块链
		blockChain.add(block);
		System.out.println(JSON.toJSONString(blockChain));
		
		//发送方钱包地址
		String sender = "sender_wallet";
		//接收方钱包地址
		String recipient = "recipient_wallet";
		
		//创建一个空的交易集合
		List<Transaction> txs = new ArrayList<>();
		System.out.println(sender+"钱包余额为:"+Block.getWalletBalance(blockChain, sender));
		
		//创建一个空的交易集合
		List<Transaction> txs1 = new ArrayList<>();
		//已经发生但未记账的交易记录,发送者给接收者3个比特币
		Transaction tx1 = new Transaction(CryptoUtil.UUID(),sender,recipient,3);
		//已经发生但未记账的交易记录,发送者给接收者1个比特币
		Transaction tx2 = new Transaction(CryptoUtil.UUID(),sender,recipient,1);
		txs1.add(tx1);
		txs1.add(tx2);
		
		//挖矿
		Block.mineBlock(blockChain, txs1, sender);
		System.out.println(sender+"钱包的余额为:"+Block.getWalletBalance(blockChain, sender));
		System.out.println(recipient+"钱包的余额为:"+Block.getWalletBalance(blockChain, recipient));
		
	}
	
	

}
