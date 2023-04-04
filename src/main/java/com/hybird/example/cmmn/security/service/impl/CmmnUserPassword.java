package com.hybird.example.cmmn.security.service.impl;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CmmnUserPassword {

	//private final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="DynamicSecurityDAO")
	private DynamicSecurityDAO dynamicSecurityDAO;
	
	public int loadUserByPasswordChk(String id,String pw) throws UsernameNotFoundException {
		int passwordChk ;
		try {
			if(pw.length() == 64) {
				//자동 로그인 시 암호화된 비밀번호 매칭
				passwordChk = dynamicSecurityDAO.selectSecurityUserPasswordEncryption(id,pw);
		    }else {
		    	//일반 비밀번호 매칭
		    	passwordChk = dynamicSecurityDAO.selectSecurityUserPassword(id,pw);
		    }
			
		} catch (Exception e) {
			//logger.error("loadUserByUsername", e);
			throw new UsernameNotFoundException(id);
		}
		
		return passwordChk;
	}
}
