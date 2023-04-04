package com.hybird.example.user.gift.service.impl;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.gift.service.GiftVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@OracleMapper("GiftDAO")
public interface GiftDAO {
	List<GiftVO> listGift(@Param("userId") String userId, @Param("startDt") String startDt, @Param("endDt") String endDt) throws Exception;
}
