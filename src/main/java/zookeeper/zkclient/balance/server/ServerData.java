package zookeeper.zkclient.balance.server;

import java.io.Serializable;

/**
 * 
 * @author yuezh2   2016年4月29日 下午8:09:50
 *
 */
public class ServerData implements Serializable,Comparable<ServerData>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6803487400198466535L;

	
	
	private Integer balance;
	
	private String host;
	
	private Integer port;

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "ServerData [balance=" + balance + ", host=" + host + ", port=" + port + "]";
	}


	@Override
	public int compareTo(ServerData o) {
		// TODO Auto-generated method stub
		return this.getBalance().compareTo(o.getBalance());
	}
	
	
}
