package com.hybird.example.user.login;

import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.cmmn.PaymentUtil;
import com.hybird.example.cmmn.script.service.ScriptService;
import com.hybird.example.cmmn.security.service.impl.DynamicSecurityDAO;
import com.hybird.example.user.login.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends CmmnController {

	@Resource(name = "LoginService")
	private LoginService LoginService;

	@Resource(name = "ScriptService")
	private ScriptService scriptService;

	@Resource(name = "DynamicSecurityDAO")
	private DynamicSecurityDAO dynamicSecurityDAO;


	// 로그인
	@GetMapping("/login.do")
	public String pageLoginComplete(HttpServletRequest request) {
		String returnStr = "";
		if (PaymentUtil.isLogin(request)) {
            return "redirect:/index.do";
        }
		
		returnStr = "user:/login/login";

		return returnStr;
	}

	/***
	 * 접속 구분
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/serIndex.do")
	public String serIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String redirectUrl = "redirect:/index.do";

		return redirectUrl;
	}

	/***
	 * 로그아웃 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/serLogout.do")
//	public String serLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String redirectUrl = "";
//
//		// 로그아웃을 실행하면 auth 내용을 클리어
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//		if (auth != null) {
//			new SecurityContextLogoutHandler().logout(request, response, auth);
//		}
//
//		redirectUrl = "main:/mainPageMove";
//		return redirectUrl;
//	}

	/***
	 * 사용자 로그인 페이지 접근
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userLoginLayer.do")
	public String userLoginLayer(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return "user:/login/userLoginLayer";
	}

}
