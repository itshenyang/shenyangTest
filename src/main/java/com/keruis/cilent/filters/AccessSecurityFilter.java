package com.keruis.cilent.filters;

import com.keruis.cilent.utils.L;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Huan on 2017/2/8.
 */
public class AccessSecurityFilter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String servletPath = request.getServletPath();
        String contextPath = request.getContextPath();
        String remoteAddr = request.getRemoteAddr();
        String requestedSessionId = request.getRequestedSessionId();
        String requestURI = request.getRequestURI();
        String requestURL = request.getRequestURL().toString();
        String pathInfo = request.getPathInfo();
        String method = request.getMethod();
        L.w("servletPath=" + servletPath + ",\n" +
                "contextPath=" + contextPath + ";\n" +
                "remoteAddr=" + remoteAddr + ",\n" +
                "requestedSessionId=" + requestedSessionId + ";\n" +
                "requestURI=" + requestURI + ";\n" +
                "requestURL=" + requestURL + ";\n" +
                "method=" + method + ";\n" +
                "pathInfo=" + pathInfo);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

    }


}
