package com.hybird.example.cmmn.security.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hybird.example.cmmn.mapper.OracleMapper;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@OracleMapper("DynamicSecurityDAO")
public interface DynamicSecurityDAO {

	/***
	 * URL Base Role List
	 * @return
	 * @throws Exception
	 */
	public List<?> selectUrlBasedAuthorityList() throws Exception;
	
	/***
	 * 사용자 정보 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSecurityUserInfo(@Param("id") String userId) throws Exception;
	
	/***
	 * 관리자 정보 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSecurityMngrInfo(@Param("mngrId") String mngrId) throws Exception;
	
	/***
	 * 사용자 권한 조회
	 * @param mngrId
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSecurityUserRole(@Param("username") String username) throws Exception;
	
	/***
	 * 관리자 권한 조회
	 * @param mngrId
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSecurityMngrRole(@Param("username") String username) throws Exception;
	
	/***
	 * 사용자 계정 조회
	 * @param mngrId
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectAuthenticationUser(@Param("username") String username) throws Exception;
	
	/***
	 * 사용자 상태 조회
	 * @param mngrId
	 * @return
	 * @throws Exception
	 */
	public String selectAuthenticationUserSttu(@Param("username") String username) throws Exception;
	
	/***
	 * 사용자 비밀번호 체크
	 * @param mngrId
	 * @return
	 * @throws Exception
	 */
	public int selectSecurityUserPassword(@Param("username") String username, @Param("password") String password) throws Exception;
	
	/***
	 * 자동로그인을 위한 사용자 암호화 된 비밀번호 체크
	 * @param mngrId
	 * @return
	 * @throws Exception
	 */
	public int selectSecurityUserPasswordEncryption(@Param("username") String username, @Param("password") String password) throws Exception;
	
	/***
	 * 관리자 계정 조회
	 * @param mngrId
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectAuthenticationMngr(@Param("username") String username) throws Exception;
	
	/***
	 * 사용자의 메뉴목록 조회 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<?> selectSecurityUserMenuList(@Param("authCd") String authCd) throws Exception;
	
	/***
	 * 사용자의 나의 메뉴목록 조회 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<?> selectSecurityUserMyMenuList(@Param("authCd") String authCd) throws Exception;
	
	/***
	 * 메뉴 네비게이션 목록 조회
	 * @return
	 * @throws Exception
	 */
	public List<?> selectSecurityMenuNaviList(@Param("authCd") String authCd) throws Exception;
	
	/***
	 * 로그인 성공 내역 저장
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int insertLoginSuccess(@Param("username") String username, @Param("ip") String ip) throws Exception;
	
	/***
	 * 로그인 실패 내역 저장
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int insertLoginFailure(@Param("username") String username, @Param("ip") String ip) throws Exception;
}
