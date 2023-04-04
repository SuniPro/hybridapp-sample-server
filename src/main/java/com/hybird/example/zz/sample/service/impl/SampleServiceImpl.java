package com.hybird.example.zz.sample.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import com.hybird.example.cmmn.script.service.AttachVO;
import com.hybird.example.cmmn.script.service.ScriptService;
import com.hybird.example.zz.sample.service.SampleVO;
import org.springframework.stereotype.Service;

import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;
import com.hybird.example.zz.sample.service.SampleService;


@Service("SampleService")
public class SampleServiceImpl implements SampleService {

	@Resource(name = "SampleDAO")
	private SampleDAO sampleDAO;

	@Resource(name = "ScriptService")
	private ScriptService scriptService;

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());


	@Override
	public int noticeInsert(Map<String, Object> map, SampleVO vo) throws Exception {
		int result = 0;

		try {
			result = sampleDAO.noticeInsert(vo);
			AttachVO attachVO = new AttachVO();
			attachVO.setBoardSe("NOTICE");
			attachVO.setBoardSn(vo.getNoticeSn());
			attachVO.setOrgnFileNm((String)map.get("orgnFileNm"));
			attachVO.setNewFileNm((String)map.get("newFileNm"));
			attachVO.setSavePath((String)map.get("savePath"));
			attachVO.setUrl((String)map.get("url"));
			attachVO.setExpsrOrdr(1);
//			scriptService.attachFileSave(attachVO);
		} catch (Exception e) {
			logger.error("noticeInsert", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.insert", null));
		}
		return result;
	}

	@Override
	public int noticeInsertMulti(List<Map<String, Object>> listMap, SampleVO vo) throws Exception {
		int result = 0;

		try {
			result = sampleDAO.noticeInsert(vo);

			for (Map<String, Object> map : listMap) {
				AttachVO attachVO = new AttachVO();
				attachVO.setBoardSe("NOTICE");
				attachVO.setBoardSn(vo.getNoticeSn());
				attachVO.setOrgnFileNm((String)map.get("orgnFileNm"));
				attachVO.setNewFileNm((String)map.get("newFileNm"));
				attachVO.setSavePath((String)map.get("savePath"));
				attachVO.setExpsrOrdr((int)map.get("expsrOrdr"));
				attachVO.setUrl((String)map.get("url"));
//				scriptService.attachFileSave(attachVO);
			}


		} catch (Exception e) {
			logger.error("noticeInsertMulti", e);
			throw new CmmnUserException(MessageManager.getIGMessage("server.fail.insert", null));
		}
		return result;
	}

}
