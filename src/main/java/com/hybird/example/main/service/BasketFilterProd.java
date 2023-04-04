package com.hybird.example.main.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasketFilterProd {

	@JsonProperty("type")
	private String type;
	@JsonProperty("id")
	private String ticketSeq;
	@JsonProperty("cnt")
	private String ticketNo;
	@JsonIgnore
	private String np;
	@JsonIgnore
	private String ap;
}