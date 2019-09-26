package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.ShareMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.*;
import graduation.project.sgu.cloudnote.eureka.client.web.service.*;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    NoteBookService noteBookService;

    @Autowired
    NoteService noteService;

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
    public ResponseDto getShareContent(String link, String pwd) {
        Share share = shareMapper.select(link);
        if (CheckerUtil.checkNulls(link)) return ResultUtil.error("链接不能为空");
        if (share == null) return ResultUtil.error("无效链接");
        if (share.getStatus() != 1) return ResultUtil.error("该分享链接已无效");
        if (share.getIshaspwd() == 1) {//分享密码校验
            if (CheckerUtil.checkNulls(pwd)) return ResultUtil.error("请输入分享密码");
            if (!pwd.equals(share.getPwd())) return ResultUtil.error("分享密码不正确");//TODO 密码加密
        }
        if (share.getLimitType() == 1) {//分享天数校验
            Calendar ExpireTime = Calendar.getInstance();
            ExpireTime.setTime(share.getCreateTime());
            ExpireTime.add(Calendar.DAY_OF_MONTH,share.getLimitContent());

            if (Calendar.getInstance().getTime().getTime() > ExpireTime.getTime().getTime()) {
                share.setStatus(0);
                return ResultUtil.error("分享已过期");
            }
        }
        Note note = noteService.getNote(share.getNoteId());
        if (note==null) return ResultUtil.error("分享内容已被删除");

        NoteContent noteContent = noteContentService.getNoteContent(share.getNoteId());
        if (noteContent==null) return ResultUtil.error("分享内容已被删除");
        //有效
        Map<String,String> map=new HashMap<String,String>();
        map.put("content",noteContent.getContent());
        map.put("title",note.getTitle());
        return ResultUtil.success("",map);
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

    /**
     * 转存分享到自己的笔记本
     * @param userId 用户id
     * @param noteBookId 笔记本id
     * @param link 分享的链接
     * @param pwd 分享的密码
     * @return ResponseDto
     */
    public ResponseDto save(Integer userId,Integer noteBookId,String link,String pwd) {

        ResponseDto shareMsg = getShareContent(link, pwd);
        if (shareMsg.getIsSuccessful().equals("0")) return ResultUtil.error(shareMsg.getMsg());
        Map<String, String> map = (Map<String, String>) shareMsg.getData();

        if (CheckerUtil.checkNulls(noteBookId)) return ResultUtil.error("缺少参数");
        NoteBook noteBook = noteBookService.getNoteBook(noteBookId);
        if (noteBook == null) return ResultUtil.error("笔记本对象无效");

        if (userId != noteBook.getUserId()) return ResultUtil.error("非法操作");

        Note myNote = new Note(null, noteBookId, map.get("title"));
        noteService.insert(myNote);
        noteContentService.insert(new NoteContent(null, myNote.getId(), map.get("content")));

        return ResultUtil.success("", myNote.getId());
    }
}