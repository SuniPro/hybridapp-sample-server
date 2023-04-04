package com.hybird.example.user.condores;

import com.hybird.example.cmmn.resapi.ResApiProperty;

public class CondoResValueGenerator {
    /* 콘도 도메인 */
    public static final String RESAPI_GOLF_RES_DOMAIN = ResApiProperty.getProperty("resapi.domain");
    /* 회원정보조회 */
    public static final String COM_MEMINFO = ResApiProperty.getProperty("com.meminfo.url");
    /* 회원정보등록 */
    public static final String COM_MEMINPUT = ResApiProperty.getProperty("com.meminput.url");
    /* 회원탈퇴등록 */
    public static final String COM_MEMOUT = ResApiProperty.getProperty("com.memout.url");
    /* 예약일자정보 */ 
    public static final String CONDORES_CALENDAR = ResApiProperty.getProperty("condores.calendar.url");
    /* 콘도ROOM리스트 */
    public static final String CONDORES_ROOMLIST = ResApiProperty.getProperty("condores.roomlist.url");
    /* 콘도ROOM정보 */
    public static final String CONDORES_ROOMINFO = ResApiProperty.getProperty("condores.roominfo.url");
    /* 콘도제약사항확인 */
    public static final String CONDORES_CONSTINFO = ResApiProperty.getProperty("condores.constinfo.url");
    /* 위약 기간 체크 */
    public static final String CONDORES_PUNISH = ResApiProperty.getProperty("condores.punish.url");
	/* 위약 벌점 체크 */
    public static final String CONDORES_PUNISH_POINT = ResApiProperty.getProperty("condores.punishpoint.url");
    /* 콘도ROOM정보 */
    public static final String CONDORES_ROOMAMT = ResApiProperty.getProperty("condores.roomamt.url");
    /* 예약등록 */
    public static final String CONDORES_INPUT = ResApiProperty.getProperty("condores.input.url");
    /* 예약리스트 */
    public static final String CONDORES_LIST = ResApiProperty.getProperty("condores.list.url");
    /* 예약상세내역 */
    public static final String CONDORES_INFO = ResApiProperty.getProperty("condores.info.url");
    /* 예약취소 */
    public static final String CONDORES_CANCEL = ResApiProperty.getProperty("condores.cancel.url");
    /* 예약변경 */
    public static final String CONDORES_CHANGE = ResApiProperty.getProperty("condores.change.url");


    public static String getUrl(String golfResCode) {
    	String url = RESAPI_GOLF_RES_DOMAIN;
		switch (golfResCode) {
		case "meminfo":
			url += COM_MEMINFO;
			break;
		case "meminput":
			url += COM_MEMINPUT;
			break;
		case "memout":
			url += COM_MEMOUT;
			break;
		case "calendar":
			url += CONDORES_CALENDAR;
			break;
		case "roomlist":
			url += CONDORES_ROOMLIST;
			break;
		case "roominfo":
			url += CONDORES_ROOMINFO;
			break;
		case "constinfo":
			url += CONDORES_CONSTINFO;
			break;
		case "punish":
			url += CONDORES_PUNISH;
			break;
		case "punish_point":
			url += CONDORES_PUNISH_POINT;
			break;
		case "roomamt": case "condoroomamt":
			url += CONDORES_ROOMAMT;
			break;
		case "input":
			url += CONDORES_INPUT;
			break;
		case "list":
			url += CONDORES_LIST;
			break;
		case "info":
			url += CONDORES_INFO;
			break;
		case "cancel":
			url += CONDORES_CANCEL;
			break;
		case "change":
			url += CONDORES_CHANGE;
			break;
		default:
			break;
		}

        return url;
    }
}
