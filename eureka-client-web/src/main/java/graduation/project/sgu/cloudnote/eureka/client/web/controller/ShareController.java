package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.service.ShareService;
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
    public ResponseDto add(HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        Integer noteId = Integer.valueOf(request.getParameter("note_id"));
        int isHasPwd = Integer.parseInt(request.getParameter("is_has_pwd"));
        String pwd = null;
        if (isHasPwd==1) pwd= UUID.randomUUID().toString().substring(0,4);
        else pwd=null;

        int limitType =0;
        int limitContent =0;
        int days=Integer.parseInt(request.getParameter("days"));
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
    public ResponseDto cancel( HttpSession session,@RequestParam("share_id") Integer shareId) {
        return shareService.cancel((Integer) session.getAttribute("userId"),shareId);
    }

    /**
     * 转存分享到自己的笔记本
     */
    @RequestMapping(value = {"/save"})
    public ResponseDto save( HttpSession session,@RequestParam("note_book_id") Integer noteBookId,@RequestParam("pwd") String pwd, @RequestParam("link_suffix")String linkSuffix){
        return shareService.save((Integer) session.getAttribute("userId"),noteBookId, shareLinkPrefix + linkSuffix,  pwd);
    }
}