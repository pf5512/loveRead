/*
 *	Copyright © 2013 China UnionPay All rights reserved.
 *	中国银联 版权所有
 *	http://www.unionpay.com
 */

package com.unionpay.loveRead.interceptor;

import com.unionpay.loveRead.controller.BaseController;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class GlobalInterceptor extends BaseController implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);
	@Autowired
	private WxMpService wxMpService;

	/**
	 * 网站域名、端口号信息
	 */
	private String baseUrl = null;
	/**
	 * 版本号格式
	 */
	public static final String DATE_FMT_YMDHMS = "yyyyMMddHHmmss";
	/**
	 * 版本日期信息，<b>存储为静态变量，启动服务器则更新</b>
	 */
	public static Date verDate = new Date();

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("PreHandle...开始");
		String userId = getSessionUid(request);
		if(StringUtils.isBlank(userId)){
			logger.info("Session为空,重新获取用户信息...");
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			logger.debug("code: " + code + ", state: " + state);
			if (!StringUtils.isBlank(code) && !StringUtils.isBlank(state)) {
				try {
					logger.info("Get OAuth access token:");
					WxMpOAuth2AccessToken wxMpOAuth2AccessToken =
							wxMpService.oauth2getAccessToken(code);
					logger.info("Token response:"+wxMpOAuth2AccessToken.toString());

					WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
					logger.info("user info:" + wxMpUser.toString());
					setSessionUid(request,wxMpUser.getOpenId());
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
		}else{
			logger.info("Session中userId为:"+userId+",不用重新获取用户信息。");
		}
        return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView modelView)
			throws Exception {

		request.setAttribute("baseUrl", baseUrl);
		// 获取当前访问链接
		String uri = request.getRequestURI();
		String contentPath = request.getContextPath();
		uri = uri.replace(contentPath, "");
		request.setAttribute("contentUri", uri);
		// 静态文件版本号
		request.setAttribute("staticVersion", new SimpleDateFormat(
				DATE_FMT_YMDHMS).format(verDate));
	}

	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
