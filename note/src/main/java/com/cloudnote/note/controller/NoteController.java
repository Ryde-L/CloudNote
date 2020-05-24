package com.cloudnote.note.controller;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.pojo.Note;
import com.cloudnote.common.utils.JsonUtil;
import com.cloudnote.note.dto.DatatablePage;
import com.cloudnote.note.service.NoteService;
import com.cloudnote.note.utils.GzipUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("datatableByAdministrator")
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
        String contentEncoding = request.getHeader("Content-Encoding")!=null?request.getHeader("Content-Encoding"):request.getHeader("accept-encoding");
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
        String contentEncoding = request.getHeader("Content-Encoding")!=null?request.getHeader("Content-Encoding"):request.getHeader("accept-encoding");
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
        Integer userId = Integer.valueOf ( request.getHeader("userId"));
        HashMap map = JsonUtil.jsonToPojo(params, HashMap.class);
        //TODO 直接点编辑按钮无法添加
        if (map.get("note_book_id")!=null)
           return add(request);//是添加，不是更新的情况
        return noteService.update(userId,Integer.valueOf((String) map.get("note")),(String) map.get("title"), (String) map.get("content"));

    }


    @RequestMapping(value = {"/getContent"})
    public ResponseDto getContent(@RequestParam("note_id")Integer noteId) {
        return noteService.getContent(noteId);
    }

    @RequestMapping(value = {"/getListByTag"})
    public ResponseDto findByTags(HttpServletRequest request,String tag){
        return noteService.getNoteListByTag(tag,Integer.parseInt(request.getHeader("userId")));
    }

    @RequestMapping(value = {"/getNoteWithNoteBookAndContent"})
    public ResponseDto getNote(HttpServletRequest request, @RequestParam("note") Integer noteId,String pwd){
        return noteService.getNoteWithNoteBookAndContent(Integer.parseInt(request.getHeader("userId")),noteId,pwd, request.getHeader("isAdmin"));
    }

    @RequestMapping(value = {"/unlock"})
    public ResponseDto unlock(HttpServletRequest request, @RequestParam("note_id") Integer noteId,String pwd){
        return noteService.unlock(Integer.parseInt(request.getHeader("userId")),noteId,pwd);
    }

    @RequestMapping(value = {"/lock"})
    public ResponseDto lock(HttpServletRequest request, @RequestParam("note_id") Integer noteId,String pwd){
        return noteService.lock(Integer.parseInt(request.getHeader("userId")),noteId,pwd);
    }


    /**
     * 管理员直接删除
     */
    @RequestMapping(value = {"/delByAdministrator"})
    public ResponseDto delByAdministrator(@RequestParam("note_id") Integer noteId){
        return noteService.deleteForever(noteId);
    }

    /** restTemplate调用 */
//    @RequestMapping(value = {"getUserNoteWithNoteBookByUserIdAndNoteId"}, produces = {"application/json;charset=utf-8"})
    @RequestMapping(value = {"getUserNoteWithNoteBookByUserIdAndNoteId"})
    public Note getUserNoteWithNoteBookByUserIdAndNoteId(Integer userId, Integer noteId){
        return noteService.getUserNoteWithNoteBookByUserIdAndNoteId(userId, noteId).getData();
    }

    /** restTemplate调用 */
    @RequestMapping(value = {"/del"})
    public int del(@RequestParam("note_id") Integer noteId){
        return noteService.delete(noteId);
    }

    /** restTemplate调用 */
//    @RequestMapping(value = {"/getNote"})
    @RequestMapping(value = {"getNote"}, produces = {"application/json;charset=utf-8"})
    public Note getNote(@RequestParam("note_id") Integer noteId){
        return noteService.getNote(noteId);
    }

    /** restTemplate调用 */
    @RequestMapping(value = {"/insert"})
    public int insert(@RequestBody Note note){
        return noteService.insert(note);
    }

}