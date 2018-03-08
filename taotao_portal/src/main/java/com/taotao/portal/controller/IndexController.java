package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/index")
@Controller
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView toIndexPage() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
