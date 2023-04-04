package com.hybird.example.main.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SertbFcltDetail extends CommonDomain implements Serializable {

	private Long fcltDtlSeq;

	private String fcltSeq;

	private String dtlMenu;

	private String dtlDesc;

	private String dtlOrdr;

	private String delYn;

	private String imgYn;
}