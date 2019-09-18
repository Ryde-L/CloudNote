package graduation.project.sgu.cloudnote.eureka.client.web.service;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;

/**
 * <p>
  * 分享表 Service 接口
 * </p>
 *
 */
public interface ShareService {

    /**
     * 创建分享
     * @param shareLinkPrefix 分享链接的前缀
     * @param userId 用户id
     * @param noteId 笔记id
     * @param isHasPwd 是否有密码
     * @param pwd 密码
     * @param limitType 分享的限制 0无 1天数 2次数
     * @param limitContent 限制的具体内容，结合limitType
     * @return ResponseDto
     */
    public ResponseDto create(String shareLinkPrefix,Integer userId, Integer noteId, int isHasPwd, String pwd, int limitType, int limitContent);

    /**
     * 打开分享链接
     * @param link 链接
     * @param pwd 分享密码，如果没有，可为null
     * @return
     */
    public ResponseDto openShare(String link,String pwd);

    /**
     * 取消分享
     * @param userId 用户id
     * @param shareId 分享id
     * @return ResponseDto
     */
    public ResponseDto cancel(Integer userId,Integer shareId);

}