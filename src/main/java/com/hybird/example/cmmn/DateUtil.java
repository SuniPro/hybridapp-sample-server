package com.hybird.example.cmmn;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtil {
	//데이터 포멧참고 https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html
	public static String dateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}
