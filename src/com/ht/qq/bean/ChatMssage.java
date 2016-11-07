package com.ht.qq.bean;

public class ChatMssage {
	private String stuname;//姓名
	private String content;//内容
	private int icon;//头像
	private int direction;//方向
	
	public ChatMssage(String stuname, String content, int icon, int direction) {
		super();
		this.stuname = stuname;
		this.content = content;
		this.icon = icon;
		this.direction = direction;
	}
	
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	
}
