package com.hybird.example.user.inqry.service;

import java.util.List;

public interface UserInqryService {

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
	 * 1:1 문의 수정
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateInqry(UserInqryVO vo) throws Exception;
	
	/**
	 * 1:1 문의 상세
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
	 * 1:1 문의 코드 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<UserInqryVO> searchUserInqryClCd(UserInqryVO vo) throws Exception;
	

	
}
