package com.hybird.example.cmmn.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DynamicSecurityService {

	public String setSessionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
