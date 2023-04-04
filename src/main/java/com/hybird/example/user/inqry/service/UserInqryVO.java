package com.hybird.example.user.inqry.service;

import com.hybird.example.cmmn.CmmnVO;

public class UserInqryVO extends CmmnVO {
	
	//답변
	private String inqrySn;				// 문의일련번호
	private String answerSj;			// 답변 제목
	private String answerCn;			// 답변 내용
	private String answerAt;			// 답변 여부
	private String answerIsrtDate;		// 답변 날짜 
	private String id;					// 아이디 
	private String inqrySe;				// 1:1구분 
	private String isrtDate;			// 문의 날짜 
	private String inqrySj;				// 문의제목 
	private String inqryCn;				// 문의내용
	private String clCd;				// 1:1구분 공통코드
	private String clNm;				// 1:1구분 코드명 
	private String isrtIp;				// 입력자 ip
	private String updtIp;				// 수정자 ip 
	
	
	public String getInqrySn() {
		return inqrySn;
	}
	public void setInqrySn(String inqrySn) {
		this.inqrySn = inqrySn;
	}
	public String getAnswerSj() {
		return answerSj;
	}
	public void setAnswerSj(String answerSj) {
		this.answerSj = answerSj;
	}
	public String getAnswerCn() {
		return answerCn;
	}
	public void setAnswerCn(String answerCn) {
		this.answerCn = answerCn;
	}
	public String getAnswerAt() {
		return answerAt;
	}
	public void setAnswerAt(String answerAt) {
		this.answerAt = answerAt;
	}
	public String getAnswerIsrtDate() {
		return answerIsrtDate;
	}
	public void setAnswerIsrtDate(String answerIsrtDate) {
		this.answerIsrtDate = answerIsrtDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInqrySe() {
		return inqrySe;
	}
	public void setInqrySe(String inqrySe) {
		this.inqrySe = inqrySe;
	}
	public String getIsrtDate() {
		return isrtDate;
	}
	public void setIsrtDate(String isrtDate) {
		this.isrtDate = isrtDate;
	}
	public String getInqrySj() {
		return inqrySj;
	}
	public void setInqrySj(String inqrySj) {
		this.inqrySj = inqrySj;
	}
	public String getInqryCn() {
		return inqryCn;
	}
	public void setInqryCn(String inqryCn) {
		this.inqryCn = inqryCn;
	}
	public String getClCd() {
		return clCd;
	}
	public void setClCd(String clCd) {
		this.clCd = clCd;
	}
	public String getClNm() {
		return clNm;
	}
	public void setClNm(String clNm) {
		this.clNm = clNm;
	}
	public String getIsrtIp() {
		return isrtIp;
	}
	public void setIsrtIp(String isrtIp) {
		this.isrtIp = isrtIp;
	}
	public String getUpdtIp() {
		return updtIp;
	}
	public void setUpdtIp(String updtIp) {
		this.updtIp = updtIp;
	}

	
	
}
