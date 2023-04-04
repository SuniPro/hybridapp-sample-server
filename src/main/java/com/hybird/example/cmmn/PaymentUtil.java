package com.hybird.example.cmmn;

import com.hybird.example.user.payMent.vo.MemberBasketVO;
import com.hybird.example.user.payMent.vo.OrderTicketVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

public class PaymentUtil {

    /**
     * 로그인 여부
     * @param request
     * @return
     */
    public static boolean isLogin(HttpServletRequest request) {
        EgovMap memberInfo = listMemberAttributes(request);
        if (memberInfo == null) {
            return false;
        } else {
            if (memberInfo.containsKey("id")) {
                String id = String.valueOf(memberInfo.get("id"));
                if (id == null || id.equals("")) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Session에서 사용자 정보중 속성값 추출
     * id, kname, password, handphone1, handphone2, handphone3, email, memberType, memberNum
     * @param request
     * @param key
     * @return
     */
    public static String getMemberAttribute(HttpServletRequest request, final String key) {
        EgovMap memberInfo = listMemberAttributes(request);
        if (memberInfo == null) {
            return null;
        }

        String value = null;
        if (memberInfo.containsKey(key)) {
            value = String.valueOf(memberInfo.get(key));
        }

        return value;
    }

    /**
     * 회원 등급
     * @param request
     * @return
     */
    public static String getMemberType(HttpServletRequest request) {
        String memberType = getMemberAttribute(request, "memberType");
        if (memberType == null || memberType.length() == 0) {
            return "A";
        }
        if ("G|R|g|r".contains(memberType)) {
            return memberType.toUpperCase(Locale.ROOT);
        }

        return "A";
    }

    /**
     * 세션에서 사용자 정보 추출
     * @param request
     * @return
     */
    public static EgovMap listMemberAttributes(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("UserInfo");
        EgovMap memberInfo = null;
        if (sessionObj != null) {
            memberInfo = (EgovMap) sessionObj;
        }

        return memberInfo;
    }

    public static void saveSessionForOrderInvoice(HttpServletRequest request, List<OrderTicketVO> orderList) {
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("orderInvoice", orderList);
    }

    public static List<OrderTicketVO> getSessionForOrderInvoice(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("orderInvoice");
        List<OrderTicketVO> list = null;
        if (sessionObj != null) {
            list = (List<OrderTicketVO>) sessionObj;
        }

        return list;
    }

    public  static void removeSession(HttpServletRequest request, String key) {
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }

    /**
     * 결제하기 요청 파라미터 Base64 디코드
     * @param query
     * @return
     */
    public static String decodeQuery(final String query) {
        if (query == null) {
            return null;
        }

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(query.getBytes(StandardCharsets.UTF_8));

        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 결제하기 요청 파라미터 Base64 디코드된 Json데이터 파싱 
     * @param query
     * @return
     */
    public static JSONObject queryToJson(final String query) {
        if (query == null) {
            return null;
        }

        JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONObject jsonObj = null;
        try {
            Object obj = parser.parse(query);
            jsonObj = (JSONObject) obj;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonObj;
    }

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
     * 장바구니 조회용 VO 생성
     * @param orderInvoice
     * @return
     */
    public static List<MemberBasketVO> makeBasketQeury(final JSONObject orderInvoice) {
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
            basket.setMemberId(mid);
            basket.setTicketSeq(id);
            basket.setUnitPackageType(type);
            basket.setTicketCount(cnt);

            list.add(basket);
        }

        return list;
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static void setModelForOrderInvoice(Model model, List<OrderTicketVO> orderList) {
        int totalTicketNo = 0;
        int totalNormalPrice = 0;
        int totalMemberPrice = 0;
        int totalDiscountPrice = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                totalTicketNo += ticket.getTicketNo();
                totalNormalPrice += ticket.getSumNormalPrice();
                totalMemberPrice += ticket.getSumMemberPrice();
                totalDiscountPrice += ticket.getSumDiscountPrice();
            }
        }
        DecimalFormat formatter = new DecimalFormat("###,###");

        model.addAttribute("totalTicketNo", formatter.format(totalTicketNo));
        model.addAttribute("totalNormalPrice", formatter.format(totalNormalPrice));
        model.addAttribute("totalMemberPrice", formatter.format(totalMemberPrice));
        model.addAttribute("totalDiscountPrice", formatter.format((-1) * totalDiscountPrice));
    }
}
