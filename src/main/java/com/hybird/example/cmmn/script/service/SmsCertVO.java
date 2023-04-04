package com.hybird.example.cmmn.script.service;

import org.springframework.beans.factory.annotation.Value;

public class SmsCertVO {
	//sms 이력
	private String handphone1;	// 휴대폰번호1
	private String handphone2;	// 휴대폰번호2
	private String handphone3;	// 휴대폰번호3
	private String certSn;		// 인증일련번호
	private String certNum;		// 인증번호
	private String certYn;		// 인증여부
	private String certDt;		// 인증일시
	private String isrtIp;		// 등록IP
	private String isrtDate;	// 등록일시
	
	//sms 발송
	private String tranPr;			// 문자일련번호
	private String tranPhone;		// 휴대폰번호
	private String tranCallback;	// 콜백 번호
	private String tranMsg;			// 내용
	private String tranSubject;		// 제목
	private String tranDate;		// 전송요청시간
	
	
	public String getHandphone1() {
		return handphone1;
	}
	public void setHandphone1(String handphone1) {
		this.handphone1 = handphone1;
	}
	public String getHandphone2() {
		return handphone2;
	}
	public void setHandphone2(String handphone2) {
		this.handphone2 = handphone2;
	}
	public String getHandphone3() {
		return handphone3;
	}
	public void setHandphone3(String handphone3) {
		this.handphone3 = handphone3;
	}
	public String getCertSn() {
		return certSn;
	}
	public void setCertSn(String certSn) {
		this.certSn = certSn;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getCertYn() {
		return certYn;
	}
	public void setCertYn(String certYn) {
		this.certYn = certYn;
	}
	public String getCertDt() {
		return certDt;
	}
	public void setCertDt(String certDt) {
		this.certDt = certDt;
	}
	public String getIsrtIp() {
		return isrtIp;
	}
	public void setIsrtIp(String isrtIp) {
		this.isrtIp = isrtIp;
	}
	public String getIsrtDate() {
		return isrtDate;
	}
	public void setIsrtDate(String isrtDate) {
		this.isrtDate = isrtDate;
	}
	public String getTranPr() {
		return tranPr;
	}
	public void setTranPr(String tranPr) {
		this.tranPr = tranPr;
	}
	public String getTranPhone() {
		return tranPhone;
	}
	public void setTranPhone(String tranPhone) {
		this.tranPhone = tranPhone;
	}
	public String getTranCallback() {
		return tranCallback;
	}
	public void setTranCallback(String tranCallback) {
		this.tranCallback = tranCallback;
	}
	public String getTranMsg() {
		return tranMsg;
	}
	public void setTranMsg(String tranMsg) {
		this.tranMsg = tranMsg;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranSubject() {
		return tranSubject;
	}
	public void setTranSubject(String tranSubject) {
		this.tranSubject = tranSubject;
	}
	
}
