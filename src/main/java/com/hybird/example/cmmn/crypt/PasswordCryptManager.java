package com.hybird.example.cmmn.crypt;

public class PasswordCryptManager {

	public static void main(String[] args) throws Exception {
		System.out.println(BCryptManager.getInstance().encode("000000000000"));
	}
}
