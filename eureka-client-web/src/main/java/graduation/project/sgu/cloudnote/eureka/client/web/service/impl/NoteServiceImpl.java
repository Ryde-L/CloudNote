package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.*;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteContentService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteTagService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 笔记表 Service 接口实现
 * </p>
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service()
@Transactional(rollbackFor = Exception.class)
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);


    @Autowired
    NoteBookService noteBookService;

    @Autowired
    NoteContentService noteContentService;

    @Autowired
    NoteTagService noteTagService;

    @Autowired
    NoteMapper noteMapper;


    public Note getNote(Integer id) {
        return noteMapper.selectByPrimaryKey(id);
    }
    public int delete(Integer id){
        return noteMapper.deleteByPrimaryKey(id);
    }
    public int insert(Note note){
        return noteMapper.insert(note);
    }

    /**
     * 创建笔记本
     *
     * @param noteBookId 笔记本id
     * @param title      笔记标题
     * @param content    笔记内容
     * @return ResponseDto
     */
    public ResponseDto createNote(Integer noteBookId, String title, String content) {
        NoteBook noteBook = noteBookService.getNoteBook(noteBookId);
        if (noteBook == null) return ResultUtil.error("笔记本无效");

        Note note = new Note(null, noteBookId, title);
        noteMapper.insert(note);
        noteContentService.insert(new NoteContent(null, note.getId(), content));
        return ResultUtil.success();
    }

    /**
     * 根据笔记本id获取笔记本里的笔记列表
     *
     * @param noteBookId 笔记本id
     * @return json格式字符串： {"msg":"","isSuccessful":"1","data":{"id":1,"userId":4,"title":"","deletable":0,"noteList":[{"id":1,"noteBookId":1,"title":""},]}}
     */
    public ResponseDto getNoteBookList(Integer noteBookId) {
        if (CheckerUtil.checkNull(noteBookId)) return ResultUtil.error("缺少参数");
        return ResultUtil.success("", noteBookService.getContainNoteList(noteBookId));
    }

    /**
     * 根据笔记id获取笔记里内容
     *
     * @param id 笔记id
     * @return ResponseDto
     */
    public ResponseDto getContent(Integer id) {
        if (CheckerUtil.checkNulls(id)) return ResultUtil.error("缺少参数");
        return ResultUtil.success("", noteContentService.getNoteContent(id));
    }

    /**
     * 通过笔记标签模糊匹配出笔记
     *
     * @param tag 标签
     * @return ResponseDto
     */
    public ResponseDto getNoteListByTag(String tag) {
        if (CheckerUtil.checkNulls(tag)) return ResultUtil.error("标签不为空");
        return ResultUtil.success("", noteMapper.selectByTag(tag));

    }

}