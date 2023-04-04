package com.hybird.example.cmmn.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.script.service.ScriptService;
import com.hybird.example.cmmn.security.service.User;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class CmmnUserDetailsService implements UserDetailsService {

	//private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name="ScriptService")
	private ScriptService scriptService;
	
	@Resource(name="DynamicSecurityDAO")
	private DynamicSecurityDAO dynamicSecurityDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		
		try {

			EgovMap userInfoMap = null;
			EgovMap roleMap = null;
			
			userInfoMap = dynamicSecurityDAO.selectAuthenticationUser(username);
			
			//차단회원 확인 
			String memberSt = dynamicSecurityDAO.selectAuthenticationUserSttu(username);
			if("UST98".equals(memberSt)) {
				throw new UsernameNotFoundException(username);
			}
			
			user = new User();
			user.setUsername(String.valueOf(userInfoMap.get(CmmnConstant.USER_DETAILS_NAME)));
			user.setPassword(String.valueOf(userInfoMap.get(CmmnConstant.USER_DETAILS_PWD)));
			
			roleMap = dynamicSecurityDAO.selectSecurityUserRole(username);
			
			if (roleMap != null && !roleMap.isEmpty()) {
				Collection<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
				authority.add(new SimpleGrantedAuthority(String.valueOf(roleMap.get(CmmnConstant.USER_DETAILS_ROLE))));
				user.setAuthorities(authority);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException(username);
		}
		
		return user;
	}
}
