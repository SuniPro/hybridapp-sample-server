package com.hybird.example.user.mypage.service.impl;

import java.util.List;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.mypage.service.MypageVO;

@OracleMapper("MypageDAO")
public interface MypageDAO {

	/**
	 * 회원 탈퇴
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mberChk(MypageVO vo) throws Exception;
	
	/**
	 * 미사용 티켓 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int unusedTicketCnt(MypageVO vo) throws Exception;
	
	/***
	 * 휴대폰번호 체크
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectUserHandphoneCheck(MypageVO vo) throws Exception;
	
	/***
	 * 회원번호 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectUserMyMemberNoCheck(MypageVO vo) throws Exception;
	
	/*** 
	 * 회원번호 2중 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectUserMyMemberNoTwoCheck(MypageVO vo) throws Exception;
	
	/*** 
	 * 회원번호 2중 ID 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String selectUserMyMemberNoTwoIdCheck(MypageVO vo) throws Exception;
	
	/***
	 * 기존회원가입자 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mberMemberNoMyUpdate(MypageVO vo) throws Exception;
	
	/**
	 * 회원 탈퇴
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int deleteMberSecsn(MypageVO vo) throws Exception;
	
	/**
	 * 탈퇴회원 등록
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int insertMberSecsn(MypageVO vo) throws Exception;
	
	/**
	 * 회원 탈퇴 시 기존회원 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateMberSecsn(MypageVO vo) throws Exception;
	
	/**
	 * 기본정보
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public MypageVO myInfoData(MypageVO vo) throws Exception;
	
	/**
	 * 비밀번호 체크
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int myInfoPassword(MypageVO vo) throws Exception;
	
	/**
	 * 기본정보 수정
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int myInfoUpdt(MypageVO vo) throws Exception;
	
	/**
	 * 장바구니 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<MypageVO> searchMypageWishList(MypageVO vo) throws Exception;
	
	/**
	 * 장바구니 수량 업데이트 ajax
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int wishListUpdate(MypageVO vo) throws Exception;
	
	/**
	 * 장바구니 삭제  
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int wishListDelete(MypageVO vo) throws Exception;
}
