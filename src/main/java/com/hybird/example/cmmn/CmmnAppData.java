package com.hybird.example.cmmn;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CmmnAppData {
	private Integer basket;
	private String menu;
	private String title;
}
