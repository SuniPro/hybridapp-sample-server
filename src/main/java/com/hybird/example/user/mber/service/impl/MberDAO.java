package com.hybird.example.user.mber.service.impl;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.mber.service.MberVO;

@OracleMapper("MberDAO")
public interface MberDAO {

	/***
	 * 아이디 중복 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectUserIdCheck(String userId) throws Exception;
	
	/***
	 * 휴대폰번호 중복 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectUserHandphoneCheck(MberVO vo) throws Exception;
	
	/***
	 * 회원번호 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectUserMemberNoCheck(MberVO vo) throws Exception;
	
	/***
	 * 회원번호 2중 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectUserMemberNoTwoCheck(MberVO vo) throws Exception;
	
	/***
	 * 기존회원가입자 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mberSingupUpdate(MberVO vo) throws Exception;
	
	/***
	 * resno 번호
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String selectUserResno(MberVO vo) throws Exception;
	
	/***
	 * ser 회원번호
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String selectUserMemberNo(MberVO vo) throws Exception;
	
	/***
	 * 회원가입
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mberSingup(MberVO vo) throws Exception;
	
	/***
	 * 회원가입 중복 체크
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mberSingupOverlap(MberVO vo) throws Exception;
	
	/**
	 * 사용자 기본 정보 조회
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public MberVO mberInfo ( MberVO vo ) throws Exception;

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
	 * 로그인 이력 저장
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int loginHistInsert(MberVO vo) throws Exception;
	
}
