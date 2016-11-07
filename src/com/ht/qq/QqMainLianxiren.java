package com.ht.qq;

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
import com.squareup.picasso.Picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class QqMainLianxiren extends Fragment {
	private final static String TAG = QqMainxiaoxi.class.getSimpleName();
	private QqMainActivity qqmainactivity;// ������

	private ImageView mimageview;// ͷ��
	ArrayList<String> grouplist = new ArrayList<String>();
	ArrayList<List<QqFrienduser>> childlist = new ArrayList<List<QqFrienduser>>();
	ArrayList<QqFrienduser> flist = new ArrayList<QqFrienduser>();
	private ExpandableListView listviews;
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			if (msg.what == 1) {
				listviews.setAdapter(new FriendFenZuAdapter(qqmainactivity, grouplist, childlist));
			}
		}
	};
	View view;

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
	private ImageView newfriend;// ���������

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		qqmainactivity = (QqMainActivity) getActivity();// ��ȡ������
		view = inflater.inflate(R.layout.activity_main_lianxiren, container, false);
		grouplist.clear();
		grouplist.add("�ر����");// 0
		grouplist.add("����Ⱥ��");// 1

		grouplist.add("Yourdream I can count");// 2
		grouplist.add("[��֪������]");// 3
		grouplist.add("Knowing that this is a d");// 4
		grouplist.add("���е�С·");// 5
		grouplist.add("That's just a country");// 6
		grouplist.add("[���ǳ����Ҳ���]");// 7
		grouplist.add("�nMy charm big world");// 8
		grouplist.add("�n����ͯζ");// 9

		grouplist.add("�ֻ�ͨѶ¼");// 10
		grouplist.add("�ҵ��豸");// 11

		loginview();// ��¼���ֵ
		findview();// ������ҳ�е�ͷ�� �� �ؼ�
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				String url = "http://" + Constants.URL + "/Android/HLF!getfriendlist?lselfaccount="
						+ login_zcaccountend;
				flist = GohttpGET(url);
				for (int i = 0; i < grouplist.size(); i++) {// ѭ����ǰ���� ����14����
					ArrayList<QqFrienduser> flistl = new ArrayList<QqFrienduser>();
					for (int j = 0; j < flist.size(); j++) {// �����ݿ��ȡ����ǰ���ѵ�ֵ
						QqFrienduser f = flist.get(j);
						// �����ݿ�����ֵ������0-13�е�ֵʱ���ͱ������Ǹ�����
						if (f.getFriendfenzu() == i) {
							flistl.add(f);
						}
					}
					childlist.add(flistl);
					handler.sendEmptyMessage(1);
				}
			}
		});
		t.start();
		return view;
	}

	private ArrayList<QqFrienduser> GohttpGET(String url) {
		ArrayList<QqFrienduser> flist = new ArrayList<QqFrienduser>();
		try {
			URL ul = new URL(url);
			HttpURLConnection urlconnection = (HttpURLConnection) ul.openConnection();
			urlconnection.setConnectTimeout(5000);
			urlconnection.setRequestMethod("POST");
			int code = urlconnection.getResponseCode();
			if (code == 200) {
				InputStream is = urlconnection.getInputStream();
				String json = IOUtils.toString(is);
				JSONArray list = new JSONArray(json);
				for (int i = 0; i < list.length(); i++) {
					JSONObject obj = list.getJSONObject(i);
					QqFrienduser fu = new QqFrienduser();
					fu.setFriendfenzu(obj.getInt("friendfenzu"));
					fu.setFriendaccount(obj.getInt("friendaccount"));
					fu.setFriendtouxiang(obj.getString("friendtouxiang"));
					fu.setFriendname(obj.getString("friendname"));
					fu.setSelfaccount(obj.getInt("selfaccount"));
					fu.setSelftouxiang(obj.getString("selftouxiang"));
					fu.setSelfname(obj.getString("selfname"));
					flist.add(fu);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flist;

	}

	private void findview() {
		listviews = (ExpandableListView) view.findViewById(R.id.contact_expandablelist);
		// ������ҳ�е�ͷ��
		mimageview = (ImageView) view.findViewById(R.id.list_view_image_2);
		
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
