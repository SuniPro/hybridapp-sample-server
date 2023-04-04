package com.hybird.example.user.stplat.web;

import javax.annotation.Resource;

import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.user.stplat.service.StplatService;
import com.hybird.example.user.stplat.service.StplatVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/stplat")
public class StplatController extends CmmnController {
	
	@Resource(name = "StplatService")
	private StplatService stplatService;
	
	/***
	 * 약관동의 화면 호출
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stplatAgreView.do")
	public String userStplatAgre(@ModelAttribute("StplatVO") StplatVO vo, Model model) throws Exception {
	
		return "user:/mber/joinStplt";
	}
	
	/***
	 * 약관 상세 보기
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stplatAgreDetail.do")
	public String rdnmAdrSch ( Model model, @ModelAttribute("StplatVO") StplatVO vo) throws Exception {
		model.addAttribute("stplatSe", vo.getStplatSe());
		return "user:/mber/detailStplt";
//		return "main_detail:/detailStplt";
	}
	
	  
	/**
	 * 악관 조회
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userStplatListView.ajax")
	public String userStplatListView(@ModelAttribute("StplatVO") StplatVO vo, Model model) throws Exception {

		model.addAttribute("result", stplatService.userStplatListView());
		
		return "jsonView";
	}
	
	/**
	 * 악관 상세 조회
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchStplat.ajax")
	public String searchStplat(@ModelAttribute("StplatVO") StplatVO vo, Model model) throws Exception {
		
		model.addAttribute("result", stplatService.searchStplat(vo));
		
		return "jsonView";
	}
}
