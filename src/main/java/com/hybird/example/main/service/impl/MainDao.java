package com.hybird.example.main.service.impl;

import com.hybird.example.main.service.SertbFclt;
import com.hybird.example.main.service.SertbFcltDetail;
import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.main.service.BasketProd;
import com.hybird.example.main.service.SertbFcltDetailImage;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OracleMapper("MainDao")
public interface MainDao {

	public Map<String, Object> selectAppVersion(String deviceType) throws Exception;

	public List<SertbFclt> menuUnitTicketList(HashMap<String, Object> map) throws Exception;

	public List<SertbFclt> fcltWaiting() throws Exception;

	public List<SertbFclt> menuPkgTicketList(HashMap<String, Object> map) throws Exception;

	public List<SertbFclt> menuUnitTicketList4bus() throws Exception;

	public List<SertbFcltDetail> menuUnitTicketDetail(HashMap<String, Object> map) throws Exception;

	public List<SertbFcltDetail> menuPkgTicketDetail(HashMap<String, Object> map) throws Exception;

	public List<SertbFcltDetailImage> menuTicketDetailImage(HashMap<String, Object> map) throws Exception;

	public void basketProd(BasketProd prod) throws Exception;

	public Integer basketCnt(String userId) throws Exception;

	public Integer myTicketCnt(String userId) throws Exception;

	public Integer giftCnt(String userId) throws Exception;

	public List<EgovMap> waitingBatchData() throws Exception;

	public List<EgovMap> updateToBeforeOpening() throws Exception;

	public List<EgovMap> updateToOpening() throws Exception;

	public List<EgovMap> updateToClosed() throws Exception;

	public int updateWaitingStatus(HashMap<String, Object> map) throws Exception;
}