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
	/// ����ҳ�洫��ֵ
	private String zlfriendname;// �������ǳ�
	private String zlfriendaccount;// �������˺�
	private int zlfriendaccounts;// �������˺� ת������
	private String zlfriendtouxiang;// ������ͷ��

	// ҳ��
	private ImageView touxiang;
	private TextView nicheng;
	private TextView qinaming;
	private Button sendmsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ziliao);
		ziliaoview();// ����ֵ
		findviews();
		changes();// �ı�ֵ
	}

	private void ziliaoview() {// ����ֵ
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
		String myJpgPath = zlfriendtouxiang;// ͷ���ַ
		File file = new File(myJpgPath);
		if (file.exists()) {
			Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
			touxiang.setImageBitmap(bm);
		} else {
			Log.v(TAG, "File Not Found!");
		}
		
		nicheng.setText(zlfriendname);
		qinaming.setText("��ඣ���Һ�����" + zlfriendname);

		sendmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	public void fanhuiziliao(View view) {// ����
		finish();
	}
}
