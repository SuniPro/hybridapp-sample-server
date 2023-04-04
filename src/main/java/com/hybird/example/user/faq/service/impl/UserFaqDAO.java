package com.hybird.example.user.faq.service.impl;

import java.util.List;

import com.hybird.example.user.faq.service.UserFaqVO;
import com.hybird.example.cmmn.mapper.OracleMapper;

@OracleMapper("UserFaqDAO")
public interface UserFaqDAO {

	/***
	 * Faq 목록 조회
	 * @return
	 * @throws Exception
	 */
	public List<UserFaqVO> searchUserFaqList(UserFaqVO vo) throws Exception;
	
	/***
	 * Faq 코드 조회
	 * @return
	 * @throws Exception
	 */
	public List<UserFaqVO> searchUserFaqClCd(UserFaqVO vo) throws Exception;
	
}
