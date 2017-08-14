package com.unionpay.loveRead.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 * 
 */
public class CommonUtil {

	/**
	 * 空字符串
	 */
	public static final String STR_BLANK = "";

	/**
	 * 将对象转成JSon格式,返回给前台
	 * 
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void writeResult(HttpServletResponse response, Object obj) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter write = response.getWriter();
			write.write(JSON.toJSONString(obj));
			write.flush();
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断当前request url是否和excludUrl中设置的url匹配
	 * 
	 * @return 匹配 -> true<br>
	 *         不匹配 -> false
	 */
	public static boolean isMatchUrl(HttpServletRequest request,
			String excludeUrl) throws ServletException {
		if (excludeUrl != null
				&& !excludeUrl.trim().equalsIgnoreCase(STR_BLANK)) {
			String contentPath = request.getContextPath();
			String url = request.getRequestURI();
			String[] urlArr = excludeUrl.split(",");
			for (int i = 0; i < urlArr.length; i++) {
				String excUrl = contentPath + urlArr[i].toString();
				if (excUrl.indexOf("*") > 0) {
					int a = excUrl.indexOf("*");
					excUrl = excUrl.substring(0, a);
				}
				if (url.startsWith(excUrl)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否为空字符串
	 * 
	 * @author jlni
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.isEmpty())
			return true;
		return false;
	}

	/**
	 * 字符串list以某分隔符连接
	 * 
	 * @author jlni
	 * @param strList
	 * @param sep
	 * @return
	 */
	public static String join(List<String> strList, String sep) {
		boolean isFirst = true;
		String joinStr = STR_BLANK;
		for (String str : strList) {
			if (isFirst) {
				joinStr += str;
				isFirst = false;
			} else {
				joinStr += sep + str;
			}

		}
		return joinStr;
	}

	/**
	 * 以当前时间为文件名
	 * @return
	 */
	public static String getFileNameByDate() {  
	    Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;  
    }

}
