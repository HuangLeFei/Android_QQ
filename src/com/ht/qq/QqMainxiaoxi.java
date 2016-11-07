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
	private QqMainActivity qqmainactivity;// 上下文
	private JSONArray jsonarraytxl;
	private ImageView mimageview;// 头像
	private ListView mlistview;
	ArrayList<QQXiaoxi> lists;
	QQXiaoxi xiaoxi;
	QqRecentlyMsgAdapter adapter;
	LayoutInflater inflater;
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

	/**
	 * 消息类型
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
		qqmainactivity = (QqMainActivity) getActivity();// 获取上下文
		View view = inflater.inflate(R.layout.activity_main_xiaoxi, container, false);

		mlistview = (ListView) view.findViewById(R.id.list_view_item);

		loginview();// 登录后的值

		// 更换主页中的头像
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

		// 查询 qq 消息
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

	// private void textselfriendmsg() {// 好友添加详情
	// QqRecentlyMsgAdapter adapter = new QqRecentlyMsgAdapter(qqmainactivity,
	// jsonarraytxl);
	// mlistview.setAdapter(adapter);

	// mlistview.setOnItemClickListener(new OnItemClickListener() {
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// // TODO Auto-generated method stub
	// Log.v(TAG, "位置："+position);
	// // position 点击时的位置。
	// xiaoxi = (QQXiaoxi) adapter.getItem(position);
	//
	// // 弹出名字
	// // Toast.makeText(MainActivity.this, bean.getStuname(),
	// // Toast.LENGTH_SHORT).show();
	//
	// // 跳转
	// Intent intent = new Intent(qqmainactivity, QqLiaoTianActivity.class);
	// intent.putExtra("friendname", "小龙");
	// intent.putExtra("selfname", "sdsdsd");// 自己昵称
	// // intent.putExtra("friendaccount", f.getFriendaccount()
	// // +"");// 朋友account
	// // intent.putExtra("friendtouxiang",
	// // f.getFriendtouxiang());//朋友头像
	// startActivity(intent);
	//
	// }
	// });

	// }

	// 查询 qq 消息
	private ArrayList<QQXiaoxi> seleqqmsg() {
		// 先于后台进行连接
		try {
			String urlStr = "http://" + Constants.URL + "/Android/HLF!qqmsg?xiaoxisendaccount=" + login_zcaccountend
					+ "";

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			// 如果请求成功 就把对象转化json对象
			if (connection.getResponseCode() == 200) {
				InputStream is = connection.getInputStream();
				String jsonStrs = IOUtils.toString(is);
				if (jsonStrs.equals("1")) {// 无聊天记录
					// Looper.prepare();
					Toast toast = Toast.makeText(qqmainactivity, "暂无添加好友请求！", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
					// Looper.loop();
				} else { // 遍历 聊天记录
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
		qqvalue = (QqValuesActivity) qqmainactivity.getApplication(); // 获得QqValuesActivity对象
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
