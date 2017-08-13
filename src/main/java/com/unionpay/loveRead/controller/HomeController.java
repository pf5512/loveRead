package com.unionpay.loveRead.controller;

import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.BookInfoView;
import com.unionpay.loveRead.domain.WxUser;
import com.unionpay.loveRead.service.BookService;
import com.unionpay.loveRead.service.UserService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    AppConfig appConfig;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    /**
     * 首页
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = {"", "index"})
    public String score(Model model, HttpServletRequest request) {
        logger.info("进入首页");
        WxMpUser user = getUserInfo(request);
        if (user != null) {
            if (user.getHeadImgUrl().contains("http")) {
                model.addAttribute("headerIcon", user.getHeadImgUrl());
            }
        }
        //获取js api
        try {
            String url = processUrl(request, appConfig.getAppUrl());
            logger.info("url:" + url);
            WxJsapiSignature wxConfig = wxMpService.createJsapiSignature(url);
            model.addAttribute("wxConfig", wxConfig);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "index";
    }

    /**
     * 所有
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "allBookAndUser")
    public String allBookAndUser(Model model, HttpServletRequest request) {
        List<BookInfoView> bookList = bookService.findBookListByLimits(null, null);
        List<WxUser> userList = userService.findUserListByLimits(null);

        //伪分页
        if (bookList.size() > Constants.PAGE_SIZE) {
            bookList = bookList.subList(0, Constants.PAGE_SIZE);
        } else {
            bookList = bookList.subList(0, bookList.size());
        }

        if (userList.size() > Constants.PAGE_SIZE) {
            userList = userList.subList(0, Constants.PAGE_SIZE);
        }
        //把积分字段替换为拥有的书本数量，在前台展示
        for (int i = 0; i < userList.size(); i++) {
            int bookCount = bookService.getBooInfoListByUid(userList.get(i).getOpenId()).size();
            userList.get(i).setScore(bookCount);
        }

        model.addAttribute("bookDetailList", bookList);
        model.addAttribute("userList", userList);
        return "book/allBookAndUserList";
    }

    /**
     * 关于我们
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "aboutUs")
    public String aboutUs(Model model, HttpServletRequest request,
                          HttpServletResponse response) {
        logger.info("into aboutUs!");
        model.addAttribute("bodyClass", "qr-code-body");
        return "aboutUs";
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