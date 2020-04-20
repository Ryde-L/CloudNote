package com.cloudnote.note.utils;

/**
 * 保存一些路径
 */
public class PathUtil {

    /*图片存放位置*/
    private static String contentImagePath;
    /*图片url前缀*/
    private static String contentImageUrlSuffix;
    /*附件存放位置*/
    private static String contentAttachmentPath;
    /*附件url前缀*/
    private static String contentAttachmentUrlSuffix;


    /**
     * 获取文本图片在系统的路径
     * @return 图片路径
     */
    public static String getContentImagePath(){
        if (CheckerUtil.checkNulls(contentImagePath))
            contentImagePath= PathUtil.class.getResource("/").getPath()+"static/content/images/";
        return contentImagePath;
    }

    /**
     * 获取文本图片url前缀
     * @return 图片路径
     */
    public static String getContentImageUrlSuffix(){
        if (CheckerUtil.checkNulls(contentImageUrlSuffix))
            contentImageUrlSuffix= "/content/images/";
        return contentImageUrlSuffix;
    }

    /**
     * 获取文本附件在系统的路径
     * @return 图片路径
     */
    public static String getContentAttachmentPath(){
        if (CheckerUtil.checkNulls(contentAttachmentPath))
            contentAttachmentPath= PathUtil.class.getResource("/").getPath()+"static/content/attachments/";
        return contentAttachmentPath;
    }

    /**
     * 获取文本附件url前缀
     * @return 图片路径
     */
    public static String getContentAttachmentUrlSuffix(){
        if (CheckerUtil.checkNulls(contentAttachmentUrlSuffix))
            contentAttachmentUrlSuffix= "/content/attachments/";
        return contentAttachmentUrlSuffix;
    }

}
