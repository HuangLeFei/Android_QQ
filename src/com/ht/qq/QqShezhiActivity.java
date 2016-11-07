package com.ht.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class QqShezhiActivity extends Activity {// 设置页面
	private final static String TAG = QqShezhiActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qqshezhi);
	}

	public void fanhui(View view) {// 返回
		finish();
	}

	public void zhanghaoguanli(View view) {// 账号管理
		Intent intent = new Intent(QqShezhiActivity.this, QqExitActivity.class);
		startActivity(intent);

	}
}
