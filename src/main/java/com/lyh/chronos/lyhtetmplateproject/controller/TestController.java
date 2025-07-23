package com.lyh.chronos.lyhtetmplateproject.controller;

import com.lyh.chronos.lyhtetmplateproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UsersService usersService;
    @GetMapping("/getDbTest")
    public String getDbTest() {
//        return "dbTest";
        return usersService.getById(1).toString();
    }
}
