package com.hybird.example.cmmn;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class SessionUtil {

	 /**
     * attribute 값을 가져 오기 위한 method
     *
     * @param String  attribute key name
     * @return String attribute obj
     */
    public static String getAttributeStr(String userScope, String name) {
    	
    	String resultStr = "";
    	
    	try {
		    	Object sessionObj = RequestContextHolder
		    							   .getRequestAttributes()
		    							   .getAttribute(userScope, RequestAttributes.SCOPE_SESSION);
		    	
		    	if (sessionObj != null && !"".equals(sessionObj)) {
		    		
			    	EgovMap memberInfo = (EgovMap) sessionObj;
	
			    	if (memberInfo.containsKey(name)) {
			    		resultStr = String.valueOf(memberInfo.get(name));
			    	}
		    	}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
        return resultStr;
    }
    
    /**
     * attribute 값을 가져 오기 위한 method
     *
     * @param String  attribute key name
     * @return String attribute obj
     */
    public static String getDirectAttribute(String name) {
    	
    	String resultStr = "";
    	
    	try {
    	
		    	Object sessionObj = RequestContextHolder
		    							   .getRequestAttributes()
		    							   .getAttribute(name, RequestAttributes.SCOPE_SESSION);
		    	
		    	if (sessionObj != null && !"".equals(sessionObj)) {
		    		resultStr = String.valueOf(sessionObj);
		    	}

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
        return resultStr;
    }
    
    //아이피 조회
    public static String getUserIp() throws Exception {

    	HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		return ip;
    }
    
    public static boolean isEmpty(CharSequence cs) {
    	return cs == null || cs.length() == 0;
    }
    
    /**
     * attribute 설정 method
     *
     * @param String  attribute key name
     * @param Object  attribute obj
     * @return void
     
    public static void setAttribute(String name, Object object) throws Exception {
        RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
    }
    */

    /**
     * 설정한 attribute 삭제
     *
     * @param String  attribute key name
     * @return void
     
    public static void removeAttribute(String name) throws Exception {
        RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }
     */
    
    /**
     * session id
     *
     * @param void
     * @return String SessionId 값
    public static String getSessionId() throws Exception  {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }
     */
}
