package com.hybird.example.user.myTicket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import com.hybird.example.user.myTicket.service.MyBuyTicketVO;
import com.hybird.example.user.myTicket.service.MyTicketService;
import com.hybird.example.user.myTicket.service.MyTicketVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service("MyTicketService")
public class MyTicketServiceImpl implements MyTicketService {
	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "MyTicketDAO")
	private MyTicketDAO MyTicketDAO;
	
	@Override
	public List<MyTicketVO> myTicketList(MyTicketVO vo) throws Exception {
		List<MyTicketVO> resultList = null;
		try {
			resultList = MyTicketDAO.myTicketList(vo);
		} catch (Exception e) {
			logger.error("myTicketList", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.select", null));
		}
		return resultList;
	}

	@Override
	public List<MyBuyTicketVO> listBuyTicket(String userId, String tkState) throws Exception {
		if (tkState != null && "2".equals(tkState)) {
			return MyTicketDAO.listBuyTicket(userId, "2");
		} else {
			return MyTicketDAO.listBuyTicket(userId, null);
		}
	}
}
