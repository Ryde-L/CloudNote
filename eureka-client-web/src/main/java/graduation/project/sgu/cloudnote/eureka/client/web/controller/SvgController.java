package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.GzipUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;


@Controller
@RequestMapping("/svg")
public class SvgController {

    @Autowired
    JsonBuilder jsonBuilder;

    @RequestMapping(value = {"/submit"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String acceptSvgCode(HttpServletRequest request, String svg) throws IOException {
        String params = "";
        try {
            // 获取 Content-Encoding 请求头
            String contentEncoding = request.getHeader("Content-Encoding");
            if (contentEncoding != null && contentEncoding.equals("gzip")) {
                // 获取输入流
                BufferedReader reader = request.getReader();
                // 将输入流中的请求实体转换为 byte 数组, 进行 gzip 解压
                byte[] bytes = IOUtils.toByteArray(reader,"iso-8859-1");
                // 对 bytes 数组进行解压
                params = GzipUtil.uncompress(bytes);
            } else {
                BufferedReader reader = request.getReader();
                params = IOUtils.toString(reader);
            }
            if (params != null && params.trim().length() > 0) {
                // 因为前台对参数进行了 url 编码, 在此进行解码
                params = URLDecoder.decode(params, "utf-8");
                HashMap map = JsonUtil.jsonToPojo(params, HashMap.class);
                System.out.println(map.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonBuilder.add("isSuccessful","1").build();
    }
}
