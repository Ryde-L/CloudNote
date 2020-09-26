package com.cloudnote.note.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.utils.CheckerUtil;
import com.cloudnote.common.utils.ResultUtil;
import com.cloudnote.note.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * <p>
 * 分享表 Controller 接口
 * </p>
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("share")
@PropertySource(value = "classpath:properties/url.properties",ignoreResourceNotFound = true,encoding = "UTF-8")

public class ShareController {

    @Autowired
    ShareService shareService;

    @Value("${shareLinkPrefix}")
    private String shareLinkPrefix;

    /**
     * 创建分享
     */
    @RequestMapping(value = {"/create"})
    @SaCheckLogin
    public ResponseDto add(HttpServletRequest request,
                           @RequestParam(value = "is_has_pwd",required = false) Integer isHasPwd,
                           @RequestParam(value = "days",required = false) Integer days) {
        Integer userId = Integer.valueOf ((String) request.getSession().getAttribute("userId"));
        Integer noteId = Integer.valueOf(request.getParameter("note_id"));
        if (CheckerUtil.checkNulls(isHasPwd,days))return ResultUtil.error("缺少参数");
        String pwd;
        if (isHasPwd==1) pwd= UUID.randomUUID().toString().substring(0,4);//创建随机提取码
        else pwd=null;
        int limitType =0;
        int limitContent =0;
        if (days!=0) {
            limitType = 1;
            limitContent = days;
        }
        return shareService.create(shareLinkPrefix,userId, noteId, isHasPwd, pwd, limitType, limitContent);
    }

    /**
     * 打开分享链接
     */
    @RequestMapping(value = {"/open"})
    public ResponseDto openShareLink(@RequestParam("pwd") String pwd, @RequestParam("link_suffix") String linkSuffix) {
        return shareService.getShareContent(shareLinkPrefix + linkSuffix, pwd);
    }

    /**
     * 取消分享
     */
    @RequestMapping(value = {"/cancel"})
    @SaCheckLogin
    public ResponseDto cancel( HttpSession session,@RequestParam("share_id") Integer shareId) {
        return shareService.cancel(Integer.valueOf ((String) session.getAttribute("userId")),shareId);
    }

    /**
     * 转存分享到自己的笔记本
     */
    @RequestMapping(value = {"/save"})
    @SaCheckLogin
    public ResponseDto save( HttpSession session,@RequestParam("note_book_id") Integer noteBookId,@RequestParam("pwd") String pwd, @RequestParam("link_suffix")String linkSuffix){
        return shareService.save(Integer.valueOf ((String) session.getAttribute("userId")),noteBookId, shareLinkPrefix + linkSuffix,  pwd);
    }
}