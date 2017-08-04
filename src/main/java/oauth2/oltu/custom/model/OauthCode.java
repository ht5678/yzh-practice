package oauth2.oltu.custom.model;

import java.util.Date;

/**
 * 
 * @author yuezh2   2017年8月3日 下午2:58:53
 *
 */
public class OauthCode {

    private static final long serialVersionUID = 7861853986708936572L;
    private String code;

    private String username;

    private String clientId;
    
    private Date createTime;

    public OauthCode() {
    }


    public String code() {
        return code;
    }

    public OauthCode code(String code) {
        this.code = code;
        return this;
    }

    public String username() {
        return username;
    }

    public OauthCode username(String username) {
        this.username = username;
        return this;
    }

    public String clientId() {
        return clientId;
    }

    public OauthCode clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
