package com.cloudnote.note.controller;

import com.cloudnote.note.dto.DatatablePage;
import com.cloudnote.note.dto.ResponseDto;
import com.cloudnote.note.service.NoteService;
import com.cloudnote.note.utils.GzipUtil;
import com.cloudnote.note.utils.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("datatable")
    public String datatable(HttpServletRequest request, HttpServletResponse response) {
        List<Map> list = new ArrayList<>();
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<Map> data = new DatatablePage<>();
        String search = "";
        list=noteService.selectList(Integer.parseInt( start),Integer.parseInt(length));
        data.setRecordsFiltered(noteService.countAll());//过滤后的总记录数
        data.setDraw(Integer.parseInt(request.getParameter("draw")) + 1);
        data.setData(list);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JsonUtil.objectToJson(data);
    }


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
        Integer userId = Integer.valueOf ((String) request.getSession().getAttribute("userId"));
        HashMap map = JsonUtil.jsonToPojo(params, HashMap.class);
        //TODO 直接点编辑按钮无法添加
        if (map.get("note_book_id")!=null)
           return add(request);//是添加，不是更新的情况
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
        return noteService.getNoteWithNoteBookAndContent(Integer.valueOf ((String) session.getAttribute("userId")),noteId,pwd, String.valueOf(session.getAttribute("isAdmin")));
    }

    @RequestMapping(value = {"/unlock"})
    public ResponseDto unlock(HttpSession session, @RequestParam("note_id") Integer noteId,String pwd){
        return noteService.unlock(Integer.valueOf ((String) session.getAttribute("userId")),noteId,pwd);
    }

    @RequestMapping(value = {"/lock"})
    public ResponseDto lock(HttpSession session, @RequestParam("note_id") Integer noteId,String pwd){
        return noteService.lock(Integer.valueOf ((String) session.getAttribute("userId")),noteId,pwd);
    }


    /**
     * 管理员直接删除
     */
    @RequestMapping(value = {"/delByAdministrator"})
    public ResponseDto delByAdministrator(HttpSession session,@RequestParam("note_id") Integer noteId,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return noteService.deleteForever(Integer.valueOf ((String) session.getAttribute("userId")),noteId);
    }
}