package com.ht.qq.bean;

public class QQUserBean {
	private int imgid;
	private String stuname;
	private String shuoming;

	public QQUserBean(int imgid, String stuname, String shuoming) {
		super();
		this.imgid = imgid;
		this.stuname = stuname;
		this.shuoming = shuoming;
	}

	public int getImgid() {
		return imgid;
	}

	public void setImgid(int imgid) {
		this.imgid = imgid;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getShuoming() {
		return shuoming;
	}

	public void setShuoming(String shuoming) {
		this.shuoming = shuoming;
	}

}
