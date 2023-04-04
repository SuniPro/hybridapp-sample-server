package com.hybird.example.user.condores;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hybird.example.cmmn.resapi.service.ResApiService;
import com.hybird.example.main.service.MainService;
import com.hybird.example.user.payMent.service.CondoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.cmmn.PaymentUtil;
import com.hybird.example.cmmn.SessionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user/condo")
public class CondoResController extends CmmnController {

	private final Log logger = LogFactory.getLog(getClass());

	private static final String[] ENCRYPT_REQUEST = {"input","change"};
	private static final String[] ENCRYPT_RESPONSE = {"info"};
	private static final String[] INS_RESPONSE = {"roomamt", "punish", "punish_point", "condoroomamt"};

	@Resource(name = "ResApiService")
    private ResApiService resApiService;

	@Resource
	private MainService mainService;

	@Resource
	private CondoService condoService;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/moveCondoRes.do")
	public String moveCondoRes() {
		return "user:/condo/moveCondoRes";
	}

	@GetMapping("/condoMain.do")
	public String condoMain(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/condo/condoMain";
	}

	@GetMapping("/condoInfo.do")
	public String condoInfo(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/condo/condoInfo";
	}

	@GetMapping("/bellelagoRoom.do")
	public String bellelagoRoom(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/condo/bellelagoRoom";
	}

	@GetMapping("/bellelagoRoomDetail.do")
	public String bellelagoRoomDetail(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/condo/bellelagoRoomDetail";
	}

	@GetMapping("/petforetRoom.do")
	public String petforetRoom(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/condo/petforetRoom";
	}

	@GetMapping("/petforetRoomDetail.do")
	public String petforetRoomDetail(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/condo/petforetRoomDetail";
	}

	@GetMapping("/petforetFacility.do")
	public String petforetFacility(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/condo/petforetFacility";
	}

