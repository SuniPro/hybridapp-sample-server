package com.hybird.example.cmmn.script.service.impl;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hybird.example.cmmn.script.service.EmailVO;
import com.hybird.example.cmmn.script.service.ScriptService;
import com.hybird.example.cmmn.script.service.SmsAtalk;
import com.hybird.example.cmmn.script.service.SmsCertVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hybird.example.cmmn.CmmnConstant;
import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.cmmn.StringUtil;
import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;

@Service(value="ScriptService")
public class ScriptServiceImpl implements ScriptService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "ScriptDAO")
	private ScriptDAO scriptDAO;
	
	@Override
	public int smsCert(SmsCertVO vo) throws Exception {
		int result = 0;

		try {

			String RandomNumber = StringUtil.numberGen(6,2); 
			
	        //메시지 저장
	        vo.setTranMsg(MessageManager.getIGMessage("script.sucs.sms", RandomNumber));
	        vo.setTranCallback(CmmnConstant.TRAN_CALLBACK);
	        vo.setIsrtIp(SessionUtil.getUserIp());
	        vo.setCertNum(RandomNumber);
			result = scriptDAO.smsCert(vo);
			//callback 전화번호
			scriptDAO.smsSand(vo);
			
		} catch (Exception e) {
			logger.error("smsCert", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.insert", null));
		}

		return result;
	}
	
	@Override
	public int smsCertSameMatch(SmsCertVO vo) throws Exception {
		int result = 0;

		result = scriptDAO.smsCertSameMatch(vo);
		if(result <= 0) {
			result = 0;
//			throw new CmmnUserException(MessageManager.getIGMessage("script.fail.sms.end", null));
		}else {
			try {
				scriptDAO.smsCertSameMatchUpdate(vo);
				result = 1;
				
			} catch (Exception e) {
				logger.error("smsCertSameMatch", e);
				throw new CmmnUserException(MessageManager.getIGMessage("server.fail.insert", null));
			}
		}		
		return result;
	}
	
	@Override
	public int mmsSand(SmsCertVO vo) throws Exception {
		int result = 0;
		
		try {
			
			//콜백 번호
			vo.setTranCallback(CmmnConstant.TRAN_CALLBACK);
			//callback 전화번호
			scriptDAO.mmsSand(vo);
			
		} catch (Exception e) {
			logger.error("smsCert", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.insert", null));
		}
		
		return result;
	}

	@Override
	public int emailSand(EmailVO vo) throws Exception {
		
		int result = 0;
		
		Properties props = System.getProperties();

          props.put("mail.transport.protocol", "smtp");
          props.put("mail.smtp.host", "192.168.6.4"); // host
          props.put("mail.smtp.port", "587");
          props.put("mail.smtp.auth", "true"); // 인증 사용
          props.put("mail.debug", "true");

//          Session mailSession = Session.getInstance (props, 
//			  new javax.mail. Authenticator() { 
//	          protected PasswordAuthentication getPasswordAuthentication () { 
//	        	  return new PasswordAuthentication("smtpuser","S2ndm@1l*"); 
//			  } 
//		  });
          
          Authenticator auth = new Authenticator(){
	          protected PasswordAuthentication getPasswordAuthentication() {
	        	  return new PasswordAuthentication("smtpuser", "S2ndm@1l*");
	          }
          };
          String userEmail = SessionUtil.getAttributeStr("UserInfo","email");
          String kname = SessionUtil.getAttributeStr("UserInfo","kname");
          String handphone1 = SessionUtil.getAttributeStr("UserInfo","handphone1");
          String handphone2 = SessionUtil.getAttributeStr("UserInfo","handphone2");
          String handphone3 = SessionUtil.getAttributeStr("UserInfo","handphone3");
          String handphone = handphone1+"-"+handphone2+"-"+handphone3;
          Session session = Session.getDefaultInstance(props, auth);
		try {
			MimeMessage message = new MimeMessage(session);
			session.setDebug(true);
            message.setFrom(new InternetAddress("no-reply@bsbelleforet.com","no-reply"));
            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(vo.getEmail())); 
            // Subject
            message.setSubject("[벨포레 SER App] 1:1 문의가 접수되었습니다."); //메일 제목을 입력
            // Text
            message.setContent("<h2>"+vo.getSubject()+"</h2><br/>"+vo.getContents()+
            		"<br/><br/>[민원인정보]<br/>이름<br/>&nbsp;&nbsp;&nbsp;&nbsp;"+kname+"<br/>휴대폰번호<br/>&nbsp;&nbsp;&nbsp;&nbsp;"+handphone
            		+"<br/>이메일<br/>&nbsp;&nbsp;&nbsp;&nbsp;"+userEmail,"text/html; charset=euc-kr"); // HTML 형식
            
//            message.setText(vo.getContents());    //메일 내용을 입력
            
            // send the message
            Transport.send(message); ////전송
            System.out.println("message sent successfully...");
			
		} catch (Exception e) {
			logger.error("mailSand", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.insert", null));
		}
		
		return result;
	}

	@Override
	public void insertAtalkMsg(SmsAtalk vo) throws Exception {
		logger.info("저장 시작 501");
		try {
			//입력값 저장
			scriptDAO.insertAtalkMsg(vo);
		} catch (Exception e) {
			logger.error("insertAtalkMsg", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.insert", null));
		}
		logger.info("저장 완료 500");
	}


}
