package com.unionpay.loveRead.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 所有图书
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = {"", "index"})
    public String score(Model model, HttpServletRequest request) {
        logger.info("score test");
        return "index";
    }

    /**
     * 404
     *
     * @return
     */
    @RequestMapping(value = "system/404", method = RequestMethod.GET)
    public String notFound() {
        logger.info("404!");
        return "system/404";
    }

    /**
     * 403
     *
     * @return
     */
    @RequestMapping(value = "system/403", method = RequestMethod.GET)
    public String forbidden() {
        logger.info("403!");
        return "system/403";
    }

    /**
     * error
     *
     * @return
     */
    @RequestMapping(value = "system/error", method = RequestMethod.GET)
    public String error() {
        logger.info("error!");
        return "system/error";
    }

}