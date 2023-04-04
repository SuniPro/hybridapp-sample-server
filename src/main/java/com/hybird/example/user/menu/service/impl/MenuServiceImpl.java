package com.hybird.example.user.menu.service.impl;

import com.hybird.example.cmmn.ex.CmmnUserException;
import com.hybird.example.cmmn.msg.MessageManager;
import com.hybird.example.user.menu.service.MenuService;
import com.hybird.example.user.menu.service.MenuVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("MenuService")
public class MenuServiceImpl implements MenuService {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "MenuDAO")
	private MenuDAO menuDAO;


    /**
     * 사용자 앱 메뉴 목록 조회
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public List<MenuVO> getMobileMenuList(MenuVO vo) throws Exception {
        List<MenuVO> result ;

        try {
            result = menuDAO.getMobileMenuList(vo);

        } catch (Exception e) {
            logger.error("searchUserIdCheck", e);
            throw new CmmnUserException(MessageManager.getIGMessage("server.fail.check", null));
        }

        return result;
    }
}
