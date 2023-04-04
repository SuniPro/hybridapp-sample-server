package com.hybird.example.user.menu.service;

import java.util.List;

public interface MenuService {

	/**
	 * 사용자 앱 메뉴 목록 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> getMobileMenuList ( MenuVO vo) throws Exception;
	

	
}
