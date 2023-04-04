package com.hybird.example.cmmn.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.script.service.ScriptService;

public class CmmnAccessDeniedHandler implements AccessDeniedHandler {

	@Resource(name="ScriptService")
	private ScriptService scriptService;
	
	private String accessDeniedUrl;
	
	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}
	
	private String accessCSRFMissingUser;
	
	public void setAccessCSRFMissingUser(String accessCSRFMissingUser) {
		this.accessCSRFMissingUser = accessCSRFMissingUser;
	}
	
	private String accessCSRFMissingMngr;
	
	public void setAccessCSRFMissingMngr(String accessCSRFMissingMngr) {
		this.accessCSRFMissingMngr = accessCSRFMissingMngr;
	}
	

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
		
		exception.printStackTrace();

		if (CmmnConstant.REQUEST_IS_AJAX.equals(request.getHeader(CmmnConstant.REQUEST_AJAX_CALL))) {

			// CSRF Token이 없을때 세션이 종료
			if (exception instanceof MissingCsrfTokenException) {
				// 로그인 요청시 없다면 901 코드를 던짐 - 그외 모든 예외는 403을 보냄
				if (request.getRequestURL().indexOf("j_spring_security_check") > -1) {
					response.sendError(CmmnConstant.REQUEST_CSRF_MISSING);
				} else {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
				// MissingCsrfTokenException 로그인으로 보내기위해 무조건 901을 던짐
				//response.sendError(CmmnConstant.REQUEST_CSRF_MISSING);
				
			// 전송된 토큰이 다른 경우 - 필요시 구현
			//	} else if (exception instanceof InvalidCsrfTokenException) {
			//	}
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
			
		} else {
			// 세션이 종료
			if (exception instanceof MissingCsrfTokenException) {

				try {
					request.getRequestDispatcher(accessCSRFMissingUser).forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
					throw new IOException();
				}
			// 전송된 토큰이 다른 경우 - 필요시 구현
			//} else if (exception instanceof InvalidCsrfTokenException) {
			//}
			} else {
				
				try {
					request.getRequestDispatcher(accessDeniedUrl).forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
					throw new IOException();
				}
			}
		}
	}
}
