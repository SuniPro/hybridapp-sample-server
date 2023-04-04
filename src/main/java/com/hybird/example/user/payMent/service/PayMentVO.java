package com.hybird.example.user.payMent.service;

import lombok.Data;

@Data
public class PayMentVO {
	
	// 결제 인증 값 저장
	private String authResultCode; 	//"AUTH_RESULT_CODE" NUMBER, 인증결과 : 0000(성공)
	private String authResultMsg; 	//"AUTH_RESULT_MSG" VARCHAR2(2000), 인증결과 메시지
	private String nextAppUrl; 		//"NEXT_APP_URL" VARCHAR2(255), 승인 요청 URL
	private String txTid; 			//"TXTID" VARCHAR2(30), 거래 ID
	private String authToken; 		//"AUTH_TOKEN" VARCHAR2(40), 인증 TOKEN
	private String payMethod;		//"PAY_METHOD" VARCHAR2(10), 결제수단
	private String mid;				//"MID" VARCHAR2(10), 상점 아이디
	private String moid;			//"MOID" VARCHAR2(64), 상점 주문번호
	private int amt;				//"AMT" NUMBER, 결제 금액
	private String reqReserved; 	//"REQ_RESERVED" VARCHAR2(500), 상점 예약필드
	private String netCancelUrl ;	//"NET_CANCEL_URL" VARCHAR2(255), 망취소 요청 URL
	private String status;			// 결제 상태값
	private String authDate;		// 인증일시
	private String signData;		// hash암호

}
