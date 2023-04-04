package com.hybird.example.cmmn.security.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	private static final long serialVersionUID = 7888099440754803231L;
	
	private String userId;
	private String userPw;

	private List<?> authorities;
	private boolean isAccountNonExpired = true;
	private boolean isAccountNonLocked = true;
	private boolean isCredentialsNonExpired = true;
	private boolean isEnabled = true;
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) { 
		this.authorities = (List<?>) authorities; 
	}

	public void setPassword(String userPw) {
		this.userPw = userPw; 
	}
	
	@Override
	public String getPassword() {
		return userPw;
	}

	public void setUsername(String userId) { 
		this.userId = userId;
	}
	
	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
