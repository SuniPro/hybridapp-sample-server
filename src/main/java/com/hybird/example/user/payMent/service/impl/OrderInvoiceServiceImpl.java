package com.hybird.example.user.payMent.service.impl;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.user.payMent.service.PaidService;
import com.hybird.example.user.payMent.service.OrderInvoiceService;
import com.hybird.example.user.payMent.service.PaidVO;
import com.hybird.example.user.payMent.util.*;
import com.hybird.example.user.payMent.vo.*;
import com.plnc.user.payMent.util.*;
import com.plnc.user.payMent.vo.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@Service("OrderInvoiceService")
@Transactional(transactionManager = "txManager", readOnly = false)
public class OrderInvoiceServiceImpl implements OrderInvoiceService {
    @Resource(name = "OrderInvoiceDAO")
    private OrderInvoiceDAO orderInvoiceDAO;

    @Resource(name = "PaidDAO")
    private PaidDAO paidDAO;

    @Resource
    private PaidService paidService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 구매하기 상품카운트
     * @param list
     * @param priceGrace
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(transactionManager = "txManager", readOnly = true)
    public int selectBasketCount(final List<MemberBasketVO> list, final String priceGrace) throws Exception {
        return orderInvoiceDAO.selectBasketCount(list, priceGrace);
    }

    /**
     * 구매하기 상품 목록
     * @param list
     * @param priceGrace
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(transactionManager = "txManager", readOnly = true)
    public List<OrderTicketVO> listBasketGoods(List<MemberBasketVO> list, String priceGrace) throws Exception {
        return orderInvoiceDAO.listBasketGoods(list, priceGrace);
    }

    @Override
    public String getOrderNoMakeSeq() throws Exception {
        return orderInvoiceDAO.getOrderNoMakeSeq();
    }

    @Override
    public String getOrderSequence() throws Exception {
        return orderInvoiceDAO.getOrderSequence();
    }

    @Override
    public String getOrderDetailSequence() throws Exception {
        return orderInvoiceDAO.getOrderDetailSequence();
    }

    @Override
    public int insertShopOrder(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertShopOrder(map);
    }

    @Override
    public int insertShopOrderDetail(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertShopOrderDetail(map);
    }

    @Override
    public int insertPGRequest(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertPGRequest(map);
    }

    @Override
    @Transactional(transactionManager = "txManager", rollbackFor = CmmnUserException.class)
    public void saveShopOrder(HttpServletRequest request, Map<String, Object> map, Model model, List<OrderTicketVO> orderList)  throws Exception {
        /* 1 주문번호 생성 */
        final String orderNumber = OrderInvoiceUtil.makeOrderNumber(getOrderNoMakeSeq());
        /* 2 주문정보 생성 및 저장 */
        Map<String, Object> orderMap = OrderMapGenerator.makeShopOrder(request, orderList);
        final String orderSeq = getOrderSequence();
        orderMap.put("orderSeq", orderSeq);
        orderMap.put("orderNo", orderNumber);
        insertShopOrder(orderMap);

        /* 3. 주문상세 저장 */
        for (OrderTicketVO ticket : orderList) {
            Map<String, Object> detailMap = OrderMapGenerator.makeShopOrderDetail(request, ticket);
            detailMap.put("orderSeq", orderSeq);
            detailMap.put("orderNo", orderNumber);
            insertShopOrderDetail(detailMap);
        }

        /* 4. SERTB_PG_REQUEST 테이블에 저장할 데이터와 PG로 보낼 데이터 생성 */
        String payMethod = ((String)map.get("payMethod"));
        String ediDate = OrderInvoiceUtil.getyyyyMMddHHmmss();
        String merchantKey = OrderValueGenerator.getMerchantKey(payMethod);
        String merchantID = OrderValueGenerator.getMerchantId(payMethod);

        DataEncrypt sha256Enc = new DataEncrypt();
        String hashString = sha256Enc.encrypt(ediDate + merchantID + OrderValueGenerator.getTotalMemberPrice(orderList) + merchantKey);

        /* 5. SERTB_PG_REQUEST 테이블에 저장 */
        Map<String, Object> saveRequest = OrderMapGenerator.makeSavePGRequest(request, orderList, payMethod);
        saveRequest.put("orderNo", orderNumber);
        saveRequest.put("signData", hashString);
        saveRequest.put("ediDate", ediDate);
        insertPGRequest(saveRequest);

        /* 6. PG로 보낼 데이터 생성 */
        Map<String, Object> sendPgMap = OrderMapGenerator.makePGRequest(request, orderList, payMethod);
        sendPgMap.put("Moid", orderNumber);  // 주문번호
        sendPgMap.put("EdiDate", ediDate);
        sendPgMap.put("SignData", hashString);
        sendPgMap.put("WapUrl", OrderValueGenerator.WAP_URL); //Web-View 연동시 ios에서 필요
        sendPgMap.put("IspCancelUrl", OrderValueGenerator.ISP_CANCEL_URL); //Web-View 연동시 ios에서 필요

