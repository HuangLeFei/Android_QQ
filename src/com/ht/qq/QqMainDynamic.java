package com.ht.qq;

import java.io.File;

import com.ht.qq.sqllite.Constants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class QqMainDynamic extends Fragment {// QQ动态主页面
	private final static String TAG = QqMainxiaoxi.class.getSimpleName();
	private QqMainActivity qqmainactivity;// 上下文

	private ImageView mimageview;// 头像
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		qqmainactivity = (QqMainActivity) getActivity();// 获取上下文
		View view = inflater.inflate(R.layout.activity_main_dongtai, container, false);

		loginview();// 登录后的值

		// 更换主页中的头像
		mimageview = (ImageView) view.findViewById(R.id.list_view_image_3);
		Picasso.with(qqmainactivity).load("http://" + Constants.URL + login_zctouxiangend).into(mimageview);
		// String myJpgPath = login_zctouxiangend;
		// File file = new File(myJpgPath);
		// if (file.exists()) {
		// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
		// mimageview.setImageBitmap(bm);
		// } else {
		// Log.v(TAG, "File Not Found!");
		// }
		return view;
	}

	private void loginview() {
		qqvalue = (QqValuesActivity) qqmainactivity.getApplication(); // 获得QqValuesActivity对象
		login_zctouxiangend = qqvalue.getLogin_zctouxiangend();
		login_zcaccountend = qqvalue.getLogin_zcaccountend();
		login_zcphoneend = qqvalue.getLogin_zcphoneend();
		login_zcnameend = qqvalue.getLogin_zcnameend();
		login_zcpwdend = qqvalue.getLogin_zcpwdend();
		login_zcsexend = qqvalue.getLogin_zcsexend();
		login_zcaddressend = qqvalue.getLogin_zcaddressend();
		login_zcqianmingend = qqvalue.getLogin_zcqianmingend();
		// 单个activity之间获取参数
		// // 先拿到Intent
		// Intent intent = qqmainactivity.getIntent();
		// // String str = intent.getStringExtra("login");
		// Bundle bundle = intent.getBundleExtra("login");
		// login_zctouxiangend = bundle.getString("zctouxiang");
		// login_zcaccountend = bundle.getInt("zcaccount");
		// login_zcphoneend = bundle.getString("zcphone");
		// login_zcnameend = bundle.getString("zcname");
		// login_zcpwdend = bundle.getString("zcpwd");
		// login_zcsexend = bundle.getString("zcsex");
		// login_zcaddressend = bundle.getString("zcaddress");
		// login_zcqianmingend = bundle.getString("zcqianming");
	}
}
