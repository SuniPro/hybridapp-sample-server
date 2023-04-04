package com.hybird.example.main.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class SertbMenuPkg extends CommonDomain implements Serializable {

	private Long menuPkgSeq;
	private String menuPkgNm;
	private String muOnoffKb;
	private String muAgentCd;
	private String slCmt;
	private String limitDesc;
	private Date delYn;
}