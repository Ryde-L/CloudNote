package com.cloudnote.note.controller;

import com.cloudnote.common.pojo.NoteContent;
import com.cloudnote.note.service.NoteContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
  * 笔记内容表 Controller 接口
 * </p>
 */
@RestController
@RequestMapping("noteContent")
public class NoteContentController {

    @Autowired
    NoteContentService noteContentService;

    @RequestMapping(value = {"/getNoteContent"})
    public NoteContent getNoteContent(@RequestParam("note_id") Integer noteId){
        return noteContentService.getNoteContent(noteId);
    }
}