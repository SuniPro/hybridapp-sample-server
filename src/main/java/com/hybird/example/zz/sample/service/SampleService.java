package com.hybird.example.zz.sample.service;

import java.util.List;
import java.util.Map;

public interface SampleService {

	/**
	 * 공지사항저장
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int noticeInsert(Map<String, Object> map, SampleVO vo) throws Exception;
	
	/**
	 * 공지사항저장 멀티 이미지
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int noticeInsertMulti(List<Map<String, Object>> map, SampleVO vo) throws Exception;
	
}
