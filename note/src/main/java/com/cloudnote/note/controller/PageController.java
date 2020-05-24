package com.cloudnote.note.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("path")
public class PageController {

    @RequestMapping("{page}")
    public void showPage(@PathVariable String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(page);
        response.sendRedirect("/page/"+page+(request.getQueryString()==null?"":"?"+request.getQueryString()));
    }
}
