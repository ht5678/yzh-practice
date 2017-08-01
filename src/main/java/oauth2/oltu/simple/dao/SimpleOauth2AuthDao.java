package oauth2.oltu.simple.dao;

import oauth2.oltu.simple.model.ClientModel;

/**
 * 
 * @author yuezh2   2017年7月31日 下午5:51:50
 *
 */
public interface SimpleOauth2AuthDao {
	
	
	/**
	 * 通过设备id查询
	 * @param clientId
	 * @return
	 */
	public ClientModel queryByClientId(String clientId);

}
