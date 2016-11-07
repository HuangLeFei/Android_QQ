package com.ht.qq;

import android.app.ActionBar.LayoutParams;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ht.qq.adapter.FriendFenZuAdapter;
import com.ht.qq.bean.QqFrienduser;
import com.ht.qq.sqllite.Constants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.sip.SipSession.State;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QqMainActivity extends FragmentActivity implements OnClickListener {
	private final static String TAG = QqMainActivity.class.getSimpleName();
	private LayoutInflater inflater;
	private SlidingMenu slidingMenu;
	private JSONArray jsonarraytxl;
	private QqMainLianxiren aa;
	private ImageView sidingimg;// 侧滑里的头像
	private TextView sidingname;// 侧滑里的昵称
	private Button sidingqianming;// 侧滑里的签名

	private RelativeLayout relativelayout;
	private ImageView xiaoxiimg;
	private ImageView lianxirenimg;
	private ImageView dongtaiimg;

	private QqMainxiaoxi xiaoxiFragment;// 消息页面
	private QqMainLianxiren lianxirenFragment;// 联系人页面
	private QqMainDynamic dongtaiFragment;// 动态页面

	FragmentManager manager;

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
	protected void onCreate(Bundle savedInstanceState) {// 主页面
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
		findview();// 控件
		initFragment();
		setlistenter();// 监听
		loginview();// 登录后的值

		// 获取 FragmentManager
		manager = this.getSupportFragmentManager();
		// 拿到一个处理事务
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.list_qq_layoutrel, xiaoxiFragment);
		transaction.commit();
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 设置模式为左侧滑
		slidingMenu.setMode(SlidingMenu.LEFT);
		// 设置侧滑菜单显示的布局
		slidingMenu.setMenu(R.layout.activity_slidingmenu);
		// 设置菜单出来的触摸方式,此处指定全屏
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置侧滑菜单出来的宽度为屏幕宽度的0.5
		slidingMenu.setBehindWidth((int) (getResources().getDisplayMetrics().widthPixels * 0.85));
		// 设置侧滑效果:渐变效果
		slidingMenu.setFadeDegree(0.5f);

		// 设置 侧滑的imageview头像
		sidingimg = (ImageView) slidingMenu.findViewById(R.id.list_siding_touxiang);
		
		//获取tomcat 服务器中的图片
		Picasso.with(QqMainActivity.this)
		.load("http://" + Constants.URL+login_zctouxiangend).into(sidingimg);
		
//		String myJpgPath = Constants.URL+"/Android/images/head_2.png";// 头像地址
//		File file = new File(myJpgPath);
//		if (file.exists()) {
//			Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
//			sidingimg.setImageBitmap(bm);
//		} else {
//			Log.v(TAG, "File Not Found!");
//		}
		// 设置侧滑的昵称
		sidingname = (TextView) slidingMenu.findViewById(R.id.list_siding_name);
		sidingname.setText(login_zcnameend);
		// 设置侧滑的签名
		sidingqianming = (Button) slidingMenu.findViewById(R.id.list_siding_qianming);
		sidingqianming.setText(login_zcqianmingend);

	}

	private void findview() {// 控件
		relativelayout = (RelativeLayout) this.findViewById(R.id.list_qq_layoutrel);
		xiaoxiimg = (ImageView) this.findViewById(R.id.list_xiaoxi_img);
		lianxirenimg = (ImageView) this.findViewById(R.id.list_lianxiren_img);
		dongtaiimg = (ImageView) this.findViewById(R.id.list_dongtai_img);
		// inflater = LayoutInflater.from(this);
		// View v = inflater.inflate(R.layout.activity_main_lianxiren, null);
		// listviews = (ExpandableListView)
		// v.findViewById(R.id.contact_expandablelist);
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
		// // 先拿到Intent
		// Intent intent = this.getIntent();
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

	private void initFragment() {// 创建
		xiaoxiFragment = new QqMainxiaoxi();
		lianxirenFragment = new QqMainLianxiren();
		dongtaiFragment = new QqMainDynamic();
	}

	// 监听
	private void setlistenter() {
		xiaoxiimg.setOnClickListener(this);
		lianxirenimg.setOnClickListener(this);
		dongtaiimg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = manager.beginTransaction();
		switch (v.getId()) {
		case R.id.list_xiaoxi_img:
			transaction.replace(R.id.list_qq_layoutrel, xiaoxiFragment);
			xiaoxiimg.setImageResource(R.drawable.skin_tab_icon_conversation_selected);// 亮
			lianxirenimg.setImageResource(R.drawable.skin_tab_icon_contact_normal);
			dongtaiimg.setImageResource(R.drawable.skin_tab_icon_plugin_normal);
			break;
		case R.id.list_lianxiren_img:
			transaction.replace(R.id.list_qq_layoutrel, lianxirenFragment);
			xiaoxiimg.setImageResource(R.drawable.skin_tab_icon_conversation_normal);
			lianxirenimg.setImageResource(R.drawable.skin_tab_icon_contact_selected);// 亮
			dongtaiimg.setImageResource(R.drawable.skin_tab_icon_plugin_normal);
			break;
		case R.id.list_dongtai_img:
			transaction.replace(R.id.list_qq_layoutrel, dongtaiFragment);
			xiaoxiimg.setImageResource(R.drawable.skin_tab_icon_conversation_normal);
			lianxirenimg.setImageResource(R.drawable.skin_tab_icon_contact_normal);
			dongtaiimg.setImageResource(R.drawable.skin_tab_icon_plugin_selected);// 亮
			break;
		}
		transaction.commit();
	}

	// 手机物理键打开
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			slidingMenu.toggle(true);
			break;
		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void showpopupwindow(View view) {// popupwindow 窗口
		LayoutInflater inflater = LayoutInflater.from(this);
		View contextview = inflater.inflate(R.layout.activity_popupwindow, null);
		PopupWindow window = new PopupWindow(contextview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		Button jiafriend = (Button) contextview.findViewById(R.id.add_friend);
		jiafriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(QqMainActivity.this, QqFiendSelActivity.class);
				startActivity(intent);
			}
		});
		// 解决点击不能隐藏的问题
		window.setFocusable(true);
		window.setBackgroundDrawable(new BitmapDrawable());
		window.showAsDropDown(view);

		// 先找到锚点的中点，找到他的宽度
		// int anchorWidth = view.getWidth();
		// 找到contentView也是要找到contentView的宽度
		// int contentViewWidth = contextview.getWidth();// 这是有问题的。
		// contextview.measure(MeasureSpec.UNSPECIFIED,
		// MeasureSpec.UNSPECIFIED);
		// int measureWidth = contextview.getMeasuredWidth();

		// window.showAsDropDown(view, (anchorWidth-measureWidth)/2, 0);
	}

	public void lianxirentianjia(View view) {
		Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QqMainActivity.this, QqFiendSelActivity.class);
		startActivity(intent);
	}

	public void dynamicgengduo(View view) {
		Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
	}

	public void shezhiClick(View view) {// 设置
		Intent intent = new Intent(this, QqShezhiActivity.class);
		startActivity(intent);
	}

	public void slidingmenuonclick(View view) {// 设置点击左上角图片时侧滑
		Toast.makeText(this, "touxiang", Toast.LENGTH_SHORT).show();
		slidingMenu.toggle(true);
	}

	public void dynaiconclick(View view) {// 动态页面
		Intent intent = new Intent(QqMainActivity.this, DongtTaiPublishedActivity.class);
		startActivity(intent);
	}

	public void addfriendonclick(View view) {// 新朋友
		Intent intent = new Intent(this, QqAddFriendActivity.class);
		// loginview();
		Bundle bundle = new Bundle();
		bundle.putString("zctouxiang", login_zctouxiangend);
		bundle.putInt("zcaccount", login_zcaccountend);
		bundle.putString("zcphone", login_zcphoneend);
		bundle.putString("zcname", login_zcnameend);
		bundle.putString("zcpwd", login_zcpwdend);
		bundle.putString("zcsex", login_zcsexend);
		bundle.putString("zcaddress", login_zcaddressend);
		bundle.putString("zcqianming", login_zcqianmingend);
		intent.putExtra("login", bundle);
		// 这是一种跳转的方式
		startActivity(intent);
	}

	// @Override
	// public void itemsadapterclick(View v) {
	//// Log.v(TAG, "dadsssssssss");
	// Toast.makeText(QqMainActivity.this,
	// "位置是:" + (Integer) v.getTag() + ",内容是:" + v.getTag(),
	// Toast.LENGTH_SHORT).show();
	// }

}
