package com.hybird.example.user.payMent.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaidVO {
    //회원고유번호
    private String resNo;

    //결제 수단 조회
    private String authCode; //승인번호
    private String cardQuota; //할부개월
    private String cardName; //카드명

    //지불내역 등록 및 취소를 위한 데이터 조회(승인번호 + 카드명)
    private String prCode; //선수금 접수번호
    private String cardNo; //카드번호
    private String netAmt; //공급가액
    private String vatAmt; //부가세
}
