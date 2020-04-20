package com.cloudnote.note.thread;

import com.cloudnote.note.service.NoteContentService;
import com.cloudnote.note.utils.PathUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;


/**
 * 定时清附件和图片
 */
@Component
public class DeleteAttachmentThread implements InitializingBean,Runnable {

    @Autowired
    private NoteContentService noteContentService;
    //30分钟执行一次
    private static final long sleepTime=1800000;

    public DeleteAttachmentThread() { }
    public DeleteAttachmentThread(NoteContentService noteContentService) {
        this.noteContentService=noteContentService;
    }

    @Override
    public void afterPropertiesSet() {
        Thread thread=new Thread(new DeleteAttachmentThread(noteContentService));
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                 del(new  File(PathUtil.getContentAttachmentPath()));
                 del(new  File(PathUtil.getContentImagePath()));
                //休眠半小时
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void del(File dir){
        if (dir.exists() && dir.isDirectory()) {
            //遍历附件目录
            File[] files = dir.listFiles();
            for (File file : files) {
                String fileName =file.getName();
                //根据文件名查找笔记内容，没找到即删除
                if (file.exists()&&noteContentService.isContainTarget(fileName)==null)
                    file.delete();
            }
        }
    }

}

