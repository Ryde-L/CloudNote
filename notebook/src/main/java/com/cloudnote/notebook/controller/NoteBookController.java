package com.cloudnote.notebook.controller;

import com.cloudnote.notebook.dto.ResponseDto;
import com.cloudnote.notebook.pojo.NoteBook;
import com.cloudnote.notebook.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
    public ResponseDto add(@RequestParam("title") String title, HttpSession session){
        Integer userId = Integer.valueOf((String)session. getAttribute("userId"));
        return noteBookService.createNoteBook(userId,title);
    }

    @RequestMapping(value = {"/delete"})
    public ResponseDto del(@RequestParam("note_book_id") int noteBookId, HttpSession session){
        Integer userId = Integer.valueOf((String)session. getAttribute("userId"));
        return noteBookService.remove(userId,noteBookId);
    }

    @RequestMapping(value = {"/getNoteBooks"})
    public ResponseDto getNoteBooks(HttpSession session){
        Integer userId = Integer.valueOf((String)session. getAttribute("userId"));
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
}