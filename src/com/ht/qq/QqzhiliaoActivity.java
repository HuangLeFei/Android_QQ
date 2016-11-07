package com.ht.qq;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QqzhiliaoActivity extends Activity {
	private final static String TAG = QqzhiliaoActivity.class.getSimpleName();
	/// 聊天页面传递值
	private String zlfriendname;// 接收者昵称
	private String zlfriendaccount;// 接收者账号
	private int zlfriendaccounts;// 接收者账号 转换整形
	private String zlfriendtouxiang;// 发送者头像

	// 页面
	private ImageView touxiang;
	private TextView nicheng;
	private TextView qinaming;
	private Button sendmsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ziliao);
		ziliaoview();// 资料值
		findviews();
		changes();// 改变值
	}

	private void ziliaoview() {// 资料值
		Intent intent = this.getIntent();
		zlfriendname = intent.getStringExtra("zlfriendname");
		zlfriendaccount = intent.getStringExtra("zlfriendaccount");
		zlfriendaccounts = Integer.parseInt(zlfriendaccount);
		zlfriendtouxiang = intent.getStringExtra("zlfriendtouxiang");
		Log.v(TAG, "zlfriendtouxiang  " + zlfriendtouxiang + "   ss    " + zlfriendaccounts);
	}

	private void findviews() {
		touxiang = (ImageView) this.findViewById(R.id.list_ziliao_touxiang);
		nicheng = (TextView) this.findViewById(R.id.list_ziliao_nicheng);
		qinaming = (TextView) this.findViewById(R.id.list_ziliao_qianming);
		sendmsg = (Button) this.findViewById(R.id.list_ziliao_msg);
	}

	private void changes() {
		String myJpgPath = zlfriendtouxiang;// 头像地址
		File file = new File(myJpgPath);
		if (file.exists()) {
			Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
			touxiang.setImageBitmap(bm);
		} else {
			Log.v(TAG, "File Not Found!");
		}
		
		nicheng.setText(zlfriendname);
		qinaming.setText("嗨喽，大家好我是" + zlfriendname);

		sendmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	public void fanhuiziliao(View view) {// 返回
		finish();
	}
}
