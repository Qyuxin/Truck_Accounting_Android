package com.xin.hcjz.bean;

import java.io.Serializable;

public class UpdownAndPriceBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String updown;
	private String price;
	public UpdownAndPriceBean(String updown, String price) {
		super();
		this.updown = updown;
		this.price = price;
	}
	public String getUpdown() {
		return updown;
	}
	public void setUpdown(String updown) {
		this.updown = updown;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
