package com.hybird.example.user.inqry.web;

import javax.annotation.Resource;

import com.hybird.example.user.inqry.service.UserInqryService;
import com.hybird.example.user.inqry.service.UserInqryVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.main.service.MainService;

@Controller
@RequestMapping("/user/inqry")
public class UserInqryController extends CmmnController {

	@Resource(name = "UserInqryService")
	private UserInqryService userInqryService;
	
	@Resource
	private MainService mainService;
	/***
	 * 1:1 문의 page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inqryView.do")
	public String inqryView(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/inqry/inqryView";
	}
	
	/***
	 * 1:1문의 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectInqryList.ajax")
	public String selectInqryList(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		model.addAttribute("result", userInqryService.selectInqryList(vo));
		return "jsonView";
	}
	
	/***
	 * 1:1문의 코드 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchUserInqryClCd.ajax")
	public String searchUserInqryClCd(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		model.addAttribute("code", userInqryService.searchUserInqryClCd(vo));
		return "jsonView";
	}
	
	/***
	 * 1:1 문의 등록 page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inqryRegistView.do")
	public String inqryRegistView(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/inqry/inqryRegistView";
	}
	
	/***
	 * 1:1문의 등록
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertInqry.ajax")
	public String insertInqry(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		vo.setIsrtIp(SessionUtil.getUserIp());
		vo.setUpdtIp(SessionUtil.getUserIp());
		model.addAttribute("result", userInqryService.insertInqry(vo));
		return "jsonView";
	}
	
	/***
	 * 1:1 문의 수정페이지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inqryModifyView.do")
	public String inqryModifyView(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		model.addAttribute("inqrySn", vo.getInqrySn());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/inqry/inqryModifyView";
	}
	
	/***
	 * 1:1문의 상세 조회 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectInqryDatail.ajax")
	public String selectInqryDatail(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		model.addAttribute("result", userInqryService.selectInqryDatail(vo));
		return "jsonView";
	}
	
	/***
	 * 1:1문의 수정
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateInqry.ajax")
	public String updateInqry(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		vo.setUpdtIp(SessionUtil.getUserIp());
		model.addAttribute("result", userInqryService.updateInqry(vo));
		return "jsonView";
	}
	
	/***
	 * 1:1문의 삭제
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteInqry.ajax")
	public String deleteInqry(@ModelAttribute("UserInqryVO") UserInqryVO vo, Model model) throws Exception {
		vo.setUpdtIp(SessionUtil.getUserIp());
		model.addAttribute("result", userInqryService.deleteInqry(vo));
		return "jsonView";
	}
	
	
}
