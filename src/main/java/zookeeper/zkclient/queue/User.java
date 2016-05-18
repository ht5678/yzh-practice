package zookeeper.zkclient.queue;

import java.io.Serializable;

/**
 * 
 * @author yuezh2   2016年5月17日 下午8:21:52
 *
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8125074243886278880L;

	private String name;
	
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + "]";
	}
	
	
}
