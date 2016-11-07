package com.ht.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ht.qq.sqllite.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QqFiendSelActivity extends Activity {// ��Ӻ���
	private final static String TAG = QqFiendSelActivity.class.getSimpleName();
	private EditText accountsel;
	private Button addbtn;

	private String account;// �˺�
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

	// ��ѯ�˺�֮���ȡ��ֵ
	private String addfztouxiang;// ͷ��
	private int addfzcaccount;// �˺�
	private String addfzcname;// �ǳ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendsel);
		finviews();
		loginview();// ��·���ֵ
		selaccount();// ��Ӻ���
	}

	private void finviews() {
		accountsel = (EditText) this.findViewById(R.id.list_selfriend);
		addbtn = (Button) this.findViewById(R.id.list_selfriend_selbtn);
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

	private void isfriend() {// �ж��Ƿ�Ϊ����
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String urlStr = "http://"+Constants.URL+"/Android/HLF!isfriend?getselfzcaccount=" + addfzcaccount
							+ "&getselzcaccount=" + login_zcaccountend + "&selfname="
							+ URLEncoder.encode(login_zcnameend, "utf-8") + "&selftouxiang=" + login_zctouxiangend
							+ "&friendname=" + URLEncoder.encode(addfzcname, "utf-8") + "&friendtouxiang="
							+ addfztouxiang + "";
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
						if (jsonStrs.equals("1")) {// �Ѿ��Ǻ���
							Log.v(TAG, jsonStrs + "     ");
							Looper.prepare();
							Toast toast = Toast.makeText(QqFiendSelActivity.this, "�Ѿ��Ǻ����ˣ�", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();

						} else {// ���Ǻ��Ѿ����

							Looper.prepare();
							Toast toast = Toast.makeText(QqFiendSelActivity.this, "���ѳɹ�������Ӻ�������,��ȴ��Է���ˣ�",
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		thread.start();
	}

	private void selaccount() {// �Ȳ�ѯ����Ӻ���
		addbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				account = accountsel.getText().toString();// �˺�
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String urlStr = "http://"+Constants.URL+"/Android/HLF!selfriends?getselfzcaccount="
									+ account + "";
							URL url = new URL(urlStr);
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setDoOutput(true);
							connection.setDoInput(true);
							connection.setRequestMethod("POST");

							// �������ɹ� �ͰѶ���ת��json����
							if (connection.getResponseCode() == 200) {
								InputStream is = connection.getInputStream();
								String jsonStr = IOUtils.toString(is);
								Log.v(TAG, jsonStr);
								if (jsonStr.equals("1")) {// ���ʧ��
									Log.v(TAG, jsonStr + "     ");
									Looper.prepare();
									Toast toast = Toast.makeText(QqFiendSelActivity.this, "��������˺Ų����ڣ�",
											Toast.LENGTH_LONG);
									toast.setGravity(Gravity.TOP, 0, 0);
									toast.show();
									Looper.loop();

								} else {// �ж��Ƿ�Ϊ����
									JSONArray array = new JSONArray(jsonStr);
									for (int i = 0; i < array.length(); i++) {
										JSONObject obj = (JSONObject) array.get(i);
										addfztouxiang = (String) obj.opt("zctouxiang");// ͷ��
										addfzcaccount = (Integer) obj.opt("zcaccount");// �˺�
										addfzcname = (String) obj.opt("zcname");// �ǳ�
										Log.v(TAG, "" + array);
										Log.v(TAG, "" + addfztouxiang);
									}
									isfriend();
								}

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
	}

	public void addfanhui(View view) {// ��ӷ���
		finish();
	}

}
