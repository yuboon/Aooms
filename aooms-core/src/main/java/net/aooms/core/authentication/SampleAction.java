package net.aooms.core.authentication;

import java.security.PrivilegedAction;

/**
 * 类描述
 * Created by 风象南(yuboon) on 2018/11/13
 */
public class SampleAction implements PrivilegedAction {

    @Override
    public Object run() {
        System.err.println("do as");
        return null;
    }

}
