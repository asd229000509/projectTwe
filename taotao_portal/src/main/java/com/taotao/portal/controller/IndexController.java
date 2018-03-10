package com.taotao.portal.controller;

import com.taotao.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/index")
@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView toIndexPage() {
        ModelAndView mv = new ModelAndView("index");
        try {
            //6条最新的大广告
            mv.addObject("bigAdData", contentService.queryPortalBigAdData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }
}
