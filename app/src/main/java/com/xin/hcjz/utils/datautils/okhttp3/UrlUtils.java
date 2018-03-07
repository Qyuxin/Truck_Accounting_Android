package com.xin.hcjz.utils.datautils.okhttp3;

/**
 * Created by Y on 2018/2/27.
 */

public class UrlUtils {

//    public static final String BASE_URL = "http://localhost:8080/HcjzService/";
    public static final String BASE_URL = "http://i.jmtopapp.cn:8088/HcjzService/";

    public static final String USER_LOGIN = "userLogin";//登录
    public static final String GET_COMS = "getComs";//获取所有厂名
    public static final String GET_STARTS_BY_COM = "getStartsByCom";//根据厂名获取所有起始地址
    public static final String GET_ENDS_BY_START = "getEndsByStart";//根据起点获取终点
    public static final String GET_UAP_BY_SAE = "getUAPBySAE";//根据起点和终点获取是否上下货和价格
    public static final String ADD_ORDER = "addOrder";//上传账单
    public static final String GET_ORDERS = "getOrders";//获取账单


    public static String getUserLoginUrl(String username, String pwd) {
        System.out.println("访问URL-->" + BASE_URL + USER_LOGIN + "?username=" + username + "&pwd=" + pwd);
        return BASE_URL + USER_LOGIN + "?username=" + username + "&pwd=" + pwd;
    }

    public static String getEndsByStartUrl(String start) {
        return BASE_URL + GET_ENDS_BY_START + "?start=" + start;
    }

    public static String getUAPBySAE(String start, String end) {
        return BASE_URL + GET_UAP_BY_SAE + "?start=" + start + "&end=" + end;
    }

    /**
     * post内容："orderInfo":orderInfoBean
     */
    public static String getAddOrdersUrlPost() {
        return BASE_URL + ADD_ORDER;
    }

    /**
     * post内容："orderCondition":orderConditionBean
     */
    public static String getGetOrdersUrlPost() {
        return BASE_URL + ADD_ORDER;
    }

}
