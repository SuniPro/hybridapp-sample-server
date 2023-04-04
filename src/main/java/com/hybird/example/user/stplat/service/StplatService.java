package com.hybird.example.user.stplat.service;

import java.util.List;

public interface StplatService {

	/**
	 * 약관목록 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<StplatVO> userStplatListView() throws Exception;
	
	
	/***
	 * 약관 조회
	 * @param stplatSe
	 * @return
	 * @throws Exception
	 */
	public StplatVO searchStplat(StplatVO vo) throws Exception;
	
}
