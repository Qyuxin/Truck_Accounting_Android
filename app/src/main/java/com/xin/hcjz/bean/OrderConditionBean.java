package com.xin.hcjz.bean;

public class OrderConditionBean {
	private String orderNo;// 订单编号 时间戳
	private String com;// 厂名
	private String start;// 开始地址
	private String end;// 结束地址
	private String price;// 价格
	private String shrqStart;// 起始上货日期
	private String shrqEnd;// 最终上货日期
	private String scry;// 上传人员
	private String scsjStart;// 起始上传时间
	private String scsjEnd;// 最终上传时间
	private String desc;// 派车理由
	private String updown;// 是否上下货 0->否 1->是
	private String orderHavenumber;//是否有票据
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
	public String getShrqStart() {
		return shrqStart;
	}
	public void setShrqStart(String shrqStart) {
		this.shrqStart = shrqStart;
	}
	public String getShrqEnd() {
		return shrqEnd;
	}
	public void setShrqEnd(String shrqEnd) {
		this.shrqEnd = shrqEnd;
	}
	public String getScry() {
		return scry;
	}
	public void setScry(String scry) {
		this.scry = scry;
	}
	public String getScsjStart() {
		return scsjStart;
	}
	public void setScsjStart(String scsjStart) {
		this.scsjStart = scsjStart;
	}
	public String getScsjEnd() {
		return scsjEnd;
	}
	public void setScsjEnd(String scsjEnd) {
		this.scsjEnd = scsjEnd;
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
	

}
