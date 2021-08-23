package com.stefanini.taskmanager.server.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@javax.servlet.annotation.WebFilter(urlPatterns = "/")
public class CustomFilter implements javax.servlet.Filter {
  @Override
  public void init(FilterConfig filterConfig) {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    filterChain.doFilter(servletRequest, httpResponse);
  }

  @Override
  public void destroy() {}
}
