package com.hybird.example.main.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class CommonDomain implements Serializable {
	private String ISRT_ID;
	private Date ISRT_DATE;
	private String UPDT_ID;
	private Date UPDT_DATE;
}