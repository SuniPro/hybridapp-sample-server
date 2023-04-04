package com.hybird.example.cmmn.ex;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.context.MessageSource;

import egovframework.rte.fdl.cmmn.exception.BaseException;

public class CmmnUserException extends BaseException{

	private static final long serialVersionUID = 4413010501303980029L;

	/**
	 * CmmnUserException 생성자.
	 * 기본 메세지 없음
	 */
	public CmmnUserException() {
		this("", null, null);
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param defaultMessage 메세지 지정
	 */
	public CmmnUserException(String defaultMessage) {
		this(defaultMessage, null, null);
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param defaultMessage 메세지 지정
	 * @param wrappedException 원인 Exception
	 */
	public CmmnUserException(String defaultMessage, Exception wrappedException) {
		this(defaultMessage, null, wrappedException);
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param defaultMessage 메세지 지정(변수지정)
	 * @param messageParameters 치환될 메세지 리스트
	 * @param wrappedException 원인 Exception
	 */
	public CmmnUserException(String defaultMessage, Object[] messageParameters, Exception wrappedException) {
		String userMessage = defaultMessage;
		
		if (messageParameters != null) {
			userMessage = MessageFormat.format(defaultMessage, messageParameters);
		}
		
		this.message = userMessage;
		this.wrappedException = wrappedException;
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 */
	public CmmnUserException(MessageSource messageSource, String messageKey) {
		this(messageSource, messageKey, null, null, Locale.getDefault(), null);
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param wrappedException 원인 Exception
	 */
	public CmmnUserException(MessageSource messageSource, String messageKey, Exception wrappedException) {
		this(messageSource, messageKey, null, null, Locale.getDefault(), wrappedException);
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param locale 국가/언어지정
	 * @param wrappedException 원인 Exception
	 */
	public CmmnUserException(MessageSource messageSource, String messageKey, Locale locale, Exception wrappedException) {
		this(messageSource, messageKey, null, null, locale, wrappedException);
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param locale 국가/언어지정
	 * @param wrappedException 원인 Exception
	 */
	public CmmnUserException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale, Exception wrappedException) {
		this(messageSource, messageKey, messageParameters, null, locale, wrappedException);
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param wrappedException 원인 Exception
	 */
	public CmmnUserException(MessageSource messageSource, String messageKey, Object[] messageParameters, Exception wrappedException) {
		this(messageSource, messageKey, messageParameters, null, Locale.getDefault(), wrappedException);
	}

	/**
	 * CmmnUserException 생성자.
	 * 
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param defaultMessage 기본 메시지
	 * @param wrappedException 원인 Exception
	 */
	public CmmnUserException(MessageSource messageSource, String messageKey, Object[] messageParameters, String defaultMessage, Exception wrappedException) {
		this(messageSource, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
	}

	/**
	 *  CmmnUserException 생성자.
	 *  
	 * @param messageSource 메세지 리소스
	 * @param messageKey 메세지키값
	 * @param messageParameters 치환될 메세지 리스트
	 * @param defaultMessage 기본 메시지
	 * @param locale 국가/언어지정
	 * @param wrappedException 원인 Exception
	 */
	public CmmnUserException(MessageSource messageSource, String messageKey, Object[] messageParameters, String defaultMessage, Locale locale, Exception wrappedException) {
		this.messageKey = messageKey;
		this.messageParameters = messageParameters;
		this.message = messageSource.getMessage(messageKey, messageParameters, defaultMessage, locale);
		this.wrappedException = wrappedException;
	}
}
