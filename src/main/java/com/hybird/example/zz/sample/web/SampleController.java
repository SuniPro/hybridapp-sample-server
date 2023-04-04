package com.hybird.example.zz.sample.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hybird.example.cmmn.CmmnController;
import com.hybird.example.cmmn.fileUtil.service.CmmnFileSaveService;
import com.hybird.example.cmmn.script.service.ScriptService;
import com.hybird.example.zz.sample.service.SampleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hybird.example.cmmn.SessionUtil;
import com.hybird.example.zz.sample.service.SampleService;

@Controller
@RequestMapping("/user/sample")
public class SampleController extends CmmnController {
	
	@Resource(name = "CmmnFileSaveService")
	private CmmnFileSaveService cmmnFileSaveService;
	
	@Resource(name = "ScriptService")
	private ScriptService scriptService;
	
	@Resource(name = "SampleService")
	private SampleService  sampleService;
	
	@RequestMapping("/sample.do")
	public String samplePage() throws Exception {
		System.out.println("######################## :: sample page");
		return "user:/sample/sample";
	}
	
	@RequestMapping("/index.do")
	public String indexPage() throws Exception {
		System.out.println("######################## :: index page");
		return "user:/sample/index";
	}
	
	@RequestMapping("/test.ajax")
	public String test(HttpServletRequest request, Model model) throws Exception {
//		List<Object> list = (List<Object>) scriptService.selectCmmnCdList(null);
		
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("list", list);
		map.put("test", "test");
		
		model.addAttribute("result", map);
//		model.addAttribute("result", scriptService.getMngrDomainPropertie(request.getServerName()));
		
		return "jsonView";
	}
	
	
	@RequestMapping("/sampleFileUpload.do")
	public String sampleFileUpload() throws Exception {
		return "user:/sample/sampleFileUpload";
	}
	
	@RequestMapping(value = "/testFileSave.ajax")
	public String testFileSave(@ModelAttribute("SampleVO") SampleVO vo, @RequestParam("fileUpload") MultipartFile file, Model model, HttpServletRequest request) throws Exception {
		logger.info("단일업로드 >>>>>");
		Map<String, Object> fileMap = new HashMap<String, Object>();
		
		fileMap = cmmnFileSaveService.saveFile(file, null);
		
		logger.info(fileMap);
		
		sampleService.noticeInsert(fileMap,vo);
		
		return "jsonView";
	}
	
	@RequestMapping(value = "/testFileMultiSave.ajax")
	public String testFileMultiSave(@ModelAttribute("SampleVO") SampleVO vo, @RequestParam("fileUpload") MultipartFile[] file, Model model,HttpServletRequest request) throws Exception {
		logger.info("다중업로드 >>>>>");
		List<Map<String, Object>> fileMap = new ArrayList<Map<String, Object>>();
		String rootUrl = request.getRequestURL().toString().replace(request.getRequestURI(),"")+"/";
		fileMap = cmmnFileSaveService.saveFileMulti(file, rootUrl);
		vo.setIsrtIp(SessionUtil.getUserIp());
		vo.setUpdtIp(SessionUtil.getUserIp());
		
//		sampleService.noticeInsertMulti(fileMap,vo);
		
		return "jsonView";
	}
	
}
