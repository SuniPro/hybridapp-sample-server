package com.hybird.example.user.event.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.main.service.MainService;
import com.hybird.example.user.event.service.UserEventService;
import com.hybird.example.user.event.service.UserEventVO;

@Controller
@RequestMapping("/user/event")
public class UserEventController extends CmmnController {

	@Resource(name = "UserEventService")
	private UserEventService  userEventService;
	
	@Resource
	private MainService mainService;
	
	/***
	 * 이벤트 page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventView.do")
	public String noticeView(@ModelAttribute("UserEventVO") UserEventVO vo, Model model) throws Exception {
		model.addAttribute("mParam", vo);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/event/eventView";
	}
	
	/***
	 * 이벤트 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectEventList.ajax")
	public String selectEventList(@ModelAttribute("UserEventVO") UserEventVO vo, Model model) throws Exception {
		model.addAttribute("result", userEventService.selectEventList(vo));
		model.addAttribute("pages", vo);
		return "jsonView";
	}
	
	/***
	 * 이벤트 팝업 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectEventPopupList.ajax")
	public String selectEventPopupList(@ModelAttribute("UserEventVO") UserEventVO vo, Model model,HttpServletRequest request) throws Exception {
		model.addAttribute("result", userEventService.selectEventPopupList(vo));
		model.addAttribute("file", userEventService.selectEventFile(vo));
		model.addAttribute("root", request.getRequestURL().toString().replace(request.getRequestURI(),""));
		
		return "jsonView";
	}
	
	/***
	 * 이벤트 상세 page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventDetailView.do")
	public String eventDetailView(@ModelAttribute("UserEventVO") UserEventVO vo, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		model.addAttribute("evntSn", vo.getEvntSn());
		model.addAttribute("mParam", vo);
		return "user:/event/eventDetail";
	}
	
	/***
	 * 이벤트 상세 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectEventView.ajax")
	public String selectEventView(@ModelAttribute("UserEventVO") UserEventVO vo, Model model,HttpServletRequest request) throws Exception {

		/* 상세 조회 */
		model.addAttribute("result", userEventService.searchEventDetail(vo));
		model.addAttribute("file", userEventService.selectEventFile(vo));
		model.addAttribute("root", request.getRequestURL().toString().replace(request.getRequestURI(),""));
		return "jsonView";
	}
	
	
}
