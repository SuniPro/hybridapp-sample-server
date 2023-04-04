package com.hybird.example.user.intro.web;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.main.service.MainService;

@Controller
@RequestMapping("/user")
public class IntroController extends CmmnController {

	@Resource
	private MainService mainService;
	
	// 벨포레소개
	@RequestMapping("/serIntrcn.do")
	public String serIntrcn(Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/bffser/serIntrcn";
	}
	
	// 단지안내
	@RequestMapping("/serInformation.do")
	public String serInformation(Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/bffser/serInformation";
	}
	
	// 오시는길
	@RequestMapping("/serWay.do")
	public String serWay(Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/bffser/serWay";
	}
	
	// 벨포레셔틀
	@RequestMapping("/serShuttle.do")
	public String serShuttle(Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/bffser/serShuttle";
	}
	
	// 주차장
	@RequestMapping("/serParking.do")
	public String serParking(Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/bffser/serParking";
	}
	
	// 주차장
	@RequestMapping("/serIntroduce.do")
	public String serIntroduce(Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/bffser/serIntroduce";
	}
	
	
}
