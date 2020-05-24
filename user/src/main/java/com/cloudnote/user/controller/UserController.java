package com.cloudnote.user.controller;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.utils.JsonUtil;
import com.cloudnote.user.dto.DatatablePage;
import com.cloudnote.common.pojo.User;
import com.cloudnote.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("datatableByAdministrator")
    public String datatable(HttpServletRequest request) {
        List<User> list = new ArrayList<>();
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<User> data = new DatatablePage<>();
        String search = "";
        list=userService.selectList(Integer.parseInt( start),Integer.parseInt(length));
        data.setRecordsFiltered(userService.countAll());//过滤后的总记录数
        data.setDraw(Integer.parseInt(request.getParameter("draw")) + 1);
        data.setData(list);
        return JsonUtil.objectToJson(data);
    }


    @RequestMapping("lockByAdministrator")
    ResponseDto lock(@RequestParam("user")Integer userId){
        return userService.lock(userId);
    }

    @RequestMapping("unlockByAdministrator")
    ResponseDto unlock(@RequestParam("user")Integer userId){
        return userService.unlock(userId);
    }

    @RequestMapping("getById")
    ResponseDto getById(@RequestParam("user_id")Integer userId){
        return userService.getById(userId);
    }


}
