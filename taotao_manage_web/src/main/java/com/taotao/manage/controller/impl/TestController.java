package com.taotao.manage.controller.impl;

import com.taotao.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("date")
    public String queryCurrentDate() {
        return testService.queryCurrentDate();
    }
}
