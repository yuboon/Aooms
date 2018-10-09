package net.aooms.core.web;

import net.aooms.core.databoss.DataResult;
import net.aooms.core.databoss.DataResultStatus;
import net.aooms.core.web.annotation.ClearInterceptor;
import net.aooms.core.web.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 全局异常处理
 * Created by 风象南(cheereebo) on 2018/9/7
 */
@Controller
@ClearInterceptor(LoginInterceptor.class)
public class AoomsGlobalErrorController extends BasicErrorController {

    private static final String ERROR_PATH = "/error";

    private Logger logger = LoggerFactory.getLogger(getClass());

    public AoomsGlobalErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    /**
     * 覆盖默认的Json响应
     */
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> model = getErrorAttributes(request, true);
        HttpStatus status = getStatus(request);

        DataResult ret = new DataResult();
        DataResultStatus rsStatus = ret.getStatus();
        rsStatus.setSuccess(false);
        rsStatus.setMsg("");
        rsStatus.setError(String.valueOf(model.get("message")));
        rsStatus.setCode(status.value());
        rsStatus.setTrace(String.valueOf(model.get("trace")));
        return new ResponseEntity<Map<String, Object>>(ret.getData(), status);
    }

    /**
     * 覆盖默认的HTML响应
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        //请求的状态
        HttpStatus status = getStatus(request);
        //isIncludeStackTrace(request, MediaType.TEXT_HTML)

        // message
        // status
        // trace
        // path
        // timestamp
        // error

        // 包含异常堆栈信息
        Map<String, Object> model = getErrorAttributes(request, true);
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);

        //指定自定义的视图
        return new ModelAndView("/error.html", model);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

} 