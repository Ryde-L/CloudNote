package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteBookMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.*;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteContentService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteTagService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
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
    JsonBuilder jsonBuilder;

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
     * @return json
     */
    public String createNote(Integer noteBookId, String title, String content) {
        try {

            NoteBook noteBook = noteBookService.getNoteBook(noteBookId);
            if (noteBook == null)
                return jsonBuilder.setValuesIntoTemplate("0", "笔记本无效").build();

            Note note = new Note(null, noteBookId, title);
            noteMapper.insert(note);
            noteContentService.insert(new NoteContent(null, note.getId(), content));
            return jsonBuilder.setValuesIntoTemplate("1", "").build();

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            logger.error("创建笔记抛出异常：" + e.getMessage());
            e.printStackTrace();
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "抛出异常").build();
        }
    }

    public String createNote(Map m) {
        if (CheckerUtil.checkNull(m))
            return jsonBuilder.setValuesIntoTemplate("0", "缺少参数").build();
        Object note_book_id_obj = m.get("note_book_id");
        Object title_obj = m.get("title");
        Object content_obj = m.get("content");
        Integer note_book_id = null;
        String title = null;
        String content = null;

        if (CheckerUtil.checkNulls(note_book_id_obj, title_obj, content_obj))
            return jsonBuilder.setValuesIntoTemplate("0", "缺少参数").build();
        try {
            note_book_id = Integer.valueOf(((String) note_book_id_obj));
            title = ((String) title_obj);
            content = ((String) content_obj);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return jsonBuilder.setValuesIntoTemplate("0", "类型转换出错").build();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonBuilder.setValuesIntoTemplate("0", "抛出异常").build();
        }
        return createNote(note_book_id, title, content);
    }


    /**
     * 根据笔记本id获取笔记本里的笔记列表
     *
     * @param noteBookId 笔记本id
     * @return json格式字符串： {"msg":"","isSuccessful":"1","data":{"id":1,"userId":4,"title":"","deletable":0,"noteList":[{"id":1,"noteBookId":1,"title":""},]}}
     */
    public String getNoteBookList(String noteBookId) {
        try {
            if (CheckerUtil.checkNull(noteBookId))
                return jsonBuilder.setValuesIntoTemplate("0", "缺少参数").build();
            Integer noteBookIdInt = Integer.valueOf(noteBookId);
            return jsonBuilder
                    .setValuesIntoTemplate("1", "")
                    .add("data", noteBookService.getContainNoteList(noteBookIdInt))
                    .build();
        } catch (NumberFormatException e) {
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "获取笔记列表时格式转换出错").build();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            logger.error("获取笔记列表抛出异常：" + e.getMessage());
            e.printStackTrace();
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "抛出异常").build();
        }
    }

    /**
     * 根据笔记id获取笔记里内容
     *
     * @param noteId 笔记id
     * @return json
     */
    public String getContent(String noteId) {
        try {
            if (CheckerUtil.checkNull(noteId))
                return jsonBuilder.setValuesIntoTemplate("0", "缺少参数").build();
            Integer id = Integer.valueOf(noteId);
            return jsonBuilder
                    .setValuesIntoTemplate("1", "")
                    .add("data", noteContentService.getNoteContent(id))
                    .build();
        } catch (NumberFormatException e) {
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "获取笔记内容时格式转换出错").build();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            logger.error("获取笔记内容抛出异常：" + e.getMessage());
            e.printStackTrace();
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "抛出异常").build();
        }
    }

    /**
     * 通过笔记标签模糊匹配出笔记
     *
     * @param tag 标签
     * @return json
     */
    public String getNoteListByTag(String tag) {
        try {
            if (CheckerUtil.checkNulls(tag))
                return jsonBuilder.setValuesIntoTemplate("0", "标签不为空").build();

            return jsonBuilder.
                    createConstructor("isSuccessful", "1", "msg", "")
                    .add("data", noteMapper.selectByTag(tag))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据标签查找笔记的时候抛异常");
            return jsonBuilder.setValuesIntoTemplate("0", "根据标签查找笔记的时候抛异常").build();
        }
    }




}