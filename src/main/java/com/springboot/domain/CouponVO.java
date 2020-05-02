package com.springboot.domain;

public class CouponVO {
	private String couponId;
	private String sendDt;
	private String expireDt;
	private String stateSe;
	
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getSendDt() {
		return sendDt;
	}
	public void setSendDt(String sendDt) {
		this.sendDt = sendDt;
	}
	public String getExpireDt() {
		return expireDt;
	}
	public void setExpireDt(String expireDt) {
		this.expireDt = expireDt;
	}
	public String getStateSe() {
		return stateSe;
	}
	public void setStateSe(String stateSe) {
		this.stateSe = stateSe;
	}	
}
