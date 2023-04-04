package com.hybird.example.user.gift.web;

import com.hybird.example.user.gift.service.GiftService;
import com.hybird.example.user.gift.service.GiftVO;
import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.user.payMent.util.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user/gift")
public class GiftController extends CmmnController {

	@Resource(name = "GiftService")
	private GiftService giftService;

	// 선물함 조회
	@RequestMapping("/giftBox.do")
	public ModelAndView giftBox(@RequestParam Map<String, String> param) throws Exception {
		String seUserId = SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "id");

		List<GiftVO> list = giftService.listGift(seUserId, param.get("start_dt"), param.get("end_dt"));
		log.info("list : {}", list);
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("user:/gift/giftBox");
		return mav;
	}

	@RequestMapping("/giftTest.ajax")
	public String giftTest(HttpServletRequest request, Model model) throws Exception {
		String seUserId = SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "id");

		String code = "0000";
		model.addAttribute("seUserId", seUserId);
		model.addAttribute("resultCode", code);
		model.addAttribute("resultMsg", ResponseCode.getMessage(code));

		return "jsonView";
	}

	//선물 보내기
	@RequestMapping("/giftSend.ajax")
	public String giftSend(HttpServletRequest request, Model model) throws Exception {
		String userId = SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "id");
		String code = "0000";

		//선물함 DB insert

		//마이티켓관련 DB에 선물한 티켓으로 업데이트

		//알림톡 발송을 위한 데이터 내려주기
//		String phone = request.getAttribute("receivePhoneNum1").toString() + "-" + request.getAttribute("receivePhoneNum2").toString() + "-" + request.getAttribute("receivePhoneNum3").toString();
//		model.addAttribute("kName", request.getAttribute("receiveUserName").toString());
//		model.addAttribute("phone", phone);
		model.addAttribute("resultCode", code);

		return "jsonView";
	}
}
