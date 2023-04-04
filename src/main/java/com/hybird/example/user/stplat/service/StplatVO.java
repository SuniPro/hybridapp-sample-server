package com.hybird.example.user.stplat.service;

import com.hybird.example.cmmn.CmmnVO;

public class StplatVO extends CmmnVO {

	private String userSn;					// 회원 코드
	private String stplatVer;				// 약관 버전
	private String stplatSe;				// 약관구분
	private String stplatSj;				// 약관제목
	private String stplatCn;				// 약관내용
	private String stplatOrdr;				// 약관순서
	private String essntlAt;				// 필수여부
	private String useAt;					// 사용여부
	private String registSn;				// 등록코드
	private String registDt;				// 메등록일시
	private String updtSn;					// 수정코드
	private String updtDt;					// 수정일시
	
	public String getUserSn() {
		return userSn;
	}
	public void setUserSn(String userSn) {
		this.userSn = userSn;
	}
	
	public String getStplatVer() {
		return stplatVer;
	}
	public void setStplatVer(String stplatVer) {
		this.stplatVer = stplatVer;
	}
	
	public String getStplatSe() {
		return stplatSe;
	}
	public void setStplatSe(String stplatSe) {
		this.stplatSe = stplatSe;
	}
	
	public String getStplatSj() {
		return stplatSj;
	}
	public void setStplatSj(String stplatSj) {
		this.stplatSj = stplatSj;
	}
	
	public String getStplatCn() {
		return stplatCn;
	}
	public void setStplatCn(String stplatCn) {
		this.stplatCn = stplatCn;
	}
	
	public String getStplatOrdr() {
		return stplatOrdr;
	}
	public void setStplatOrdr(String stplatOrdr) {
		this.stplatOrdr = stplatOrdr;
	}
	
	public String getEssntlAt() {
		return essntlAt;
	}
	public void setEssntlAt(String essntlAt) {
		this.essntlAt = essntlAt;
	}
	
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	
	public String getRegistSn() {
		return registSn;
	}
	public void setRegistSn(String registSn) {
		this.registSn = registSn;
	}
	
	public String getRegistDt() {
		return registDt;
	}
	public void setRegistDt(String registDt) {
		this.registDt = registDt;
	}
	
	public String getUpdtSn() {
		return updtSn;
	}
	public void setUpdtSn(String updtSn) {
		this.updtSn = updtSn;
	}
	
	public String getUpdtDt() {
		return updtDt;
	}
	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}
	
}
