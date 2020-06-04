package com.cloudnote.note.controller;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.note.service.NoteTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
  * 笔记标签表 Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("noteTag")
public class NoteTagController {

    @Autowired
    NoteTagService noteTagService;


    @RequestMapping(value = {"/addTags"})
    public ResponseDto addTags(@RequestParam("note_id") Integer noteId, @RequestParam("tags[]") String[] tags) throws Exception {
        return noteTagService.addTags(noteId, tags);
    }
    @RequestMapping(value = {"/delTag"})
    public ResponseDto delTag(@RequestParam("note_id") Integer noteId,@RequestParam("tag") String tag) throws Exception {
        return noteTagService.delTag(noteId, tag);
    }

    @RequestMapping("/delByTagId")
    public ResponseDto delOne( @RequestParam("tag_id") Integer tagId) throws IOException {
        return noteTagService.delByTagId(tagId);
    }


    @RequestMapping("/delAllByNoteId")
    public ResponseDto delAll( @RequestParam("note_id") Integer noteId) {
        return noteTagService.delAll(noteId);
    }

    @RequestMapping("/getTags")
    public ResponseDto getTags(@RequestParam("note_id") Integer noteId) {
        return noteTagService.getTags(noteId);
    }



	
}