package com.hybird.example.user.mypage.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hybird.example.cmmn.*;
import com.hybird.example.user.mypage.service.MypageVO;
import com.plnc.cmmn.*;
import com.hybird.example.user.payMent.service.CondoPayHistoryVO;
import com.hybird.example.user.payMent.service.CondoService;
import com.hybird.example.user.payMent.util.OrderValueGenerator;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hybird.example.user.mypage.service.MypageService;
import com.hybird.example.user.payMent.service.PayMentService;
import com.hybird.example.user.payMent.service.payHistoryVO;
import com.hybird.example.user.payMent.util.OrderInvoiceUtil;
import com.hybird.example.main.service.MainService;

@Slf4j
@Controller
@RequestMapping("/user/mypage")
public class MypageController extends CmmnController {
	
	@Resource(name = "PayMentService")
	private PayMentService  payMentService;

	@Resource(name = "CondoService")
	private CondoService condoService;

	@Resource(name = "MypageService")
	private MypageService  mypageService;
	
	@Resource
	private MainService mainService;
	
	// 마이페이지
	@RequestMapping("/mypage.do")
	public String mypage(Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		if (auth == null || CmmnConstant.USER_ANONYMOUS.equals(auth.getPrincipal())) {
			return "redirect:/login.do";
		}
		return "user:/myPage/mypageView";
	}
	
	// 티켓 결제내역 2022.04.14 추가
		@GetMapping("/payMentHistory.do")
		public String payHistory(HttpServletRequest request, @RequestParam Map<String, Object> map, Model model) throws Exception {
	        /* 1. 로그인 체크  */
	        if (!PaymentUtil.isLogin(request)) {
	           return "redirect:/login.do";
	        }

			// 세션에서 가져오기
			String userId = OrderInvoiceUtil.getMemberAttribute(request, "id");

			// 고객 주문내역 조회
			String startDate = OrderValueGenerator.getValueForMap(map, "startDate");
			String endDate = OrderValueGenerator.getValueForMap(map, "endDate");
			String searchStatus = OrderValueGenerator.getValueForMap(map, "searchStatus");
			List<payHistoryVO> payHistoryList = payMentService.payHistoryList(userId, startDate, endDate, searchStatus);
			
			model.addAttribute("payHistoryList", payHistoryList);
			if (map != null && map.size() > 0) {
				for (String key : map.keySet()) {
					model.addAttribute(key, map.get(key));
				}
			}
			
			CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
			model.addAttribute("url_scheme", app);
			
			return "user:/myPage/orderHistory";
		}
		
		// 티켓주문상세내역
		@GetMapping("/payMentDetail.do")
		public String payMentDetail(HttpServletRequest request, Model model) throws Exception {
	        /* 1. 로그인 체크  */
	        if (!PaymentUtil.isLogin(request)) {
	            return "redirect:/login.do";
	        }
			// 세션에서 가져오기
	        String userId = OrderInvoiceUtil.getMemberAttribute(request, "id");
	        String orderSeq = (String)request.getParameter("orderSeq");

	        Map<String, Object> searchDetailMap = new HashMap<String, Object>();
	        
	        searchDetailMap.put("userId", userId);
	        searchDetailMap.put("orderSeq", orderSeq);
	        
	        // 결제일 조회
	        String buyDate = payMentService.payMentBuyDate(searchDetailMap);
	        searchDetailMap.put("buyDate", buyDate);
	        
			// 고객 주문내역 조회
			List<payHistoryVO> payMentDetail = payMentService.payMentDetail(searchDetailMap);
			
			// 고객정보 및 결제수단 조회
			payHistoryVO payMentInfo = payMentService.payMentInfo(searchDetailMap);

			model.addAttribute("payMentDetail", payMentDetail);
			model.addAttribute("Detail", searchDetailMap);
			model.addAttribute("payMentInfo", payMentInfo);
			
			return "user:/myPage/orderDetail";
		}

