package com.cloudnote.eureka.client.web.service.impl;

import com.cloudnote.eureka.client.web.service.NoteBookService;
import com.cloudnote.eureka.client.web.utils.CheckerUtil;
import com.cloudnote.eureka.client.web.utils.ResultUtil;
import com.cloudnote.eureka.client.web.dao.mapper.NoteBookMapper;
import com.cloudnote.eureka.client.web.dto.ResponseDto;
import com.cloudnote.eureka.client.web.pojo.Note;
import com.cloudnote.eureka.client.web.pojo.NoteBook;
import com.cloudnote.eureka.client.web.service.RecycleBinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 笔记本表 Service 接口实现
 * </p>
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service()
@Transactional(rollbackFor = Exception.class)
public class NoteBookServiceImpl implements NoteBookService {

    private static final Logger logger = LoggerFactory.getLogger(NoteBookServiceImpl.class);

    @Autowired
    NoteBookMapper noteBookMapper;

    @Autowired
    RecycleBinService recycleBinService;


    public int insert(NoteBook noteBook) {
        return noteBookMapper.insert(noteBook);
    }

    /**
     * 创建一个新的笔记本
     *
     * @param userId 用户id
     * @param title  笔记本标题
     * @return 返回ResponseDto
     */
    public ResponseDto createNoteBook(Integer userId, String title) {
        if (CheckerUtil.checkNull(userId)) return ResultUtil.error("获取不到用户id", null);
        if (CheckerUtil.checkNull(title)) return ResultUtil.error("笔记本标题不为空", null);
        NoteBook noteBook = new NoteBook(null, userId, title, 1);
        noteBookMapper.insert(noteBook);
        return ResultUtil.success("", noteBook);
    }

    public NoteBook getNoteBook(Integer id) {
        return noteBookMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据笔记本id获取包含笔记集合和标签集合的笔记本
     *
     * @param id 笔记本id
     * @return ResponseDto
     */
    public ResponseDto getContainNoteListWithTag(Integer id) {
        return ResultUtil.success("", noteBookMapper.selectByIdContainsNoteListWithTags(id));
    }

    /**
     * 获取用户的默认笔记本
     * @param userId 用户id
     * @return 用户默认的笔记本
     */
    public NoteBook getDefaultNoteBook(Integer userId){
        return noteBookMapper.selectDefaultNoteBook(userId);
    }

    /**
     * 删除笔记本
     * @param userId 用户id
     * @param id 笔记本id
     * @return ResponseDto
     */
    public ResponseDto remove(Integer userId,Integer id) {
        if (CheckerUtil.checkNull(id)) return ResultUtil.error("缺少参数", null);

        NoteBook noteBookWithNoteList = noteBookMapper.selectWithNoteList(id);
        List<Note> noteList = noteBookWithNoteList.getNoteList();
        Integer[] noteIds = new Integer[noteList.size()];
        for (int i = 0; i < noteIds.length; i++)
            noteIds[i] = noteList.get(i).getId();

        //删除笔记
        recycleBinService.throwNoteIntoRecycleBin(userId,noteIds);

        //删除笔记本
        noteBookMapper.deleteByPrimaryKey(id);

        return ResultUtil.success("", noteBookMapper.selectWithNoteList(id));
    }


    /**
     * 获取用户的所有笔记本和笔记
     * @param userId 用户id
     * @return ResponseDto
     */
    public ResponseDto getNoteBooksWithNoteList(Integer userId){
        return ResultUtil.success("", noteBookMapper.selectUserNoteBooksWithNoteList(userId));
    }

    /**
     * 获取用户的所有笔记本
     * @param userId 用户id
     * @return ResponseDto
     */
    public ResponseDto getNoteBooks(Integer userId){
        return ResultUtil.success("", noteBookMapper.selectUserNoteBooksWithNoteList(userId));
    }



}