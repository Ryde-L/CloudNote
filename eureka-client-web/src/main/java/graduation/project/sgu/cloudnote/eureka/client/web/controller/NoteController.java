package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.GzipUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * <p>
  * 笔记表 Controller 接口
 * </p>
 *
 */
@RestController
@RequestMapping("note")
public class NoteController {

    @Autowired
    NoteService noteService;


    /**
     * 添加笔记
     *
     * @return
     */
    @RequestMapping(value = {"/add"})
    public ResponseDto add(HttpServletRequest request) throws IOException {
        String params = "";
        // 获取 Content-Encoding 请求头
        String contentEncoding = request.getHeader("Content-Encoding");
        if (contentEncoding != null && contentEncoding.equals("gzip")) {
            // 获取输入流
            BufferedReader reader = request.getReader();
            // 将输入流中的请求实体转换为 byte 数组, 进行 gzip 解压
            byte[] bytes = IOUtils.toByteArray(reader, "iso-8859-1");
            // 对 bytes 数组进行解压
            params = GzipUtil.uncompress(bytes);
        } else {
            BufferedReader reader = request.getReader();
            params = IOUtils.toString(reader);
        }
        if (params != null && params.trim().length() > 0) {
            // 因为前台对参数进行了 url 编码, 在此进行解码
            params = URLDecoder.decode(params, "utf-8");
        }
        HashMap map = JsonUtil.jsonToPojo(params, HashMap.class);
        System.out.println(map.get("note_book_id"));
        System.out.println((String)map.get("note_book_id"));
        Integer noteBookId=null;
        if (map.get("note_book_id") instanceof String)
            noteBookId=Integer.valueOf((String)map.get("note_book_id"));
        else if (map.get("note_book_id") instanceof Integer)
            noteBookId= (Integer) map.get("note_book_id");

        return noteService.createNote(noteBookId,(String) map.get("title"), (String) map.get("content"));

    }

    @RequestMapping(value = {"/update"})
    public ResponseDto update(HttpServletRequest request) throws IOException {
        String params = "";
        // 获取 Content-Encoding 请求头
        String contentEncoding = request.getHeader("Content-Encoding");
        if (contentEncoding != null && contentEncoding.equals("gzip")) {
            // 获取输入流
            BufferedReader reader = request.getReader();
            // 将输入流中的请求实体转换为 byte 数组, 进行 gzip 解压
            byte[] bytes = IOUtils.toByteArray(reader, "iso-8859-1");
            // 对 bytes 数组进行解压
            params = GzipUtil.uncompress(bytes);
        } else {
            BufferedReader reader = request.getReader();
            params = IOUtils.toString(reader);
        }
        if (params != null && params.trim().length() > 0) {
            // 因为前台对参数进行了 url 编码, 在此进行解码
            params = URLDecoder.decode(params, "utf-8");
        }
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        HashMap map = JsonUtil.jsonToPojo(params, HashMap.class);
        return noteService.update(userId,Integer.valueOf((String) map.get("note")),(String) map.get("title"), (String) map.get("content"));

    }


//    @RequestMapping(value = {"/getNotesWithTags"})
//    public ResponseDto getList(@RequestParam("note_book_id")Integer noteBookId) {
//        return noteService.getNoteBookList(noteBookId);
//    }

    @RequestMapping(value = {"/getContent"})
    public ResponseDto getContent(@RequestParam("note_id")Integer noteId) {
        return noteService.getContent(noteId);
    }

    @RequestMapping(value = {"/getListByTag"})
    public ResponseDto findByTags(HttpSession session,String tag){
        return noteService.getNoteListByTag(tag,Integer.valueOf ((String) session.getAttribute("userId")));
    }

    @RequestMapping(value = {"/getNoteWithNoteBookAndContent"})
    public ResponseDto getNote(HttpSession session, @RequestParam("note") Integer noteId,String pwd){
        return noteService.getNoteWithNoteBookAndContent(Integer.valueOf ((String) session.getAttribute("userId")),noteId,pwd);
    }

    @RequestMapping(value = {"/unlock"})
    public ResponseDto unlock(HttpSession session, @RequestParam("note_id") Integer noteId,String pwd){
        return noteService.unlock(Integer.valueOf ((String) session.getAttribute("userId")),noteId,pwd);
    }

    @RequestMapping(value = {"/lock"})
    public ResponseDto lock(HttpSession session, @RequestParam("note_id") Integer noteId,String pwd){
        return noteService.lock(Integer.valueOf ((String) session.getAttribute("userId")),noteId,pwd);
    }

}