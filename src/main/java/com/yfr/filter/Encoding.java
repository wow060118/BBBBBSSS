package com.yfr.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Koreyoshi on 2017/8/20.
 */
public class Encoding implements Filter {
    private String encoding="";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding=filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
