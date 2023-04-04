package com.hybird.example.main.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SertbFclt extends CommonDomain implements Serializable {

	private Long fcltSeq;

	private String fcltNm;

	private String slCmt;

	private String limitDesc;

	private String muTicketNm;

	private String muTicketShortNm;

	private String weekFlag;

	private String muTicketSeq;

	private String status;

	private Integer waitingTm;

	private String fcltClass;

	private String NP;

	private String AP;

	private String RP;

	private String GP;

	private String PP;

	private String num;

	private String url;

}