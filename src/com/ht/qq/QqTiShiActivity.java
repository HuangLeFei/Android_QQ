package com.ht.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class QqTiShiActivity extends Activity {
	TextView qqhao;
	Button denglu;
	private String friendaccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qqtishi);
		loginqqhaochang();
	}

	private void loginqqhaochang() {
		qqhao = (TextView) this.findViewById(R.id.list_tishi_hao);
		denglu = (Button) this.findViewById(R.id.list_tishi_loginbtn);
		Intent intent = this.getIntent();
		friendaccount = intent.getStringExtra("friendaccount");
		qqhao.setText(friendaccount);

		denglu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intents = new Intent(QqTiShiActivity.this, MainActivity.class);
				startActivity(intents);
				finish();
			}
		});

	}

}
