package com.hybird.example.main.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PurcaseProd {
	private String mid;
	private String type;
	private List<BasketFilterProd> data = new ArrayList<>();
}