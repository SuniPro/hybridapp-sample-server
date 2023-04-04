package com.hybird.example.user.mber.service;

import com.hybird.example.cmmn.CmmnVO;

public class MberVO extends CmmnVO {
	
	//사용자 정보
	private String companyCd;			// 회사구분
	private String id;					// 아이디
	private String resno;				// 고유번호
	private String password;			// 패스워드
	private String kname;				// 회원명
	private String engName;				// 회원명_영문
	private String chnName;				// 회원명_한자
	private String entryCd;				// 가입구분
	private String memberCd;			// 회원구분
	private String memberSt;			// 회원상태
	private String memberType;			// 회원타입
	private String memberNum;			// 멤버쉽번호
	private String emdNm;				// 행정동 이름
	private String sidoNm;				// 시도이름
	private String sexCd;				// 성별구분
	private String birthDay;			// 생년월일
	private String lunarCd;				// 음양
	private String homeZipCd;			// 자택우편번호
	private String homeAddr1;			// 자택주소1
	private String homeAddr2;			// 자택주소2
	private String homeTel1;			// 자택전화번호1
	private String homeTel2;			// 자택전화번호2
	private String homeTel3;			// 자택전화번호3
	private String postYn;				// 우편물수신여부
	private String handphone;			// 핸드폰
	private String handphone1;			// 핸드폰1
	private String handphone2;			// 핸드폰2
	private String handphone3;			// 핸드폰3
	private String smsYn;				// SMS 수신여부
	private String email;				// 이메일
	private String emailYn;				// 이메일수신여부
	private String isrtId;				// 입력자
	private String isrtIp;				// 입력자 아이피
	private String isrtDate;			// 입력시간
	private String updtId;				// 수정자
	private String updtIp;				// 수정자 아이피
	private String updtDate;			// 수정시간
	
	public String getCompanyCd() {
		return companyCd;
	}
	public void setCompanyCd(String companyCd) {
		this.companyCd = companyCd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getKname() {
		return kname;
	}
	public void setKname(String kname) {
		this.kname = kname;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getChnName() {
		return chnName;
	}
	public void setChnName(String chnName) {
		this.chnName = chnName;
	}
	public String getEntryCd() {
		return entryCd;
	}
	public void setEntryCd(String entryCd) {
		this.entryCd = entryCd;
	}
	public String getMemberCd() {
		return memberCd;
	}
	public void setMemberCd(String memberCd) {
		this.memberCd = memberCd;
	}
	public String getMemberSt() {
		return memberSt;
	}
	public void setMemberSt(String memberSt) {
		this.memberSt = memberSt;
	}
	public String getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}
	public String getSexCd() {
		return sexCd;
	}
	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getLunarCd() {
		return lunarCd;
	}
	public void setLunarCd(String lunarCd) {
		this.lunarCd = lunarCd;
	}
	public String getHomeZipCd() {
		return homeZipCd;
	}
	public void setHomeZipCd(String homeZipCd) {
		this.homeZipCd = homeZipCd;
	}
	public String getHomeAddr1() {
		return homeAddr1;
	}
	public void setHomeAddr1(String homeAddr1) {
		this.homeAddr1 = homeAddr1;
	}
	public String getHomeAddr2() {
		return homeAddr2;
	}
	public void setHomeAddr2(String homeAddr2) {
		this.homeAddr2 = homeAddr2;
	}
	public String getHomeTel1() {
		return homeTel1;
	}
	public void setHomeTel1(String homeTel1) {
		this.homeTel1 = homeTel1;
	}
	public String getHomeTel2() {
		return homeTel2;
	}
	public void setHomeTel2(String homeTel2) {
		this.homeTel2 = homeTel2;
	}
	public String getHomeTel3() {
		return homeTel3;
	}
	public void setHomeTel3(String homeTel3) {
		this.homeTel3 = homeTel3;
	}
	public String getPostYn() {
		return postYn;
	}
	public void setPostYn(String postYn) {
		this.postYn = postYn;
	}
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
	public String getSmsYn() {
		return smsYn;
	}
	public void setSmsYn(String smsYn) {
		this.smsYn = smsYn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailYn() {
		return emailYn;
	}
	public void setEmailYn(String emailYn) {
		this.emailYn = emailYn;
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
	public String getUpdtId() {
		return updtId;
	}
	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}
	public String getUpdtDate() {
		return updtDate;
	}
	public void setUpdtDate(String updtDate) {
		this.updtDate = updtDate;
	}
	public String getEmdNm() {
		return emdNm;
	}
	public void setEmdNm(String emdNm) {
		this.emdNm = emdNm;
	}
	public String getSidoNm() {
		return sidoNm;
	}
	public void setSidoNm(String sidoNm) {
		this.sidoNm = sidoNm;
	}
	public String getHandphone() {
		return handphone;
	}
	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getResno() {
		return resno;
	}
	public void setResno(String resno) {
		this.resno = resno;
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
