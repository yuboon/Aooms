package net.aooms.core.authentication;

/**
 * 类描述
 * Created by 风象南(yuboon) on 2018/11/13
 */

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

public class SampleLoginModule implements LoginModule {

    private boolean isAuthenticated = false;
    private CallbackHandler callbackHandler;
    private Subject subject;
    private SamplePrincipal principal;

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    public boolean login() throws LoginException {
        try {
            NameCallback nameCallback = new NameCallback("username");
            PasswordCallback passwordCallback = new PasswordCallback("password", false);
            final Callback[] calls = new Callback[]{nameCallback, passwordCallback};

            //  获取用户数据
            callbackHandler.handle(calls);
            String username = nameCallback.getName();
            String password = String.valueOf(passwordCallback.getPassword());

            //  TODO  验证，如：查询数据库、LDAP。。。

            if (true) {//  验证通过
                principal = new SamplePrincipal(username);
                isAuthenticated = true;
            } else {
                throw new LoginException("user or password is wrong");
            }

        } catch (IOException e) {
            throw new LoginException("no such user");
        } catch (UnsupportedCallbackException e) {
            throw new LoginException("login  failure");
        }
        return isAuthenticated;
    }

    /**
     * 验证后的处理,在Subject中加入用户对象
     */
    public boolean commit() throws LoginException {
        if (isAuthenticated) {
            subject.getPrincipals().add(principal);
        } else {
            throw new LoginException("Authentication failure");
        }
        return isAuthenticated;
    }

    public boolean abort() throws LoginException {
        return false;
    }

    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(principal);
        principal = null;
        return true;
    }
}
