package com.hybird.example.cmmn.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.util.StringUtils;

import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.script.service.ScriptService;

public class CmmnInvalidSessionStrategyFilter implements InvalidSessionStrategy {

	@SuppressWarnings("unused")
	private final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="ScriptService")
	private ScriptService scriptService;
	
	private String invalidSessionUser;

	public void setInvalidSessionUser(String invalidSessionUser) {
		this.invalidSessionUser = invalidSessionUser;
	}

	private String invalidSessionMngr;
	
	public void setInvalidSessionMngr(String invalidSessionMngr) {
		this.invalidSessionMngr = invalidSessionMngr;
	}
	
	/****
	 * Session Invalid를 감지하면 작동
	 */
	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// Ajax 대한 로직을 타고 들어올일이 없음 들어오면 안됨 Ajax는 Authentication에서 처리함 (403) - 혹시 몰라서 해둔거임
		// session-management invalid-session-url 설정하면 다른 설정이 깡그리 무시됨
        if (CmmnConstant.REQUEST_IS_AJAX.equals(request.getHeader(CmmnConstant.REQUEST_AJAX_CALL))) {

        	if (request.getSession() != null) {
            	
                HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
                SavedRequest savedRequest = httpSessionRequestCache.getRequest(request, response);
                
                if (savedRequest != null) {
                    httpSessionRequestCache.removeRequest(request, response);
                }
            }
            
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            
        } else {
        	
        	// 새로운 Session을 만들어서 Redirect
        	// 안만들어주면 심각한 오류가 납니다. Session 자체가 없으므로 이녀석이 미친듯이 돌아감
        	String queryStr = getRequestUrl(request);

            HttpSession session = request.getSession(true);
            
            if (!"".equals(queryStr) && queryStr != null) {
            	session.setAttribute(CmmnConstant.CHECK_QUERY_STRING, queryStr);
            }

            try {
				response.sendRedirect(invalidSessionUser);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IOException();
			}
        }
	}

	private String getRequestUrl(HttpServletRequest request) {
		
		String retString = "";
        String queryString = request.getQueryString();

        if (StringUtils.hasText(queryString)) {
        	if (queryString.indexOf("0") == -1) {
        		//retString = queryString;
        		retString = request.getParameter(CmmnConstant.CHECK_QUERY_STRING);
        	}
        } else {
        	
        	String uri = request.getRequestURI();
        	
        	if (uri.indexOf("logout") > -1) {
        		retString = CmmnConstant.SESSION_INVALID_CODE;
        	}
        }

        return retString;
    }
}

