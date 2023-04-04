package com.hybird.example.main.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PurchaseDto implements Serializable {

	/* 전송구분  direct | basket */
	private String send;

	/* 상품구분 pkg | unit */

	private String type;

	/* 회원 아이디 */
	private String mid;

	private Object data;
}
