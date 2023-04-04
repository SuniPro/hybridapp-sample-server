package com.hybird.example.user.payMent.web;

import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.PaymentUtil;
import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.user.payMent.service.*;
import com.hybird.example.user.payMent.util.*;
import com.plnc.user.payMent.service.*;
import com.plnc.user.payMent.util.*;
import com.hybird.example.user.payMent.vo.CondoSessionVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/user/condo")
public class CondoPayController {
    @Resource(name = "CondoService")
    private com.hybird.example.user.payMent.service.CondoService CondoService;

    @Resource(name = "OrderInvoiceService")
    private OrderInvoiceService orderInvoiceService;

    @Resource
    private PaidService paidService;

    /* 콘도 관련 결제 */
    @RequestMapping(path = "/condoPay/condoPayment.do", method = RequestMethod.POST)
    public String condoPayment(HttpServletRequest request, @RequestParam Map<String, Object> map, Model model, HttpServletResponse response) throws Exception {
        // 결제 요청전 condoPaymentMap 초기화
        Map<String, Object> condoPaymentMap = new HashMap<>();
        log.info("### 결제 요청 map : {} ###", map);
        String result = "SUCCESS";
        String msg = "SUCCESS";

        /* 로그인 체크  */
        if (!PaymentUtil.isLogin(request)) {
            result = "NO_LOGIN";
            msg = "로그인해주세요.";
            log.info("### 로그인이 안되어 있습니다. ###");
        }

        /* 1 주문번호 생성 */
        final String orderNumber = OrderInvoiceUtil.makeOrderNumber(CondoService.getOrdernoMakeSeq());
        /* 2 주문정보 생성 */
        Map<String, Object> orderMap = map;
        final String orderSeq = CondoService.getOrderSeq();
        orderMap.put("orderSeq", orderSeq);
        orderMap.put("orderNo", orderNumber);

        /* 4. PG로 보낼 데이터 생성 */
        String payMethod = ((String)map.get("payMethod"));
        String ediDate = OrderInvoiceUtil.getyyyyMMddHHmmss();
        String merchantKey = OrderValueGenerator.getMerchantKey(payMethod);
        String merchantID = OrderValueGenerator.getMerchantId(payMethod);

        DataEncrypt sha256Enc = new DataEncrypt();
        String hashString = sha256Enc.encrypt(ediDate + merchantID + map.get("price") + merchantKey);

        String convertPayMethod = payMethod;
        String payType = "CARD";
        log.info("### payMethod {}", payMethod);
        if("KAKAO".equals(payMethod)) {
            convertPayMethod = "CARD";
            payType = "KAKAO";
            condoPaymentMap.put("DirectShowOpt", "CARD");
            condoPaymentMap.put("DirectEasyPay", "");
            condoPaymentMap.put("NicepayReserved", "DirectKakao=Y");
            log.info("### kakaoPay ###");
        } else if("NAVER".equals(payMethod)) {
            convertPayMethod = "CARD";
            payType = "NAVER";
            condoPaymentMap.put("DirectShowOpt", "CARD");
            condoPaymentMap.put("DirectEasyPay", "E020");
            condoPaymentMap.put("NicepayReserved", "DirectKakao=N");
            condoPaymentMap.put("EasyPayMethod", "E020=CARD");
            log.info("### NaverPay ###");
        } else {
            /* 카카오페이 전역변수, 카카오페이가 아닐 경우 초기화 */
            condoPaymentMap.put("NicepayReserved", "DirectKakao=N");
            /* 네이버페이 전역변수, 네이버페이가 아닐 경우 초기화 */
            condoPaymentMap.put("DirectEasyPay", "");
            payType = "CARD";
            log.info("### Card Payment  ###");
        }
        condoPaymentMap.put("PayMethod", convertPayMethod);
        condoPaymentMap.put("GoodsName", map.get("mName"));
        condoPaymentMap.put("Amt", map.get("price"));
        condoPaymentMap.put("MID", OrderValueGenerator.getMerchantId(payMethod));

        condoPaymentMap.put("BuyerName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
        condoPaymentMap.put("BuyerEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
        String BuyerTel = OrderInvoiceUtil.getMemberAttribute(request,"handphone1");
        BuyerTel += "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone2");
        BuyerTel += "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone3");
        condoPaymentMap.put("BuyerTel", BuyerTel);
        condoPaymentMap.put("ReturnURL", OrderValueGenerator.CONDO_RETURN_URL);
        condoPaymentMap.put("status", "PAYMENT");


        condoPaymentMap.put("Moid", orderNumber);  // 주문번호
        condoPaymentMap.put("EdiDate", ediDate);
        condoPaymentMap.put("SignData", hashString);

        condoPaymentMap.put("WapUrl", OrderValueGenerator.WAP_URL); //Web-View 연동시 ios에서 필요
        condoPaymentMap.put("IspCancelUrl", OrderValueGenerator.ISP_CANCEL_URL); //Web-View 연동시 ios에서 필요
        condoPaymentMap.put("payType", payType);

        /* 주문정보 */
        condoPaymentMap.put("room_cd", map.get("room_cd"));
        condoPaymentMap.put("start_dt", map.get("start_dt"));
        condoPaymentMap.put("end_dt", map.get("end_dt"));
        condoPaymentMap.put("few_day", map.get("few_day"));
        condoPaymentMap.put("adult_num", map.get("adult_num"));
        condoPaymentMap.put("child_num", map.get("child_num"));
        condoPaymentMap.put("member_nm", map.get("member_nm"));
        condoPaymentMap.put("hand_tel1", map.get("hand_tel1"));
        condoPaymentMap.put("hand_tel2", map.get("hand_tel2"));
        condoPaymentMap.put("hand_tel3", map.get("hand_tel3"));
        condoPaymentMap.put("guest_nm", map.get("guest_nm"));
        condoPaymentMap.put("ghand_tel1", map.get("ghand_tel1"));
        condoPaymentMap.put("ghand_tel2", map.get("ghand_tel2"));
        condoPaymentMap.put("ghand_tel3", map.get("ghand_tel3"));
        condoPaymentMap.put("pet_num", map.get("pet_num"));
        condoPaymentMap.put("pet_add", map.get("pet_add"));
        condoPaymentMap.put("mber_add", map.get("mber_add"));

        model.addAttribute("result",condoPaymentMap);

        /* 세션 존재여부 체크 및 주문정보 저장 */
        CondoSessionVO condoSessionVO = OrderInvoiceUtil.getSessionForCondoPay(request);
        if(condoSessionVO == null) {
            condoSessionVO = new CondoSessionVO();
        }
        condoSessionVO.setUserId(OrderInvoiceUtil.getMemberAttribute(request,"id"));
        condoSessionVO.setOrderCondoMap(condoPaymentMap);
        OrderInvoiceUtil.saveSessionForCondoPay(request, condoSessionVO);

        model.addAttribute("code",result);
        model.addAttribute("msg",msg);

        log.info("[콘도] 나이스페이 요청값 및 주문정보: {}", condoPaymentMap);

        return "jsonView";
    }

