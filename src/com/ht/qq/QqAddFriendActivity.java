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

public class QqAddFriendActivity extends Activity implements Callback {// ����֪ͨ��֤
	private final static String TAG = QqAddFriendActivity.class.getSimpleName();
	private ListView listviews;
	private JSONArray jsonarraytxl;
	// ��ѯ����
	private String selfnamevalue;// �Լ��ǳ�
	private String selftouxiangvalue;// �Լ�ͷ��
	private String friendnamevalue;// �����ǳ�
	private String friendtouxiangvalue;// �����ǳ�
	private int getselzcaccountvalue;// ���ҵ��˺� �����˺� 494946020
	private int getselfzcaccountvalue;// �Լ����˺� �û��˺�
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
	private Button agreebtn;// ͬ��
	private Button noagreebtn;// ��ͬ��
	// ͬ��
	private int add_selfaccount;// �Լ��˺�
	private int add_fzcaccount;// �����˺�
	private String add_fzcname;// �ǳ�
	private String add_fzselftouxiang;// ͷ��
	private String add_fzfzcname;// �����ǳ�
	private String add_fztouxiang;// ����ͷ��

	// ��ͬ��
	private int noadd_selfaccount;// �Լ��˺�
	private int noadd_fzcaccount;// �����˺�
	private String noadd_fzcname;// �ǳ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriend);
		findviews();// �ؼ�
		loginview();
		HttpToRequest();// ��������ͬ������
	}

	private void findviews() {// ��¼���ȡ��ֵ
		listviews = (ListView) this.findViewById(R.id.list_addfriend);
		agreebtn = (Button) this.findViewById(R.id.list_friend_agree);
		noagreebtn = (Button) this.findViewById(R.id.list_friend_dontagree);
		// listviews.setOnItemClickListener(this);
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

	public void friendfanhui(View view) {// �����ѷ���
		finish();
	}

	public void friendaddsel(View view) {// ��Ӻ��Ѱ�ť
		Toast.makeText(this, "��Ӻ���", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, QqFiendSelActivity.class);
		startActivity(intent);
	}

	private void textaddfriend() {// �����������
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
		// ���ں�̨��������
		try {
			String urlStr = "http://" + Constants.URL + "/Android/HLF!agreeaddfriend?selfaccountagree="
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
				Log.w(TAG, jsonStrs);

				if (jsonStrs.equals("1")) {// �Ѿ��Ǻ���
					Looper.prepare();
					Toast toast = Toast.makeText(QqAddFriendActivity.this, "������Ӻ�������", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
					Looper.loop();
				} else { // ������������
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
	 * �ӿڷ�������ӦListView��ť����¼�
	 */
	@Override
	public void agreebtnadapterclick(View v) {// ͬ�ⰴť
		Log.v(TAG, "�����ͬ��");
		try {
//			Toast.makeText(QqAddFriendActivity.this,
//					"λ����:" + (Integer) v.getTag() + ",������:" + jsonarraytxl.get((Integer) v.getTag()),
//					Toast.LENGTH_SHORT).show();
			// ��������items��ֵ
			Log.v(TAG, "arrayarrayarray  " + jsonarraytxl.get((Integer) v.getTag()));

			JSONObject arrayitems = (JSONObject) jsonarraytxl.get((Integer) v.getTag());
			add_selfaccount = (Integer) arrayitems.opt("selfaccount");// �Լ��˺�
			add_fzcaccount = (Integer) arrayitems.opt("friendaccount");// �����˺�
			add_fzcname = (String) arrayitems.opt("selfname");// �ǳ�
			add_fzselftouxiang = (String) arrayitems.opt("selftouxiang");// ͷ��
			add_fzfzcname = (String) arrayitems.opt("friendname");// �����ǳ�
			add_fztouxiang = (String) arrayitems.opt("friendtouxiang");// ����ͷ��
			Log.v(TAG, "�Լ��˺�" + add_selfaccount);
			Log.v(TAG, "�����˺�" + add_fzcaccount);
			Log.v(TAG, "�ǳ�" + add_fzcname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ���ͬ����Ӻ�������
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

					// �������ɹ� �ͰѶ���ת��json����
					if (connection.getResponseCode() == 200) {
						InputStream is = connection.getInputStream();
						String jsonStrs = IOUtils.toString(is);
						Log.v(TAG, jsonStrs);
						Looper.prepare();
						Toast toast = Toast.makeText(QqAddFriendActivity.this, "��ϲ�㣡�ɹ���Ӻ��ѡ�", Toast.LENGTH_LONG);
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
		refresh();// ˢ��
	}

	@Override
	public void noagreebtnadapterclick(View v) {// ��ͬ�ⰴť
		Log.v(TAG, "��ͬ�ⰴť");
		try {
//			Toast.makeText(QqAddFriendActivity.this,
//					"λ����:" + (Integer) v.getTag() + ",������:" + jsonarraytxl.get((Integer) v.getTag()),
//					Toast.LENGTH_SHORT).show();
			// ��������items��ֵ
			Log.v(TAG, "arrayarrayarray  " + jsonarraytxl.get((Integer) v.getTag()));

			JSONObject arrayitems = (JSONObject) jsonarraytxl.get((Integer) v.getTag());
			noadd_selfaccount = (Integer) arrayitems.opt("selfaccount");// �Լ��˺�
			noadd_fzcaccount = (Integer) arrayitems.opt("friendaccount");// �����˺�
			noadd_fzcname = (String) arrayitems.opt("selfname");// �ǳ�
			Log.v(TAG, "�Լ��˺�" + noadd_selfaccount);
			Log.v(TAG, "�����˺�" + noadd_fzcaccount);
			Log.v(TAG, "�ǳ�" + noadd_fzcname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ���buͬ����Ӻ�������
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

					// �������ɹ� �ͰѶ���ת��json����
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
		refresh();// ˢ��
	}

	/**
	 * ��ӦListView��item�ĵ���¼�
	 */
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View v, int position, long
	// id) {
	// Log.v(TAG, "��ͬ�ⰴť31232213");
	// Toast.makeText(this, "listview��item������ˣ��������λ����-->" + position,
	// Toast.LENGTH_SHORT).show();
	// Log.v(TAG, "onItemClick �������");
	// }

	/**
	 * ˢ��
	 */
	private void refresh() {
		finish();
		Intent intent = new Intent(QqAddFriendActivity.this, QqAddFriendActivity.class);
		startActivity(intent);
	}

}
