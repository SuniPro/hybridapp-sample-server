package com.hybird.example.user.myTicket.service;

import com.hybird.example.cmmn.CmmnVO;

/*
 * mypage 회원
 */
public class MyTicketVO extends CmmnVO {
	//SERTB_SHOP_PAYMENT
    /* 주문ID */
    private int orderSeq;
    /* 주문번호 */
    private String orderNo;
    /* 회원 ID */
    private String memId;
    /* 회원구분 */
    private Object memType;
    /* 회원 이름 */
    private String memName;
    /* 회원 휴대폰 1 */
    private String memHandphone1;
    /* 회원 휴대폰 2 */
    private String memHandphone2;
    /* 회원 휴대폰 3 */
    private String memHandphone3;
    /* 회원 이메일 */
    private String memEmail;
    /* 정상가 합계 */
    private int sumNpAmount;
    /* 회원가 합계 */
    private int sumMemAmount;
    /* 주문 카운트 합계 */
    private int sumOrderQty;
    /* 결제수단 */
    private String payMethod;
    /* 거래ID */
    private String txtid;
    /* 유효기간 시작일 */
    private Object limitStartDate;
    /* 유효기간 종료일 */
    private Object limitEndDate;
    /* 티켓 상태 */
    private String ticketStatus;
    /* 승인코드 */
    private String authCode;
    /* 승인날짜 */
    private String authDate;
    /* 결제 카드사 코드 */
    private int cardCode;
    /* 결제 카드사명 */
    private String cardName;
    /* 카드번호 */
    private String cardNo;
    /* 할부개월 */
    private int cardQuota;
    /* 등록자 */
    private String isrtId;
    /* 등록자IP */
    private String isrtIp;
    /* 등록일자 */
    private Object isrtDate;
    
    //SERTB_SHOP_PAYMENT_DTL
    /* 주문상세 ID */
    private int orderDtlSeq;
//    /* 주문ID */
//    private int orderSeq;
//    /* 주문번호 */
//    private String orderNo;
    /* 티켓 ID */
    private int ticketSeq;
    /* 티켓구분 */
    private Object muMpFlag;
    /* 티켓 명 */
    private String ticketNm;
    /* 티켓 약칭 */
    private String ticketShortNm;
    /* 정상가 */
    private int npAmount;
    /* 회원가 */
    private int memAmount;
    /* 구매 개수 */
    private int buyQty;
    /* 결제취소 여부 */
    private Object cancelYn;
    /* 결제취소 ID */
    private int cancelSeq;
//    /* 등록자 */
//    private String isrtId;
//    /* 등록자IP */
//    private String isrtIp;
//    /* 등록일자 */
//    private Object isrtDate;
    /* 수정자 */
    private String updtId;
    /* 수정자IP */
    private String updtIp;
    /* 수정일자 */
    private Object updtDate;
    
    //SERTB_SHOP_PAYMENT_QRCODE
    /* QRCODE ID */
    private int qrcodeSeq;
//    /* 주문ID */
//    private int orderSeq;
//    /* 주문상세 ID */
//    private int orderDtlSeq;
    /* 주문상세별 일련번호 */
    private int orderDtlNo;
    /* 단일상품 티켓 ID */
    private int muTicketSeq;
    /* P_MENU 상품코드 */
    private String mMenu;
    /* QRCODE */
    private String paymentQrcode;
    /* 티켓 사용 회수 */
    private int ticketLimitCnt;
    /* 티켓 명 */
    private String muTicketNm;
    /* 티켓 약칭 */
    private String muTicketShortNm;
    /* 휴일 구분 */
    private String weekFlag;
//    /* 등록자 */
//    private String isrtId;
//    /* 등록자IP */
//    private String isrtIp;
//    /* 등록일자 */
//    private Object isrtDate;
    
