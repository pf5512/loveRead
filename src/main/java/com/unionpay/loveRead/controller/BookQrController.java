package com.unionpay.loveRead.controller;

import com.google.zxing.WriterException;
import com.unionpay.loveRead.bean.BaseResponse;
import com.unionpay.loveRead.bean.RespStatus;
import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.BookBorrowFlow;
import com.unionpay.loveRead.domain.WxUser;
import com.unionpay.loveRead.service.BookService;
import com.unionpay.loveRead.service.BorrowService;
import com.unionpay.loveRead.service.UserService;
import com.unionpay.loveRead.utils.QrCodeUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping(value = "qrBook")
public class BookQrController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BookQrController.class);

    @Autowired
    BorrowService borrowService;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    AppConfig appConfig;

    @Autowired
    WxMpService wxMpService;

    /**
     * 跳转至借书二维码生成页面
     *
     * @param model
     * @param request
     * @param response
     * @param bookId
     * @param ownerId
     * @param bookName
     * @param lastDay
     *
     * @return
     */
    @RequestMapping(value = "borrow", method = RequestMethod.POST)
    public String borrowQrCode(Model model, HttpServletRequest request,
                               HttpServletResponse response, Integer bookId, String ownerId,
                               String bookName, Integer lastDay, String bookCover, String borrowType) {
        //生成OAUTH url 前缀，否则
        String borrowUrl = appConfig.getAppUrl() + "/loveRead/borrow/1/" + bookId + "/" + ownerId + "/test";
        borrowUrl = wxMpService.oauth2buildAuthorizationUrl(borrowUrl, WxConsts.OAUTH2_SCOPE_USER_INFO, "STATE");
        logger.info("borrowUrl:" + borrowUrl);
        try {
            borrowUrl = URLEncoder.encode(borrowUrl, "UTF-8");
            logger.info("encode borrowUrl:" + borrowUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "system/error";
        }
        model.addAttribute("borrowUrl", borrowUrl);
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookName", bookName);
        model.addAttribute("bookCover", bookCover);
        WxMpUser user = getUserInfo(request);
        model.addAttribute("userName", user.getNickname());
        model.addAttribute("lastDay", lastDay == null ? Constants.LASTDAY : lastDay);
        model.addAttribute("borrowType", borrowType);
        return "borrow/borrowByCode";
    }

    /**
     * 跳转至还书二维码生成页面
     *
     * @param model
     * @param request
     * @param response
     * @param bookId
     * @param ownerId
     * @param bookName
     *
     * @return
     */
    @RequestMapping(value = "return", method = RequestMethod.POST)
    public String returnQrCode(Model model, HttpServletRequest request,
                               HttpServletResponse response, Integer bookId, String ownerId,
                               String bookName, String bookCover) {

        WxMpUser user = getUserInfo(request);
        model.addAttribute("borrowerName", user.getNickname());
        WxUser owner = userService.getUserByOpenId(user.getOpenId());
        model.addAttribute("userName", owner.getNickName());
        model.addAttribute("bookCover", bookCover);

        String returnUrl = appConfig.getAppUrl() + "/loveRead/borrow/2/" + bookId + "/" + user.getOpenId() + "/test";

        model.addAttribute("returnUrl", returnUrl);
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookName", bookName);

        BookBorrowFlow borrow = borrowService.getBorrowRecordById(user.getOpenId(), bookId + "", Constants.BORROW_ING);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String endTs = sdf.format(calendar.getTime());
        calendar.setTime(borrow.getOutTime());
        String startTs = sdf.format(calendar.getTime());

        model.addAttribute("startTs", startTs);
        model.addAttribute("endTs", endTs);
        model.addAttribute("bodyClass", "qr-code-body");
        return "borrow/returnByCode";
    }

    /**
     * 生成二维码
     *
     * @param request
     * @param response
     * @param text
     */
    @RequestMapping(value = "generate")
    public void generateQr(HttpServletRequest request,
                           HttpServletResponse response, String type, String text) {
        try {
            text = text.replace("\\/", "/");
            // URL编码解析
            text = URLDecoder.decode(text, "UTF-8");
            logger.info("text: " + text);
            OutputStream out = response.getOutputStream();
            // 设置输出文件流类型
            response.setContentType("image/png");

            if (type.equals("qr")) {
                //二维码
                QrCodeUtil.createQrCode(text, out);
            } else if (type.equals("bar")) {
                //条形码
                QrCodeUtil.createBarCode(text, out);
            } else {
                logger.info("please check your code type!");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("io exception:" + e.getMessage());
        } catch (WriterException e) {
            logger.error("writer exception:" + e.getMessage());
        } catch (Exception e) {
            logger.error("qrCode exception:" + e.getMessage());
        }

    }

    /**
     * 轮询查书状态
     *
     * @param model
     * @param request
     * @param bookId
     * @param type    1为借书时 2为还书时
     */
    @RequestMapping(value = "getBookState", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getBookState(Model model, HttpServletRequest request, String bookId, Integer type) {
        BaseResponse response = new BaseResponse();
        if (!StringUtils.isBlank(bookId)) {
            Book book = bookService.getBookById(bookId);
            if (book != null) {
                if (type == 1 && Constants.BOOK_STATUS_OUT.equals(book.getStatus())) {
                    response.setCode(RespStatus.OK.getCode());
                    response.setMessage("借书成功!");
                } else if (type == 2 && Constants.BOOK_STATUS_AVAILABLE.equals(book.getStatus())) {
                    response.setCode(RespStatus.OK.getCode());
                    response.setMessage("还书成功!");
                } else {
                    response.setCode(RespStatus.SERVER_ERROR.getCode());
                    response.setMessage(RespStatus.SERVER_ERROR.getCode());
                }
            }
        }
        return response;
    }
}
