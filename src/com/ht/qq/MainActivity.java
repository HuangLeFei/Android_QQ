package com.ht.qq;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509CRLEntry;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ht.qq.sqllite.AccountPwdSqlite;
import com.ht.qq.sqllite.Constants;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.R.array;
import android.R.integer;
import android.app.ActionBar.LayoutParams;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnContextClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final static String TAG = MainActivity.class.getSimpleName();
	private Button mLoginImg;
	private EditText mLoginEdt;
	private EditText mLoginEdtm;
	private String account;
	private String accountm;
	private ImageView touxiang;// 头像
	private ImageView delzhnaghao;
	private ImageView delmima;
	private JSONArray jsonarraytxl;

	// login
	private String logintouxiang;// 头像
	private int loginzcaccount;// 账号
	private String loginzcpwd;// 密码
	private String loginzcphone;// 手机号码
	private String loginzcname;// 昵称
	private String loginzcsex;// 性别
	private String loginzcaddress;// 地址
	private String loginzcqianming;// 签名
	private int logingetzcaccount;// 获取share中的account
	private String logingetzcpwd;// 获取share中的pwd
	private String logingetzctouxiang;// 获取share中的touxiang
	private String ins;// 监听账号
	private String insp;// 监听密码
	private QqValuesActivity qqvalue;// 全局变量的值

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qq);
		androidLog("Huang");

		findView();// 找到控件
		setListener();// 添加事件
		loginlistener();// 监听事件
	}

	private void androidLog(String name) {// 日志文件
		Log.v(TAG + "  androidLog", name);
	}

	/**
	 * 找到控件
	 */
	private void findView() {
		mLoginImg = (Button) this.findViewById(R.id.login_image_dl);// 登录
		mLoginEdt = (EditText) this.findViewById(R.id.login_account_zh);// 账号
		mLoginEdtm = (EditText) this.findViewById(R.id.login_account_mm);// 密码
		delzhnaghao = (ImageView) this.findViewById(R.id.qq_xx1);// 清空账号
		delmima = (ImageView) this.findViewById(R.id.qq_xx2);// 清空密码
		touxiang = (ImageView) this.findViewById(R.id.qq_tupian);// 头像
	}

	/**
	 * 添加事件
	 */
	private void setListener() {
		mLoginImg.setOnClickListener(new OnClickListener() {// 账号
			@Override
			public void onClick(View v) {
				account = mLoginEdt.getText().toString();// 账号
				accountm = mLoginEdtm.getText().toString();// 密码

				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String urlStr = "http://" + Constants.URL + "/Android/HLF!sellogin?zcaccount=" + account
									+ "&zcpwd=" + accountm + "";
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

								if (jsonStr.equals("1")) {// 登录失败

									if (account.equals("")) {
										Looper.prepare();
										Toast toast = Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.TOP, 0, 0);
										toast.show();
										Looper.loop();
										return;
									} else if (accountm.equals("")) {
										Looper.prepare();
										Toast toast = Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.TOP, 0, 0);
										toast.show();
										Looper.loop();
										return;
									}

									Log.v(TAG, jsonStr + "     ");
									Looper.prepare();
									Toast toast = Toast.makeText(MainActivity.this, "你输入的账号或密码不匹配！", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.TOP, 0, 0);
									toast.show();
									Looper.loop();

								} else {// 登录成功
									Looper.prepare();
									Toast toast = Toast.makeText(MainActivity.this, "登录中. . .", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.TOP, 0, 0);
									toast.show();
									JSONArray array = new JSONArray(jsonStr);
									for (int i = 0; i < array.length(); i++) {
										JSONObject obj = (JSONObject) array.get(i);
										logintouxiang = (String) obj.opt("zctouxiang");// 头像
										loginzcaccount = (Integer) obj.opt("zcaccount");// 账号
										loginzcpwd = (String) obj.opt("zcpwd");// 密码
										loginzcphone = (String) obj.opt("zcphone");// 手机号码
										loginzcname = (String) obj.opt("zcname");// 昵称
										loginzcsex = (String) obj.opt("zcsex");// 性别
										loginzcaddress = (String) obj.opt("zcaddress");// 地址
										loginzcqianming = (String) obj.opt("zcqianming");// 签名
									}

									Log.v(TAG, "登录成功");
									btnClick();// 登录成功

									// 把账号记录保存到数据库
									setsharepreferencevalue();// 保存登录信息
									Looper.loop();
								}

								Log.v(TAG, jsonStr);

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

		delzhnaghao.setOnClickListener(new OnClickListener() {// 清空账号

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLoginEdt.setText("");
			}
		});

		delmima.setOnClickListener(new OnClickListener() {// 清空密码
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLoginEdtm.setText("");
			}
		});

	}

	private void setsharepreferencevalue() {
		SharedPreferences spreferences = this.getSharedPreferences("login", MODE_PRIVATE);

		// 数据发生变化时的监听
		spreferences.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {

			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
				// TODO Auto-generated method stub
				Log.v(TAG, "数据值发生了改变");
			}
		});
		Log.v(TAG, "头像  " + logintouxiang);
		// 设置值
		SharedPreferences.Editor editor = spreferences.edit();
		editor.putInt("zcaccount", loginzcaccount);
		editor.putString("zcpwd", loginzcpwd);
		editor.putString("zctouxiang", logintouxiang);
		editor.commit();
	}

	private void getsharepreferencevalue2() {
		SharedPreferences spreferences = this.getSharedPreferences("login", MODE_PRIVATE);
		logingetzcaccount = spreferences.getInt("zcaccount", 329150185);// 账号
		logingetzcpwd = spreferences.getString("zcpwd", "");// 密码
		logingetzctouxiang = spreferences.getString("zctouxiang", "");// 头像
	}

	/**
	 * 监听账号 是否已有
	 */
	private void loginlistener() {
		getsharepreferencevalue2();
		Log.v(TAG, logingetzctouxiang);
		if (logingetzctouxiang != "") {// 头像
			// 更换主页中的头像
			// 获取tomcat 服务器中的图片
			Picasso.with(MainActivity.this).load("http://" + Constants.URL + logingetzctouxiang).into(touxiang);
			// String myJpgPath = logingetzctouxiang;
			// File file = new File(myJpgPath);
			// if (file.exists()) {
			// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
			// touxiang.setImageBitmap(bm);
			// } else {
			// Log.v(TAG, "File Not Found!");
			// }
		}
		ins = logingetzcaccount + "";// 监听账号
		insp = logingetzcpwd;// 监听密码
		if (ins != "") {
			mLoginEdt.setText(ins);
		}
		if (insp != "") {
			mLoginEdtm.setText(insp);
		}
		mLoginEdt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

				if (s.toString().equals(ins)) {
					Toast toast = Toast.makeText(MainActivity.this, ins, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
					mLoginEdtm.setText(insp);
				} else if (!s.toString().equals(ins)) {
					mLoginEdtm.setText("");
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void btnClick() {// 登录

		// 设置为全局变量
		qqvalue = (QqValuesActivity) getApplication(); // 获得QqValuesActivity对象
		qqvalue.setLogin_zcaccountend(loginzcaccount); // 账号
		qqvalue.setLogin_zctouxiangend(logintouxiang);// 头像
		qqvalue.setLogin_zcphoneend(loginzcphone);// 手机号码
		qqvalue.setLogin_zcnameend(loginzcname);// 昵称
		qqvalue.setLogin_zcpwdend(loginzcpwd);// 密码
		qqvalue.setLogin_zcsexend(loginzcsex);// 性别
		qqvalue.setLogin_zcaddressend(loginzcaddress);// 地址
		qqvalue.setLogin_zcqianmingend(loginzcqianming);// 签名
		Intent intent = new Intent(this, QqMainActivity.class);
		// intent.setClass(MainActivity.this, QqFiendSelActivity.class);
		// 单个activity 之间传递参数
		// Bundle bundle = new Bundle();
		// bundle.putString("zctouxiang", logintouxiang);
		// bundle.putInt("zcaccount", loginzcaccount);
		// bundle.putString("zcphone", loginzcphone);
		// bundle.putString("zcname", loginzcname);
		// bundle.putString("zcpwd", loginzcpwd);
		// bundle.putString("zcsex", loginzcsex);
		// bundle.putString("zcaddress", loginzcaddress);
		// bundle.putString("zcqianming", loginzcqianming);
		// intent.putExtra("login", bundle);
		// 这是一种跳转的方式
		this.startActivity(intent);
	}

	public void zhuce(View view) {// 注册
		Intent intent = new Intent(this, QqZhuCeActivity.class);
		startActivity(intent);
	}

	private void HttpToRequest() {// 线程网络
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.v(TAG, "点了！");
				// 先于后台进行连接
				try {

					String urlStr = "http://" + Constants.URL + "/AccountSel/account";

					URL url = new URL(urlStr);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setRequestMethod("POST");
					// 如果请求成功 就把对象转化json对象
					if (connection.getResponseCode() == 200) {
						InputStream is = connection.getInputStream();
						String jsonStr = IOUtils.toString(is);
						Log.w(TAG, jsonStr);
						jsonarraytxl = new JSONArray(jsonStr);
						Log.v(TAG, "222222222" + jsonarraytxl.toString());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	public void accountsel(View view) {// 点击下拉显示账号
		HttpToRequest();
		LayoutInflater inflater = LayoutInflater.from(this);
		View contextview = inflater.inflate(R.layout.activity_accountpopupwindow, null);
		PopupWindow window = new PopupWindow(contextview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// 解决点击不能隐藏的问题
		window.setFocusable(true);
		window.setBackgroundDrawable(new BitmapDrawable());
		window.showAsDropDown(view);

		// WindowManager.LayoutParams params = getWindow().getAttributes();
		// params.alpha = 0.7f;

		// if (window.isShowing()) {
		// WindowManager.LayoutParams lp = getWindow().getAttributes();
		// lp.alpha = 0.5f; // 0.0-1.0
		// getWindow().setAttributes(lp);
		//
		// } else if(!window.isShowing()){
		// WindowManager.LayoutParams lp = getWindow().getAttributes();
		// lp.alpha = 1f; // 0.0-1.0
		// getWindow().setAttributes(lp);
		// }

	}

}
