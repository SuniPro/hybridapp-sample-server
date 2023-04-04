package com.hybird.example.main.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybird.example.main.service.SertbFclt;
import com.hybird.example.cmmn.CmmnAppData;
import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.PaymentUtil;
import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.main.service.MainService;
import com.hybird.example.main.service.PurchaseDto;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class MainController {

	@Resource(name = "LoginService")
	private com.hybird.example.user.login.service.LoginService LoginService;

	@Resource
	private MainService mainService;

	/**
	 * index
	 */
	@GetMapping(value = "/index.do")
	public String idx(@RequestParam HashMap<String, Object> map, HttpServletRequest request, /*HttpServletResponse response,*/ Model model) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		/* URL scheme */
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("ham").title("logo").build();
		model.addAttribute("url_scheme", app);
		/* URL scheme */

		// 로그인 전
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {

			// 로그아웃하면 session이 완전히 사라짐
			HttpSession session = request.getSession(false);

			if (session == null) {
				session = request.getSession();
			}

			@SuppressWarnings("unchecked") List<EgovMap> menuList = (List<EgovMap>) LoginService.searchBasicViewMenuList();

			session.setAttribute(CmmnConstant.SESSION_MENU, menuList);

			// 타입코드가 넘어오면 Head에서 처리 Session 들어있으면
			String typeCode = SessionUtil.getDirectAttribute(CmmnConstant.CHECK_QUERY_STRING);

			if (typeCode != null && !"".equals(typeCode)) {
				model.addAttribute(CmmnConstant.CHECK_QUERY_STRING, typeCode);
				// 타입코드가 계속 있으면 안되므로 제거
				session.removeAttribute(CmmnConstant.CHECK_QUERY_STRING);
			}

			// 타입코드가 넘어오면 Head에서 처리
			String typeCodeParam = request.getParameter(CmmnConstant.CHECK_QUERY_STRING);

			if (typeCodeParam != null && !"".equals(typeCodeParam)) {
				model.addAttribute(CmmnConstant.CHECK_QUERY_STRING, typeCodeParam);
			}

			return "main:/main";
		}
		// 로그인 후
		else {
			return "main:/main";
		}
	}
	
	/**
	 * main 변경으로 index.do > ticket.do
	 */
	@GetMapping(value = "/ticket.do")
	public String ticket(@RequestParam HashMap<String, Object> map, HttpServletRequest request, /*HttpServletResponse response,*/ Model model) throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		/* URL scheme */
