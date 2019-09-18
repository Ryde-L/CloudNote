package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.service.ShareService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        String pwd = request.getParameter("pwd");
        int limitType = Integer.parseInt(request.getParameter("limit_type"));
        int limitContent = Integer.parseInt(request.getParameter("limit_content"));
        return shareService.create(shareLinkPrefix,userId, noteId, isHasPwd, pwd, limitType, limitContent);
    }

    /**
     * 打开分享链接
     */
    @RequestMapping(value = {"/open"})
    public ResponseDto openShareLink(@RequestParam("pwd") String pwd, @RequestParam("link_suffix") String linkSuffix) {
        return shareService.openShare(shareLinkPrefix + linkSuffix, pwd);
    }

    /**
     * 取消分享
     */
    @RequestMapping(value = {"/cancel"})
    public ResponseDto cancel( HttpSession session,@RequestParam("share_id") Integer shareId) {
        return shareService.cancel((Integer) session.getAttribute("userId"),shareId);
    }

}