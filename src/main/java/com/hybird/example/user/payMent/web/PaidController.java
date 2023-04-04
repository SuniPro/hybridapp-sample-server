package com.hybird.example.user.payMent.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.PaymentUtil;
import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.cmmn.resapi.service.ResApiService;
import com.hybird.example.main.service.MainService;
import com.hybird.example.user.payMent.service.PaidService;
import com.hybird.example.user.payMent.service.PaidVO;
import com.hybird.example.user.payMent.util.PaidValueGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/user/paid")
public class PaidController {

    private final Log logger = LogFactory.getLog(getClass());

    private static final String[] ENCRYPT_REQUEST = {"input","cancel", "condocancel"};
    private static final String[] INS_RESPONSE = {"input", "cancel", "condocancel"};

    @Resource(name = "ResApiService")
    private ResApiService resApiService;

    @Resource
    private MainService mainService;

    @Resource
    private PaidService paidService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/paid_update.do")
    public String callPrCodeUpdate(HttpServletRequest request, Model model) throws Exception {
        // 선수금 접수번호 업데이트
        Map<String, Object> map = new HashMap<>();
        map.put("prCode", request.getParameter("prCode"));
        map.put("orderNo", request.getParameter("orderNo"));

        int res = paidService.updatePaidRef(map);
        log.info("### updete res : {}", res);

        return "jsonView";
    }
    @PostMapping(value = "/paid_select.do")
    public String callPrCodeSelect(HttpServletRequest request, Model model) throws Exception {
        // 선수금 접수번호 조회
        PaidVO res = paidService.selectPaid(request.getParameter("orderNo"));
        log.info("### select res : {}", res);
        model.addAttribute("res", res);

        return "jsonView";
    }

    @PostMapping(value = "/api/{svc}.ajax")
    public String callPaidRes(@PathVariable("svc") String svc, @RequestParam Map<String, Object> param, Model model, HttpServletRequest request) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("### param : {} ###", param);

        String userId = "";
        if("input".equals(svc) || "condocancel".equals(svc)) {
            /* 대원 api 호출 시 security 예외처리, context-security.xml에서 예외처리 추가 */
            userId = param.get("userId").toString();
        }else {
            userId = (String) auth.getPrincipal();
        }

        CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
        model.addAttribute("url_scheme", app);
        if(!"input".equals(svc) && !"condocancel".equals(svc)) {
            /* 대원 api 호출 시 security 예외처리 */
            if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
                model.addAttribute("isLogin", false);
                return "jsonView";
            }
        }

        model.addAttribute("response", postForEntity(svc, setMultiValueMap(svc, param), request));
        model.addAttribute("isLogin", true);
        return "jsonView";
    }

    public JsonNode postForEntity(String svc, MultiValueMap<String, Object> multiMap, HttpServletRequest request) throws Exception {
        JsonNode root = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(multiMap, headers);

            logger.info("entity : {}" + entity);
            ResponseEntity<String> response = restTemplate.postForEntity(PaidValueGenerator.getUrl(svc), entity, String.class);

            Map<String, Object> log = new HashMap<String, Object>();
            log.put("isrtIp", PaymentUtil.getClientIP(request));
            log.put("svcType", "paid");
            log.put("svcCode", svc);
            log.put("svcUrl", PaidValueGenerator.getUrl(svc));

            Map<String, Object> reqParam = multiMap.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

            /*if(Arrays.asList(ENCRYPT_REQUEST).contains(svc)) {
                reqParam.forEach((k,v) ->{if (k.equals("handphone2")) reqParam.put(k, "****");});
            }*/

            log.put("requestParam", reqParam.toString());
            log.put("httpStatusCode", response.getStatusCode().toString());
            if(response.getStatusCode() == HttpStatus.OK) {
                root = mapper.readTree(response.getBody());
                log.put("responseCode", root.get("code").asText());
                log.put("responseMsg", root.get("msg").asText());
                if(Arrays.asList(INS_RESPONSE).contains(svc)) {
                    log.put("insResponse", "Y");
                    log.put("responseData", mapper.convertValue(root, new TypeReference<Map<String, Object>>(){}).toString());
                } else {
                    log.put("insResponse", "N");
                }
            } else {
                logger.error("paid rest api HttpStatus " + response.getStatusCode().toString());
                root = mapper.readTree("{\"code\": \"9999\", \"msg\": \"System Failed!!!\"}");
            }
            logger.debug("response.getBody : " + response.getBody());

            resApiService.insertResApi(log);
        } catch (Exception e) {
            logger.error("paid rest api postForEntity", e);
            root = mapper.readTree("{\"code\": \"9999\", \"msg\": \"System Error!!!\"}");
        }
        return root;
    }

    public MultiValueMap<String, Object> setMultiValueMap(String svc, Map<String, Object> param) throws Exception {
        MultiValueMap<String, Object> multiMap = new LinkedMultiValueMap<>();
        multiMap.add("resno", SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "resno"));
        //암호화된 휴대폰 번호 회원가입에서 조회
        String handPhone2 = paidService.selectUserHandPhoneTwo(SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "resno"));
        log.info("### handphone2 : {}", handPhone2);
        switch (svc) {
            case "input":
                multiMap.add("member_name", param.get("member_name").toString());
                multiMap.add("handphone1", param.get("handphone1").toString());
                multiMap.add("handphone2", handPhone2);
                multiMap.add("handphone3", param.get("handphone3").toString());
                multiMap.add("pramt_kind", param.get("pramt_kind").toString());
                multiMap.add("pur_no", param.get("pur_no").toString());
                multiMap.add("approve_no", param.get("approve_no").toString());
                multiMap.add("divide_term", param.get("divide_term").toString());
                multiMap.add("paid_dt", param.get("paid_dt").toString());
                multiMap.add("card_no", param.get("card_no").toString());
                multiMap.add("purc_name", param.get("purc_name").toString());
                multiMap.add("card_amt", param.get("card_amt").toString());
                multiMap.add("net_amt", param.get("net_amt").toString());
                multiMap.add("vat_amt", param.get("vat_amt").toString());
                multiMap.add("bookgNo", param.get("bookgNo").toString());
                break;
            case "cancel": case "condocancel":
                multiMap.add("member_name", param.get("member_name").toString());
                multiMap.add("pur_no", param.get("pur_no").toString());
                multiMap.add("approve_no", param.get("approve_no").toString());
                multiMap.add("pr_code", param.get("pr_code").toString());
                multiMap.add("cancel_dt", param.get("cancel_dt").toString());
                multiMap.add("cancel_amt", param.get("cancel_amt").toString());
                multiMap.add("card_no", param.get("card_no").toString());
                multiMap.add("purc_name", param.get("purc_name").toString());
                multiMap.add("bookgNo", param.get("bookgNo").toString());
                multiMap.add("used_kb", param.get("used_kb").toString());
                break;
            default:
                log.info("??? params : " + param);
                break;
        }
        return multiMap;
    }
}
