package com.hybird.example.user.myTicket.web;

import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.cmmn.PaymentUtil;
import com.hybird.example.main.service.MainService;
import com.hybird.example.user.myTicket.service.MyBuyTicketVO;
import com.hybird.example.user.myTicket.service.MyTicketService;
import com.hybird.example.user.myTicket.service.MyTicketVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/myTicket")
public class MyTicketController extends CmmnController {

	@Resource(name = "MyTicketService")
	private MyTicketService myTicketService;

	@Resource
	private MainService mainService;


	// 마이티켓
	@GetMapping("/myTicket.do")
//	public String myTicket(@ModelAttribute("MyTicketVO") MyTicketVO vo, Model model, HttpServletRequest request) throws Exception {
	public String myTicket(HttpServletRequest request, String tkState, Model model) throws Exception {
		if (!PaymentUtil.isLogin(request)) {
			return "redirect:/login.do";
		}
		String seUserId = PaymentUtil.getMemberAttribute(request, "id");
//		vo.setSeUserId(seUserId);
		log.info("tkState : {}", tkState);
		List<MyBuyTicketVO> list = myTicketService.listBuyTicket(seUserId, tkState);
		list.forEach(o -> {
			String cssStr = "";
			// 회원 구분
			if ("R".equals(o.getMemType())) {
				cssStr += " red";
			}
			else if ("G".equals(o.getMemType())) {
				cssStr += " green";
			}
			else {
				if ("TICKET".equals(o.getFcltClass())) {
					cssStr += " yellow";
				}
				else {
					cssStr += " blue";
				}
			}
			// 사용완료여부
			if ("2".equals(o.getTkState())) {
				// 사용완료
				cssStr += " complete";
			}
			else {
				if (o.getTicketLimitCnt() == 0) {
					// 사용가능 - 횟수가 없는 상품
					cssStr += " available";
				}
			}
			o.setCssStr(cssStr);
		});
		log.info("list : {}", list);

		model.addAttribute("list", list);
		model.addAttribute("basket_cnt", this.mainService.basketCnt(seUserId));
		model.addAttribute("tkState", request.getParameter("tkState"));

		return "user:/myTicket/myTicketView";
	}

	@RequestMapping(value = "/myTicketList.ajax")
	public String myTicketList(@ModelAttribute("MyTicketVO") MyTicketVO vo, Model model, HttpServletRequest request) throws Exception {
		if (!PaymentUtil.isLogin(request)) {
			return "redirect:/login.do";
		}
		String seUserId = PaymentUtil.getMemberAttribute(request, "id");
		vo.setSeUserId(seUserId);
		model.addAttribute("list", myTicketService.myTicketList(vo));
		return "jsonView";
	}

}
