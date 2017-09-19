package com.unionpay.loveRead.controller;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.loveRead.bean.BaseResponse;
import com.unionpay.loveRead.bean.BookDetail;
import com.unionpay.loveRead.bean.BorrowHistory;
import com.unionpay.loveRead.bean.RespStatus;
import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.BookInfoView;
import com.unionpay.loveRead.service.BookService;
import com.unionpay.loveRead.service.DaraWrapperService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 图书处理类
 *
 * @author tony 2016年11月24日 下午10:50:15
 */
@Controller
@RequestMapping(value = "book")
public class BookController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @Autowired
    AppConfig appConfig;

    @Autowired
    DaraWrapperService daraWrapperService;

    /**
     * 图书总搜索
     *
     * @param model
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "search")
    public String search(Model model, HttpServletRequest request) {
        // 获取请求参数
        String keywords = request.getParameter("keywords");
        logger.info("----------搜索开始,keywords：" + keywords + "--------");
        // 缺少必要参数
        List<BookInfoView> bookList = bookService.getBookListByKeywords(keywords);
        //增加点赞数
        bookList = daraWrapperService.addLikeNums(bookList);
        model.addAttribute("bookDetailList", bookList);
        logger.info("----------搜索结果:共" + bookList.size() + "本书-------");
        return "book/bookList";
    }

    /**
     * 通过bookId获取图书详情
     *
     * @param model
     * @param bookId
     */

    @RequestMapping(value = "bookDetail/{bookId}", method = RequestMethod.GET)
    public String getBookDetail(ModelMap model, @PathVariable("bookId") String bookId) {
        logger.info("-------查询图书：" + bookId + " 详情----------");
        BookInfoView bookInfo = bookService.getBookDetailByBookId(bookId);
        BookDetail bookDetail;
        // TODO 如果在借，则添加借阅信息
        bookDetail = new BookDetail(bookInfo);
        model.addAttribute("bookDetail", bookDetail);

        //TODO 查询该书的相关评论
        return "book/bookDetail";
    }

    /**
     * 跳转到书本上传页面
     *
     * @param model
     *
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.GET)
    public String toBookUpload(Model model) {
        logger.info("-----进入书本上传-----");
        return "book/bookUpload";
    }

    /**
     * 图书封面上传
     *
     * @param newImg
     * @param recId
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public void upload(@RequestParam MultipartFile newImg, String recId,
                       Model model, HttpServletRequest request,
                       HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("result", "fail");
        WxMpUser user = getUserInfo(request);
        if (user != null) {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyyMMddHHmmss");
            String dateString = formatter.format(currentTime);
            String dbPath = dateString + ".jpg";
            String diskPath = appConfig.getShareDir() + "/";
            // 创建文件夹
            File targetFile = new File(diskPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            targetFile = new File(diskPath + dbPath);
            try {
                if (recId != null && !"".equals(recId)) {
                    logger.info("-------修改图书信息------");
                    newImg.transferTo(targetFile);
                    Book book = bookService.getBookById(recId);
                    book.setCover(dbPath);
                    book.setUpdateTime(time);
                    bookService.updateBook(book);
                    String recordId = book.getId().toString();
                    map.put("result", "success");
                    map.put("recordId", recordId);
                } else {
                    logger.info("-----上传书本封面图片-----");
                    newImg.transferTo(targetFile);
                    Book book = new Book();
                    book.setBookName("temp");
                    book.setOwnerId(user.getOpenId());
                    book.setCover(appConfig.getAppUrl() + "/upload/" + dbPath);
                    book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
                    book.setBorrowType(Constants.BOOK_BORROW);
                    book.setCreateTime(time);
                    book.setLastDay(Constants.LASTDAY);
                    bookService.addBook(book);
                    String recordId = book.getId().toString();
                    map.put("result", "success");
                    map.put("recordId", recordId);
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeResponse(map, response);
    }

    /**
     * 图书封面上传完成之后，增加其他相应信息
     *
     * @param model
     * @param bookName
     * @param summary
     * @param lastDay
     * @param borrowType
     * @param exchangeCondition
     * @param recordId
     * @param defaultCover
     * @param request
     * @param response
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void addBookInfo(Model model, String bookName, String summary,
                            String lastDay, String borrowType, String exchangeCondition,
                            String recordId, String defaultCover, String isbn, HttpServletRequest request,
                            HttpServletResponse response) {
        String result = "fail";
        if (StringUtils.isBlank(lastDay)) {
            lastDay = String.valueOf(Constants.LASTDAY);
        }

        if (recordId != null) {
            try {
                Book book = bookService.getBookById(recordId);
                book.setBookName(bookName);
                book.setSummary(summary);
                book.setLastDay(Integer.parseInt(lastDay));
                book.setBorrowType(borrowType);
                book.setIsbn(isbn);
                bookService.updateBook(book);
                result = "success";
            } catch (Exception e) {
                bookService.delById(Integer.parseInt(recordId));
                writeResponseStr(result, response);
                e.printStackTrace();
            }
        } else {
            WxMpUser user = getUserInfo(request);
            if (user != null && !StringUtils.isBlank(user.getOpenId())) {
                Book book = new Book();
                book.setBookName(bookName);
                book.setOwnerId(user.getOpenId());
                book.setSummary(summary);
                book.setCover(appConfig.getAppUrl() + "/upload/" + defaultCover);
                book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
                book.setBorrowType(borrowType);
                book.setLastDay(Integer.parseInt(lastDay));
                book.setCreateTime(new Timestamp(System.currentTimeMillis()));
                book.setIsbn(isbn);
                bookService.addBook(book);
                result = "success";
            }
        }
        writeResponseStr(result, response);
    }

    /**
     * 跳转至修改图书信息页面
     *
     * @param model
     * @param id
     *
     * @return
     */
    @RequestMapping(value = "alter", method = RequestMethod.GET)
    public String alterBookInfo(Model model, String id) {
        logger.info("-----跳转至修改图书信息页面-----");
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "userHome/alterBookInfo";
    }

    /**
     * 修改图书信息
     *
     * @param model
     * @param bookName
     * @param lastDay
     * @param borrowType
     * @param recordId
     * @param defaultCover
     * @param request
     * @param response
     */
    @RequestMapping(value = "alter", method = RequestMethod.POST)
    public void alterBookInfo(Model model, String bookName,
                              String lastDay, String borrowType,
                              String recordId, String defaultCover, HttpServletRequest request,
                              HttpServletResponse response) {
        logger.info("-------修改图书信息-------");
        String result = "fail";
        try {
            Book book = bookService.getBookById(recordId);
            book.setBookName(bookName);
            //book.setCategoryId(Integer.parseInt(category));
            book.setCover(defaultCover);
            book.setBorrowType(borrowType);
            book.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            book.setLastDay(Integer.parseInt(lastDay));
            bookService.updateBook(book);
            result = "success";
        } catch (Exception e) {
            writeResponseStr(result, response);
            e.printStackTrace();
        }
        writeResponseStr(result, response);
    }

    /**
     * 图书下架/上架
     *
     * @param model
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "drop")
    public void dropBookInfo(Model model, String id,
                             HttpServletRequest request, HttpServletResponse response) {
        logger.info("-------图书下架/上架-------");
        String result = "fail";
        try {
            Book book = bookService.getBookById(id);
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            if (Constants.BOOK_STATUS_AVAILABLE.equals(book.getStatus())) {
                book.setStatus(Constants.BOOK_STATUS_UNAVAILABLE);
                bookService.updateBook(book);
                result = "success";
                // TODO 图书下架，扣除积分
            } else if (Constants.BOOK_STATUS_UNAVAILABLE.equals(book
                    .getStatus())) {
                book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
                book.setCreateTime(nowTime);
                bookService.updateBook(book);
                result = "success";
                // TODO 图书上架，增加积分
            }
        } catch (Exception e) {
            writeResponseStr(result, response);
            e.printStackTrace();
        }
        writeResponseStr(result, response);
    }

    /**
     * 用户点/取赞
     *
     * @param bookId
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "addLike")
    @ResponseBody
    public BaseResponse addLike(String bookId,HttpServletRequest request) {
        logger.info("------用户对图书点赞操作-----");
        BaseResponse baseResp = new BaseResponse();
        WxMpUser user = getUserInfo(request);

        if(!StringUtils.isBlank(user.getOpenId())){
            String result = bookService.addLike(user.getOpenId(), bookId);
            baseResp.setCode(result);
        }else{
            baseResp.setCode(RespStatus.LOGIN_EXPIRED.getCode());
            baseResp.setMessage(RespStatus.LOGIN_EXPIRED.getMessage());
        }
        logger.info("点赞结果："+baseResp.getCode());
        return baseResp;
    }

    /**
     * 加载更多书籍
     *
     * @param model
     * @param request
     * @param response
     * @param start
     * @param type
     */
    @RequestMapping(value = "getMoreBook", method = RequestMethod.POST)
    public void getMoreBook(Model model, HttpServletRequest request,
                            HttpServletResponse response, Integer start, String type) {
        JSONObject obj = new JSONObject();
        List<BookInfoView> bookList = bookService.findBookListByLimits(start,
                type);
        if (bookList == null || bookList.size() == 0) {
            obj.put("result", "fail");
        } else {
            obj.put("result", "success");
        }
        obj.put("bookList", bookList);
        writeResponse(obj, response);
    }

    /**
     * 根据isbn判断能否上传该书
     *
     * @param request
     * @param model
     * @param isbn
     *
     * @return
     */
    @RequestMapping(value = "checkIsbn", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse checkIsbn(HttpServletRequest request, Model model,
                                  String isbn) {
        logger.info("-----isbn号为:" + isbn + "，判断能否上传该书...");
        BaseResponse response = new BaseResponse();
        String uid = getSessionUid(request);
        if (StringUtils.isBlank(uid)) {
            // 用户信息无法获取
            response.setCode(RespStatus.LOGIN_EXPIRED.getCode());
            response.setMessage(RespStatus.LOGIN_EXPIRED.getMessage());
        } else {
            // 判断该用户是否已经拥有两本同样isbn的书
            int bookNums = bookService.getUserSameBookNums(uid, isbn);
            if (bookNums < appConfig.getSameBookLimit()) {
                // 判断该用户是否借了一本该isbn号的书
                List<BorrowHistory> borrowHistory = new ArrayList<>();
                // 获取用户借了某本书的本数
                bookNums = bookService.getBorrowSameBookNums(uid, isbn,
                        borrowHistory);
                if (bookNums > 0) {
                    // 已经借了该isbn号的图书，则不可再上传
                    response.setCode(RespStatus.BORROW_SAME_BOOK.getCode());
                    response.setMessage(RespStatus.BORROW_SAME_BOOK.getMessage());
                } else {
                    int bookCount = bookService.getBooInfoListByUid(uid).size();
                    if (bookCount >= appConfig.getUserBookLimit()) {
                        // 每个人拥有的图书上限
                        response.setCode(RespStatus.USER_BOOK_LIMIT.getCode());
                        response.setMessage(RespStatus.USER_BOOK_LIMIT.getMessage());
                    }
                }
            } else {
                // 相同的图书达到上限
                response.setCode(RespStatus.SAME_BOOK_LIMIT.getCode());
                response.setMessage(RespStatus.SAME_BOOK_LIMIT.getMessage());
            }

        }
        return response;
    }
}