package com.hybird.example.cmmn;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CmmnVO {

	/** 현재페이지 */
	private int currPageIdx;
	private int offsetIndex;
	
	/** 페이지 전체 갯수 */
	private int totPageCnt;
	
	/** 한번에 보여질 갯수 */
	private int pageSize;
	
	/** 하단 페이지 수 */
	private int pageBottomSize;
	
	/** 목록 조회시 순번 변수 */
	private String no;
	
	/* 사용자 정보 */
	private String seUserSn;      		//사용자 일련번호
	private String seUserId;     		//사용자 아이디
	private String seUserNm;      		//사용자 명칭
	private String seUserSe;      		//사용자 구분
	private String seAuthCd;	  		//사용자 권한 코드

	private String popupTargetId;  		//팝업에서 타겟명
	private String popupCallbackFnNm;	//팝업에서 호출될 펑션명
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getOffsetIndex() {
		
		if (currPageIdx > 0) {
			offsetIndex = ((currPageIdx-1) * 10)+1;
		}
		return offsetIndex;
	}
	
	public int getCurrPageIdx() {
		return currPageIdx;
	}
	
	public void setCurrPageIdx(int currPageIdx) {
		this.currPageIdx = currPageIdx;
	}
	
	public int getTotPageCnt() {
		
		if (totPageCnt == 0) {
			totPageCnt = 1;
		}
		
		return totPageCnt;
	}
	
	public void setTotPageCnt(int totPageCnt) {
		this.totPageCnt = totPageCnt;
	}
	
	public int getPageSize() {
		
		if (pageSize == 0) {
			pageSize = 10;
		}
		if(currPageIdx != 0) {
			pageSize = currPageIdx * 10;
		}
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageBottomSize() {
		
		if (pageBottomSize == 0) {
			pageBottomSize = 10;
		}
		
		return pageBottomSize;
	}

	public void setPageBottomSize(int pageBottomSize) {
		this.pageBottomSize = pageBottomSize;
	}

	public String getSeUserSn() {
		
		if (seUserSn == null || "".equals(seUserSn)) {
			seUserSn = SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, CmmnConstant.USER_SN);
		}
		
		return seUserSn;
	}

	public void setSeUserSn(String seUserSn) {
		this.seUserSn = seUserSn;
	}

	public String getSeUserId() {
		
		if (seUserId == null || "".equals(seUserId)) {
			seUserId = SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, CmmnConstant.USER_ID);
		}
		
		return seUserId;
	}

	public void setSeUserId(String seUserId) {
		this.seUserId = seUserId;
	}

	public String getSeUserNm() {
		
		if (seUserNm == null || "".equals(seUserNm)) {
			seUserNm = SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, CmmnConstant.USER_NM);
		}
		
		return seUserNm;
	}

	public void setSeUserNm(String seUserNm) {
		this.seUserNm = seUserNm;
	}

	public String getSeUserSe() {
		
		if (seUserSe == null || "".equals(seUserSe)) {
			seUserSe = SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, CmmnConstant.USER_SE);
		}
		
		return seUserSe;
	}

	public void setSeUserSe(String seUserSe) {
		this.seUserSe = seUserSe;
	}
	
	public String getSeAuthCd() {
		
		if (seAuthCd == null || "".equals(seAuthCd)) {
			seAuthCd = SessionUtil.getAttributeStr(CmmnConstant.SESSION_USER, CmmnConstant.AUTH_CD);
		}
		
		return seAuthCd;
	}

	public void setSeAuthCd(String seAuthCd) {
		this.seAuthCd = seAuthCd;
	}

	public String getPopupTargetId() {
		return popupTargetId;
	}

	public void setPopupTargetId(String popupTargetId) {
		this.popupTargetId = popupTargetId;
	}

	public String getPopupCallbackFnNm() {
		return popupCallbackFnNm;
	}

	public void setPopupCallbackFnNm(String popupCallbackFnNm) {
		this.popupCallbackFnNm = popupCallbackFnNm;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
