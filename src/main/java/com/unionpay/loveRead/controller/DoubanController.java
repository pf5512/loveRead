package com.unionpay.loveRead.controller;

import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.service.BookService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("douban")
public class DoubanController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DoubanController.class);

    @Autowired
    BookService bookService;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadBook(HttpServletRequest request, Model model, String bookStr) {
        Book book = bookService.convertRes2Book(bookStr);
        WxMpUser user = getUserInfo(request);
        logger.info("-----图书开始入库...");
        book.setOwnerId(user.getOpenId());
        bookService.addBook(book);
        return "success";
    }
}
