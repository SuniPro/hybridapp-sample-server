package com.hybird.example.user.faq.web;

import javax.annotation.Resource;

import com.hybird.example.user.faq.service.UserFaqService;
import com.hybird.example.user.faq.service.UserFaqVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.main.service.MainService;

@Controller
@RequestMapping("/user/faq")
public class UserFaqController extends CmmnController{
	
	
	
	@Resource(name = "UserFaqService")
	private UserFaqService userFaqService;
	
	@Resource
	private MainService mainService;
	
	/***
	 * FAQ
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userFaqView.do")
	public String userFaqView(@ModelAttribute("UserFaqVO") UserFaqVO vo, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		model.addAttribute("mParam", vo);
		
		return "user:/faq/faqView";
	}
	
	/***
	 * FAQ 목록 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchUserFaqList.ajax")
	public String searchUserFaqList(@ModelAttribute("UserFaqVO") UserFaqVO vo, Model model) throws Exception {
		
		/* 목록 조회 */
		model.addAttribute("result", userFaqService.searchUserFaqList(vo));
		
		model.addAttribute("code", userFaqService.searchUserFaqClCd(vo));
		
		return "jsonView";
	}
	
}
