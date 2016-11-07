package com.ht.qq.bean;

public class QqFrienduser {
	private String selfname;// 用户名字
	private String selftouxiang;// 用户头像
	private int selfaccount;// 用户账号
	private int friendaccount;// 好友账号
	private String friendname;// 好友名字
	private String friendtouxiang;// 好友头像
	private int friendfenzu;// 分组

	public String getSelfname() {
		return selfname;
	}

	public void setSelfname(String selfname) {
		this.selfname = selfname;
	}

	public String getSelftouxiang() {
		return selftouxiang;
	}

	public void setSelftouxiang(String selftouxiang) {
		this.selftouxiang = selftouxiang;
	}

	public int getSelfaccount() {
		return selfaccount;
	}

	public void setSelfaccount(int selfaccount) {
		this.selfaccount = selfaccount;
	}

	public int getFriendaccount() {
		return friendaccount;
	}

	public void setFriendaccount(int friendaccount) {
		this.friendaccount = friendaccount;
	}

	public String getFriendname() {
		return friendname;
	}

	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}

	public String getFriendtouxiang() {
		return friendtouxiang;
	}

	public void setFriendtouxiang(String friendtouxiang) {
		this.friendtouxiang = friendtouxiang;
	}

	public int getFriendfenzu() {
		return friendfenzu;
	}

	public void setFriendfenzu(int friendfenzu) {
		this.friendfenzu = friendfenzu;
	}

}
