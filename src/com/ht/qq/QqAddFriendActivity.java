package com.ht.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ht.qq.adapter.AddFriendAdapter;
import com.ht.qq.adapter.AddFriendAdapter.Callback;
import com.ht.qq.sqllite.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class QqAddFriendActivity extends Activity implements Callback {// 好友通知验证
	private final static String TAG = QqAddFriendActivity.class.getSimpleName();
	private ListView listviews;
	private JSONArray jsonarraytxl;
	// 查询好友
	private String selfnamevalue;// 自己昵称
	private String selftouxiangvalue;// 自己头像
	private String friendnamevalue;// 好友昵称
	private String friendtouxiangvalue;// 好友昵称
	private int getselzcaccountvalue;// 查找的账号 好友账号 494946020
	private int getselfzcaccountvalue;// 自己的账号 用户账号
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
	private Button agreebtn;// 同意
	private Button noagreebtn;// 不同意
	// 同意
	private int add_selfaccount;// 自己账号
	private int add_fzcaccount;// 好友账号
	private String add_fzcname;// 昵称
	private String add_fzselftouxiang;// 头像
	private String add_fzfzcname;// 好友昵称
	private String add_fztouxiang;// 好友头像

	// 不同意
	private int noadd_selfaccount;// 自己账号
	private int noadd_fzcaccount;// 好友账号
	private String noadd_fzcname;// 昵称

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriend);
		findviews();// 控件
		loginview();
		HttpToRequest();// 加载请求同意数据
	}

	private void findviews() {// 登录后获取的值
		listviews = (ListView) this.findViewById(R.id.list_addfriend);
		agreebtn = (Button) this.findViewById(R.id.list_friend_agree);
		noagreebtn = (Button) this.findViewById(R.id.list_friend_dontagree);
		// listviews.setOnItemClickListener(this);
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

	public void friendfanhui(View view) {// 新朋友返回
		finish();
	}

	public void friendaddsel(View view) {// 添加好友按钮
		Toast.makeText(this, "添加好友", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, QqFiendSelActivity.class);
		startActivity(intent);
	}

	private void textaddfriend() {// 好友添加详情
		AddFriendAdapter adapter = new AddFriendAdapter(this, jsonarraytxl, this);
		listviews.setAdapter(adapter);
	}

	private void HttpToRequest() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				jsonarraytxl = getPostContext();
				runOnUiThread(new Runnable() {
					public void run() {
						textaddfriend();
					}
				});
			}
		});
		thread.start();
	}

	private JSONArray getPostContext() {
		// 先于后台进行连接
		try {
			String urlStr = "http://" + Constants.URL + "/Android/HLF!agreeaddfriend?selfaccountagree="
					+ login_zcaccountend + "";

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			// 如果请求成功 就把对象转化json对象
			if (connection.getResponseCode() == 200) {
				InputStream is = connection.getInputStream();
				String jsonStrs = IOUtils.toString(is);
				Log.w(TAG, jsonStrs);

				if (jsonStrs.equals("1")) {// 已经是好友
					Looper.prepare();
					Toast toast = Toast.makeText(QqAddFriendActivity.this, "暂无添加好友请求！", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
					Looper.loop();
				} else { // 遍历好友请求
					jsonarraytxl = new JSONArray(jsonStrs);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonarraytxl;
	}

	/*
	 * 接口方法，响应ListView按钮点击事件
	 */
	@Override
	public void agreebtnadapterclick(View v) {// 同意按钮
		Log.v(TAG, "点击了同意");
		try {
//			Toast.makeText(QqAddFriendActivity.this,
//					"位置是:" + (Integer) v.getTag() + ",内容是:" + jsonarraytxl.get((Integer) v.getTag()),
//					Toast.LENGTH_SHORT).show();
			// 解析单个items的值
			Log.v(TAG, "arrayarrayarray  " + jsonarraytxl.get((Integer) v.getTag()));

			JSONObject arrayitems = (JSONObject) jsonarraytxl.get((Integer) v.getTag());
			add_selfaccount = (Integer) arrayitems.opt("selfaccount");// 自己账号
			add_fzcaccount = (Integer) arrayitems.opt("friendaccount");// 好友账号
			add_fzcname = (String) arrayitems.opt("selfname");// 昵称
			add_fzselftouxiang = (String) arrayitems.opt("selftouxiang");// 头像
			add_fzfzcname = (String) arrayitems.opt("friendname");// 好友昵称
			add_fztouxiang = (String) arrayitems.opt("friendtouxiang");// 好友头像
			Log.v(TAG, "自己账号" + add_selfaccount);
			Log.v(TAG, "好友账号" + add_fzcaccount);
			Log.v(TAG, "昵称" + add_fzcname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 点击同意添加好友请求
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String urlStr = "http://" + Constants.URL + "/Android/HLF!updfriendstate?selfaccount="
							+ add_selfaccount + "&friendaccoun=" + add_fzcaccount + "&selfnameend="
							+ URLEncoder.encode(add_fzcname, "utf-8") + "&selftouxiangend=" + add_fzselftouxiang
							+ "&fznameend=" + URLEncoder.encode(add_fzfzcname, "utf-8") + "&fztouxiangend="
							+ add_fztouxiang + "";
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
						Looper.prepare();
						Toast toast = Toast.makeText(QqAddFriendActivity.this, "恭喜你！成功添加好友。", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.TOP, 0, 0);
						toast.show();
						Looper.loop();

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();
		refresh();// 刷新
	}

	@Override
	public void noagreebtnadapterclick(View v) {// 不同意按钮
		Log.v(TAG, "不同意按钮");
		try {
//			Toast.makeText(QqAddFriendActivity.this,
//					"位置是:" + (Integer) v.getTag() + ",内容是:" + jsonarraytxl.get((Integer) v.getTag()),
//					Toast.LENGTH_SHORT).show();
			// 解析单个items的值
			Log.v(TAG, "arrayarrayarray  " + jsonarraytxl.get((Integer) v.getTag()));

			JSONObject arrayitems = (JSONObject) jsonarraytxl.get((Integer) v.getTag());
			noadd_selfaccount = (Integer) arrayitems.opt("selfaccount");// 自己账号
			noadd_fzcaccount = (Integer) arrayitems.opt("friendaccount");// 好友账号
			noadd_fzcname = (String) arrayitems.opt("selfname");// 昵称
			Log.v(TAG, "自己账号" + noadd_selfaccount);
			Log.v(TAG, "好友账号" + noadd_fzcaccount);
			Log.v(TAG, "昵称" + noadd_fzcname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 点击bu同意添加好友请求
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String urlStr = "http://" + Constants.URL + "/Android/HLF!deletefriend?delselfaccount="
							+ noadd_selfaccount + "&delfriendaccount=" + noadd_fzcaccount + "";
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
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();
		refresh();// 刷新
	}

	/**
	 * 响应ListView中item的点击事件
	 */
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View v, int position, long
	// id) {
	// Log.v(TAG, "不同意按钮31232213");
	// Toast.makeText(this, "listview的item被点击了！，点击的位置是-->" + position,
	// Toast.LENGTH_SHORT).show();
	// Log.v(TAG, "onItemClick 被点击了");
	// }

	/**
	 * 刷新
	 */
	private void refresh() {
		finish();
		Intent intent = new Intent(QqAddFriendActivity.this, QqAddFriendActivity.class);
		startActivity(intent);
	}

}
