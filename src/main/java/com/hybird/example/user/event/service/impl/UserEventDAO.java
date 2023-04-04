package com.hybird.example.user.event.service.impl;

import java.util.List;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.event.service.UserEventVO;

@OracleMapper("UserEventDAO")
public interface UserEventDAO {

	/**
	 * 이벤트 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserEventVO> selectEventList(UserEventVO vo) throws Exception;
	
	
	/**
	 * 이벤트 팝업 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserEventVO> selectEventPopupList(UserEventVO vo) throws Exception;
	
	/**
	 * 이벤트 전체 갯수
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectEventTotalCnt(UserEventVO vo) throws Exception;

	/**
	 * 이벤트 상세 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UserEventVO selectEventDetail(UserEventVO vo) throws Exception;
	
	
	/**
	 * 이벤트 팝업 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserEventVO> selectEventFile(UserEventVO vo) throws Exception;
	
}
