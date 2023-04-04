package com.hybird.example.cmmn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CmmnController {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	// 조회조건 유지시 사용
	public String getSearchParamJSON(Object param) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(param);
	}
}
