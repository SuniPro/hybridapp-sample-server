package com.hybird.example.user.payMent.service.impl;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.payMent.service.CondoPayHistoryVO;

import java.util.List;
import java.util.Map;

@OracleMapper("CondoDAO")
public interface CondoDAO {
    
    /* 콘도결제 */
	String getOrdernoMakeSeq() throws Exception;
    String getOrderSeq() throws Exception;
    
    /* 결제 후 Response */
    int insertCertification(Map<String, Object> map) throws Exception;
    int insertApprovalRequest(Map<String, Object> map) throws Exception;
    int insertNetCancel(Map<String, Object> map) throws Exception;
    int insertPGResult(Map<String, Object> map) throws Exception;
    
    String getCardInfo(Map<String, Object> map)throws Exception;
    int insertCondoPay(Map<String, Object> map) throws Exception;
    int insertCondoPayDetail(Map<String, Object> map) throws Exception;
    

    /* 마이페이지 - 콘도 결제 내역 조회 */
    List<CondoPayHistoryVO> selectCondoPay(Map<String, Object> map) throws Exception;

    CondoPayHistoryVO selectCondoPayDetail(Map<String, Object> map) throws Exception;
    List<CondoPayHistoryVO> selectCondoPayDetailAmount(Map<String, Object> map) throws Exception;
    String condoPaymentBuyDate(Map<String, Object> map) throws Exception;

    CondoPayHistoryVO selectCondoPaymentInfo(Map<String, Object> map) throws Exception;


    /* 결제취소 상세내용 조회 */
    Map<String,Object> selectCancelDetail(Map<String, Object> map) throws Exception;
    int updateCondoPay(Map<String,Object> map) throws Exception;

    String selectCondoSeq(Map<String, Object> map) throws Exception;

    int updateCondoPayPunish(Map<String, Object> map) throws Exception;

    /* 암호화 데이터 추출 */
    Map<String, Object> selectEncryptedData(String resNo) throws Exception;
    /* 콘도 횟수  */
    Integer myCondoCnt(String userId) throws Exception;
}
