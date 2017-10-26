package com.ht.qq.adapter;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ht.qq.QqAddFriendActivity;
import com.ht.qq.R;
import com.ht.qq.sqllite.Constants;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddFriendAdapter extends BaseAdapter {
	private final static String TAG = QqAddFriendActivity.class.getSimpleName();
	private LayoutInflater inflater;
	private JSONArray mqqlist;
	private Callback mCallback;
	private Button agreebtnadapter;
	private Button noagreebtnadapter;
	private Context contexts;
	/**修改了，上传github
	 * 自定义接口，用于回调按钮点击事件到Activity
	 */
	public interface Callback {

		public void agreebtnadapterclick(View v1);// 同意按钮

		public void noagreebtnadapterclick(View v2);// 不同意按钮
	}

	public AddFriendAdapter(Context context, JSONArray qqlist, Callback callback) {
		inflater = LayoutInflater.from(context);
		mqqlist = qqlist;
		mCallback = callback;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mqqlist.length();
	};

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return mqqlist.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		View view = inflater.inflate(com.ht.qq.R.layout.activity_friend, null);
		ImageView headimage = (ImageView) view.findViewById(R.id.friend_add_touxiang);
		TextView nametext = (TextView) view.findViewById(R.id.list_friend_name);
		agreebtnadapter = (Button) view.findViewById(R.id.list_friend_agree);
		noagreebtnadapter = (Button) view.findViewById(R.id.list_friend_dontagree);
		try {
			// 这里obj.optString("qstuname")要和数据库里的值一样
			JSONObject obj = (JSONObject) mqqlist.get(position);
			// 自己账号
			int accountself = obj.optInt("selfaccount");
			// 好友账号
			int accountfz = obj.optInt("friendaccount");

			Picasso.with(contexts).load("http://" + Constants.URL + obj.optString("friendtouxiang")).into(headimage);

			// String myJpgPath = obj.optString("friendtouxiang");// 头像地址
			// File file = new File(myJpgPath);
			// if (file.exists()) {
			// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
			// headimage.setImageBitmap(bm);
			// } else {
			// Log.v(TAG, " File Not Found!");
			// }

			nametext.setText(obj.optString("friendname"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 响应按钮点击事件,调用子定义接口，并传入View
		agreebtnadapter.setOnClickListener(new OnClickListener() {// 同意
			@Override
			public void onClick(View v) {
				mCallback.agreebtnadapterclick(v);

			}
		});
		noagreebtnadapter.setOnClickListener(new OnClickListener() {// 不同意
			@Override
			public void onClick(View v) {
				mCallback.noagreebtnadapterclick(v);

			}
		});
		agreebtnadapter.setTag(position);
		noagreebtnadapter.setTag(position);
		return view;
	}
}
