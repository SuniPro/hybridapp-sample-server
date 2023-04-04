package com.hybird.example.user.menu.service.impl;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.menu.service.MenuVO;

import java.util.List;

@OracleMapper("MenuDAO")
public interface MenuDAO {

	/**
	 * 사용자 앱 메뉴 목록 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<MenuVO> getMobileMenuList(MenuVO vo) throws Exception;

	
}
