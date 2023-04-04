package com.hybird.example.user.payMent.util;

import com.hybird.example.user.payMent.vo.CondoSessionVO;
import com.hybird.example.user.payMent.vo.OrderSessionVO;
import com.hybird.example.user.payMent.vo.OrderTicketVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderInvoiceUtil {
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
                if (id == null || "".equals(id) || "null".equals(id)) {
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
            if (value == null || "".equals(value) || "null".equals(value)) {
                value = null;
            }
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
        System.out.println("memberType : " + memberType);
        if (memberType == null || memberType.length() == 0 || "null".equals(memberType)) {
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

//    public static void saveSessionForQuery(HttpServletRequest request, String query) {
//        if (request == null) {
//            return;
//        }
//        HttpSession session = request.getSession();
//        session.removeAttribute("query");
//        session.setAttribute("query", query);
//    }

//    public static List<OrderTicketVO> getSessionForOrderInvoice(HttpServletRequest request) {
//        if (request == null) {
//            return null;
//        }
//        HttpSession session = request.getSession();
//        Object sessionObj = session.getAttribute("orderInvoice");
//        List<OrderTicketVO> list = null;
//        if (sessionObj != null) {
//            list = (List<OrderTicketVO>) sessionObj;
//        }
//
//        return list;
//    }

    public static OrderSessionVO getSessionForOrderInvoice(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("orderInvoice");
        OrderSessionVO orderSessionVO = null;
        if (sessionObj != null) {
            orderSessionVO = (OrderSessionVO) sessionObj;
        }

        return orderSessionVO;
    }
    public static CondoSessionVO getSessionForCondoPay(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("condoSessionVO");
        CondoSessionVO condoSessionVO = null;
        if (sessionObj != null) {
            condoSessionVO = (CondoSessionVO) sessionObj;
        }
        return condoSessionVO;
    }

    public static List<OrderTicketVO> getSessionForTicketPayList(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute("payList");
        List<OrderTicketVO> paymentList = null;
        if (sessionObj != null) {
            paymentList = (List<OrderTicketVO>) sessionObj;
        }
        return paymentList;
    }

    public static void saveSessionForOrderInvoice(HttpServletRequest request, OrderSessionVO orderSessionVO) {
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        session.removeAttribute("orderInvoice");
        session.setAttribute("orderInvoice", orderSessionVO);
    }
    public static void saveSessionForCondoPay(HttpServletRequest request, CondoSessionVO condoSessionVO) {
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        session.removeAttribute("condoSessionVO");
        session.setAttribute("condoSessionVO", condoSessionVO);
    }
    public static void saveSessionForTicketPayList(HttpServletRequest request, List<OrderTicketVO> payList) {
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        session.removeAttribute("payList");
        session.setAttribute("payList", payList);
    }

//    public static void saveSessionForCompleteInvoice(HttpServletRequest request, Map<String, Object> modelMap) {
//        if (request == null) {
//            return;
//        }
//        HttpSession session = request.getSession();
//        session.removeAttribute("completeInvoice");
//        session.setAttribute("completeInvoice", modelMap);
//    }

//    public static Map<String, Object> getSessionForCompleteInvoice(HttpServletRequest request) {
//        if (request == null) {
//            return null;
//        }
//        HttpSession session = request.getSession();
//        Object sessionObj = session.getAttribute("completeInvoice");
//        Map<String, Object> modelMap = null;
//        if (sessionObj != null) {
//            modelMap = (Map<String, Object>) sessionObj;
//        }
//
//        return modelMap;
//    }

//    public static String getSessionForQuery(HttpServletRequest request) {
//        if (request == null) {
//            return null;
//        }
//        HttpSession session = request.getSession();
//        Object sessionObj = session.getAttribute("query");
//        String query = null;
//        if (sessionObj != null) {
//            query = (String) sessionObj;
//        }
//
//        return query;
//    }

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

    /**
     * 주문상품 가격정보 Model에 담기
     * @param model
     * @param orderList
     */
    public static void setModelForOrderInvoice(Model model, List<OrderTicketVO> orderList) {
        int totalTicketNo = 0;
        int totalNormalPrice = 0;
        int totalMemberPrice = 0;
        int totalDiscountPrice = 0;
        String goodsName = "";
        int count = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                if (count == 0) {
                    goodsName = ticket.getTicketNm();
                }
                totalTicketNo += ticket.getTicketNo();
                totalNormalPrice += ticket.getSumNormalPrice();
                totalMemberPrice += ticket.getSumMemberPrice();
                totalDiscountPrice += ticket.getSumDiscountPrice();
                count++;
            }
        }
        if (count > 1) {
            goodsName += " 외 " + (count - 1);
        }
        model.addAttribute("goodsName", substringBytes(goodsName, 0, 40));
        model.addAttribute("totalTicketNo", totalTicketNo);
        model.addAttribute("totalNormalPrice", totalNormalPrice);
        model.addAttribute("totalMemberPrice", totalMemberPrice);
        model.addAttribute("totalDiscountPrice", (-1) * totalDiscountPrice);
    }

    public static void setSessionForOrderInvoice(HttpServletRequest request, List<OrderTicketVO> orderList) {
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        int totalTicketNo = 0;
        int totalNormalPrice = 0;
        int totalMemberPrice = 0;
        int totalDiscountPrice = 0;
        String goodsName = "";
        int count = 0;
        if (orderList != null && orderList.size() > 0) {
            for (OrderTicketVO ticket : orderList) {
                if (count == 0) {
                    goodsName = ticket.getTicketNm();
                }
                totalTicketNo += ticket.getTicketNo();
                totalNormalPrice += ticket.getSumNormalPrice();
                totalMemberPrice += ticket.getSumMemberPrice();
                totalDiscountPrice += ticket.getSumDiscountPrice();
                count++;
            }
        }
        if (count > 1) {
            goodsName += " 외 " + (count - 1);
        }
        /* Session에서 속성 삭제 */
        session.removeAttribute("goodsName");
        session.removeAttribute("totalTicketNo");
        session.removeAttribute("totalNormalPrice");
        session.removeAttribute("totalMemberPrice");
        session.removeAttribute("totalDiscountPrice");
        /* Session에 저장 */
        session.setAttribute("goodsName", substringBytes(goodsName, 0, 40));
        session.setAttribute("totalTicketNo", totalTicketNo);
        session.setAttribute("totalNormalPrice", totalNormalPrice);
        session.setAttribute("totalMemberPrice", totalMemberPrice);
        session.setAttribute("totalDiscountPrice", (-1) * totalDiscountPrice);
    }

    public static String substringBytes(String str, int beginBytes, int endBytes) {
        if (str == null || str.length() == 0) {
            return "";
        }

        if (beginBytes < 0) {
            beginBytes = 0;
        }
        if (endBytes < 1) {
            return "";
        }

        int beginIndex = -1;
        int endIndex = 0;

        int curBytes = 0;
        String ch = null;
        for (int i = 0, len = str.length(); i < len; i++) {
            ch = str.substring(i, i + 1);
            curBytes += ch.getBytes().length;

            if (beginIndex == -1 && curBytes >= beginBytes) {
                beginIndex = i;
            }

            if (curBytes > endBytes) {
                break;
            } else {
                endIndex = i + 1;
            }
        }

        return str.substring(beginIndex, endIndex);
    }

    public static String getyyyyMMddHHmmss(){
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        return yyyyMMddHHmmss.format(new Date());
    }

    public static String makeOrderNumber(final String sequence) {
        return calculateDateFormat(0L, "yyMMddHHmmss") + sequence;
    }

    public static String calculateDateFormat(final long interval, final String format) {
        LocalDateTime dateTime = null;
        if (interval > 0) {
            dateTime = LocalDateTime.now().plusDays(interval);
        } else if (interval < 0) {
            dateTime = LocalDateTime.now().minusDays(Math.abs(interval));
        } else {
            dateTime = LocalDateTime.now();
        }

        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static String connectToServer(String data, String reqUrl) throws Exception{
        HttpURLConnection conn 		= null;
        BufferedReader resultReader = null;
        PrintWriter pw 				= null;
        URL url 					= null;

        int statusCode = 0;
        StringBuffer recvBuffer = new StringBuffer();
        try{
            url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(25000);
            conn.setDoOutput(true);

            pw = new PrintWriter(conn.getOutputStream());
            pw.write(data);
            pw.flush();

            statusCode = conn.getResponseCode();
            resultReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            for(String temp; (temp = resultReader.readLine()) != null;){
                recvBuffer.append(temp).append("\n");
            }

            if(!(statusCode == HttpURLConnection.HTTP_OK)){
                throw new Exception();
            }

            return recvBuffer.toString().trim();
        }catch (Exception e){
            return "9999";
        }finally{
            recvBuffer.setLength(0);

            try{
                if(resultReader != null){
                    resultReader.close();
                }
            }catch(Exception ex){
                resultReader = null;
            }

            try{
                if(pw != null) {
                    pw.close();
                }
            }catch(Exception ex){
                pw = null;
            }

            try{
                if(conn != null) {
                    conn.disconnect();
                }
            }catch(Exception ex){
                conn = null;
            }
        }
    }

    public static HashMap jsonStringToHashMap(String str) throws Exception{
        HashMap dataMap = new HashMap();
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(str);
            JSONObject jsonObject = (JSONObject)obj;

            Iterator<String> keyStr = jsonObject.keySet().iterator();
            while(keyStr.hasNext()){
                String key = keyStr.next();
                Object value = jsonObject.get(key);

                dataMap.put(key, value);
            }
        }catch(Exception e){

        }
        return dataMap;
    }
}