	/* 콘도 결제내역 */
	@GetMapping("/condoPayMentHistory.do")
	public String condoPayHistory(HttpServletRequest request, @RequestParam Map<String, Object> map, Model model) throws Exception {
		// 1. 로그인 체크
		if (!PaymentUtil.isLogin(request)) {
			return "redirect:/login.do";
		}

		// 2. 세션에서 가져오기
		String userId = OrderInvoiceUtil.getMemberAttribute(request, "id");

		// 3. 결제내역 조회
		String startDate = OrderValueGenerator.getValueForMap(map, "startDate");
		String endDate = OrderValueGenerator.getValueForMap(map, "endDate");
		List<CondoPayHistoryVO> condoPayList = condoService.condoPayHistoryList(userId, startDate, endDate);

		if (map != null && map.size() > 0) {
			for (String key : map.keySet()) {
				model.addAttribute(key, map.get(key));
				log.info("key : {}, value : {}", key, map.get(key));
			}
		}

		// 4. 결제내역은 30개까지만 출력
		if(condoPayList.size() > 30) {
			model.addAttribute("condoPayList", condoPayList.subList(0, 30));
		} else {
			model.addAttribute("condoPayList", condoPayList);
		}

		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);

		return "user:/myPage/condoOrderHistory";
	}

	/* 콘도결제상세내역 */
	@GetMapping("/condoPayMentDetail.do")
	public String condoPayMentDetail(HttpServletRequest request, Model model) throws Exception {
		// 1. 로그인 체크
		if (!PaymentUtil.isLogin(request)) {
			return "redirect:/login.do";
		}
		// 2. 세션에서 가져오기
		String userId = OrderInvoiceUtil.getMemberAttribute(request, "id");
		log.info("### request : {}", request);
		String reserveNo = (String)request.getParameter("reserveNo");

		Map<String, Object> searchDetailMap = new HashMap<>();

		searchDetailMap.put("userId", userId);
		searchDetailMap.put("reserveNo", reserveNo);

		// 3.결제일 조회
		String buyDate = condoService.condoPaymentBuyDate(userId, reserveNo);
		searchDetailMap.put("payDate", buyDate);
		log.info("결제일 조회 : {}", buyDate);
		log.info("### searchDetailMap ### {}", searchDetailMap);

		// 4. 고객 주문내역 조회
		CondoPayHistoryVO condoPayDetail = condoService.selectCondoPayDetail(searchDetailMap);
		log.info("결제 상세내역 : {}", condoPayDetail);
		searchDetailMap.put("condoSeq", condoPayDetail.getCondoSeq());
		// 5. 일자별 결제금액 조회
		List<CondoPayHistoryVO> condoPayDetailAmount = condoService.selectCondoPayDetailAmount(searchDetailMap);

		// 마지막날은 제외
		if(condoPayDetailAmount.size() > 1) {
			condoPayDetailAmount.remove(condoPayDetailAmount.size() - 1);
		}

		log.info("일자별 결제금액 조회 : {}", condoPayDetail);

		// 6. 결제수단 조회
		CondoPayHistoryVO condoPayment = condoService.selectCondoPaymentInfo(searchDetailMap);
		log.info("결제수단 조회 : {}", condoPayment);

		model.addAttribute("condoPayDetail", condoPayDetail);
		model.addAttribute("condoPayDetailAmount", condoPayDetailAmount);
		model.addAttribute("condoPayment", condoPayment);
		model.addAttribute("Detail", searchDetailMap);

		return "user:/myPage/condoOrderDetail";
	}
		
	// 회원탈퇴 화면
	@GetMapping("/mberSecsn.do")
	public String pageMberSecsnComplete(HttpServletRequest req,Model model) {
		//회원탈퇴 본인 인증 후 파라미터
		String kname = req.getParameter("kname");
		String birthDay = req.getParameter("birthDay");
		String handphone = req.getParameter("handphone");
		
		model.addAttribute("kname", kname);
		model.addAttribute("birthDay", birthDay);
		model.addAttribute("handphone", handphone);
		return "user:/myPage/mberSecsn";
	}
	
	// 회원 탈퇴 휴대폰 인증 성공
	@RequestMapping("/secsnSuccess.do")
	public String pageValidSuccess() {
		
		return "user:/myPage/secsnSuccess";
	}
	
	// 회원 탈퇴 휴대폰 인증 실패
	@RequestMapping("/secsnFail.do")
	public String pageValidFail() {
		
		return "user:/myPage/secsnFail";
	}
	
	// 다른번호 인증
	@RequestMapping("/smsCert.do")
	public String smsCert() {
		
		return "user:/myPage/smsCert";
	}
	
	/***
	 * 휴대폰번호 체크
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectUserHandphoneCheck.ajax")
	public String selectUserHandphoneCheck(@ModelAttribute("MypageVO") MypageVO vo, Model model) throws Exception {
		int result = mypageService.selectUserHandphoneCheck(vo);
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	/***
	 * 미사용 티켓 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/unusedTicketCnt.ajax")
	public String unusedTicketCnt(@ModelAttribute("MypageVO") MypageVO vo, Model model) throws Exception {
		int result = mypageService.unusedTicketCnt(vo);
		
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	
	/***
	 * 회원조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mberChk.ajax")
	public String mberChk(@ModelAttribute("MypageVO") MypageVO vo, Model model) throws Exception {
		int result = mypageService.mberChk(vo);
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	/***
	 * 회원탈퇴
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteMberSecsn.ajax")
	public String deleteMberSecsn(@ModelAttribute("MypageVO") MypageVO vo, Model model) throws Exception {
		vo.setUpdtIp(SessionUtil.getUserIp());
		int result = mypageService.deleteMberSecsn(vo);
		
		model.addAttribute("result", result);
		
		return "jsonView";
	}
	
	/**
	 * 기본정보
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myInfoData.ajax")
	public String myInfoData(@ModelAttribute("MypageVO") MypageVO vo, Model model) throws Exception {
		model.addAttribute("result", mypageService.myInfoData(vo));
		
		return "jsonView";
	}
	
	// 내정보 수정
	@RequestMapping("/myUpdt.do")
	public String myUpdt(Model model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) auth.getPrincipal();
		
		CmmnAppData app = CmmnAppData.builder().basket(this.mainService.basketCnt(userId)).menu("back").build();
		model.addAttribute("url_scheme", app);
		return "user:/myPage/myUpdt";
	}
	
	/**
	 * 현재 비밀번호 체크 
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myInfoPassword.ajax")
	public String myInfoPassword(@ModelAttribute("MypageVO") MypageVO vo, Model model) throws Exception {
		model.addAttribute("result", mypageService.myInfoPassword(vo));
		
		return "jsonView";
	}
	
	/**
	 * 기본정보 수정
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myInfoUpdt.ajax")
	public String myInfoUpdt(@ModelAttribute("MypageVO") MypageVO vo, Model model) throws Exception {
		vo.setUpdtIp(SessionUtil.getUserIp());
		model.addAttribute("result", mypageService.myInfoUpdt(vo));
		
		return "jsonView";
	}
	
	/**
	 * 장바구니
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/wishList.do")
	public String pageMberWishList(@ModelAttribute("MypageVO") MypageVO vo, Model model,HttpServletRequest request) throws Exception{
		//login.do 로 전달 
		
		if(!PaymentUtil.isLogin(request)) {
			return "redirect:/login.do";
		}
		String id = PaymentUtil.getMemberAttribute(request, "id");
		String memberNum = PaymentUtil.getMemberAttribute(request, "memberNum");
		String memberTypeNm = PaymentUtil.getMemberType(request);		
				
		vo.setMemberNum(memberNum);
		vo.setId(id);
		if(memberTypeNm == null) {
			vo.setMemberTypeNm("AP");	
		}else {
			vo.setMemberTypeNm(memberTypeNm+"P");	
		}

		model.addAttribute("mParam", mypageService.searchMypageWishList(vo));

		return "user:/myPage/wishList";
	}
	/**
	 * 장바구니에 담기
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/wishListInsert.do")
	public String wishListInsert(@ModelAttribute("MypageVO") MypageVO vo, Model model,HttpServletRequest req) throws Exception{
		
		
	
		
		return "/wishList.do";
	}
	
	/**
	 * 장바구니삭제
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/wishListdelete.do")
	public String wishListdelete(@ModelAttribute("MypageVO") MypageVO vo, Model model,HttpServletRequest req) throws Exception{
		
		
		return "/wishList.do";
	}
	
	/**
	 * 장바구니 수량 업데이트 ajax
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/wishListTicketAjax.ajax")
	public String wishListTicketAjax(@ModelAttribute("MypageVO") MypageVO vo, Model model,HttpServletRequest req) throws Exception{
		String id = PaymentUtil.getMemberAttribute(req, "id");
		vo.setId(id);
		int result = mypageService.wishListUpdate(vo);
		model.addAttribute("result",result);
		
		return "jsonView";
	}
	
	
	/**
	 * 장바구니 삭제 ajax
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/wishListDeleteAjax.ajax")
	public String wishListDeleteAjax(@ModelAttribute("MypageVO") MypageVO vo, Model model,HttpServletRequest req) throws Exception{
		int result = mypageService.wishListDelete(vo);
		model.addAttribute("result",result);
		
		return "jsonView";
	}
}
