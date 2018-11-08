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

import cn.hutool.http.HttpStatus;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.SSOConstants;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.security.token.SSOToken;
import net.aooms.core.AoomsVar;
import net.aooms.core.databoss.DataResult;
import net.aooms.core.exception.AoomsExceptions;
import net.aooms.core.property.PropertyObject;
import net.aooms.core.web.render.RenderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 登陆认证
 * Created by 风象南(yuboon) on 2018/9/7
 */
public class LoginInterceptor extends AoomsAbstractInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    public LoginInterceptor(String[] pathPatterns, String[] ignores) {
        super(pathPatterns, ignores);
    }

    /**
     * 登录权限验证
     * 方法拦截 controller 处理之前进行调用。
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        /**
         * 处理 controller 方法
         * <p>
         * 判断 handler 是否为 HandlerMethod 实例
         * </p>
         */
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            String errMsg = "未登录，访问受限";

            /**
             * 正常执行
             */
            String cookieName = PropertyObject.getInstance().getKissoProperty().getConfig().getCookieName();
            SSOToken ssoToken = null;
            try{
                ssoToken = SSOHelper.getSSOToken(request);
                // 再次从参数列表获取token
                if(ssoToken == null){
                    String tokenStr = request.getParameter(cookieName);
                    if(tokenStr != null){
                        ssoToken = SSOToken.parser(tokenStr, false);
                    }
                }
            }catch(Exception e){
                throw AoomsExceptions.create(e.getMessage(),e);
            }

            if (ssoToken == null) {
                if (HttpUtil.isAjax(request)) {
                    /*
                     * Handler 处理 AJAX 请求
					 */
                    response.setContentType(RenderType.JSON.getContentType());
                    response.setCharacterEncoding(AoomsVar.ENCODE);
                    DataResult dataResult = new DataResult();
                    dataResult.failure(HttpStatus.HTTP_UNAUTHORIZED, errMsg);
                    try{
                        response.getWriter().write(dataResult.toJsonStr());
                        response.getWriter().flush();
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
					logger.info(errMsg);
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
