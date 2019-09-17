package graduation.project.sgu.cloudnote.eureka.client.web.service;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.RecycleBin;

/**
 * <p>
  * 废纸篓表 Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
public interface RecycleBinService {

    /**
     * 根据笔记id获取RecycleBin
     * @param noteId 笔记id
     * @return RecycleBin对象
     */
    public RecycleBin getRecycleBin(Integer noteId);


    /**
     * 将笔记本扔进回收站
     * @param noteIds 笔记id
     * @return json
     */
    ResponseDto throwNoteIntoRecycleBin(Integer[] noteIds);

    /**
     * 将笔记本从回收站恢复
     * @param noteIds 笔记id
     * @return json
     */

    public ResponseDto noteRecover(Integer userId,Integer ...noteIds);


}