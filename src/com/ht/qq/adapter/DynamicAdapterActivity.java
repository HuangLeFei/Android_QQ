package com.ht.qq.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ht.qq.DongtTaiPublishedActivity;
import com.ht.qq.R;
import com.ht.qq.bean.DynamicBean;
import com.ht.qq.sqllite.Constants;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DynamicAdapterActivity extends BaseAdapter {
	private final static String TAG = DynamicAdapterActivity.class.getSimpleName();
	private LayoutInflater inflater;
	private List<DynamicBean> mqqlist;
	private DynamicBean d;
	private Context contexts;

	public DynamicAdapterActivity(Context context, ArrayList<DynamicBean> qqlist) {
		inflater = LayoutInflater.from(context);
		this.mqqlist = qqlist;
		this.contexts = context;
		Log.v(TAG, " 动态值 " + mqqlist);
	}

	@Override
	public int getCount() {
		if (null != mqqlist) {
			return mqqlist.size();
		}
		return 0;

	}

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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// 返回Item的视图。
		View view = inflater.inflate(com.ht.qq.R.layout.activity_dynamic, null);
		ImageView headimage = (ImageView) view.findViewById(R.id.list_dongtai_touxiang);
		Button dnamebtn = (Button) view.findViewById(R.id.list_dongtai_name);
		TextView daytimetext = (TextView) view.findViewById(R.id.list_dongtai_daytime);
		TextView contenttext = (TextView) view.findViewById(R.id.list_dongtai_content);
		// try {
		// 这里obj.optString("senddynamicname")要和数据库里的值一样
		// JSONObject obj = (JSONObject) mqqlist.get(position);

		d = mqqlist.get(position);

		// String myJpgPath = d.getSenddynamictouxiang();// 头像地址
		// 获取tomcat 服务器中的图片
		Picasso.with(contexts).load("http://" + Constants.URL + mqqlist.get(position).getSenddynamictouxiang()).into(headimage);
		
		dnamebtn.setText(d.getSenddynamicname());
		daytimetext.setText(d.getSenddynamictime());
		contenttext.setText(d.getSenddynamiccontent());
		return view;
	}

}
