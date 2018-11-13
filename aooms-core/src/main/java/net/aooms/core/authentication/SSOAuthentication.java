package net.aooms.core.authentication;

import com.baomidou.kisso.common.SSOConstants;
import com.baomidou.kisso.security.token.SSOToken;
import net.aooms.core.Aooms;
import net.aooms.core.web.AoomsContext;
import net.oschina.j2cache.CacheObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * SSO认证用户操作
 * Created by 风象南(yuboon) on 2018/11/1
 */
public class SSOAuthentication {

    static final String CACHE_GROUP_PLACEHOLDER = "cacheGroup";
    static final String CACHE_TIMEOUT_PLACEHOLDER = "cacheTimeout";

    static final String DEFAULT_CACHE_GROUP_NAME = "AoomsUserGroup";  // 设定缓存组名称
    static final Long   DEFAULT_CACHE_TIMEOUT = 7200L;                  // 设定缓存过期时间 -- 单位秒

    public static AuthenticationInfo getAuthenticationInfo(){
        HttpServletRequest request = AoomsContext.getRequest();
        SSOToken ssoToken = (SSOToken) request.getAttribute(SSOConstants.SSO_TOKEN_ATTR);
        if(ssoToken == null){
            return null;
        }

        String sessionId = ssoToken.getId();
        String cacheGroup = ssoToken.getClaims().get(CACHE_GROUP_PLACEHOLDER,String.class);
        if(cacheGroup == null) return null;
        //long timeout = ssoToken.getClaims().get(CACHE_TIMEOUT,Long.class);

        return (AuthenticationInfo) Aooms.self().getJ2Cache().get(cacheGroup,sessionId).getValue();
    }

    public static boolean refreshAuthenticationInfo(AuthenticationInfo authenticationInfo){
        if(authenticationInfo == null) return true;
        HttpServletRequest request = AoomsContext.getRequest();
        SSOToken ssoToken = (SSOToken) request.getAttribute(SSOConstants.SSO_TOKEN_ATTR);
        if(ssoToken == null){
            return false;
        }

        String cacheGroup = ssoToken.getClaims().get(CACHE_GROUP_PLACEHOLDER,String.class);
        long cacheTimeout = ssoToken.getClaims().get(CACHE_TIMEOUT_PLACEHOLDER,Integer.class);
        if(cacheGroup == null) return false;

        CacheObject cacheObject = Aooms.self().getJ2Cache().get(cacheGroup,authenticationInfo.getSessionId());
        // 缓存对象不存在
        if(cacheObject == null){
            return false;
        }

        cacheAuthenticationInfo(cacheGroup, cacheTimeout ,authenticationInfo);
        return true;
    }

    static boolean cacheAuthenticationInfo(String cacheGroup, long timeout, AuthenticationInfo authenticationInfo){
        if(timeout == -1){
            Aooms.self().getJ2Cache().set(cacheGroup,authenticationInfo.getSessionId(),authenticationInfo);
            return true;
        }
        if(authenticationInfo != null){
            Aooms.self().getJ2Cache().set(cacheGroup,authenticationInfo.getSessionId(),authenticationInfo,timeout);
        }
        return true;
    }

    public static boolean removeAuthenticationInfo(){
        HttpServletRequest request = AoomsContext.getRequest();
        SSOToken ssoToken = (SSOToken) request.getAttribute(SSOConstants.SSO_TOKEN_ATTR);
        if(ssoToken == null){
            return false;
        }

        String cacheGroup = ssoToken.getClaims().get(CACHE_GROUP_PLACEHOLDER,String.class);
        if(cacheGroup == null) return false;

        Aooms.self().getJ2Cache().evict(cacheGroup, ssoToken.getId());
        return true;
    }
}

