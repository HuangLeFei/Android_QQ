package com.ht.qq.sqllite;

import com.ht.qq.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CustomDialog extends Dialog {

	private Context mcontext;

	public CustomDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomDialog(Context context, int themeResId) {
		super(context, themeResId);
		setContentView(R.layout.activity_dialog_view);
		mcontext = context;
		Button button = (Button) this.findViewById(R.id.list_must_btn);
		Button quxiao = (Button) this.findViewById(R.id.list_exit_btn);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(mcontext, "点击了退出按钮！！！",
				// Toast.LENGTH_SHORT).show();
				CustomDialog.this.dismiss();
				Activity activity = (Activity) mcontext;
				// activity.finish();
				// 退出程序
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addCategory(Intent.CATEGORY_HOME);
				activity.startActivity(intent);
				System.exit(0);
			}
		});

		quxiao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				CustomDialog.this.dismiss();
			}
		});

	}

}
