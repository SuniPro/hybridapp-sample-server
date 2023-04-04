package com.hybird.example.user.payMent.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface CondoService {
    /* 콘도 결제 주문번호, 시퀀스 생성 */
	String getOrdernoMakeSeq() throws Exception;
	String getOrderSeq() throws Exception;
    
    /* 결과값 저장 */
    String saveShopPayment(HttpServletRequest request, Model model) throws Exception;
    
    String getCardInfo(Map<String, Object> map) throws Exception;
    int insertCondoPay(Map<String, Object> map) throws Exception;
    int insertCondoPayDetail(Map<String, Object> map) throws Exception;

    /* 콘도 결제 내역 조회 */
    List<CondoPayHistoryVO> condoPayHistoryList(String userId, String startDate, String endDate) throws Exception;

    CondoPayHistoryVO selectCondoPayDetail(Map<String, Object> map) throws Exception;
    List<CondoPayHistoryVO> selectCondoPayDetailAmount(Map<String, Object> map) throws Exception;

    String condoPaymentBuyDate(String userId, String reserveNo) throws Exception;

    String selectCondoSeq(String userId, String reserveNo) throws Exception;

    CondoPayHistoryVO selectCondoPaymentInfo(Map<String, Object> map) throws Exception;

    /* 결제취소 상세 내용 조회 */
    Map<String,Object> selectCancelDetail(Map<String, Object> map) throws Exception;
    int updateCondoPay(Map<String, Object> map) throws Exception;

    int updateCondoPayPunish(Map<String, Object> map) throws Exception;

    /* 암호화 데이터 조회 */
    Map<String,Object> selectEncryptedData(String resNo) throws Exception;
    /* 콘도 카운트 */
    int myCondoCnt(String userId) throws Exception;
}
