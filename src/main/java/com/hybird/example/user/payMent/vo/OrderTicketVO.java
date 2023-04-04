package com.hybird.example.user.payMent.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderTicketVO implements Serializable {
    private String orderSequence;
    private String orderNumber;
    private String orderDetailSequence;
    private String memberId;
    private String ticketSeq;
    private String muMpFlag;
    private Integer ticketNo;
    private String ticketNm;
    private String ticketShortNm;
    private Integer normalPrice;
    private Integer memberPrice;
    private Integer discountPrice;
    private Integer sumNormalPrice;
    private Integer sumMemberPrice;
    private Integer sumDiscountPrice;

    private String cancelYn;
    private String useYn;
}
