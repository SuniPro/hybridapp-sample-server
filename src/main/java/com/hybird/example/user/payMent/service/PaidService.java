package com.hybird.example.user.payMent.service;

import java.util.Map;

public interface PaidService {

    /* 지불내역 등록 및 취소를 위한 데이터 조회*/
    public PaidVO selectPaid(String orderNo) throws Exception;

    /* 선수금 접수번호 업데이트 */
    // ITREV_PAID 테이블 ip_ref1 컬럼에 업데이트
    public int updatePaidRef(Map<String, Object> map) throws Exception;

    /* 암호화된 휴대폰 번호 조회(2번째) */
    public String selectUserHandPhoneTwo(String resNo) throws Exception;
}
