package com.hybird.example.user.payMent.web;

import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.PaymentUtil;
import com.hybird.example.main.service.MainService;
import com.hybird.example.user.payMent.service.PaidService;
import com.hybird.example.user.payMent.util.*;
import com.hybird.example.user.payMent.service.OrderInvoiceService;
import com.hybird.example.user.payMent.service.PaidVO;
import com.plnc.user.payMent.util.*;
import com.hybird.example.user.payMent.vo.MemberBasketVO;
import com.hybird.example.user.payMent.vo.OrderSessionVO;
import com.hybird.example.user.payMent.vo.OrderTicketVO;
import com.hybird.example.user.payMent.vo.TicketInformation;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/user/payMent")
public class PayController {
    @Resource(name = "OrderInvoiceService")
    private OrderInvoiceService orderInvoiceService;

    @Resource
    private MainService mainService;

    @Resource
    private PaidService paidService;

    @GetMapping("/moveTicket.do")
    public String moveCondoRes() {
        return "user:/payMent/moveTicket";
    }

    @GetMapping("/failPayment.do")
    public String moveFailPayment(Model model) {
        return "user:/payMent/failPayment";
    }

    @GetMapping("/failOrder.do")
    public String moveFailOrder(Model model) {
        return "user:/payMent/failOrder";
    }

    @GetMapping("/tmp.do")
    public String moveInvoice(Model model) {
        return "user:/payMent/moveInvoice";
    }

    @GetMapping("/orderInvoice.do")
    public String orderInvoice(HttpServletRequest request, String query, Model model, HttpServletResponse response) throws Exception {
        /* 1. 로그인 체크  */
        if (!PaymentUtil.isLogin(request)) {
            return "redirect:/login.do";
        }
        /* 2. query : 구매상품정보 체크 */
        OrderSessionVO orderSessionVO = OrderInvoiceUtil.getSessionForOrderInvoice(request);
        if (query == null && orderSessionVO != null && orderSessionVO.getQuery() != null) {
            query = orderSessionVO.getQuery();
        }
        log.info("query : {}", query);
//        query = "eyJtaWQiOiJieXRlcnVzIiwidHlwZSI6ImRpcmVjdCIsImRhdGEiOlt7InR5cGUiOiJ1bml0IiwiaWQiOiI0IiwiY250IjoxfSx7InR5cGUiOiJwa2ciLCJpZCI6IjMiLCJjbnQiOjF9LHsidHlwZSI6InBrZyIsImlkIjoiNCIsImNudCI6M31dfQ==";

        JSONObject orderInvoice = OrderInvoiceUtil.queryToJson(PaymentUtil.decodeQuery(query));
        String validateResult = OrderValidator.validateOrderInvoice(orderInvoice);
        if (validateResult.equals("FAIL")) {
            model.addAttribute("code", ResponseCode.SYS_2000.getCode());
            model.addAttribute("msg",ResponseCode.SYS_2000.getMessage());
            return "user:/payMent/orderInvoice";
        }
        log.info("orderInvoice : {}", orderInvoice);
        log.info("validate : {}", validateResult);
        log.info("memberNo : {}", OrderInvoiceUtil.getMemberAttribute(request, "memberNo"));
        /* 3. 장바구니 조회용 VO */
        List<MemberBasketVO> list = OrderValueGenerator.makeBasketQeury(orderInvoice, OrderInvoiceUtil.getMemberAttribute(request, "id"));
        if (list == null || list.size() == 0) {
            model.addAttribute("code",ResponseCode.SYS_2000.getCode());
            model.addAttribute("msg",ResponseCode.SYS_2000.getMessage());
            return "user:/payMent/orderInvoice";
        }
        log.info("MemberBasketVO : {}", list);
        /* 4. 회원 등급 */
        String memberType = OrderInvoiceUtil.getMemberType(request);
//        if (memberType == null) {
//            model.addAttribute("code",ResponseCode.SYS_2001.getCode());
//            model.addAttribute("msg",ResponseCode.SYS_2001.getMessage());
//            return "user:/payMent/orderInvoice";
//        }
        log.info("memberType : {}", memberType);
        /* 5. 장바구니 카운트 */
        int basketCount = orderInvoiceService.selectBasketCount(list, memberType + "P");
        if (basketCount == 0) {
            model.addAttribute("code",ResponseCode.SYS_2002.getCode());
            model.addAttribute("msg",ResponseCode.SYS_2002.getMessage());
            return "user:/payMent/orderInvoice";
        }
        log.info("basketCount : {}", basketCount);
        /* 6. 상품목록 Session에 담기 */
        List<OrderTicketVO> orderList = orderInvoiceService.listBasketGoods(list,memberType + "P");
        /* 7. 상품정보중 P_MENU 테이블에 정보가 없을 경우 결제 막기 */
        for (OrderTicketVO ticketVO : orderList) {
            List<TicketInformation> listGoods = null;
            if ("S".equals(ticketVO.getMuMpFlag())) {
                listGoods = orderInvoiceService.listUnitTicketGoods(ticketVO.getTicketSeq(), memberType + "P");
            } else if ("P".equals(ticketVO.getMuMpFlag())) {
                listGoods = orderInvoiceService.listPackageTicketGoods(ticketVO.getTicketSeq(), memberType + "P");
            }
            log.info("Good_Info : {}", listGoods);
            if (listGoods == null || listGoods.size() == 0) {
                model.addAttribute("code",ResponseCode.SYS_2002.getCode());
                model.addAttribute("msg",ResponseCode.SYS_2002.getMessage());
                return "user:/payMent/orderInvoice";
            }
        }

        int cardCount = 0;
        List<Map<String, Object>> listCard = orderInvoiceService.listSimpleCard(OrderInvoiceUtil.getMemberAttribute(request, "id"));
        if (listCard != null) {
            cardCount = listCard.size();
        }
        model.addAttribute("code",ResponseCode.SYS_0000.getCode());
        model.addAttribute("msg",ResponseCode.SYS_0000.getMessage());
        model.addAttribute("billYn",request.getParameter("billYn"));
        model.addAttribute("cardCount",cardCount);
        model.addAttribute("listCard",listCard);

        /* 세션에 저장 */
        if (orderSessionVO == null) {
            orderSessionVO = new OrderSessionVO();
        }
        orderSessionVO.setQuery(query);
        orderSessionVO.setOrderList(orderList);
        OrderInvoiceUtil.saveSessionForOrderInvoice(request, orderSessionVO);

        OrderInvoiceUtil.setModelForOrderInvoice(model, orderList);
        model.addAttribute("orderList", orderList);

        //결제창 세션 유지
        response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");
        return "user:/payMent/orderInvoice";
    }

