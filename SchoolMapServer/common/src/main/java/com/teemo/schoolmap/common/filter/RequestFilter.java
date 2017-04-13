package com.teemo.schoolmap.common.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 10:48
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
@WebFilter(urlPatterns = "/api/*")
public class RequestFilter implements Filter {
    public static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        requestHolder.set((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
