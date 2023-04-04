package com.hybird.example.user.myTicket.service;

import lombok.Data;

@Data
public class MyBuyTicketVO {
    private String fcltSeq;
    private String fcltNm;
    private String ticketNm;
    private String fcltClass;
    private Integer ticketLimitCnt;
    private String memType;
    private String limitStartDate;
    private String limitEndDate;
    private String weekFlag;
    private String weekFlagText;
    private String paymentQrcode;
    private String muTicketSeq;
    private String muTicketNm;
    private String muTicketShortNm;
    private String ticketCount;
    private String tkState;
    private String tkOriginCnt;
    private String tkRemainCnt;
    private String tkUsedCnt;
    private String cssStr;
    private String isPkg;
}
