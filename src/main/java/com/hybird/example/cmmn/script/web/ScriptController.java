package com.hybird.example.cmmn.script.web;

import javax.annotation.Resource;

import com.hybird.example.cmmn.script.service.ScriptService;
import com.hybird.example.cmmn.script.service.SmsAtalk;
import com.hybird.example.cmmn.script.service.SmsCertVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hybird.example.cmmn.CmmnController;

@Controller
@RequestMapping("/cmmn/script")
public class ScriptController extends CmmnController {

	@Resource(name = "ScriptService")
	private ScriptService scriptService;
	
	
	/***
	 * 유저 공통스크립트 로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userScriptCmmn.do")
	public String userBackCommonScript() throws Exception {
		return "cmmn/script/scriptCmmn";
	}
	
	/***
	 * 휴대폰 인증 번호 발송
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/smsCert.ajax")
	public String smsCert(@ModelAttribute("SmsCertVO") SmsCertVO vo, Model model) throws Exception {
		scriptService.smsCert(vo);
		return "jsonView";
	}
	
	/***
	 * 휴대폰 인증일치 여부
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/smsCertSameMatch.ajax")
	public String smsCertSameMatch(@ModelAttribute("SmsCertVO") SmsCertVO vo, Model model) throws Exception {
		model.addAttribute("data",scriptService.smsCertSameMatch(vo));
		return "jsonView";
	}
	
	/***
	 * mms 발송
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mmsSand.ajax")
	public String mmsCert(@ModelAttribute("SmsCertVO") SmsCertVO vo, Model model) throws Exception {
		scriptService.mmsSand(vo);
		return "jsonView";
	}

	/**
	 * 알림톡 발송
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/smsAtalk.ajax")
	public String insertAtalkMsg(@ModelAttribute("SmsAtalk") SmsAtalk vo, Model model) throws Exception {
		scriptService.insertAtalkMsg(vo);
		return "jsonView";
	}
	
	/**
	 * security 제외용 알림톡 발송
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/condoSmsAtalk.ajax")
	public String insertCondoAtalkMsg(@ModelAttribute("SmsAtalk") SmsAtalk vo, Model model) throws Exception {
		scriptService.insertAtalkMsg(vo);
		return "jsonView";
	}
	
}
