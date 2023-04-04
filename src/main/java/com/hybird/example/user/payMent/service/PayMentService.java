package com.hybird.example.user.payMent.service;

import java.util.List;
import java.util.Map;

import com.hybird.example.user.mber.service.MberVO;
import com.hybird.example.zz.sample.service.impl.ProductSampleVO;

public interface PayMentService {

	/**
	 * 주문번호 채번
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public String searchMoid() throws Exception;
	
	/**
	 * 결제요청 회원정보 조회
	 * @return MberVO
	 * @throws Exception
	 */
	public MberVO searchName(String id) throws Exception;
	
	/**
	 * 결제 내역 조회
	 * @return payHistoryVO
	 * @throws Exception
	 */
	public List<payHistoryVO> payHistoryList(String userId, String startDate, String endDate, String searchStatus) throws Exception;
	
	/**
	 * 결제 내역 상세 조회
	 * @return payHistoryVO
	 * @throws Exception
	 */
	public List<payHistoryVO> payMentDetail(Map<String, Object> searchDetailMap) throws Exception;
	
	/**
	 * 고객정보 및 결제수단 조회
	 * @return payHistoryVO
	 * @throws Exception
	 */
	public payHistoryVO payMentInfo(Map<String, Object> searchDetailMap) throws Exception;
	
	/**
	 * 결제일 조회
	 * @return String
	 * @throws Exception
	 */
	public String payMentBuyDate(Map<String, Object> orderSeq) throws Exception;
	
	/**
	 * 결제 테이블에 결제내용 저장(임시, 아직 확정이 아님)
	 * @return int
	 * @throws Exception
	 */
	public int insertPayment(Map<String, Object> resultMap) throws Exception;

	/**
	 * 일반결제 tid(트랜잭션 아이디 조회) 조회
	 * @return String
	 * @throws Exception
	 */
	public String searchPayMentDetail(String moid) throws Exception;
	
	/**
	 * 결제 히스토리 저장
	 * @return
	 * @throws Exception
	 */
	public void pgRawDataHistory(Map<String, Object> requesttMap) throws Exception;
	
	/**
	 * view로부터 받은 구매요청정보 저장
	 * @return
	 * @throws Exception
	 */
	public void clientRequest(ProductSampleVO psVO) throws Exception;
	
	/**
	 * PG사로 요청 보낼 값 저장
	 * @return
	 * @throws Exception
	 */
	public void pgRequest(Map<String, Object> resultMap) throws Exception;
	
	/**
	 * PG사로 결제 요청 후 인증값 저장
	 * @return
	 * @throws Exception
	 */
	public void pgCertification(PayMentVO pv) throws Exception;
	
	/**
	 * PG사로 결제승인 요청을 위한 값 저장
	 * @return
	 * @throws Exception
	 */
	public void pgApprovalRequest(PayMentVO payApproReq) throws Exception;
	
	/**
	 * PG사 결제승인 실패 시 응답값  저장
	 * @return
	 * @throws Exception
	 */
	public void pgCommFailed(Map<String, Object> cmnctFlr) throws Exception;
	
	/**
	 * PG사 결제성공 응답값  저장
	 * @return
	 * @throws Exception
	 */
	public void pgSuccessResult(Map<String, Object> resultSucessMap) throws Exception;
	
	/**
	 * view로부터 받은 취소요청정보 저장
	 * @return
	 * @throws Exception
	 */
	public void clientCancelRequest(Map<String, Object> cancelRequestMap) throws Exception;
	
	/**
	 * PG사로 보낼 결제 취소요청 정보 저장
	 * @return
	 * @throws Exception
	 */
	public void pgCancelRequest(Map<String, Object> pgCancelRequestMap) throws Exception;
	
	/**
	 * 빌링발급 요청값 저장
	 * @return String
	 * @throws Exception
	 */
	public void bilkeyRequest(Map<String, Object> resultMap) throws Exception;
		
	/**
	 * PG사 결제 취소 응답값 저장
	 * @return
	 * @throws Exception
	 */
	public void pgCancelResult(Map<String, Object> pgCancelResultMap) throws Exception;
	
	/**
	 * 고객 간편카드 조회
	 * @return list
	 * @throws Exception
	 */
	public List<BillingCardVO> cardCheck(String userId) throws Exception;
	
	/**
	 * 빌키 요청에 대한 응답값 저장
	 * @return
	 * @throws Exception
	 */
	public void dataStorage(Map<String, Object> resultMap) throws Exception;
	
	
	/**
	 * 고객 등록카드 중복 조회 
	 * @return BillingCardVO
	 * @throws Exception
	 */
	public BillingCardVO billCardSelect(Map<String, Object> resultMap) throws Exception;
	
	/**
	 * 고객 빌키 update
	 * @return BillingCardVO
	 * @throws Exception
	 */
	public int billkeyUpdate(Map<String, Object> resultMap) throws Exception;
	
	/**
	 * 고객 빌키 조회
	 * @return String
	 * @throws Exception
	 */
	public String bilkyInquiry(int birSeq) throws Exception;
	
	/**
	 * 빌링결제 응답값 저장
	 * @return String
	 * @throws Exception
	 */
	public void billingResult(Map<String, Object> billResultSuccessMap) throws Exception;
	
	/**
	 * 빌링결제 tid(트랜잭션 아이디 조회) 조회
	 * @return String
	 * @throws Exception
	 */
	public String searchBillingTid(String moid) throws Exception;
}
