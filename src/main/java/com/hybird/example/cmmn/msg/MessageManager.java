package com.hybird.example.cmmn.msg;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;

import com.hybird.example.cmmn.CmmnConstant;

public class MessageManager {
	
	private static MessageSourceAccessor message = null;
	
	public void setMessageSourceAccessor(MessageSourceAccessor message) {
		MessageManager.message = message;
	}
		
	/***
	 * 메세지 조회
	 * 매핑 데이터는 {},{} 패턴으로 구분
	 * @param msgKey
	 * @param arrayContent
	 * @return
	 */
	public static String getIGMessage(String msgKey, String msgContent) {
		
		String resultMsg = "";
		String[] contents = null;
		
		
		if (StringUtils.isNotBlank(msgContent)) {
			
			String[] splitContent = msgContent.split(",");
			
			for (int i = 0; i < splitContent.length; i++) {
				System.out.println(splitContent[i]);
			}
			
			if (splitContent.length > 0) {
				
				int leng = splitContent.length;
				
				contents = new String[leng];
				
				for (int i = 0 ; i < splitContent.length ; i++) {
					String msgStr = splitContent[i];
					String msg = msgStr.trim().replace("{", "").replace("}", "");
					contents[i] = msg;
				}
			}
		}
		
		if (!"".equals(msgKey) && msgKey != null) {
			if (contents != null && contents.length > 0) {
				resultMsg = message.getMessage(msgKey, contents);
				System.out.println("resultMsg = " + resultMsg);
			} else {
				resultMsg = message.getMessage(msgKey);
			}
		} else {
			resultMsg = message.getMessage(CmmnConstant.MSG_KEY_ERROR);
		}
			
		return resultMsg;
	}
}
