package com.xin.hcjz.utils.datautils.okhttp3;

import com.xin.hcjz.cc.Session;
import com.xin.hcjz.debug.DebugConfig;

/**
 * Created by Y on 2018/2/27.
 */

public class UrlUtils {


    //    public static final String BASE_URL = "http://localhost:8080/HcjzService/";
    public static String BASE_URL = "http://i.jmtopapp.cn:8088/HcjzService/";
    public static String BASE_URL_DEBUG = "http://localhost:8080/HcjzService/";
    public static String BASE_URL_COMMON = "http://i.jmtopapp.cn:8088/HcjzService/";

    {
        if (DebugConfig.urlDebug == true) {
            BASE_URL = BASE_URL_DEBUG;
        }
    }

    public static final String USER_LOGIN = "userLogin";//登录
    public static final String GET_COMS = "getComs";//获取所有厂名
    public static final String GET_STARTS_BY_COM = "getStartsByCom";//根据厂名获取所有起始地址
    public static final String GET_ENDS_BY_START = "getEndsByStart";//根据起点获取终点
    public static final String GET_DEFAULT_INFO_BY_POSITION = "getDefaultInfoByPosition";//根据起点和终点获取订单默认信息

    public static final String ADD_ORDER = "addOrder";//上传账单
    public static final String GET_ORDERS = "getOrders";//获取账单
    public static final String UPDATE_ORDER = "updateOrder";//修改账单
    public static final String DEL_ORDER = "delOrder";//删除账单

    public static final String ADD_SHINFO = "addShinfo";//上传账单
    public static final String GET_SHINFOS = "getShinfos";//获取账单
    public static final String UPDATE_SHINFO = "updateShinfo";//修改账单
    public static final String DEL_SHINFO = "delShinfo";//删除账单


    public static String getUserLoginUrl(String username, String pwd) {
        System.out.println("访问URL-->" + BASE_URL + USER_LOGIN + "?username=" + username + "&pwd=" + pwd);
        return BASE_URL + USER_LOGIN + "?username=" + username + "&pwd=" + pwd;
    }

    public static String getEndsByStartUrl(String start) {
        return BASE_URL + GET_ENDS_BY_START + "?start=" + start;
    }

    public static String getDefaultInfoByPosition(String start, String end) {
        return BASE_URL + GET_DEFAULT_INFO_BY_POSITION + "?start=" + start + "&end=" + end;
    }

    /**
     * post内容："orderInfo":orderInfoBean
     */
    public static String getAddOrderUrlPost() {
        return BASE_URL + ADD_ORDER;
    }

    /**
     * post内容："orderCondition":orderConditionBean
     */
    public static String getGetOrdersUrlPost() {
        return BASE_URL + GET_ORDERS;
    }

    /**
     * 修改Order内容
     * post内容："orderInfo":orderInfoBean
     */
    public static String getUpdateOrderUrlPost() {
        return BASE_URL + UPDATE_ORDER;
    }

    //删除Order
    public static String getDelOrder(String orderNo) {
        return BASE_URL + DEL_ORDER + "?user=" + Session.USERNAME + "&orderNo=" + orderNo;
    }

    /**
     * 增加预留信息
     * post内容："shinfo":shinfoBean
     */
    public static String getAddShinfoUrlPost() {
        return BASE_URL + ADD_SHINFO;
    }

    /**
     * 获取所有预留信息
     */
    public static String getGetShinfosUrlPost() {
        return BASE_URL + GET_SHINFOS;
    }

    /**
     * 修改Shinfo内容
     * post内容："ShinfoInfo":ShinfoInfoBean
     */
    public static String getUpdateShinfoUrlPost() {
        return BASE_URL + UPDATE_SHINFO;
    }

    //删除Shinfo
    public static String getDelShinfo(String id) {
        return BASE_URL + DEL_SHINFO + "?id=" + id;
    }

}
