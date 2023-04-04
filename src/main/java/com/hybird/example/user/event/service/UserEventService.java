package com.hybird.example.user.event.service;

import java.util.List;

public interface UserEventService {

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
	 * 이벤트 상세 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UserEventVO searchEventDetail(UserEventVO vo) throws Exception;
	
	/**
	 * 이벤트 파일 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserEventVO> selectEventFile(UserEventVO vo) throws Exception;
	
	
}
