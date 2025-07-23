package com.lyh.chronos.lyhtetmplateproject.controller;

import com.lyh.chronos.lyhtetmplateproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserService userService;
    @GetMapping("/getDbTest")
    public String getDbTest() {
        return "dbTest";
    }
}