	@GetMapping("/condoRes.do")
	public String condoRes(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
	           return "redirect:/login.do";
		}
		return "user:/condo/condoRes";
	}

	@PostMapping("/condoResReg.do")
	public String condoResReg(@RequestParam HashMap<String, Object> param, Model model, HttpServletRequest request) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
	           return "redirect:/login.do";
		}
		model.addAttribute("query", param);
		return "user:/condo/condoResReg";
	}

	@GetMapping("/condoResList.do")
	public String condoResList(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
	           return "redirect:/login.do";
		}
		return "user:/condo/condoResList";
	}

	@GetMapping("/condoResPayment.do")
	public String condoResPayment(@RequestParam HashMap<String, Object> param, HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
			return "redirect:/login.do";
		}
		model.addAttribute("param", param);

		//결제창 세션 유지
		response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");
		return "user:/condo/condoResPayment";
	}

	@GetMapping("/condoResPayFail.do")
	public String condoResPayFail(HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		//결제완료 페이지에 무조건 설정(안그러면 가끔씩 세션 날라감)
		response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");
		return "user:/condo/condoResPayFail";
	}

	@GetMapping("/condoResPayComplete.do")
	public String condoResPayComplete(HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		//결제완료 페이지에 무조건 설정(세션 소멸 문제 이유)
		response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/; Secure; SameSite=None");
		return "user:/condo/condoResPayComplete";
	}

	@RequestMapping(value = "/api/{svc}.ajax")
	public String callCondoRes(@PathVariable("svc") String svc, @RequestParam Map<String, Object> param, Model model, HttpServletRequest request) throws Exception {
		log.info("------ svc : " + svc + " / " + param);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String userId = "";
		if("input".equals(svc) || "condoroomamt".equals(svc)) {
			/* 콘도 예약 후 대원 api 호출 시 security 예외처리, context-security.xml에서 예외처리 추가 */
			userId = param.get("userId").toString();
		}else {
			userId = (String) auth.getPrincipal();
		}

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if(!"input".equals(svc) && !"meminput".equals(svc) && !"condoroomamt".equals(svc)) {	/* 콘도 예약 후 대원 api 호출 시 security 예외처리 */
			if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(userId)) {
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
			logger.info("entity : " + entity);

			ResponseEntity<String> response = restTemplate.postForEntity(CondoResValueGenerator.getUrl(svc), entity, String.class);

			Map<String, Object> log = new HashMap<String, Object>();
			log.put("isrtIp", PaymentUtil.getClientIP(request));
			log.put("svcType", "condores");
			log.put("svcCode", svc);
			log.put("svcUrl", CondoResValueGenerator.getUrl(svc));

			Map<String, Object> reqParam = multiMap.entrySet()
	    			.stream()
	    			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

	    	if(Arrays.asList(ENCRYPT_REQUEST).contains(svc)) {
	    		reqParam.forEach((k,v) ->{if (k.equals("hand_tel2") || k.equals("ghand_tel2")) reqParam.put(k, "****");});
	    	}

	    	log.put("requestParam", reqParam.toString());
			log.put("httpStatusCode", response.getStatusCode().toString());
			if("meminput".equals(svc)) {
				log.put("user_id", reqParam.get("user_id").toString());
			}
			if(response.getStatusCode() == HttpStatus.OK) {
				root = mapper.readTree(response.getBody());
				logger.info("root : "+ root);
				log.put("responseCode", root.get("code").asText());
				log.put("responseMsg", root.get("msg").asText());
				if(Arrays.asList(INS_RESPONSE).contains(svc)) {
					log.put("insResponse", "Y");
					log.put("responseData", mapper.convertValue(root, new TypeReference<Map<String, Object>>(){}).toString());
					if(Arrays.asList(ENCRYPT_RESPONSE).contains(svc)) {
						// TODO 필요 시 마스킹 처리
					}
				} else {
					log.put("insResponse", "N");
				}
			} else {
				logger.error("condo rest api HttpStatus " + response.getStatusCode().toString());
				root = mapper.readTree("{\"code\": \"9999\", \"msg\": \"System Failed!!!\"}");
			}
			logger.debug("response.getBody : " + response.getBody());
			resApiService.insertResApi(log);
		} catch (Exception e) {
			logger.error("condo rest api postForEntity", e);
			root = mapper.readTree("{\"code\": \"9999\", \"msg\": \"System Error!!!\"}");
		}
		return root;
	}

	public MultiValueMap<String, Object> setMultiValueMap(String svc, Map<String, Object> param) throws Exception {
		MultiValueMap<String, Object> multiMap= new LinkedMultiValueMap<>();
		if(!"meminput".equals(svc)) {
			multiMap.add("resno", SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "resno"));
		}
		switch (svc) {
			case "meminfo":

				break;
			case "meminput":
				//암호화 데이터 조회
				Map<String, Object> encryptedData = condoService.selectEncryptedData(param.get("resno").toString());
				multiMap.add("user_id", param.get("user_id").toString());
				multiMap.add("resno", param.get("resno").toString());
				multiMap.add("password", encryptedData.get("PASSWORD").toString());
				multiMap.add("member_name", param.get("member_name").toString());
				multiMap.add("eng_name", param.get("eng_name").toString());
				multiMap.add("chn_name", param.get("chn_name").toString());
				multiMap.add("entry_cd", param.get("entry_cd").toString());
				multiMap.add("member_cd", param.get("member_cd").toString());
				multiMap.add("member_no", param.get("member_no").toString());
				multiMap.add("sex_cd", param.get("sex_cd").toString());
				multiMap.add("birth_day", param.get("birth_day").toString());
				multiMap.add("lunar_cd", param.get("lunar_cd").toString());
				multiMap.add("home_zip_cd", param.get("home_zip_cd").toString());
				multiMap.add("home_addr1", param.get("home_addr1").toString());
				multiMap.add("home_addr2", param.get("home_addr2").toString());
				multiMap.add("home_tel1", param.get("home_tel1").toString());
				multiMap.add("home_tel2", param.get("home_tel2").toString());
				multiMap.add("home_tel3", param.get("home_tel3").toString());
				multiMap.add("post_yn", param.get("post_yn").toString());
				multiMap.add("handphone1", param.get("handphone1").toString());
				multiMap.add("handphone2", encryptedData.get("HANDPHONE2").toString());
				multiMap.add("handphone3", param.get("handphone3").toString());
				multiMap.add("sms_yn", param.get("sms_yn").toString());
				multiMap.add("email", encryptedData.get("EMAIL").toString());
				multiMap.add("email_yn", param.get("email_yn").toString());
				break;
			case "memout":
				Date todate = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(todate);

				multiMap.add("user_id", param.get("user_id").toString());
				multiMap.add("out_dt", date.toString());
				multiMap.add("out_gubun", param.get("out_gubun").toString());
				break;
			case "calendar":
				multiMap.add("point_dt", param.get("point_dt").toString());
				break;
			case "roomlist":
				multiMap.add("point_dt", param.get("point_dt").toString());
				break;
			case "roominfo":
				multiMap.add("point_dt", param.get("point_dt").toString());
				multiMap.add("room_cd", param.get("room_cd").toString());
				break;
			case "constinfo":
				multiMap.add("point_dt", param.get("point_dt").toString());
				multiMap.add("room_cd", param.get("room_cd").toString());
				break;
			case "punish":

				break;
			case "punish_point":
				multiMap.add("start_dt", param.get("start_dt").toString());
				break;
			case "roomamt": case "condoroomamt":
				multiMap.add("room_cd", param.get("room_cd").toString());
				multiMap.add("start_dt", param.get("start_dt").toString());
				multiMap.add("end_dt", param.get("end_dt").toString());
				break;
			case "input":	/* 콘도 예약등록 */
				log.info("setMultiValueMap.input params : " + param);
				multiMap.add("room_cd", param.get("room_cd").toString());
				multiMap.add("start_dt", param.get("start_dt").toString());
				multiMap.add("end_dt", param.get("end_dt").toString());
				multiMap.add("few_day", param.get("few_day").toString());
				multiMap.add("adult_num", param.get("adult_num").toString());
				multiMap.add("child_num", param.get("child_num").toString());
				multiMap.add("member_nm", param.get("member_nm").toString());
				multiMap.add("hand_tel1", param.get("hand_tel1").toString());
				multiMap.add("hand_tel2", param.get("hand_tel2").toString());
				multiMap.add("hand_tel3", param.get("hand_tel3").toString());
				multiMap.add("guest_nm", param.get("guest_nm").toString());
				multiMap.add("ghand_tel1", param.get("ghand_tel1").toString());
				multiMap.add("ghand_tel2", param.get("ghand_tel2").toString());
				multiMap.add("ghand_tel3", param.get("ghand_tel3").toString());
				multiMap.add("pet_add", param.get("pet_add").toString());
				break;
			case "payment":
				multiMap.add("room_cd", param.get("room_cd").toString());
				multiMap.add("start_dt", param.get("start_dt").toString());
				multiMap.add("end_dt", param.get("end_dt").toString());
				multiMap.add("few_day", param.get("few_day").toString());
				multiMap.add("adult_num", param.get("adult_num").toString());
				multiMap.add("member_nm", param.get("member_nm").toString());
				multiMap.add("hand_tel1", param.get("hand_tel1").toString());
				multiMap.add("hand_tel2", param.get("hand_tel2").toString());
				multiMap.add("hand_tel3", param.get("hand_tel3").toString());
				multiMap.add("guest_nm", param.get("guest_nm").toString());
				multiMap.add("ghand_tel1", param.get("ghand_tel1").toString());
				multiMap.add("ghand_tel2", param.get("ghand_tel2").toString());
				multiMap.add("ghand_tel3", param.get("ghand_tel3").toString());
				multiMap.add("pet_add", param.get("pet_add").toString());
				multiMap.add("remark_tx", param.get("remark_tx").toString());
				break;
			case "list":
				multiMap.add("start_dt", param.get("start_dt").toString());
				multiMap.add("end_dt", param.get("end_dt").toString());
				multiMap.add("bookg_gubun", param.get("bookg_gubun").toString());
				break;
			case "info":
				multiMap.add("bookg_no", param.get("bookg_no").toString());
				multiMap.add("bookg_seq", param.get("bookg_seq").toString());
				break;
			case "cancel":
				multiMap.add("bookg_no", param.get("bookg_no").toString());
				multiMap.add("bookg_seq", param.get("bookg_seq").toString());
				multiMap.add("paid_yn", param.get("paid_yn").toString());
				multiMap.add("use_punish_yn", param.get("use_punish_yn").toString());
				break;
			case "change":
				multiMap.add("bookg_no", param.get("bookg_no").toString());
				multiMap.add("bookg_seq", param.get("bookg_seq").toString());
				multiMap.add("guest_nm", param.get("guest_nm").toString());
				multiMap.add("ghand_tel1", param.get("ghand_tel1").toString());
				multiMap.add("ghand_tel2", param.get("ghand_tel2").toString());
				multiMap.add("ghand_tel3", param.get("ghand_tel3").toString());
				break;
			default:
				break;
		}
		return multiMap;
	}

}
