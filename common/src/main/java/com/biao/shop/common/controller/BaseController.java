package com.biao.shop.common.controller;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName BaseController
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/11
 * @Version V1.0
 **/
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    // 属性自动绑定
    @ModelAttribute
    public void setReqRes(HttpServletRequest req, HttpServletResponse res){
        this.request = req;
        this.response = res;
        this.session = req.getSession();
    }
}
