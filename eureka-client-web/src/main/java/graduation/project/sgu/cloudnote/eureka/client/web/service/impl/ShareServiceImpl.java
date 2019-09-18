package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.ShareMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteContent;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Share;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.User;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteContentService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.ShareService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.UserService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.UUID;

/**
 * <p>
 * 分享表 Service 接口实现
 * </p>
 */
@Service()
@Transactional(rollbackFor = Exception.class)
public class ShareServiceImpl implements ShareService {

    @Autowired
    ShareMapper shareMapper;

    @Autowired
    UserService userService;

    @Autowired
    NoteContentService noteContentService;

    /**
     * 创建分享
     *
     * @param shareLinkPrefix 分享链接的前缀
     * @param userId       用户id
     * @param noteId       笔记id
     * @param isHasPwd     是否有密码
     * @param pwd          密码
     * @param limitType    分享的限制 0无 1天数 2次数
     * @param limitContent 限制的具体内容，结合limitType
     * @return ResponseDto
     */
    public ResponseDto create(String shareLinkPrefix,Integer userId, Integer noteId, int isHasPwd, String pwd, int limitType, int limitContent) {
        if (CheckerUtil.checkNulls(userId, noteId)) return ResultUtil.error("缺少参数");
        if (isHasPwd == 1 && CheckerUtil.checkNulls(pwd)) return ResultUtil.error("缺少分享密码");
        String link = shareLinkPrefix+Calendar.getInstance().getTime().getTime() + UUID.randomUUID().toString()+"-"+userId;
        shareMapper.insert(new Share(null, userId, noteId, link, isHasPwd, pwd, limitType, limitContent, Calendar.getInstance().getTime(), 1));
        return ResultUtil.success();
    }

    /**
     * 打开分享链接
     *
     * @param link 链接
     * @param pwd  分享密码，如果没有，可为null
     * @return ResponseDto
     */
    public ResponseDto openShare(String link, String pwd) {
        Share share = shareMapper.select(link);
        if (share == null) return ResultUtil.error("无效链接");
        if (share.getStatus() != 1) return ResultUtil.error("该分享链接已无效");
        if (share.getIshaspwd() == 1) {//分享密码校验
            if (CheckerUtil.checkNulls(pwd)) return ResultUtil.error("请输入分享密码");
            if (!pwd.equals(share.getPwd())) return ResultUtil.error("分享密码不正确");//TODO 密码加密
        }
        if (share.getLimitType() == 1) {//分享次数校验
            if (share.getLimitContent() <= 0) {
                share.setStatus(0);
                return ResultUtil.error("超过分享次数");
            }
            share.setLimitContent(share.getLimitType() - 1);
        } else if (share.getLimitType() == 2) {//分享天数校验
            if (Calendar.getInstance().getTime().getTime() > share.getCreateTime().getTime()) {
                share.setStatus(0);
                return ResultUtil.error("分享已过期");
            }
        }
        NoteContent noteContent = noteContentService.getNoteContent(share.getNoteId());
        if (noteContent==null) return ResultUtil.error("分享内容已被删除");
        //有效
        return ResultUtil.success("",noteContent.getContent());
    }

    /**
     * 取消分享
     * @param userId 用户id
     * @param shareId 分享id
     * @return ResponseDto
     */
    public ResponseDto cancel(Integer userId,Integer shareId){
        if( CheckerUtil.checkNulls(shareId)) return ResultUtil.error("缺少参数");
        User user = userService.getUser(userId);
        if (user==null) return ResultUtil.error("用户对象无效");

        Share share = shareMapper.selectByPrimaryKey(shareId);
        if (share==null) return ResultUtil.error("分享对象无效");

        if (share.getUserId()!=user.getId()) return ResultUtil.error("非法操作");
        share.setStatus(0);
        shareMapper.updateByPrimaryKey(share);
        return ResultUtil.success();

    }
}