package com.hybird.example.cmmn.fileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.hybird.example.cmmn.CmmnConstant;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ExcelUploadManager {

	/**
	 * 
	 * 엑셀파일을 읽어서 List<Map<String, Object>> 객체에 리턴한다.
	 * XLS와 XLSX 확장자를 비교한다.
	 * 
	 * @param MultipartFile, String[], int, int , boolean
	 * @return
	 * @throws IOException 
	 * 
	 */
	@SuppressWarnings({ "deprecation" })
	public static List<Map<String, Object>> getExcelDataList(MultipartFile file, String[] filedNm, int sheetIndex, int startIndex, boolean isDecimal) throws IOException {
	    
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		InputStream inputStream = null;
		Workbook workbook = null;
		
		try {
				inputStream  = file.getInputStream();
		} catch (FileNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
		}
		
		String origName = new String(file.getOriginalFilename().getBytes(CmmnConstant.ENCODING_8859_1), CmmnConstant.ENCODING_UTF_8); 
		String fileType = origName.substring(file.getOriginalFilename().lastIndexOf(".")).toUpperCase();
	
		int columnindex	=	0;
		int rows				= 	0;
		
		if(CmmnConstant.EXCEL_EXT_XLS.equals(fileType)) {
	
			try {
					workbook = new HSSFWorkbook(inputStream);
					HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(sheetIndex);
					rows = sheet.getPhysicalNumberOfRows();
	
					for(int rowindex = startIndex ; rowindex < rows ; rowindex++){
	    
						HSSFRow row = sheet.getRow(rowindex);
					
						if(row != null){
							
							Map<String, Object> columnMap = new HashMap<String, Object>();
							
							int cells = row.getPhysicalNumberOfCells();
							
							if (filedNm != null && filedNm.length > 0) {
								cells = filedNm.length;
							}
							
							for(columnindex = 0 ; columnindex < cells ; columnindex++){

								HSSFCell cell 	= 	row.getCell(columnindex);
								String 	value	=	"";

								if(cell != null ) {
									
									switch (cell.getCellType()) {
									
										case HSSFCell.CELL_TYPE_FORMULA:
										value	=	String.valueOf(cell.getCellFormula());
										break;
										
										case HSSFCell.CELL_TYPE_NUMERIC:
											value	=	String.valueOf(cell.getNumericCellValue());
											if (!isDecimal) {
												value	=	String.valueOf(Integer.parseInt(value));
											}
										break;
										
										case HSSFCell.CELL_TYPE_STRING:
											value	=	String.valueOf(cell.getStringCellValue());
										break;
										
										case HSSFCell.CELL_TYPE_BLANK:
										value	=	String.valueOf(cell.getBooleanCellValue());
										break;
										
										case HSSFCell.CELL_TYPE_ERROR:
										value	=	String.valueOf(cell.getErrorCellValue());
										break;
									}
								} 
								
								if (filedNm != null && filedNm.length > 0) {
									columnMap.put(filedNm[columnindex], value);
								} else {
									columnMap.put("col"+columnindex, value);
								}
							}
							retList.add(columnMap);
						}
					}
			} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
			}
			
		} else if(CmmnConstant.EXCEL_EXT_XLSX.equals(fileType)) {
			
			try {
					workbook = new XSSFWorkbook(inputStream);
					XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(sheetIndex);
					rows = sheet.getPhysicalNumberOfRows();
					
					for(int rowindex = startIndex ; rowindex < rows ; rowindex++) {
						
						XSSFRow row=sheet.getRow(rowindex);
						
						if(row != null){
							
							Map<String, Object> columnMap = new HashMap<String, Object>();
							
							int cells = row.getPhysicalNumberOfCells();
							
							if (filedNm != null && filedNm.length > 0) {
								cells = filedNm.length;
							}
							
							for(columnindex = 0 ; columnindex < cells ; columnindex++){
								
								XSSFCell cell	=	row.getCell(columnindex);
								String value		=	"";
								
								if(cell != null) {

									switch (cell.getCellType()){
									
										case XSSFCell.CELL_TYPE_FORMULA:
										value	=	String.valueOf(cell.getCellFormula());
										break;
										
										case XSSFCell.CELL_TYPE_NUMERIC:
											value	=	String.valueOf(cell.getNumericCellValue());
											if (!isDecimal) {
												value	=	String.valueOf((int)cell.getNumericCellValue());
											}
										break;
										
										case XSSFCell.CELL_TYPE_STRING:
										value	=	String.valueOf(cell.getStringCellValue());
										break;
										
										case XSSFCell.CELL_TYPE_BLANK:
										value		=	"";
										break;
										
										case XSSFCell.CELL_TYPE_ERROR:
										value	=	String.valueOf(cell.getErrorCellValue());
										break;
									}
								}
								
								if (filedNm != null && filedNm.length > 0) {
									columnMap.put(filedNm[columnindex], value);
								} else {
									columnMap.put("col"+columnindex, value);
								}
							}
							
							retList.add(columnMap);
						}
					}
			} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
			}
		}
		
		return retList;
	}

	@SuppressWarnings({ "deprecation" })
	public static List<EgovMap> getExcelDataListForEgovMap(MultipartFile file, String[] filedNm, int sheetIndex, int startIndex, boolean isDecimal) throws IOException {
	    
	    List<EgovMap> retList = new ArrayList<EgovMap>();
	    InputStream inputStream = null;
	    Workbook workbook = null;
	    
	    try {
	        inputStream  = file.getInputStream();
	    } catch (FileNotFoundException e) {
	        throw new RuntimeException(e.getMessage(), e);
	    }
	    
	    String origName = new String(file.getOriginalFilename().getBytes(CmmnConstant.ENCODING_8859_1), CmmnConstant.ENCODING_UTF_8); 
	    String fileType = origName.substring(file.getOriginalFilename().lastIndexOf(".")).toUpperCase();
	    
	    int columnindex	=	0;
	    int rows				= 	0;
	    
	    if(CmmnConstant.EXCEL_EXT_XLS.equals(fileType)) {
	        
	        try {
	            workbook = new HSSFWorkbook(inputStream);
	            HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(sheetIndex);
	            rows = sheet.getPhysicalNumberOfRows();
	            
	            for(int rowindex = startIndex ; rowindex < rows ; rowindex++){
	                
	                HSSFRow row = sheet.getRow(rowindex);
	                
	                if(row != null){
	                    
	                    EgovMap columnMap = new EgovMap();
	                    
	                    int cells = row.getPhysicalNumberOfCells();
	                    
	                    if (filedNm != null && filedNm.length > 0) {
	                        cells = filedNm.length;
	                    }
	                    
	                    for(columnindex = 0 ; columnindex < cells ; columnindex++){
	                        
	                        HSSFCell cell 	= 	row.getCell(columnindex);
	                        String 	value	=	"";
	                        
	                        if(cell != null ) {
	                            
	                            switch (cell.getCellType()) {
	                            
	                            case HSSFCell.CELL_TYPE_FORMULA:
	                                value	=	String.valueOf(cell.getCellFormula());
	                                break;
	                                
	                            case HSSFCell.CELL_TYPE_NUMERIC:
	                                value	=	String.valueOf(cell.getNumericCellValue());
	                                if (!isDecimal) {
	                                    value	=	String.valueOf(Integer.parseInt(value));
	                                }
	                                break;
	                                
	                            case HSSFCell.CELL_TYPE_STRING:
	                                value	=	String.valueOf(cell.getStringCellValue());
	                                break;
	                                
	                            case HSSFCell.CELL_TYPE_BLANK:
	                                value	=	String.valueOf(cell.getBooleanCellValue());
	                                break;
	                                
	                            case HSSFCell.CELL_TYPE_ERROR:
	                                value	=	String.valueOf(cell.getErrorCellValue());
	                                break;
	                            }
	                        } 
	                        
	                        if (filedNm != null && filedNm.length > 0) {
	                            columnMap.put(filedNm[columnindex], value);
	                        } else {
	                            columnMap.put("col"+columnindex, value);
	                        }
	                    }
	                    retList.add(columnMap);
	                }
	            }
	        } catch (IOException e) {
	            throw new RuntimeException(e.getMessage(), e);
	        }
	        
	    } else if(CmmnConstant.EXCEL_EXT_XLSX.equals(fileType)) {
	        
	        try {
	            workbook = new XSSFWorkbook(inputStream);
	            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(sheetIndex);
	            rows = sheet.getPhysicalNumberOfRows();
	            
	            for(int rowindex = startIndex ; rowindex < rows ; rowindex++) {
	                
	                XSSFRow row=sheet.getRow(rowindex);
	                
	                if(row != null){
	                    
	                    EgovMap columnMap = new EgovMap();
	                    
	                    int cells = row.getPhysicalNumberOfCells();
	                    
	                    if (filedNm != null && filedNm.length > 0) {
	                        cells = filedNm.length;
	                    }
	                    
	                    for(columnindex = 0 ; columnindex < cells ; columnindex++){
	                        
	                        XSSFCell cell	=	row.getCell(columnindex);
	                        String value		=	"";
	                        
	                        if(cell != null) {
	                            
	                            switch (cell.getCellType()){
	                            
	                            case XSSFCell.CELL_TYPE_FORMULA:
	                                value	=	String.valueOf(cell.getCellFormula());
	                                break;
	                                
	                            case XSSFCell.CELL_TYPE_NUMERIC:
	                                value	=	String.valueOf(cell.getNumericCellValue());
	                                if (!isDecimal) {
	                                    value	=	String.valueOf((int)cell.getNumericCellValue());
	                                }
	                                break;
	                                
	                            case XSSFCell.CELL_TYPE_STRING:
	                                value	=	String.valueOf(cell.getStringCellValue());
	                                break;
	                                
	                            case XSSFCell.CELL_TYPE_BLANK:
	                                value		=	"";
	                                break;
	                                
	                            case XSSFCell.CELL_TYPE_ERROR:
	                                value	=	String.valueOf(cell.getErrorCellValue());
	                                break;
	                            }
	                        }
	                        
	                        if (filedNm != null && filedNm.length > 0) {
	                            columnMap.put(filedNm[columnindex], value);
	                        } else {
	                            columnMap.put("col"+columnindex, value);
	                        }
	                    }
	                    
	                    retList.add(columnMap);
	                }
	            }
	        } catch (IOException e) {
	            throw new RuntimeException(e.getMessage(), e);
	        }
	    }
	    
	    return retList;
	}
}
