package com.xin.hcjz.bean;

import java.io.Serializable;

public class DefaultInfoOfOrderBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String updown;
    private String price;
    private String haveNumber;

    public DefaultInfoOfOrderBean(String updown, String price, String haveNumber) {
        super();
        this.updown = updown;
        this.price = price;
        this.haveNumber = haveNumber;
    }

    public void setUpdown(String updown) {
        this.updown = updown;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setHaveNumber(String haveNumber) {
        this.haveNumber = haveNumber;
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public String getUpdown() {
        return updown;
    }

    public String getPrice() {
        return price;
    }

    public String getHaveNumber() {
        return haveNumber;
    }
}