    @RequestMapping("/listBillCard.do")
    public String listBillCard(HttpServletRequest request, Model model) throws Exception {
        int cardCount = 0;
        List<Map<String, Object>> listCard = null;
        /* 1. 로그인 체크  */
        if (PaymentUtil.isLogin(request)) {
            listCard = orderInvoiceService.listSimpleCard(OrderInvoiceUtil.getMemberAttribute(request, "id"));
            if (listCard != null) {
                cardCount = listCard.size();
            }
        }

        model.addAttribute("cardCount",cardCount);
        model.addAttribute("listCard",listCard);

        return "jsonView";
    }

    @RequestMapping("/billSignUp.do")
    public String billSignUp(HttpServletRequest request, @RequestParam Map<String, Object> map, Model model) throws Exception {
        String result = "SUCCESS";
        String msg = "SUCCESS";
        /* 1. 로그인 체크  */
        if (!PaymentUtil.isLogin(request)) {
            result = "NO_LOGIN";
            msg = "로그인해주세요.";
        }
        /* 2. 정상적인 경우 다음 로직 수행 */
        if ("SUCCESS".equals(result)) {
            orderInvoiceService.billSignUp(request, map, model);
        }

        model.addAttribute("code",result);
        model.addAttribute("msg",msg);

        return "jsonView";
    }

