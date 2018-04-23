package net.aooms.core.web.render;

import java.io.InputStream;

/**
 * 渲染工厂
 * Created by cccyb on 2018-04-20
 */
public class RenderFactory {

    private static final RenderFactory renderFactory = new RenderFactory();

    public static RenderFactory me(){
        return renderFactory;
    }

    /**
     * 获取JSONRender
     * @return
     */
    public IRender getJSONRender(){
       return new JSONRender();
    }

    /**
     * 获取HtmlRender
     * @return
     */
    public IRender getHtmlRender() {
        return new HtmlRender();
    }

    /**
     * 获取JavaScriptRender
     * @return
     */
    public IRender getJavaScriptRender() {
        return new JavaScriptRender();
    }

    /**
     * 获取TextRender
     * @return
     */
    public IRender getTextRender() {
        return new TextRender();
    }

    /**
     * 获取ImageRender
     * @return
     */
    public IRender getImageRender(String suffix) {
        return new ImageRender(suffix);
    }

    /**
     * 获取FileRender
     * @return
     */
    public IRender getFileRender(String fileName){
        return new FileRender(fileName);
    }


}