package com.hybird.example.user.mber.service;

public interface MberService {

	/***
	 * 아이디 중복 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int searchUserIdCheck(String userId) throws Exception;
	
	/***
	 * 휴대폰 중복 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int searchUserPhoneCheck(MberVO vo) throws Exception;
	
	 /***
	  * 회원가입
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	public int mberSingup(MberVO vo) throws Exception;
	
	/**
	 * 아이디 찾기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String myIdFind(MberVO vo) throws Exception;
	
	/**
	 * 비밀번호 찾기
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int myPwdFind(MberVO vo) throws Exception;
	
	/**
	 * 비밀번호 재설정
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int myPwdReset(MberVO vo) throws Exception;

	/**
	 * 비밀번호 재설정
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int loginHistInsert(MberVO vo) throws Exception;

}
