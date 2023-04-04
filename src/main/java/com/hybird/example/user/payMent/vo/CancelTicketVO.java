package com.hybird.example.user.payMent.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CancelTicketVO implements Serializable {
    private String orderSeq;
    private String orderDtlSeq;
    private String orderNo;
    private String memId;
    private String memName;
    private Integer sumMemAmount;
    private String txtid;
    private String limitStartDate;
    private String limitEndDate;
    private String ticketStatus;
    private String mid;
    private Integer goodsNo;
    private Integer cancelCount;
    private Integer cancelFlag;
    private Integer memAmount;
    private Integer cancelAmount;
    private String closeYn;
}
