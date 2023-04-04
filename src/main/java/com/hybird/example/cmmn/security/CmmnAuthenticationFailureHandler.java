package com.hybird.example.cmmn.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.security.service.impl.DynamicSecurityDAO;

public class CmmnAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "DynamicSecurityDAO")
	private DynamicSecurityDAO dynamicSecurityDAO;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
		try {
//			dynamicSecurityDAO.insertLoginFailure(exception.getMessage(), CmmnConstant.getRequestIP(request));
			String memberSt = dynamicSecurityDAO.selectAuthenticationUserSttu(exception.getMessage());
			
			/* 로그인 실패가 Ajax 호출이면 */
			if (CmmnConstant.REQUEST_IS_AJAX.equals(request.getHeader(CmmnConstant.REQUEST_AJAX_CALL))) {
				if("UST98".equals(memberSt)) {
					// 차단 회원 
					response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				}else {
					// 403을 던짐
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
				/* 로그인 실패가 Form submit 호출이면 */
			} else {
				request.setAttribute(CmmnConstant.REQUEST_ERROR_KEY, exception.getMessage());
				request.getRequestDispatcher("/error/authFailure.do").forward(request, response);
			}
		} catch (Exception e) {
			logger.error("onAuthenticationFailure", e);
		}
		
		
	}
}