    @PostMapping("/condoPay/condoPayCallback.do")
    public String condoPayCallback(HttpServletRequest request, @RequestParam Map<String, Object> map, Model model) throws Exception {
        String code = ResponseCode.SYS_0000.getCode();

        /* 0. 결제완료/결제실패 후 뒤로가기를 눌러서 들어온 경우 처리. */
        CondoSessionVO condoSessionVO = OrderInvoiceUtil.getSessionForCondoPay(request);
        log.info("### condoSessionVO : {}", condoSessionVO);

        /* 1. 로그인 체크  */
        if (!PaymentUtil.isLogin(request)) {
            code = ResponseCode.SYS_1000.getCode();
            model.addAttribute("code", code);
            model.addAttribute("msg", ResponseCode.getMessage(code));
            return "user:/condo/condoResPaymentResult";
        }

        //동시결제시 DB데이터 섞이는 현상으로 인한 결제실패 처리
        if(condoSessionVO == null) {
            log.info("### 주문정보 없음(동시 접근 / 세션 오류). 결제취소 진행 ###");
            //나이스페이 결제창은 결제완료 처리(뒤로가기 방지)
            CondoSessionVO csv = new CondoSessionVO();
            csv.setUserId(OrderInvoiceUtil.getMemberAttribute(request,"id"));
            csv.setNiceCertMap(map);
            log.info("### csv {}", csv);
            OrderInvoiceUtil.saveSessionForCondoPay(request, csv);

            code = ResponseCode.SYS_5000.getCode();
            model.addAttribute("code", code);
            model.addAttribute("msg", ResponseCode.getMessage(code));
            return "user:/condo/condoResPaymentResult";
        }

        //결제완료 정보는 있고 주문 정보 없으면 뒤로가기로 인식
        if(condoSessionVO.getNiceCertMap() != null && condoSessionVO.getOrderCondoMap() == null) {
            log.info("### 뒤로가기로 들어온 요청입니다. ###");
            return "user:/condo/moveCondoRes";
        }

        /* 2. 나이스 페이로 부터 받은 데이터 한글 수신을 위해서 인코딩(다음 순서 중요 */
        request.setCharacterEncoding("euc-kr");
        Map<String, Object> niceCertMap = OrderMapGenerator.makeRecvCertification(request);
        request.setCharacterEncoding("utf-8");

        log.info("### condoPayCallback.do map params : " + map);
        log.info("### condoPayCallback.do niceCertMap params : " + niceCertMap);

        /* 3. 결제 인증 결과 유효성 체크 */
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            code = OrderValidator.validateCertification(niceCertMap);
        }
        log.info("validateCertification Check : {}", code);

