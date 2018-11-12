package net.aooms.core.web.interceptor;


import cn.hutool.core.date.DateUtil;
import net.aooms.core.Aooms;
import net.aooms.core.AoomsVar;
import net.aooms.core.web.service.ServiceConfiguration;
import net.aooms.core.web.service.ServiceConfigurations;
import net.oschina.j2cache.cache.support.util.SpringUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 框架执行日志打印
 * Created by 风象南(yuboon) on 2018-04-19
 */
public class ReportInterceptor extends AoomsAbstractInterceptor {

    private static final String title = "\nAooms-" + Aooms.VERSION + " Report -------------- ";

    public ReportInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        boolean isDevMode = Aooms.self().getPropertyObject().getAoomsProperty().isDevMode();
        if (isDevMode && handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod)handler;
            StringBuilder sb = new StringBuilder(title).append(DateUtil.now()).append(" --------------------------\n");
            sb.append("Url         : ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n");
            Class<?> cc = method.getBean().getClass();
            sb.append("Controller  : ").append(cc.getName()).append(".(").append(cc.getSimpleName()).append(".java:1)");
            sb.append("\nMethod      : ").append(method.getMethod().getName()).append("\n");

            /*
            Interceptor[] inters = action.getInterceptors();
            if (inters.length > 0) {
                sb.append("Interceptor : ");
                for (int i = 0; i < inters.length; i++) {
                    if (i > 0)
                        sb.append("\n              ");
                    Interceptor inter = inters[i];
                    Class<? extends Interceptor> ic = inter.getClass();
                    sb.append(ic.getName()).append(".(").append(ic.getSimpleName()).append(".java:1)");
                }
                sb.append("\n");
            }*/

            // print all parameters
            Enumeration<String> e = request.getParameterNames();
            if (e.hasMoreElements()) {
                sb.append("Parameter   : ");
                while (e.hasMoreElements()) {
                    String name = e.nextElement();
                    String[] values = request.getParameterValues(name);
                    if (values.length == 1) {
                        sb.append(name).append("=");
                            sb.append(values[0]);
                    } else {
                        sb.append(name).append("[]={");
                        for (int i = 0; i < values.length; i++) {
                            if (i > 0)
                                sb.append(",");
                            sb.append(values[i]);
                        }
                        sb.append("}");
                    }
                    sb.append("  ");
                }
                sb.append("\n");
            }
            sb.append("--------------------------------------------------------------------------------\n");
            System.out.println(sb.toString());
        }
        return true;
    }

}