package com.hybird.example.main.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MainService {
	/***
	 * App버전 체크
	 */
	public Map<String, Object> selectAppVersion(String deviceType) throws Exception;

	/***
	 * 메인 단위 상품 리스트
	 */
	public List<SertbFclt> menuUnitTicketList(HashMap<String, Object> map) throws Exception;

	/***
	 * 시설대기시간
	 */
	public List<SertbFclt> fcltWaiting() throws Exception;

	/***
	 * 메인 패키지 상품 리스트
	 */
	public List<SertbFclt> menuPkgTicketList(HashMap<String, Object> map) throws Exception;

	/***
	 * 메인 승차권권상품 리스트
	 */
	public List<SertbFclt> menuUnitTicketList4bus() throws Exception;

	/***
	 * 메인 단품 상세 리스트
	 */
	public List<SertbFcltDetail> menuUnitTicketDetail(HashMap<String, Object> map) throws Exception;

	/***
	 * 메인 패키디 상세 리스트
	 */
	public List<SertbFcltDetail> menuPkgTicketDetail(HashMap<String, Object> map) throws Exception;


	/***
	 * 메인 섬네일이미지
	 */
	public List<SertbFcltDetailImage> menuTicketDetailImage(HashMap<String, Object> map) throws Exception;

	/***
	 * 장바구니에 저장
	 */
	public String basketProd(PurchaseDto req, List<Object> obj) throws Exception;

	/***
	 * 주문에 전달
	 */
	public String sendProd(PurchaseDto req, List<Object> obj) throws Exception;

	/***
	 * 장바구기 갯수
	 */
	public Integer basketCnt(String userId) throws Exception;
	/***
	 * 마이티켓 갯수
	 */
	public Integer myTicketCnt(String userId) throws Exception;
	/*
	* 선물함 내 선물 갯수
	* */
	public Integer giftCnt(String userId) throws Exception;
	/***
	 * 회원가격 변경
	 */
	public List<SertbFclt> makePrice(String userId, List<SertbFclt> list) throws Exception;

	public SertbFclt makePrice(String userId, SertbFclt o) throws Exception;

	// 준비중
	public void beforeOpeningBatch() throws Exception;

	// 운행중
	public void openBatch() throws Exception;

	// 운행종료
	public void closedBatch() throws Exception;

	public void updateStatus(List<EgovMap> list) throws Exception;
}
