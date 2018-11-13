package net.aooms.core.authentication;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.enums.TokenOrigin;
import com.baomidou.kisso.security.token.SSOToken;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsContext;

import javax.security.auth.callback.CallbackHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Login 总控
 * Created by 风象南(yuboon) on 2018/11/13
 */
public class LoginContext {

    private LoginService loginService;
    private String cacheGroup = SSOAuthentication.DEFAULT_CACHE_GROUP_NAME;
    private Long timeout = SSOAuthentication.DEFAULT_CACHE_TIMEOUT;

    public LoginContext(LoginService loginService) {
        this.loginService = loginService;
    }

    public LoginContext(LoginService loginService, String cacheGroup, Long timeout) {
        this.loginService = loginService;
        this.cacheGroup = cacheGroup;
        this.timeout = timeout;
    }

    public AuthenticationInfo login(String username, String password){
        AuthenticationInfo authenticationInfo = loginService.login(username,password);

        //boolean success = true;
        if(authenticationInfo == null){
            // 返回一个Ghost用户
            //authenticationInfo = new AuthenticationInfo().ghost();
            //success = false;
            return null;
        }


        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(new DefaultClaims());
        jwtBuilder.claim(SSOAuthentication.CACHE_GROUP_PLACEHOLDER, cacheGroup);
        jwtBuilder.claim(SSOAuthentication.CACHE_TIMEOUT_PLACEHOLDER, timeout);
        SSOToken token = SSOToken.create(jwtBuilder)
                .setId(authenticationInfo.getSessionId())
                .setIssuer(Aooms.NAME)
                .setOrigin(TokenOrigin.HTML5)
                .setTime(System.currentTimeMillis());
        authenticationInfo.setToken(token.getToken());

        // 缓存
        cache(authenticationInfo);
        return authenticationInfo;
    }

    public boolean logout(){
        loginService.logout(SSOAuthentication.getAuthenticationInfo());
        return SSOAuthentication.removeAuthenticationInfo();
    }

    protected void cache(AuthenticationInfo authenticationInfo){
        SSOAuthentication.cacheAuthenticationInfo(cacheGroup,timeout,authenticationInfo);
    }

    public String getCacheGroup() {
        return cacheGroup;
    }

    public void setCacheGroup(String cacheGroup) {
        this.cacheGroup = cacheGroup;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
