package net.aooms.core.web.render;

import org.springframework.web.servlet.ModelAndView;

/**
 * 渲染工厂
 * Created by 风象南(cheereebo) on 2018-04-20
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
    public AbstractRender getJSONRender(){
       return new JSONRender();
    }

    /**
     * 获取HtmlRender
     * @return
     */
    public AbstractRender getHtmlRender() {
        return new HtmlRender();
    }

    /**
     * 获取JavaScriptRender
     * @return
     */
    public AbstractRender getJavaScriptRender() {
        return new JavaScriptRender();
    }

    /**
     * 获取TextRender
     * @return
     */
    public AbstractRender getTextRender() {
        return new TextRender();
    }

    /**
     * 获取ImageRender
     * @return
     */
    public AbstractRender getImageRender(String suffix) {
        return new ImageRender(suffix);
    }

    /**
     * 获取FileRender
     * @return
     */
    public AbstractRender getFileRender(String fileName){
        return new FileRender(fileName);
    }

    /**
     * 获取ModelAndViewRender
     * @return
     */
    public AbstractRender getThymeleafRender(ModelAndView mv){
        return new ThymeleafRender(mv);
    }


}