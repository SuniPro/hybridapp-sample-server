package com.hybird.example.user.gift.service;

import java.util.List;

public interface GiftService {

	List<GiftVO> listGift(String seUserId, String startDt, String endDt) throws Exception;
}
