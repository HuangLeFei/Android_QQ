package com.ht.qq.adapter;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ht.qq.QqAddFriendActivity;
import com.ht.qq.QqLiaoTianActivity;
import com.ht.qq.QqMainActivity;
import com.ht.qq.R;
import com.ht.qq.adapter.AddFriendAdapter.Callback;
import com.ht.qq.bean.QQXiaoxi;
import com.ht.qq.bean.QqFrienduser;
import com.ht.qq.bean.SendRecevieMSG;
import com.ht.qq.sqllite.Constants;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QqRecentlyMsgAdapter extends BaseAdapter {
	private final static String TAG = QqMainActivity.class.getSimpleName();
	private LayoutInflater inflater;
	private List<QQXiaoxi> mqqlist;
	private QQXiaoxi q;
	Context qqMainxiaoxis;
	View view;

	public QqRecentlyMsgAdapter(Context context, List<QQXiaoxi> msgs) {
		inflater = LayoutInflater.from(context);
		this.mqqlist = msgs;
		this.qqMainxiaoxis = context;
		Log.v(TAG, "mqqlist  " + mqqlist);
	}

	@Override
	public int getCount() {
		if (null != mqqlist) {
			return mqqlist.size();
		}
		return 0;
	};

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// 返回Item的视图。
		q = mqqlist.get(position);
		view = inflater.inflate(R.layout.activity_list_qq, null);
		ImageView imagesco = (ImageView) view.findViewById(R.id.qq_ico);
		TextView textname = (TextView) view.findViewById(R.id.qq_textname);
		TextView textcontent = (TextView) view.findViewById(R.id.qq_text2shuoming);
		// 这里obj.optString("qstuname")要和数据库里的值一样
		// JSONObject obj = (JSONObject) mqqlist.get(position);

		textname.setText(q.getReceivename());
		textcontent.setText(q.getMessagecontent());

		Picasso.with(qqMainxiaoxis).load("http://" + Constants.URL + mqqlist.get(position).getReceivetouxiang())
				.into(imagesco);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(qqMainxiaoxis, QqLiaoTianActivity.class);
				intent.putExtra("friendname", mqqlist.get(position).getReceivename());// 朋友name
				intent.putExtra("selfname", mqqlist.get(position).getSendname());// 自己昵称
				intent.putExtra("friendaccount", mqqlist.get(position).getReceiveaccount() + "");// 朋友account
				intent.putExtra("friendtouxiang", mqqlist.get(position).getReceivetouxiang());// 朋友头像
				qqMainxiaoxis.startActivity(intent);
			}
		});

		// view.setOnClickListener(new lvButtonListener(position));

		return view;
	}

	// class lvButtonListener implements OnClickListener {
	// private int position;
	//
	// lvButtonListener(int pos) {
	// position = pos;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// int vid = v.getId();
	// if (vid == view.getId()) {
	// mqqlist.get(position).getReceivename();
	// Log.v(TAG, "自己身上 " + mqqlist.get(position).getReceivename());
	// Log.v(TAG, "自己 " + q.getReceivename() + " " + q.getSendname() + " " +
	// q.getReceiveaccount()
	// + " ");
	// }
	// }
	// }

}
