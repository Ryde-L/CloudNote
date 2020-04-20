package com.cloudnote.user.controller;

import com.cloudnote.user.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
  *  Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("administrator")
public class AdministratorController {

    @RequestMapping(value = {"/"})
    public ResponseDto getNote(HttpSession session, @RequestParam("note") Integer noteId, String pwd){

        return null;
    }

}