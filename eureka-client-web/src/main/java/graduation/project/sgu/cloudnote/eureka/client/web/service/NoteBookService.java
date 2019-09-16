package graduation.project.sgu.cloudnote.eureka.client.web.service;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;

/**
 * <p>
  * 笔记本表 Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
public interface NoteBookService {

    int insert(NoteBook noteBook);

    /**
     * 创建一个新的笔记本
     *
     * @param userId 用户id
     * @param title  笔记本标题
     * @return {"msg":"消息内容","isSuccessful":"0|1"}
     */
    String createNoteBook(Integer userId, String title);

    NoteBook getNoteBook(Integer id);

    /**
     * 根据笔记本id获取笔记本和其所有的所有笔记列表
     *
     * @param id 笔记本id
     * @return 包含NoteList的NoteBook对象
     */
    NoteBook getContainNoteList(Integer id);

    /**
     * 获取用户的默认笔记本
     * @param userId 用户id
     * @return 用户默认的笔记本
     */
    NoteBook getDefaultNoteBook(Integer userId);

    /**
     * 删除笔记本
     * @param noteBookId 笔记本id
     * @return json
     */
    public String remove(String noteBookId);

}