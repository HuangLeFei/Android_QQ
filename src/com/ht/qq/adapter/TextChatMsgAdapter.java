package com.ht.qq.adapter;


import java.util.List;

import com.ht.qq.ChatActivity;
import com.ht.qq.R;
import com.ht.qq.bean.ChatMssage;
import com.ht.qq.direction.ChatDirection;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TextChatMsgAdapter extends BaseAdapter{
	private final static String TAG = ChatActivity.class.getSimpleName();
	private LayoutInflater inflater;
	private List<ChatMssage> chatMessages;
	
	public TextChatMsgAdapter(Context context,List<ChatMssage> msgs) {
		inflater = LayoutInflater.from(context);
		chatMessages = msgs;
	}

	@Override
	public int getCount() {
		if(null != chatMessages){
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
		ChatMssage message = chatMessages.get(position);
		//���ж�convertView�Ƿ�Ϊ��
		//�ǿգ��͸���ChatMessage�õ�����֮��ȥ����������߻����ұ�
		//���ǿգ���Ҫ�ж����convertView����ߵĻ����ұߵģ���������ǵ�ChatMessage�ķ���һ���ǾͿ���
		//���ã������һ�¾Ͳ��ܸ��á�
		View view = null;
		if(convertView == null){
			if(message.getDirection() ==ChatDirection.Left){
				LeftViewHolder leftViewHolder = new LeftViewHolder();
				//ȥ������ߵĲ����ļ�
				view = inflater.inflate(R.layout.activity_list_left, null);
				leftViewHolder.leftIconIv = (ImageView) view.findViewById(R.id.chat_left_qqicon);
				leftViewHolder.leftMsgContentTv = (TextView) view.findViewById(R.id.chat_left_listview);
				leftViewHolder.leftIconIv.setImageResource(message.getIcon());
				leftViewHolder.leftMsgContentTv.setText(message.getContent());
				view.setTag(leftViewHolder);
			}else{
				//ȥ�����ұߵĲ����ļ�
				RightViewHolder rightViewHolder = new RightViewHolder();
				view = inflater.inflate(R.layout.activity_list_right, null);
				rightViewHolder.rightIconIv = (ImageView) view.findViewById(R.id.chat_right_qqicon);
				rightViewHolder.rightMsgContentTv = (TextView) view.findViewById(R.id.chat_right_listview);
				rightViewHolder.rightIconIv.setImageResource(message.getIcon());
				rightViewHolder.rightMsgContentTv.setText(message.getContent());
				view.setTag(rightViewHolder);
			}
		}else{
			Object viewHolder = convertView.getTag();
			if(viewHolder instanceof LeftViewHolder){
				if(message.getDirection() == ChatDirection.Left){
					view = convertView;
					LeftViewHolder leftViewHolder = (LeftViewHolder) viewHolder;
					leftViewHolder.leftIconIv.setImageResource(message.getIcon());
					leftViewHolder.leftMsgContentTv.setText(message.getContent());
				}else if(message.getDirection() == ChatDirection.Right){
					//ȥ�����ұߵĲ����ļ�
					RightViewHolder rightViewHolder = new RightViewHolder();
					view = inflater.inflate(R.layout.activity_list_right, null);
					rightViewHolder.rightIconIv = (ImageView) view.findViewById(R.id.chat_right_qqicon);
					rightViewHolder.rightMsgContentTv = (TextView) view.findViewById(R.id.chat_right_listview);
					rightViewHolder.rightIconIv.setImageResource(message.getIcon());
					rightViewHolder.rightMsgContentTv.setText(message.getContent());
					view.setTag(rightViewHolder);
				}
			}else if(viewHolder instanceof RightViewHolder){
				if(message.getDirection() == ChatDirection.Left){
					LeftViewHolder leftViewHolder = new LeftViewHolder();
					//ȥ������ߵĲ����ļ�
					view = inflater.inflate(R.layout.activity_list_left, null);
					leftViewHolder.leftIconIv = (ImageView) view.findViewById(R.id.chat_left_qqicon);
					leftViewHolder.leftMsgContentTv = (TextView) view.findViewById(R.id.chat_left_listview);
					leftViewHolder.leftIconIv.setImageResource(message.getIcon());
					leftViewHolder.leftMsgContentTv.setText(message.getContent());
					view.setTag(leftViewHolder);
				}else if(message.getDirection() == ChatDirection.Right){
					view = convertView;
					RightViewHolder rightViewHolder = (RightViewHolder) viewHolder;
					rightViewHolder.rightIconIv.setImageResource(message.getIcon());
					rightViewHolder.rightMsgContentTv.setText(message.getContent());
				}
			}
			
		}
		return view;
	}
	
	
	private class LeftViewHolder{
		TextView leftMsgContentTv;
		ImageView leftIconIv;
	}
	
	private class RightViewHolder{
		TextView rightMsgContentTv;
		ImageView rightIconIv;
		
	}

}
