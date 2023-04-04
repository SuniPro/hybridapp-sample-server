package com.hybird.example.user.mber.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.user.mber.service.MberService;
import com.hybird.example.user.mber.service.MberVO;

@Controller
@RequestMapping("/user/mber")
public class MberController extends CmmnController {

	@Resource(name = "MberService")
	private MberService  mberService;
	
	
	// 회원가입 1/3 - 약관동의 페이지
	@RequestMapping("/stplt.do")
	public String pageJoinStplt() {
		
		return "user:/mber/joinStplt";
	}
	
	// 회원가입 2/3 - 실명확인 및 가입여부 확인 페이지
	@RequestMapping("/valid.do")
	public String pageJoinValid() {
		return "user:/mber/joinValid";
	}
	
	// 회원가입 3/3 - 가입정보 기입란 페이지
	@RequestMapping("/input.do")
	public String pageJoinInput(HttpServletRequest req,Model model,@ModelAttribute("MberVO") MberVO vo) throws Exception {
		String kname = req.getParameter("kname");
		String birthDay = req.getParameter("birthDay");
		String handphone = req.getParameter("handphone");
		String sexCd = req.getParameter("sexCd");
		
		logger.info("#################### >> kname() "+kname);
		logger.info("#################### >> birthDay() "+birthDay);
		logger.info("#################### >> handphone() "+handphone);
		logger.info("#################### >> sexCd() "+sexCd);
		
		
		model.addAttribute("kname", vo.getKname());
		model.addAttribute("birthDay", vo.getBirthDay());
		model.addAttribute("handphone", vo.getHandphone());
		model.addAttribute("sexCd", vo.getSexCd());
		return "user:/mber/joinInput";
	}
	
