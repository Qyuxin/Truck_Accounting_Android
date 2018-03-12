package com.xin.hcjz.bean;

import java.io.Serializable;

public class ShInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;//id
    private String com;//公司名称
    private String start;//开始地址
    private String end;//结束地址
    private String price;//送货价格
    private String updown;//是否上下货
    private String havenumber;//是否有回单

    public String getId() {
        return id;
    }

    public String getHavenumber() {
        return havenumber;
    }

    public void setHavenumber(String havenumber) {
        this.havenumber = havenumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpdown() {
        return updown;
    }

    public void setUpdown(String updown) {
        this.updown = updown;
    }


}
