package com.hybird.example.user.login.service;

import java.util.List;

public interface LoginService {

	/***
	 * 기본보기 화면 목록 조회
	 * @return
	 * @throws Exception
	 */
	public List<?> searchBasicViewMenuList() throws Exception;
	
	/***
	 * 기본보기 네비게이션 목록 조회
	 * @return
	 * @throws Exception
	 */
	public List<?> searchBasicViewMenuNaviList() throws Exception;
	
}
