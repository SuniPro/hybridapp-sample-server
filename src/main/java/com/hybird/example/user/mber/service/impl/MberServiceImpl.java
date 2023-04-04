package com.hybird.example.user.mber.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import com.hybird.example.cmmn.script.service.ScriptService;
import com.hybird.example.user.mber.service.MberService;
import com.hybird.example.user.mber.service.MberVO;

@Service("MberService")
public class MberServiceImpl implements MberService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "MberDAO")
	private MberDAO mberDAO;
	
	@Resource(name = "ScriptService")
	private ScriptService  scriptService;
	
	@Override
	public int searchUserIdCheck(String userId) throws Exception {
		
		int result = 0;

		try {
			result = mberDAO.selectUserIdCheck(userId);

		} catch (Exception e) {
			logger.error("searchUserIdCheck", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.check", null));
		}

		return result;
	}
	
	@Override
	public int searchUserPhoneCheck(MberVO vo) throws Exception {
		
		int result = 0;
		
		try {
			result = mberDAO.selectUserHandphoneCheck(vo);
			
		} catch (Exception e) {
			logger.error("searchUserPhoneCheck", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.check", null));
		}
		
		return result;
	}

	@Override
	@Transactional(transactionManager = "txManager", rollbackFor = CmmnUserException.class)
	public int mberSingup(MberVO vo) throws Exception {
		int result = 0;
		int idCheck = 0; 
		int handphoneCheck = 0; 
		int updateMember = 0; 
		int memberChk = 0;
		idCheck = mberDAO.selectUserIdCheck(vo.getId());
		handphoneCheck = mberDAO.selectUserHandphoneCheck(vo);
		if(!StringUtil.isEmpty(vo.getMemberNum())) {
			memberChk = mberDAO.selectUserMemberNoCheck(vo);
			if(memberChk > 0) {
				updateMember = mberDAO.selectUserMemberNoTwoCheck(vo);
				if(updateMember == 0) {
					throw new CmmnUserException(MessageManager.getIGMessage("mber.join.memberNo.name.fail", null));
				}
			}else {
				throw new CmmnUserException(MessageManager.getIGMessage("mber.join.memberNo.fail", null));
			}
		}else {
			vo.setMemberNum(mberDAO.selectUserMemberNo(vo));
		}
		vo.setResno(mberDAO.selectUserResno(vo));
		if(idCheck > 0) { //중복 아이디 체크
			throw new CmmnUserException(MessageManager.getIGMessage("mber.join.userId.dplct.fail", null));
		}
		if(handphoneCheck > 0) { //중복 휴대폰 체크
			throw new CmmnUserException(MessageManager.getIGMessage("mber.join.userPhone.dplct.fail", null));
		}
		try {
			if(updateMember > 0) {
				result = mberDAO.mberSingupUpdate(vo);
			}else {
				result = mberDAO.mberSingup(vo);
			}
		} catch (Exception e) {
			logger.error("mberSingup", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.sbscrb", null));
		}
		return result;
	}
	

	@Override
	public String myIdFind(MberVO vo) throws Exception {
		
		String result;
		
		try {
			result = mberDAO.myIdFind(vo);
			
		} catch (Exception e) {
			logger.error("myIdFind", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return result;
	}
	
	@Override
	public int myPwdFind(MberVO vo) throws Exception {
		
		int result;
		
		try {
			result = mberDAO.myPwdFind(vo);
			
		} catch (Exception e) {
			logger.error("myPwdFind", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return result;
	}
	
	@Override
	public int myPwdReset(MberVO vo) throws Exception {
		
		int result = 0;
		
		try {
			
//			vo.setPassword(BCryptManager.getInstance().encode(vo.getPassword())); //비밀번호 암호화
			result = mberDAO.myPwdReset(vo);
			
		} catch (Exception e) {
			logger.error("myPwdReset", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.update", null));
		}
		
		return result;
	}

	/**
	 * 로그인 이력 저장
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int loginHistInsert(MberVO vo) throws Exception {
		int result = 0;

		try {

			result = mberDAO.loginHistInsert(vo);

		} catch (Exception e) {
			logger.error("myPwdReset", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.update", null));
		}
		return result;
	}
}
