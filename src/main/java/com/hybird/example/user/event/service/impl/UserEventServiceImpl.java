package com.hybird.example.user.event.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import com.hybird.example.user.event.service.UserEventService;
import com.hybird.example.user.event.service.UserEventVO;

@Service("UserEventService")
public class UserEventServiceImpl implements UserEventService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "UserEventDAO")
	private UserEventDAO userEventDAO;
	

	@Override
	public List<UserEventVO> selectEventList(UserEventVO vo) throws Exception {

		List<UserEventVO> resultList = null;

		try {
			vo.setTotPageCnt(userEventDAO.selectEventTotalCnt(vo));
			resultList = userEventDAO.selectEventList(vo);
		} catch (Exception e) {
			logger.error("selectEventList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}

		return resultList;
	}
	
	
	@Override
	public List<UserEventVO> selectEventPopupList(UserEventVO vo) throws Exception {
		
		List<UserEventVO> resultList = null;
		
		try {
			resultList = userEventDAO.selectEventPopupList(vo);
		} catch (Exception e) {
			logger.error("selectEventPopupList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return resultList;
	}
	
	@Override
	public UserEventVO searchEventDetail(UserEventVO vo) throws Exception {
		
		UserEventVO notice = new UserEventVO();
		
		try {
			notice = userEventDAO.selectEventDetail(vo);
		} catch (Exception e) {
			logger.error("searchEventDetail", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return notice;
	}

	@Override
	public List<UserEventVO> selectEventFile(UserEventVO vo) throws Exception {
		
		List<UserEventVO> resultList = null;
		
		try {
			resultList = userEventDAO.selectEventFile(vo);
		} catch (Exception e) {
			logger.error("selectEventFile", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return resultList;
	}


}
