package com.hybird.example.main.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybird.example.cmmn.JsonUtil;
import com.hybird.example.cmmn.security.service.impl.DynamicSecurityDAO;
import com.hybird.example.main.service.*;
import com.plnc.main.service.*;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class MainServiceImpl implements MainService {


	@Resource
	private MainDao mainDao;

	@Resource(name = "DynamicSecurityDAO")
	private DynamicSecurityDAO dynamicSecurityDAO;

	@Override
	public Map<String, Object> selectAppVersion(String deviceType) throws Exception {
		return mainDao.selectAppVersion(deviceType);
	}

	@Override
	public List<SertbFclt> menuUnitTicketList(HashMap<String, Object> map) throws Exception {
		return mainDao.menuUnitTicketList(map);
	}

	@Override
	public List<SertbFclt> fcltWaiting() throws Exception {
		return mainDao.fcltWaiting();
	}

	@Override
	public List<SertbFcltDetail> menuUnitTicketDetail(HashMap<String, Object> map) throws Exception {
		return mainDao.menuUnitTicketDetail(map);
	}

	@Override
	public List<SertbFcltDetail> menuPkgTicketDetail(HashMap<String, Object> map) throws Exception {
		return mainDao.menuPkgTicketDetail(map);
	}

	@Override
	public List<SertbFcltDetailImage> menuTicketDetailImage(HashMap<String, Object> map) throws Exception {
		return mainDao.menuTicketDetailImage(map);
	}

	@Override
	public String basketProd(PurchaseDto req, List<Object> obj) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		for (Object o : obj) {
			BasketProd prod = mapper.convertValue(o, BasketProd.class);
			prod.setMemberId(req.getMid());
			prod.setMuMpFlag((req.getType().equals("pkg") ? "P" : "S"));
			mainDao.basketProd(prod);
		}
		return "";
	}

	@Override
	public String sendProd(PurchaseDto req, List<Object> obj) throws Exception {

		String json_str = null;
		ObjectMapper mapper = new ObjectMapper();
		PurcaseProd purcaseProd = new PurcaseProd();
		purcaseProd.setType(req.getSend());
		purcaseProd.setMid(req.getMid());
		for (Object o : obj) {
			BasketFilterProd basketFilterProd = mapper.convertValue(o, BasketFilterProd.class);
			basketFilterProd.setType(req.getType());
			purcaseProd.getData().add(basketFilterProd);
		}
		try {
			json_str = JsonUtil.mapToJson(JsonUtil.objToMap(purcaseProd));
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(json_str.getBytes());
	}

	@Override
	public Integer basketCnt(String userId) throws Exception {
		return this.mainDao.basketCnt(userId);
	}
	@Override
	public Integer myTicketCnt(String userId) throws Exception {
		return this.mainDao.myTicketCnt(userId);
	}

	@Override
	public Integer giftCnt(String userId) throws Exception {
		return mainDao.giftCnt(userId);
	}

	@Override
	public List<SertbFclt> menuPkgTicketList(HashMap<String, Object> map) throws Exception {
		return mainDao.menuPkgTicketList(map);
	}

	@Override
	public List<SertbFclt> menuUnitTicketList4bus() throws Exception {
		return mainDao.menuUnitTicketList4bus();
	}

	@Override
	public List<SertbFclt> makePrice(String userId, List<SertbFclt> list) throws Exception {
		for (SertbFclt o : list) {
			this.makePrice(userId, o);
		}
		return list;
	}

	@Override
	public SertbFclt makePrice(String userId, SertbFclt o) throws Exception {
		EgovMap userInfoMap = this.dynamicSecurityDAO.selectSecurityUserInfo(userId);
		if (userInfoMap == null) return o;
		Optional<String> type = Optional.ofNullable((String) userInfoMap.get("memberType"));
		log.info(">>> {}", userInfoMap.get("id"));
		type.ifPresent(t -> {
			if (t.equals("G")) {
				o.setAP(o.getGP());
			}
			else if (t.equals("R")) {
				o.setAP(o.getRP());
			}
		});
		return o;
	}

	@Override
	public void beforeOpeningBatch() throws Exception {
		List<EgovMap> list = this.mainDao.updateToBeforeOpening();
		log.info(">>> Batch : name = {}", "beforeOpeningBatch()");
		this.updateStatus(list);
	}

	@Override
	public void openBatch() throws Exception {
		List<EgovMap> list = this.mainDao.updateToOpening();
		log.info(">>> Batch : name = {}", "openBatch()");
		this.updateStatus(list);
	}

	@Override
	public void closedBatch() throws Exception {
		List<EgovMap> list = this.mainDao.updateToClosed();
		log.info(">>> Batch : name = {}", "closedBatch()");
		this.updateStatus(list);
	}

	@Override
	public void updateStatus(List<EgovMap> l) {

		Optional<List<EgovMap>> opt = Optional.ofNullable(l);

		opt.ifPresent(list -> {
			for (EgovMap map : list) {
				boolean update = false;
				log.info(">>> map = {}", map.toString());
				if (map.get("dayorweekend").equals("1")) {
					if (((BigDecimal)map.get("d")).intValue() == 0 ) {
						update = true;
					}
				}
				else if (map.get("dayorweekend").equals("2")) {
					if ( ((BigDecimal)map.get("e")).intValue() == 0 ) {
						update = true;
					}
				}
				if (update) {
					HashMap<String, Object> data = new HashMap<>();
					data.put("status", map.get("nextstatus"));
					data.put("idx", map.get("fcltseq"));
					log.info(">>> Batch : status = {} idx = {}", map.get("nextstatus"), map.get("fcltseq"));
					try {
						this.mainDao.updateWaitingStatus(data);
					}
					catch (Exception e) {
						log.info(">>> exception msg : ", e);
					}
				}
			}
		});
	}
}