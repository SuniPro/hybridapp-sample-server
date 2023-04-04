package com.hybird.example.user.notice.service;

import java.util.List;

public interface UserNoticeService {

	/**
	 * 공지사항 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserNoticeVO> selectNoticeList(UserNoticeVO vo) throws Exception;
	
	/**
	 * 공지사항 팝업 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserNoticeVO> selectNoticePopupList(UserNoticeVO vo) throws Exception;
	
	/**
	 * 공지사항 상세 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UserNoticeVO searchNoticeDetail(UserNoticeVO vo) throws Exception;
	
	/**
	 * 공지사항 파일 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserNoticeVO> selectNoticeFile(UserNoticeVO vo) throws Exception;
	
	
}
