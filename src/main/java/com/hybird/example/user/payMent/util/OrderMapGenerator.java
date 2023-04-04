package com.hybird.example.user.payMent.util;

import com.hybird.example.user.payMent.vo.CancelTicketVO;
import com.hybird.example.user.payMent.vo.OrderTicketVO;
import com.hybird.example.user.payMent.vo.TicketInformation;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapGenerator {
    public static final String[] ORDER_REQUEST = {"mName", "price", "payMethod"};

    public static boolean checkOrderPopParameters(Map<String, Object> map) {
        if (map == null) {
            return false;
        }
        boolean checkFlag = true;
        for (String key : ORDER_REQUEST) {
            if (!map.containsKey(key)) {
                checkFlag = false;
                break;
            }
        }

        return checkFlag;
    }

    public static Map<String, Object> makeShopOrder(HttpServletRequest request, List<OrderTicketVO> orderList) {
        Map<String, Object> map = new HashMap<>();
        map.put("memId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("memType", OrderInvoiceUtil.getMemberType(request));
        map.put("memName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
        map.put("memHandphone1", OrderInvoiceUtil.getMemberAttribute(request,"handphone1"));
        map.put("memHandphone2", OrderInvoiceUtil.getMemberAttribute(request,"handphone2"));
        map.put("memHandphone3", OrderInvoiceUtil.getMemberAttribute(request,"handphone3"));
        map.put("memEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
        map.put("sumNpAmount", OrderValueGenerator.getTotalNormalPrice(orderList));
        map.put("sumMemAmount", OrderValueGenerator.getTotalMemberPrice(orderList));
        map.put("sumOrderQty", OrderValueGenerator.getTotalTicketCount(orderList));
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeShopOrderDetail(HttpServletRequest request, OrderTicketVO ticket) {
        Map<String, Object> map = new HashMap<>();
        map.put("ticketSeq", ticket.getTicketSeq());
        map.put("muMpFlag", ticket.getMuMpFlag());
        map.put("ticketNm", ticket.getTicketNm());
        map.put("ticketShortNm", ticket.getTicketShortNm());
        map.put("npAmount", ticket.getSumNormalPrice());
        map.put("memAmount", ticket.getSumMemberPrice());
        map.put("orderQty", ticket.getTicketNo());
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeSavePGRequest(HttpServletRequest request, List<OrderTicketVO> orderList, String payMethod) {
        Map<String, Object> map = new HashMap<>();
        map.put("mid", OrderValueGenerator.getMerchantId(payMethod));
        map.put("payMethod", payMethod);
        map.put("ticketName", OrderValueGenerator.getPaymentGoodsName(orderList));
        map.put("sumMemAmount", OrderValueGenerator.getTotalMemberPrice(orderList));
        map.put("memName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
        map.put("memHandphone1", OrderInvoiceUtil.getMemberAttribute(request,"handphone1"));
        map.put("memHandphone2", OrderInvoiceUtil.getMemberAttribute(request,"handphone2"));
        map.put("memHandphone3", OrderInvoiceUtil.getMemberAttribute(request,"handphone3"));
        map.put("memEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
        map.put("returnUrl", OrderValueGenerator.CARD_RETURN_URL);
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makePGRequest(HttpServletRequest request, List<OrderTicketVO> orderList, String payMethod) {
        Map<String, Object> map = new HashMap<>();

        String convertPayMethod = payMethod;
        if("KAKAO".equals(payMethod)) {
            convertPayMethod = "CARD";
            map.put("DirectShowOpt", "CARD");
            map.put("NicepayReserved", "DirectKakao=Y");
        } else if("NAVER".equals(payMethod)) {
            convertPayMethod = "CARD";
            map.put("DirectShowOpt", "CARD");
            map.put("DirectEasyPay", "E020");
            map.put("EasyPayMethod", "E020=CARD");
        }
        map.put("PayMethod", convertPayMethod);
        map.put("GoodsName", OrderValueGenerator.getPaymentGoodsName(orderList));
        map.put("Amt", OrderValueGenerator.getTotalMemberPrice(orderList));
        map.put("MID", OrderValueGenerator.getMerchantId(payMethod));

        map.put("BuyerName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
        map.put("BuyerEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
        String BuyerTel = OrderInvoiceUtil.getMemberAttribute(request,"handphone1");
        BuyerTel += "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone2");
        BuyerTel += "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone3");
        map.put("BuyerTel", BuyerTel);
        map.put("ReturnURL", OrderValueGenerator.CARD_RETURN_URL);
        map.put("status", "PAYMENT");

        return map;
    }

    public static Map<String, Object> makeRecvCertification(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("authResultCode", request.getParameter("AuthResultCode"));
        String authResultMsg = null;
        String msg = request.getParameter("AuthResultMsg");
        if (msg != null) {
            String str = new String(msg);
            authResultMsg = new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        }

        map.put("authResultMsg", authResultMsg);
        map.put("authToken", request.getParameter("AuthToken"));
        map.put("payMethod", request.getParameter("PayMethod"));
        map.put("mid", request.getParameter("MID"));
        map.put("moid", request.getParameter("Moid"));
        map.put("amt", request.getParameter("Amt"));
        map.put("signature", request.getParameter("Signature"));
        map.put("reqReserved", request.getParameter("ReqReserved"));
        map.put("txTid", request.getParameter("TxTid"));
        map.put("nextAppURL", request.getParameter("NextAppURL"));
        map.put("netCancelURL", request.getParameter("NetCancelURL"));
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static String makePaymentRequest(Map<String, Object> map, final String ediDate, final String signData) {
        return makePaymentRequest(map, ediDate, signData, false);
    }

    public static String makePaymentRequest(Map<String, Object> map, final String ediDate, final String signData, final boolean cancelYn) {
        StringBuffer requestData = new StringBuffer();
        requestData.append("TID=").append(map.get("txTid")).append("&");
        requestData.append("AuthToken=").append(map.get("authToken")).append("&");
        requestData.append("MID=").append(map.get("mid")).append("&");
        requestData.append("Amt=").append(map.get("amt")).append("&");
        requestData.append("EdiDate=").append(ediDate).append("&");
        requestData.append("CharSet=").append("utf-8").append("&");
        requestData.append("SignData=").append(signData);
        if (cancelYn) {
            requestData.append("&").append("NetCancel=").append("1");
        }

        return requestData.toString();
    }

    public static Map<String, Object> makeApprovalRequest(Map<String, Object> niceCertMap, final String ediDate, final String signData) {
        Map<String, Object> map = new HashMap<>();
        map.put("txTid", niceCertMap.get("txTid"));
        map.put("authToken", niceCertMap.get("authToken"));
        map.put("mid", niceCertMap.get("mid"));
        map.put("amt", niceCertMap.get("amt"));
        map.put("moid", niceCertMap.get("moid"));
        map.put("ediDate", ediDate);
        map.put("signData", signData);

        return map;
    }

    public static Map<String, Object> makeNetCancel(HashMap cancelResultData) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", cancelResultData.get("ResultCode"));
        map.put("resultMsg", cancelResultData.get("ResultMsg"));
        map.put("errorCd", cancelResultData.get("ErrorCD"));
        map.put("errorMsg", cancelResultData.get("ErrorMsg"));
        map.put("cancelAmt", cancelResultData.get("CancelAmt"));
        map.put("mid", cancelResultData.get("MID"));
        map.put("moid", cancelResultData.get("Moid"));
        map.put("payMethod", cancelResultData.get("PayMethod"));
        map.put("tid", cancelResultData.get("TID"));
        map.put("cancelDate", cancelResultData.get("CancelDate"));
        map.put("cancelTime", cancelResultData.get("CancelTime"));
        map.put("cancelNum", cancelResultData.get("CancelNum"));
        map.put("remainAmt", cancelResultData.get("RemainAmt"));

        return map;
    }

    /* 콘도 결제 */
    public static Map<String, Object> makeFinalResult(HttpServletRequest request, HashMap resultData) {
        if (resultData.get("Moid") == null || resultData.get("Amt") == null) {
            return null;
        }
        if (String.valueOf(resultData.get("Moid")).length() != 16) {
            return null;
        }
        if (String.valueOf(resultData.get("Amt")).length() < 4) {
            return null;
        }
        final String[] SRC_MAP_ARRAY = {"Moid", "MID", "PayMethod", "TID", "ResultCode", "ResultMsg", "GoodsName", "Amt", "AuthCode", "AuthDate", "CardCode", "CardName", "CardNo", "CardQuota"};
        final String[] DEST_MAP_ARRAY = {"orderNo", "mid", "payMethod", "txtid", "resultCode", "resultMsg", "ticketName", "sumMemAmount", "authCode", "authDate", "cardCode", "cardName", "cardNo", "cardQuota"};

        Map<String, Object> map = new HashMap<>();
        for (int i = 0, size = SRC_MAP_ARRAY.length; i < size; i++) {
            map.put(DEST_MAP_ARRAY[i], resultData.get(SRC_MAP_ARRAY[i]));
        }
        map.put("memName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
        map.put("memEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
        map.put("memHandphone1", OrderInvoiceUtil.getMemberAttribute(request,"handphone1"));
        map.put("memHandphone2", OrderInvoiceUtil.getMemberAttribute(request,"handphone2"));
        map.put("memHandphone3", OrderInvoiceUtil.getMemberAttribute(request,"handphone3"));
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeShopPayment(HttpServletRequest request, HashMap resultData, List<OrderTicketVO> orderList) {
        final String[] SRC_MAP_ARRAY = {"Moid", "PayMethod", "TID", "AuthCode", "AuthDate", "CardCode", "CardName", "CardNo", "CardQuota", "MID"};
        final String[] DEST_MAP_ARRAY = {"orderNo", "payMethod", "txtid", "authCode", "authDate", "cardCode", "cardName", "cardNo", "cardQuota", "mid"};

        Map<String, Object> map = new HashMap<>();
        for (int i = 0, size = SRC_MAP_ARRAY.length; i < size; i++) {
            map.put(DEST_MAP_ARRAY[i], resultData.get(SRC_MAP_ARRAY[i]));
        }

        map.put("memId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("memType", OrderInvoiceUtil.getMemberType(request));
        map.put("memName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
        map.put("memEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
        map.put("memHandphone1", OrderInvoiceUtil.getMemberAttribute(request,"handphone1"));
        map.put("memHandphone2", OrderInvoiceUtil.getMemberAttribute(request,"handphone2"));
        map.put("memHandphone3", OrderInvoiceUtil.getMemberAttribute(request,"handphone3"));
        map.put("sumNpAmount", OrderValueGenerator.getTotalNormalPrice(orderList));
        map.put("sumMemAmount", OrderValueGenerator.getTotalMemberPrice(orderList));
        map.put("sumDiscountAmount", OrderValueGenerator.getTotalDiscountPrice(orderList));
        map.put("sumOrderQty", OrderValueGenerator.getTotalTicketCount(orderList));
        map.put("limitStartDate", OrderInvoiceUtil.calculateDateFormat(0L, "yyyy-MM-dd"));
        map.put("limitEndDate", OrderInvoiceUtil.calculateDateFormat(31L, "yyyy-MM-dd"));
        map.put("ticketStatus", "0");
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeShopPaymentDetail(HttpServletRequest request, OrderTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();

        map.put("ticketSeq", ticketVO.getTicketSeq());
        map.put("muMpFlag", ticketVO.getMuMpFlag());
        map.put("ticketNm", ticketVO.getTicketNm());
        map.put("ticketShortNm", ticketVO.getTicketShortNm());
        map.put("npAmount", ticketVO.getNormalPrice());
//        map.put("npAmount", ticketVO.getSumNormalPrice());
        map.put("memAmount", ticketVO.getMemberPrice());
//        map.put("memAmount", ticketVO.getSumMemberPrice());
        map.put("discountAmount", ticketVO.getDiscountPrice());
        map.put("buyQty", 1);
//        map.put("buyQty", ticketVO.getTicketNo());
        map.put("cancelYn", "N");
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeShopPaymentQRCode(HttpServletRequest request, TicketInformation good) {
        Map<String, Object> map = new HashMap<>();
        map.put("muTicketSeq", good.getMuTicketSeq());
        map.put("mMenu", good.getIaGoodsCd());
        map.put("ticketLimitCnt", good.getTkOriginCnt());
        map.put("muTicketNm", good.getIaGoodsNm());
        map.put("muTicketShortNm", good.getMuTicketShortNm());
        map.put("weekFlag", good.getIaWeekTy());
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeItrevActivity(HttpServletRequest request, TicketInformation good) {
        Map<String, Object> map = new HashMap<>();
        map.put("iaOnoffKb", good.getIaOnoffKb());
        map.put("iaAgentCd", good.getIaAgentCd());
        map.put("iaSalesDt", good.getIaSalesDt());
        map.put("iaOutletCd", good.getIaOutletCd());
        map.put("iaGoodsCd", good.getIaGoodsCd());
        map.put("iaGoodsNm", good.getIaGoodsNm());
        map.put("iaGoodsTy", good.getIaGoodsTy());
        map.put("iaWeekTy", good.getIaWeekTy());
        map.put("iaUnitPrice", good.getIaUnitPrice());
        map.put("iaSalesAmt", good.getIaSalesAmt());
        map.put("iaMemberNo", good.getIaMemberNo());
        map.put("iaUseYn", good.getIaUseYn());
        map.put("iaUseDt", good.getIaUseDt());
        map.put("iaCloseYn", good.getIaCloseYn());
        map.put("iaIssuedDt", good.getIaIssuedDt());
        map.put("iaExpireDt", good.getIaExpireDt());
        map.put("iaRefundYn", good.getIaRefundYn());
        map.put("iaRefundDt", good.getIaRefundDt());
        map.put("iaRefundAmt", good.getIaRefundAmt());
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeItqrTicketDtl(TicketInformation good) {
        Map<String, Object> map = new HashMap<>();
        map.put("tkUseOutlet", good.getIaOutletCd());
        map.put("tkGoodsCd", good.getIaGoodsCd());
        map.put("tkPkgsubCd", good.getTkPkgsubCd());
        map.put("tkPkgsubNm", good.getTkPkgsubNm());
        map.put("tkOriginCnt", good.getTkOriginCnt());
        map.put("tkRemainCnt", good.getTkRemainCnt());
        map.put("tkState", good.getTkState());
        map.put("tkItemCd", good.getIaGoodsCd());

        return map;
    }

    public static Map<String, Object> makeClientCancelRequest(HttpServletRequest request, CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderSeq", ticketVO.getOrderSeq());
        map.put("orderDtlSeq", ticketVO.getOrderDtlSeq());
        map.put("cancelAmt", ticketVO.getMemAmount());
        if (ticketVO.getGoodsNo() == (ticketVO.getCancelCount() + 1)) {
            map.put("partialCancelCode", "0");
        } else {
            map.put("partialCancelCode", "1");
        }
        map.put("moid", ticketVO.getOrderNo());
        map.put("cancelMsg", "고객요청");
        map.put("tid", ticketVO.getTxtid());
        map.put("userId", ticketVO.getMemId());
        map.put("ccrDate", OrderInvoiceUtil.getyyyyMMddHHmmss());
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }
    
    public static Map<String, Object> makeClientCancelRequest(HttpServletRequest request, Map<String, Object> map) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderSeq", map.get("orderSeq"));
        params.put("cancelAmt", map.get("Amt"));
       	params.put("cancelCode", map.get("cancelCode"));
        params.put("moid", map.get("orderNo"));
        params.put("cancelMsg", "고객요청");
        params.put("tid", map.get("tid"));
        params.put("userId", map.get("userId"));
        params.put("ccrDate", OrderInvoiceUtil.getyyyyMMddHHmmss());
        params.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        params.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return params;
    }

    public static Map<String, Object> makeRefundCancelRequest(CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderSeq", ticketVO.getOrderSeq());
        map.put("orderDtlSeq", ticketVO.getOrderDtlSeq());
        map.put("cancelAmt", ticketVO.getMemAmount());
        if (ticketVO.getGoodsNo() == (ticketVO.getCancelCount() + 1)) {
            map.put("partialCancelCode", "0");
        } else {
            map.put("partialCancelCode", "1");
        }
        map.put("moid", ticketVO.getOrderNo());
        map.put("cancelMsg", "고객요청");
        map.put("tid", ticketVO.getTxtid());
        map.put("userId", ticketVO.getMemId());
        map.put("ccrDate", OrderInvoiceUtil.getyyyyMMddHHmmss());
        map.put("isrtId", "batch");
        map.put("isrtIp", "127.0.0.1");

        return map;
    }

    public static String makeCancelRequest(CancelTicketVO ticketVO, final String ediDate, final String signData) throws UnsupportedEncodingException {
        StringBuffer requestData = new StringBuffer();
        requestData.append("TID=").append(ticketVO.getTxtid()).append("&");
        requestData.append("MID=").append(ticketVO.getMid()).append("&");
        requestData.append("Moid=").append(ticketVO.getOrderNo()).append("&");
        requestData.append("CancelAmt=").append(ticketVO.getMemAmount()).append("&");
        requestData.append("CancelMsg=").append(URLEncoder.encode("고객요청", "euc-kr")).append("&");
        if (ticketVO.getGoodsNo() == 1) {
            requestData.append("PartialCancelCode=").append("0").append("&");
        } else {
            requestData.append("PartialCancelCode=").append("1").append("&");
        }
        requestData.append("EdiDate=").append(ediDate).append("&");
        requestData.append("CharSet=").append("utf-8").append("&");
        requestData.append("SignData=").append(signData);

        return requestData.toString();
    }
    
    /* 콘도 결제 취소 */
    public static String makeCancelRequest(Map<String, Object> map, final String ediDate, final String signData) throws UnsupportedEncodingException {
        StringBuffer requestData = new StringBuffer();
        requestData.append("TID=").append(map.get("tid")).append("&");
        requestData.append("MID=").append(map.get("mid")).append("&");
        requestData.append("Moid=").append(map.get("orderNo")).append("&");
        requestData.append("CancelAmt=").append(map.get("cancelAmt")).append("&");
        requestData.append("CancelMsg=").append(URLEncoder.encode("고객요청", "euc-kr")).append("&");
        if (Integer.valueOf((String) map.get("cancelCode")) == 1) {	//부분취소 여부
            requestData.append("PartialCancelCode=").append("0").append("&");
        } else {
            requestData.append("PartialCancelCode=").append("1").append("&");
        }
        requestData.append("EdiDate=").append(ediDate).append("&");
        requestData.append("CharSet=").append("utf-8").append("&");
        requestData.append("SignData=").append(signData);

        return requestData.toString();
    }
    

    public static Map<String, Object> makePGCancelRequest(HttpServletRequest request, CancelTicketVO ticketVO, String signData, String ediDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("tid", ticketVO.getTxtid());
        map.put("mid", ticketVO.getMid());
        map.put("orderSeq", ticketVO.getOrderSeq());
        map.put("orderDtlSeq", ticketVO.getOrderDtlSeq());
        map.put("moid", ticketVO.getOrderNo());
        map.put("cancelAmt", ticketVO.getMemAmount());
        map.put("cancelMsg", "고객요청");
        if (ticketVO.getGoodsNo() == 1) {
            map.put("partialCancelCode", "0");
        } else {
            map.put("partialCancelCode", "1");
        }
        map.put("signData", signData);
        map.put("ediDate", ediDate);
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }
    
    /* 콘도 결제 취소 */
    public static Map<String, Object> makePGCancelRequest(HttpServletRequest request, Map<String, Object> params, String signData, String ediDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("tid", params.get("tid"));
        map.put("mid", params.get("mid"));
        map.put("orderSeq", params.get("orderSeq"));		//nicepay 주문번호
        map.put("orderDtlSeq", params.get("orderDtlSeq"));	//결제 테이블 seq
        map.put("moid", params.get("orderNo"));				//nicepay 주문번호
        map.put("cancelAmt", params.get("Amt"));
        map.put("cancelMsg", "고객요청");
        map.put("partialCancelCode", params.get("cancelCode"));
        map.put("signData", signData);
        map.put("ediDate", ediDate);
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makePGRefundCancelRequest(CancelTicketVO ticketVO, String signData, String ediDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("tid", ticketVO.getTxtid());
        map.put("mid", ticketVO.getMid());
        map.put("orderSeq", ticketVO.getOrderSeq());
        map.put("orderDtlSeq", ticketVO.getOrderDtlSeq());
        map.put("moid", ticketVO.getOrderNo());
        map.put("cancelAmt", ticketVO.getMemAmount());
        map.put("cancelMsg", "고객요청");
        if (ticketVO.getGoodsNo() == 1) {
            map.put("partialCancelCode", "0");
        } else {
            map.put("partialCancelCode", "1");
        }
        map.put("signData", signData);
        map.put("ediDate", ediDate);
        map.put("isrtId", "batch");
        map.put("isrtIp", "127.0.0.1");

        return map;
    }

    public static Map<String, Object> makeShopCancel(HttpServletRequest request, HashMap resultData, CancelTicketVO ticketVO) {
        final String[] SRC_MAP_ARRAY = {"ResultCode", "ResultMsg", "CancelAmt", "TID", "Moid", "RemainAmt", "PayMethod"};
        final String[] DEST_MAP_ARRAY = {"resultCode", "resultMsg", "cancelAmt", "tid", "moid", "remainAmt", "payMethod"};

        Map<String, Object> map = new HashMap<>();
        for (int i = 0, size = SRC_MAP_ARRAY.length; i < size; i++) {
            map.put(DEST_MAP_ARRAY[i], resultData.get(SRC_MAP_ARRAY[i]));
        }
        map.put("orderSeq", ticketVO.getOrderSeq());
        map.put("orderDtlSeq", ticketVO.getOrderDtlSeq());
        map.put("cancelDate", OrderValueGenerator.getValueForResultMap(resultData, "CancelDate") + OrderValueGenerator.getValueForResultMap(resultData, "CancelTime"));
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }
    
    /* 콘도 결제취소 */
    public static Map<String, Object> makeShopCancel(HttpServletRequest request, HashMap resultData, Map<String,Object> params) {
        final String[] SRC_MAP_ARRAY = {"ResultCode", "ResultMsg", "CancelAmt", "TID", "Moid", "RemainAmt", "PayMethod"};
        final String[] DEST_MAP_ARRAY = {"resultCode", "resultMsg", "cancelAmt", "tid", "moid", "remainAmt", "payMethod"};

        Map<String, Object> map = new HashMap<>();
        for (int i = 0, size = SRC_MAP_ARRAY.length; i < size; i++) {
            map.put(DEST_MAP_ARRAY[i], resultData.get(SRC_MAP_ARRAY[i]));
        }
        map.put("orderSeq", params.get("orderSeq"));
        map.put("orderDtlSeq", params.get("orderDtlSeq"));
        map.put("cancelDate", OrderValueGenerator.getValueForResultMap(resultData, "CancelDate") + OrderValueGenerator.getValueForResultMap(resultData, "CancelTime"));
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }
    

    public static Map<String, Object> makeShopRefundCancel(HashMap resultData, CancelTicketVO ticketVO) {
        final String[] SRC_MAP_ARRAY = {"ResultCode", "ResultMsg", "CancelAmt", "TID", "Moid", "RemainAmt", "PayMethod"};
        final String[] DEST_MAP_ARRAY = {"resultCode", "resultMsg", "cancelAmt", "tid", "moid", "remainAmt", "payMethod"};

        Map<String, Object> map = new HashMap<>();
        for (int i = 0, size = SRC_MAP_ARRAY.length; i < size; i++) {
            map.put(DEST_MAP_ARRAY[i], resultData.get(SRC_MAP_ARRAY[i]));
        }
        map.put("orderSeq", ticketVO.getOrderSeq());
        map.put("orderDtlSeq", ticketVO.getOrderDtlSeq());
        map.put("cancelDate", OrderValueGenerator.getValueForResultMap(resultData, "CancelDate") + OrderValueGenerator.getValueForResultMap(resultData, "CancelTime"));
        map.put("isrtId", "batch");
        map.put("isrtIp", "127.0.0.1");

        return map;
    }

    public static Map<String, Object> makeUpdatePaymentForCancel(HttpServletRequest request, CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        if ((ticketVO.getCancelCount() + 1) >= ticketVO.getGoodsNo()) {
            map.put("ticketStatus", "2");
        } else {
            map.put("ticketStatus", "1");
        }
        map.put("updtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("updtIp", OrderInvoiceUtil.getClientIP(request));
        map.put("orderSeq", ticketVO.getOrderSeq());

        return map;
    }

    public static Map<String, Object> makeUpdatePaymentForRefundCancel(CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        if ((ticketVO.getCancelCount() + 1) >= ticketVO.getGoodsNo()) {
            map.put("ticketStatus", "2");
        } else {
            map.put("ticketStatus", "1");
        }
        map.put("updtId", "batch");
        map.put("updtIp", "127.0.0.1");
        map.put("orderSeq", ticketVO.getOrderSeq());

        return map;
    }

    public static Map<String, Object> makeUpdatePaymentDetailForCancel(HttpServletRequest request, CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("updtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("updtIp", OrderInvoiceUtil.getClientIP(request));
        map.put("orderSeq", ticketVO.getOrderSeq());
        map.put("orderDtlSeq", ticketVO.getOrderDtlSeq());

        return map;
    }

    public static Map<String, Object> makeUpdatePaymentDetailForRefundCancel(CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("updtId", "batch");
        map.put("updtIp", "127.0.0.1");
        map.put("orderSeq", ticketVO.getOrderSeq());
        map.put("orderDtlSeq", ticketVO.getOrderDtlSeq());

        return map;
    }

    public static Map<String, Object> makeUpdateItrevActivityForCancel(HttpServletRequest request, CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("updtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("updtIp", OrderInvoiceUtil.getClientIP(request));
        map.put("iaRefundAmt", ticketVO.getMemAmount());

        return map;
    }

    public static Map<String, Object> makeUpdateItrevActivityForRefundCancel(CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("updtId", "batch");
        map.put("updtIp", "127.0.0.1");
        map.put("iaRefundAmt", ticketVO.getMemAmount());

        return map;
    }

    public static Map<String, Object> makeBillSignUp(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("MID", OrderValueGenerator.NICE_MERCHANT_ID_BILL);
        //map.put("Moid", moid);
//        map.put("GoodsName", map.get("mName"));
//        map.put("Amt", map.get("price"));
        map.put("BuyerName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
        String BuyerTel = OrderInvoiceUtil.getMemberAttribute(request,"handphone1");
        BuyerTel += "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone2");
        BuyerTel += "-" + OrderInvoiceUtil.getMemberAttribute(request,"handphone3");//세션값을 통해 유저정보 조회
        map.put("BuyerTel", BuyerTel);
        map.put("BuyerEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
        map.put("ReturnUrl", OrderValueGenerator.BILL_RETURN_URL);
        map.put("PayMethod", "BILL");
        map.put("SecureKeypadYN", "Y"); 						// 보안키패드 사용유무 (필히 Y 값일 것)
        //map.put("rqstDate", rqstDate);
//        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
//        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeBillKeyRequest(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("mid", OrderValueGenerator.NICE_MERCHANT_ID_BILL);
        map.put("payMethod", "BILL");
        map.put("memName", OrderInvoiceUtil.getMemberAttribute(request,"kname"));
        map.put("memHandphone1", OrderInvoiceUtil.getMemberAttribute(request,"handphone1"));
        map.put("memHandphone2", OrderInvoiceUtil.getMemberAttribute(request,"handphone2"));
        map.put("memHandphone3", OrderInvoiceUtil.getMemberAttribute(request,"handphone3"));
        map.put("memEmail", OrderInvoiceUtil.getMemberAttribute(request,"email"));
        map.put("returnUrl", OrderValueGenerator.BILL_RETURN_URL);
        map.put("secureKeypadYn", "Y"); 						// 보안키패드 사용유무 (필히 Y 값일 것)
        map.put("rqstDate", OrderInvoiceUtil.getyyyyMMddHHmmss()); 						// 보안키패드 사용유무 (필히 Y 값일 것)
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeBillKeyResponse(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", request.getParameter("resultCode"));
        map.put("resultMsg", request.getParameter("resultMsg"));
        map.put("billkey", request.getParameter("billkey"));
        map.put("tid", request.getParameter("TID"));
        map.put("cardNo", request.getParameter("CardNo"));
        map.put("cardName", request.getParameter("cardName"));
        map.put("cardCode", request.getParameter("cardCode"));
        map.put("expYY", request.getParameter("expYY"));
        map.put("expMM", request.getParameter("expMM"));
        map.put("cardCl", request.getParameter("CardCl"));
        map.put("userId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("birDate", OrderInvoiceUtil.getyyyyMMddHHmmss());
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeBillResult(HttpServletRequest request, HashMap resultData) {
        Map<String, Object> map = new HashMap<>();
        map.put("ResultCode", resultData.get("ResultCode"));
        map.put("ResultMsg", resultData.get("ResultMsg"));
        map.put("TID", resultData.get("TID"));
        map.put("Moid", resultData.get("Moid"));
        map.put("Amt", resultData.get("Amt"));
        map.put("AuthCode", resultData.get("AuthCode"));
        map.put("AuthDate", resultData.get("AuthDate"));
        map.put("AcquCardCode", resultData.get("AcquCardCode"));
        map.put("AcquCardName", resultData.get("AcquCardName"));
        map.put("CardNo", resultData.get("CardNo"));
        map.put("CardCode", resultData.get("CardCode"));
        map.put("CardName", resultData.get("CardName"));
        map.put("CardQuota", resultData.get("CardQuota"));
        map.put("CardCl", resultData.get("CardCl"));
        map.put("CardInterest", resultData.get("CardInterest"));
        map.put("CcPartCl", resultData.get("CcPartCl"));
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));
        
        return map;
    }

    public static void makeCompleteMap(Map<String, Object> modelMap, Model model) {
        if (model == null) {
            return;
        }
        if (modelMap == null || modelMap.size() == 0) {
            return;
        }

        for (String key : modelMap.keySet()) {
            model.addAttribute(key, modelMap.get(key));
        }
//        CompleteInvoiceDTO invoiceDTO = new CompleteInvoiceDTO();
//        Map<String, Object> modelMap = model.asMap();
//
//        return invoiceDTO;
    }

    public static OrderTicketVO makePaymentList(OrderTicketVO ticketVO) {
        if (ticketVO == null) {
            return null;
        }
        OrderTicketVO paymentTicketVO = new OrderTicketVO();
        paymentTicketVO.setDiscountPrice(ticketVO.getDiscountPrice());
        paymentTicketVO.setMemberId(ticketVO.getMemberId());
        paymentTicketVO.setMemberPrice(ticketVO.getMemberPrice());
        paymentTicketVO.setMuMpFlag(ticketVO.getMuMpFlag());
        paymentTicketVO.setNormalPrice(ticketVO.getNormalPrice());
        paymentTicketVO.setOrderNumber(ticketVO.getOrderNumber());
        paymentTicketVO.setOrderSequence(ticketVO.getOrderSequence());
        paymentTicketVO.setSumDiscountPrice(ticketVO.getDiscountPrice());
        paymentTicketVO.setSumMemberPrice(ticketVO.getMemberPrice());
        paymentTicketVO.setSumNormalPrice(ticketVO.getNormalPrice());
        paymentTicketVO.setTicketNm(ticketVO.getTicketNm());
        paymentTicketVO.setTicketNo(1);
        paymentTicketVO.setTicketSeq(ticketVO.getTicketSeq());
        paymentTicketVO.setTicketShortNm(ticketVO.getTicketShortNm());

        return paymentTicketVO;
    }

    /* 콘도 결제 */
    public static Map<String, Object> makeInsertPaid(HttpServletRequest request, HashMap resultData) {
        if (request == null || resultData == null) {
            return null;
        }

        final String[] SRC_MAP_ARRAY = {"Moid", "CardNo", "MID", "AuthCode", "CardQuota"};
        final String[] DEST_MAP_ARRAY = {"ipPurNo", "ipCardNo", "ipMid", "ipApproveNo", "ipDivideTerm"};

        Map<String, Object> map = new HashMap<>();
        for (int i = 0, size = SRC_MAP_ARRAY.length; i < size; i++) {
            map.put(DEST_MAP_ARRAY[i], resultData.get(SRC_MAP_ARRAY[i]));
        }
        int totalMemberPrice = Integer.parseInt(resultData.get("Amt").toString());
        int vatAmount = 0;
        if (totalMemberPrice > 0) {
            vatAmount = (int) (totalMemberPrice / 11);
        }
        map.put("ipAgentCd", "92");
        map.put("ipPaidDt", OrderInvoiceUtil.calculateDateFormat(0L, "yyyyMMdd"));
        String acquCardCode = null;
        if (resultData.containsKey("AcquCardCode") && resultData.get("AcquCardCode") != null) {
            acquCardCode = resultData.get("AcquCardCode").toString();
        }
        map.put("ipPurcCode", CreditCard.getCardCode(acquCardCode));
        map.put("ipPurcName", CreditCard.getCardName(acquCardCode));
        map.put("ipCardAmt", totalMemberPrice);
        map.put("ipCardCancel", "0");
        map.put("ipPaidStore", "62");
        map.put("ipNetAmt", totalMemberPrice - vatAmount);
        map.put("ipVatAmt", vatAmount);
        map.put("ipSync", "I");
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }
    
    /* 콘도결제 */
    public static Map<String, Object> makeInsertPaid(HttpServletRequest request, HashMap resultData, List<OrderTicketVO> orderList) {
        if (request == null || resultData == null || orderList == null) {
            return null;
        }

        final String[] SRC_MAP_ARRAY = {"Moid", "CardNo", "MID", "AuthCode", "CardQuota"};
        final String[] DEST_MAP_ARRAY = {"ipPurNo", "ipCardNo", "ipMid", "ipApproveNo", "ipDivideTerm"};

        Map<String, Object> map = new HashMap<>();
        for (int i = 0, size = SRC_MAP_ARRAY.length; i < size; i++) {
            map.put(DEST_MAP_ARRAY[i], resultData.get(SRC_MAP_ARRAY[i]));
        }
        int totalMemberPrice = OrderValueGenerator.getTotalMemberPrice(orderList);
        int vatAmount = 0;
        if (totalMemberPrice > 0) {
            vatAmount = (int) (totalMemberPrice / 11);
        }
        map.put("ipAgentCd", "92");
        map.put("ipPaidDt", OrderInvoiceUtil.calculateDateFormat(0L, "yyyyMMdd"));
        String acquCardCode = null;
        if (resultData.containsKey("AcquCardCode") && resultData.get("AcquCardCode") != null) {
            acquCardCode = resultData.get("AcquCardCode").toString();
        }
        map.put("ipPurcCode", CreditCard.getCardCode(acquCardCode));
        map.put("ipPurcName", CreditCard.getCardName(acquCardCode));
        map.put("ipCardAmt", totalMemberPrice);
        map.put("ipCardCancel", "0");
        map.put("ipPaidStore", "62");
        map.put("ipNetAmt", totalMemberPrice - vatAmount);
        map.put("ipVatAmt", vatAmount);
        map.put("ipSync", "I");
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makePaidVoid(HttpServletRequest request, CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("pvPurNo", ticketVO.getOrderNo());
        map.put("pvCancelDt", OrderInvoiceUtil.calculateDateFormat(0L, "yyyyMMdd"));
        int cancelAmount = ticketVO.getMemAmount();
        int vatAmount = (int) (cancelAmount / 11);
        map.put("pvCancelAmt", cancelAmount);
        map.put("pvCancelNet", (cancelAmount - vatAmount));
        map.put("pvCancelVat", vatAmount);
        map.put("pvSync", "I");
        map.put("isrtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("isrtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeUpdatePaid(HttpServletRequest request, CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("ipPurNo", ticketVO.getOrderNo());
        String ipCardCancel = "1";
        if (ticketVO.getGoodsNo() == 1) {
            ipCardCancel = "5";
        } else {
            if (ticketVO.getGoodsNo() == (ticketVO.getCancelCount() + 1)) {
                ipCardCancel = "5";
            }
        }
        map.put("ipCardCancel", ipCardCancel);
        map.put("ipCancelDate", OrderInvoiceUtil.calculateDateFormat(0L, "yyyyMMdd"));
        map.put("ipCancelAmt", ticketVO.getMemAmount());
        map.put("updtId", OrderInvoiceUtil.getMemberAttribute(request,"id"));
        map.put("updtIp", OrderInvoiceUtil.getClientIP(request));

        return map;
    }

    public static Map<String, Object> makeRefundPaidVoid(CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("pvPurNo", ticketVO.getOrderNo());
        map.put("pvCancelDt", OrderInvoiceUtil.calculateDateFormat(0L, "yyyyMMdd"));
        int cancelAmount = ticketVO.getMemAmount();
        int vatAmount = (int) (cancelAmount / 11);
        map.put("pvCancelAmt", cancelAmount);
        map.put("pvCancelNet", (cancelAmount - vatAmount));
        map.put("pvCancelVat", vatAmount);
        map.put("pvSync", "I");
        map.put("isrtId", "batch");
        map.put("isrtIp", "127.0.0.1");

        return map;
    }

    public static Map<String, Object> makeUpdateRefundPaid(CancelTicketVO ticketVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("ipPurNo", ticketVO.getOrderNo());
        String ipCardCancel = "1";
        if (ticketVO.getGoodsNo() == 1) {
            ipCardCancel = "5";
        } else {
            if (ticketVO.getGoodsNo() == (ticketVO.getCancelCount() + 1)) {
                ipCardCancel = "5";
            }
        }
        map.put("ipCardCancel", ipCardCancel);
        map.put("ipCancelDate", OrderInvoiceUtil.calculateDateFormat(0L, "yyyyMMdd"));
        map.put("ipCancelAmt", ticketVO.getMemAmount());
        map.put("updtId", "batch");
        map.put("updtIp", "127.0.0.1");

        return map;
    }
}
