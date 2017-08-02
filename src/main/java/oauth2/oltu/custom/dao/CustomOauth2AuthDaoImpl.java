package oauth2.oltu.custom.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import oauth2.oltu.simple.model.ClientModel;

/**
 * 
 * @author yuezh2   2017年7月31日 下午5:52:10
 *
 */
@Component
public class CustomOauth2AuthDaoImpl implements CustomOauth2AuthDao {
	
	@Resource(name="otemplate")
	private JdbcTemplate template;
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(CustomOauth2AuthDaoImpl.class);
	
	/** 通过clientId查询设备信息 */
	private static final String QUERY_CLIENT_BY_ID = "select * from oauth_simple.oauth_client_details where client_id=?";

	
	/**
	 * 通过设备id查询
	 * @param clientId
	 * @return
	 */
	public ClientModel queryByClientId(String clientId){
		final ClientModel model = new ClientModel();
		
		if(StringUtils.isEmpty(clientId)){
			return model;
		}
		
		try{
			template.query(QUERY_CLIENT_BY_ID, new Object[]{clientId}, new ResultSetExtractor<Integer>(){

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					while(rs.next()){
						model.setClientId(rs.getString("client_id"));
						model.setClientName(rs.getString("client_name"));
						model.setRedirectUri(rs.getString("redirect_uri"));
					}
					return null;
				}
				
			});
		}catch(Exception e){
			LOGGER.error(String.format("the error occured:%s", e.getMessage()), e);
		}
		return model;
	}
	
	
}
