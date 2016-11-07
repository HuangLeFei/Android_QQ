package com.ht.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ht.qq.adapter.MsgAdapter;
import com.ht.qq.bean.SendRecevieMSG;
import com.ht.qq.sqllite.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class QqLiaoTianActivity extends Activity {
	private final static String TAG = QqLiaoTianActivity.class.getSimpleName();
	private TextView textfriendname;
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

	// FriendFenZuAdapter ����ֵ
	private String friendname;// �������ǳ�
	private String selfname;// �����ǳ�
	private String friendaccount;// �������˺�
	private int friendaccounts;// �������˺� ת������
	private String friendtouxiang;// ������ͷ��

	// ҳ��
	private EditText sendcontent;// ��������
	private Button sendmsgbtn;// ���Ͱ�ť
	private String sendmsgneirong;//// ��ȡ���͵�����
	private ImageView friendziliao;

	/**
	 * ��Ϣ����
	 */
	private final static int TIME_WHAT = 1;
	private final static int TIME_TWO = 2;
	private final static int SEND_THREE = 3;
	private int i;
	private Handler sendhandler = new Handler() {
		@Override
		public void handleMessage(Message msgg) {
			super.handleMessage(msgg);
			if (msgg.what == TIME_WHAT) {
				listviewmsg.setAdapter(adapter);
			}
			if (msgg.what == TIME_TWO) {
				Msgadapter();// ��ȡ��Ϣ
				sendhandler.sendEmptyMessageDelayed(2, 1500);
			}
		}
	};

	private ListView listviewmsg;
	MsgAdapter adapter;// ��Ϣadapter
	ArrayList<SendRecevieMSG> lists;
	SendRecevieMSG msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_list);
		// sendmsgtime();// ����ʱ�� ������Ϣ
		findviews();// �ؼ�
		loginview();// ��¼���ֵ

		liaotianview();// ��ȡ�����ֵ
		lianxirenziliao();// ����
		Msgadapter();// ��ȡ��Ϣ
		sendmsg();// ������Ϣ
		sendhandler.sendEmptyMessage(TIME_TWO);
	}

	private void sendmsgtime() {// ÿһ�����һ����Ϣ
		Timer time = new Timer();
		time.schedule(new TimerTask() {
			@Override
			public void run() {
				Msgadapter();
				// sendhandler.sendEmptyMessage(TIME_TWO);
			}
		}, 1000, 1000);
	}

	private void findviews() {
		textfriendname = (TextView) this.findViewById(R.id.chat_with_who_name_tv);// �ǳ�
		sendcontent = (EditText) this.findViewById(R.id.list_sendmsg_ed);// ��������
		sendmsgbtn = (Button) this.findViewById(R.id.list_send_msgbtn);// ���Ͱ�ť
		friendziliao = (ImageView) this.findViewById(R.id.list_sendmsg_imgren);
		listviewmsg = (ListView) this.findViewById(R.id.chat_list_view);
		listviewmsg.setStackFromBottom(true);
	}

	private void lianxirenziliao() {// ����
		friendziliao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(QqLiaoTianActivity.this, QqzhiliaoActivity.class);
				intent.putExtra("zlfriendname", friendname);// ����name
				intent.putExtra("zlfriendaccount", friendaccount + "");// ����account
				intent.putExtra("zlfriendtouxiang", friendtouxiang);// ����ͷ��
				startActivity(intent);
			}
		});
	}

	private void liaotianview() {// ����ֵ
		Intent intent = this.getIntent();
		friendname = intent.getStringExtra("friendname");
		selfname = intent.getStringExtra("selfname");
		friendaccount = intent.getStringExtra("friendaccount");
		friendaccounts = Integer.parseInt(friendaccount);
		friendtouxiang = intent.getStringExtra("friendtouxiang");
		Log.v(TAG, "friendaccount " + friendtouxiang);
		textfriendname.setText(friendname);// ̧ͷ�ǳƸ�Ϊ��̬
		Log.v(TAG, "selfname" + selfname);
	}

	private void Msgadapter() {// ��ȡ��Ϣ
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String urlStr = "http://" + Constants.URL + "/Android/HLF!getmesssage?msgsendaccount="
							+ login_zcaccountend + "&msgreceiveaccount=" + friendaccounts + "";

					URL url = new URL(urlStr);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setRequestMethod("POST");

					// �������ɹ� �ͰѶ���ת��json����
					if (connection.getResponseCode() == 200) {
						InputStream is = connection.getInputStream();
						String jsonStr = IOUtils.toString(is);
						Log.v(TAG, "��Ϣ���ݣ�" + jsonStr);
						if (jsonStr.equals("1")) {// ��¼ʧ��
							Looper.prepare();
							Toast toast = Toast.makeText(QqLiaoTianActivity.this, "��ʱû����Ϣ��", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else {// ����Ϣ���浽arraylist
							JSONArray list = new JSONArray(jsonStr);

							// SendRecevieMSG msg = new SendRecevieMSG();
							lists = new ArrayList<SendRecevieMSG>();
							for (int i = 0; i < list.length(); i++) {
								msg = new SendRecevieMSG();
								JSONObject obj = list.getJSONObject(i);
								msg.setSendaccount(obj.getInt("sendaccount"));
								msg.setSendtouxiang(obj.getString("sendtouxiang"));
								msg.setSendname(obj.getString("sendname"));
								msg.setMessagecontent(obj.getString("messagecontent"));
								msg.setSendtime(obj.getString("sendtime"));
								msg.setReceivename(obj.getString("receivename"));
								msg.setReceivetouxiang(obj.getString("receivetouxiang"));
								msg.setReceiveaccount(obj.getInt("receiveaccount"));
								lists.add(msg);

							}
							// Log.v(TAG, lists + " zhi");
							adapter = new MsgAdapter(QqLiaoTianActivity.this, lists, login_zcaccountend);
							sendhandler.sendEmptyMessage(TIME_WHAT);

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

	// ������Ϣ
	private void sendmsg() {
		sendmsgbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// ��ȡ���͵�����
				sendmsgneirong = sendcontent.getText().toString();

				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String urlStr = "http://" + Constants.URL + "/Android/HLF!sendsavemssage?sendaccountmsg="
									+ login_zcaccountend + "&receiveaccountmsg=" + friendaccounts + "&sendtouxiangmsg="
									+ login_zctouxiangend + "&sendnamemsg="
									+ URLEncoder.encode(login_zcnameend, "utf-8") + "&messagecontentmsg="
									+ URLEncoder.encode(sendmsgneirong, "utf-8") + "&receivenamemsg="
									+ URLEncoder.encode(friendname, "utf-8") + "&receivetouxiangmsg=" + friendtouxiang
									+ "";

							URL url = new URL(urlStr);
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setDoOutput(true);
							connection.setDoInput(true);
							connection.setRequestMethod("POST");
							// �������ɹ� �ͰѶ���ת��json����
							if (connection.getResponseCode() == 200) {
								// InputStream is = connection.getInputStream();
								// String jsonStr = IOUtils.toString(is);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				thread.start();
				sendcontent.setText("");// ������Ϣ֮�� ����������
				// Date sendtime = new Date();
				// msg.setSendaccount(login_zcaccountend);
				// msg.setSendtouxiang(login_zctouxiangend);
				// msg.setSendname(login_zcnameend);
				// msg.setMessagecontent(sendmsgneirong);
				// msg.setSendtime(sendtime.toLocaleString());
				// msg.setReceivename(friendname);
				// msg.setReceivetouxiang(friendtouxiang);
				// msg.setReceiveaccount(friendaccounts);
				// lists.add(msg);
				// adapter = new MsgAdapter(QqLiaoTianActivity.this, lists,
				// login_zcaccountend);
				// sendhandler.sendEmptyMessage(SEND_THREE);
			}
		});
	}

	private void loginview() {
		qqvalue = (QqValuesActivity) this.getApplication(); // ���QqValuesActivity����
		login_zctouxiangend = qqvalue.getLogin_zctouxiangend();
		login_zcaccountend = qqvalue.getLogin_zcaccountend();
		login_zcphoneend = qqvalue.getLogin_zcphoneend();
		login_zcnameend = qqvalue.getLogin_zcnameend();
		login_zcpwdend = qqvalue.getLogin_zcpwdend();
		login_zcsexend = qqvalue.getLogin_zcsexend();
		login_zcaddressend = qqvalue.getLogin_zcaddressend();
		login_zcqianmingend = qqvalue.getLogin_zcqianmingend();
	}

	// ����
	public void xiaoxi(View view) {
		finish();
	}
}
