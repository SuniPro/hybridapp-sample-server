package com.hybird.example.cmmn.fileUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hybird.example.cmmn.CmmnConstant;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class MakeExcelManager {

	public MakeExcelManager() {
		
	}

	public static String get_Filename() {
		SimpleDateFormat ft = new SimpleDateFormat(CmmnConstant.EXCEL_DOWN_DATE_FORMAT);
		return ft.format(new Date());
	}

	public static String get_Filename(String pre) {
		return pre + "_" + get_Filename();
	}

	/**
	 * Make Excel & Download. 
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, Map<String , Object> beans, String templateFile, String filename) {
		
		String tempPath = request.getSession().getServletContext().getRealPath(CmmnConstant.EXCEL_DOWN_TEMPLETE_DIR);
		InputStream is  = null;
		OutputStream os = null;
		Workbook resultWorkbook = null;

		try {
        	String header = request.getHeader("User-Agent");
			is	= new BufferedInputStream(new FileInputStream(tempPath + File.separator + templateFile));
			XLSTransformer 	transformer		= new XLSTransformer();
			resultWorkbook = transformer.transformXLS(is, beans);
			
			String dwonLoadNm = "";
			
			if (filename != null && !"".equals(filename)) {
				dwonLoadNm = get_Filename(filename);
			} else {
				dwonLoadNm = get_Filename();
			}

	        if (header.contains("MSIE") || header.contains("Trident")) {
	        	dwonLoadNm = URLEncoder.encode(dwonLoadNm,"UTF-8").replaceAll("\\+", "%20");
	        }
	        else {
	        	dwonLoadNm = new String(dwonLoadNm.getBytes("UTF-8"), "ISO-8859-1");
	        }
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + dwonLoadNm + ".xlsx\"");
			
			os = response.getOutputStream();
			resultWorkbook.write(os);
			os.flush();
		}
		catch (ParsePropertyException | InvalidFormatException | IOException ex) {
			ex.printStackTrace();
		}
		finally {
			
			try {
				
				if(os != null) {
					os.close();
				}
				if(resultWorkbook != null) {
					resultWorkbook.close();
				}
				if(is != null) {
					is.close();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Make Excel & Download. 
	 */
	@SuppressWarnings({ "unchecked", "resource", "deprecation" })
	public static void downloadBig(HttpServletRequest request, HttpServletResponse response, Map<String , Object> beans, String templateFile, String filename) {
		
		String tempPath = request.getSession().getServletContext().getRealPath(CmmnConstant.EXCEL_DOWN_TEMPLETE_DIR);
		ServletOutputStream sos = null;
		// SXSSFWorkbook 해당 객체는 write only 이므로 읽기 불가능
		SXSSFWorkbook workbook = null;
		SXSSFSheet sheet = null;
		SXSSFRow row = null;
		SXSSFCell cell = null;
		
		try {
			
			String header = request.getHeader("User-Agent");
			String dwonLoadNm = "";
			List<String> keyList = new ArrayList<String> ();
			
			// 템플릿 엑셀 파일을 읽어온다 
			OPCPackage opcPackage = OPCPackage.open(new File(tempPath + File.separator + templateFile));
			// 워크북 생성
			XSSFWorkbook template = new XSSFWorkbook(opcPackage); //Excel 2007 이상인 경우
			// 0번째 시트를 얻어온다
			Sheet st = template.getSheetAt(0);
			   
			workbook = new SXSSFWorkbook(10000);
			sheet = workbook.createSheet("sheet1");

			// 템플릿의 머지 확인하여 새로 생성할 셀에 병합처리
			for(int i=0; i<st.getNumMergedRegions(); i++) {
				
				CellRangeAddress region = st.getMergedRegion(i);
				int fstRow = region.getFirstRow();
				int lstRow = region.getLastRow();
				int fstCol = region.getFirstColumn();
				int lstCol = region.getLastColumn();
				
				sheet.addMergedRegion(new CellRangeAddress(fstRow, lstRow, fstCol, lstCol));
			}
			
			// 템플릿 전체 로우 수를 가져온다
			int rowCnt = st.getPhysicalNumberOfRows();
			
			// 템플릿에 설정된 값들을 채운다
			for(int i=0; i<rowCnt; i++) {
				
				Row readRow = st.getRow(i);

				if(readRow != null) {
					
					// 템플릿의 셀갯수 얻어오기
					int cellCnt = readRow.getPhysicalNumberOfCells();
					
					if(i != (rowCnt - 1)) {
						row = sheet.createRow(i);
						// 템플릿의 셀 높이 얻어오기
						row.setHeightInPoints(readRow.getHeightInPoints());
					}
					for(int j=0; j<cellCnt; j++) {
						
						XSSFCell readCell = (XSSFCell) readRow.getCell(j);
						
						if(i != (rowCnt - 1)) {
							
							cell = row.createCell(j);

							// cell Style 생성
							XSSFCellStyle cs = (XSSFCellStyle) workbook.createCellStyle();
							// 텍스트 중앙정렬
							cs.setAlignment(CellStyle.ALIGN_CENTER);
							// 개행문자 사용가능 설정 추가
							cs.setWrapText(true);
							// 배경색 얻기
						    XSSFColor color = readCell.getCellStyle().getFillForegroundColorColor();
						    // 배경색이 null이 아닐 경우에만 설정
						    if(color != null) {
							    cs.setFillForegroundColor(color); 
							    cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
						    }
						    
						    Font font = readCell.getCellStyle().getFont();
						    if(font != null) {
						    	
						    	System.out.println("================[setFont][" + font.getColor() + "]");
						    	Font cellFont = workbook.createFont();
						    	cellFont.setColor(font.getColor());
						    	cellFont.setBold(font.getBold());
						    	cs.setFont(cellFont);
						    }
						    
							cell.setCellStyle(cs);
							// 템플릿의 열 너비값 세팅
							sheet.setColumnWidth(j, st.getColumnWidth(j));
							// cell 값 Set
							cell.setCellValue(readCell.getStringCellValue());
						}
						// 마지막 로우일 경우 템플릿의 키 파싱을 위해서 .. 따로 처리
						else {
							
							if(null == readCell) {
								
								keyList.add("");
							}
							else {
								
								String cellVal = readCell.getStringCellValue();
								keyList.add(cellVal);
							}
						}
					}
				}
			}
			
			System.out.println("[rowCnt][" + rowCnt + "]");
			System.out.println("[keyList][" + keyList.toString() + "]");
			
			List<EgovMap> result = (List<EgovMap>) beans.get("template");
			
			if(result != null) {

				int startIdx = rowCnt - 1;
				for (int i = 0; i < result.size(); i++) {
					
					row = sheet.createRow(startIdx);
					
					EgovMap item = result.get(i);
					
					for(int j=0; j<keyList.size(); j++) {
						
						cell = row.createCell(j);
						if("".equals(keyList.get(j))) {
							
							cell.setCellValue("");
						}
						else {
							
							String value = String.valueOf(item.get(keyList.get(j)));
							if(!"null".equals(value)) {
								cell.setCellValue(value);
							}
						}
					}
					
					startIdx++;
				}
			}

			if (filename != null && !"".equals(filename)) {
				dwonLoadNm = get_Filename(filename);
			} else {
				dwonLoadNm = get_Filename();
			}
			
	        if (header.contains("MSIE") || header.contains("Trident")) {
	        	dwonLoadNm = URLEncoder.encode(dwonLoadNm,"UTF-8").replaceAll("\\+", "%20");
	        }
	        else {
	        	dwonLoadNm = new String(dwonLoadNm.getBytes("UTF-8"), "ISO-8859-1");
	        }

			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + dwonLoadNm + ".xlsx\"");
			sos = response.getOutputStream();
			workbook.write(sos);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				workbook.dispose(); // 디스크 저장 임시 파일 삭제 ** 대용량 파일 처리를 위해
				workbook.close();
				sos.flush();
				sos.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
