package zookeeper.subscribe;



/**
 * 服务器节点信息
 * @author yuezh2   2016年4月20日 上午12:49:15
 *
 */
public class ServerData {

	/** 节点id */
	private Integer id;
	/** 节点的ip地址 */
	private String address;
	/** 节点名称 */
	private String name;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ServerData [id=" + id + ", address=" + address + ", name=" + name + "]";
	}
	
	
	
}