        log.info("Session Check : {}", code);

        /* 4. 결제 인증 결과 성공에 결제 요청, 결제인증 실패처리, 망취소등 처리 */
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            code = CondoService.saveShopPayment(request, model);
            log.info("CondoService.saveShopPayment model : " + model);
        }
        log.info("saveShopPayment Check : {}", code);

        // 중간점검으로 결제 오류 처리
        if(!code.equals(ResponseCode.SYS_0000.getCode())) {
            model.addAttribute("code", code);
            model.addAttribute("msg", ResponseCode.getMessage(code));

            return "user:/condo/condoResPaymentResult";
        }

        /* 5. 지불내역 등록을 위한 SERTB_PG_FINAL_RESULT 조회 */
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            PaidVO res = paidService.selectPaid(niceCertMap.get("moid").toString());
            log.info("### res : {}", res);
            model.addAttribute("cardName", res.getCardName());
            model.addAttribute("authCode", res.getAuthCode());
            model.addAttribute("cardNo", res.getCardNo());
            model.addAttribute("payMethod", niceCertMap.get("payMethod"));
            model.addAttribute("cardQuota", res.getCardQuota());
            model.addAttribute("netAmt", res.getNetAmt());
            model.addAttribute("vatAmt", res.getVatAmt());
        }

        /* 6. 주문정보 : 결제 완료페이지에 셋팅 */
        if(code.equals(ResponseCode.SYS_0000.getCode())) {
            if(niceCertMap.get("isrtId").equals(condoSessionVO.getUserId())) {
                condoSessionVO.setNiceCertMap(niceCertMap);
                model.addAttribute("userId", niceCertMap.get("isrtId"));
                model.addAttribute("resno", SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "resno"));
                model.addAttribute("room_cd", condoSessionVO.getOrderCondoMap().get("room_cd").toString());
                model.addAttribute("start_dt", condoSessionVO.getOrderCondoMap().get("start_dt").toString());
                model.addAttribute("end_dt", condoSessionVO.getOrderCondoMap().get("end_dt").toString());
                model.addAttribute("few_day", condoSessionVO.getOrderCondoMap().get("few_day").toString());
                model.addAttribute("adult_num", condoSessionVO.getOrderCondoMap().get("adult_num").toString());
                model.addAttribute("child_num", condoSessionVO.getOrderCondoMap().get("child_num").toString());
                model.addAttribute("member_nm", condoSessionVO.getOrderCondoMap().get("member_nm").toString());
                model.addAttribute("hand_tel1", condoSessionVO.getOrderCondoMap().get("hand_tel1").toString());
                model.addAttribute("hand_tel2", condoSessionVO.getOrderCondoMap().get("hand_tel2").toString());
                model.addAttribute("hand_tel3", condoSessionVO.getOrderCondoMap().get("hand_tel3").toString());
                model.addAttribute("guest_nm", condoSessionVO.getOrderCondoMap().get("guest_nm").toString());
                model.addAttribute("ghand_tel1", condoSessionVO.getOrderCondoMap().get("ghand_tel1").toString());
                model.addAttribute("ghand_tel2", condoSessionVO.getOrderCondoMap().get("ghand_tel2").toString());
                model.addAttribute("ghand_tel3", condoSessionVO.getOrderCondoMap().get("ghand_tel3").toString());
                model.addAttribute("pet_num", condoSessionVO.getOrderCondoMap().get("pet_num").toString());
                model.addAttribute("pet_add", condoSessionVO.getOrderCondoMap().get("pet_add").toString());
                model.addAttribute("mber_add", condoSessionVO.getOrderCondoMap().get("mber_add").toString());
                model.addAttribute("SignData", condoSessionVO.getOrderCondoMap().get("SignData").toString());
                model.addAttribute("amt", condoSessionVO.getOrderCondoMap().get("Amt").toString());
                model.addAttribute("moid", condoSessionVO.getOrderCondoMap().get("Moid").toString());
                model.addAttribute("payType", condoSessionVO.getOrderCondoMap().get("payType").toString());
            }
        }

        model.addAttribute("code", code);
        model.addAttribute("msg", ResponseCode.getMessage(code));

        return "user:/condo/condoResPaymentResult";
    }

    @RequestMapping(path = "/condoPay/insertPayResult.do", method = RequestMethod.POST)
    public String insertPayResult(HttpServletRequest request, Model model, @RequestParam Map<String, Object> map) throws Exception {
        log.info("### map : {}", map);

        /* 카드정보는 SERTB_PG_FINAL_RESULT 테이블에서 moid(order_no)로 조회해서 insert 함 */
        map.put("payInfo", CondoService.getCardInfo(map));

        int paySeq = CondoService.insertCondoPay(map);

        return "jsonView";
    }

    @RequestMapping(path = "/condoPay/punishUpdate.do", method = RequestMethod.POST)
    public String updatePunish(HttpServletRequest request, @RequestParam Map<String, Object> map) throws Exception {
        log.info("### map : {}", map);
        int seq = CondoService.updateCondoPayPunish(map);
        log.info("### Seq : {}", seq);
        return "jsonView";
    }

    @RequestMapping(path = "/condoPay/insertPayDetail.do", method = RequestMethod.POST)
    public String insertPayDetail(HttpServletRequest request, Model model, @RequestParam Map<String, Object> map) throws Exception {
        log.info("### param : {}", map);

        // 1. CondoSeq 조회
        String condoSeq = CondoService.selectCondoSeq(map.get("userId").toString(), map.get("reserveNo").toString());

        log.info("### condoSeq : {}", condoSeq);
        // 2. SERTB_CONDO_PAYMENT_DTL insert
        map.remove("reserveNo");
        Map<String, Object> param = map;
        param.put("condoSeq", condoSeq);
        int seq = CondoService.insertCondoPayDetail(param);
        log.info("### Seq : {}", seq);

        model.addAttribute("condoSeq", condoSeq);
        return "jsonView";
    }

    @RequestMapping("/condoPay/condoCancel.do")
    public String orderCancel(HttpServletRequest request, Model model, @RequestParam Map<String, Object> map) throws Exception {
        log.info("/condoPay/condoCancel.do map : " + map);
        String code = "0000";

        Map<String, Object> condoMap = null;
        /* 1. 요청 파라미터 체크 */
        String orderSequence = (String) map.get("orderSeq");
        if (orderSequence == null) {
            code = ResponseCode.SYS_6000.getCode();
        }else {
            /* 결제취소 관련 상세값 생성 */
            condoMap = CondoService.selectCancelDetail(map);
            log.info("condopay/condoCancel comdoMap.params : " + condoMap);
            map.put("tid", condoMap.get("TID"));
            map.put("mid", condoMap.get("MID"));
            map.put("orderNo", condoMap.get("MOID"));
            map.put("Amt", condoMap.get("TOTAL_AMOUNT"));
            if(map.get("cancelAmt").toString().equals(condoMap.get("TOTAL_AMOUNT").toString()))
                map.put("cancelCode",  "1");
            else
                map.put("cancelCode",  "0");
        }

        /* 2. 로그인 체크  */
        if (!OrderInvoiceUtil.isLogin(request)) {
            code = ResponseCode.SYS_1000.getCode();
        }

//        if(!"".equals(String.valueOf(map.get("mid"))) || !"null".equals(map.get("mid"))) {
        if(map.get("mid") != null) {
            /* 3. 나이스페이 호출 */
            /* 3.1 나이스페이 전문 생성 */
            DataEncrypt sha256Enc = new DataEncrypt();
            String ediDate = OrderInvoiceUtil.getyyyyMMddHHmmss();
            String signData = sha256Enc.encrypt(map.get("mid").toString() + map.get("cancelAmt").toString() + ediDate + OrderValueGenerator.searchMerchantKey(map.get("mid").toString()));

            /* 3.2 나이스페이 취소 전문 요청 */
            String resultJsonStr = OrderInvoiceUtil.connectToServer(OrderMapGenerator.makeCancelRequest(map, ediDate, signData), "https://webapi.nicepay.co.kr/webapi/cancel_process.jsp");

            /* 3.3 취소전문 저장. 단. 예약실패로 인한 취소는 저장 하지 않음.(테이블에 정보가 없어서 불가능함.) */
            if (map.get("payYn").toString().equals("fail")) {
                log.info("### 결제상태 : {} ", map.get("payYn").toString());
                code = ResponseCode.SYS_0000.getCode();
                //테이블에 저장된 결제 요청 삭제
                model.addAttribute("resultCode", code);
                model.addAttribute("resultMsg", ResponseCode.getMessage(code));

                return "jsonView";
            } else {
                log.info("### 결제상태 : {} ", map.get("payYn").toString());
                map.put("orderDtlSeq", condoMap.get("CONDO_SEQ"));
                Map<String, Object> cancelApiMap = OrderMapGenerator.makePGCancelRequest(request, map, signData, ediDate);
                orderInvoiceService.insertPGCancelRequest(cancelApiMap);

                if ("9999".equals(resultJsonStr)) {
                    log.info("Network Error : {}", resultJsonStr);
                    code = ResponseCode.SYS_6005.getCode();
                } else {
                    HashMap resultData = OrderInvoiceUtil.jsonStringToHashMap(resultJsonStr);
                    System.out.println("OrderInvoiceUtil.jsonStringToHashMap(resultJsonStr) : " + OrderInvoiceUtil.jsonStringToHashMap(resultJsonStr));
                    if (OrderValueGenerator.isCancelySuccess(resultData)) {
                        /* 취소전문 결과 저장 */
                        Map<String, Object> resultMap = OrderMapGenerator.makeShopCancel(request, resultData, map);
                        String cancelSequence = orderInvoiceService.getCancelSequence();
                        resultMap.put("pcsSeq", cancelSequence);
                        orderInvoiceService.insertCancelResult(resultMap);

                        /* condo 결제취소 상태값 변경. */

                        resultMap.put("updtId", OrderInvoiceUtil.getMemberAttribute(request, "id"));
                        resultMap.put("updtIp", OrderInvoiceUtil.getClientIP(request));
                        resultMap.put("payYn", map.get("payYn"));
                        System.out.println("condopay/condoCancel resultMap : " + resultMap);
                        CondoService.updateCondoPay(resultMap);
                        code = ResponseCode.SYS_0000.getCode();
                    } else {
                        log.info("Cancel Fail : {}", resultJsonStr);
                        code = ResponseCode.SYS_6006.getCode();
                    }
                }
            }
        } else {
            log.info("Cancel Fail(mid is null) : {}", ResponseCode.SYS_6000.getCode());
            code = ResponseCode.SYS_6000.getCode();
        }
        model.addAttribute("resultCode", code);
        model.addAttribute("resultMsg", ResponseCode.getMessage(code));

        return "jsonView";
    }

    /**
     * 예약확인에서 결제내역 조회
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/condoPayHistory.do")
    public String selectCondoPayHistory(HttpServletRequest request, Model model) throws Exception {
        String userId = OrderInvoiceUtil.getMemberAttribute(request, "id");
        log.info("### request : {}", request);
        String reserveNo = request.getParameter("reserveNo");

        Map<String, Object> searchDetailMap = new HashMap<>();

        searchDetailMap.put("userId", userId);
        searchDetailMap.put("reserveNo", reserveNo);

        String buyDate = CondoService.condoPaymentBuyDate(userId, reserveNo);
        searchDetailMap.put("payDate", buyDate);
        log.info("결제일 조회 : {}", buyDate);
        log.info("### searchDetailMap ### {}", searchDetailMap);

        // 주문내역 조회
        CondoPayHistoryVO condoPayDetail = CondoService.selectCondoPayDetail(searchDetailMap);
        log.info("결제 상세내역 : {}", condoPayDetail);

        String payHistory = "N";
        if(condoPayDetail != null) {
            payHistory = "Y";
        }

        model.addAttribute("payHistory", payHistory);
        return "jsonView";
    }
}