	/***
	 * 휴대폰 중복 체크
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/infoUserPhoneChk.ajax")
	public String infoUserPhoneChk(@ModelAttribute("MberVO") MberVO vo, Model model) throws Exception {
		
		int result = mberService.searchUserPhoneCheck(vo);
		
		model.addAttribute("phoneChk", result);
		
		return "jsonView";
	}
	
	
	// 회원가입 완료
	@RequestMapping("/complete.do")
	public String pageJoinComplete() {
		
		return "user:/mber/joinComplete";
	}
	
	// 아이디 찾기
	@RequestMapping("/idFind.do")
	public String pageIdFindComplete() {
		
		return "user:/mber/findId";
	}
	
	// 회원가입 휴대폰 인증 성공
	@RequestMapping("/validSuccess.do")
	public String pageValidSuccess( Model model, HttpServletRequest request, HttpSession session) {
		
		String rootUrl = request.getRequestURL().toString().replace(request.getRequestURI(),"");
		//인증 후 결과값이 null로 나오는 부분은 관리담당자에게 문의 바랍니다.
	    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

	    String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");
	    
	    logger.info("sEncodeData ################################  >> "+sEncodeData);
	    
	    String sSiteCode = "BP123";					// NICE로부터 부여받은 사이트 코드
	    String sSitePassword = "5HfBaZ64tR8C";		// NICE로부터 부여받은 사이트 패스워드

	    String sCipherTime = "";			// 복호화한 시간
	    String sRequestNumber = "";			// 요청 번호
	    String sResponseNumber = "";		// 인증 고유번호
	    String sAuthType = "";				// 인증 수단
	    String sName = "";					// 성명
	    String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
	    String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
	    String sBirthDate = "";				// 생년월일(YYYYMMDD)
	    String sGender = "";				// 성별
	    String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
		String sMobileNo = "";				// 휴대폰번호
		String sMobileCo = "";				// 통신사
	    String sMessage = "";
	    String sPlainData = "";
	    
	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);
	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();
	        
	        // 데이타를 추출합니다.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
	        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
	        sResponseNumber = (String)mapresult.get("RES_SEQ");
	        sAuthType		= (String)mapresult.get("AUTH_TYPE");
	        sName			= (String)mapresult.get("NAME");
			//sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
	        sBirthDate		= (String)mapresult.get("BIRTHDATE");
	        sGender			= (String)mapresult.get("GENDER");
	        sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
	        sDupInfo		= (String)mapresult.get("DI");
	        sConnInfo		= (String)mapresult.get("CI");
	        sMobileNo		= (String)mapresult.get("MOBILE_NO");
	        sMobileCo		= (String)mapresult.get("MOBILE_CO");
	        
	        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
	        if(!sRequestNumber.equals(session_sRequestNumber))
	        {
	            sMessage = "세션값 불일치 오류입니다.";
	            sResponseNumber = "";
	            sAuthType = "";
	        }
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = "복호화 시스템 오류입니다.";
	    }    
	    else if( iReturn == -4)
	    {
	        sMessage = "복호화 처리 오류입니다.";
	    }    
	    else if( iReturn == -5)
	    {
	        sMessage = "복호화 해쉬 오류입니다.";
	    }    
	    else if( iReturn == -6)
	    {
	        sMessage = "복호화 데이터 오류입니다.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "입력 데이터 오류입니다.";
	    }    
	    else if( iReturn == -12)
	    {
	        sMessage = "사이트 패스워드 오류입니다.";
	    }    
	    else
	    {
	        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
	    }
	    logger.info("kname ################################  >> "+sName);
	    logger.info("birthDay ################################  >> "+sBirthDate);
	    logger.info("handphone ################################  >> "+sMobileNo);
	    logger.info("sGender ################################  >> "+sGender);
	    model.addAttribute("rootUrl", rootUrl);
	    model.addAttribute("kname", sName);
	    model.addAttribute("birthDay", sBirthDate);
	    model.addAttribute("handphone", sMobileNo);
	    model.addAttribute("sexCd", sGender);
		return "user:/mber/validSuccess";
	}
	
	//본인인증 후 데이터 파싱
	 public String requestReplace (String paramValue, String gubun) {

	        String result = "";
	        
	        if (paramValue != null) {
	        	
	        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

	        	paramValue = paramValue.replaceAll("\\*", "");
	        	paramValue = paramValue.replaceAll("\\?", "");
	        	paramValue = paramValue.replaceAll("\\[", "");
	        	paramValue = paramValue.replaceAll("\\{", "");
	        	paramValue = paramValue.replaceAll("\\(", "");
	        	paramValue = paramValue.replaceAll("\\)", "");
	        	paramValue = paramValue.replaceAll("\\^", "");
	        	paramValue = paramValue.replaceAll("\\$", "");
	        	paramValue = paramValue.replaceAll("'", "");
	        	paramValue = paramValue.replaceAll("@", "");
	        	paramValue = paramValue.replaceAll("%", "");
	        	paramValue = paramValue.replaceAll(";", "");
	        	paramValue = paramValue.replaceAll(":", "");
	        	paramValue = paramValue.replaceAll("-", "");
	        	paramValue = paramValue.replaceAll("#", "");
	        	paramValue = paramValue.replaceAll("--", "");
	        	paramValue = paramValue.replaceAll("-", "");
	        	paramValue = paramValue.replaceAll(",", "");
	        	
	        	if(gubun != "encodeData"){
	        		paramValue = paramValue.replaceAll("\\+", "");
	        		paramValue = paramValue.replaceAll("/", "");
	            paramValue = paramValue.replaceAll("=", "");
	        	}
	        	
	        	result = paramValue;
	            
	        }
	        return result;
	  }
	 
	// 회원가입 휴대폰 인증 실패
	@RequestMapping("/validFail.do")
	public String pageValidFail() {
		
		return "user:/mber/validFail";
	}
	
	// 아이디 찾기 완료
	@RequestMapping("/idFindResult.do")
	public String idFindResult(@ModelAttribute("MberVO") MberVO vo, Model model) {
		model.addAttribute("id", vo.getId());
		return "user:/mber/findIdResult";
	}
	
	// 비밀번호 찾기
	@RequestMapping("/pwFind.do")
	public String pagePwFindComplete() {
		
		return "user:/mber/findPassInput";
	}
	
	// 비밀번호 변경 페이지
	@RequestMapping("/newPassword.do")
	public String pagenewPasswordComplete(@ModelAttribute("MberVO") MberVO vo, Model model) {
		model.addAttribute("result", vo);
		return "user:/mber/newPassword";
	}
	
	/***
	 * 아이디 중복 체크
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/infoUserIdChk.ajax")
	public String infoUserIdChk(@ModelAttribute("MberVO") MberVO vo, Model model) throws Exception {
		int result = mberService.searchUserIdCheck(vo.getId());
		
		model.addAttribute("result", result);
		
		return "jsonView";
	}
	
	/***
	 * 회원가입
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mberSingup.ajax")
	public String mberSingup(@ModelAttribute("MberVO") MberVO vo, Model model) throws Exception {
		vo.setUpdtIp(SessionUtil.getUserIp());
		mberService.mberSingup(vo);

		model.addAttribute("result", vo);

		return "jsonView";
	}
	
	/**
	 * 아이디 찾기
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myIdFind.ajax")
	public String myIdFind(@ModelAttribute("MberVO") MberVO vo, Model model) throws Exception {
		
		model.addAttribute("result", mberService.myIdFind(vo));
		
		return "jsonView";
	}
	
	/**
	 * 비밀번호 찾기
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myPwdFind.ajax")
	public String myPwdFind(@ModelAttribute("MberVO") MberVO vo, Model model) throws Exception {
		
		model.addAttribute("result", mberService.myPwdFind(vo));
		
		return "jsonView";
	}
	
	/**
	 * 비밀번호 재설정
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myPwdReset.ajax")
	public String myPwdReset(@ModelAttribute("MberVO") MberVO vo, Model model) throws Exception {
		vo.setUpdtIp(SessionUtil.getUserIp());
		model.addAttribute("result", mberService.myPwdReset(vo));
		
		return "jsonView";
	}


	/**
	 * 로그인 이력 저장
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginHistInsert.ajax")
	public String loginHistInsert(@ModelAttribute("MberVO") MberVO vo, Model model) throws Exception {

		try{
			vo.setIsrtIp(SessionUtil.getUserIp());

			model.addAttribute("result", mberService.loginHistInsert(vo));

		} catch (Exception e){
			logger.error("/user/mber/loginHistInsert.ajax [로그인 이력저장 실패] ");
			e.printStackTrace();
		}

		return "jsonView";
	}
	
}
