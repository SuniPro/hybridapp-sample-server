package com.hybird.example.user.payMent.util;

import com.hybird.example.user.payMent.vo.CancelTicketVO;
import com.hybird.example.user.payMent.vo.OrderTicketVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public class OrderValidator {
    /**
     * 결제하기 요청 파라미터 유효성 체크
     * @param invoice
     * @return
     */
    public static String validateOrderInvoice(final JSONObject invoice) {
        if (invoice == null) {
            return "FAIL";
        }

        if (!validateJsonObject(invoice, "mid")) {
            return "FAIL";
        }

        if (!validateJsonObject(invoice, "type")) {
            return "FAIL";
        }

        if (!validateJsonArray(invoice, "data")) {
            return "FAIL";
        }

        return "SUCCESS";
    }

    private static boolean validateJsonObject(final JSONObject invoice, final String key) {
        if (invoice == null || key == null) {
            return false;
        }
        if (!invoice.containsKey(key)) {
            return false;
        }
        if (invoice.get(key) == null) {
            return false;
        }

        return true;
    }

    private static boolean validateJsonArray(final JSONObject invoice, final String key) {
        if (invoice == null || key == null) {
            return false;
        }
        if (!invoice.containsKey(key)) {
            return false;
        }
        if (invoice.get(key) == null) {
            return false;
        }

        JSONArray dataArray = (JSONArray) invoice.get("data");
        if (dataArray.size() == 0) {
            return false;
        }
        for (Object object : dataArray) {
            JSONObject goods = (JSONObject) object;
            if (!validateJsonObject(goods, "type")) {
                return false;
            }
            if (!validateJsonObject(goods, "id")) {
                return false;
            }
            if (!validateJsonObject(goods, "cnt")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 장바구니 상품과 금액이 다른지 체크
     * @param map
     * @param orderList
     * @return
     */
    public static boolean checkPayAmount(Map<String, Object> map, List<OrderTicketVO> orderList) {
        int totalMemberPrice = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                totalMemberPrice += ticket.getSumMemberPrice();
            }
        }
        int orderAmount = Integer.parseInt(((String)map.get("price")));

        System.out.println("totalMemberPrice : " + totalMemberPrice);
        System.out.println("orderAmount : " + orderAmount);

        return (totalMemberPrice > 0) && (totalMemberPrice == orderAmount);
    }

    public static String validateCertification(Map<String, Object> niceCertMap) {
        if (niceCertMap == null) {
            return ResponseCode.SYS_3000.getCode();
        }
        /* 1. 인증 결과 코드 */
        if (niceCertMap.get("authResultCode") == null) {
            return ResponseCode.SYS_3001.getCode();
        }
        if (!"0000".equals(String.valueOf(niceCertMap.get("authResultCode")))) {
            return ResponseCode.SYS_3001.getCode();
        }
        /* 2. 인증 Token */
        if (niceCertMap.get("authToken") == null) {
            return ResponseCode.SYS_3002.getCode();
        }
        /* 3. 결제수단 */
        if (niceCertMap.get("payMethod") == null) {
            return ResponseCode.SYS_3003.getCode();
        }
        /* 4. 상점아이디 */
        if (niceCertMap.get("mid") == null) {
            return ResponseCode.SYS_3004.getCode();
        }
        /* 5. 주문번호 */
        if (niceCertMap.get("moid") == null) {
            return ResponseCode.SYS_3005.getCode();
        }
        /* 6.결제금액 */
        if (niceCertMap.get("amt") == null) {
            return ResponseCode.SYS_3006.getCode();
        }
        /* 7. 위변조 검증 데이터 */
        if (niceCertMap.get("signature") == null) {
            return ResponseCode.SYS_3007.getCode();
        }
        /* 8. 거래 ID */
        if (niceCertMap.get("txTid") == null) {
            return ResponseCode.SYS_3008.getCode();
        }

        return ResponseCode.SYS_0000.getCode();
    }

    public static String validateCancelTicket(CancelTicketVO ticketVO) {
        if (ticketVO == null) {
            return ResponseCode.SYS_6000.getCode();
        }
        /* 1. 이미 취소된 상품인지 체크 */
        if (ticketVO.getCancelFlag() == 1) {
            return ResponseCode.SYS_6001.getCode();
        }
        /* 2. 유효기간이 만료된 상품인지 체크 */
//        if ("Y".equals(ticketVO.getCloseYn())) {
//            return ResponseCode.SYS_6002.getCode();
//        }

        return ResponseCode.SYS_0000.getCode();
    }
}
