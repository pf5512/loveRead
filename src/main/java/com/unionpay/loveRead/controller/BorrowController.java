package com.unionpay.loveRead.controller;

import com.unionpay.loveRead.bean.BaseResponse;
import com.unionpay.loveRead.bean.RespStatus;
import com.unionpay.loveRead.constants.AppConfig;
import com.unionpay.loveRead.constants.Constants;
import com.unionpay.loveRead.domain.Book;
import com.unionpay.loveRead.domain.WxUser;
import com.unionpay.loveRead.enums.CodeEventEnums;
import com.unionpay.loveRead.service.BookService;
import com.unionpay.loveRead.service.BorrowService;
import com.unionpay.loveRead.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 图书借阅及归还处理类
 *
 * @author tony 2016年11月24日 下午10:50:15
 */
@Controller
@RequestMapping(value = "")
public class BorrowController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(BorrowController.class);

    @Autowired
    AppConfig appConfig;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    BorrowService borrowService;

    /**
     * 二维码借/还书
     *
     * @param eventId
     * @param bookId
     * @param ownId
     * @param model
     *
     * @return
     */
    @RequestMapping(value = "borrow/{eventId}/{bookId}/{ownId}/test")
    public String borrowRedirect(HttpServletRequest request,
                                 @PathVariable("eventId") String eventId,
                                 @PathVariable("bookId") String bookId,
                                 @PathVariable("ownId") String ownId, Model model) {
        logger.info("eventId : " + eventId + ", bookeId : " + bookId + ", ownId : " + ownId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("ownId", ownId);
        //获取当前用户信息
        String optUserId = getSessionUid(request);
        logger.info("optUserId:" + optUserId);
        //根据事件类型分别处理
        BaseResponse response = new BaseResponse();
        if (eventId.equals(CodeEventEnums.BORROW.getCode())) {
            //借阅
            borrow(response, bookId, ownId, optUserId);
        } else if (eventId.equals(CodeEventEnums.RETURN.getCode())) {
            //归还
            returnBack(response, bookId, optUserId, ownId);
        } else {
            //出错
            response.setCode(RespStatus.FAIL.getCode());
            response.setMessage(RespStatus.LACK_PARAM.getCode());
        }
        model.addAttribute("resp", response);
        return "borrow/redirect";
    }

    private void borrow(BaseResponse response, String bookId, String ownerId, String readerId) {
        if (bookId == null || readerId == null) {
            // 缺少必要参数
            response.setCode(RespStatus.LACK_PARAM.getCode());
            response.setMessage(RespStatus.LACK_PARAM.getMessage());
        } else {
            WxUser reader = userService.getUserByOpenId(readerId);
            if (reader == null) {
                response.setCode(RespStatus.NO_AUTH_BORROW.getCode());
                response.setMessage(RespStatus.NO_AUTH_BORROW.getMessage());
            }

            boolean success = true;
            // 先判断图书状态，如果可借则执行后续操作，否则返回；
            Book book = bookService.getBookById(bookId);
            if (book.getStatus().equals(Constants.BOOK_STATUS_AVAILABLE)) {
                //判断读者是否是自己
                if (!book.getOwnerId().equals(readerId)) {
                    success = borrowService.borrowBook(bookId, readerId, ownerId, book.getLastDay());
                    //如果流水表插入成功
                    if (success) {
                        WxUser owner = userService.getUserByOpenId(ownerId);
                        //TODO 给图书主人发消息
                        String ownerContent = "出借成功！您的图书《" + book.getBookName() + "》已成功出借给" + reader.getNickName() + "!";
                        logger.info(ownerContent);
                    } else {
                        //TODO 插入错误流水表
                        logger.error("借书失败！");
                    }
                } else {
                    response.setCode(RespStatus.FAIL.getCode());
                    response.setMessage("借出失败，自己不可以借自己的书哦！");
                }
            } else {
                response.setCode(RespStatus.FAIL.getCode());
                response.setMessage("图书已被借出，请不要多次借阅!");
            }
        }
        logger.info("-----返回信息：" + response.toString());
        logger.info("----------借阅结束-------");
    }

    private void returnBack(BaseResponse response, String bookId, String ownerId, String readerId) {
        if (bookId == null || readerId == null) {
            // 缺少必要参数
            response.setCode(RespStatus.LACK_PARAM.getCode());
            response.setMessage(RespStatus.LACK_PARAM.getMessage());
        } else {
            boolean success = true;
            logger.info("-----readerId：" + readerId + ";归还bookId:" + bookId + ";ownerId:" + ownerId);
            // 先判断图书状态
            Book book = bookService.getBookById(bookId);
            // 如果是借出，则执行还书操作
            if (book.getStatus().equals(Constants.BOOK_STATUS_OUT)) {
                //判断图书是否归还给了主人
                if (book.getOwnerId().equals(ownerId)) {
                    success = borrowService.returnBook(bookId, readerId, ownerId);
                    //如果流水表插入成功
                    if (success) {
                        WxUser owner = userService.getUserByOpenId(ownerId);
                        String readerContent = "还书成功！您已成功归还" + owner.getNickName() + "的《" + book.getBookName() + "》!";
                        logger.info(readerContent);
                    } else {
                        //插入错误流水表
                        logger.error("还书失败！");
                    }
                } else {
                    response.setCode(RespStatus.FAIL.getCode());
                    response.setMessage("归还失败，你不是图书主人哦！");
                }
            } else {
                // 缺少必要参数
                response.setCode(RespStatus.FAIL.getCode());
                response.setMessage("图书已还，无需还书！");
            }
        }
        logger.info("-----返回信息：" + response.toString());
        logger.info("----------归还结束-------");
    }

}
