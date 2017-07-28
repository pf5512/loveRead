package com.unionpay.loveRead.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseController {

    public static final String USER = "user";
    
    /**
     * 设置会话信息
     * 
     * @param request
     * @param name
     * @param value
     */
    public void setSession(HttpServletRequest request,String name, String value) {
        HttpSession session = request.getSession();
        session.setAttribute(name, value);
    }


    /**
     * 获取session中uid
     */
    public final String getSessionUid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String uid = (String) session.getAttribute(USER);
        if (uid == null) {
            uid = "";
        }
        return uid;
    }

    /**
     * 获取session中uid
     */
    public void setSessionUid(HttpServletRequest request, String value) {
        HttpSession session = request.getSession();
        session.setAttribute(USER, value);
    }
    /**
     * 清空用户会话信息
     * 
     * @param request
     */
    public void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //FIXME
        session.removeAttribute("");
    }

    /**
     * 为response提供Json格式的返回数据
     * 
     * @param obj
     *            任何对象
     * @return void
     */
    public void writeResponse(Object obj, HttpServletResponse response) {
        try {
            response.setContentType("text/json;charset=utf-8");

            String str = JSON.toJSONString(obj);

            PrintWriter writer;
            writer = response.getWriter();
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 为response提供String数据的返回，字符集为utf-8
     * 
     * @param str
     *            字符串
     * @return void
     */
    public void writeResponseStr(String str, HttpServletResponse response) {

        writeResponseStr(str, response, "utf-8");
    }

    /**
     * 为response提供String数据的返回
     * 
     * @param str
     *            字符串
     * @param encoding
     *            字符编码
     */
    public void writeResponseStr(String str, HttpServletResponse response,
            String encoding) {
        try {
            response.setContentType("text/html;charset=" + encoding);

            PrintWriter writer;
            writer = response.getWriter();
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前登录用户的IP地址
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public String getIpAddr(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 获取请求参数中所有的信息
     * 
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);

                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /**
     * 将页面请求参数转化为Map类型数据，方便调用，不保存:reqInfo
     * 
     * @param requestParam
     * @return
     */
    public JSONObject processRequest(String requestParam) {
        JSONObject result = new JSONObject();

        if (!StringUtils.isBlank(requestParam)) {

            String[] params = requestParam.split("&");
            if (params.length > 0) {
                int index = 0;
                for (int i = 0; i < params.length; i++) {
                    index = params[i].indexOf('=');
                    if (index > 0) {
                        String key = params[i].substring(0, index);
                        String value = params[i].substring(index + 1);

                        if (!"reqInfo".equals(key)) {
                            if (result.containsKey(key)) {

                                // 已存储为数组
                                JSONArray _valueArr = result.getJSONArray(key);
                                if (_valueArr != null) {
                                    _valueArr.add(value);
                                    result.put(key, _valueArr);
                                    continue;
                                }

                                // 已存储为字符串
                                String _value = result.getString(key);
                                if (_value != null) {
                                    _valueArr = new JSONArray();
                                    _valueArr.add(_value);
                                    _valueArr.add(value);
                                    result.put(key, _valueArr);
                                } else {
                                    _valueArr = result.getJSONArray(key);
                                    _valueArr.add(value);
                                    result.put(key, _valueArr);
                                }

                            } else {

                                result.put(key, value);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}
