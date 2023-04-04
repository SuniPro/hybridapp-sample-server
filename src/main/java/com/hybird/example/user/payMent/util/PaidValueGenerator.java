package com.hybird.example.user.payMent.util;

import com.hybird.example.cmmn.resapi.ResApiProperty;

public class PaidValueGenerator {
    /* 도메인 */
    public static final String RESAPI_PAID_RES_DOMAIN = ResApiProperty.getProperty("resapi.domain");
    /* 지불내역등록 */
    public static final String PAID_INPUT = ResApiProperty.getProperty("paid.input.url");
    /* 지불내역취소 */
    public static final String PAID_CANCEL = ResApiProperty.getProperty("paid.cancel.url");
    public static String getUrl(String paidCode) {
        String url = RESAPI_PAID_RES_DOMAIN;
        switch (paidCode) {
            case "input":
                url += PAID_INPUT;
                break;
            case "cancel": case "condocancel":
                url += PAID_CANCEL;
                break;
            default:
                break;
        }
        return url;
    }
}
