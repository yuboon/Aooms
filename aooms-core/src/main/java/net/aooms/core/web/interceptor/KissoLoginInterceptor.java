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

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.SSOConstants;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.security.token.SSOToken;
import com.baomidou.kisso.web.handler.SSOHandlerInterceptor;
import net.aooms.core.Vars;
import net.aooms.core.data.DataResult;
import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.property.PropertyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 登录权限验证
 */
public class KissoLoginInterceptor extends AoomsAbstractInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(KissoLoginInterceptor.class);
    private SSOHandlerInterceptor handlerInterceptor;

    public KissoLoginInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    /**
     * 登录权限验证
     * <p>
     * 方法拦截 Controller 处理之前进行调用。
     * </p>
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        /**
         * 处理 Controller 方法
         * <p>
         * 判断 handler 是否为 HandlerMethod 实例
         * </p>
         */
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            /**
             * 正常执行
             */
            String accessTokenName = PropertyObject.getInstance().getKissoProperty().getConfig().getAccessTokenName();
            SSOToken ssoToken = SSOHelper.getSSOToken(request);
            // 再次从参数列表获取token
            if(ssoToken == null){
                String tokenStr = request.getParameter(accessTokenName);
                if(tokenStr != null){
                    ssoToken = SSOToken.parser(tokenStr, false);
                }
            }

            if (ssoToken == null) {
                if (HttpUtil.isAjax(request)) {
                    /*
                     * Handler 处理 AJAX 请求
					 */
                    response.setCharacterEncoding(Vars.ENCODE);
                    DataResult dataResult = new DataResult();
                    dataResult.logicFailure(Vars.Status.AUTH_NO_LOGIN,"用户未登陆");

                    try{
                        response.getWriter().write(dataResult.toJsonStr());
                    }catch (IOException e){
                        throw AoomsExceptions.create(e.getMessage(),e);
                    }
                    //this.getHandlerInterceptor().preTokenIsNullAjax(request, response);
                    return false;
                } else {
					/*
					 * token 为空，调用 Handler 处理
					 * 返回 true 继续执行，清理登录状态并重定向至登录界面
					 */
					logger.info("用户未登陆,跳转登录页");
                    try {
                        SSOHelper.clearRedirectLogin(request, response);
                    } catch (IOException e){
                        throw AoomsExceptions.create(e.getMessage(),e);
                    }
                    return false;
                }
            } else {
				/*
				 * 正常请求，request 设置 token 减少二次解密
				 */
                request.setAttribute(SSOConstants.SSO_TOKEN_ATTR, ssoToken);
            }
        }

        /**
         * 通过拦截
         */
        return true;
    }


}
