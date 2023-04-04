package com.hybird.example.cmmn.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS");
//		httpResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With, X-Auth-Token");
		httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
