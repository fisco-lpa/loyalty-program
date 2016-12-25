package com.fiscolpa.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	protected FilterConfig filterConfig;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hres = (HttpServletResponse) res;
		String requestUrl = hreq.getRequestURI();
		if (requestUrl.indexOf("/registerUser") > -1 || requestUrl.indexOf("/doLogin") > -1 || requestUrl.indexOf("/static") > -1) {
			chain.doFilter(req, res);
		} else {
			Object temp = hreq.getSession().getAttribute("user");
			if (temp == null) {
				hres.sendRedirect(hreq.getContextPath() + "/static/homePage/login.html");
			} else {
				chain.doFilter(req, res);
			}
		}
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void init(FilterConfig config) {
		this.filterConfig = config;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	
}