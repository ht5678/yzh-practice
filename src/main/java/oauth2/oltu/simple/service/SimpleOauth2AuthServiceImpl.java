package oauth2.oltu.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oauth2.oltu.simple.dao.SimpleOauth2AuthDao;
import oauth2.oltu.simple.model.ClientModel;

/**
 * 
 * @author yuezh2   2017年7月31日 下午5:53:10
 *
 */
@Service
public class SimpleOauth2AuthServiceImpl implements SimpleOauth2AuthService {

	
	@Autowired
	private SimpleOauth2AuthDao dao;
	
	/**
	 * 通过设备id查询
	 * @param clientId
	 * @return
	 */
	public ClientModel queryByClientId(String clientId){
		return dao.queryByClientId(clientId);
	}
	
}
