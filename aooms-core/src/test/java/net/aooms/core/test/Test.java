package net.aooms.core.test;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 测试
 * Created by cccyb on 2018-03-06
 */
public class Test {

    public static void main(String[] args) throws URISyntaxException, MalformedURLException {
        URI url = new URI("xp://localhost:8080/aooms/get/findList");

        System.err.println(url.getHost());
        System.err.println(url.getPath());
        System.err.println(url.getPort());
        System.err.println(url.getScheme());
        System.err.println(url.getSchemeSpecificPart());
        System.err.println(url.getRawSchemeSpecificPart());
        System.err.println(url.getUserInfo());
        System.err.println(url.getRawUserInfo());
        System.err.println(url.getAuthority());
        System.err.println(url.getFragment());
        //URL URL = new URL("");

        URI uri = new URI("");

    }

}
