package com.hybird.example.cmmn.script.service;


public interface ScriptService {

	/***
	 * 휴대폰 인증 번호 발송
	 * @param serverName
	 * @return
	 * @throws Exception
	 */
	public int smsCert(SmsCertVO vo) throws Exception;
	
	/***
	 * 휴대폰번호 일치 여부
	 * @param serverName
	 * @return
	 * @throws Exception
	 */
	public int smsCertSameMatch(SmsCertVO vo) throws Exception;
	
	/***
	 * mms 발송
	 * @param serverName
	 * @return
	 * @throws Exception
	 */
	public int mmsSand(SmsCertVO vo) throws Exception;
	
	/***
	 * 메일 발송
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int emailSand(EmailVO vo) throws Exception;

	/***
	 * 알림톡 발송
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void insertAtalkMsg(SmsAtalk vo) throws Exception;
}
