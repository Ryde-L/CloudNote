package com.cloudnote.notebook.service.impl;

import com.cloudnote.notebook.service.NoteBookService;
import com.cloudnote.notebook.dao.mapper.NoteBookMapper;
import com.cloudnote.notebook.dto.ResponseDto;
import com.cloudnote.notebook.pojo.Note;
import com.cloudnote.notebook.pojo.NoteBook;
import com.cloudnote.notebook.utils.CheckerUtil;
import com.cloudnote.notebook.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    NoteBookMapper noteBookMapper;

    @Autowired
    RestTemplate restTemplate;


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
        List<Integer> testList=new ArrayList<>();
        for (int i = 0; i < noteIds.length; i++) {
            noteIds[i] = noteList.get(i).getId();
            testList.add(noteList.get(i).getId());
        }

        //删除笔记
        Map<String, String> orgNames = new HashMap<>();
//        String[] allIdArray = new String[]{"id1", "id2"};
        MultiValueMap<String, Object> convertVars = new LinkedMultiValueMap<>();
        convertVars.add("note_ids", noteIds);
        convertVars.add("user_id", userId);
        String json = restTemplate.postForEntity("http://binServices/recycleBin/noteThrowAway", convertVars, String.class).getBody();
        System.out.println(json);
//        String json = restTemplate.getForEntity("http://recycle-bin/recycleBin/noteThrowAway?user_id=" + userId + "note_id[]" +, String.class).getBody();
//        recycleBinService.throwNoteIntoRecycleBin(userId, noteIds);

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

    @Override
    public ResponseDto getNoteBookByNoteBookId(Integer noteBookId) {
        return ResultUtil.success("", noteBookMapper.selectByPrimaryKey(noteBookId));
    }


}