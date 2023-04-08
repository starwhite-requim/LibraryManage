package com.se.librarymanagesystem.filter;

import com.se.librarymanagesystem.common.CurrentId;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;

@WebFilter(filterName = "threadLocalFilter",urlPatterns = "/*")
public class threadLocalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute("Employee")!=null){
            //已经登陆，则把ID存入ThreadLocal中
            Long id = (Long) session.getAttribute("Employee");
            CurrentId.SetId(id);
        }
        filterChain.doFilter(request,response);
        return;
    }
}
