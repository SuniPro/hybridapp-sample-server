package com.hybird.example.user.payMent.service.impl;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.payMent.service.PaidVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@OracleMapper("PaidDAO")
public interface PaidDAO {
    int insertPaid(Map<String, Object> map) throws Exception;
    int updatePaid(Map<String, Object> map) throws Exception;
    int selectMaxCancelSequence(@Param("ipPurNo") String ipPurNo) throws Exception;
    int insertPaidVoid(Map<String, Object> map) throws Exception;

    void updateExpireDateTckState (Map<String, Object> map) throws Exception;

    int updatePaidRefOne(Map<String, Object> map) throws Exception;

    PaidVO selectPaidInfo(String orderNo) throws Exception;

    String selectUserHandPhoneTwo(String resNo) throws Exception;
}
