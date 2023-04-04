package com.hybird.example.user.payMent.service.impl;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.user.payMent.service.CondoPayHistoryVO;
import com.hybird.example.user.payMent.service.CondoService;
import com.hybird.example.user.payMent.util.*;
import com.plnc.user.payMent.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service("CondoService")
@Transactional(transactionManager = "txManager", readOnly = false, rollbackFor = CmmnUserException.class)
public class CondoServiceImpl implements CondoService {
    @Resource(name = "CondoDAO")
    private CondoDAO condoDAO;
    
    @Resource(name = "OrderInvoiceDAO")
    private OrderInvoiceDAO orderInvoiceDAO;

    @Resource(name = "PaidDAO")
    private PaidDAO paidDAO;

    private HashMap<String, Object> res = null;


    /* 콘도결제 */
    public String getOrdernoMakeSeq() throws Exception {
        return condoDAO.getOrdernoMakeSeq();
    }
    
    public String getOrderSeq() throws Exception {
        return condoDAO.getOrderSeq();
    }
    
    /* 콘도결제 결과값 저장 */
    public String saveShopPayment(HttpServletRequest request, Model model) throws Exception {
        /* 2. 나이스 페이로 부터 받은 데이터 한글 수신을 위해서 인코딩(다음 순서 중요) */
        request.setCharacterEncoding("euc-kr");
        Map<String, Object> niceCertMap = OrderMapGenerator.makeRecvCertification(request);
        log.debug("CondoServiceImpl saveShopPayment niceCertMap : {}", niceCertMap);
        log.debug("CondoServiceImpl saveShopPayment model : {}", model);
        request.setCharacterEncoding("utf-8");

        /* 4. 결제 인증 결과 유효성 체크 */
        String code = OrderValidator.validateCertification(niceCertMap);
        log.debug("saveShopPayment validateCertification : {}", code);
        if (!code.equals(ResponseCode.SYS_0000.getCode())) {
            return code;
        }
        
        /* 5. PG 인증 정보 저장 */
        String ediDate = OrderInvoiceUtil.getyyyyMMddHHmmss();
        niceCertMap.put("status", "PAYMENT");
        niceCertMap.put("authDate", ediDate);
        insertCertification(niceCertMap);

        /* ###. 결제 진행 ### */
        String authResultCode = String.valueOf(niceCertMap.get("authResultCode"));
        
        log.info("=========== condoServiceImpl nicepay request param : " + niceCertMap + " / " + ediDate);

        if ("0000".equals(authResultCode)) {
            /* 6. 결제 요청 */
            String merchantKey = OrderValueGenerator.searchMerchantKey(String.valueOf(niceCertMap.get("mid")));

            DataEncrypt sha256Enc = new DataEncrypt();
            String encStr = String.valueOf(niceCertMap.get("authToken")) + String.valueOf(niceCertMap.get("mid")) + String.valueOf(niceCertMap.get("amt")) + ediDate + merchantKey;
            String hashString = sha256Enc.encrypt(encStr);
            
            /* 7. 결제인증 요청 데이터 저장 */
            Map<String, Object> approveMap = OrderMapGenerator.makeApprovalRequest(niceCertMap, ediDate, hashString);
            approveMap.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
            approveMap.put("isrtIp", OrderInvoiceUtil.getClientIP(request));
            
            log.info("=========== condoServiceImpl nicepay request param : " + approveMap);

            /* 8. 나이스페이에 결제인증 요청 */
            String resultJsonStr = OrderInvoiceUtil.connectToServer(OrderMapGenerator.makePaymentRequest(niceCertMap, ediDate, hashString), String.valueOf(niceCertMap.get("nextAppURL")));

            /* 9. 통신 실패로 인한 망취소 */
            if ("9999".equals(resultJsonStr)) {
                String cancelResultJsonStr = OrderInvoiceUtil.connectToServer(OrderMapGenerator.makePaymentRequest(niceCertMap, ediDate, hashString, true), String.valueOf(niceCertMap.get("netCancelURL")));

                HashMap cancelResultData = OrderInvoiceUtil.jsonStringToHashMap(cancelResultJsonStr);
                Map<String, Object> cancelMap = OrderMapGenerator.makeNetCancel(cancelResultData);
                cancelMap.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
                cancelMap.put("isrtIp", OrderInvoiceUtil.getClientIP(request));
                insertNetCancel(cancelMap);
                log.debug("insertNetCancel : {}", cancelMap);

                return ResponseCode.SYS_4001.getCode();
            } else {
                HashMap resultData = OrderInvoiceUtil.jsonStringToHashMap(resultJsonStr);
                log.debug("saveShopPayment insertPGResult : {}", resultData);
                insertPGResult(OrderMapGenerator.makeFinalResult(request, resultData));
                res = resultData;
                
                /* 10. 결제 성공 */
                if (OrderValueGenerator.isPaySuccess(resultData)) {
                	/* 10.1.1 ITREV_PAID 테이블에 저장 */
                    Map<String, Object> paidMap = OrderMapGenerator.makeInsertPaid(request, resultData);
                    paidDAO.insertPaid(paidMap);
                    log.info("### isPaySuccess ###");
                } else {
                    /* 결제 실패인 경우 에러코드 */
                    return ResponseCode.SYS_5000.getCode();
                }
            }
        } else {
            niceCertMap.put("status", "FAIL");
            niceCertMap.put("authDate", OrderInvoiceUtil.getyyyyMMddHHmmss());
            insertCertification(niceCertMap);

            return ResponseCode.SYS_4000.getCode();
        }

        return ResponseCode.SYS_0000.getCode();
    }
    
