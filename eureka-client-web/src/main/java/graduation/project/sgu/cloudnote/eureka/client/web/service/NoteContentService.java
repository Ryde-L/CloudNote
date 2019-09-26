package graduation.project.sgu.cloudnote.eureka.client.web.service;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteContent;

/**
 * <p>
 * 笔记内容表 Service 接口
 * </p>
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
public interface NoteContentService {
    int insert(NoteContent noteContent);
    int update(NoteContent noteContent);
    /**
     * 通过笔记id获取笔记内容
     *
     * @param noteId 笔记id
     * @return NoteContent对象
     */
    NoteContent getNoteContent(Integer noteId);

}