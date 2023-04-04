package com.hybird.example.user.inqry.service.impl;

import java.util.List;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.inqry.service.UserInqryVO;

@OracleMapper("UserInqryDAO")
public interface UserInqryDAO {

	/**
	 * 1:1 문의 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserInqryVO> selectInqryList(UserInqryVO vo) throws Exception;
	
	/**
	 * 1:1 문의 등록
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int insertInqry(UserInqryVO vo) throws Exception;
	
	/**
	 * 1:1 문의 히스토리 저장
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int insertInqryHist(UserInqryVO vo) throws Exception;
	
	/**
	 * 1:1 문의 수정
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateInqry(UserInqryVO vo) throws Exception;
	/**
	 * 1:1 문의 상세 수정
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UserInqryVO selectInqryDatail(UserInqryVO vo) throws Exception;
	
	/**
	 * 1:1 문의 삭제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int deleteInqry(UserInqryVO vo) throws Exception;
	
	/**
	 * 1:1 문의 관리자 이메일 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String inqryMngrEmail(String inqrySe) throws Exception;
	
	/**
	 * 1:1 문의 공통코드 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserInqryVO> searchUserInqryClCd(UserInqryVO vo) throws Exception;
}
