package com.hybird.example.user.gift.service.impl;

import com.hybird.example.user.gift.service.GiftService;
import com.hybird.example.user.gift.service.GiftVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service("GiftService")
@Transactional(transactionManager = "txManager", readOnly = false)
public class GiftServiceImpl implements GiftService {
	/**
	 * Logger available to subclasses
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "GiftDAO")
	private GiftDAO giftDAO;


	@Override
	public List<GiftVO> listGift(String seUserId, String startDt, String endDt) throws Exception {
		return giftDAO.listGift(seUserId, startDt, endDt);
	}
}
