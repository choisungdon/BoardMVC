package com.springbook.board;

public class BoardVO {
	private int i_board;
	private String title;
	private String ctnt;
	private String r_dt;
	private int sidx;
	private int count;
	private String serchText;
	
	
	public String getSerchText() {
		return serchText;
	}
	public void setSerchText(String serchText) {
		this.serchText = serchText;
	}
	public int getSidx() {
		return sidx;
	}
	public void setSidx(int sidx) {
		this.sidx = sidx;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getI_board() {
		return i_board;
	}
	public void setI_board(int i_board) {
		this.i_board = i_board;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCtnt() {
		return ctnt;
	}
	public void setCtnt(String ctnt) {
		this.ctnt = ctnt;
	}
	public String getR_dt() {
		return r_dt;
	}
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}
	
	
	 
}
