package com.hybird.example.cmmn.crypt;

import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptManager extends BCryptPasswordEncoder {

	private BCryptManager () {
		
	}
	
	private static interface Singleton {
		final BCryptManager INSTANCE = new BCryptManager();
	}
	
	public static BCryptManager getInstance() throws SQLException {
	    return Singleton.INSTANCE;
	}
}
