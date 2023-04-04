package com.hybird.example.user.menu.web;

/**
 * 사용자 앱 메뉴목록 조회를 위한 Controller
 *
 * @since	2022.02.15
 * @author	honock
 */

import com.hybird.example.user.menu.service.MenuService;
import com.hybird.example.user.menu.service.MenuVO;
import com.hybird.example.cmmn.CmmnController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user/menu")
public class MenuController extends CmmnController {

	@Resource(name = "MenuService")
	private MenuService menuService;


	/**
	 * 사용자 앱의 메뉴목록 조회
	 *
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menuList.ajax")
	public String menuList(@ModelAttribute("MenuVO") MenuVO vo, Model model) throws Exception {

		model.addAttribute("menuList", menuService.getMobileMenuList(vo));

		return "jsonView";
	}

}
