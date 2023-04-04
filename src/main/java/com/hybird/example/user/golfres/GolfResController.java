package com.hybird.example.user.golfres;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybird.example.cmmn.*;
import com.hybird.example.cmmn.resapi.service.ResApiService;
import com.hybird.example.main.service.MainService;
import com.plnc.cmmn.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/user/golf")
public class GolfResController extends CmmnController {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private static final String[] ENCRYPT_REQUEST = {"input","change", "mod", "refinput"};
	private static final String[] ENCRYPT_RESPONSE = {"info"};
	private static final String[] INS_RESPONSE = {"refable", "punish"};
	
	@Resource(name = "ResApiService")
    private ResApiService resApiService;
	
	@Resource
	private MainService mainService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/golfMain.do")
	public String golfMain(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/golf/golfMain";
	}
	
	@GetMapping("/golfInfo.do")
	public String golfInfo(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/golf/golfInfo";
	}
	
	@GetMapping("/golfCourse_m.do")
	public String golfCourseM(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/golf/golfCourseM";
	}
	
	@GetMapping("/golfCourse_L.do")
	public String golfCourseL(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/golf/golfCourseL";
	}
	
	@GetMapping("/golfRes.do")
	public String golfRes(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
			return "redirect:/login.do";
		}
		return "user:/golf/golfRes";
	}
	
	@PostMapping("/golfResReg.do")
	public String golfResReg(@RequestParam HashMap<String, Object> param, Model model, HttpServletRequest request) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
	           return "redirect:/login.do";
		}
		model.addAttribute("query", param);
		return "user:/golf/golfResReg";
	}
	
	@GetMapping("/golfResList.do")
	public String golfResList(HttpServletRequest request, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
	           return "redirect:/login.do";
		}
		return "user:/golf/golfResList";
	}
	
	@PostMapping("/golfResMod.do")
	public String golfResMod(@RequestParam HashMap<String, Object> param, Model model, HttpServletRequest request) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
	           return "redirect:/login.do";
		}
		model.addAttribute("query", param);
		return "user:/golf/golfResMod";
	}
	
	@RequestMapping(value = "/api/{svc}.ajax")
	public String callGolfRes(@PathVariable("svc") String svc, @RequestParam Map<String, Object> param, Model model, HttpServletRequest request) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
        	model.addAttribute("isLogin", false);
        	return "jsonView";
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

			ResponseEntity<String> response = restTemplate.postForEntity(GolfResValueGenerator.getUrl(svc), entity, String.class);

			Map<String, Object> log = new HashMap<String, Object>();
			if (request != null) {
				log.put("isrtIp", PaymentUtil.getClientIP(request));
			}
			log.put("svcType", "golfres");
			log.put("svcCode", svc);
			log.put("svcUrl", GolfResValueGenerator.getUrl(svc));

			Map<String, Object> reqParam = multiMap.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

			if (Arrays.asList(ENCRYPT_REQUEST).contains(svc)) {
				reqParam.forEach((k, v) -> {
					if (k.equals("hand_tel2") || k.equals("ref_tel2")) reqParam.put(k, "****");
				});
	    	}
	    	
	    	log.put("requestParam", reqParam.toString());
	    	log.put("httpStatusCode", response.getStatusCode().toString());
	        if(response.getStatusCode() == HttpStatus.OK) {
	        	root = mapper.readTree(response.getBody());
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
		        logger.error("golf rest api HttpStatus " + response.getStatusCode().toString());
		        root = mapper.readTree("{\"code\": \"9999\", \"msg\": \"System Failed!!!\"}");
	        }
			logger.debug("response.getBody : " + response.getBody());
			if (request != null) {
				resApiService.insertResApi(log);
			}
		} catch (Exception e) {
			logger.error("golf rest api postForEntity", e);
			root = mapper.readTree("{\"code\": \"9999\", \"msg\": \"System Error!!!\"}");
		}
		return root;
	}
	
	public MultiValueMap<String, Object> setMultiValueMap(String svc, Map<String, Object> param) {
		MultiValueMap<String, Object> multiMap= new LinkedMultiValueMap<>();
		multiMap.add("resno", SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "resno"));
		switch (svc) {
		case "meminfo":
			
			break;
		case "calendar":
			multiMap.add("point_dt", param.get("point_dt").toString());
			break;
		case "timelist":
			multiMap.add("point_dt", param.get("point_dt").toString());
			multiMap.add("course_id", param.get("course_id").toString());
			break;
		case "punish":
			
			break;
		case "blacklist":
			
			break;
		case "input":
			multiMap.add("point_dt", param.get("point_dt").toString());
			multiMap.add("course_id", param.get("course_id").toString());
			multiMap.add("choice_time", param.get("choice_time").toString());
			multiMap.add("hand_tel1", param.get("hand_tel1").toString());
			multiMap.add("hand_tel2", param.get("hand_tel2").toString());
			multiMap.add("hand_tel3", param.get("hand_tel3").toString());
			multiMap.add("res_div", param.get("res_div").toString());
			multiMap.add("ref_name", param.get("ref_name").toString());
			multiMap.add("ref_tel1", param.get("ref_tel1").toString());
			multiMap.add("ref_tel2", param.get("ref_tel2").toString());
			multiMap.add("ref_tel3", param.get("ref_tel3").toString());
			break;
		case "info":
			multiMap.add("point_dt", param.get("point_dt").toString());
			multiMap.add("course_id", param.get("course_id").toString());
			multiMap.add("choice_time", param.get("choice_time").toString());
			break;
		case "list":
			multiMap.add("start_dt", param.get("start_dt").toString());
			multiMap.add("end_dt", param.get("end_dt").toString());
			multiMap.add("bookg_gubun", param.get("bookg_gubun").toString());
			break;
		case "mod":
			multiMap.add("point_dt", param.get("point_dt").toString());
			multiMap.add("course_id", param.get("course_id").toString());
			multiMap.add("choice_time", param.get("choice_time").toString());
			multiMap.add("hand_tel1", param.get("hand_tel1").toString());
			multiMap.add("hand_tel2", param.get("hand_tel2").toString());
			multiMap.add("hand_tel3", param.get("hand_tel3").toString());
			break;
		case "change":
			multiMap.add("point_dt", param.get("point_dt").toString());
			multiMap.add("hand_tel1", param.get("hand_tel1").toString());
			multiMap.add("hand_tel2", param.get("hand_tel2").toString());
			multiMap.add("hand_tel3", param.get("hand_tel3").toString());
			multiMap.add("oldpoint_id", param.get("oldpoint_id").toString());
			multiMap.add("oldpoint_time", param.get("oldpoint_time").toString());
			multiMap.add("course_id", param.get("course_id").toString());
			multiMap.add("choice_time", param.get("choice_time").toString());
			break;
		case "cancel":
			multiMap.add("point_dt", param.get("point_dt").toString());
			multiMap.add("course_id", param.get("course_id").toString());
			multiMap.add("choice_time", param.get("choice_time").toString());
			break;
		case "refable":
			multiMap.add("point_dt", param.get("point_dt").toString());
			break;
		case "refinput":
			multiMap.add("point_dt", param.get("point_dt").toString());
			multiMap.add("course_id", param.get("course_id").toString());
			multiMap.add("choice_time", param.get("choice_time").toString());
			multiMap.add("res_div", param.get("res_div").toString());
			multiMap.add("ref_name", param.get("ref_name").toString());
			multiMap.add("ref_tel1", param.get("ref_tel1").toString());
			multiMap.add("ref_tel2", param.get("ref_tel2").toString());
			multiMap.add("ref_tel3", param.get("ref_tel3").toString());
			break;
		case "refcancel":
			multiMap.add("point_dt", param.get("point_dt").toString());
			multiMap.add("course_id", param.get("course_id").toString());
			multiMap.add("choice_time", param.get("choice_time").toString());
			multiMap.add("res_div", param.get("res_div").toString());
			break;
		default:
			break;
		}
		return multiMap;
	}

}
