package com.hybird.example.user.payMent.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class DataEncrypt {
    private MessageDigest md;
    private String strSRCData = "";
    private String strENCData = "";
    private String strOUTData = "";

    public DataEncrypt(){ }
    public String encrypt(String strData){
        String passACL = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(strData.getBytes());
            byte[] raw = md.digest();
            passACL = encodeHex(raw);
        } catch(Exception e) {
            System.out.print("암호화 에러" + e.toString());
        }
        return passACL;
    }

    public String encodeHex(byte [] b){
        char [] c = Hex.encodeHex(b);
        return new String(c);
    }
}
