package com.hybird.example.user.payMent.service;

import lombok.Data;

@Data
public class payHistoryVO {

	// 결제내역 관련
	private String orderSeq; 			// 주문번호
	private String orderNo;
	private String memType; 			// 멤버등급
	private String sumNpAmount; 		// 공급가
	private String sumMemAmount; 		// 판매가
	private String sumOrderQty; 		// 판매수량
	private String buyDate; 			// 구매일
	private String limitStartDate; 		// 유효기간 시작일
	private String limitEndDate; 		// 유효기간 마감일
	private String ticketNm; 			// 티켓명
	private String ticketShortNm; 		// 상품명
	private String ticketStatus; 		// 티켓 상태
	
	// 결제상세내역 관련
	private String orderDtlSeq; 		// 주문상세번호
	private String buyQty; 				// 구매수량
	private String cancelYn; 			// 결제취소상태 	   (Y : 결제취소된 상태 / N : 결제취소가능상태)
	private String cancelEnableYn; 		// 결제취소가능상태 (N : 불가능 / Y : 가능)
	
	// 결제고객 / 수단 관련
	private String memName;				// 고객명
	private String memHandphone1;		// 핸드폰번호1
	private String memHandphone2;		// 핸드폰번호2
	private String memHandphone3;		// 핸드폰번호3
	private String memEmail;			// 고객이메일
	private String discountAmountmem;	// 할인금액
	private String payMethod;			// 결제수단
	private String cardCode;			// 카드코드
	private String cardName;			// 카드명
	private String cardNo;				// 카드번호
	private String cardQuota;			// 일시불
	
}
