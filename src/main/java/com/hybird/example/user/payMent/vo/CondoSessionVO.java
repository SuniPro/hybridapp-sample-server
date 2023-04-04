package com.hybird.example.user.payMent.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class CondoSessionVO implements Serializable {
    private String userId; //아이디
    private Map<String, Object> orderCondoMap; //주문정보
    private Map<String, Object> niceCertMap; //nicePay 리턴값
}
