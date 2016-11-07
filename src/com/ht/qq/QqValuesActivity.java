package com.ht.qq;

import android.app.Application;

public class QqValuesActivity extends Application {
	private static final String NAME = "HLF";

	// 登录后获取的值
	private String login_zctouxiangend;// 头像
	private int login_zcaccountend;// 账号
	private String login_zcphoneend;// 手机号码
	private String login_zcnameend;// 昵称
	private String login_zcpwdend;// 密码
	private String login_zcsexend;// 性别
	private String login_zcaddressend;// 地址
	private String login_zcqianmingend;// 签名

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 设置初始化值
		// setLogin_zcaccountend(123456789);
	}

	public String getLogin_zctouxiangend() {
		return login_zctouxiangend;
	}

	public void setLogin_zctouxiangend(String login_zctouxiangend) {
		this.login_zctouxiangend = login_zctouxiangend;
	}

	public int getLogin_zcaccountend() {
		return login_zcaccountend;
	}

	public void setLogin_zcaccountend(int login_zcaccountend) {
		this.login_zcaccountend = login_zcaccountend;
	}

	public String getLogin_zcphoneend() {
		return login_zcphoneend;
	}

	public void setLogin_zcphoneend(String login_zcphoneend) {
		this.login_zcphoneend = login_zcphoneend;
	}

	public String getLogin_zcnameend() {
		return login_zcnameend;
	}

	public void setLogin_zcnameend(String login_zcnameend) {
		this.login_zcnameend = login_zcnameend;
	}

	public String getLogin_zcpwdend() {
		return login_zcpwdend;
	}

	public void setLogin_zcpwdend(String login_zcpwdend) {
		this.login_zcpwdend = login_zcpwdend;
	}

	public String getLogin_zcsexend() {
		return login_zcsexend;
	}

	public void setLogin_zcsexend(String login_zcsexend) {
		this.login_zcsexend = login_zcsexend;
	}

	public String getLogin_zcaddressend() {
		return login_zcaddressend;
	}

	public void setLogin_zcaddressend(String login_zcaddressend) {
		this.login_zcaddressend = login_zcaddressend;
	}

	public String getLogin_zcqianmingend() {
		return login_zcqianmingend;
	}

	public void setLogin_zcqianmingend(String login_zcqianmingend) {
		this.login_zcqianmingend = login_zcqianmingend;
	}

}
