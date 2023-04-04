package com.hybird.example.zz.sample.service.impl;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.zz.sample.service.SampleVO;

@OracleMapper("SampleDAO")
public interface SampleDAO {
	
	/***
	 * 공지사항
	 * @return
	 * @throws Exception
	 */
	public int noticeInsert(SampleVO vo) throws Exception;
	
}
