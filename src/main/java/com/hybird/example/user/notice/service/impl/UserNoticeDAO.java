package com.hybird.example.user.notice.service.impl;

import java.util.List;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.notice.service.UserNoticeVO;

@OracleMapper("UserNoticeDAO")
public interface UserNoticeDAO {

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
	 * 공지사항 전체 갯수
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectNoticeTotalCnt(UserNoticeVO vo) throws Exception;

	/**
	 * 공지사항 상세 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UserNoticeVO selectNoticeDetail(UserNoticeVO vo) throws Exception;
	
	
	/**
	 * 공지사항 팝업 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserNoticeVO> selectNoticeFile(UserNoticeVO vo) throws Exception;
	
}
