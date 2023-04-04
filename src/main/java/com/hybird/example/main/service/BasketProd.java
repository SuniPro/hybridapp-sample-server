package com.hybird.example.main.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasketProd {

	@JsonIgnore
	private String memberId;
	@JsonIgnore
	private String muMpFlag;
	@JsonProperty("type")
	private String type;
	@JsonProperty("id")
	private String ticketSeq;
	@JsonProperty("cnt")
	private String ticketNo;
	@JsonProperty("np")
	private String np;
	@JsonProperty("ap")
	private String ap;
}