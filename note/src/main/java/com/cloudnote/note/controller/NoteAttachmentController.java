package com.cloudnote.note.controller;

import com.cloudnote.note.dto.FileResponseDto;
import com.cloudnote.note.utils.FileResponseUtil;
import com.cloudnote.note.utils.PathUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * <p>
  * 笔记附件表 Controller 接口
 * </p>
 */
@RestController
@RequestMapping("noteAttachment")
public class NoteAttachmentController {


    /**
     * 录音上传
     */
    @RequestMapping(value = {"/voiceUpload"})
    public FileResponseDto voiceUpload(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
            MultipartFile file = req.getFile("file");
            //添加后缀检验
            File prepareSaveFile = new File(PathUtil.getContentAttachmentPath(), "voice" + System.currentTimeMillis() + file.getOriginalFilename());
            file.transferTo(prepareSaveFile);
            return FileResponseUtil.success("");
        } catch (Exception e) {
            e.printStackTrace();
            return FileResponseUtil.error(e.getMessage());
        }
    }
	
}