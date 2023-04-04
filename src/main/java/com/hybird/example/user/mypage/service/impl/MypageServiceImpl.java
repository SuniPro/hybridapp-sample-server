package com.hybird.example.user.mypage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import com.hybird.example.user.mber.service.impl.MberDAO;
import com.hybird.example.user.mypage.service.MypageVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Service;

import com.hybird.example.user.mypage.service.MypageService;

@Service("MypageService")
public class MypageServiceImpl implements MypageService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "MypageDAO")
	private MypageDAO mypageDAO;

	@Resource(name = "MberDAO")
	private MberDAO mberDAO;
	
	@Override
	public int mberChk(MypageVO vo) throws Exception {
		int result = 0;
		try {
			
			result = mypageDAO.mberChk(vo);
			
		} catch ( Exception e ) {
			logger.info("회원 조회");
		}
		return result;
	}
	
	@Override
	public int unusedTicketCnt(MypageVO vo) throws Exception {
		int result = 0;
		try {
			
			result = mypageDAO.unusedTicketCnt(vo);
			
		} catch ( Exception e ) {
			logger.info("미사용 티켓");
		}
		return result;
	}
	
	@Override
	public int selectUserHandphoneCheck(MypageVO vo) throws Exception {
		int result = 0;
		try {
			result = mypageDAO.selectUserHandphoneCheck(vo);
			
		} catch ( Exception e ) {
			logger.info("번호 체크");
		}
		return result;
	}
	
	@Override
	public int deleteMberSecsn(MypageVO vo) throws Exception {
		int result = 0;
		
		try {
			if(StringUtil.isEmpty(vo.getMemberType())) {
				vo.setWmSync("I");
				//탈퇴회원등록
				mypageDAO.insertMberSecsn(vo);
				//회원탈퇴
				mypageDAO.deleteMberSecsn(vo);
			}else {
				vo.setWmSync("E");
				//탈퇴회원등록
				mypageDAO.insertMberSecsn(vo);
				//회원 탈퇴 시 기존회원 업데이트
				mypageDAO.updateMberSecsn(vo);
			}
			
		} catch ( Exception e ) {
			logger.info("회원탈퇴 오류");
		}
		return result;
	}
	
	@Override
	public MypageVO myInfoData(MypageVO vo) throws Exception {
		MypageVO result = null;
		
		try {
			
			result = mypageDAO.myInfoData(vo);
			
		} catch (Exception e) {
			logger.error("myInfoData", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		return result;
	}
	
	@Override
	public int myInfoPassword(MypageVO vo) throws Exception {
		int result = 0;
		
		try {
			
			//비밀번호 체크 
			result = mypageDAO.myInfoPassword(vo);
			
		} catch ( Exception e ) {
			logger.info("비밀번호 체크");
		}
		return result;
	}
	
	@Override
	public int myInfoUpdt(MypageVO vo) throws Exception {
		int result = 0;
		int memberChk = 0;
		int updateMember = 0;
		String idMember = "";
		//콘도번호 체크
		if(StringUtil.isEmpty(vo.getMemberNoChk())) {
			if(!StringUtil.isEmpty(vo.getMemberNo())) {
				memberChk = mypageDAO.selectUserMyMemberNoCheck(vo);
				if(memberChk > 0) {
					updateMember = mypageDAO.selectUserMyMemberNoTwoCheck(vo);
					if(updateMember == 0) {
						throw new CmmnUserException(MessageManager.getIGMessage("mber.join.memberNo.name.fail", null));
					}else {
						idMember = mypageDAO.selectUserMyMemberNoTwoIdCheck(vo);
						if(!StringUtil.isEmpty(idMember)) {
							throw new CmmnUserException(MessageManager.getIGMessage("mber.join.memberNo.fail.id", null));
						}
					}
				}else {
					throw new CmmnUserException(MessageManager.getIGMessage("mber.join.memberNo.fail", null));
				}
			}
		}
		try {
			if(updateMember > 0) {
				//기존회원 업데이트
				result = mypageDAO.mberMemberNoMyUpdate(vo);
				vo.setWmSync("I");
				//탈퇴회원등록
				mypageDAO.insertMberSecsn(vo);
				//회원탈퇴
				mypageDAO.deleteMberSecsn(vo);
			}else {
				result = mypageDAO.myInfoUpdt(vo);
			}
			
		} catch ( Exception e ) {
			logger.error("myInfoUpdt", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.update", null));
		}
		return result;
	}
	
	@Override
	public List<MypageVO> searchMypageWishList(MypageVO vo) throws Exception {

		List<MypageVO> resultList = null;

		try {
			resultList = mypageDAO.searchMypageWishList(vo);
		} catch (Exception e) {
			logger.error("searchMypageWishListList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}

		return resultList;
	}

	@Override
	public int wishListUpdate(MypageVO vo) throws Exception {
		
		int result = mypageDAO.wishListUpdate(vo);
		
		return result;
	}
	
	@Override
	public int wishListDelete(MypageVO vo) throws Exception {
		
		int result = mypageDAO.wishListDelete(vo);
		
		return result;
	}

	
}
