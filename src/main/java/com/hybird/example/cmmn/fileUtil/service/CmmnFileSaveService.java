package com.hybird.example.cmmn.fileUtil.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hybird.example.cmmn.script.service.AttachVO;
import org.springframework.web.multipart.MultipartFile;

public interface CmmnFileSaveService {
	
	/***
	 * Request 던지고 List로 결과 받을시
	 * @param req
	 * @param dir
	 * @param uploadNm
	 * @return
	 * @throws IOException
	 */
	public List<Map<String, Object>>  saveFindNameMultipartFiles(HttpServletRequest req, final String dir, final String uploadNm) throws Exception;
	
	/***
	 * 업로드 후 DB에 저장하고 Key를 받을경우 1건리아도 List드림
	 * @param req
	 * @param dir
	 * @param uploadNm
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<String>>  saveFileResultKey(HttpServletRequest req, final String dir, final String uploadNm) throws Exception;
	
	/***
	 * 파일 리스트 저장
	 * @param fileList
	 * @param dir
	 * @param uploadNm
	 * @return 파일 그룹 일련번호 
	 * @throws Exception
	 */
	public String saveFileList(List<MultipartFile> fileList, final String dir, final String fileGrpSe, final String pFileGrpSn) throws Exception;
	
	/***
	 * 파일의 Contents를 꺼낼때
	 * @param path
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public String readFileContent(String path) throws Exception;
	
	/***
	 * 파일 저장 - 1건씩
	 * @param file
	 * @param sOptionalPath
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> saveFile(MultipartFile file, String sOptionalPath) throws Exception;
	
	/***
	 * 파일 저장 - 다중
	 * @param file
	 * @param sOptionalPath
	 * @return
	 * @throws IOException
	 */
	public List<Map<String, Object>> saveFileMulti(MultipartFile[] file, String sOptionalPath) throws Exception;
	
	/***
	 * 파일 삭제시
	 * @param fileUrl
	 * @throws IOException
	 */
	public void deleteFile(String fileUrl) throws Exception;
	
	/***
	 * 파일 삭제시
	 * @param fileUrl
	 * @throws IOException
	 */
	public void deleteFile(String fileGrpSn, String fileSn) throws Exception;
	
	/***
	 * 파일 삭제 전체
	 * @param fileUrl
	 * @throws IOException
	 */
	public void deleteFileAll(String fileGrpSn) throws Exception;
	
	/***
	 * 파일 목록 조회
	 * @param fileUrl
	 * @return
	 * @throws Exception
	 */
	public List<AttachVO> searchFileList(final String fileGrpSn) throws Exception;
	
}
