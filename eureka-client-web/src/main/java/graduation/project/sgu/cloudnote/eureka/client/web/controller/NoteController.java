package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.GzipUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * <p>
  * 笔记表 Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("note")
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    JsonBuilder jsonBuilder;

    /**
     * 添加笔记
     *
     * @return
     */
    @RequestMapping(value = {"/add"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String add(HttpServletRequest request) {
        String params = "";
        try {
            // 获取 Content-Encoding 请求头
            String contentEncoding = request.getHeader("Content-Encoding");
            if (contentEncoding != null && contentEncoding.equals("gzip")) {
                // 获取输入流
                BufferedReader reader = request.getReader();
                // 将输入流中的请求实体转换为 byte 数组, 进行 gzip 解压
                byte[] bytes = IOUtils.toByteArray(reader, "iso-8859-1");
                // 对 bytes 数组进行解压
                params = GzipUtil.uncompress(bytes);
            } else {
                BufferedReader reader = request.getReader();
                params = IOUtils.toString(reader);
            }
            if (params != null && params.trim().length() > 0) {
                // 因为前台对参数进行了 url 编码, 在此进行解码
                params = URLDecoder.decode(params, "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return jsonBuilder.setValuesIntoTemplate("0", "创建笔记抛出异常").build();
        }
        return noteService.createNote(JsonUtil.jsonToPojo(params, HashMap.class));

    }



    @RequestMapping(value = {"/getListByNoteBook"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getList(HttpServletRequest request) {
        String noteBookId = request.getParameter("note_book_id");
        return noteService.getNoteBookList(noteBookId);
    }

    @RequestMapping(value = {"/getContent"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getContent(HttpServletRequest request) {
        String noteId = request.getParameter("note_id");
        return noteService.getContent(noteId);
    }

    @RequestMapping(value = {"/getListByTag"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String findByTags(String tag){
        return noteService.getNoteListByTag(tag);
    }
}