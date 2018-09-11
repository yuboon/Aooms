package net.aooms.core.web.render;

import javax.servlet.http.HttpServletResponse;

/**
 * js渲染
 * Created by 风象南(cheereebo) on 2018-04-20
 */
public class JavaScriptRender extends AbstractRender {

    public JavaScriptRender() {
        this.renderType = RenderType.JAVASCRIPT;
    }

    @Override
    public void render(HttpServletResponse response, Object value) throws Exception {
        response.setContentType(renderType.getContentType());
        //response.getWriter().write(String.valueOf(value));
        //this.flushAndClose(response);
        this.springMvcRender(value);
    }
}