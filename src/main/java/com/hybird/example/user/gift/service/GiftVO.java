package com.hybird.example.user.gift.service;

import java.util.Date;

public class GiftVO {

    private int giftSeq;

    //선물 분류 코드 activity, golf, condo
    private int giftCode;

    private String memId;

    private String memNm;

    private String giftNm;

    private Date giftDate;

    private String giftReceiveDate;

    private String receiveMemNm;

    private String handPhone1;

    private String handPhone2;

    private String handPhone3;

    private Date giftBuyDate;

    private String giftLimitEnd;

    //티켓 상태 -> 0: 사용 전, 2: 부분 사용, 3: 사용완료, 4: 사용기간 만료
    private int giftState;

    //결제상태 -> 1:결제완료, 0:결제취
    private int payState;

    //사용가능횟수 -> ex) 3회권을 구매했을 때, 3회라는걸 증명하는 상수
    private int originCnt;

    //잔여횟수 -> 티켓 잔여 횟수
    private int remainCnt;

    //menu unit ... 테이블 에서 추출하는 티켓 이름
    private String muTicketNm;

    //menu unit ... 테이블에서 추출하는 티켓 디테일 3회권, 대인, 소인 등 etc
    private String muTicketShortNm;

    //admin 시설 코드 값 추출;

    //주중 사용, 주말사용, 모두 사용 가능 등의 구분자
    private char weekFlag;

    //티켓 qrcode
    private String ticketQrCode;

    /*private String giftRef1;

    private String giftRef2;*/

    private String updateId;

    private String updateIp;

    private Date updateDate;
}
