package com.cloudnote.eureka.client.web.service.impl;

import com.cloudnote.eureka.client.web.dao.mapper.RecycleBinMapper;
import com.cloudnote.eureka.client.web.pojo.Note;
import com.cloudnote.eureka.client.web.pojo.RecycleBin;
import com.cloudnote.eureka.client.web.service.NoteBookService;
import com.cloudnote.eureka.client.web.service.NoteContentService;
import com.cloudnote.eureka.client.web.service.NoteService;
import com.cloudnote.eureka.client.web.service.RecycleBinService;
import com.cloudnote.eureka.client.web.utils.CheckerUtil;
import com.cloudnote.eureka.client.web.utils.ResultUtil;
import com.cloudnote.eureka.client.web.dto.ResponseDto;
import com.cloudnote.eureka.client.web.pojo.NoteBook;
import com.cloudnote.eureka.client.web.pojo.NoteContent;
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

    @Autowired
    NoteContentService noteContentService;


    /**
     * 将笔记本扔进回收站
     *
     * @param noteIds 笔记id
     * @return ResponseDto
     */
    public ResponseDto throwNoteIntoRecycleBin(Integer userId,Integer[] noteIds) {
        Calendar toWeekLater = Calendar.getInstance();
        toWeekLater.set(Calendar.DATE, toWeekLater.get(Calendar.DATE) + 14);//两周后的时间
        if (CheckerUtil.checkNull(noteIds)) return ResultUtil.error("缺少参数");

        for (Integer id : noteIds) {
            ResponseDto dto = noteService.getUserNoteWithNoteBookByUserIdAndNoteId(userId,id);
            Note note= (Note) dto.getData();
            if (note == null) return ResultUtil.error("存在无效笔记对象或非法操作");
            RecycleBin temp = getRecycleBin(id);
            //如果回收站因某些不正常原因已存在笔记，强制覆盖
            if (temp != null) recycleBinMapper.deleteByPrimaryKey(temp.getId());
            //存入回收站
            recycleBinMapper.insert(new RecycleBin(null, userId,note.getId(), note.getNoteBookId(), note.getTitle(), Calendar.getInstance().getTime(), toWeekLater.getTime()));
            //删除原先的笔记
            noteService.delete(id);
        }
        return ResultUtil.success();
    }

    /**
     * 彻底删除回收站里的笔记
     * @param userId 用户id
     * @param ids id数组
     * @return ResponseDto
     */
    public ResponseDto removeNotes(Integer userId,Integer[] ids) {
        //TODO 漏删
       if (CheckerUtil.checkNulls(ids)) return ResultUtil.error("缺少参数");
        for (Integer id : ids) {
            RecycleBin recycleBin = recycleBinMapper.selectByPrimaryKey(id);
            if (recycleBin==null) continue;
            if(!recycleBin.getUserId().equals(userId)) return ResultUtil.error("非法操作");
            recycleBinMapper.deleteByPrimaryKey(id);
        }
        return ResultUtil.success();
    }


    /**
     * 将笔记本从回收站恢复
     *
     * @param noteIds 笔记id不定长参数
     * @return ResponseDto
     */
    public ResponseDto noteRecover(Integer userId, Integer... noteIds) {
        for (Integer id : noteIds) {
            if (CheckerUtil.checkNull(id)) return ResultUtil.error("缺少参数");

            RecycleBin recycleBin = recycleBinMapper.selectByPrimaryKey(id);
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
            noteService.insert(new Note(recycleBin.getNoteId(), recycleBin.getNoteBookId(), recycleBin.getNoteTitle(),0,null,0,null));
            //回收站删除
            recycleBinMapper.deleteByPrimaryKey(recycleBin.getId());
        }
        return ResultUtil.success();
    }

    /**
     * 根据笔记id获取RecycleBin
     *
     * @param noteId 笔记id
     * @return RecycleBin对象
     */
    public RecycleBin getRecycleBin(Integer noteId) {
        return recycleBinMapper.selectByNoteId(noteId);
    }


    /**
     * 获取笔记详情
     * @param userId 用户id
     * @param binId 废纸篓id
     * @return ResponseDto：data是 note对象
     */
    public ResponseDto getNoteWithNoteBookAndContent(Integer userId, Integer binId) {
        if(CheckerUtil.checkNulls(binId)) return ResultUtil.error("缺少参数");
        RecycleBin recycleBin = recycleBinMapper.selectByPrimaryKey(binId);
        if (recycleBin==null) return ResultUtil.error("废纸篓里对象不存在");

        Note note=new Note();
        note.setTitle(recycleBin.getNoteTitle());
        note.setId(recycleBin.getNoteId());
        note.setNoteBookId(recycleBin.getNoteBookId());

        NoteContent noteContent = noteContentService.getNoteContent(recycleBin.getNoteId());
        if (noteContent==null) note.setContent("");
        else note.setContent(noteContent.getContent());

        NoteBook noteBook = noteBookService.getNoteBook(recycleBin.getNoteBookId());
        if (noteBook==null) noteBook=noteBookService.getDefaultNoteBook(userId);//获取默认笔记本
        note.setNoteBook(noteBook);
        return ResultUtil.success(note);
    }


    /**
     * 根据用户id获取废纸篓里的笔记集合
     * @param userId 用户id
     * @return ResponseDto data是RecycleBinList
     */
    public ResponseDto getNotesByUserId(Integer userId){
        return ResultUtil.success( recycleBinMapper.selectRecycleBinListByUserId(userId));
    }
}