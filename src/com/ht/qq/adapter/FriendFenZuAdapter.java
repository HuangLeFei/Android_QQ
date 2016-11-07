package com.ht.qq.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ht.qq.QqLiaoTianActivity;
import com.ht.qq.QqMainActivity;
import com.ht.qq.QqMainxiaoxi;
import com.ht.qq.R;
import com.ht.qq.bean.QqFrienduser;
import com.ht.qq.sqllite.Constants;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendFenZuAdapter extends BaseExpandableListAdapter {
	private final static String TAG = QqMainActivity.class.getSimpleName();
	ArrayList<String> grouplist = new ArrayList<String>();
	ArrayList<List<QqFrienduser>> childlist = new ArrayList<List<QqFrienduser>>();
	private LayoutInflater inflater;
	private ImageView ltouxiang;
	private TextView textmsg;
	private TextView textname;
	private QqFrienduser f;
	private String myJpgPath;
	Context qqMainxiaoxis;

	public FriendFenZuAdapter(Context qqMainxiaoxi, ArrayList groplist, ArrayList<List<QqFrienduser>> cildlist) {
		inflater = LayoutInflater.from(qqMainxiaoxi);
		this.grouplist = groplist;
		this.childlist = cildlist;
		this.qqMainxiaoxis = qqMainxiaoxi;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return grouplist.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childlist.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return grouplist.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childlist.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String name = grouplist.get(groupPosition);
		View v = inflater.inflate(R.layout.activity_friendshow, null);
		TextView text = (TextView) v.findViewById(R.id.contact_fragment_groupname);// 我的好友
		text.setText(name);
		ImageView im = (ImageView) v.findViewById(R.id.fragmenr_left_group);
		if (isExpanded) {
			im.setImageResource(R.drawable.qb_down);
		} else {
			im.setImageResource(R.drawable.qb_right);
		}
		return v;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		f = childlist.get(groupPosition).get(childPosition);
		convertView = inflater.inflate(R.layout.activity_friendshowcontent, null);
		// 昵称
		textname = (TextView) convertView.findViewById(R.id.contact_friendname);
		textname.setText(f.getFriendname());
		// 说明
		textmsg = (TextView) convertView.findViewById(R.id.contact_friendmessage);
		textmsg.setText("[4G在线]" + f.getFriendname());

		// 头像
		ltouxiang = (ImageView) convertView.findViewById(R.id.contact_friendheadico);

		// Log.v(TAG, "头像"+f.getFriendtouxiang());
//		Picasso.with(qqMainxiaoxis)
//				.load("file://" + childlist.get(groupPosition).get(childPosition).getFriendtouxiang()).into(ltouxiang);
		Picasso.with(qqMainxiaoxis).load("http://" + Constants.URL + childlist.get(groupPosition).get(childPosition).getFriendtouxiang()).into(ltouxiang);
		// 点击进行聊天 跳转到另一个页面
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Log.v(TAG,
				// "姓名："+childlist.get(groupPosition).get(childPosition).getFriendname());

				Intent intent = new Intent(qqMainxiaoxis, QqLiaoTianActivity.class);
				intent.putExtra("friendname", childlist.get(groupPosition).get(childPosition).getFriendname());// 朋友name
				intent.putExtra("selfname", childlist.get(groupPosition).get(childPosition).getSelfname());// 自己昵称
				intent.putExtra("friendaccount",
						childlist.get(groupPosition).get(childPosition).getFriendaccount() + "");// 朋友account
				intent.putExtra("friendtouxiang", childlist.get(groupPosition).get(childPosition).getFriendtouxiang());// 朋友头像
				qqMainxiaoxis.startActivity(intent);
			}
		});

		return convertView;
	}

	private QqMainActivity getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
