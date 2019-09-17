package graduation.project.sgu.cloudnote.eureka.client.web.service.impl;

import graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteBookMapper;
import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Note;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.RecycleBinService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
        noteBookMapper.insert(new NoteBook(null, userId, title, 1));
        return ResultUtil.success("", null);
    }

    public NoteBook getNoteBook(Integer id) {
        return noteBookMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据笔记本id获取笔记本和其所有的所有笔记列表
     *
     * @param id 笔记本id
     * @return 包含NoteList的NoteBook对象
     */
    public NoteBook getContainNoteList(Integer id) {
        return noteBookMapper.selectByIdContainsNoteList(id);
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
     * @param id 笔记本id
     * @return ResponseDto
     */
    public ResponseDto remove(Integer id) {
        if (CheckerUtil.checkNull(id)) return ResultUtil.error("缺少参数", null);

        NoteBook noteBookWithNoteList = noteBookMapper.selectWithNoteList(id);
        List<Note> noteList = noteBookWithNoteList.getNoteList();
        Integer[] noteIds = new Integer[noteList.size()];
        for (int i = 0; i < noteIds.length; i++)
            noteIds[i] = noteList.get(i).getId();

        //删除笔记
        recycleBinService.throwNoteIntoRecycleBin(noteIds);

        //删除笔记本
        noteBookMapper.deleteByPrimaryKey(id);

        return ResultUtil.success("", noteBookMapper.selectWithNoteList(id));
    }
}