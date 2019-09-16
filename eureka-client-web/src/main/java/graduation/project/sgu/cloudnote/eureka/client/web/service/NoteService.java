package graduation.project.sgu.cloudnote.eureka.client.web.service;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Note;

import java.util.Map;

/**
 * <p>
  * 笔记表 Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
public interface NoteService {

    Note getNote(Integer id);
    int delete(Integer id);
    int insert(Note note);

    /**
     * 创建笔记本
     * @param noteBookId 笔记本id
     * @param title 笔记标题
     * @param content 笔记内容
     * @return json
     */
     String createNote(Integer noteBookId,String title,String content);

    /**
     * 创建笔记本
     * @param m 保存有笔记本id、笔记标题、笔记内容的map
     * @return json
     */
     String createNote(Map m);

    /**
     * 根据笔记本id获取笔记本里的笔记列表
     * @param noteBookId 笔记本id
     * @return json {"msg":"","isSuccessful":"1","data":{"id":1,"userId":4,"title":"","deletable":0,"noteList":[{"id":1,"noteBookId":1,"title":""},]}}
     */
     String getNoteBookList(String noteBookId);

    /**
     * 根据笔记id获取笔记里内容
     * @param noteId 笔记id
     * @return json
     */
    public String getContent(String noteId);

    /**
     * 通过笔记标签模糊匹配出笔记
     *
     * @param tag 标签
     * @return json
     */
    public String getNoteListByTag(String tag);


}