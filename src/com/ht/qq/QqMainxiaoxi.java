package com.ht.qq;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ht.qq.adapter.FriendFenZuAdapter;
import com.ht.qq.adapter.QqRecentlyMsgAdapter;
import com.ht.qq.adapter.TextBaserAdapter;
import com.ht.qq.bean.QQUserBean;
import com.ht.qq.bean.QQXiaoxi;
import com.ht.qq.sqllite.Constants;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class QqMainxiaoxi extends Fragment {
	private final static String TAG = QqMainxiaoxi.class.getSimpleName();
	private QqMainActivity qqmainactivity;// ������
	private JSONArray jsonarraytxl;
	private ImageView mimageview;// ͷ��
	private ListView mlistview;
	ArrayList<QQXiaoxi> lists;
	QQXiaoxi xiaoxi;
	QqRecentlyMsgAdapter adapter;
	LayoutInflater inflater;
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

	/**
	 * ��Ϣ����
	 */
	private final static int TIME_WHAT = 1;
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			if (msg.what == TIME_WHAT) {
				mlistview.setAdapter(adapter);
			}
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		qqmainactivity = (QqMainActivity) getActivity();// ��ȡ������
		View view = inflater.inflate(R.layout.activity_main_xiaoxi, container, false);

		mlistview = (ListView) view.findViewById(R.id.list_view_item);

		loginview();// ��¼���ֵ

		// ������ҳ�е�ͷ��
		mimageview = (ImageView) view.findViewById(R.id.list_view_image_1);
		
		Picasso.with(qqmainactivity).load("http://" + Constants.URL + login_zctouxiangend).into(mimageview);
		
//		String myJpgPath = login_zctouxiangend;
//		Log.v(TAG, myJpgPath);
//		File file = new File(myJpgPath);
//		if (file.exists()) {
//			Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
//			mimageview.setImageBitmap(bm);
//		} else {
//			Log.v(TAG, "File Not Found!");
//		}

		// ��ѯ qq ��Ϣ
		HttpToRequest();

		return view;
	}

	private void HttpToRequest() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				seleqqmsg();
				// handler.sendEmptyMessage(TIME_WHAT);
			}

		});
		thread.start();
	}

	// private void textselfriendmsg() {// �����������
	// QqRecentlyMsgAdapter adapter = new QqRecentlyMsgAdapter(qqmainactivity,
	// jsonarraytxl);
	// mlistview.setAdapter(adapter);

	// mlistview.setOnItemClickListener(new OnItemClickListener() {
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// // TODO Auto-generated method stub
	// Log.v(TAG, "λ�ã�"+position);
	// // position ���ʱ��λ�á�
	// xiaoxi = (QQXiaoxi) adapter.getItem(position);
	//
	// // ��������
	// // Toast.makeText(MainActivity.this, bean.getStuname(),
	// // Toast.LENGTH_SHORT).show();
	//
	// // ��ת
	// Intent intent = new Intent(qqmainactivity, QqLiaoTianActivity.class);
	// intent.putExtra("friendname", "С��");
	// intent.putExtra("selfname", "sdsdsd");// �Լ��ǳ�
	// // intent.putExtra("friendaccount", f.getFriendaccount()
	// // +"");// ����account
	// // intent.putExtra("friendtouxiang",
	// // f.getFriendtouxiang());//����ͷ��
	// startActivity(intent);
	//
	// }
	// });

	// }

	// ��ѯ qq ��Ϣ
	private ArrayList<QQXiaoxi> seleqqmsg() {
		// ���ں�̨��������
		try {
			String urlStr = "http://" + Constants.URL + "/Android/HLF!qqmsg?xiaoxisendaccount=" + login_zcaccountend
					+ "";

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			// �������ɹ� �ͰѶ���ת��json����
			if (connection.getResponseCode() == 200) {
				InputStream is = connection.getInputStream();
				String jsonStrs = IOUtils.toString(is);
				if (jsonStrs.equals("1")) {// �������¼
					// Looper.prepare();
					Toast toast = Toast.makeText(qqmainactivity, "������Ӻ�������", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
					// Looper.loop();
				} else { // ���� �����¼
					JSONArray list = new JSONArray(jsonStrs);
					lists = new ArrayList<QQXiaoxi>();
					for (int i = 0; i < list.length(); i++) {
						xiaoxi = new QQXiaoxi();
						JSONObject obj = list.getJSONObject(i);
						xiaoxi.setMid(obj.getInt("mid"));
						xiaoxi.setSendaccount(obj.getInt("sendaccount"));
						xiaoxi.setSendtouxiang(obj.getString("sendtouxiang"));
						xiaoxi.setSendname(obj.getString("sendname"));
						xiaoxi.setMessagecontent(obj.getString("messagecontent"));
						xiaoxi.setSendtime(obj.getString("sendtime"));
						xiaoxi.setReceivename(obj.getString("receivename"));
						xiaoxi.setReceivetouxiang(obj.getString("receivetouxiang"));
						xiaoxi.setReceiveaccount(obj.getInt("receiveaccount"));
						lists.add(xiaoxi);
					}
					adapter = new QqRecentlyMsgAdapter(qqmainactivity, lists);
					handler.sendEmptyMessage(TIME_WHAT);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}

	private void loginview() {
		qqvalue = (QqValuesActivity) qqmainactivity.getApplication(); // ���QqValuesActivity����
		login_zctouxiangend = qqvalue.getLogin_zctouxiangend();
		login_zcaccountend = qqvalue.getLogin_zcaccountend();
		login_zcphoneend = qqvalue.getLogin_zcphoneend();
		login_zcnameend = qqvalue.getLogin_zcnameend();
		login_zcpwdend = qqvalue.getLogin_zcpwdend();
		login_zcsexend = qqvalue.getLogin_zcsexend();
		login_zcaddressend = qqvalue.getLogin_zcaddressend();
		login_zcqianmingend = qqvalue.getLogin_zcqianmingend();
	}

}
