package com.hybird.example.user.golfres;

import com.hybird.example.cmmn.resapi.ResApiProperty;

public class GolfResValueGenerator {
    /* 골프 도메인 */
    public static final String RESAPI_GOLF_RES_DOMAIN = ResApiProperty.getProperty("resapi.domain");
    /* 회원정보조회 */
    public static final String COM_MEMINFO = ResApiProperty.getProperty("com.meminfo.url");
    /* 예약일자정보 */
    public static final String GOLFRES_CALENDAR = ResApiProperty.getProperty("golfres.calendar.url");
    /* 예약시간정보 */
    public static final String GOLFRES_TIMELIST = ResApiProperty.getProperty("golfres.timelist.url");
    /* 위약체크 */
    public static final String GOLFRES_PUNISH = ResApiProperty.getProperty("golfres.punish.url");
    /* 블랙리스트체크 */
    public static final String GOLFRES_BLACKLIST = ResApiProperty.getProperty("golfres.blacklist.url");
    /* 예약등록 */
    public static final String GOLFRES_INPUT = ResApiProperty.getProperty("golfres.input.url");
    /* 예약상세내역 */
    public static final String GOLFRES_INFO = ResApiProperty.getProperty("golfres.info.url");
    /* 예약리스트 */
    public static final String GOLFRES_LIST = ResApiProperty.getProperty("golfres.list.url");
    /* 예약정보변경 */
    public static final String GOLFRES_MOD = ResApiProperty.getProperty("golfres.mod.url");
    /* 예약변경 */
    public static final String GOLFRES_CHANGE = ResApiProperty.getProperty("golfres.change.url");
    /* 예약취소 */
    public static final String GOLFRES_CANCEL = ResApiProperty.getProperty("golfres.cancel.url");
    /* 위임가능여부확인 */
    public static final String GOLFRES_REFABLE = ResApiProperty.getProperty("golfres.refable.url");
    /* 위임등록 */
    public static final String GOLFRES_REFINPUT = ResApiProperty.getProperty("golfres.refinput.url");
    /* 위임취소 */
    public static final String GOLFRES_REFCANCEL = ResApiProperty.getProperty("golfres.refcancel.url");


    public static String getUrl(String golfResCode) {
    	String url = RESAPI_GOLF_RES_DOMAIN;
		switch (golfResCode) {
		case "meminfo":
			url += COM_MEMINFO;
			break;
		case "calendar":
			url += GOLFRES_CALENDAR;
			break;
		case "timelist":
			url += GOLFRES_TIMELIST;
			break;
		case "punish":
			url += GOLFRES_PUNISH;
			break;
		case "blacklist":
			url += GOLFRES_BLACKLIST;
			break;
		case "input":
			url += GOLFRES_INPUT;
			break;
		case "info":
			url += GOLFRES_INFO;
			break;
		case "list":
			url += GOLFRES_LIST;
			break;
		case "mod":
			url += GOLFRES_MOD;
			break;
		case "change":
			url += GOLFRES_CHANGE;
			break;
		case "cancel":
			url += GOLFRES_CANCEL;
			break;
		case "refable":
			url += GOLFRES_REFABLE;
			break;
		case "refinput":
			url += GOLFRES_REFINPUT;
			break;
		case "refcancel":
			url += GOLFRES_REFCANCEL;
			break;
		default:
			break;
		}

        return url;
    }
}
