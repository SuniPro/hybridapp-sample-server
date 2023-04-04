package com.hybird.example.cmmn.script.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsAtalk {
    //알림톡 발송

    /* 공통 */
    private String tmplCd;      // 템플릿코드
    private String subject;		// 제목
    private String kName;       // 닉네임
    private String phone;       // 휴대폰번호

    /* 골프 예약 / 변경 / 취소 시에 사용 */
    private String pointDt;     // 예약일시
    private String courseName;  // 예약코스

    /* 콘도 예약 / 취소 시에 사용 */
    private String bookgNo;     // 예약번호
    private String bookgDate;   // 예약일시
    private String roomName;    // 객실타입
    private String roomRms;     // 예약객실수

    /* 관리자 벌점 발송*/
    private String userPhone;   //고객 휴대폰번호
    private String startAt;     //입실일
    private String endAt;       //취소일
    private String point;       //벌점
}
