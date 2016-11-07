package com.ht.qq.adapter;

import java.io.File;
import java.util.List;

import com.ht.qq.QqLiaoTianActivity;
import com.ht.qq.QqValuesActivity;
import com.ht.qq.R;
import com.ht.qq.bean.ChatMssage;
import com.ht.qq.bean.SendRecevieMSG;
import com.ht.qq.direction.ChatDirection;
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
import android.widget.ImageView;
import android.widget.TextView;

public class MsgAdapter extends BaseAdapter {
	private final static String TAG = QqLiaoTianActivity.class.getSimpleName();
	private LayoutInflater inflater;
	private List<SendRecevieMSG> chatMessages;
	// 登录后获取的值
	private int login_zcaccountends;// 账号
	private Context contexts;

	public MsgAdapter(Context context, List<SendRecevieMSG> msgs, int login_zcaccountend) {
		inflater = LayoutInflater.from(context);
		this.chatMessages = msgs;
		this.login_zcaccountends = login_zcaccountend;
		this.contexts = context;
	}

	public void setData(List<SendRecevieMSG> contact) {
		this.chatMessages = contact;
	}

	@Override
	public int getCount() {
		if (null != chatMessages) {
			return chatMessages.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SendRecevieMSG message = chatMessages.get(position);
		// 先判断convertView是否为空
		// 是空，就根据ChatMessage拿到方向，之后去决定加载左边还是右边
		// 不是空，还要判断这个convertView是左边的还是右边的，如果和我们的ChatMessage的方向一致那就可以
		// 复用，如果不一致就不能复用。
		// Log.v(TAG, "值是nei荣 ：" + message.getMessagecontent());
		// Log.v(TAG, "值是message ：" + message);
		Log.v(TAG, "账号 ：" + message.getSendtouxiang());
		Log.v(TAG, "tupian  " + message.getReceivetouxiang());
		View view = null;
		if (convertView == null) {
			if (message.getSendaccount() != login_zcaccountends) {
				
				LeftViewHolder leftViewHolder = new LeftViewHolder();
				// 去加载左边的布局文件
				view = inflater.inflate(R.layout.activity_list_left, null);
				leftViewHolder.leftIconIv = (ImageView) view.findViewById(R.id.chat_left_qqicon);
				leftViewHolder.leftMsgContentTv = (TextView) view.findViewById(R.id.chat_left_listview);
				leftViewHolder.lefttime = (TextView) view.findViewById(R.id.list_sendmsg_time_left);
				leftViewHolder.lefttime.setText(message.getSendtime());// 时间

				Picasso.with(contexts).load("http://" + Constants.URL + chatMessages.get(position).getReceivetouxiang())
						.into(leftViewHolder.leftIconIv);
				// String myJpgPath = message.getReceivetouxiang();
				// Log.v(TAG, "左边"+myJpgPath);
				// File file = new File(myJpgPath);
				// if (file.exists()) {
				// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
				// leftViewHolder.leftIconIv.setImageBitmap(bm);
				// } else {
				// Log.v(TAG, "File Not Found!");
				// }

				 leftViewHolder.leftIconIv.setImageResource(R.drawable.recevieleft);//
				// 头像
				leftViewHolder.leftMsgContentTv.setText(message.getMessagecontent());
				view.setTag(leftViewHolder);
			} else {// 加载
				// 去加载右边的布局文件
				RightViewHolder rightViewHolder = new RightViewHolder();
				view = inflater.inflate(R.layout.activity_list_right, null);
				rightViewHolder.rightIconIv = (ImageView) view.findViewById(R.id.chat_right_qqicon);
				rightViewHolder.rightMsgContentTv = (TextView) view.findViewById(R.id.chat_right_listview);
				rightViewHolder.righttime = (TextView) view.findViewById(R.id.list_sendmsg_time_right);
				rightViewHolder.righttime.setText(message.getSendtime());

				Picasso.with(contexts).load("http://" + Constants.URL + chatMessages.get(position).getSendtouxiang())
						.into(rightViewHolder.rightIconIv);
				// String myJpgPath = message.getSendtouxiang();
				// Log.v(TAG, myJpgPath);
				// File file = new File(myJpgPath);
				// if (file.exists()) {
				// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
				// rightViewHolder.rightIconIv.setImageBitmap(bm);
				// } else {
				// Log.v(TAG, "File Not Found!");
				// }

//				 rightViewHolder.rightIconIv.setImageResource(message.getIcon());
				rightViewHolder.rightMsgContentTv.setText(message.getMessagecontent());
				view.setTag(rightViewHolder);
			}
		} else {
			Object viewHolder = convertView.getTag();
			if (viewHolder instanceof LeftViewHolder) {
				if (message.getSendaccount() != login_zcaccountends) {// 加载左边
					view = convertView;
					LeftViewHolder leftViewHolder = (LeftViewHolder) viewHolder;

					Picasso.with(contexts)
							.load("http://" + Constants.URL + chatMessages.get(position).getReceivetouxiang())
							.into(leftViewHolder.leftIconIv);
					// String myJpgPath = message.getReceivetouxiang();
					// Log.v(TAG, myJpgPath);
					// File file = new File(myJpgPath);
					// if (file.exists()) {
					// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
					// leftViewHolder.leftIconIv.setImageBitmap(bm);
					// } else {
					// Log.v(TAG, "File Not Found!");
					// }
					 leftViewHolder.leftIconIv.setImageResource(R.drawable.recevieleft);//
					// 头像

					leftViewHolder.leftMsgContentTv.setText(message.getMessagecontent());
					leftViewHolder.lefttime.setText(message.getSendtime());// 时间
				} else if (message.getSendaccount() == login_zcaccountends) {// 加载右边
					// 去加载右边的布局文件
					RightViewHolder rightViewHolder = new RightViewHolder();
					view = inflater.inflate(R.layout.activity_list_right, null);
					rightViewHolder.rightIconIv = (ImageView) view.findViewById(R.id.chat_right_qqicon);
					rightViewHolder.rightMsgContentTv = (TextView) view.findViewById(R.id.chat_right_listview);
					rightViewHolder.righttime = (TextView) view.findViewById(R.id.list_sendmsg_time_right);
					rightViewHolder.righttime.setText(message.getSendtime());// 时间

					Picasso.with(contexts)
							.load("http://" + Constants.URL + chatMessages.get(position).getSendtouxiang())
							.into(rightViewHolder.rightIconIv);
					// String myJpgPath = message.getSendtouxiang();
					// Log.v(TAG, myJpgPath);
					// File file = new File(myJpgPath);
					// if (file.exists()) {
					// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
					// rightViewHolder.rightIconIv.setImageBitmap(bm);
					// } else {
					// Log.v(TAG, "File Not Found!");
					// }

					// rightViewHolder.rightIconIv.setImageResource(message.getIcon());
					rightViewHolder.rightMsgContentTv.setText(message.getMessagecontent());
					view.setTag(rightViewHolder);
				}
			} else if (viewHolder instanceof RightViewHolder) {
				if (message.getSendaccount() != login_zcaccountends) {// 加载左边
					LeftViewHolder leftViewHolder = new LeftViewHolder();
					// 去加载左边的布局文件
					view = inflater.inflate(R.layout.activity_list_left, null);
					leftViewHolder.leftIconIv = (ImageView) view.findViewById(R.id.chat_left_qqicon);
					leftViewHolder.leftMsgContentTv = (TextView) view.findViewById(R.id.chat_left_listview);
					leftViewHolder.lefttime = (TextView) view.findViewById(R.id.list_sendmsg_time_left);
					leftViewHolder.lefttime.setText(message.getSendtime());// 时间

					Picasso.with(contexts)
							.load("http://" + Constants.URL + chatMessages.get(position).getReceivetouxiang())
							.into(leftViewHolder.leftIconIv);
//					 String myJpgPath = message.getReceivetouxiang();
					// Log.v(TAG, myJpgPath);
					// File file = new File(myJpgPath);
					// if (file.exists()) {
					// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
					// leftViewHolder.leftIconIv.setImageBitmap(bm);
					// } else {
					// Log.v(TAG, "File Not Found!");
					// }

					 leftViewHolder.leftIconIv.setImageResource(R.drawable.recevieleft);//
					// 头像

					leftViewHolder.leftMsgContentTv.setText(message.getMessagecontent());
					view.setTag(leftViewHolder);
				} else if (message.getSendaccount() == login_zcaccountends) {
					view = convertView;
					RightViewHolder rightViewHolder = (RightViewHolder) viewHolder;
					Picasso.with(contexts)
							.load("http://" + Constants.URL + chatMessages.get(position).getSendtouxiang())
							.into(rightViewHolder.rightIconIv);
					// String myJpgPath = message.getSendtouxiang();
					// Log.v(TAG, myJpgPath);
					// File file = new File(myJpgPath);
					// if (file.exists()) {
					// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
					// rightViewHolder.rightIconIv.setImageBitmap(bm);
					// } else {
					// Log.v(TAG, "File Not Found!");
					// }

					// rightViewHolder.rightIconIv.setImageResource(message.getIcon());
					rightViewHolder.rightMsgContentTv.setText(message.getMessagecontent());
					rightViewHolder.righttime.setText(message.getSendtime());// 时间
				}
			}

		}
		return view;
	}

	private class LeftViewHolder {
		TextView leftMsgContentTv;
		ImageView leftIconIv;
		TextView lefttime;
	}

	private class RightViewHolder {
		TextView rightMsgContentTv;
		ImageView rightIconIv;
		TextView righttime;

	}

}
