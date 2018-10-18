package com.micro.service.zuul.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileUploadController {
    @RequestMapping(value = "/loginerror")
    public  String login(HttpServletResponse rsp) {
        System.out.println("testtest___________");
        return  "loginerror";
    }

}