    public int insertCertification(Map<String, Object> map) throws Exception {
        return condoDAO.insertCertification(map);
    }
    
    public int insertApprovalRequest(Map<String, Object> map) throws Exception {
        return condoDAO.insertApprovalRequest(map);
    }

    public int insertNetCancel(Map<String, Object> map) throws Exception {
        return condoDAO.insertNetCancel(map);
    }

    public int insertPGResult(Map<String, Object> map) throws Exception {
        if (map == null) {
            return 0;
        }
        return condoDAO.insertPGResult(map);
    }

    public String getCardInfo(Map<String, Object> map) throws Exception {
    	return condoDAO.getCardInfo(map);
    }
    
    public int insertCondoPay(Map<String, Object> map) throws Exception {
        return condoDAO.insertCondoPay(map);
    }
    public int insertCondoPayDetail(Map<String, Object> map) throws Exception {
        return condoDAO.insertCondoPayDetail(map);
    }

    public List<CondoPayHistoryVO> selectCondoPay(Map<String, Object> map) throws Exception {
        return condoDAO.selectCondoPay(map);
    }
    public CondoPayHistoryVO selectCondoPayDetail(Map<String, Object> map) throws Exception {
        return condoDAO.selectCondoPayDetail(map);
    }
    public List<CondoPayHistoryVO> selectCondoPayDetailAmount(Map<String, Object> map) throws Exception {
        return condoDAO.selectCondoPayDetailAmount(map);
    }
    public String condoPaymentDate(Map<String, Object> map) throws Exception {
        return condoDAO.condoPaymentBuyDate(map);
    }
    public String selectCondoSeqData(Map<String, Object> map) throws Exception {
        return condoDAO.selectCondoSeq(map);
    }
    public CondoPayHistoryVO selectCondoPaymentInfo(Map<String, Object> map) throws Exception {
        return condoDAO.selectCondoPaymentInfo(map);
    }

    public int updateCondoPayPunish(Map<String, Object> map) throws Exception {
        return condoDAO.updateCondoPayPunish(map);
    }

    /* 콘도 결제내역 조회 */
    public List<CondoPayHistoryVO> condoPayHistoryList(String userId, String startDate, String endDate) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return selectCondoPay(map);
    }

    /* 콘도 결제내역 상세 조회 */
    public CondoPayHistoryVO selectCondoPayDetail(String userId, String reserveNo, String payDate) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("reserveNo", reserveNo);
        map.put("payDate", payDate);
        return selectCondoPayDetail(map);
    }

    /* 콘도 일자별 결제금액 조회 */
    public List<CondoPayHistoryVO> selectCondoPayDetailAmount(String userId, String reserveNo, String payDate) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("reserveNo", reserveNo);
        map.put("payDate", payDate);
        return selectCondoPayDetailAmount(map);
    }

    /* 콘도 결제일 조회 */
    public String condoPaymentBuyDate(String userId, String reserveNo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("reserveNo", reserveNo);
        return condoPaymentDate(map);
    }

    /* 결제취소 상세내용 조회 */
    public Map<String, Object> selectCancelDetail(Map<String, Object> map) throws Exception {
        return condoDAO.selectCancelDetail(map);
    }

    public int updateCondoPay(Map<String, Object> map) throws Exception {
        return condoDAO.updateCondoPay(map);
    }

    /* 결제한 정보 condoSEQ 조회 */
    public String selectCondoSeq(String userId, String reserveNo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("reserveNo", reserveNo);
        return selectCondoSeqData(map);
    }

    /* 회원등록을 위한 암호화데이터 조회 */
    public Map<String, Object> selectEncryptedData(String resNo) throws Exception {
        return condoDAO.selectEncryptedData(resNo);
    }

    @Override
    public int myCondoCnt(String userId) throws Exception {
        return condoDAO.myCondoCnt(userId);
    }
}
