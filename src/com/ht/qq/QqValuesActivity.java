package com.ht.qq;

import android.app.Application;

public class QqValuesActivity extends Application {
	private static final String NAME = "HLF";

	// ��¼���ȡ��ֵ
	private String login_zctouxiangend;// ͷ��
	private int login_zcaccountend;// �˺�
	private String login_zcphoneend;// �ֻ�����
	private String login_zcnameend;// �ǳ�
	private String login_zcpwdend;// ����
	private String login_zcsexend;// �Ա�
	private String login_zcaddressend;// ��ַ
	private String login_zcqianmingend;// ǩ��

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// ���ó�ʼ��ֵ
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