//		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("ham").title("logo").build();
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		map.put("single_mode", 1);
		map.put("activity_mode", 1);
		/* URL scheme */
		
		// 승차권 링크로 진입 시 승차권 탭으로 바로가기 
		model.addAttribute("tab", request.getParameter("tab"));
		
		// 로그인 전
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
			
			// 로그아웃하면 session이 완전히 사라짐
			HttpSession session = request.getSession(false);
			
			// 그래서 하나 생성해줌 어차피 anonymousUser 이므로 생성해도 될 듯?
			if (session == null) {
				session = request.getSession();
			}
			
			@SuppressWarnings("unchecked") List<EgovMap> menuList = (List<EgovMap>) LoginService.searchBasicViewMenuList();
			
			session.setAttribute(CmmnConstant.SESSION_MENU, menuList);
			
			// 타입코드가 넘어오면 Head에서 처리 Session 들어있으면
			String typeCode = SessionUtil.getDirectAttribute(CmmnConstant.CHECK_QUERY_STRING);
			
			if (typeCode != null && !"".equals(typeCode)) {
				model.addAttribute(CmmnConstant.CHECK_QUERY_STRING, typeCode);
				// 타입코드가 계속 있으면 안되므로 제거
				session.removeAttribute(CmmnConstant.CHECK_QUERY_STRING);
			}
			
			// 타입코드가 넘어오면 Head에서 처리
			String typeCodeParam = request.getParameter(CmmnConstant.CHECK_QUERY_STRING);
			
			if (typeCodeParam != null && !"".equals(typeCodeParam)) {
				model.addAttribute(CmmnConstant.CHECK_QUERY_STRING, typeCodeParam);
			}
			
			model.addAttribute("items", this.mainService.menuUnitTicketList(map));
			model.addAttribute("pkg_items", this.mainService.menuPkgTicketList(map));
			
			// 셔틀카트
			map.put("activity_mode", 2);
			model.addAttribute("bus_items", this.mainService.menuUnitTicketList(map));
			return "main:/ticket";
		}
		// 로그인 후
		else {
			model.addAttribute("items", this.mainService.makePrice(userId, this.mainService.menuUnitTicketList(map)));
			model.addAttribute("pkg_items", this.mainService.menuPkgTicketList(map));
			
			// 셔틀카트
			map.put("activity_mode", 2);
			model.addAttribute("bus_items", this.mainService.makePrice(userId, this.mainService.menuUnitTicketList(map)));
			return "main:/ticket";
		}
	}

	/***
	 * 로그아웃 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/serLogout.do")
	public String serLogout(@RequestParam HashMap<String, Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String redirectUrl = "";
		
		// 로그아웃을 실행하면 auth 내용을 클리어
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		if( map.get("myUpdt") != null) {
//			return "redirect:/login.do";
			return "user:/login/logout";
		}
		redirectUrl = "redirect:/index.do";
		return redirectUrl;
	}

	@GetMapping(value = "/detail.do")
	public String detail(@RequestParam HashMap<String, Object> map, Model model) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		/* URL scheme */
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		/* URL scheme */

		map.put("single_mode", 1);
		if (map.get("pchs") != null && map.get("pchs").equals("t")) {
			model.addAttribute("pchs", "t");
		}
		if (map.get("t").equals("pkg")) {
			map.put("image_type", "EQIPP");
			model.addAttribute("images", this.mainService.menuTicketDetailImage(map));
			model.addAttribute("item", this.mainService.makePrice(userId, this.mainService.menuPkgTicketList(map).get(0)));
			model.addAttribute("details", this.mainService.menuPkgTicketDetail(map));
		}
		else {
			map.put("image_type", "EQIP");
			model.addAttribute("images", this.mainService.menuTicketDetailImage(map));
			model.addAttribute("item", this.mainService.makePrice(userId, this.mainService.menuUnitTicketList(map).get(0)));
			model.addAttribute("details", this.mainService.menuUnitTicketDetail(map));
		}
		model.addAttribute("query", map);
		return "main_detail:/detail";
	}

	@GetMapping(value = "/purchase.do")
	public String purchase(@RequestParam HashMap<String, Object> map, Model model) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();

		/* URL scheme */
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).title("구매하기").build();
		model.addAttribute("url_scheme", app);
		/* URL scheme */

		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
		String validDayString = today.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " ~ " + today.plusDays(31).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
		List<SertbFclt> list;
		if (map.get("t").equals("pkg")) {
			list = this.mainService.menuPkgTicketList(map);
		}
		else {
			list = this.mainService.menuUnitTicketList(map);
		}

		List<SertbFclt> weekend = new ArrayList<>();
		List<SertbFclt> days = new ArrayList<>();
		List<SertbFclt> allday = new ArrayList<>();

		for (SertbFclt obj : list) {
			if (obj.getWeekFlag() != null ) {
				if (obj.getWeekFlag().equals("E")) {
					weekend.add(obj); // 주말상품
				}
				else if (obj.getWeekFlag().equals("D")) {
					days.add(obj); // 주중상품
				}
				else {
					allday.add(obj);
				}
			}
		}
		model.addAttribute("weekend", this.mainService.makePrice(userId, weekend)); // 주말상품
		model.addAttribute("days", this.mainService.makePrice(userId, days)); // 주중상품
		model.addAttribute("allday", this.mainService.makePrice(userId, allday)); // 주중 + 주말 상품
		model.addAttribute("validate", validDayString); // 유효기간
		
		model.addAttribute("idx", map.get("idx")); // 상품코드

		return "main_purchase:/purchase";
	}

	@GetMapping(value = "/waiting.do")
	public String waiting(@RequestParam HashMap<String, Object> map, Model model) throws Exception {

		/* URL scheme */
		CmmnAppData app = CmmnAppData.builder().title("현장대기").build();
		model.addAttribute("url_scheme", app);
		/* URL scheme */

		model.addAttribute("items", this.mainService.fcltWaiting());
		return "main_detail:/waiting";
	}

	@GetMapping(value = "/purchasefilter.do")
	public String purchasefiler(@RequestParam HashMap<String, Object> map, HttpServletRequest request) throws Exception {

		if (!PaymentUtil.isLogin(request)) {
			return "redirect:/login.do";
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String redirectUrl = "/user/mypage/wishList.do"; // default 장바구니 URL
		String query = "?query=";
		String json = new String(Base64.getDecoder().decode((String) map.get("query")));

		//TODO 삭제
		log.info(">>> query = {}", map.get("query"));

		ObjectMapper mapper = new ObjectMapper();
		PurchaseDto req = mapper.readValue(json, new TypeReference<PurchaseDto>() {
		});

		// 회원아이디 추출
		log.info(">>> purchase.do / login user id = " + auth.getPrincipal());
		req.setMid((String) auth.getPrincipal());
		List<Object> obj = (List<Object>) req.getData();

		if (!req.getSend().equals("basket")) {
			String json_str = this.mainService.sendProd(req, obj);
			redirectUrl = "/user/payMent/orderInvoice.do?is_full=y&query=" + json_str; // 결제페이지 이동
		}
		return "redirect:" + redirectUrl;
	}
}