package com.hybird.example.user.mypage.service;

import java.util.List;

public interface MypageService {

	
	/***
	 * 회원 조회
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int mberChk(MypageVO vo) throws Exception;
	
	/***
	 * 미사용티켓조회
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int unusedTicketCnt(MypageVO vo) throws Exception;
	
	/***
	 * 번호 체크
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int selectUserHandphoneCheck(MypageVO vo) throws Exception;
	
	/***
	 * 회원탈퇴
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int deleteMberSecsn(MypageVO vo) throws Exception;
	
	/**
	 * 기본정보
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public MypageVO myInfoData(MypageVO vo) throws Exception;
	
	/***
	 * 비밀번호 체크
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int myInfoPassword(MypageVO vo) throws Exception;
	
	/***
	 * 기본정보 수정
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int myInfoUpdt(MypageVO vo) throws Exception;
	
	/***
	 * 장바구니
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<MypageVO> searchMypageWishList(MypageVO vo) throws Exception;
	
	/***
	 * 장바구니 수량 업데이트 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int wishListUpdate(MypageVO vo) throws Exception;
	
	/***
	 * 장바구니 삭제 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int wishListDelete(MypageVO vo) throws Exception;
}
