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

public class SendShuoActivity extends Activity {
	private final static String TAG = SendShuoActivity.class.getSimpleName();
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

	private Button sendshuobtn;// �������
	private EditText sendcontent;
	private String sendneirong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendshuo);
		loginview();
		findviews();
		sendshuoshuo();
	}

	private void findviews() {
		sendshuobtn = (Button) this.findViewById(R.id.list_dongtai_sendfabiao);// �������
		sendcontent = (EditText) this.findViewById(R.id.list_senddynamic_ed);
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

	private void sendshuoshuo() {
		sendshuobtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				sendneirong = sendcontent.getText().toString();// ��������
				Log.v(TAG, "������˵��");
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String urlStr = "http://" + Constants.URL + "/Android/HLF!isenddynamic?senddynamicaccount="
									+ login_zcaccountend + "&senddynamictouxiang=" + login_zctouxiangend
									+ "&senddynamicname=" + URLEncoder.encode(login_zcnameend, "utf-8")
									+ "&senddynamiccontent=" + URLEncoder.encode(sendneirong, "utf-8") + "";
							URL url = new URL(urlStr);
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setDoOutput(true);
							connection.setDoInput(true);
							connection.setRequestMethod("POST");
							if (connection.getResponseCode() == 200) {
								InputStream is = connection.getInputStream();
								String jsonStr = IOUtils.toString(is);
								// ������ɺ� ���ص�����˵˵��ҳ��
								finish();
								Intent intent = new Intent(SendShuoActivity.this, DongtTaiPublishedActivity.class);
								startActivity(intent);
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

	public void sendfanhui(View view) {// ����
		finish();
	}
}
