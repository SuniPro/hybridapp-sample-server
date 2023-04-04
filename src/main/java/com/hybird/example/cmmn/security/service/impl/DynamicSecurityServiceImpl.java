package com.hybird.example.cmmn.security.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hybird.example.cmmn.security.service.DynamicSecurityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.script.service.ScriptService;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("DynamicSecurityService")
public class DynamicSecurityServiceImpl implements DynamicSecurityService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="ScriptService")
	private ScriptService scriptService;
	
	@Resource(name = "DynamicSecurityDAO")
	private DynamicSecurityDAO dynamicSecurityDAO;
	
	@Value("${site.user.main}")
	private String userMain;
	
	@Override
	public String setSessionInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		
        // 기본은 NUll 또는 "" 처리
        String targetUrl = "";
        // 접속유저
        String userName = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // 접속유저의 권한
        @SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authority = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<GrantedAuthority> iter = authority.iterator();
        
		try {
			
			/* 사용자 부가정보 설정 */
			EgovMap userInfoMap = null;
				
			targetUrl = userMain;
			userInfoMap = dynamicSecurityDAO.selectSecurityUserInfo(userName);
			session.setAttribute(CmmnConstant.SESSION_USER, userInfoMap);

			// 권한에 따른 메뉴목록 설정
			List<?> menulAllList = null;
			// 사용자 나의 메뉴 목록
			List<?> myMenuList = null;
			// 권한에 따른 네비게이션 설정
			//List<?> menulNavi = null;
			
			while(iter.hasNext()) {
				
				GrantedAuthority grantedAuthority = iter.next();
				String authCd = String.valueOf(grantedAuthority);
				
				List<?> menuList = dynamicSecurityDAO.selectSecurityUserMenuList(authCd);
				
				//myMenuList = dynamicSecurityDAO.selectSecurityUserMyMenuList(authCd);
				
				//menulNavi = dynamicSecurityDAO.selectSecurityMenuNaviList(authCd);
				
				if (menulAllList == null) {
					menulAllList = menuList;
				}
			}
			
			/* 메뉴리스트 세팅 */
			session.setAttribute(CmmnConstant.SESSION_MENU, menulAllList);
			
			/* 나의 메뉴 목록 세팅 */
			if (myMenuList != null) {
				session.setAttribute(CmmnConstant.SESSION_MY_MENU, myMenuList);
			}
			
			/* 메뉴 네비게이션 세팅 */
			//session.setAttribute(CmmnConstant.SESSION_MENU_NAVI, menulNavi);
			// 접속한 사용자는 기본 Session Time 60분
			session.setMaxInactiveInterval(CmmnConstant.DEFAUL_SESSION_TIME_OUT);
			
			try {
				//dynamicSecurityDAO.insertLoginSuccess(userName, CmmnConstant.getRequestIP(request));
			} catch (Exception e) {
				logger.error("insertLoginSuccess", e);
			}
			
		} catch (Exception e) {
			logger.error("onAuthenticationSuccess", e);
		}
		
		return targetUrl;
	}
}
