package com.hybird.example.user.faq.service;

import java.util.List;

public interface UserFaqService {

	/***
	 * Faq 목록 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserFaqVO> searchUserFaqList(UserFaqVO vo) throws Exception;
	
	/***
	 * Faq 코드 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserFaqVO> searchUserFaqClCd(UserFaqVO vo) throws Exception;
	
	
}
