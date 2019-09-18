package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.RecycleBinMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Note;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.RecycleBin;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.RecycleBinService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * <p>
  * 废纸篓表 Service 接口实现
 * </p>
 *
 */
@Service()
@Transactional(rollbackFor = Exception.class)
public class RecycleBinServiceImpl implements RecycleBinService {

    private static final Logger logger = LoggerFactory.getLogger(RecycleBinServiceImpl.class);


    @Autowired
    RecycleBinMapper recycleBinMapper;

    @Autowired
    NoteService noteService;

    @Autowired
    NoteBookService noteBookService;


    /**
     * 将笔记本扔进回收站，没必要如此类型转换，在接收参数时做
     * @param noteIds 笔记id
     * @return ResponseDto
     */
    public ResponseDto throwNoteIntoRecycleBin(Integer[] noteIds) {
        Calendar toWeekLater = Calendar.getInstance();
        toWeekLater.set(Calendar.DATE, toWeekLater.get(Calendar.DATE) + 14);//两周后的时间
        if (CheckerUtil.checkNull(noteIds)) return ResultUtil.error("缺少参数");

        for (Integer id : noteIds) {
            Note note = noteService.getNote(id);
            if (note == null) return ResultUtil.error("存在无效笔记对象");

            RecycleBin temp = getRecycleBin(id);
            //如果回收站因某些不正常原因已存在笔记，强制覆盖
            if (temp != null) recycleBinMapper.deleteByPrimaryKey(temp.getId());
            //存入回收站
            recycleBinMapper.insert(new RecycleBin(null, note.getId(), note.getNoteBookId(), note.getTitle(), Calendar.getInstance().getTime(), toWeekLater.getTime()));
            //删除原先的笔记
            noteService.delete(id);
        }
        return ResultUtil.success();
    }

    /**
     * 将笔记本从回收站恢复
     * @param noteIds 笔记id不定长参数
     * @return ResponseDto
     */
    public ResponseDto noteRecover(Integer userId, Integer ...noteIds) {
        for (Integer id : noteIds) {
            if (CheckerUtil.checkNull(id)) return ResultUtil.error("缺少参数");

            RecycleBin recycleBin = getRecycleBin(id);
            if (recycleBin == null) return ResultUtil.error("存在无效笔记对象");

            //冲突就强制覆盖原先的
            if (noteService.getNote(id) != null)
                noteService.delete(id);
            //原先的笔记本不存在了，则放入用户的默认笔记本
            if (noteBookService.getNoteBook(recycleBin.getNoteBookId()) == null) {
                NoteBook defaultNoteBook = noteBookService.getDefaultNoteBook(userId);
                if (defaultNoteBook == null)
                    return ResultUtil.error("找不到用户默认笔记本");
                recycleBin.setNoteBookId(defaultNoteBook.getId());
            }
            //插回note表
            noteService.insert(new Note(recycleBin.getNoteId(), recycleBin.getNoteBookId(), recycleBin.getNoteTitle()));
            //回收站删除
            recycleBinMapper.deleteByPrimaryKey(recycleBin.getId());
        }
        return ResultUtil.success();
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