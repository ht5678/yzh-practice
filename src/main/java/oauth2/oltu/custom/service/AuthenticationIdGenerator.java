package oauth2.oltu.custom.service;

/**
 * 15-6-20
 *
 * @author Shengzhao Li
 */

public interface AuthenticationIdGenerator {

    public String generate(String clientId, String username, String scope);

}