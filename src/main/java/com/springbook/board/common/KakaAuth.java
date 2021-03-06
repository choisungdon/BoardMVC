package com.springbook.board.common;

public class KakaAuth {
	private String access_token;
	private String refresh_token;
	private String token_type; // 안쓰지만 ObjectMapper에서 받을때 없으면 안됩니다.  
	private int expires_in;
	private int refresh_token_expires_in;
	
	
	@Override
	public String toString() {
		return "access_token :"+access_token+" refresh_token : "+refresh_token+" expires_in :"+expires_in+" refresh_token_expires_in : "+refresh_token_expires_in;
	}
	
	
	public String getToken_type() {
		return token_type;
	}


	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}


	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public int getRefresh_token_expires_in() {
		return refresh_token_expires_in;
	}
	public void setRefresh_token_expires_in(int refresh_token_expires_in) {
		this.refresh_token_expires_in = refresh_token_expires_in;
	}
	
	
}