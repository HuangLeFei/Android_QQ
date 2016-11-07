package com.ht.qq.adapter;

import java.util.List;

import com.ht.qq.R;
import com.ht.qq.bean.QQUserBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TextBaserAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<QQUserBean> mqqcontent;

	public TextBaserAdapter(Context qqMainxiaoxi, List<QQUserBean> qqcontent) {
		inflater = LayoutInflater.from(qqMainxiaoxi);
		mqqcontent = qqcontent;
	}

	public void setData(List<QQUserBean> contact){
		this.mqqcontent = contact;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mqqcontent.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mqqcontent.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// ·µ»ØItemµÄÊÓÍ¼¡£
		View view = inflater.inflate(R.layout.activity_list_qq, null);
		ImageView imagesco = (ImageView) view.findViewById(R.id.qq_ico);
		TextView text1 = (TextView) view.findViewById(R.id.qq_textname);
		TextView text2 = (TextView) view.findViewById(R.id.qq_text2shuoming);

		QQUserBean user = mqqcontent.get(position);
		imagesco.setImageResource(user.getImgid());
		text1.setText(user.getStuname());
		text2.setText(user.getShuoming());
		return view;
	}
}
