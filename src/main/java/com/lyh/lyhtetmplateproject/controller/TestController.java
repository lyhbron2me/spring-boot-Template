package com.lyh.lyhtetmplateproject.controller;

import com.lyh.lyhtetmplateproject.entity.ResponseResult;
import com.lyh.lyhtetmplateproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    
    /**
     * 测试空指针异常
     */
    @GetMapping("/nullPointerException")
    public ResponseResult testNullPointerException() {
        String str = null;
        int length = str.length(); // 这里会抛出空指针异常
        return ResponseResult.okResult();
    }
    
    /**
     * 测试算术异常
     */
    @GetMapping("/arithmeticException")
    public ResponseResult testArithmeticException() {
        int result = 10 / 0; // 这里会抛出算术异常
        return ResponseResult.okResult();
    }
    
    /**
     * 测试自定义运行时异常
     */
    @GetMapping("/runtimeException")
    public ResponseResult testRuntimeException() {
        throw new RuntimeException("这是一个运行时异常");
    }
    
    /**
     * 测试普通异常
     */
    @GetMapping("/exception")
    public ResponseResult testException() throws Exception {
        throw new Exception("这是一个普通异常");
    }
    
    /**
     * 测试数组越界异常
     */
    @GetMapping("/indexOutOfBoundsException")
    public ResponseResult testIndexOutOfBoundsException() {
        List<String> list = new ArrayList<>();
        String item = list.get(0); // 这里会抛出数组越界异常
        return ResponseResult.okResult();
    }
}