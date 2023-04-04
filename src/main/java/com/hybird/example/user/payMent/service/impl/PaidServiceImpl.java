package com.hybird.example.user.payMent.service.impl;

import com.hybird.example.user.payMent.service.PaidService;
import com.hybird.example.user.payMent.service.PaidVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service("PaidService")
@Transactional(transactionManager = "txManager", readOnly = false)
public class PaidServiceImpl implements PaidService {

    @Resource(name = "PaidDAO")
    private PaidDAO paidDAO;

    /* 지불내역 등록 및 취소를 위한 데이터 조회 */
    public PaidVO selectPaid(String orderNo) throws Exception {
        return paidDAO.selectPaidInfo(orderNo);
    }

    /* 선수금 접수번호 업데이트 */
    public int updatePaidRef(Map<String, Object> map) throws Exception {
        return paidDAO.updatePaidRefOne(map);
    }

    @Override
    public String selectUserHandPhoneTwo(String resNo) throws Exception {
        return paidDAO.selectUserHandPhoneTwo(resNo);
    }
}
