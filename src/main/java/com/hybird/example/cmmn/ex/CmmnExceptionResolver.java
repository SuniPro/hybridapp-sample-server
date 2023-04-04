package com.hybird.example.cmmn.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.hybird.example.cmmn.CmmnConstant;

public class CmmnExceptionResolver extends SimpleMappingExceptionResolver {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		logger.error(ex.getMessage());
		ex.printStackTrace();
		
		if (CmmnConstant.REQUEST_IS_AJAX.equals(request.getHeader(CmmnConstant.REQUEST_AJAX_CALL))) {
			logger.info("############################################## AJAX doResolveException");
			//String viewName = determineViewName(ex, request);
			ModelAndView mv = new ModelAndView("cmmn/error/errorMessage");
			mv.addObject(CmmnConstant.AJAX_ERROR_KEY, ex.getMessage());
			return mv;
		}
		
		return super.doResolveException(request, response, handler, ex);
	}
}
