package com.hybird.example.user.inqry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Service;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import com.hybird.example.cmmn.script.service.EmailVO;
import com.hybird.example.cmmn.script.service.ScriptService;
import com.hybird.example.user.inqry.service.UserInqryService;
import com.hybird.example.user.inqry.service.UserInqryVO;

@Service("UserInqryService")
public class UserInqryServiceImpl implements UserInqryService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "UserInqryDAO")
	private UserInqryDAO userInqryDAO;

	@Resource(name = "ScriptService")
	private ScriptService  scriptService;
	
	@Override
	public List<UserInqryVO> selectInqryList(UserInqryVO vo) throws Exception {
		List<UserInqryVO> resultList = null;

		try {
			resultList = userInqryDAO.selectInqryList(vo);
		} catch (Exception e) {
			logger.error("selectInqryList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}

		return resultList;
	}

	@Override
	public int insertInqry(UserInqryVO vo) throws Exception {
		int result = 0;  
		try {
			//발송이메일 조회
			String email = userInqryDAO.inqryMngrEmail(vo.getInqrySe());
			
			if(!StringUtil.isEmpty(email)) {
				//이메일 발송
				EmailVO mailVO = new EmailVO();
				mailVO.setEmail(email);
				mailVO.setSubject(vo.getInqrySj());
				mailVO.setContents(vo.getInqryCn());
				scriptService.emailSand(mailVO);
			}
			
			result = userInqryDAO.insertInqry(vo);
			
		} catch (Exception e) {
			logger.error("insertInqry", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.insert", null));
		}
		
		return result;
	}
	
	@Override
	public int updateInqry(UserInqryVO vo) throws Exception {
		int result = 0;  
		try {
			//발송이메일 조회
//			String email = userInqryDAO.inqryMngrEmail(vo.getInqrySe());
//			
//			//이메일 발송
//			EmailVO mailVO = new EmailVO();
//			mailVO.setEmail(email);
//			mailVO.setSubject(vo.getInqrySj());
//			mailVO.setContents(vo.getInqryCn());
//			scriptService.emailSand(mailVO);
			//히스토리 저장
			result = userInqryDAO.insertInqryHist(vo);
			
			result = userInqryDAO.updateInqry(vo);
			
		} catch (Exception e) {
			logger.error("updateInqry", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.update", null));
		}
		
		return result;
	}
	
	@Override
	public UserInqryVO selectInqryDatail(UserInqryVO vo) throws Exception {
		UserInqryVO result = null;  
		try {
			result = userInqryDAO.selectInqryDatail(vo);
			
		} catch (Exception e) {
			logger.error("selectInqryDatail", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return result;
	}
	
	@Override
	public int deleteInqry(UserInqryVO vo) throws Exception {
		int result = 0;  
		try {
			result = userInqryDAO.deleteInqry(vo);
			
		} catch (Exception e) {
			logger.error("delectInqry", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.delete", null));
		}
		
		return result;
	}

	@Override
	public List<UserInqryVO> searchUserInqryClCd(UserInqryVO vo) throws Exception {
		List<UserInqryVO> resultList = null;
		
		try {
			resultList = userInqryDAO.searchUserInqryClCd(vo);
		} catch (Exception e) {
			logger.error("searchUserInqryClCd", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return resultList;
	}

}
