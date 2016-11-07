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
	// ��¼���ȡ��ֵ
	private int login_zcaccountends;// �˺�
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
		// ���ж�convertView�Ƿ�Ϊ��
		// �ǿգ��͸���ChatMessage�õ�����֮��ȥ����������߻����ұ�
		// ���ǿգ���Ҫ�ж����convertView����ߵĻ����ұߵģ���������ǵ�ChatMessage�ķ���һ���ǾͿ���
		// ���ã������һ�¾Ͳ��ܸ��á�
		// Log.v(TAG, "ֵ��nei�� ��" + message.getMessagecontent());
		// Log.v(TAG, "ֵ��message ��" + message);
		Log.v(TAG, "�˺� ��" + message.getSendtouxiang());
		Log.v(TAG, "tupian  " + message.getReceivetouxiang());
		View view = null;
		if (convertView == null) {
			if (message.getSendaccount() != login_zcaccountends) {
				
				LeftViewHolder leftViewHolder = new LeftViewHolder();
				// ȥ������ߵĲ����ļ�
				view = inflater.inflate(R.layout.activity_list_left, null);
				leftViewHolder.leftIconIv = (ImageView) view.findViewById(R.id.chat_left_qqicon);
				leftViewHolder.leftMsgContentTv = (TextView) view.findViewById(R.id.chat_left_listview);
				leftViewHolder.lefttime = (TextView) view.findViewById(R.id.list_sendmsg_time_left);
				leftViewHolder.lefttime.setText(message.getSendtime());// ʱ��

				Picasso.with(contexts).load("http://" + Constants.URL + chatMessages.get(position).getReceivetouxiang())
						.into(leftViewHolder.leftIconIv);
				// String myJpgPath = message.getReceivetouxiang();
				// Log.v(TAG, "���"+myJpgPath);
				// File file = new File(myJpgPath);
				// if (file.exists()) {
				// Bitmap bm = BitmapFactory.decodeFile(myJpgPath);
				// leftViewHolder.leftIconIv.setImageBitmap(bm);
				// } else {
				// Log.v(TAG, "File Not Found!");
				// }

				 leftViewHolder.leftIconIv.setImageResource(R.drawable.recevieleft);//
				// ͷ��
				leftViewHolder.leftMsgContentTv.setText(message.getMessagecontent());
				view.setTag(leftViewHolder);
			} else {// ����
				// ȥ�����ұߵĲ����ļ�
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
				if (message.getSendaccount() != login_zcaccountends) {// �������
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
					// ͷ��

					leftViewHolder.leftMsgContentTv.setText(message.getMessagecontent());
					leftViewHolder.lefttime.setText(message.getSendtime());// ʱ��
				} else if (message.getSendaccount() == login_zcaccountends) {// �����ұ�
					// ȥ�����ұߵĲ����ļ�
					RightViewHolder rightViewHolder = new RightViewHolder();
					view = inflater.inflate(R.layout.activity_list_right, null);
					rightViewHolder.rightIconIv = (ImageView) view.findViewById(R.id.chat_right_qqicon);
					rightViewHolder.rightMsgContentTv = (TextView) view.findViewById(R.id.chat_right_listview);
					rightViewHolder.righttime = (TextView) view.findViewById(R.id.list_sendmsg_time_right);
					rightViewHolder.righttime.setText(message.getSendtime());// ʱ��

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
				if (message.getSendaccount() != login_zcaccountends) {// �������
					LeftViewHolder leftViewHolder = new LeftViewHolder();
					// ȥ������ߵĲ����ļ�
					view = inflater.inflate(R.layout.activity_list_left, null);
					leftViewHolder.leftIconIv = (ImageView) view.findViewById(R.id.chat_left_qqicon);
					leftViewHolder.leftMsgContentTv = (TextView) view.findViewById(R.id.chat_left_listview);
					leftViewHolder.lefttime = (TextView) view.findViewById(R.id.list_sendmsg_time_left);
					leftViewHolder.lefttime.setText(message.getSendtime());// ʱ��

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
					// ͷ��

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
					rightViewHolder.righttime.setText(message.getSendtime());// ʱ��
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
