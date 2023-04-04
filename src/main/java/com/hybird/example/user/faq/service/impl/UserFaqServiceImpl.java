package com.hybird.example.user.faq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hybird.example.user.faq.service.UserFaqService;
import com.hybird.example.user.faq.service.UserFaqVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.fileUtil.service.CmmnFileSaveService;
import com.hybird.example.cmmn.msg.MessageManager;

@Service("UserFaqService")
public class UserFaqServiceImpl implements UserFaqService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "UserFaqDAO")
	private UserFaqDAO userFaqDAO;
	
	@Resource(name = "CmmnFileSaveService")
	private CmmnFileSaveService cmmnFileSaveService;

	@Override
	public List<UserFaqVO> searchUserFaqList(UserFaqVO vo) throws Exception {
		
		List<UserFaqVO> resultList = null;
		
		try {
			resultList = userFaqDAO.searchUserFaqList(vo);
			
		} catch (Exception e) {
			logger.error("searchUserFaqList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return resultList;
	}
	
	@Override
	public List<UserFaqVO> searchUserFaqClCd(UserFaqVO vo) throws Exception {
		
		List<UserFaqVO> resultList = null;
		
		try {
			resultList = userFaqDAO.searchUserFaqClCd(vo);
			
		} catch (Exception e) {
			logger.error("searchUserFaqClCd", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return resultList;
	}

}
