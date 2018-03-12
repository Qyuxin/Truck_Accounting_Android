package com.xin.hcjz.bean;

import java.io.Serializable;

public class OrderInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String orderNo;// 订单编号 时间戳
    private String com;// 厂名
    private String start;// 开始地址
    private String end;// 结束地址
    private String price;// 价格
    private String shrq;// 送货日期
    private String shsjd;// 送货时间段
    private String scry;// 上传人员
    private String scsj;// 上传时间
    private String desc;// 派车理由
    private String updown;// 是否上下货 0->否 1->是
    private String orderHavenumber;// 是否有票据 0-->无  1-->有
    private String orderNumber;// 票据单号

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getShrq() {
        return shrq;
    }

    public void setShrq(String shrq) {
        this.shrq = shrq;
    }

    public String getShsjd() {
        return shsjd;
    }

    public void setShsjd(String shsjd) {
        this.shsjd = shsjd;
    }

    public String getScry() {
        return scry;
    }

    public void setScry(String scry) {
        this.scry = scry;
    }

    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUpdown() {
        return updown;
    }

    public void setUpdown(String updown) {
        this.updown = updown;
    }

    public String getOrderHavenumber() {
        return orderHavenumber;
    }

    public void setOrderHavenumber(String orderHavenumber) {
        this.orderHavenumber = orderHavenumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrderInfoBean{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", com='").append(com).append('\'');
        sb.append(", start='").append(start).append('\'');
        sb.append(", end='").append(end).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", shrq='").append(shrq).append('\'');
        sb.append(", shsjd='").append(shsjd).append('\'');
        sb.append(", scry='").append(scry).append('\'');
        sb.append(", scsj='").append(scsj).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", updown='").append(updown).append('\'');
        sb.append(", orderHavenumber='").append(orderHavenumber).append('\'');
        sb.append(", orderNumber='").append(orderNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
