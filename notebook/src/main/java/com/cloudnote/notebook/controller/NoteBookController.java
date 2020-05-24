package com.cloudnote.notebook.controller;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.pojo.NoteBook;
import com.cloudnote.notebook.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
  * 笔记本表 Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("noteBook")
public class NoteBookController {

    @Autowired
    NoteBookService noteBookService;

    @RequestMapping(value = {"/add"})
    public ResponseDto add(@RequestParam("title") String title, HttpServletRequest request){
        Integer userId = Integer.valueOf(request.getHeader("userId"));
        return noteBookService.createNoteBook(userId,title);
    }

    @RequestMapping(value = {"/createDefault"})
    public int createDefault(HttpServletRequest request){
        return noteBookService.createDefaultNoteBook(Integer.valueOf(request.getParameter("userId")));
    }

    @RequestMapping(value = {"/delete"})
    public ResponseDto del(@RequestParam("note_book_id") int noteBookId, HttpServletRequest request){
        Integer userId = Integer.valueOf(request.getHeader("userId"));
        return noteBookService.remove(userId,noteBookId);
    }

    @RequestMapping(value = {"/getNoteBooks"})
    public ResponseDto getNoteBooks(HttpServletRequest request){
        Integer userId = Integer.valueOf(request.getHeader("userId"));
        return noteBookService.getNoteBooks(userId);
    }

    @RequestMapping(value = {"/getNotesWithTags"})
    public ResponseDto getList(@RequestParam("note_book_id")Integer noteBookId) {
        return noteBookService.getContainNoteListWithTag(noteBookId);
    }

    @RequestMapping(value = {"/getNoteBookByNoteBookId"})
    public ResponseDto getNoteBookByNoteBookId(@RequestParam("note_book_id")Integer noteBookId){
        return noteBookService.getNoteBookByNoteBookId(noteBookId);
    }

    /* restTemplate */
    @RequestMapping(value = {"/getNoteBook"})
    public NoteBook getNoteBook(@RequestParam("note_book_id")Integer noteBookId){
        return (NoteBook) noteBookService.getNoteBookByNoteBookId(noteBookId).getData();
    }
    /* restTemplate */
    @RequestMapping(value = {"/getDefaultNoteBook"})
    public NoteBook getDefaultNoteBook(HttpServletRequest request){
        return noteBookService.getDefaultNoteBook(Integer.valueOf(request.getHeader("userId")));
    }
}