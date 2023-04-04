package com.hybird.example.user.myTicket.service;

import java.util.List;

public interface MyTicketService {
	
	public List<MyTicketVO> myTicketList(MyTicketVO vo) throws Exception;
	List<MyBuyTicketVO> listBuyTicket(String userId, String tkState) throws Exception;
}
