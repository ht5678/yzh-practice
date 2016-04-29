package zookeeper.zkclient.mastersel;

import java.io.Serializable;

/**
 * 
 * @author sdwhy
 *
 */
public class RunningData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1144137484595349801L;

	private long cid;
	
	private String name;

	

	public long getCid() {
		return cid;
	}


	public void setCid(long cid) {
		this.cid = cid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
