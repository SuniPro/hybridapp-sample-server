package com.hybird.example.cmmn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.hybird.example.cmmn.security.service.impl.CmmnUserDetailsService;
import com.hybird.example.cmmn.security.service.impl.CmmnUserPassword;

public class CmmnAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CmmnUserDetailsService cmmnUserDetailsService;
	
	@Autowired
	private CmmnUserPassword cmmnUserPassword;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
	
        UserDetails userDtl = cmmnUserDetailsService.loadUserByUsername(username);
        
        if(userDtl == null) {
            throw new BadCredentialsException(username);
        }
        
    	int isPwd = cmmnUserPassword.loadUserByPasswordChk(username, password);
    	
    	if (isPwd < 1) {
    		throw new BadCredentialsException(username);
    	}
    	
    	return new UsernamePasswordAuthenticationToken(userDtl.getUsername(), userDtl.getPassword(), userDtl.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
