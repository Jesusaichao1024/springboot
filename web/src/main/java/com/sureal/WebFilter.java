package com.sureal;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/21
 * @TIME: 18:52
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: com.sureal
 */
@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        //前置过滤器
        return "pre";
    }
    @Override
    public int filterOrder() {
        //优先级wei0 数字越大 优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器 true
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("9011执行该过滤器true = " + true);
        //获取HttpRequest 请求头信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //获取请求头信息
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
