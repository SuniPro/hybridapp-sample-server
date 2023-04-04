package com.hybird.example.user.stplat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hybird.example.user.stplat.service.StplatService;
import com.hybird.example.user.stplat.service.StplatVO;

@Service("StplatService")
public class StplatServiceImpl implements StplatService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "StplatDAO")
	private StplatDAO stplatDAO;

	@Override
	public List<StplatVO> userStplatListView() throws Exception {
		
		List<StplatVO> resultList = null;
		
		try {
			resultList = stplatDAO.userStplatListView();
			
		} catch (Exception e) {
			logger.error("selectStplatList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return resultList;
	}

	@Override
	public StplatVO searchStplat(StplatVO vo) throws Exception {

		StplatVO result = null;
		
		try {
			
			result = stplatDAO.selectStplat(vo);
			
		} catch (Exception e) {
			logger.error("searchStplat", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		
		return result;
	}	
}
