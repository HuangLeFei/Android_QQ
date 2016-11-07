package com.ht.qq;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.ht.qq.adapter.DynamicAdapterActivity;
import com.ht.qq.adapter.QqRecentlyMsgAdapter;
import com.ht.qq.bean.DynamicBean;
import com.ht.qq.bean.QQXiaoxi;
import com.ht.qq.sqllite.Constants;
import com.squareup.picasso.Picasso;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class DongtTaiPublishedActivity extends Activity {
	private final static String TAG = DongtTaiPublishedActivity.class.getSimpleName();
	private ListView listviews;
	private JSONArray jsonarraytxl;
	private ImageView imagedianzan;
	PopupWindow window;
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

	private ImageView dytouxiang;// 头像

	ArrayList<DynamicBean> lists;
	DynamicBean dybean;
	DynamicAdapterActivity adapter;
	/**
	 * 消息类型
	 */
	private final static int TIME_WHAT = 1;
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			if (msg.what == TIME_WHAT) {
				listviews.setAdapter(adapter);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dongtaipublished);
		listviews = (ListView) this.findViewById(R.id.list_dongtai_listview);
		ImageView qzbeijing = (ImageView) this.findViewById(R.id.list_dongtai_beijinimg);
		Glide.with(this).load(R.drawable.qzbeijing).into(qzbeijing);
		loginview();
		HttpToRequest();
		findview();
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

	private void findview() {
		imagedianzan = (ImageView) this.findViewById(R.id.list_dongtai_dianzan);// 点赞图片
		dytouxiang = (ImageView) this.findViewById(R.id.list_dongtai_datouxiang);

		// 获取tomcat 服务器中的图片
		Picasso.with(DongtTaiPublishedActivity.this).load("http://" + Constants.URL + login_zctouxiangend)
				.into(dytouxiang);
		// String myJpgPath = login_zctouxiangend;// 头像地址
		// File file = new File(myJpgPath);
		// if (file.exists()) {
		// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
		// dytouxiang.setImageBitmap(bm);
		// } else {
		// Log.v(TAG, " File Not Found!");
		// }

		LinearLayout linbeijin = (LinearLayout) this.findViewById(R.id.list_dongtai_layoutxsgy);
		linbeijin.getBackground().setAlpha(100);// 0~255透明度值

	}

	public void dynamicdianzan(View view) {// 放大
		Log.v(TAG, "放大");
		ImageView iv = (ImageView) this.findViewById(R.id.list_dongtai_dianzan);
		// 先加载到一个动画
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.activity_scale);
		// 把动画设置到控件中
		iv.setAnimation(anim);
		// 启动动画
		anim.start();
	}

	public void fanhui(View view) {// 返回
		finish();
	}

	// private void textdongtailogin() {
	// DynamicAdapterActivity adapter = new DynamicAdapterActivity(this,
	// jsonarraytxl);
	// listviews.setAdapter(adapter);
	//
	// }

	private void HttpToRequest() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				seleqqdynamic();
				// handler.sendEmptyMessage(TIME_WHAT);
			}

		});
		thread.start();
	}

	// 查询 动态
	private ArrayList<DynamicBean> seleqqdynamic() {
		// 先于后台进行连接
		try {
			String urlStr = "http://" + Constants.URL + "/Android/HLF!igetdynamic?dynamicselfaccount="
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
				Log.v(TAG, "jsonStrs 动态" + jsonStrs);
				if (jsonStrs.equals("1")) {// 无动态
					Toast toast = Toast.makeText(DongtTaiPublishedActivity.this, "暂无好友发表动态！", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
				} else { // 遍历 聊天记录
					JSONArray list = new JSONArray(jsonStrs);
					lists = new ArrayList<DynamicBean>();
					for (int i = 0; i < list.length(); i++) {
						dybean = new DynamicBean();
						JSONObject obj = list.getJSONObject(i);
						dybean.setSenddynamicaccount(obj.getInt("senddynamicaccount"));
						dybean.setSenddynamictouxiang(obj.getString("senddynamictouxiang"));
						dybean.setSenddynamicname(obj.getString("senddynamicname"));
						dybean.setSenddynamiccontent(obj.getString("senddynamiccontent"));
						dybean.setSenddynamictime(obj.getString("senddynamictime"));
						lists.add(dybean);
					}
					adapter = new DynamicAdapterActivity(DongtTaiPublishedActivity.this, lists);
					handler.sendEmptyMessage(TIME_WHAT);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}

	// private JSONArray getPostContext() {
	// // 先于后台进行连接
	// try {
	//
	// String urlStr = "http://" + Constants.URL +
	// "/Android/HLF!igetdynamic?dynamicselfaccount="
	// + login_zcaccountend + "";
	//
	// URL url = new URL(urlStr);
	// HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	// connection.setDoOutput(true);
	// connection.setDoInput(true);
	// connection.setRequestMethod("POST");
	// // 如果请求成功 就把对象转化json对象
	// if (connection.getResponseCode() == 200) {
	// InputStream is = connection.getInputStream();
	// String jsonStr = IOUtils.toString(is);
	// Log.w(TAG, jsonStr);
	//
	// if (jsonStr.equals("1")) {// 没有数据
	// Log.v(TAG, "无数据");
	// Toast toast = Toast.makeText(DongtTaiPublishedActivity.this, "暂无好友发表动态！",
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.TOP, 0, 0);
	// toast.show();
	// } else {
	// jsonarraytxl = new JSONArray(jsonStr);
	// }
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return jsonarraytxl;
	// }

	public void showpopupwindowdynamic(View view) {// popupwindow 窗口
		LayoutInflater inflater = LayoutInflater.from(this);
		View contextview = inflater.inflate(R.layout.activity_popupwindowdynamic, null);
		window = new PopupWindow(contextview, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ImageView sendshuoshuo = (ImageView) contextview.findViewById(R.id.list_dynamic_shuoshuo);
		sendshuoshuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DongtTaiPublishedActivity.this, SendShuoActivity.class);
				startActivity(intent);
				finish();
			}
		});
		// 解决点击不能隐藏的问题
		window.setFocusable(true);
		window.setBackgroundDrawable(new BitmapDrawable());
		// 显示在顶部
		window.showAtLocation(view, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 20);
	}

	public void dynamicclose(View view) {// 取消发说说
		window.dismiss();
	}

}
