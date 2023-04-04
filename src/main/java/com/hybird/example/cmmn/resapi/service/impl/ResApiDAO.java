package com.hybird.example.cmmn.resapi.service.impl;

import java.util.Map;

import com.hybird.example.cmmn.mapper.OracleMapper;

@OracleMapper("ResApiDAO")
public interface ResApiDAO {
	
	int insertResApi(Map<String, Object> map) throws Exception;

}