    @RequestMapping("/pay/billSignUpResponse.do")
    public String billSignUpResponse(HttpServletRequest request, Model model) throws Exception {
        String code = ResponseCode.SYS_0000.getCode();
        /* 1. 로그인 체크  */
        if (!PaymentUtil.isLogin(request)) {
//            return "redirect:/login.do";
            return "user:/payMent/moveLogin";
        }
        OrderSessionVO orderSessionVO = OrderInvoiceUtil.getSessionForOrderInvoice(request);
        List<OrderTicketVO> orderList = null;
        if (orderSessionVO == null || orderSessionVO.getOrderList() == null) {
            code = ResponseCode.SYS_2000.getCode();
            model.addAttribute("code", ResponseCode.SYS_2000.getCode());
            model.addAttribute("msg", ResponseCode.SYS_2000.getMessage());
        } else {
            orderList = orderSessionVO.getOrderList();
        }
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            code = orderInvoiceService.billSignUpResponse(request, model);
        }

        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            OrderInvoiceUtil.setModelForOrderInvoice(model, orderList);
            model.addAttribute("orderList", orderList);

            return "user:/payMent/moveInvoice";
        } else {

            return "user:/payMent/failBillSignUp";
        }
    }

    @RequestMapping("/openInvoicePop.do")
    public String openPop(HttpServletRequest request, @RequestParam Map<String, Object> map, Model model, HttpServletResponse response) throws Exception {
        String result = "SUCCESS";
        String msg = "SUCCESS";
        /* 1. 로그인 체크  */
        if (!PaymentUtil.isLogin(request)) {
            result = "NO_LOGIN";
            msg = "로그인해주세요.";
        }

        /* 2. Session에 주문할 상품 유효성 체크 */
        OrderSessionVO orderSessionVO = OrderInvoiceUtil.getSessionForOrderInvoice(request);
        List<OrderTicketVO> orderList = null;
        if ("SUCCESS".equals(result)) {
            if (orderSessionVO == null || orderSessionVO.getOrderList() == null) {
                result = "NO_ORDERS";
                msg = "주문정보가 없습니다.";
            } else {
                orderList = orderSessionVO.getOrderList();
            }
        }
        /* 3. 결제할 금액과 실제 상품금액 합이 같은지 체크 */
        if ("SUCCESS".equals(result)) {
            if (!OrderMapGenerator.checkOrderPopParameters(map)) {
                result = "NO_PARAM";
                msg = "잘못된 페이지 접근입니다.";
            }
        }
        if ("SUCCESS".equals(result)) {
            if (!OrderValidator.checkPayAmount(map, orderList)) {
                result = "NO_AMOUNT";
                msg = "주문금액 정보가 상이합니다.";
            }
        }
        /* 4. 정상적인 경우 다음 로직 수행 */
        if ("SUCCESS".equals(result)) {
            orderInvoiceService.saveShopOrder(request, map, model, orderList);
        }
        log.info("openInvoicePop Code : {}", result);
        log.info("openInvoicePop Message : {}", msg);

        model.addAttribute("code",result);
        model.addAttribute("msg",msg);

        return "jsonView";
    }

    @RequestMapping("/billPayment.do")
    public String billPayment(HttpServletRequest request, Model model) throws Exception {
        /* 0. 결제완료시 뒤로 가기를 눌러서 들어온 경우 처리 */
        OrderSessionVO orderSessionVO = OrderInvoiceUtil.getSessionForOrderInvoice(request);
        Map<String, Object> modelMap = null;
        if (orderSessionVO != null && orderSessionVO.getCompleteMap() != null && (orderSessionVO.getOrderList() == null || orderSessionVO.getQuery() == null)) {
            modelMap = orderSessionVO.getCompleteMap();
        }
        if (modelMap != null) {
            log.info("modelMap : {}", modelMap);
            OrderMapGenerator.makeCompleteMap(modelMap, model);
            return "user:/payMent/completeInvoice";
        }

        String code = ResponseCode.SYS_0000.getCode();
        /* 1. 로그인 체크  */
        if (!PaymentUtil.isLogin(request)) {
            code = ResponseCode.SYS_1000.getCode();
        }
        log.info("Session Check : {}", code);
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            code = orderInvoiceService.saveShopBillPayment(request, model);
        }
        log.info("Orderlist Check : {}", code);

        model.addAttribute("code", code);
        model.addAttribute("msg", ResponseCode.getMessage(code));

        return "user:/payMent/completeInvoice";
    }
    @PostMapping("/pay/completeInvoice.do")
    public String payResult(HttpServletRequest request, Model model, RedirectAttributes reAttr) throws Exception {
        /* 0. 결제완료시 뒤로 가기를 눌러서 들어온 경우 처리 */
        OrderSessionVO orderSessionVO = OrderInvoiceUtil.getSessionForOrderInvoice(request);
        Map<String, Object> modelMap = null;
        if (orderSessionVO != null && orderSessionVO.getCompleteMap() != null && (orderSessionVO.getOrderList() == null || orderSessionVO.getQuery() == null)) {
            modelMap = orderSessionVO.getCompleteMap();
        }

        log.info("completeInvoice.do modelMap : {}", modelMap);

        if (modelMap != null) {
            log.info("completeInvoice.do modelMap : {}", modelMap);
            //뒤로가기시 반응하는 페이지로 이동함.
            return "user:/payMent/moveTicket";
            //OrderMapGenerator.makeCompleteMap(modelMap, model);
            //return "user:/payMent/completeInvoice";
        }
        /* 1. 나이스 페이로 부터 받은 데이터 한글 수신을 위해서 인코딩(다음 순서 중요 */
        request.setCharacterEncoding("euc-kr");
        Map<String, Object> niceCertMap = OrderMapGenerator.makeRecvCertification(request);
        request.setCharacterEncoding("utf-8");

        /* 2. Session 체크 */
        String code = ResponseCode.SYS_0000.getCode();
        /* 1. 로그인 체크  */
        if (!PaymentUtil.isLogin(request)) {
            code = ResponseCode.SYS_1000.getCode();
        }
        log.info("Session Check : {}", code);
        /* 2. Session에 주문할 상품 유효성 체크 */
        List<OrderTicketVO> orderList = null;
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            if (orderSessionVO == null || orderSessionVO.getOrderList() == null || orderSessionVO.getOrderList().size() == 0) {
                code = ResponseCode.SYS_2000.getCode();
            } else {
                orderList = orderSessionVO.getOrderList();
            }
        }
        log.info("Orderlist Check : {}", code);

        /* 3. 결제 인증 결과 유효성 체크 */
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            code = OrderValidator.validateCertification(niceCertMap);
        }
        log.info("validateCertification Check : {}", code);
        log.info("niceCertMap : {}", niceCertMap);
        /* 4. 결제 인증 결과 성공에 결제 요청, 결제인증 실패처리, 망취소등 처리 */
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            code = orderInvoiceService.saveShopPayment(request, model);
        }
        /* 5. 지불내역 등록을 위한 SERTB_PG_FINAL_RESULT 조회 */
        if (code.equals(ResponseCode.SYS_0000.getCode())) {
            PaidVO res = paidService.selectPaid(niceCertMap.get("moid").toString());
            log.info("### res : {}", res);
            log.info("### model : {}", model);
            model.addAttribute("moid", niceCertMap.get("moid").toString());
            model.addAttribute("authCode", res.getAuthCode());
            model.addAttribute("encryptedCardNo", res.getCardNo());
            model.addAttribute("netAmt", res.getNetAmt());
            model.addAttribute("vatAmt", res.getVatAmt());
        }
        log.info("saveShopPayment Check : {}", code);
        model.addAttribute("code", code);
        model.addAttribute("msg", ResponseCode.getMessage(code));

        return "user:/payMent/completeInvoice";
    }

    @GetMapping("/failInvoice.do")
    public String payFail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        //결제완료 페이지에 세션 설정(안그러면 연속 결제시 가끔 세션 날라감)
        response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");
        return "user:/payMent/failInvoice";
    }

    @GetMapping("/successInvoice.do")
    public String paySuccess(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) auth.getPrincipal();

        CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
        model.addAttribute("url_scheme", app);
        //결제 티켓 내역 세팅
        model.addAttribute("paymentList", OrderInvoiceUtil.getSessionForTicketPayList(request));

        //결제완료 페이지에 세션 설정(안그러면 연속 결제시 가끔 세션 날라감)
        response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");
        return "user:/payMent/successInvoice";
    }


    @RequestMapping("/orderCancel.do")
    public String orderCancel(HttpServletRequest request, Model model) throws Exception {
        String code = orderInvoiceService.cancelPayment(request, model);
        model.addAttribute("resultCode", code);
        model.addAttribute("resultMsg", ResponseCode.getMessage(code));

        return "jsonView";
    }
}
