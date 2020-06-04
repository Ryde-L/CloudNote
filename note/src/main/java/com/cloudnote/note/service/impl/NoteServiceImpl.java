package com.cloudnote.note.service.impl;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.pojo.Note;
import com.cloudnote.common.pojo.NoteContent;
import com.cloudnote.common.utils.CheckerUtil;
import com.cloudnote.common.utils.ResultUtil;
import com.cloudnote.note.dao.mapper.NoteMapper;
import com.cloudnote.note.service.*;
import com.cloudnote.note.utils.ElasticSearchUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    RestTemplate restTemplate;

    @Autowired
    NoteContentService noteContentService;

    @Autowired
    NoteTagService noteTagService;

    @Autowired
    ShareService shareService;

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    RestHighLevelClient restHighLevelClient;


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
     * 创建笔记
     *
     * @param noteBookId 笔记本id
     * @param title      笔记标题
     * @param content    笔记内容
     * @return ResponseDto
     */
    public ResponseDto createNote(Integer noteBookId, String title, String content) throws IOException {
        ResponseDto responseDto = restTemplate.getForObject("http://notebookServices/noteBook/getNoteBookByNoteBookId?note_book_id=" + noteBookId, ResponseDto.class);
        if ("1".equals(responseDto.getIsSuccessful())&&responseDto.getData()==null) return ResultUtil.error("笔记本对象无效");
        Note note = new Note(null, noteBookId, title,0,null);
        noteMapper.insert(note);
        noteContentService.insert(new NoteContent(null, note.getId(), content));
        Map<String, Object> map = new HashMap<>(1);
        map.put("content",content);
        ElasticSearchUtil.documentUpdate(restHighLevelClient,"cloud_note",String.valueOf(note.getId()),map);
        return ResultUtil.success("",String.valueOf(note.getId()));
    }

    /**
     * 更新
     * @param userId 用户id
     * @param noteId 笔记id
     * @param title 笔记标题
     * @param content 笔记内容
     * @return ResponseDto
     */
    public ResponseDto update(Integer userId,Integer noteId, String title, String content) throws IOException {
        if (CheckerUtil.checkNulls(noteId,title,content)) return ResultUtil.error("缺少参数");
        Note note = noteMapper.selectByIdWithNoteBookAndContent(noteId);
        if (note==null) return ResultUtil.error("笔记对象无效");
        if (note.getNoteBook()==null) return ResultUtil.error("笔记本对象不存在");
        if (!userId.equals(note.getNoteBook().getUserId())) return ResultUtil.error("非法操作");

        note.setTitle(title);
        noteMapper.updateByPrimaryKey(note);
        NoteContent noteContent = noteContentService.getNoteContent(noteId);
        if (noteContent==null) {
            noteContent=new NoteContent(null,noteId,content);
            noteContentService.insert(noteContent);
        }else {
            noteContent.setContent(content);
            noteContentService.update(noteContent);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("content",content);
        ElasticSearchUtil.documentUpdate(restHighLevelClient,"cloud_note",String.valueOf(note.getId()),map);

        return ResultUtil.success();
    }
    /**
     * 根据笔记本id获取笔记本里的笔记列表
     *
     * @param noteBookId 笔记本id
     * @return json格式字符串： {"msg":"","isSuccessful":"1","data":{"id":1,"userId":4,"title":"","deletable":0,"noteList":[{"id":1,"noteBookId":1,"title":""},]}}
     */
    public ResponseDto getNoteBookList(Integer noteBookId) {
        if (CheckerUtil.checkNulls(noteBookId)) return ResultUtil.error("缺少参数");
        //TODO Test
        ResponseDto responseDto = restTemplate.getForObject("http://notebookServices/noteBook/getNoteBookByNoteBookId?note_book_id=" + noteBookId, ResponseDto.class);
        return ResultUtil.success("", responseDto.getData());
    }

    /**
     * 根据笔记id获取笔记里内容
     *
     * @param id 笔记id
     * @return ResponseDto
     */
    public ResponseDto getContent(Integer id) {
        if (CheckerUtil.checkNulls(id)) return ResultUtil.error("缺少参数");
        return ResultUtil.success("", noteContentService.getNoteContent(id));
    }

    /**
     * 通过笔记关键字模糊匹配出笔记
     *
     * @param tag 标签
     * @param userId 用户Id
     * @return ResponseDto
     */
    public ResponseDto getNoteListByTag(String tag,Integer userId) throws IOException {
        if (CheckerUtil.checkNulls(tag)) return ResultUtil.error("标签不为空");
        List<Note> data=new ArrayList<>();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().
                must(QueryBuilders.matchQuery("user_id", userId)).
                must(QueryBuilders.queryStringQuery(tag).field("tag").field("content"));

        SearchHits hits = ElasticSearchUtil.query(restHighLevelClient,"cloud_note",boolQuery);
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits)
            data.add( noteMapper.selectByPrimaryKey(Integer.valueOf(hit.getId())));
        return ResultUtil.success("", data);
    }

    /**
     * 获取笔记详情
     * @param userId 用户id
     * @param id 笔记id
     * @param pwd 笔记密码
     * @return ResponseDto
     */
    public ResponseDto getNoteWithNoteBookAndContent(Integer userId, Integer id,String pwd,String isAdmin) {
        if(CheckerUtil.checkNulls(id)) return ResultUtil.error("缺少参数");
        Note note = noteMapper.selectByIdWithNoteBookAndContent(id);
        if (note==null) return ResultUtil.error("笔记对象不存在");
        if (note.getNoteBook()==null) return ResultUtil.error("笔记本对象不存在");
        if (!userId.equals( note.getNoteBook().getUserId())) return ResultUtil.error("非法操作");
        if (!"true".equals(isAdmin))
            if (note.getIsHasPwd()==1&&!note.getPwd().equals(pwd))
                return ResultUtil.error("笔记密码错误");
        return ResultUtil.success("",note);
    }


    /**
     * 获取包含NoteBook的Note,如果相应信息不符，返回data为null
     * @param userId 用户id
     * @param noteId 笔记id
     * @return ResponseDto
     */
    public ResponseDto<Note> getUserNoteWithNoteBookByUserIdAndNoteId(Integer userId, Integer noteId){
        if(CheckerUtil.checkNulls(noteId)) return ResultUtil.error("缺少参数");
        Note note = noteMapper.selectWithNoteBook(noteId);
        if (note==null) return ResultUtil.success(null);
        if (note.getNoteBook()==null) return ResultUtil.success(null);
        if (!note.getNoteBook().getUserId().equals(userId)) return ResultUtil.success(null);
        return ResultUtil.success("",note);
    }

    /**
     * 笔记加密
     * @param userId 用户id
     * @param noteId 笔记id
     * @param pwd 笔记密码
     * @return ResponseDto
     */
    public ResponseDto lock(Integer userId, Integer noteId,String pwd){
        if(CheckerUtil.checkNulls(noteId)) return ResultUtil.error("缺少参数");
        Note note = noteMapper.selectWithNoteBook(noteId);
        if (note==null) return ResultUtil.error("笔记对象不存在");
        if (note.getNoteBook()==null) return ResultUtil.error("笔记本对象不存在");
        if (!userId.equals( note.getNoteBook().getUserId())) return ResultUtil.error("非法操作");
        if (note.getIsHasPwd()==1) return ResultUtil.error("原先已加密过，请先解密");
        note.setIsHasPwd(1);
        note.setPwd(pwd);
        noteMapper.updateByPrimaryKey(note);
        return ResultUtil.success();
    }

    /**
     * 笔记解密
     * @param userId 用户id
     * @param noteId 笔记id
     * @param pwd 笔记密码
     * @return ResponseDto
     */
    public ResponseDto unlock(Integer userId, Integer noteId,String pwd){
        if(CheckerUtil.checkNulls(noteId)) return ResultUtil.error("缺少参数");
        Note note = noteMapper.selectWithNoteBook(noteId);
        if (note==null) return ResultUtil.error("笔记对象不存在");
        if (!note.getPwd().equals(pwd)) return ResultUtil.error("笔记密码错误");
        if (note.getNoteBook()==null) return ResultUtil.error("笔记本对象不存在");
        if (!userId.equals( note.getNoteBook().getUserId())) return ResultUtil.error("非法操作");
        note.setIsHasPwd(0);
        note.setPwd(null);
        noteMapper.updateByPrimaryKey(note);
        return ResultUtil.success();
    }

    @Override
    public ResponseDto deleteForever(Integer noteId) {
        if(CheckerUtil.checkNulls(noteId)) return ResultUtil.error("缺少参数");
        Note note = noteMapper.selectByIdWithNoteBookAndContent(noteId);
        if (note!=null) noteMapper.deleteByPrimaryKey(noteId);
        noteContentService.deleteByNoteId(noteId);
        noteTagService.deleteByNoteId(noteId);
        shareService.deleteByNoteId(noteId);
        return ResultUtil.success();
    }

    @Override
    public List<Map> selectList(int start, int length) {
        return noteMapper.selectList(start,length);
    }

    @Override
    public int countAll() {
        return noteMapper.countAll();
    }
}