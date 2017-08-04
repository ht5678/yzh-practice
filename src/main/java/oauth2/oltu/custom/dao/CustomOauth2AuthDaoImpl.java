package oauth2.oltu.custom.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import oauth2.oltu.custom.model.AccessToken;
import oauth2.oltu.custom.model.ClientDetails;
import oauth2.oltu.custom.model.OauthCode;
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
	/** 查询code */
	private static final String QUERY_CODE = "select * from oauth_simple.oauth_code where code = ? and client_id = ?";
	/** 查询refreshtoken */
	private static final String LOAD_ACCESSTOKEN_BY_REFRESHTOKEN = "select * from oauth_simple.oauth_code where code = ? and client_id = ?";
	/** 查询refreshtoken */
	private static final String FIND_OAUTHCODE_BY_USERNAMECLIENTID = "select * from oauth_simple.oauth_code where username = ? and client_id = ?";
	/** 删除code */
	private static final String DELETE_OAUTH_CODE = "delete from oauth_simple.oauth_code where code = ? and client_id = ? and username = ?";
	/** 生成oauthcode */
	private static final String SAVE_OAUTH_CODE = "insert into oauth_simple.oauth_code(code,username,client_id) values (?,?,?)";
	/** 删除oauthcode */
	private static final String DELETE_OAUTH_CODE_BYCODE = "delete from oauth_simple.oauth_code where code = ?";
	/** 查询accesstoken */
	private static final String FIND_ACCESS_TOKEN = "select * from oauth_access_token where client_id = ? and username = ? and authentication_id = ?";
	/** 删除accesstoken */
	private static final String DELETE_ACCESS_TOKEN = "delete from oauth_access_token where client_id = ? and username = ? and authentication_id = ?";
	/** 新增accesstoken */
	private static final String INSERT_ACCESS_TOKEN = "insert into oauth_access_token(token_id,token_expired_seconds,authentication_id,username,client_id,token_type,refresh_token_expired_seconds,refresh_token) values (?,?,?,?,?,?,?,?)";
	
	
	
	/**
	 * 新增accesstoken
	 * @param accessToken
	 * @return
	 */
	public int saveAccessToken(final AccessToken accessToken) {
        return template.update(INSERT_ACCESS_TOKEN, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, accessToken.tokenId());
                ps.setInt(2, accessToken.tokenExpiredSeconds());
                ps.setString(3, accessToken.authenticationId());

                ps.setString(4, accessToken.username());
                ps.setString(5, accessToken.clientId());
                ps.setString(6, accessToken.tokenType());

                ps.setInt(7, accessToken.refreshTokenExpiredSeconds());
                ps.setString(8, accessToken.refreshToken());
            }
        });
    }
	
	
	
	/**
	 * 删除accesstoken
	 * @param accessToken
	 * @return
	 */
	public int deleteAccessToken(final AccessToken accessToken) {
        return template.update(DELETE_ACCESS_TOKEN, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, accessToken.clientId());
                ps.setString(2, accessToken.username());
                ps.setString(3, accessToken.authenticationId());
            }
        });
    }
	
	
	
	/**
	 * 查询accesstoken
	 * @param clientId
	 * @param username
	 * @param authenticationId
	 * @return
	 */
	public AccessToken findAccessToken(String clientId, String username, String authenticationId) {
		final AccessToken oauthCode = new AccessToken();
        template.query(FIND_ACCESS_TOKEN, new Object[]{clientId, username, authenticationId} , new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				while(rs.next()){
					oauthCode.tokenId(rs.getString("token_id"))
	                .tokenExpiredSeconds(rs.getInt("token_expired_seconds"))
	                .authenticationId(rs.getString("authentication_id"))

	                .username(rs.getString("username"))
	                .clientId(rs.getString("client_id"))
	                .tokenType(rs.getString("token_type"))

	                .refreshTokenExpiredSeconds(rs.getInt("refresh_token_expired_seconds"))
	                .refreshToken(rs.getString("refresh_token"));

			        oauthCode.setCreateTime(rs.getTimestamp("create_time"));
				}
				return null;
			}
        	
        });
        return oauthCode;
    }
	
	
	
	
	/**
	 * 删除code
	 * @param code
	 * @return
	 */
	public int removeOauthCode(String code){
		int result = 0;
		try{
			result = template.update(DELETE_OAUTH_CODE_BYCODE, new Object[]{code});
		}catch(Exception e){
			LOGGER.error(String.format("the error occured:%s", e.getMessage()), e);
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 生成oauthcode
	 * @param oauthCode
	 * @return
	 */
	public int saveOauthCode(final OauthCode oauthCode) {
        return template.update(SAVE_OAUTH_CODE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, oauthCode.code());
                ps.setString(2, oauthCode.username());
                ps.setString(3, oauthCode.clientId());
            }
        });
    }
	
	
	
	/**
	 * 删除oauthcode
	 * @param oauthCode
	 * @return
	 */
	public int deleteOauthCode(final OauthCode oauthCode) {
        return template.update(DELETE_OAUTH_CODE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, oauthCode.code());
                ps.setString(2, oauthCode.clientId());
                ps.setString(3, oauthCode.username());
            }
        });
    }
	
	
	/**
	 * 通过用户名和clientid查找code
	 * @param username
	 * @param clientId
	 * @return
	 */
	public OauthCode findOauthCodeByUsernameClientId(String username , String clientId){
		final OauthCode retVal = new OauthCode();
		try{
			template.query(FIND_OAUTHCODE_BY_USERNAMECLIENTID, new Object[]{username , clientId }, new ResultSetExtractor<Integer>(){

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					while(rs.next()){
						retVal.clientId(rs.getString("client_id"))
				                .username(rs.getString("username"))
				                .code(rs.getString("code"));

						retVal.setCreateTime(rs.getTimestamp("create_time"));
					}
					return null;
				}
				
			});
		}catch(Exception e){
			LOGGER.error(String.format("the error occured:%s", e.getMessage()), e);
		}
		return retVal;
	}
	
	
	
	
	/**
	 * 查询token
	 * @param token
	 * @param clientId
	 * @return
	 */
	public AccessToken loadAccessTokenByRefreshToken(String token  , String clientId){
		final AccessToken retVal = new AccessToken();
		try{
			template.query(LOAD_ACCESSTOKEN_BY_REFRESHTOKEN, new Object[]{token,clientId}, new ResultSetExtractor<Integer>(){

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					while(rs.next()){
						retVal.tokenId(rs.getString("token_id"))
				                .tokenExpiredSeconds(rs.getInt("token_expired_seconds"))
				                .authenticationId(rs.getString("authentication_id"))

				                .username(rs.getString("username"))
				                .clientId(rs.getString("client_id"))
				                .tokenType(rs.getString("token_type"))

				                .refreshTokenExpiredSeconds(rs.getInt("refresh_token_expired_seconds"))
				                .refreshToken(rs.getString("refresh_token"));

				        retVal.setCreateTime(rs.getTimestamp("create_time"));
					}
					return null;
				}
				
			});
		}catch(Exception e){
			LOGGER.error(String.format("the error occured:%s", e.getMessage()), e);
		}
		return retVal;
	}
	
	
	
	/**
	 * 查询code
	 * @param code
	 * @param clientId
	 * @return
	 */
	public OauthCode loadOauthCode(String code , String clientId){
		final OauthCode retVal = new OauthCode();
		try{
			template.query(QUERY_CODE, new Object[]{code,clientId}, new ResultSetExtractor<Integer>(){

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					while(rs.next()){
						retVal.clientId(rs.getString("client_id"))
				                .username(rs.getString("username"))
				                .code(rs.getString("code"));
					}
					return null;
				}
				
			});
		}catch(Exception e){
			LOGGER.error(String.format("the error occured:%s", e.getMessage()), e);
		}
		return retVal;
	}
	
	
	
	
	/**
	 * 
	 * @param clientId
	 * @return
	 */
	public ClientDetails loadClientDetails(String clientId) {
		final ClientDetails details = new ClientDetails();
		try{
			template.query(QUERY_CLIENT_BY_ID, new Object[]{clientId}, new ResultSetExtractor<Integer>(){

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					while(rs.next()){
				        details.setClientId(rs.getString("client_id"));
				        details.setClientSecret(rs.getString("client_secret"));

				        details.setName(rs.getString("client_name"));
				        details.setClientUri(rs.getString("client_uri"));
//				        details.setIconUri(rs.getString("client_icon_uri"));

//				        details.resourceIds(rs.getString("resource_ids"));
//				        details.scope(rs.getString("scope"));
//				        details.grantTypes(rs.getString("grant_types"));

				        details.setRedirectUri(rs.getString("redirect_uri"));
//				        details.roles(rs.getString("roles"));
				        details.accessTokenValidity(rs.getInt("access_token_validity"));

				        details.refreshTokenValidity(rs.getInt("refresh_token_validity"));
				        details.setDescription(rs.getString("description"));
				        details.createTime(rs.getTimestamp("create_time"));

				        details.archived(rs.getBoolean("archived"));
				        details.trusted(rs.getBoolean("trusted"));
					}
					return null;
				}
				
			});
		}catch(Exception e){
			LOGGER.error(String.format("the error occured:%s", e.getMessage()), e);
		}
		return details;
	}

	
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
