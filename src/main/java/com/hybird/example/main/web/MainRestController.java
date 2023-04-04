package com.hybird.example.main.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybird.example.main.service.VersionCheckDto;
import com.hybird.example.cmmn.DateUtil;
import com.hybird.example.cmmn.JsonUtil;
import com.hybird.example.cmmn.PaymentUtil;
import com.hybird.example.main.service.MainService;
import com.hybird.example.main.service.PurchaseDto;
import com.hybird.example.user.golfres.GolfResController;
import com.hybird.example.user.payMent.service.CondoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin
public class MainRestController {

	@Resource
	private MainService mainService;

	@Resource
	private CondoService condoService;

	@Resource
	private GolfResController golfResController;

	/**
	 * app 버전 체크
	 *
	 * @param deviceType
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/update_check/{device_type}.ajax")
	public VersionCheckDto versionCheck(@PathVariable("device_type") String deviceType) throws Exception {
		log.info("deviceType = " + deviceType);
		Map<String, Object> versionData = mainService.selectAppVersion(deviceType); //버전
		log.info("versionData : {} ", versionData);

		Boolean forceUpdate = false;
		//Y = true, N = false
		if(versionData.get("FORCEUPDATE").toString().equals("Y")) {
			forceUpdate = true;
		}

		return VersionCheckDto.builder()
				.device(deviceType)
				.version(versionData.get("VERSION").toString())
				.forceUpdate(forceUpdate)
				.build();
	}

	@GetMapping(value = "/user/{user_id}/{res_no}/menuCount.ajax")
	public String menuCount(@PathVariable("user_id") String userId, @PathVariable("res_no") String resNo) throws Exception {
		Map<String, Integer> map = new HashMap<>();
		log.info("userId = " + userId);
		log.info("resNo = " + resNo);
		map.put("myTicketCnt", mainService.myTicketCnt(userId));
		map.put("myCondoCnt", condoService.myCondoCnt(userId));
		map.put("myGolfCnt",myGolfCnt(resNo));
		map.put("giftCnt",mainService.giftCnt(userId));
		log.info(map.toString());

		return JsonUtil.mapToJsonInt(map);
	}

	/**
	 * 장바구니 담기 및 이동
	 */
	@GetMapping(value = "/purchase.ajax")
	public String purchase(HttpServletRequest request, @RequestParam HashMap<String, Object> map) {

		if (!PaymentUtil.isLogin(request)) {
			return "{\"result\": \"reqlogin\"}";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String json = new String(Base64.getDecoder().decode((String) map.get("query")));

		try {
			ObjectMapper mapper = new ObjectMapper();
			PurchaseDto req = mapper.readValue(json, new TypeReference<PurchaseDto>() {
			});

			// 회원아이디 추출
			log.info(">>> purchase.ajax / login user id = " + auth.getPrincipal());
			req.setMid((String) auth.getPrincipal());
			List<Object> obj = (List<Object>) req.getData();
			if (req.getSend().equals("basket")) {
				this.mainService.basketProd(req, obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//TODO result vo로 개선
			return "{\"result\": \"fail\"}";
		}
		//TODO result vo로 개선
		return "{\"result\": \"success\"}";
	}

	public int myGolfCnt(String resNo) throws Exception {
		String svc = "list";
		Map<String, Object> param = new HashMap<>();
		Date startDt = new Date();
		Calendar endDt = Calendar.getInstance();
		endDt.setTime(startDt);
		endDt.add(Calendar.MONTH, 3);
		param.put("start_dt", DateUtil.dateToString(startDt, "yyyyMMdd"));
		param.put("end_dt", DateUtil.dateToString(endDt.getTime(), "yyyyMMdd"));
		param.put("bookg_gubun", "");
		MultiValueMap<String, Object> multiMap = new LinkedMultiValueMap<>();
		multiMap.add("resno", resNo);
		multiMap.add("start_dt", param.get("start_dt").toString());
		multiMap.add("end_dt", param.get("end_dt").toString());
		multiMap.add("bookg_gubun", param.get("bookg_gubun").toString());
		JsonNode response = golfResController.postForEntity(svc, multiMap, null);
		JsonNode data = response.get("data");
		return data.size();
	}

}