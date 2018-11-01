/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.aooms.core.web.interceptor;

import com.baomidou.kisso.SSOAuthorization;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.security.token.SSOToken;
import net.aooms.core.exception.AoomsExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 授权认证
 * Created by 风象南(yuboon) on 2018/9/7
 */
public class PermissionInterceptor extends AoomsAbstractInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    /*
     * 系统权限授权接口
     */
    private SSOAuthorization authorization = new DefaultSSOAuthorization();

    /*
     * 非法请求提示 URL
     */
    private String illegalUrl;

    /**
     * 无注解情况下，设置为true，不进行权限验证
     */
    private boolean nothingAnnotationPass = false;

    public PermissionInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    /**
     * <p>
     * 用户权限验证
     * </p>
     * <p>
     * 方法拦截 controller 处理之前进行调用。
     * </p>
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            SSOToken token = SSOHelper.attrToken(request);
            if (token == null) {
                return true;
            }

			/*
             * 权限验证合法
			 */
            if (isVerification(request, handler, token)) {
                return true;
            }

			/*
             * 无权限访问
			 */
			try{
                return unauthorizedAccess(request, response);
            }catch (Exception e){
			    throw AoomsExceptions.create(e.getMessage(),e);
			}
        }

        return true;
    }


    /**
     * <p>
     * 判断权限是否合法，支持 1、请求地址 2、注解编码
     * </p>
     *
     * @param request
     * @param handler
     * @param token
     * @return
     */
    protected boolean isVerification(HttpServletRequest request, Object handler, SSOToken token) {
        /*
         * URL 权限认证
		 */
        if (SSOConfig.getInstance().isPermissionUri()) {
            String uri = request.getRequestURI();
            if (uri == null || this.getAuthorization().isPermitted(token, uri)) {
                return true;
            }
        }

		/*
		 * 注解权限认证
		 */
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Permission pm = method.getAnnotation(Permission.class);
        if (pm != null) {
            if (pm.action() == Action.Skip) {
                /**
                 * 忽略拦截
                 */
                return true;
            } else if (!"".equals(pm.value()) && this.getAuthorization().isPermitted(token, pm.value())) {
                /**
                 * 权限合法
                 */
                return true;
            }
        }
		/*
		 * 非法访问
		 */
        return false;
    }


    /**
     * <p>
     * 无权限访问处理，默认返回 403  ，illegalUrl 非空重定向至该地址
     * </p>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean unauthorizedAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" request 403 url: " + request.getRequestURI());
        if (HttpUtil.isAjax(request)) {
			/* AJAX 请求 403 未授权访问提示 */
            HttpUtil.ajaxStatus(response, 403, "ajax Unauthorized access.");
        } else {
			/* 正常 HTTP 请求 */
            if (this.getIllegalUrl() == null || "".equals(this.getIllegalUrl())) {
                response.sendError(403, "Forbidden");
            } else {
                response.sendRedirect(this.getIllegalUrl());
            }
        }
        return false;
    }


    public SSOAuthorization getAuthorization() {
        return authorization;
    }

    public String getIllegalUrl() {
        return illegalUrl;
    }


}


class DefaultSSOAuthorization implements  SSOAuthorization{

    @Override
    public boolean isPermitted(SSOToken token, String permission) {
        return false;
    }
}
