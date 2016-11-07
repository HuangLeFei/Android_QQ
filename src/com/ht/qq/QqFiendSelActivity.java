package com.ht.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ht.qq.sqllite.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QqFiendSelActivity extends Activity {// 添加好友
	private final static String TAG = QqFiendSelActivity.class.getSimpleName();
	private EditText accountsel;
	private Button addbtn;

	private String account;// 账号
	// 登录后获取的值
	private String login_zctouxiangend;// 头像
	private int login_zcaccountend;// 账号
	private String login_zcphoneend;// 手机号码
	private String login_zcnameend;// 昵称
	private String login_zcpwdend;// 密码
	private String login_zcsexend;// 性别
	private String login_zcaddressend;// 地址
	private String login_zcqianmingend;// 签名
	private QqValuesActivity qqvalue;// 全局变量的值

	// 查询账号之后获取的值
	private String addfztouxiang;// 头像
	private int addfzcaccount;// 账号
	private String addfzcname;// 昵称

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendsel);
		finviews();
		loginview();// 登路后的值
		selaccount();// 添加好友
	}

	private void finviews() {
		accountsel = (EditText) this.findViewById(R.id.list_selfriend);
		addbtn = (Button) this.findViewById(R.id.list_selfriend_selbtn);
	}

	private void loginview() {
		qqvalue = (QqValuesActivity) getApplication(); // 获得QqValuesActivity对象
		login_zctouxiangend = qqvalue.getLogin_zctouxiangend();
		login_zcaccountend = qqvalue.getLogin_zcaccountend();
		login_zcphoneend = qqvalue.getLogin_zcphoneend();
		login_zcnameend = qqvalue.getLogin_zcnameend();
		login_zcpwdend = qqvalue.getLogin_zcpwdend();
		login_zcsexend = qqvalue.getLogin_zcsexend();
		login_zcaddressend = qqvalue.getLogin_zcaddressend();
		login_zcqianmingend = qqvalue.getLogin_zcqianmingend();
	}

	private void isfriend() {// 判断是否为好友
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String urlStr = "http://"+Constants.URL+"/Android/HLF!isfriend?getselfzcaccount=" + addfzcaccount
							+ "&getselzcaccount=" + login_zcaccountend + "&selfname="
							+ URLEncoder.encode(login_zcnameend, "utf-8") + "&selftouxiang=" + login_zctouxiangend
							+ "&friendname=" + URLEncoder.encode(addfzcname, "utf-8") + "&friendtouxiang="
							+ addfztouxiang + "";
					URL url = new URL(urlStr);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setRequestMethod("POST");

					// 如果请求成功 就把对象转化json对象
					if (connection.getResponseCode() == 200) {
						InputStream is = connection.getInputStream();
						String jsonStrs = IOUtils.toString(is);
						Log.v(TAG, jsonStrs);
						if (jsonStrs.equals("1")) {// 已经是好友
							Log.v(TAG, jsonStrs + "     ");
							Looper.prepare();
							Toast toast = Toast.makeText(QqFiendSelActivity.this, "已经是好友了！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();

						} else {// 不是好友就添加

							Looper.prepare();
							Toast toast = Toast.makeText(QqFiendSelActivity.this, "你已成功发送添加好友请求,请等待对方审核！",
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();
	}

	private void selaccount() {// 先查询后添加好友
		addbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				account = accountsel.getText().toString();// 账号
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String urlStr = "http://"+Constants.URL+"/Android/HLF!selfriends?getselfzcaccount="
									+ account + "";
							URL url = new URL(urlStr);
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setDoOutput(true);
							connection.setDoInput(true);
							connection.setRequestMethod("POST");

							// 如果请求成功 就把对象转化json对象
							if (connection.getResponseCode() == 200) {
								InputStream is = connection.getInputStream();
								String jsonStr = IOUtils.toString(is);
								Log.v(TAG, jsonStr);
								if (jsonStr.equals("1")) {// 添加失败
									Log.v(TAG, jsonStr + "     ");
									Looper.prepare();
									Toast toast = Toast.makeText(QqFiendSelActivity.this, "你输入的账号不存在！",
											Toast.LENGTH_LONG);
									toast.setGravity(Gravity.TOP, 0, 0);
									toast.show();
									Looper.loop();

								} else {// 判断是否为好友
									JSONArray array = new JSONArray(jsonStr);
									for (int i = 0; i < array.length(); i++) {
										JSONObject obj = (JSONObject) array.get(i);
										addfztouxiang = (String) obj.opt("zctouxiang");// 头像
										addfzcaccount = (Integer) obj.opt("zcaccount");// 账号
										addfzcname = (String) obj.opt("zcname");// 昵称
										Log.v(TAG, "" + array);
										Log.v(TAG, "" + addfztouxiang);
									}
									isfriend();
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
				thread.start();
			}
		});
	}

	public void addfanhui(View view) {// 添加返回
		finish();
	}

}
