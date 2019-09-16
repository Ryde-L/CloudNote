package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.RecycleBinMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Note;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.RecycleBin;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.RecycleBinService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Date;
import java.util.Calendar;

/**
 * <p>
  * 废纸篓表 Service 接口实现
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service()
@Transactional(rollbackFor = Exception.class)
public class RecycleBinServiceImpl implements RecycleBinService {

    private static final Logger logger = LoggerFactory.getLogger(RecycleBinServiceImpl.class);

    @Autowired
    JsonBuilder jsonBuilder;

    @Autowired
    RecycleBinMapper recycleBinMapper;

    @Autowired
    NoteService noteService;

    @Autowired
    NoteBookService noteBookService;


    /**
     * 将笔记本扔进回收站，没必要如此类型转换，在接收参数时做
     * @param noteIds 笔记id
     * @return json
     */
    @Deprecated
    public String throwNoteIntoRecycleBin(String[] noteIds){
        try {
            Integer[] noteIdsInteger=new Integer[noteIds.length];
            for (int i = 0; i <noteIdsInteger.length; i++)
                noteIdsInteger[i]=Integer.valueOf(noteIds[i]);
            return throwNoteIntoRecycleBin(noteIdsInteger);
        }catch (NumberFormatException e){
            return jsonBuilder.setValuesIntoTemplate("0","笔记放入回收站时格式转换不对").build();
        }
    }

    public String throwNoteIntoRecycleBin(Integer[] noteIds){
        Calendar toWeekLater = Calendar.getInstance();
        toWeekLater.set(Calendar.DATE, toWeekLater.get(Calendar.DATE) + 14);//两周后的时间
        if (CheckerUtil.checkNull(noteIds))
            return jsonBuilder.setValuesIntoTemplate("0", "缺少参数").build();
        try {
            for (Integer id:noteIds) {
                Note note = noteService.getNote(id);
                if (note==null)
                    return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "存在无效笔记对象").build();

                //存入回收站
                RecycleBin temp = getRecycleBin(id);
                if (temp!=null) {
                    recycleBinMapper.deleteByPrimaryKey(temp.getId());
                    temp=null;
                }

                recycleBinMapper.insert(new RecycleBin(null, note.getId(), note.getNoteBookId(), note.getTitle(), Calendar.getInstance().getTime(), toWeekLater.getTime()));
                //删除原先的笔记
                noteService.delete(id);
            }
            return jsonBuilder.createConstructor("isSuccessful", "1", "msg", "").build();

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            logger.error("笔记放入回收站时内容抛出异常：" + e.getMessage());
            e.printStackTrace();
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "笔记放入回收站时抛出异常").build();
        }
    }

    /**
     * 将笔记本从回收站恢复
     * @param noteIds 笔记id不定长参数
     * @return json
     */

    public String noteRecover(Integer userId,String ...noteIds){
        try {
            for (String ids:noteIds) {
                if (CheckerUtil.checkNull(ids))
                    return jsonBuilder.setValuesIntoTemplate("0", "缺少参数").build();

                Integer id = Integer.valueOf(ids);

                RecycleBin recycleBin = getRecycleBin(id);
                if (recycleBin==null)
                    return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "存在无效笔记对象").build();



               //冲突就强制覆盖原先的
                if(noteService.getNote(id)!=null)
                    noteService.delete(id);
                //原先的笔记本不存在了，则放入用户的默认笔记本
                if(noteBookService.getNoteBook(recycleBin.getNoteBookId())==null){
                    NoteBook defaultNoteBook = noteBookService.getDefaultNoteBook(userId);
                    if (defaultNoteBook==null)
                        return jsonBuilder.createConstructor("isSuccessful", "o", "msg", "找不到用户默认笔记本").build();
                    recycleBin.setNoteBookId(defaultNoteBook.getId());
                }
                //插回note表
                noteService.insert(new Note(recycleBin.getNoteId(), recycleBin.getNoteBookId(), recycleBin.getNoteTitle()));

                //回收站删除
                recycleBinMapper.deleteByPrimaryKey(recycleBin.getId());
            }
            return jsonBuilder.createConstructor("isSuccessful", "1", "msg", "").build();

        }catch (NumberFormatException e){
            return jsonBuilder.setValuesIntoTemplate("0","笔记放入回收站时格式转换不对").build();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            logger.error("笔记放入回收站时内容抛出异常：" + e.getMessage());
            e.printStackTrace();
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "笔记放入回收站时抛出异常").build();
        }
    }

    /**
     * 根据笔记id获取RecycleBin
     * @param noteId 笔记id
     * @return RecycleBin对象
     */
    public RecycleBin getRecycleBin(Integer noteId){
        return recycleBinMapper.selectByNoteId(noteId);
    }

}