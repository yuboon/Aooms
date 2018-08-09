package net.aooms.core.web.render;

import net.aooms.core.exception.AoomsException;
import net.aooms.core.web.AoomsContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 页面渲染
 * Created by cccyb on 2018-04-20
 */
public class ThymeleafRender extends AbstractRender {

    private ModelAndView mv;

    public ThymeleafRender(ModelAndView mv) {
        this.renderType = RenderType.HTML;
        this.mv = mv;
    }

    @Override
    public void render(HttpServletResponse response, Object value) throws  Exception{
        // get ApplicationContext
        ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(AoomsContextHolder.getRequest().getServletContext());

        // get ThymeleafViewResolver Bean
        ThymeleafViewResolver resolver = (ThymeleafViewResolver) ac1.getBean("thymeleafViewResolver");
        View view = resolver.resolveViewName(mv.getViewName(), AoomsContextHolder.getRequest().getLocale());
        view.render(mv.getModel(), AoomsContextHolder.getRequest(), AoomsContextHolder.getResponse());
    }
}