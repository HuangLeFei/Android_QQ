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

	private ImageView dytouxiang;// ͷ��

	ArrayList<DynamicBean> lists;
	DynamicBean dybean;
	DynamicAdapterActivity adapter;
	/**
	 * ��Ϣ����
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
		qqvalue = (QqValuesActivity) getApplication(); // ���QqValuesActivity����
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
		imagedianzan = (ImageView) this.findViewById(R.id.list_dongtai_dianzan);// ����ͼƬ
		dytouxiang = (ImageView) this.findViewById(R.id.list_dongtai_datouxiang);

		// ��ȡtomcat �������е�ͼƬ
		Picasso.with(DongtTaiPublishedActivity.this).load("http://" + Constants.URL + login_zctouxiangend)
				.into(dytouxiang);
		// String myJpgPath = login_zctouxiangend;// ͷ���ַ
		// File file = new File(myJpgPath);
		// if (file.exists()) {
		// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
		// dytouxiang.setImageBitmap(bm);
		// } else {
		// Log.v(TAG, " File Not Found!");
		// }

		LinearLayout linbeijin = (LinearLayout) this.findViewById(R.id.list_dongtai_layoutxsgy);
		linbeijin.getBackground().setAlpha(100);// 0~255͸����ֵ

	}

	public void dynamicdianzan(View view) {// �Ŵ�
		Log.v(TAG, "�Ŵ�");
		ImageView iv = (ImageView) this.findViewById(R.id.list_dongtai_dianzan);
		// �ȼ��ص�һ������
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.activity_scale);
		// �Ѷ������õ��ؼ���
		iv.setAnimation(anim);
		// ��������
		anim.start();
	}

	public void fanhui(View view) {// ����
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

	// ��ѯ ��̬
	private ArrayList<DynamicBean> seleqqdynamic() {
		// ���ں�̨��������
		try {
			String urlStr = "http://" + Constants.URL + "/Android/HLF!igetdynamic?dynamicselfaccount="
					+ login_zcaccountend + "";

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			// �������ɹ� �ͰѶ���ת��json����
			if (connection.getResponseCode() == 200) {
				InputStream is = connection.getInputStream();
				String jsonStrs = IOUtils.toString(is);
				Log.v(TAG, "jsonStrs ��̬" + jsonStrs);
				if (jsonStrs.equals("1")) {// �޶�̬
					Toast toast = Toast.makeText(DongtTaiPublishedActivity.this, "���޺��ѷ���̬��", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
				} else { // ���� �����¼
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
	// // ���ں�̨��������
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
	// // �������ɹ� �ͰѶ���ת��json����
	// if (connection.getResponseCode() == 200) {
	// InputStream is = connection.getInputStream();
	// String jsonStr = IOUtils.toString(is);
	// Log.w(TAG, jsonStr);
	//
	// if (jsonStr.equals("1")) {// û������
	// Log.v(TAG, "������");
	// Toast toast = Toast.makeText(DongtTaiPublishedActivity.this, "���޺��ѷ���̬��",
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

	public void showpopupwindowdynamic(View view) {// popupwindow ����
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
		// �������������ص�����
		window.setFocusable(true);
		window.setBackgroundDrawable(new BitmapDrawable());
		// ��ʾ�ڶ���
		window.showAtLocation(view, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 20);
	}

	public void dynamicclose(View view) {// ȡ����˵˵
		window.dismiss();
	}

}
