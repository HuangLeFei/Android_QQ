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

	// FriendFenZuAdapter 传的值
	private String friendname;// 接收者昵称
	private String selfname;// 发送昵称
	private String friendaccount;// 发送者账号
	private int friendaccounts;// 发送者账号 转换整形
	private String friendtouxiang;// 发送者头像

	// 页面
	private EditText sendcontent;// 发送内容
	private Button sendmsgbtn;// 发送按钮
	private String sendmsgneirong;//// 获取发送的内容
	private ImageView friendziliao;

	/**
	 * 消息类型
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
				Msgadapter();// 获取消息
				sendhandler.sendEmptyMessageDelayed(2, 1500);
			}
		}
	};

	private ListView listviewmsg;
	MsgAdapter adapter;// 消息adapter
	ArrayList<SendRecevieMSG> lists;
	SendRecevieMSG msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_list);
		// sendmsgtime();// 发送时间 接受消息
		findviews();// 控件
		loginview();// 登录后的值

		liaotianview();// 获取聊天的值
		lianxirenziliao();// 资料
		Msgadapter();// 获取消息
		sendmsg();// 发送消息
		sendhandler.sendEmptyMessage(TIME_TWO);
	}

	private void sendmsgtime() {// 每一秒接收一遍消息
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
		textfriendname = (TextView) this.findViewById(R.id.chat_with_who_name_tv);// 昵称
		sendcontent = (EditText) this.findViewById(R.id.list_sendmsg_ed);// 发送内容
		sendmsgbtn = (Button) this.findViewById(R.id.list_send_msgbtn);// 发送按钮
		friendziliao = (ImageView) this.findViewById(R.id.list_sendmsg_imgren);
		listviewmsg = (ListView) this.findViewById(R.id.chat_list_view);
		listviewmsg.setStackFromBottom(true);
	}

	private void lianxirenziliao() {// 资料
		friendziliao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(QqLiaoTianActivity.this, QqzhiliaoActivity.class);
				intent.putExtra("zlfriendname", friendname);// 朋友name
				intent.putExtra("zlfriendaccount", friendaccount + "");// 朋友account
				intent.putExtra("zlfriendtouxiang", friendtouxiang);// 朋友头像
				startActivity(intent);
			}
		});
	}

	private void liaotianview() {// 聊天值
		Intent intent = this.getIntent();
		friendname = intent.getStringExtra("friendname");
		selfname = intent.getStringExtra("selfname");
		friendaccount = intent.getStringExtra("friendaccount");
		friendaccounts = Integer.parseInt(friendaccount);
		friendtouxiang = intent.getStringExtra("friendtouxiang");
		Log.v(TAG, "friendaccount " + friendtouxiang);
		textfriendname.setText(friendname);// 抬头昵称改为动态
		Log.v(TAG, "selfname" + selfname);
	}

	private void Msgadapter() {// 获取消息
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

					// 如果请求成功 就把对象转化json对象
					if (connection.getResponseCode() == 200) {
						InputStream is = connection.getInputStream();
						String jsonStr = IOUtils.toString(is);
						Log.v(TAG, "消息内容：" + jsonStr);
						if (jsonStr.equals("1")) {// 登录失败
							Looper.prepare();
							Toast toast = Toast.makeText(QqLiaoTianActivity.this, "暂时没有消息！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else {// 把消息保存到arraylist
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

	// 发送消息
	private void sendmsg() {
		sendmsgbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 获取发送的内容
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
							// 如果请求成功 就把对象转化json对象
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
				sendcontent.setText("");// 发送消息之后 把输入框清空
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
		qqvalue = (QqValuesActivity) this.getApplication(); // 获得QqValuesActivity对象
		login_zctouxiangend = qqvalue.getLogin_zctouxiangend();
		login_zcaccountend = qqvalue.getLogin_zcaccountend();
		login_zcphoneend = qqvalue.getLogin_zcphoneend();
		login_zcnameend = qqvalue.getLogin_zcnameend();
		login_zcpwdend = qqvalue.getLogin_zcpwdend();
		login_zcsexend = qqvalue.getLogin_zcsexend();
		login_zcaddressend = qqvalue.getLogin_zcaddressend();
		login_zcqianmingend = qqvalue.getLogin_zcqianmingend();
	}

	// 返回
	public void xiaoxi(View view) {
		finish();
	}
}
