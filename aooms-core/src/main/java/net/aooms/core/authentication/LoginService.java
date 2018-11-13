package net.aooms.core.authentication;

/**
 * 登录认证接口
 * Created by 风象南(yuboon) on 2018/11/1
 */
public interface LoginService {

    AuthenticationInfo login(String username,String password);

    boolean logout(AuthenticationInfo authenticationInfo);


}

