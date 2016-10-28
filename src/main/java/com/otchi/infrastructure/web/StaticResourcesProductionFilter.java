package com.otchi.infrastructure.web;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This filter is used in production, to serve static resources generated by "gulp build".
 */
public class StaticResourcesProductionFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String requestURI = httpRequest.getRequestURI();
        requestURI = substringAfter(requestURI, contextPath);
        if (requestURI.equals("/")) {
            requestURI = "/index.html";
        }
        String newURI = "/dist" + requestURI;
        request.getRequestDispatcher(newURI).forward(request, response);
    }

    private String substringAfter(String str, String separator) {
        int pos = str.indexOf(separator);
        return pos == -1 ? "" : str.substring(pos + separator.length());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to initialize
    }

    @Override
    public void destroy() {
        // Nothing to destroy
    }

}