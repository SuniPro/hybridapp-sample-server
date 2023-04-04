package com.hybird.example.user.payMent.util;

import com.hybird.example.user.payMent.vo.MemberBasketVO;
import com.hybird.example.user.payMent.vo.OrderTicketVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class OrderValueGenerator {
    /* 벨포레 도메인 URL */
    public static final String BELLEFORET_DOMAIN = NicePayProperty.getProperty("belleforet.domain");
    /* 신용카드 결제 완료후 호출 URL */
    public static final String CARD_RETURN_URL = BELLEFORET_DOMAIN + NicePayProperty.getProperty("card.return.url");
    /* 신용카드 콘도 결제 완료후 호출 URL | 2022.10.21 | hjm */
    public static final String CONDO_RETURN_URL = BELLEFORET_DOMAIN + NicePayProperty.getProperty("condo.return.url");
    /* Ios 결제후 app 전환 스키마 */
    public static final String WAP_URL = NicePayProperty.getProperty("wap.url");
    /* Ios 결제취소후 app 전환 스키마 */
    public static final String ISP_CANCEL_URL = NicePayProperty.getProperty("isp.cancel.url");
    /* 간편결제 등록후 처리 요청 URL */
    public static final String BILL_RETURN_URL = BELLEFORET_DOMAIN + NicePayProperty.getProperty("bill.return.url");
    /* 나이스페이 취소요청 URL */
    public static final String NICE_CANCEL_REQUEST_URL = NicePayProperty.getProperty("nice.cancel.request.url");
    /* 나이스페이 간편결제 승인요청 URL */
    public static final String NICE_BILL_REQUEST_URL = NicePayProperty.getProperty("nice.bill.request.url");

    /* Merchant Key */
    public static final String NICE_MERCHANT_KEY_ORG = NicePayProperty.getProperty("nice.merchant.key.org");
    public static final String NICE_MERCHANT_KEY_KAKAO = NicePayProperty.getProperty("nice.merchant.key.kakao");
    public static final String NICE_MERCHANT_KEY_NAVER = NicePayProperty.getProperty("nice.merchant.key.naver");
    public static final String NICE_MERCHANT_KEY_BILL = NicePayProperty.getProperty("nice.merchant.key.bill");

    /* Merchant ID */
    public static final String NICE_MERCHANT_ID_ORG = NicePayProperty.getProperty("nice.merchant.id.org");
    public static final String NICE_MERCHANT_ID_KAKAO = NicePayProperty.getProperty("nice.merchant.id.kakao");
    public static final String NICE_MERCHANT_ID_NAVER = NicePayProperty.getProperty("nice.merchant.id.naver");
    public static final String NICE_MERCHANT_ID_BILL = NicePayProperty.getProperty("nice.merchant.id.bill");

    public static String getMerchantKey(String payMethod) {
        if (payMethod != null && "KAKAO".equals(payMethod)) {
            return NICE_MERCHANT_KEY_KAKAO;
        }
        if (payMethod != null && "NAVER".equals(payMethod)) {
            return NICE_MERCHANT_KEY_NAVER;
        }
        if (payMethod != null && "BILL".equals(payMethod)) {
            return NICE_MERCHANT_KEY_BILL;
        }

        return NICE_MERCHANT_KEY_ORG;
    }

    public static String getMerchantId(String payMethod) {
        if (payMethod != null && "KAKAO".equals(payMethod)) {
            return NICE_MERCHANT_ID_KAKAO;
        }
        if (payMethod != null && "NAVER".equals(payMethod)) {
            return NICE_MERCHANT_ID_NAVER;
        }
        if (payMethod != null && "BILL".equals(payMethod)) {
            return NICE_MERCHANT_ID_BILL;
        }

        return NICE_MERCHANT_ID_ORG;
    }

    public static int getTotalTicketCount(List<OrderTicketVO> orderList) {
        int totalTicketNo = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                totalTicketNo += ticket.getTicketNo();
            }
        }

        return totalTicketNo;
    }

    public static int getTotalNormalPrice(List<OrderTicketVO> orderList) {
        int totalNormalPrice = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                totalNormalPrice += ticket.getSumNormalPrice();
            }
        }

        return totalNormalPrice;
    }
    public static int getTotalMemberPrice(List<OrderTicketVO> orderList) {
        int totalMemberPrice = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                totalMemberPrice += ticket.getSumMemberPrice();
            }
        }

        return totalMemberPrice;
    }
    public static int getTotalDiscountPrice(List<OrderTicketVO> orderList) {
        int totalDiscountPrice = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                totalDiscountPrice += ticket.getSumDiscountPrice();
            }
        }

        return (-1) * totalDiscountPrice;
    }
    public static String getPaymentGoodsName(List<OrderTicketVO> orderList) {
        String goodsName = "";
        int count = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                if (count == 0) {
                    goodsName = ticket.getTicketNm();
                }
                count++;
            }
        }
        if (count > 1) {
            goodsName += " 외 " + (count - 1);
        }
        goodsName = OrderInvoiceUtil.substringBytes(goodsName, 0, 40);

        return goodsName;
    }

    /**
     * 장바구니 조회용 VO 생성
     * @param orderInvoice
     * @return
     */
    public static List<MemberBasketVO> makeBasketQeury(final JSONObject orderInvoice, final String memberId) {
        if (orderInvoice == null) {
            return null;
        }
        List<MemberBasketVO> list = new ArrayList<>();
        String mid = String.valueOf(orderInvoice.get("mid"));

        JSONArray dataArray = (JSONArray) orderInvoice.get("data");
        for (Object object : dataArray) {
            JSONObject goods = (JSONObject) object;
            String tmpType = String.valueOf(goods.get("type"));
            String type = null;
            if (tmpType.equals("unit")) {
                type = "S";
            }
            if (tmpType.equals("pkg")) {
                type = "P";
            }
            if (type == null) {
                continue;
            }
            String id = String.valueOf(goods.get("id"));
            String tmpCnt = String.valueOf(goods.get("cnt"));
            Integer cnt = Integer.valueOf(tmpCnt);

            MemberBasketVO basket = new MemberBasketVO();
            basket.setMemberId(memberId);
            basket.setTicketSeq(id);
            basket.setUnitPackageType(type);
            basket.setTicketCount(cnt);

            list.add(basket);
        }

        return list;
    }

    public static String searchMerchantKey(String mid) {
        if (NICE_MERCHANT_ID_KAKAO.equals(mid)) {
            return NICE_MERCHANT_KEY_KAKAO;
        }
        if (NICE_MERCHANT_ID_NAVER.equals(mid)) {
            return NICE_MERCHANT_KEY_NAVER;
        }
        if (NICE_MERCHANT_ID_BILL.equals(mid)) {
            return NICE_MERCHANT_KEY_BILL;
        }

        return NICE_MERCHANT_KEY_ORG;
    }

    public static String getValueForResultMap(HashMap resultData, final String key) {
        if (resultData == null) {
            return null;
        }
        String value = null;
        if (resultData.containsKey(key)) {
            if (resultData.get(key) != null) {
                value = String.valueOf(resultData.get(key));
            }
        }

        return value;
    }

    public static String getValueForMap(Map<String, Object> resultData, final String key) {
        if (resultData == null) {
            return null;
        }
        String value = null;
        if (resultData.containsKey(key)) {
            if (resultData.get(key) != null) {
                value = String.valueOf(resultData.get(key));
            }
        }

        return value;
    }

    public static boolean isPaySuccess(HashMap resultData) {
        String payMethod = getValueForResultMap(resultData, "PayMethod");
        String resultCode = getValueForResultMap(resultData, "ResultCode");
        if (payMethod == null || resultCode == null) {
            return false;
        }

        if(payMethod.equals("CARD") && resultCode.equals("3001")){
            return true;
        }
        if(payMethod.equals("BANK") && resultCode.equals("4000")){
            return true;
        }
        if(payMethod.equals("CELLPHONE") && resultCode.equals("A000")){
            return true;
        }
        if(payMethod.equals("VBANK") && resultCode.equals("4100")){
            return true;
        }
        if(payMethod.equals("SSG_BANK") && resultCode.equals("0000")){
            return true;
        }
        if(payMethod.equals("CMS_BANK") && resultCode.equals("0000")){
            return true;
        }

        return false;
    }

    public static boolean isCancelySuccess(HashMap resultData) {
        String resultCode = getValueForResultMap(resultData, "ResultCode");
        if (resultCode == null) {
            return false;
        }

        if("2001".equals(resultCode)){
            return true;
        }
        if("2211".equals(resultCode)){
            return true;
        }

        return false;
    }

    public static String getPaymentName(final String payMethod) {
        if (payMethod == null) {
            return null;
        }

        if(payMethod.equals("CARD")){
            return  "카드";
        }
        if(payMethod.equals("BANK")){
            return "계좌이체";
        }
        if(payMethod.equals("CELLPHONE")){
            return "휴대폰";
        }
        if(payMethod.equals("VBANK")){
            return "가상계좌";
        }
        if(payMethod.equals("SSG_BANK")){
            return "SSG은행계좌";
        }
        if(payMethod.equals("CMS_BANK")){
            return "계좌간편결제";
        }

        return "";
    }

    public static String makeRandomDigit(int len, int dupCd ) {
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i = 0; i < len; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            if (dupCd == 1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            } else if (dupCd == 2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }

        return numStr;
    }
}
