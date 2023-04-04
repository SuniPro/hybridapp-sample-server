package com.hybird.example.user.notice.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.main.service.MainService;
import com.hybird.example.user.notice.service.UserNoticeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hybird.example.user.notice.service.UserNoticeVO;

@Controller
@RequestMapping("/user/notice")
public class UserNoticeController extends CmmnController {

	@Resource(name = "UserNoticeService")
	private UserNoticeService userNoticeService;
	
	@Resource
	private MainService mainService;
	
	/***
	 * 공지 사항 page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/noticeView.do")
	public String noticeView(@ModelAttribute("UserNoticeVO") UserNoticeVO vo, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		model.addAttribute("mParam", vo);
		return "user:/notice/noticeView";
	}
	/***
	 * 공지사항 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectNoticeList.ajax")
	public String selectNoticeList(@ModelAttribute("UserNoticeVO") UserNoticeVO vo, Model model) throws Exception {
		model.addAttribute("result", userNoticeService.selectNoticeList(vo));
		model.addAttribute("pages", vo);
		return "jsonView";
	}
	
	/***
	 * 공지사항 팝업 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectNoticePopupList.ajax")
	public String selectNoticePopupList(@ModelAttribute("UserNoticeVO") UserNoticeVO vo, Model model,HttpServletRequest request) throws Exception {
		model.addAttribute("result", userNoticeService.selectNoticePopupList(vo));
		model.addAttribute("file", userNoticeService.selectNoticeFile(vo));
		model.addAttribute("root", request.getRequestURL().toString().replace(request.getRequestURI(),""));
		return "jsonView";
	}
	
	/***
	 * 공지 사항 상세 page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/noticeDetailView.do")
	public String noticeDetailView(@ModelAttribute("UserNoticeVO") UserNoticeVO vo, Model model) throws Exception {
		model.addAttribute("noticeSn", vo.getNoticeSn());
		model.addAttribute("mParam", vo);
		return "user:/notice/noticeDetail";
	}
	
	/***
	 * 공지사항 상세 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoticeView.ajax")
	public String selectNoticeView(@ModelAttribute("UserNoticeVO") UserNoticeVO vo, Model model,HttpServletRequest request) throws Exception {

		/* 상세 조회 */
		model.addAttribute("result", userNoticeService.searchNoticeDetail(vo));
		model.addAttribute("file", userNoticeService.selectNoticeFile(vo));
		
		model.addAttribute("root", request.getRequestURL().toString().replace(request.getRequestURI(),""));
		return "jsonView";
	}
	
	
}
