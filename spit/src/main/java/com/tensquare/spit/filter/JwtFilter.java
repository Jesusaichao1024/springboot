package com.tensquare.spit.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/19
 * @TIME: 17:44
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.tensquare.user.filter
 */
@Component
public class JwtFilter extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("吐槽的拦截器JWT拦截啦");
        final String authorization = request.getHeader("Authorization");
        //判断头部信息的格式是否正确
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")) {
            //解析头部信息
            final String token = authorization.split(" ")[1];
            //取出角色信息
            Claims claims = jwtUtil.parseJWT(token);
            String roles = (String) claims.get("roles");
            //管理员
            if ("admin_role".equals(roles)) {
                request.setAttribute("admin_role", claims);
            }
            //用户
            if ("user_role".equals(roles)) {
                request.setAttribute("user_role", claims);
            }
        }
        return true;
    }
}
