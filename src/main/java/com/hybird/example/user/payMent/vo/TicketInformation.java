package com.hybird.example.user.payMent.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TicketInformation implements Serializable {
    private String iaOnoffKb;
    private String iaAgentCd;
    private String iaSalesDt;
    private String iaOutletCd;
    private String iaGoodsCd;
    private String iaGoodsNm;
    private String muTicketShortNm;
    private String muTicketSeq;
    private String iaGoodsTy;
    private String iaWeekTy;
    private Integer iaUnitPrice;
    private Integer iaSalesAmt;
    private String iaMemberNo;
    private String iaUseYn;
    private String iaCloseYn;
    private String iaUseDt;
    private String iaIssuedDt;
    private String iaExpireDt;
    private String iaRefundYn;
    private String iaRefundDt;
    private Integer iaRefundAmt;
    private Integer tkOriginCnt;
    private Integer tkRemainCnt;
    private String tkPkgsubCd;
    private String tkPkgsubNm;
    private String tkState;
}
