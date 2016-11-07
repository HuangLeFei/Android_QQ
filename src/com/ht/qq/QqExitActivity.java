package com.ht.qq;

import com.ht.qq.bean.QQXiaoxi;
import com.ht.qq.sqllite.CustomDialog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class QqExitActivity extends Activity {
	private final static String TAG = QqExitActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exit);

		onclickexit();// 点击退出

	}

	public void fanhuiexit(View view) {// 返回
		finish();
	}

	private void onclickexit() {// 点击退出
		Button exitbtn = (Button) this.findViewById(R.id.list_exit);
		exitbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.v(TAG, "退出");
				CustomDialog customdialog = new CustomDialog(QqExitActivity.this, R.style.activity_dialog_style);
				customdialog.show();
			}
		});
	}

}
