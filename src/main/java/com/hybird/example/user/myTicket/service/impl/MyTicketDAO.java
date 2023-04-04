package com.hybird.example.user.myTicket.service.impl;

import java.util.List;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.myTicket.service.MyBuyTicketVO;
import com.hybird.example.user.myTicket.service.MyTicketVO;
import org.apache.ibatis.annotations.Param;

@OracleMapper("MyTicketDAO")
public interface MyTicketDAO {
	
	List<MyTicketVO> myTicketList(MyTicketVO vo) throws Exception;
	List<MyBuyTicketVO> listBuyTicket(@Param("userId")  String userId, @Param("tkState") String tkState) throws Exception;
}
