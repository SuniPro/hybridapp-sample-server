package com.hybird.example.cmmn.resapi.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.cmmn.resapi.service.ResApiService;

@Service("ResApiService")
public class ResApiServiceImpl implements ResApiService {
	
	@Resource(name = "ResApiDAO")
    private ResApiDAO resApiDAO;

	@Override
	public int insertResApi(Map<String, Object> map) throws Exception {
		if("meminput".equals(map.get("svcCode"))) {
			map.put("id", map.get("user_id"));
			map.put("isrtId", map.get("user_id"));
		} else {
			map.put("id", SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "id"));
			map.put("isrtId", SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, "id"));
		}
		return resApiDAO.insertResApi(map); 
	}

}
