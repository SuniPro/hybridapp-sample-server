package com.hybird.example.user.payMent.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class OrderSessionVO implements Serializable {
    private String query;
    private List<OrderTicketVO> orderList;
    private Map<String, Object> completeMap;
}
