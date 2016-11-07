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
	private ImageView sidingimg;// �໬���ͷ��
	private TextView sidingname;// �໬����ǳ�
	private Button sidingqianming;// �໬���ǩ��

	private RelativeLayout relativelayout;
	private ImageView xiaoxiimg;
	private ImageView lianxirenimg;
	private ImageView dongtaiimg;

	private QqMainxiaoxi xiaoxiFragment;// ��Ϣҳ��
	private QqMainLianxiren lianxirenFragment;// ��ϵ��ҳ��
	private QqMainDynamic dongtaiFragment;// ��̬ҳ��

	FragmentManager manager;

	// ��¼���ȡ��ֵ
	private String login_zctouxiangend;// ͷ��
	private int login_zcaccountend;// �˺�
	private String login_zcphoneend;// �ֻ�����
	private String login_zcnameend;// �ǳ�
	private String login_zcpwdend;// ����
	private String login_zcsexend;// �Ա�
	private String login_zcaddressend;// ��ַ
	private String login_zcqianmingend;// ǩ��
	private QqValuesActivity qqvalue;// ȫ�ֱ�����ֵ

	@Override
	protected void onCreate(Bundle savedInstanceState) {// ��ҳ��
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
		findview();// �ؼ�
		initFragment();
		setlistenter();// ����
		loginview();// ��¼���ֵ

		// ��ȡ FragmentManager
		manager = this.getSupportFragmentManager();
		// �õ�һ����������
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.list_qq_layoutrel, xiaoxiFragment);
		transaction.commit();
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// ����ģʽΪ��໬
		slidingMenu.setMode(SlidingMenu.LEFT);
		// ���ò໬�˵���ʾ�Ĳ���
		slidingMenu.setMenu(R.layout.activity_slidingmenu);
		// ���ò˵������Ĵ�����ʽ,�˴�ָ��ȫ��
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// ���ò໬�˵������Ŀ��Ϊ��Ļ��ȵ�0.5
		slidingMenu.setBehindWidth((int) (getResources().getDisplayMetrics().widthPixels * 0.85));
		// ���ò໬Ч��:����Ч��
		slidingMenu.setFadeDegree(0.5f);

		// ���� �໬��imageviewͷ��
		sidingimg = (ImageView) slidingMenu.findViewById(R.id.list_siding_touxiang);
		
		//��ȡtomcat �������е�ͼƬ
		Picasso.with(QqMainActivity.this)
		.load("http://" + Constants.URL+login_zctouxiangend).into(sidingimg);
		
//		String myJpgPath = Constants.URL+"/Android/images/head_2.png";// ͷ���ַ
//		File file = new File(myJpgPath);
//		if (file.exists()) {
//			Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
//			sidingimg.setImageBitmap(bm);
//		} else {
//			Log.v(TAG, "File Not Found!");
//		}
		// ���ò໬���ǳ�
		sidingname = (TextView) slidingMenu.findViewById(R.id.list_siding_name);
		sidingname.setText(login_zcnameend);
		// ���ò໬��ǩ��
		sidingqianming = (Button) slidingMenu.findViewById(R.id.list_siding_qianming);
		sidingqianming.setText(login_zcqianmingend);

	}

	private void findview() {// �ؼ�
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
		qqvalue = (QqValuesActivity) getApplication(); // ���QqValuesActivity����
		login_zctouxiangend = qqvalue.getLogin_zctouxiangend();
		login_zcaccountend = qqvalue.getLogin_zcaccountend();
		login_zcphoneend = qqvalue.getLogin_zcphoneend();
		login_zcnameend = qqvalue.getLogin_zcnameend();
		login_zcpwdend = qqvalue.getLogin_zcpwdend();
		login_zcsexend = qqvalue.getLogin_zcsexend();
		login_zcaddressend = qqvalue.getLogin_zcaddressend();
		login_zcqianmingend = qqvalue.getLogin_zcqianmingend();
		// // ���õ�Intent
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

	private void initFragment() {// ����
		xiaoxiFragment = new QqMainxiaoxi();
		lianxirenFragment = new QqMainLianxiren();
		dongtaiFragment = new QqMainDynamic();
	}

	// ����
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
			xiaoxiimg.setImageResource(R.drawable.skin_tab_icon_conversation_selected);// ��
			lianxirenimg.setImageResource(R.drawable.skin_tab_icon_contact_normal);
			dongtaiimg.setImageResource(R.drawable.skin_tab_icon_plugin_normal);
			break;
		case R.id.list_lianxiren_img:
			transaction.replace(R.id.list_qq_layoutrel, lianxirenFragment);
			xiaoxiimg.setImageResource(R.drawable.skin_tab_icon_conversation_normal);
			lianxirenimg.setImageResource(R.drawable.skin_tab_icon_contact_selected);// ��
			dongtaiimg.setImageResource(R.drawable.skin_tab_icon_plugin_normal);
			break;
		case R.id.list_dongtai_img:
			transaction.replace(R.id.list_qq_layoutrel, dongtaiFragment);
			xiaoxiimg.setImageResource(R.drawable.skin_tab_icon_conversation_normal);
			lianxirenimg.setImageResource(R.drawable.skin_tab_icon_contact_normal);
			dongtaiimg.setImageResource(R.drawable.skin_tab_icon_plugin_selected);// ��
			break;
		}
		transaction.commit();
	}

	// �ֻ��������
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

	public void showpopupwindow(View view) {// popupwindow ����
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
		// �������������ص�����
		window.setFocusable(true);
		window.setBackgroundDrawable(new BitmapDrawable());
		window.showAsDropDown(view);

		// ���ҵ�ê����е㣬�ҵ����Ŀ��
		// int anchorWidth = view.getWidth();
		// �ҵ�contentViewҲ��Ҫ�ҵ�contentView�Ŀ��
		// int contentViewWidth = contextview.getWidth();// ����������ġ�
		// contextview.measure(MeasureSpec.UNSPECIFIED,
		// MeasureSpec.UNSPECIFIED);
		// int measureWidth = contextview.getMeasuredWidth();

		// window.showAsDropDown(view, (anchorWidth-measureWidth)/2, 0);
	}

	public void lianxirentianjia(View view) {
		Toast.makeText(this, "���", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QqMainActivity.this, QqFiendSelActivity.class);
		startActivity(intent);
	}

	public void dynamicgengduo(View view) {
		Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
	}

	public void shezhiClick(View view) {// ����
		Intent intent = new Intent(this, QqShezhiActivity.class);
		startActivity(intent);
	}

	public void slidingmenuonclick(View view) {// ���õ�����Ͻ�ͼƬʱ�໬
		Toast.makeText(this, "touxiang", Toast.LENGTH_SHORT).show();
		slidingMenu.toggle(true);
	}

	public void dynaiconclick(View view) {// ��̬ҳ��
		Intent intent = new Intent(QqMainActivity.this, DongtTaiPublishedActivity.class);
		startActivity(intent);
	}

	public void addfriendonclick(View view) {// ������
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
		// ����һ����ת�ķ�ʽ
		startActivity(intent);
	}

	// @Override
	// public void itemsadapterclick(View v) {
	//// Log.v(TAG, "dadsssssssss");
	// Toast.makeText(QqMainActivity.this,
	// "λ����:" + (Integer) v.getTag() + ",������:" + v.getTag(),
	// Toast.LENGTH_SHORT).show();
	// }

}
