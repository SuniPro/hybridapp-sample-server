package com.hybird.example.zz.sample.service.impl;

import lombok.Data;

@Data
// 결제 테스트용 상품VO 및 CLIENT_REQUEST 용
public class ProductSampleVO {
	
	private String moid;	// 주문번호
	private String mMenu;	//"M_MENU" VARCHAR2(6), 상품코드
	private String mDname;	//"M_DNAME" VARCHAR2(30), 상품명
	private int mAmount; 	//"M_AMOUNT" NUMBER, 공급가액
	private int mPrice; 	//"M_PRICE" NUMBER, 판매가액
	private int qty;		//"QTY" NUMBER, 수량
	private String crDate;	//"CR_DATE" DATE DEFAULT SYSDATE, 요청일시
	private String userId;	//"USER_ID" VARCHAR2(100), 회원아이디
	private int price;		//"PRICE" NUMBER 총가격
	private String status;	//"STATUS" VARCHAR2(10), 상태값
	private String payMethod;	//
}
