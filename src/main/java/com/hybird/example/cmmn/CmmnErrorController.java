package com.hybird.example.cmmn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hybird.example.cmmn.script.service.ScriptService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CmmnErrorController extends CmmnController {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="ScriptService")
	private ScriptService scriptService;
	
	
	@RequestMapping("/error/errorEtc.do") 
	public String moveEtcErrorPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String retPage = "";
		
		retPage = "userError:/userErrorEtc";
		
		logger.info("############################ request moveEtcErrorPage :: " + getPreUrl(request, response));
		
		return retPage;
	}
	
	@RequestMapping("/error/error404.do") 
	public String move404ErrorPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String retPage = "";
		
		retPage = "userError:/userError404";

		logger.info("############################ request move404ErrorPage :: " + getPreUrl(request, response));
		
		return retPage;
	}
	
	@RequestMapping("/error/error500.do") 
	public String move500ErrorPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String retPage = "";
		
		retPage = "userError:/userError500";

		logger.info("############################ request move500ErrorPage :: " + getPreUrl(request, response));
		
		return retPage;

	}
	
	@RequestMapping("/error/errorDenied.do") 
	public String moveDeniedErrorPage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String retPage = "";
		
		retPage = "userError:/userErrorDenied";

		logger.info("############################ request moveDeniedErrorPage :: " + getPreUrl(request, response));
		
		return retPage;
	}
	
	@RequestMapping("/error/authFailure.do") 
	public String authFailure(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String retPage = "";
		
		retPage = "userError:/userErrorEtc";

		logger.info("############################ request authFailure :: " +  getPreUrl(request, response));
		
		return retPage;
	}

	private String getPreUrl(HttpServletRequest request, HttpServletResponse response) {
		
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if (savedRequest == null) {
			return "";
		} 
		
		return savedRequest.getRedirectUrl();
	}
	
	@RequestMapping("/error/errorView.do")
	public String errorView(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		return "user:/error/errorView";
	}
}
