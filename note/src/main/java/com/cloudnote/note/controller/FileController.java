package com.cloudnote.note.controller;

import com.cloudnote.note.dto.FileResponseDto;
import com.cloudnote.note.utils.FileResponseUtil;
import com.cloudnote.note.utils.PathUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * <p>
  * 提醒表 Controller 接口
 * </p>
 *
 */
@RestController
@RequestMapping("file")
public class FileController {

    /**
     * 文件上传
     */
    @RequestMapping(value = {"/upload"})
    public FileResponseDto upload(HttpServletRequest request, MultipartFile imgFile) {
        String newName =null;

        try {
            String dir = request.getParameter("dir");
            if (dir==null||"".equals(dir)) dir="media";
            System.out.println(dir);
            System.out.println(imgFile.getOriginalFilename());
            newName = ""+System.currentTimeMillis() + imgFile.getOriginalFilename();

            if (dir.equals("image")) {
                File realDir=new File(PathUtil.getContentImagePath());
                if (!realDir.exists())
                    realDir.mkdirs();
                File file = new File(PathUtil.getContentImagePath(), newName);
                imgFile.transferTo(file);
                return FileResponseUtil.success(PathUtil.getContentImageUrlSuffix()+newName);

            }else if (dir.equals("file")||dir.equals("media")){
                File realDir=new File(PathUtil.getContentAttachmentPath());
                if (!realDir.exists())
                    realDir.mkdirs();
                File file = new File(PathUtil.getContentAttachmentPath(), newName);
                imgFile.transferTo(file);
                return FileResponseUtil.success(PathUtil.getContentAttachmentUrlSuffix()+newName);
            }else{
                return FileResponseUtil.error("不期望的文件类型");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return FileResponseUtil.error(e.getMessage());
        }
    }

}