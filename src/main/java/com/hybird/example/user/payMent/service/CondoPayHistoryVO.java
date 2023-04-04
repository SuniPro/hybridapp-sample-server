package com.hybird.example.user.payMent.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CondoPayHistoryVO {
    // 결제내역
    private String condoSeq; 			// 콘도 주문SEQ
    private String reserveNo;           // 예약번호
    private String orderNo;             // 주문번호
    private String roomType;            // 객실타입
    private String payDate;             // 결제일시
    private String startDate;           // 입실일
    private String endDate;             // 퇴실일
    private String totalAmount;         // 총 결제금액
    private String payYn;               // 결제상태

    // 결제상세내역
    private String condoDtlSeq;         // 콘도주문상세SEQ
    private String adultNum;            // 성인
    private String childNum;            // 소아
    private String petNum;              // 반려견
    private String addUser;             // 추가인원
    private String addDog;              // 추가반려견
    private String roomCd;              // 객실코드
    private String cancelDate;          // 결제취소일자
    private String cancelAmount;        // 결제취소금액
    private String penaltyAmount;       // 위약금
    private String penaltyInfo;         // 위약정보
    private String penaltyPoint;        // 벌점

    // 일자별 금액 조회
    private String reserveDate;         // 예약일자
    private String dayAmount;           // 숙박금액

    // 결제수단
    private String payType;			    // 결제수단
    private String payInfo;			    // 결제 카드번호
    private String cardQuota;		    // 일시불
}
