package com.hybird.example.user.notice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import com.hybird.example.user.notice.service.UserNoticeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hybird.example.user.notice.service.UserNoticeVO;

@Service("UserNoticeService")
public class UserNoticeServiceImpl implements UserNoticeService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "UserNoticeDAO")
	private UserNoticeDAO userNoticeDAO;

	
	@Override
	public List<UserNoticeVO> selectNoticeList(UserNoticeVO vo) throws Exception {

		List<UserNoticeVO> resultList = null;

		try {
			vo.setTotPageCnt(userNoticeDAO.selectNoticeTotalCnt(vo));
			resultList = userNoticeDAO.selectNoticeList(vo);
		} catch (Exception e) {
			logger.error("selectStplatList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}

		return resultList;
	}
	
	
	@Override
	public List<UserNoticeVO> selectNoticePopupList(UserNoticeVO vo) throws Exception {
		
		List<UserNoticeVO> resultList = null;
		
		try {
			resultList = userNoticeDAO.selectNoticePopupList(vo);
		} catch (Exception e) {
			logger.error("selectNoticePopupList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return resultList;
	}
	
	@Override
	public UserNoticeVO searchNoticeDetail(UserNoticeVO vo) throws Exception {
		
		UserNoticeVO notice = new UserNoticeVO();
		
		try {
			notice = userNoticeDAO.selectNoticeDetail(vo);
		} catch (Exception e) {
			logger.error("searchNoticeDetail", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return notice;
	}

	@Override
	public List<UserNoticeVO> selectNoticeFile(UserNoticeVO vo) throws Exception {
		
		List<UserNoticeVO> resultList = null;
		
		try {
			resultList = userNoticeDAO.selectNoticeFile(vo);
		} catch (Exception e) {
			logger.error("selectNoticeFile", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return resultList;
	}


}
