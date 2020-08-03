package com.springbook.board.user;

public class UserVO {
	private int i_user;
	private String cid;
	private String cpw;
	private String salt;
	private String recpw;
	private String nm;
	private String caddr;
	private String ph;
	private String r_dt;
	private String phAuthNumber;
	
	
	
	
	public String getPhAuthNumber() {
		return phAuthNumber;
	}
	public void setPhAuthNumber(String phAuthNumber) {
		this.phAuthNumber = phAuthNumber;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getRecpw() {
		return recpw;
	}
	public void setRcpw(String rcpw) {
		this.recpw = rcpw;
	}
	
	public int getI_user() {
		return i_user;
	}
	public void setI_user(int i_user) {
		this.i_user = i_user;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCpw() {
		return cpw;
	}
	public void setCpw(String cpw) {
		this.cpw = cpw;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public String getCaddr() {
		return caddr;
	}
	public void setCaddr(String caddr) {
		this.caddr = caddr;
	}
	public String getR_dt() {
		return r_dt;
	}
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}
	
	
	
	
}