    /* 판매시점의 시설이용 가능 횟수를 의미함 */
    private int tkOriginCnt;
    /* 사용잔여횟수 */
    private int tkRemainCnt;
    /* 사용횟수 */
    private int tkUsedCnt;
    /* yyyy-mm-dd hh24:mi:ss */
    private Object tkFirstTm;
    /* yyyy-mm-dd hh24:mi:ss */
    private Object tkLastTm;
    /* 티켓사용상태(0:사용전,1:부분사용,2:사용완료) */
    private Object tkState;
    /* 온라인아이템코드 */
    private String tkItemCd;
    /* TK_QRCODE  */
    private String tkCode;
    /* TK_REF3 */
    private String tkRef3;
    /* TK_REF4 */
    private String tkRef4;
    /* TK_REF5 */
    private String tkRef5;
    
	public int getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public Object getMemType() {
		return memType;
	}
	public void setMemType(Object memType) {
		this.memType = memType;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemHandphone1() {
		return memHandphone1;
	}
	public void setMemHandphone1(String memHandphone1) {
		this.memHandphone1 = memHandphone1;
	}
	public String getMemHandphone2() {
		return memHandphone2;
	}
	public void setMemHandphone2(String memHandphone2) {
		this.memHandphone2 = memHandphone2;
	}
	public String getMemHandphone3() {
		return memHandphone3;
	}
	public void setMemHandphone3(String memHandphone3) {
		this.memHandphone3 = memHandphone3;
	}
	public String getMemEmail() {
		return memEmail;
	}
	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}
	public int getSumNpAmount() {
		return sumNpAmount;
	}
	public void setSumNpAmount(int sumNpAmount) {
		this.sumNpAmount = sumNpAmount;
	}
	public int getSumMemAmount() {
		return sumMemAmount;
	}
	public void setSumMemAmount(int sumMemAmount) {
		this.sumMemAmount = sumMemAmount;
	}
	public int getSumOrderQty() {
		return sumOrderQty;
	}
	public void setSumOrderQty(int sumOrderQty) {
		this.sumOrderQty = sumOrderQty;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getTxtid() {
		return txtid;
	}
	public void setTxtid(String txtid) {
		this.txtid = txtid;
	}
	public Object getLimitStartDate() {
		return limitStartDate;
	}
	public void setLimitStartDate(Object limitStartDate) {
		this.limitStartDate = limitStartDate;
	}
	public Object getLimitEndDate() {
		return limitEndDate;
	}
	public void setLimitEndDate(Object limitEndDate) {
		this.limitEndDate = limitEndDate;
	}
	public String getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public int getCardCode() {
		return cardCode;
	}
	public void setCardCode(int cardCode) {
		this.cardCode = cardCode;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public int getCardQuota() {
		return cardQuota;
	}
	public void setCardQuota(int cardQuota) {
		this.cardQuota = cardQuota;
	}
	public String getIsrtId() {
		return isrtId;
	}
	public void setIsrtId(String isrtId) {
		this.isrtId = isrtId;
	}
	public String getIsrtIp() {
		return isrtIp;
	}
	public void setIsrtIp(String isrtIp) {
		this.isrtIp = isrtIp;
	}
	public Object getIsrtDate() {
		return isrtDate;
	}
	public void setIsrtDate(Object isrtDate) {
		this.isrtDate = isrtDate;
	}
	public int getOrderDtlSeq() {
		return orderDtlSeq;
	}
	public void setOrderDtlSeq(int orderDtlSeq) {
		this.orderDtlSeq = orderDtlSeq;
	}
	public int getTicketSeq() {
		return ticketSeq;
	}
	public void setTicketSeq(int ticketSeq) {
		this.ticketSeq = ticketSeq;
	}
	public Object getMuMpFlag() {
		return muMpFlag;
	}
	public void setMuMpFlag(Object muMpFlag) {
		this.muMpFlag = muMpFlag;
	}
	public String getTicketNm() {
		return ticketNm;
	}
	public void setTicketNm(String ticketNm) {
		this.ticketNm = ticketNm;
	}
	public String getTicketShortNm() {
		return ticketShortNm;
	}
	public void setTicketShortNm(String ticketShortNm) {
		this.ticketShortNm = ticketShortNm;
	}
	public int getNpAmount() {
		return npAmount;
	}
	public void setNpAmount(int npAmount) {
		this.npAmount = npAmount;
	}
	public int getMemAmount() {
		return memAmount;
	}
	public void setMemAmount(int memAmount) {
		this.memAmount = memAmount;
	}
	public int getBuyQty() {
		return buyQty;
	}
	public void setBuyQty(int buyQty) {
		this.buyQty = buyQty;
	}
	public Object getCancelYn() {
		return cancelYn;
	}
	public void setCancelYn(Object cancelYn) {
		this.cancelYn = cancelYn;
	}
	public int getCancelSeq() {
		return cancelSeq;
	}
	public void setCancelSeq(int cancelSeq) {
		this.cancelSeq = cancelSeq;
	}
	public String getUpdtId() {
		return updtId;
	}
	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}
	public String getUpdtIp() {
		return updtIp;
	}
	public void setUpdtIp(String updtIp) {
		this.updtIp = updtIp;
	}
	public Object getUpdtDate() {
		return updtDate;
	}
	public void setUpdtDate(Object updtDate) {
		this.updtDate = updtDate;
	}
	public int getQrcodeSeq() {
		return qrcodeSeq;
	}
	public void setQrcodeSeq(int qrcodeSeq) {
		this.qrcodeSeq = qrcodeSeq;
	}
	public int getOrderDtlNo() {
		return orderDtlNo;
	}
	public void setOrderDtlNo(int orderDtlNo) {
		this.orderDtlNo = orderDtlNo;
	}
	public int getMuTicketSeq() {
		return muTicketSeq;
	}
	public void setMuTicketSeq(int muTicketSeq) {
		this.muTicketSeq = muTicketSeq;
	}
	public String getmMenu() {
		return mMenu;
	}
	public void setmMenu(String mMenu) {
		this.mMenu = mMenu;
	}
	public String getPaymentQrcode() {
		return paymentQrcode;
	}
	public void setPaymentQrcode(String paymentQrcode) {
		this.paymentQrcode = paymentQrcode;
	}
	public int getTicketLimitCnt() {
		return ticketLimitCnt;
	}
	public void setTicketLimitCnt(int ticketLimitCnt) {
		this.ticketLimitCnt = ticketLimitCnt;
	}
	public String getMuTicketNm() {
		return muTicketNm;
	}
	public void setMuTicketNm(String muTicketNm) {
		this.muTicketNm = muTicketNm;
	}
	public String getMuTicketShortNm() {
		return muTicketShortNm;
	}
	public void setMuTicketShortNm(String muTicketShortNm) {
		this.muTicketShortNm = muTicketShortNm;
	}
	public String getWeekFlag() {
		return weekFlag;
	}
	public void setWeekFlag(String weekFlag) {
		this.weekFlag = weekFlag;
	}
	public int getTkOriginCnt() {
		return tkOriginCnt;
	}
	public void setTkOriginCnt(int tkOriginCnt) {
		this.tkOriginCnt = tkOriginCnt;
	}
	public int getTkRemainCnt() {
		return tkRemainCnt;
	}
	public void setTkRemainCnt(int tkRemainCnt) {
		this.tkRemainCnt = tkRemainCnt;
	}
	public int getTkUsedCnt() {
		return tkUsedCnt;
	}
	public void setTkUsedCnt(int tkUsedCnt) {
		this.tkUsedCnt = tkUsedCnt;
	}
	public Object getTkFirstTm() {
		return tkFirstTm;
	}
	public void setTkFirstTm(Object tkFirstTm) {
		this.tkFirstTm = tkFirstTm;
	}
	public Object getTkLastTm() {
		return tkLastTm;
	}
	public void setTkLastTm(Object tkLastTm) {
		this.tkLastTm = tkLastTm;
	}
	public Object getTkState() {
		return tkState;
	}
	public void setTkState(Object tkState) {
		this.tkState = tkState;
	}
	public String getTkItemCd() {
		return tkItemCd;
	}
	public void setTkItemCd(String tkItemCd) {
		this.tkItemCd = tkItemCd;
	}
	public String getTkCode() {
		return tkCode;
	}
	public void setTkCode(String tkCode) {
		this.tkCode = tkCode;
	}
	public String getTkRef3() {
		return tkRef3;
	}
	public void setTkRef3(String tkRef3) {
		this.tkRef3 = tkRef3;
	}
	public String getTkRef4() {
		return tkRef4;
	}
	public void setTkRef4(String tkRef4) {
		this.tkRef4 = tkRef4;
	}
	public String getTkRef5() {
		return tkRef5;
	}
	public void setTkRef5(String tkRef5) {
		this.tkRef5 = tkRef5;
	}
	
	@Override
	public String toString() {
		return "\nMyTicketVO [\norderSeq=" + orderSeq + ", orderNo=" + orderNo + ", memId=" + memId + "\n, memType=" + memType
			+ ", memName=" + memName + ", memHandphone1=" + memHandphone1 + "\n, memHandphone2=" + memHandphone2
			+ ", memHandphone3=" + memHandphone3 + ", memEmail=" + memEmail + "\n, sumNpAmount=" + sumNpAmount
			+ ", sumMemAmount=" + sumMemAmount + ", sumOrderQty=" + sumOrderQty + "\n, payMethod=" + payMethod
			+ ", txtid=" + txtid + ", limitStartDate=" + limitStartDate + "\n, limitEndDate=" + limitEndDate
			+ ", ticketStatus=" + ticketStatus + ", authCode=" + authCode + "\n, authDate=" + authDate + ", cardCode="
			+ cardCode + ", cardName=" + cardName + "\n, cardNo=" + cardNo + ", cardQuota=" + cardQuota + ", isrtId="
			+ isrtId + "\n, isrtIp=" + isrtIp + ", isrtDate=" + isrtDate + ", orderDtlSeq=" + orderDtlSeq
			+ "\n, ticketSeq=" + ticketSeq + ", muMpFlag=" + muMpFlag + ", ticketNm=" + ticketNm + "\n, ticketShortNm="
			+ ticketShortNm + ", npAmount=" + npAmount + ", memAmount=" + memAmount + "\n, buyQty=" + buyQty
			+ ", cancelYn=" + cancelYn + ", cancelSeq=" + cancelSeq + "\n, updtId=" + updtId + ", updtIp=" + updtIp
			+ ", updtDate=" + updtDate + "\n, qrcodeSeq=" + qrcodeSeq + ", orderDtlNo=" + orderDtlNo
			+ ", muTicketSeq=" + muTicketSeq + "\n, mMenu=" + mMenu + ", paymentQrcode=" + paymentQrcode
			+ ", ticketLimitCnt=" + ticketLimitCnt + "\n, muTicketNm=" + muTicketNm + ", muTicketShortNm="
			+ muTicketShortNm + ", weekFlag=" + weekFlag + "\n, tkOriginCnt=" + tkOriginCnt + ", tkRemainCnt="
			+ tkRemainCnt + ", tkUsedCnt=" + tkUsedCnt + "\n, tkFirstTm=" + tkFirstTm + ", tkLastTm=" + tkLastTm
			+ ", tkState=" + tkState + "\n, tkItemCd=" + tkItemCd + ", tkCode=" + tkCode + ", tkRef3=" + tkRef3
			+ "\n, tkRef4=" + tkRef4 + ", tkRef5=" + tkRef5 + "\n]";
	}
	
}
