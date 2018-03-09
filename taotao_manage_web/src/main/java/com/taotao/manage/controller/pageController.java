package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 通用跳转页面
 */
@RequestMapping("/page")
@Controller
public class pageController {

    @RequestMapping(value = "/{pageName}",method = RequestMethod.GET)
    public String toPage(@PathVariable String pageName) {
        return pageName;
    }

}
