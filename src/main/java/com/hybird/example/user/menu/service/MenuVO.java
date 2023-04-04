package com.hybird.example.user.menu.service;

/**
 * 사용자 앱 메뉴목록 조회를 위한 VO
 *
 * @author
 * @sine
 * @hist
 */
public class MenuVO {

    private String  menuCd;         //  메뉴코드
    private String  menuNm;         //  메뉴명
    private String  menuUrl;        //  메뉴 URL
    private String  menuLvl;        //  메뉴 레벨
    private String  menuParntsCd;   //  상위 메뉴코드

    public String getMenuCd() {
        return menuCd;
    }

    public void setMenuCd(String menuCd) {
        this.menuCd = menuCd;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuLvl() {
        return menuLvl;
    }

    public void setMenuLvl(String menuLvl) {
        this.menuLvl = menuLvl;
    }

    public String getMenuParntsCd() {
        return menuParntsCd;
    }

    public void setMenuParntsCd(String menuParntsCd) {
        this.menuParntsCd = menuParntsCd;
    }
}   // END class
