package net.aooms.core.authentication;

import sun.security.provider.ConfigFile;

import javax.security.auth.AuthPermission;
import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.security.PrivilegedAction;

/**
 * 类描述
 * Created by 风象南(yuboon) on 2018/11/13
 *
 * https://blog.csdn.net/flowingflying/article/details/74837413
 * 启用配置文件：
 * -Djava.security.auth.login.config=D:/JAAS.conf
 *
 */
public class LoginManager {

    static {
    }

    public static boolean login(HttpServletRequest request,String loginConfig){
        try {


            System.setProperty("java.security.auth.login.config",loginConfig);

            String username = "admin";//request.getParameter("username");
            String password = "password";//request.getParameter("password");
            // 此处指定了使用配置文件的 "Sample" 证模块，对应的实现类为SampleLoginModule
            LoginContext lc = new LoginContext("Sample",new SampleCallbackHandler(username, password));
            lc.login();// 如果验证失败会抛出异常
            System.err.println("login success");

            PrivilegedAction action = new SampleAction();
            Subject.doAs(new Subject(),action);

            return true;
        } catch (LoginException e) {
            e.printStackTrace();
            return false;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    // test
    public static void main(String[] args) {
        login(null,"D:\\dsworkspace\\idea\\Aooms\\aooms-core\\src\\main\\java\\net\\aooms\\core\\authentication\\jaas.conf");
    }

}
