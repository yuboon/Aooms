package net.aooms.core.web.render;

import net.aooms.core.web.AoomsContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletResponse;

/**
 * 页面渲染
 * Created by 风象南(yuboon) on 2018-04-20
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
        ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(AoomsContext.getRequest().getServletContext());

        // get ThymeleafViewResolver Bean
        ThymeleafViewResolver resolver = (ThymeleafViewResolver) ac1.getBean("thymeleafViewResolver");
        View view = resolver.resolveViewName(mv.getViewName(), AoomsContext.getRequest().getLocale());
        view.render(mv.getModel(), AoomsContext.getRequest(), AoomsContext.getResponse());
    }
}