package com.hybird.example.user.notice.service;

import com.hybird.example.cmmn.CmmnVO;

public class UserNoticeVO extends CmmnVO {
	
	//공지사항 정보
	private int	   noticeSn;			// 공지사항 일련번호
	private String noticeSe;			// 공지사항 구분
	private String noticeSj;			// 공지사항 제목
	private String noticeCn;			// 공지사항 내용
	private String noticeBgnde;			// 공지 시작일
	private String noticeEndde;			// 공지 종료일
	private String inqireCo;			// 조회수
	private String expsrAt;				// 노출여부
	private String useAt;				// 사용여부
	private String isrtDate;			// 등록일
	private String url;					// 파일 url
	private String newFileNm;			// 파일명 
	private String boardSn;				// 이미지일련번호
	

	
	public int getNoticeSn() {
		return noticeSn;
	}
	public void setNoticeSn(int noticeSn) {
		this.noticeSn = noticeSn;
	}
	public String getNoticeSe() {
		return noticeSe;
	}
	public void setNoticeSe(String noticeSe) {
		this.noticeSe = noticeSe;
	}
	public String getNoticeSj() {
		return noticeSj;
	}
	public void setNoticeSj(String noticeSj) {
		this.noticeSj = noticeSj;
	}
	public String getNoticeCn() {
		return noticeCn;
	}
	public void setNoticeCn(String noticeCn) {
		this.noticeCn = noticeCn;
	}
	public String getNoticeBgnde() {
		return noticeBgnde;
	}
	public void setNoticeBgnde(String noticeBgnde) {
		this.noticeBgnde = noticeBgnde;
	}
	public String getNoticeEndde() {
		return noticeEndde;
	}
	public void setNoticeEndde(String noticeEndde) {
		this.noticeEndde = noticeEndde;
	}
	public String getInqireCo() {
		return inqireCo;
	}
	public void setInqireCo(String inqireCo) {
		this.inqireCo = inqireCo;
	}
	public String getExpsrAt() {
		return expsrAt;
	}
	public void setExpsrAt(String expsrAt) {
		this.expsrAt = expsrAt;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public String getIsrtDate() {
		return isrtDate;
	}
	public void setIsrtDate(String isrtDate) {
		this.isrtDate = isrtDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNewFileNm() {
		return newFileNm;
	}
	public void setNewFileNm(String newFileNm) {
		this.newFileNm = newFileNm;
	}
	public String getBoardSn() {
		return boardSn;
	}
	public void setBoardSn(String boardSn) {
		this.boardSn = boardSn;
	}

	
	
	
}
