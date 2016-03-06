package com.tamtran.myreceipt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Tam Tran on 11/4/2015.
 */
@Controller
public class ErrorController {
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView error(Exception ex) {
        ModelAndView model = new ModelAndView();
        model.setViewName("error");
        return model;

    }
}
