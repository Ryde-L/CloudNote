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
    //临时文件存活最短时间1小时
    private static final long aliveTime=3600000;

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
        System.out.println("定时线程启动");
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
                //根据文件名比对存活时间、检测是否被使用，满足删除条件即删除
                if ((System.currentTimeMillis()- Long.parseLong(fileName.substring(0,13)))>aliveTime&&
                        file.exists()&&noteContentService.isContainTarget(fileName)==null) {
                    System.out.println("删除文件："+fileName);
                    file.delete();
                }
            }
        }
    }

}

