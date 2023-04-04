package com.hybird.example.user.payMent.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hybird.example.user.mber.service.MberVO;
import com.hybird.example.user.payMent.service.BillingCardVO;
import com.hybird.example.user.payMent.service.PayMentVO;
import com.hybird.example.user.payMent.service.payHistoryVO;
import com.hybird.example.zz.sample.service.impl.ProductSampleVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hybird.example.user.payMent.service.PayMentService;

@Service("PayMentService")
public class PayMentServiceImpl implements PayMentService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "PayMentDAO")
	private PayMentDAO payMentDAO;

	// moid 주문번호 채번
	@Override
	public String searchMoid() throws Exception {
		return payMentDAO.searchMoid();
	}

	// 결제내역조회 2022.04.04 추가
	@Override
	public List<payHistoryVO> payHistoryList(String userId, String startDate, String endDate, String searchStatus) throws Exception {
		
		return payMentDAO.payHistoryList(userId, startDate, endDate, searchStatus);
	}
	
	// 결제내역상세조회 2022.04.04 추가
	@Override
	public List<payHistoryVO> payMentDetail(Map<String, Object> searchDetailMap) throws Exception {
		
		return payMentDAO.payMentDetail(searchDetailMap);
	}
	
	// 결제일 조회
	@Override
	public String payMentBuyDate(Map<String, Object> orderSeq) throws Exception {
	
		return payMentDAO.payMentBuyDate(orderSeq);
	}
	
	// 고객정보 및 결제수단 조회
	@Override
	public payHistoryVO payMentInfo(Map<String, Object> searchDetailMap) throws Exception {
		
		return payMentDAO.payMentInfo(searchDetailMap);
	}
	
	// 결제 회원정보 조회
	@Override
	public MberVO searchName(String id) throws Exception {
		
		return payMentDAO.searchName(id);
	}
	
	// 결제 테이블에 결제 내용 저장
	@Override
	public int insertPayment(Map<String, Object> resultMap) throws Exception {
		
		return payMentDAO.insertPayment(resultMap);
	}

	// tid(트랜잭션 아이디 조회) 조회
	@Override
	public String searchPayMentDetail(String moid) throws Exception {
		return payMentDAO.searchPayMentDetail(moid);
	}
	
	// 결제 히스토리 저장
	@Override
	public void pgRawDataHistory(Map<String, Object> resultMap) throws Exception {
		payMentDAO.pgRawDataHistory(resultMap);
		
	}

	// view로부터 받은 구매 요청값 저장
	@Override
	public void clientRequest(ProductSampleVO psVO) throws Exception {
		payMentDAO.clientRequest(psVO);
	}

	// PG사로 요청 보낼 값 저장
	@Override
	public void pgRequest(Map<String, Object> resultMap) throws Exception {
		payMentDAO.pgRequest(resultMap);
		
	}

	// PG사로 결제 요청 후 인증값 저장
	@Override
	public void pgCertification(PayMentVO pv) throws Exception {
		payMentDAO.pgCertification(pv);
	}

	// PG사로 결제승인 요청을 위한 값 저장
	@Override
	public void pgApprovalRequest(PayMentVO payApproReq) throws Exception {
		payMentDAO.pgApprovalRequest(payApproReq);
		
	}
	
	// PG사 결제승인 실패 시 응답값  저장
	@Override
	public void pgCommFailed(Map<String, Object> cmnctFlr) throws Exception {
		payMentDAO.pgCommFailed(cmnctFlr);
		
	}

	// PG사 결제성공 응답값  저장
	@Override
	public void pgSuccessResult(Map<String, Object> resultSucessMap) throws Exception {
		payMentDAO.pgSuccessResult(resultSucessMap);
		
	}

	// view로부터 받은 취소요청정보 저장
	@Override
	public void clientCancelRequest(Map<String, Object> cancelRequestMap) throws Exception {
		payMentDAO.clientCancelRequest(cancelRequestMap);
		
	}

	// PG사로 보낼 결제 취소요청 정보 저장
	@Override
	public void pgCancelRequest(Map<String, Object> pgCancelRequestMap) throws Exception {
		payMentDAO.pgCancelRequest(pgCancelRequestMap);
		
	}

	// PG사 결제 취소 응답값 저장
	@Override
	public void pgCancelResult(Map<String, Object> pgCancelResultMap) throws Exception {
		payMentDAO.pgCancelResult(pgCancelResultMap);
		
	}

	// 고객 간편카드 조회
	@Override
	public List<BillingCardVO> cardCheck(String userId) throws Exception {
		return payMentDAO.cardCheck(userId);
	}

	
	// 빌키 발급 요청값 저장
	@Override
	public void bilkeyRequest(Map<String, Object> resultMap) throws Exception {
		payMentDAO.bilkeyRequest(resultMap);
		
	}
	
	// 빌키 요청에 대한 응답값 저장
	@Override
	public void dataStorage(Map<String, Object> resultMap) throws Exception {
		payMentDAO.dataStorage(resultMap);
	}

	// 고객 등록카드 중복 조회
	@Override
	public BillingCardVO billCardSelect(Map<String, Object> resultMap) throws Exception {
		return payMentDAO.billCardSelect(resultMap);
	}

	// 고객 빌키 update
	@Override
	public int billkeyUpdate(Map<String, Object> resultMap) throws Exception {
		
		return payMentDAO.billkeyUpdate(resultMap);
	}

	// 고객 빌키 조회
	@Override
	public String bilkyInquiry(int birSeq) throws Exception {
		return payMentDAO.bilkyInquiry(birSeq);
		
	}

	// 빌링결제 응답값 저장
	@Override
	public void billingResult(Map<String, Object> billResultSuccessMap) throws Exception {
		
		payMentDAO.billingResult(billResultSuccessMap);
	}

	// 빌링결제 TID(트랜잭션 아이디) 조회
	@Override
	public String searchBillingTid(String moid) throws Exception {
		
		return payMentDAO.searchBillingTid(moid);
	}



}