        model.addAttribute("result",sendPgMap);
    }

    @Override
    public int insertCertification(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertCertification(map);
    }

    @Override
    public int insertApprovalRequest(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertApprovalRequest(map);
    }

    @Override
    public int insertNetCancel(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertNetCancel(map);
    }

    @Override
    public int insertPGResult(Map<String, Object> map) throws Exception {
        if (map == null) {
            return 0;
        }
        return orderInvoiceDAO.insertPGResult(map);
    }

    @Override
    public int insertShopPayment(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertShopPayment(map);
    }

    @Override
    public int insertShopPaymentDetail(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertShopPaymentDetail(map);
    }

    @Override
    public int insertShopPaymentQrcode(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertShopPaymentQrcode(map);
    }

    @Override
    public int insertItrevActivity(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertItrevActivity(map);
    }

    @Override
    public int insertItqrTicketDtl(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertItqrTicketDtl(map);
    }

    @Override
    public String getQRCode() throws Exception {
        return orderInvoiceDAO.getQRCode();
    }

    @Override
    public List<TicketInformation> listUnitTicketGoods(String ticketSeq, String priceGrade) throws Exception {
        return orderInvoiceDAO.listUnitTicketGoods(ticketSeq, priceGrade);
    }

    @Override
    public List<TicketInformation> listPackageTicketGoods(String ticketSeq, String priceGrade) throws Exception {
        return orderInvoiceDAO.listPackageTicketGoods(ticketSeq, priceGrade);
    }

    @Override
    public int insertPaid(Map<String, Object> map) throws Exception {
        return paidDAO.insertPaid(map);
    }

    @Override
    public String selectQRCode(String orderSequence, String orderDetailSequence) throws Exception {
        return orderInvoiceDAO.selectQRCode(orderSequence, orderDetailSequence);
    }

    @Override
    @Transactional(transactionManager = "txManager", rollbackFor = CmmnUserException.class)
    public String saveShopPayment(HttpServletRequest request, Model model) throws Exception {
        /* 1. 로그인 체크  */
        if (!OrderInvoiceUtil.isLogin(request)) {
            return ResponseCode.SYS_1000.getCode();
        }
        /* 2. 나이스 페이로 부터 받은 데이터 한글 수신을 위해서 인코딩(다음 순서 중요 */
        request.setCharacterEncoding("euc-kr");
        Map<String, Object> niceCertMap = OrderMapGenerator.makeRecvCertification(request);
        log.debug("niceCertMap : {}", niceCertMap);
        request.setCharacterEncoding("utf-8");
        /* 3. Session에 주문할 상품 유효성 체크 */
        OrderSessionVO orderSessionVO = OrderInvoiceUtil.getSessionForOrderInvoice(request);
        List<OrderTicketVO> orderList = null;
        if (orderSessionVO == null || orderSessionVO.getOrderList() == null || orderSessionVO.getOrderList().size() == 0) {
            return ResponseCode.SYS_2000.getCode();
        } else {
            orderList = orderSessionVO.getOrderList();
        }
        log.info("orderList : {}", orderList);
        /* 4. 결제 인증 결과 유효성 체크 */
        String code = OrderValidator.validateCertification(niceCertMap);
        log.debug("validateCertification : {}", code);
        if (!code.equals(ResponseCode.SYS_0000.getCode())) {
            return code;
        }
        /* 5. PG 인증 정보 저장 */
        String ediDate = OrderInvoiceUtil.getyyyyMMddHHmmss();
        niceCertMap.put("status", "PAYMENT");
        niceCertMap.put("authDate", ediDate);
        insertCertification(niceCertMap);
        log.debug("insertCertification : {}", niceCertMap);

        /* ###. 결제 진행 ### */
        String authResultCode = String.valueOf(niceCertMap.get("authResultCode"));
        log.debug("authResultCode : {}", authResultCode);
        if ("0000".equals(authResultCode)) {
            /* 6. 결제 요청 */
            log.debug("ediDate : {}", ediDate);
            String merchantKey = OrderValueGenerator.searchMerchantKey(String.valueOf(niceCertMap.get("mid")));
            log.debug("merchantKey : {}", merchantKey);

            DataEncrypt sha256Enc = new DataEncrypt();
            String encStr = String.valueOf(niceCertMap.get("authToken")) + String.valueOf(niceCertMap.get("mid")) + String.valueOf(niceCertMap.get("amt")) + ediDate + merchantKey;
            String hashString = sha256Enc.encrypt(encStr);
            /* 7. 결제인증 요청 데이터 저장 */
            Map<String, Object> approveMap = OrderMapGenerator.makeApprovalRequest(niceCertMap, ediDate, hashString);
            approveMap.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
            approveMap.put("isrtIp", OrderInvoiceUtil.getClientIP(request));
            insertApprovalRequest(approveMap);
            log.debug("insertApprovalRequest : {}", approveMap);

            /* 8. 나이스페이에 결제인증 요청 */
            log.debug("makePaymentRequest : {}", OrderMapGenerator.makePaymentRequest(niceCertMap, ediDate, hashString));
            String resultJsonStr = OrderInvoiceUtil.connectToServer(OrderMapGenerator.makePaymentRequest(niceCertMap, ediDate, hashString), String.valueOf(niceCertMap.get("nextAppURL")));
            log.debug("connectToServer : {}", resultJsonStr);
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
                log.debug("insertPGResult : {}", resultData);
                insertPGResult(OrderMapGenerator.makeFinalResult(request, resultData));
                System.out.println("isPaySuccess : " + OrderValueGenerator.isPaySuccess(resultData));
                /* 10. 결제 성공인 경우 결제완료 테이블 및 QRCode 테이블에 저장 */
                if (OrderValueGenerator.isPaySuccess(resultData)) {
                    log.info("### isPaySuccess ###");
                    /* 10.1 SERTB_SHOP_PAYMENT 테이블에 저장 */
                    String orderSequence = getOrderSequence();
                    log.debug("orderSequence : {}", orderSequence);
                    Map<String, Object> paymentMap = OrderMapGenerator.makeShopPayment(request, resultData, orderList);
                    paymentMap.put("orderSeq", orderSequence);
                    model.addAttribute("payment", paymentMap);
                    insertShopPayment(paymentMap);
                    log.debug("insertShopPayment : {}", paymentMap);
                    /* 10.1.1 ITREV_PAID 테이블에 저장 */
                    Map<String, Object> paidMap = OrderMapGenerator.makeInsertPaid(request, resultData, orderList);
                    insertPaid(paidMap);
                    log.debug("insertPaid : {}", paidMap);
                    /* 10.2 SERTB_SHOP_PAYMENT_DTL, SERTB_SHOP_PAYMENT_QRCODE, ITREV_ACTIVITY, ITQR_TICKET_DTL 테이블에 저장 */
                    String orderNumber = OrderValueGenerator.getValueForResultMap(resultData, "Moid");
                    log.debug("orderNumber : {}", orderNumber);
                    List<Map<String, Object>> listPaymentDetail = new ArrayList<>();
                    /* 결제 내역을 담을 변수 */
                    List<OrderTicketVO> paymentList = new ArrayList<>();
                    for (OrderTicketVO ticketVO : orderList) {
                        ticketVO.setOrderNumber(orderNumber);
                        ticketVO.setOrderSequence(orderSequence);
                        ticketVO.setMemberId(OrderInvoiceUtil.getMemberAttribute(request,"id"));
                        log.info("ticketVO : {}", ticketVO);

                        /* 10.2.1 티켓 상품 정보 조회 */
                        List<TicketInformation> listGoods = null;
                        if ("S".equals(ticketVO.getMuMpFlag())) {
                            listGoods = listUnitTicketGoods(ticketVO.getTicketSeq(), OrderInvoiceUtil.getMemberType(request) + "P");
                        } else if ("P".equals(ticketVO.getMuMpFlag())) {
                            listGoods = listPackageTicketGoods(ticketVO.getTicketSeq(), OrderInvoiceUtil.getMemberType(request) + "P");
                        }
                        if (listGoods == null || listGoods.size() == 0) {
                            continue;
                        }

                        int orderDetailNo = 1;
                        for (int i = 0, ticketNo = ticketVO.getTicketNo(); i < ticketNo; i++) {
                            /* 10.2.2 SERTB_SHOP_PAYMENT_DTL 테이블에 저장장 */
                            String orderDetailSequence = getOrderDetailSequence();
                            OrderTicketVO paymentTicketVO = OrderMapGenerator.makePaymentList(ticketVO);
                            if (paymentTicketVO != null) {
                                paymentTicketVO.setOrderDetailSequence(orderDetailSequence);
                                paymentList.add(paymentTicketVO);
                            }
                            ticketVO.setOrderDetailSequence(orderDetailSequence);
                            Map<String, Object> detailMap = OrderMapGenerator.makeShopPaymentDetail(request, ticketVO);
                            detailMap.put("orderDtlSeq", orderDetailSequence);
                            detailMap.put("orderSeq", orderSequence);
                            detailMap.put("orderNo", orderNumber);
                            listPaymentDetail.add(detailMap);

                            log.debug("insertShopPaymentDetail : {}", detailMap);
                            insertShopPaymentDetail(detailMap);

                            /* 10.2.3 QRCode 생성, 패키지 상품은 같은 QRCode로 생성 */
                            String qrcode = getQRCode();
                            for (TicketInformation good : listGoods) {
                                /* 10.2.4 SERTB_SHOP_PAYMENT_QRCODE에 저장 */
                                Map<String, Object> qrcodeMap = OrderMapGenerator.makeShopPaymentQRCode(request, good);
                                qrcodeMap.put("orderSeq", orderSequence);
                                qrcodeMap.put("orderDtlSeq", orderDetailSequence);
                                qrcodeMap.put("orderDtlNo", orderDetailNo);
                                qrcodeMap.put("paymentQrcode", qrcode);
                                log.debug("insertShopPaymentQrcode : {}", qrcodeMap);
                                insertShopPaymentQrcode(qrcodeMap);

                                /* 10.2.5 ITREV_ACTIVITY 테이블에 저장 */
                                /* 회원번호 설정 */
                                good.setIaMemberNo(OrderInvoiceUtil.getMemberAttribute(request, "memberNo"));
                                Map<String, Object> activityMap = OrderMapGenerator.makeItrevActivity(request, good);
                                activityMap.put("iaQrcode", qrcode);
                                activityMap.put("iaPurNo", orderNumber);
                                log.debug("insertItrevActivity : {}", activityMap);
                                insertItrevActivity(activityMap);

                                /* 10.2.6 ITQR_TICKET_DTL 테이블에 저장 */
                                Map<String, Object> activityDtlMap = OrderMapGenerator.makeItqrTicketDtl(good);
                                activityDtlMap.put("tkQrcode", qrcode);
                                activityDtlMap.put("tkCode", qrcode + good.getIaOutletCd());
                                log.debug("insertItqrTicketDtl : {}", activityDtlMap);
                                insertItqrTicketDtl(activityDtlMap);

                                orderDetailNo++;
                            }
                        }
                        /* 10.2.7 장바구니에서 삭제 */
                        deleteShopBasket(ticketVO);
                    }
                    model.addAttribute("payMethod", resultData.get("PayMethod"));
                    if (resultData.get("PayMethod") != null) {
                        model.addAttribute("payName", OrderValueGenerator.getPaymentName(String.valueOf(resultData.get("PayMethod"))));
                    } else {
                        model.addAttribute("payName", "");
                    }
                    model.addAttribute("cardCode", resultData.get("CardCode"));
                    model.addAttribute("cardName", resultData.get("CardName"));
                    model.addAttribute("cardNo", resultData.get("CardNo"));
                    model.addAttribute("cardQuota", resultData.get("CardQuota"));
                    model.addAttribute("payDate", OrderInvoiceUtil.calculateDateFormat(0L, "yyyy.MM.dd"));
                    model.addAttribute("limitStart", OrderInvoiceUtil.calculateDateFormat(0L, "yyyy.MM.dd"));
                    model.addAttribute("limitEnd", OrderInvoiceUtil.calculateDateFormat(31L, "yyyy.MM.dd"));
                    model.addAttribute("orderList", orderList);
                    model.addAttribute("paymentList", paymentList);
                    model.addAttribute("paymentDetail", listPaymentDetail);
                    model.addAttribute("memName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
                    model.addAttribute("memEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
                    model.addAttribute("memPhone", OrderInvoiceUtil.getMemberAttribute(request,"handphone1") + "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone2") + "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone3"));
                    model.addAttribute("totalTicketNo", OrderValueGenerator.getTotalTicketCount(orderList));
                    model.addAttribute("totalNormalPrice", OrderValueGenerator.getTotalNormalPrice(orderList));
                    model.addAttribute("totalDiscountPrice", OrderValueGenerator.getTotalDiscountPrice(orderList));
                    model.addAttribute("totalMemberPrice", OrderValueGenerator.getTotalMemberPrice(orderList));
                    model.addAttribute("orderNumber", orderNumber);
                    //결제 완료 페이지 세팅
                    OrderInvoiceUtil.saveSessionForTicketPayList(request, paymentList);
                    orderSessionVO.setCompleteMap(model.asMap());
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

        /* Session에서 구매목록 제거 */
        orderSessionVO.setOrderList(null);
        orderSessionVO.setQuery(null);
        OrderInvoiceUtil.saveSessionForOrderInvoice(request, orderSessionVO);
//        OrderInvoiceUtil.removeSession(request, "orderInvoice");
//        OrderInvoiceUtil.removeSession(request, "query");

        return ResponseCode.SYS_0000.getCode();
    }

    @Override
    public CancelTicketVO getCancelTicket(String orderSequence, String orderDetailSequence) throws Exception {
        return orderInvoiceDAO.getCancelTicket(orderSequence, orderDetailSequence);
    }

    @Override
    public int selectTicketUsedCount(String orderSequence, String orderDetailSequence) throws Exception {
        return orderInvoiceDAO.selectTicketUsedCount(orderSequence, orderDetailSequence);
    }

    @Override
    public List<String> listTicketQRCode(String orderSequence, String orderDetailSequence) throws Exception {
        return orderInvoiceDAO.listTicketQRCode(orderSequence, orderDetailSequence);
    }

    @Override
    public int insertClientCancelRequest(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertClientCancelRequest(map);
    }

    @Override
    public int insertPGCancelRequest(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertPGCancelRequest(map);
    }

    @Override
    public int insertCancelResult(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertCancelResult(map);
    }

    @Override
    public String getCancelSequence() throws Exception {
        return orderInvoiceDAO.getCancelSequence();
    }

    @Override
    public int updatePaymentForCancel(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.updatePaymentForCancel(map);
    }

    @Override
    public int updatePaymentDetailForCancel(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.updatePaymentDetailForCancel(map);
    }

    @Override
    public int updateItrevActivityForCancel(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.updateItrevActivityForCancel(map);
    }

    @Override
    public int updatePaid(Map<String, Object> map) throws Exception {
        return paidDAO.updatePaid(map);
    }

    @Override
    public int selectMaxCancelSequence(String ipPurNo) throws Exception {
        return paidDAO.selectMaxCancelSequence(ipPurNo);
    }

    @Override
    public int insertPaidVoid(Map<String, Object> map) throws Exception {
        return paidDAO.insertPaidVoid(map);
    }

    @Override
    public void runExpireDate() throws Exception {
        HashMap<String, Object> map =   new HashMap<String, Object>();
        map.put("updtId", "batch");
        map.put("updtIp", "127.0.0.1");
        paidDAO.updateExpireDateTckState(map);
    }

    @Override
    @Transactional(transactionManager = "txManager", rollbackFor = CmmnUserException.class)
    public String cancelPayment(HttpServletRequest request, Model model) throws Exception {
        /* 1. 요청 파라미터 체크 */
        String orderSequence = request.getParameter("orderSequence");
        String orderDetailSequence = request.getParameter("orderDetailSequence");
        if (orderSequence == null || orderDetailSequence == null) {
            return ResponseCode.SYS_6000.getCode();
        }
        /* 2. 로그인 체크  */
        if (!OrderInvoiceUtil.isLogin(request)) {
            return ResponseCode.SYS_1000.getCode();
        }
        /* 3. 결제정보 조회 및 유효성 체크 */
        CancelTicketVO ticketVO = getCancelTicket(orderSequence, orderDetailSequence);
        String code = OrderValidator.validateCancelTicket(ticketVO);
        if (!code.equals(ResponseCode.SYS_0000.getCode())) {
            return code;
        }
        /* 4. 로그인한 고객과 주문자 아이디가 같은지 체크 */
        if (!ticketVO.getMemId().equals(OrderInvoiceUtil.getMemberAttribute(request, "id"))) {
            return ResponseCode.SYS_6003.getCode();
        }
        /* 5. 사용한 이력이 있는지 체크 */
        int useFlag = selectTicketUsedCount(orderSequence, orderDetailSequence);
        if (useFlag > 0) {
            return ResponseCode.SYS_6004.getCode();
        }
        /* 6. 정상처리 시작 */
        ticketVO.setOrderDtlSeq(orderDetailSequence);   // 결제 상세 시퀀스
        /* 6.1 고객취소 요청 테이블에 저장 :  */
        Map<String, Object> clientRequestMap = OrderMapGenerator.makeClientCancelRequest(request, ticketVO);
        insertClientCancelRequest(clientRequestMap);

        DataEncrypt sha256Enc 	= new DataEncrypt();
        String ediDate			= OrderInvoiceUtil.getyyyyMMddHHmmss();
        String signData 		= sha256Enc.encrypt(ticketVO.getMid() + ticketVO.getMemAmount() + ediDate + OrderValueGenerator.searchMerchantKey(ticketVO.getMid()));
        /* 6.2 나이스페이 취소 전문 요청 */
        String resultJsonStr = OrderInvoiceUtil.connectToServer(OrderMapGenerator.makeCancelRequest(ticketVO, ediDate, signData), "https://webapi.nicepay.co.kr/webapi/cancel_process.jsp");

        /* 6.3 나이스페이 취소 전문 저장 */
        Map<String, Object> cancelApiMap = OrderMapGenerator.makePGCancelRequest(request, ticketVO, signData, ediDate);
        insertPGCancelRequest(cancelApiMap);

        if ("9999".equals(resultJsonStr)) {
            log.info("Network Error : {}", resultJsonStr);
            return ResponseCode.SYS_6005.getCode();
        } else {
            HashMap resultData = OrderInvoiceUtil.jsonStringToHashMap(resultJsonStr);
            if (OrderValueGenerator.isCancelySuccess(resultData)) {
                /* 6.4 취소 성공인 경우 결과 저장 */
                Map<String, Object> resultMap = OrderMapGenerator.makeShopCancel(request, resultData, ticketVO);
                String cancelSequence = getCancelSequence();
                resultMap.put("pcsSeq", cancelSequence);
                insertCancelResult(resultMap);

                /* 6.5 SERTB_SHOP_PAYMENT 취소처리 */
                Map<String, Object> paymentMap = OrderMapGenerator.makeUpdatePaymentForCancel(request, ticketVO);
                updatePaymentForCancel(paymentMap);

                /* 6.6 SERTB_SHOP_PAYMENT_DTL 취소처리 */
                Map<String, Object> detailMap = OrderMapGenerator.makeUpdatePaymentDetailForCancel(request, ticketVO);
                detailMap.put("cancelSeq", cancelSequence);
                updatePaymentDetailForCancel(detailMap);

                /* 6.7 ITREV_ACTIVITY 취소처리 & ITREV_PAID_VOID용 Cancel_Sequence 생성 */
                int pvCancelSeq = selectMaxCancelSequence(ticketVO.getOrderNo());
                List<String> qrcodeList = listTicketQRCode(ticketVO.getOrderSeq(), ticketVO.getOrderDtlSeq());
                if (qrcodeList != null && qrcodeList.size() > 0) {
                    for (String qrCode : qrcodeList) {
                        Map<String, Object> qrcodeMap = OrderMapGenerator.makeUpdateItrevActivityForCancel(request, ticketVO);
                        qrcodeMap.put("iaQrcode", qrCode);
                        qrcodeMap.put("iaCancelSeq", pvCancelSeq);
                        updateItrevActivityForCancel(qrcodeMap);
                    }
                }

                /* 6.8 ITREV_PAID_VOID 취소내역 저장 */
                Map<String, Object> voidMap = OrderMapGenerator.makePaidVoid(request, ticketVO);
                voidMap.put("pvCancelSeq", pvCancelSeq);
                log.info("voidMap : {}", voidMap);
                insertPaidVoid(voidMap);
                /* 6.9 ITREV_PAID 취소 처리 업데이트 */
                Map<String, Object> paidMap = OrderMapGenerator.makeUpdatePaid(request, ticketVO);
                log.info("paidMap : {}", paidMap);
                updatePaid(paidMap);
                /* 6.10 지불내역 취소를 위한 데이터 반환 */
                model.addAttribute("cancelAmt", ticketVO.getMemAmount());
            } else {
                log.info("Cancel Fail : {}", resultJsonStr);
                return ResponseCode.SYS_6006.getCode();
            }
        }

        model.addAttribute("orderSequence", orderSequence);
        model.addAttribute("orderDetailSequence", orderDetailSequence);

        return ResponseCode.SYS_0000.getCode();
    }

    /* ### 간편결제 관련 시작 */
    @Override
    public List<Map<String, Object>> listSimpleCard(String userId) throws Exception {
        return orderInvoiceDAO.listSimpleCard(userId);
    }

    @Override
    public int insertBillKeyRequest(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertBillKeyRequest(map);
    }

    @Override
    public int insertBillKeyResponse(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertBillKeyResponse(map);
    }

    @Override
    public int updateBillKeyResponse(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.updateBillKeyResponse(map);
    }

    @Override
    public int getBillCardCount(String cardName, String cardNo, String userId) throws Exception {
        return orderInvoiceDAO.getBillCardCount(cardName, cardNo, userId);
    }

    @Override
    public Map<String, Object> getBillCardSelect(String userId, String billSeq) throws Exception {
        return orderInvoiceDAO.getBillCardSelect(userId, billSeq);
    }

    @Override
    public int insertBillingResult(Map<String, Object> map) throws Exception {
        return orderInvoiceDAO.insertBillingResult(map);
    }

    @Override
    public int deleteShopBasket(OrderTicketVO ticketVO) throws Exception {
        return orderInvoiceDAO.deleteShopBasket(ticketVO);
    }

    @Override
    public void billSignUp(HttpServletRequest request, Map<String, Object> map, Model model) throws Exception {
        /* 1. 주문번호 생성 */
        final String orderNumber = OrderInvoiceUtil.makeOrderNumber(getOrderNoMakeSeq());
        /* 2. 간편결제 등록을 위한 Map */
        Map<String, Object> signUpMap = OrderMapGenerator.makeBillSignUp(request);
        signUpMap.put("GoodsName", map.get("mName"));
        signUpMap.put("Amt", map.get("price"));
        signUpMap.put("Moid", orderNumber);
        signUpMap.put("rqstDate", OrderInvoiceUtil.getyyyyMMddHHmmss());
        model.addAttribute("result",signUpMap);
        /* 3. 요청 데이터 저장 */
        Map<String, Object> billKeyRequestMap = OrderMapGenerator.makeBillKeyRequest(request);
        billKeyRequestMap.put("orderNo", orderNumber);
        billKeyRequestMap.put("ticketName", map.get("mName"));
        billKeyRequestMap.put("sumMemAmount", map.get("price"));
        insertBillKeyRequest(billKeyRequestMap);
    }

    @Override
    public String billSignUpResponse(HttpServletRequest request, Model model) throws Exception {
        /* 1. 나이스 페이로 부터 받은 데이터 한글 수신을 위해서 인코딩(다음 순서 중요 */
        request.setCharacterEncoding("euc-kr");
        /* 2. 간편결제 카드등록 응답값 */
        Map<String, Object> responseMap = OrderMapGenerator.makeBillKeyResponse(request);
        log.info("BillKey Response : {}", responseMap);

        String resultCode = request.getParameter("resultCode");
        if (resultCode == null) {
            model.addAttribute("code",ResponseCode.SYS_7000.getCode());
            model.addAttribute("msg", ResponseCode.SYS_7000.getMessage());
            return ResponseCode.SYS_7000.getCode();
        } else {
            if(resultCode.equals("F100")) {
                /* 3. 등록된 카드번호 조회 */
                responseMap.put("status", "SUCCESS");
                int cardCount = getBillCardCount(request.getParameter("cardName"), request.getParameter("CardNo"), OrderInvoiceUtil.getMemberAttribute(request,"id"));
                if (cardCount == 0) {
                    insertBillKeyResponse(responseMap);
                } else {
                    updateBillKeyResponse(responseMap);
                }
            } else {
                model.addAttribute("code", ResponseCode.SYS_7001.getCode());
                model.addAttribute("msg", request.getParameter("resultMsg"));
                return ResponseCode.SYS_7001.getCode();
            }
        }

        return ResponseCode.SYS_0000.getCode();
    }

    @Override
    @Transactional(transactionManager = "txManager", rollbackFor = CmmnUserException.class)
    public String saveShopBillPayment(HttpServletRequest request, Model model) throws Exception {
        /* 1. Session에 주문할 상품 유효성 체크 */
        OrderSessionVO orderSessionVO = OrderInvoiceUtil.getSessionForOrderInvoice(request);
        List<OrderTicketVO> orderList = null;
        if (orderSessionVO == null || orderSessionVO.getOrderList() == null || orderSessionVO.getOrderList().size() == 0) {
            return ResponseCode.SYS_2000.getCode();
        } else {
            orderList = orderSessionVO.getOrderList();
        }
        log.info("orderList : {}", orderList);
        /* 2. 사용자 BillKey 조회 */
        String billSequence = request.getParameter("birSeq");
        Map<String, Object> billCardMap = getBillCardSelect(OrderInvoiceUtil.getMemberAttribute(request,"id"), billSequence);
        log.info("billCardMap : {}", billCardMap);
        if (billCardMap == null) {
            return ResponseCode.SYS_7002.getCode();
        }
        /* 3. 기본 데이터 생성 */
        String ediDate 		= OrderInvoiceUtil.getyyyyMMddHHmmss();									// 요청일시
        String tid 			= OrderValueGenerator.NICE_MERCHANT_ID_BILL + "01" + "16" + ediDate.substring(2) + OrderValueGenerator.makeRandomDigit(4, 1);	// 거래ID
        final String orderNumber = OrderInvoiceUtil.makeOrderNumber(getOrderNoMakeSeq());
        int amt	= OrderValueGenerator.getTotalMemberPrice(orderList); 				// 총 가격
        String goodsName = OrderValueGenerator.getPaymentGoodsName(orderList); 		// 상품명
        String cardInterest = "0"; 													// 이자여부 (어떻게 할건지)
        String cardQuota = request.getParameter("billInstl");

        DataEncrypt sha256Enc 	= new DataEncrypt();
        String signData = sha256Enc.encrypt(OrderValueGenerator.NICE_MERCHANT_ID_BILL + ediDate + orderNumber + OrderValueGenerator.getTotalMemberPrice(orderList) + billCardMap.get("BILLKEY") + OrderValueGenerator.NICE_MERCHANT_KEY_BILL);
        /* 4. 간편결제 요청 */
        StringBuffer requestData = new StringBuffer();
        requestData.append("BID=").append(billCardMap.get("BILLKEY")).append("&");
        requestData.append("MID=").append(OrderValueGenerator.NICE_MERCHANT_ID_BILL).append("&");
        requestData.append("TID=").append(tid).append("&");
        requestData.append("EdiDate=").append(ediDate).append("&");
        requestData.append("Moid=").append(orderNumber).append("&");
        requestData.append("Amt=").append(amt).append("&");
        requestData.append("GoodsName=").append(URLEncoder.encode(goodsName, "euc-kr")).append("&");
        requestData.append("SignData=").append(signData).append("&");
        requestData.append("CardInterest=").append(cardInterest).append("&");
        requestData.append("CardQuota=").append(cardQuota).append("&");
        requestData.append("CharSet=").append("utf-8");
        String resultJsonStr = OrderInvoiceUtil.connectToServer(requestData.toString(), "https://webapi.nicepay.co.kr/webapi/billing/billing_approve.jsp");

        log.info("resultJsonStr : {}", resultJsonStr);
        /* 5. 응답결과 처리 */
        //서버 통신 완료 결과 처리 작업 필요
        if ("9999".equals(resultJsonStr)) {
            return ResponseCode.SYS_4001.getCode();
        } else {
            HashMap resultData = OrderInvoiceUtil.jsonStringToHashMap(resultJsonStr);
            if("3001".equals(resultData.get("ResultCode"))) {
                /* 5.1 승인결과 테이블에 저장 */
                Map<String, Object> resultMap = OrderMapGenerator.makeBillResult(request, resultData);
                insertBillingResult(resultMap);



                String orderSequence = getOrderSequence();
                log.debug("orderSequence : {}", orderSequence);
                Map<String, Object> paymentMap = OrderMapGenerator.makeShopPayment(request, resultData, orderList);
                paymentMap.put("payMethod", "BILL");
                paymentMap.put("mid", OrderValueGenerator.NICE_MERCHANT_ID_BILL);
                paymentMap.put("orderSeq", orderSequence);
                model.addAttribute("payment", paymentMap);
                insertShopPayment(paymentMap);
                log.debug("insertShopPayment : {}", paymentMap);
                /* 10.1.1 ITREV_PAID 테이블에 저장 */
                Map<String, Object> paidMap = OrderMapGenerator.makeInsertPaid(request, resultData, orderList);
                insertPaid(paidMap);
                log.debug("insertPaid : {}", paidMap);
                /* 10.2 SERTB_SHOP_PAYMENT_DTL, SERTB_SHOP_PAYMENT_QRCODE, ITREV_ACTIVITY, ITQR_TICKET_DTL 테이블에 저장 */
                log.debug("orderNumber : {}", orderNumber);
                List<Map<String, Object>> listPaymentDetail = new ArrayList<>();
                /* 결제 내역을 담을 변수 */
                List<OrderTicketVO> paymentList = new ArrayList<>();
                for (OrderTicketVO ticketVO : orderList) {

                    ticketVO.setOrderNumber(orderNumber);
                    ticketVO.setOrderSequence(orderSequence);
                    ticketVO.setMemberId(OrderInvoiceUtil.getMemberAttribute(request,"id"));
                    log.info("ticketVO : {}", ticketVO);

                    /* 10.2.1 티켓 상품 정보 조회 */
                    List<TicketInformation> listGoods = null;
                    if ("S".equals(ticketVO.getMuMpFlag())) {
                        listGoods = listUnitTicketGoods(ticketVO.getTicketSeq(), OrderInvoiceUtil.getMemberType(request) + "P");
                    } else if ("P".equals(ticketVO.getMuMpFlag())) {
                        listGoods = listPackageTicketGoods(ticketVO.getTicketSeq(), OrderInvoiceUtil.getMemberType(request) + "P");
                    }
                    if (listGoods == null || listGoods.size() == 0) {
                        continue;
                    }



                    int orderDetailNo = 1;
                    for (int i = 0, ticketNo = ticketVO.getTicketNo(); i < ticketNo; i++) {
                        /* 10.2.2 SERTB_SHOP_PAYMENT_DTL 테이블에 저장장 */
                        String orderDetailSequence = getOrderDetailSequence();
                        OrderTicketVO paymentTicketVO = OrderMapGenerator.makePaymentList(ticketVO);
                        if (paymentTicketVO != null) {
                            paymentTicketVO.setOrderDetailSequence(orderDetailSequence);
                            paymentList.add(paymentTicketVO);
                        }
                        Map<String, Object> detailMap = OrderMapGenerator.makeShopPaymentDetail(request, ticketVO);
                        detailMap.put("orderDtlSeq", orderDetailSequence);
                        detailMap.put("orderSeq", orderSequence);
                        detailMap.put("orderNo", orderNumber);
                        listPaymentDetail.add(detailMap);

                        log.debug("insertShopPaymentDetail : {}", detailMap);
                        insertShopPaymentDetail(detailMap);

                        /* 10.2.3 QRCode 생성, 패키지 상품은 같은 QRCode로 생성 */
                        String qrcode = getQRCode();
                        for (TicketInformation good : listGoods) {
                            /* 10.2.4 SERTB_SHOP_PAYMENT_QRCODE에 저장 */
                            Map<String, Object> qrcodeMap = OrderMapGenerator.makeShopPaymentQRCode(request, good);
                            qrcodeMap.put("orderSeq", orderSequence);
                            qrcodeMap.put("orderDtlSeq", orderDetailSequence);
                            qrcodeMap.put("orderDtlNo", orderDetailNo);
                            qrcodeMap.put("paymentQrcode", qrcode);

                            log.debug("insertShopPaymentQrcode : {}", qrcodeMap);
                            insertShopPaymentQrcode(qrcodeMap);

                            /* 10.2.5 ITREV_ACTIVITY 테이블에 저장 */
                            /* 회원번호 설정 */
                            good.setIaMemberNo(OrderInvoiceUtil.getMemberAttribute(request, "memberNo"));
                            Map<String, Object> activityMap = OrderMapGenerator.makeItrevActivity(request, good);
                            activityMap.put("iaQrcode", qrcode);
                            activityMap.put("iaPurNo", orderNumber);
                            log.debug("insertItrevActivity : {}", activityMap);
                            insertItrevActivity(activityMap);

                            /* 10.2.6 ITQR_TICKET_DTL 테이블에 저장 */
                            Map<String, Object> activityDtlMap = OrderMapGenerator.makeItqrTicketDtl(good);
                            activityDtlMap.put("tkQrcode", qrcode);
                            activityDtlMap.put("tkCode", qrcode + good.getIaOutletCd());
                            log.debug("insertItqrTicketDtl : {}", activityDtlMap);
                            insertItqrTicketDtl(activityDtlMap);

                            orderDetailNo++;
                        }
                    }
                    /* 10.2.7 장바구니에서 삭제 */
                    deleteShopBasket(ticketVO);
                }
                model.addAttribute("payMethod", resultData.get("PayMethod"));
                if (resultData.get("PayMethod") != null) {
                    model.addAttribute("payName", OrderValueGenerator.getPaymentName(String.valueOf(resultData.get("PayMethod"))));
                } else {
                    model.addAttribute("payName", "");
                }
                model.addAttribute("cardCode", resultData.get("CardCode"));
                model.addAttribute("cardName", resultData.get("CardName"));
                model.addAttribute("cardNo", resultData.get("CardNo"));
                model.addAttribute("cardQuota", resultData.get("CardQuota"));
                model.addAttribute("payDate", OrderInvoiceUtil.calculateDateFormat(0L, "yyyy.MM.dd"));
                model.addAttribute("limitStart", OrderInvoiceUtil.calculateDateFormat(0L, "yyyy.MM.dd"));
                model.addAttribute("limitEnd", OrderInvoiceUtil.calculateDateFormat(31L, "yyyy.MM.dd"));
                model.addAttribute("orderList", orderList);
                model.addAttribute("paymentList", paymentList);
                model.addAttribute("paymentDetail", listPaymentDetail);
                model.addAttribute("memName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
                model.addAttribute("memEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
                model.addAttribute("memPhone", OrderInvoiceUtil.getMemberAttribute(request,"handphone1") + "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone2") + "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone3"));
                model.addAttribute("totalTicketNo", OrderValueGenerator.getTotalTicketCount(orderList));
                model.addAttribute("totalNormalPrice", OrderValueGenerator.getTotalNormalPrice(orderList));
                model.addAttribute("totalDiscountPrice", OrderValueGenerator.getTotalDiscountPrice(orderList));
                model.addAttribute("totalMemberPrice", OrderValueGenerator.getTotalMemberPrice(orderList));
                model.addAttribute("orderNumber", orderNumber);
                orderSessionVO.setCompleteMap(model.asMap());
            } else {
                return ResponseCode.SYS_7003.getCode();
            }
        }

        /* Session에서 구매목록 제거 */
        orderSessionVO.setOrderList(null);
        orderSessionVO.setQuery(null);
        OrderInvoiceUtil.saveSessionForOrderInvoice(request, orderSessionVO);
//        OrderInvoiceUtil.removeSession(request, "orderInvoice");
//        OrderInvoiceUtil.removeSession(request, "query");

        return ResponseCode.SYS_0000.getCode();
    }

    @Override
    public List<OrderTicketVO> listRefundPaid() throws Exception {
        return orderInvoiceDAO.listRefundPaid();
    }

    @Override
    public void doRefundPaid() throws Exception {
        List<OrderTicketVO> listCancel = listRefundPaid();
        if (listCancel == null || listCancel.size() == 0) {
            return;
        }

        for (OrderTicketVO ticketVO : listCancel) {
            String code = refundCancelPayment(ticketVO.getOrderSequence(), ticketVO.getOrderDetailSequence());
            log.info("ORDER_SEQ : {}, ORDER_DTL_SEQ : {}, RESULT : {}", ticketVO.getOrderSequence(), ticketVO.getOrderDetailSequence(), code);
        }
    }

    @Override
    @Transactional(transactionManager = "txManager", rollbackFor = CmmnUserException.class)
    public String refundCancelPayment(String orderSequence, String orderDetailSequence) throws Exception {
        /* 1. 결제정보 조회 및 유효성 체크 */
        CancelTicketVO ticketVO = getCancelTicket(orderSequence, orderDetailSequence);
        String code = OrderValidator.validateCancelTicket(ticketVO);
        if (!code.equals(ResponseCode.SYS_0000.getCode())) {
            return code;
        }
        /* 3. 사용한 이력이 있는지 체크 */
        int useFlag = selectTicketUsedCount(orderSequence, orderDetailSequence);
        if (useFlag > 0) {
            return ResponseCode.SYS_6004.getCode();
        }
        /* 4. 정상처리 시작 */
        ticketVO.setOrderDtlSeq(orderDetailSequence);   // 결제 상세 시퀀스
        /* 4.1 고객취소 요청 테이블에 저장 :  */
        Map<String, Object> clientRequestMap = OrderMapGenerator.makeRefundCancelRequest(ticketVO);
        insertClientCancelRequest(clientRequestMap);

        DataEncrypt sha256Enc 	= new DataEncrypt();
        String ediDate			= OrderInvoiceUtil.getyyyyMMddHHmmss();
        String signData 		= sha256Enc.encrypt(ticketVO.getMid() + ticketVO.getMemAmount() + ediDate + OrderValueGenerator.searchMerchantKey(ticketVO.getMid()));
        /* 4.2 나이스페이 취소 전문 요청 */
        String resultJsonStr = OrderInvoiceUtil.connectToServer(OrderMapGenerator.makeCancelRequest(ticketVO, ediDate, signData), "https://webapi.nicepay.co.kr/webapi/cancel_process.jsp");

        /* 4.3 나이스페이 취소 전문 저장 */
        Map<String, Object> cancelApiMap = OrderMapGenerator.makePGRefundCancelRequest(ticketVO, signData, ediDate);
        insertPGCancelRequest(cancelApiMap);

        if ("9999".equals(resultJsonStr)) {
            log.info("Network Error : {}", resultJsonStr);
            return ResponseCode.SYS_6005.getCode();
        } else {
            HashMap resultData = OrderInvoiceUtil.jsonStringToHashMap(resultJsonStr);
            if (OrderValueGenerator.isCancelySuccess(resultData)) {
                /* 4.4 취소 성공인 경우 결과 저장 */
                Map<String, Object> resultMap = OrderMapGenerator.makeShopRefundCancel(resultData, ticketVO);
                String cancelSequence = getCancelSequence();
                resultMap.put("pcsSeq", cancelSequence);
                insertCancelResult(resultMap);

                /* 4.5 SERTB_SHOP_PAYMENT 취소처리 */
                Map<String, Object> paymentMap = OrderMapGenerator.makeUpdatePaymentForRefundCancel(ticketVO);
                updatePaymentForCancel(paymentMap);

                /* 4.6 SERTB_SHOP_PAYMENT_DTL 취소처리 */
                Map<String, Object> detailMap = OrderMapGenerator.makeUpdatePaymentDetailForRefundCancel(ticketVO);
                detailMap.put("cancelSeq", cancelSequence);
                updatePaymentDetailForCancel(detailMap);

                /* 4.7 ITREV_ACTIVITY 취소처리 & ITREV_PAID_VOID용 Cancel_Sequence 생성 */
                int pvCancelSeq = selectMaxCancelSequence(ticketVO.getOrderNo());
                List<String> qrcodeList = listTicketQRCode(ticketVO.getOrderSeq(), ticketVO.getOrderDtlSeq());
                if (qrcodeList != null && qrcodeList.size() > 0) {
                    for (String qrCode : qrcodeList) {
                        Map<String, Object> qrcodeMap = OrderMapGenerator.makeUpdateItrevActivityForRefundCancel(ticketVO);
                        qrcodeMap.put("iaQrcode", qrCode);
                        qrcodeMap.put("iaCancelSeq", pvCancelSeq);
                        updateItrevActivityForCancel(qrcodeMap);
                    }
                }

                /* 6.8 ITREV_PAID_VOID 취소내역 저장 */
                Map<String, Object> voidMap = OrderMapGenerator.makeRefundPaidVoid(ticketVO);
                voidMap.put("pvCancelSeq", pvCancelSeq);
                log.info("voidMap : {}", voidMap);
                insertPaidVoid(voidMap);
                /* 6.9 ITREV_PAID 취소 처리 업데이트 */
                Map<String, Object> paidMap = OrderMapGenerator.makeUpdateRefundPaid(ticketVO);
                log.info("paidMap : {}", paidMap);
                updatePaid(paidMap);
                /* 6.10 지불내역 취소를 위한 데이터 조회 */
                PaidVO res = paidService.selectPaid(ticketVO.getOrderNo());
                log.info("res : {}", res);
                /* 6.11 지불내역 취소 api 호출 */
                MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
                parameters.add("resno", res.getResNo());
                parameters.add("member_name", ticketVO.getMemName());
                parameters.add("pur_no", ticketVO.getOrderNo());
                parameters.add("approve_no", res.getAuthCode());
                parameters.add("pr_code", res.getPrCode());
                parameters.add("cancel_dt", OrderInvoiceUtil.calculateDateFormat(0L, "yyyyMMdd"));
                parameters.add("cancel_amt", ticketVO.getMemAmount().toString());
                parameters.add("card_no", res.getCardNo());
                parameters.add("purc_name", res.getCardName());
                parameters.add("bookgNo", "");
                parameters.add("used_kb", "N");
                log.info("### param {} ###", parameters);
                String url = PaidValueGenerator.getUrl("cancel");
                ResponseEntity<String> apiResult = restTemplate.postForEntity(url, parameters, String.class);
                log.info("### api Success {} ###", apiResult.getBody());
            } else {
                log.info("Cancel Fail : {}", resultJsonStr);
                return ResponseCode.SYS_6006.getCode();
            }
        }

        return ResponseCode.SYS_0000.getCode();
    }
}
