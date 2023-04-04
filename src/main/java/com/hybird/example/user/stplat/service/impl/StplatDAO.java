package com.hybird.example.user.stplat.service.impl;

import java.util.List;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.stplat.service.StplatVO;

@OracleMapper("StplatDAO")
public interface StplatDAO {

	/**
	 * 약관 목록 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<StplatVO> userStplatListView() throws Exception;
	
	/**
	 * 약관 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public StplatVO selectStplat(StplatVO vo) throws Exception;
}
