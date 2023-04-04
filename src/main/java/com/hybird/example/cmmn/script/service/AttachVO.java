package com.hybird.example.cmmn.script.service;

public class AttachVO {

	private String fileGrpSn;
	private String fileOrgNm;
	private String fileOrgExt;
	private String fileNewNm;
	private String filePath;
	private String fileSize;
	private String registId;
	private String registDt;
	private String updtId;
	private String updtDt;
	
	
	private String boardSe;		//FAQ = FAQ, 공지사항 = NOTICE, 1:1 문의 = INQRY, 시설 = EQIP, 이벤트 = EVNT
	private int boardSn;		//게시판 일련번호
	private int fileSn;			//첨부파일 일련번호
	private String orgnFileNm;	//원본 파일명
	private String newFileNm;	//신규파일명
	private String savePath;	//저장경로
	private String url;			//url
	private String useAt;		//사용여부
	private int expsrOrdr; 	//노출순서
	private String isrtId;		//입력자
	private String isrtDate;	//입력시간
	
	
	public String getFileGrpSn() {
		return fileGrpSn;
	}
	public void setFileGrpSn(String fileGrpSn) {
		this.fileGrpSn = fileGrpSn;
	}
	public int getFileSn() {
		return fileSn;
	}
	public void setFileSn(int fileSn) {
		this.fileSn = fileSn;
	}
	public String getFileOrgNm() {
		return fileOrgNm;
	}
	public void setFileOrgNm(String fileOrgNm) {
		this.fileOrgNm = fileOrgNm;
	}
	public String getFileOrgExt() {
		return fileOrgExt;
	}
	public void setFileOrgExt(String fileOrgExt) {
		this.fileOrgExt = fileOrgExt;
	}
	public String getFileNewNm() {
		return fileNewNm;
	}
	public void setFileNewNm(String fileNewNm) {
		this.fileNewNm = fileNewNm;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getRegistId() {
		return registId;
	}
	public void setRegistId(String registId) {
		this.registId = registId;
	}
	public String getRegistDt() {
		return registDt;
	}
	public void setRegistDt(String registDt) {
		this.registDt = registDt;
	}
	public String getUpdtId() {
		return updtId;
	}
	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}
	public String getUpdtDt() {
		return updtDt;
	}
	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}
	public String getBoardSe() {
		return boardSe;
	}
	public void setBoardSe(String boardSe) {
		this.boardSe = boardSe;
	}
	public int getBoardSn() {
		return boardSn;
	}
	public void setBoardSn(int boardSn) {
		this.boardSn = boardSn;
	}
	public String getOrgnFileNm() {
		return orgnFileNm;
	}
	public void setOrgnFileNm(String orgnFileNm) {
		this.orgnFileNm = orgnFileNm;
	}
	public String getNewFileNm() {
		return newFileNm;
	}
	public void setNewFileNm(String newFileNm) {
		this.newFileNm = newFileNm;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public int getExpsrOrdr() {
		return expsrOrdr;
	}
	public void setExpsrOrdr(int expsrOrdr) {
		this.expsrOrdr = expsrOrdr;
	}
	public String getIsrtId() {
		return isrtId;
	}
	public void setIsrtId(String isrtId) {
		this.isrtId = isrtId;
	}
	public String getIsrtDate() {
		return isrtDate;
	}
	public void setIsrtDate(String isrtDate) {
		this.isrtDate = isrtDate;
	}
	
	
	
}
