package com.hybird.example.user.event.service;

import com.hybird.example.cmmn.CmmnVO;

public class UserEventVO extends CmmnVO {
	
	//공지사항 정보
	private String evntSn;				//이벤트 일련번호
	private String evntSj;				//이벤트 제목
	private String expsrSe;				//이벤트 구분
	private String expsrAt;				//사용여부
	private String expsrBgnde;			//이벤트 시작일
	private String expsrEndde;			//이벤트 끝일
	private String expsrCn;				//이벤트 내용
	private String mainPopAt;			//메인 팝업
	private String viewType;			//구분
	private String prePageParam;		//페이지구분
	private String imageType;			//이미지 수정
	private String isrtNm;				//등록자명
	private String updtNm;				//수정자 명
	private String url;					// 파일 url
	private String newFileNm;			// 파일명 
	private String boardSn;				// 이미지일련번호
	private String isrtDate;			// 등록일
	
	public String getEvntSn() {
		return evntSn;
	}
	public void setEvntSn(String evntSn) {
		this.evntSn = evntSn;
	}
	public String getEvntSj() {
		return evntSj;
	}
	public void setEvntSj(String evntSj) {
		this.evntSj = evntSj;
	}
	public String getExpsrSe() {
		return expsrSe;
	}
	public void setExpsrSe(String expsrSe) {
		this.expsrSe = expsrSe;
	}
	public String getExpsrAt() {
		return expsrAt;
	}
	public void setExpsrAt(String expsrAt) {
		this.expsrAt = expsrAt;
	}
	public String getExpsrBgnde() {
		return expsrBgnde;
	}
	public void setExpsrBgnde(String expsrBgnde) {
		this.expsrBgnde = expsrBgnde;
	}
	public String getExpsrEndde() {
		return expsrEndde;
	}
	public void setExpsrEndde(String expsrEndde) {
		this.expsrEndde = expsrEndde;
	}
	public String getExpsrCn() {
		return expsrCn;
	}
	public void setExpsrCn(String expsrCn) {
		this.expsrCn = expsrCn;
	}
	public String getMainPopAt() {
		return mainPopAt;
	}
	public void setMainPopAt(String mainPopAt) {
		this.mainPopAt = mainPopAt;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getPrePageParam() {
		return prePageParam;
	}
	public void setPrePageParam(String prePageParam) {
		this.prePageParam = prePageParam;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getIsrtNm() {
		return isrtNm;
	}
	public void setIsrtNm(String isrtNm) {
		this.isrtNm = isrtNm;
	}
	public String getUpdtNm() {
		return updtNm;
	}
	public void setUpdtNm(String updtNm) {
		this.updtNm = updtNm;
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
	public String getIsrtDate() {
		return isrtDate;
	}
	public void setIsrtDate(String isrtDate) {
		this.isrtDate = isrtDate;
	}
	
}
