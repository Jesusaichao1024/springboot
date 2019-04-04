package com.sureal;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/25
 * @TIME: 14:23
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.sureal
 */
@Component
public class ManagerFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("true = 9011管理员经过过滤器" + true);
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return null;
        }
        String url = request.getRequestURL().toString();
        if ("login".indexOf(url) > 0) {
            System.out.println("url登录页面 = " + url);
            return null;
        }
        if (authorization != null) {
            requestContext.addZuulRequestHeader("Authorization", authorization);
            System.out.println("url = 通过验证" + url);
            return null;
        }
        //返回false终止执行
        requestContext.setSendZuulResponse(false);
        //返回状态码
        requestContext.setResponseStatusCode(401);
        //设置正文响应内容
        requestContext.getResponse().setContentType("application/json;charset=UTF-8");
        requestContext.setResponseBody("{\"code\": 20003, \"message\": \"无权访问\", \"data\": null, \"flag\": false}");
        return null;
    }
}
