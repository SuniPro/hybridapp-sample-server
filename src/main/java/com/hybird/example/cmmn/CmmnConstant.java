package com.hybird.example.cmmn;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class CmmnConstant {
	
	/* Security Constant */
	public static final String SECURITY_ANONYMOUS_ROLE		= "ROLE_ANONYMOUS";
	
	/* System Basic */
	public static final String USER_SN 						= "userSn";
	public static final String USER_ID 						= "userId";
	public static final String USER_PWD 					= "userPwd";
	public static final String USER_NM 						= "userNm";
	public static final String MNGR_SN 						= "mngrSn";
	public static final String USER_SE 						= "userSe";

	public static final String MNGR_ID 						= "mngrId";
	public static final String MNGR_PWD 					= "mngrPwd";
	public static final String MNGR_NM 						= "mngrNm";
	public static final String AUTH_CD 						= "authCd";
	public static final String USE_YN 						= "useYn";

	public static final String USER_ANONYMOUS				= "anonymousUser";
	
	public static final String USER_DETAILS_NAME			= "username";
	public static final String USER_DETAILS_PWD				= "password";
	public static final String USER_DETAILS_ROLE			= "role";
	
	public static final String DEFAULT_Y					= "Y";
	public static final String DEFAULT_N					= "N";
	public static final String DEFAULT_SEQ					= "0";
	public static final String SESSION_USER					= "UserInfo";
	public static final String SESSION_NMBR					= "IsNmbr";
	public static final String SESSION_MNGR					= "MngrInfo";
	public static final String SESSION_MENU					= "MenuList";
	public static final String SESSION_MY_MENU				= "MyMenuList";
	public static final String SESSION_MENU_NAVI			= "MenuNaviList";
	public static final String SESSION_PETRA_TO_AES			= "gbuspbCrypt";
	
	/* Resolver Settings */
	public static final String REQUEST_ACCEPT 				= "Accept";
	public static final String REQUEST_CONTENT 				= "Content-Type";
	public static final String REQUEST_STRING_TO_JSON		= "json";
	public static final String REQUEST_STRING_TO_MULTI		= "multipart/form-data";
	public static final String REQUEST_HEADER_TO_JSON		= "application/json;charset=UTF-8";
	public static final String AJAX_ERROR_KEY				= "ajaxError";
	public static final String REQUEST_ERROR_KEY			= "reqError";
	public static final String REQUEST_MENU_LIST			= "menuUrl";
	public static final String REQUEST_AJAX_CALL			= "X-Ajax-Call";
	public static final String REQUEST_REDIRECT				= "successUrl";
	public static final String REQUEST_IS_AJAX				= "TRUE";
	public static final String SESSION_INVALID_CODE			= "3";
	public static final String PREFIX_QUERY_STRING			= "?";
	public static final String CHECK_QUERY_STRING			= "typeCode";
	
	public static final int REQUEST_CSRF_MISSING			= 901;
	public static final int DEFAUL_SESSION_TIME_OUT			= 3600;
	
	/* System Property */
	public static final String SYSTEM_JSSE_HANDSHAKE		= "jsse.enableSNIExtension";
	public static final String SYSTEM_JSSE_FALSE			= "false";
	
	/* Encoding */
	public static final String ENCODING_8859_1				= "8859_1";
	public static final String ENCODING_UTF_8				= "UTF-8";

	/* Excel Ext. */
	public static final String EXCEL_EXT_XLS				= ".XLS";
	public static final String EXCEL_EXT_XLSX				= ".XLSX";
	public static final String FILE_EXT_TXT					=".TXT";
	
	/* Upload Settings */
	public static final String UPLOAD_FILE_NM				= "UploadFile";
	public static final String UPLOAD_DEFAULT_DIR			= "default/";
	public static final String UPLOAD_ROOT_DIR				= "upload/";
	public static final String UPLOAD_EXT_INDEX				= ".";
	public static final String UPLOAD_BATCH_DIR				= "uploadBatch/";
	public static final String DOWNLOAD_BATCH_DIR			= "downloadBatch/";
	public static final String IMAGE_UPLOAD_DIR				= "userResource/upload/";
	
	public static final String FILE_ORG_NM					= "fileOrgNm";
	public static final String FILE_ORG_EXT					= "fileOrgExt";
	public static final String FILE_NEW_NM					= "fileNewNm";
	public static final String FILE_PATH					= "filePath";
	public static final String FILE_SIZE					= "fileSize";
	public static final String UPLOAD_FILE_KEY				= "insertKey";
	
	public static final String ORGN_FILE_NM					= "orgnFileNm";
	public static final String NEW_FILE_NM					= "newFileNm";
	public static final String URL							= "url";
	public static final String SAVE_PATH					= "savePath";
	public static final String EXPSR_ORDR					= "expsrOrdr";
	
	/* Download Settings */
	public static final String EXCEL_DOWN_DATE_FORMAT		= "yyyyMMddHHmmss";
	public static final String EXCEL_DOWN_TEMPLETE_DIR		= "/WEB-INF/excel/templete";
	
	/* Message Manager Settings */
	public static final String MSG_ERROR_CONTENT 			= "server.fail.send";
	public static final String MSG_KEY_ERROR 				= "server.fail.key";
	public static final String MSG_NOT_MATCHED_MEMEBER 		= "server.fail.user";
	public static final String MSG_APPLY_WAIT_MEMEBER 		= "server.fail.wait";
	public static final String MSG_AUTH_NOT_FOUND			= "server.fail.denied";
	
	public static final String MSG 							= "msg";
	public static final String MSG_FAIL 					= "fail";
	public static final String MSG_SUCCESS 					= "success";
	
	/* httpclient 모드 */
	public static final String GYTS_SERVER_MODE_RE			= "real";
	public static final String GYTS_SERVER_MODE_DE			= "dev";
	public static final String GYTS_SERVER_MODE_LO			= "local";
	
	/*송신 번호*/
	public static final String TRAN_CALLBACK				= "0439261000";
	
	/***
	 * 인증 Key 생성 (UUID 32자리)
	 * @return
	 */
	public static String getCertificationKey() {
		return String.valueOf(UUID.randomUUID()).replace("-", "");
	}
	
	/**
	 * 로그인한 사용자의 IP
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getRequestIP(HttpServletRequest request) {

		String ip = "";
		
		ip = request.getHeader("X-Forwarded-For");
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP");
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP");
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP");
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() ==  0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-Real-IP");
        }
        if (ip ==  null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr();
        }

        return ip;
	}
}
