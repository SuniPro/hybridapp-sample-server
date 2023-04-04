package com.hybird.example.cmmn.script.service.impl;

import com.hybird.example.cmmn.script.service.SmsAtalk;
import com.hybird.example.cmmn.script.service.SmsCertVO;
import com.hybird.example.cmmn.mapper.OracleMapper;
//import com.plnc.cmmn.sys.service.ClCdVO;

@OracleMapper("ScriptDAO")
public interface ScriptDAO {


	/***
	 * 휴대폰 이력 저장
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int smsCert(SmsCertVO vo) throws Exception;

	/***
	 * 휴대폰 번호 인증 일치여부
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int smsCertSameMatch(SmsCertVO vo) throws Exception;

	/***
	 * 휴대폰 번호 인증 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int smsCertSameMatchUpdate(SmsCertVO vo) throws Exception;

	/***
	 * SMS 발송(인증번호)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int smsSand(SmsCertVO vo) throws Exception;

	/***
	 * MMS 발송
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int mmsSand(SmsCertVO vo) throws Exception;

	/***
	 * 알림톡 발송
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void insertAtalkMsg(SmsAtalk vo) throws Exception;
}
