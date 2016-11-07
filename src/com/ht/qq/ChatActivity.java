package com.ht.qq;

import java.util.ArrayList;

import com.ht.qq.adapter.TextChatMsgAdapter;
import com.ht.qq.bean.ChatMssage;
import com.ht.qq.direction.ChatDirection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity{
	private ListView malistview;
	private TextView chatstunames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_list);
		findview();
		Msgadapter();
		//���ָ���
		chatstunames();
	}

	private void findview() {
		malistview = (ListView) this.findViewById(R.id.chat_list_view);
		chatstunames = (TextView) this.findViewById(R.id.chat_with_who_name_tv);
	}
	
	private void chatstunames(){
		Intent intent = this.getIntent();
		String chatnames = intent.getStringExtra("chatstuname");
		chatstunames.setText(chatnames);
	}

	private void Msgadapter() {
		ArrayList<ChatMssage> lists = new ArrayList<ChatMssage>();
		ChatMssage msg1 = new ChatMssage("С��", "�����µĶ���������������", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg2 = new ChatMssage("С��", "��˵�����Ƕ�ˮ��ѵȶ��ǿ���Ϊ����ss��", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg3 = new ChatMssage("С��", "�����µĶ�������������eqweqwewqewqe��", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg4 = new ChatMssage("С��", "��˵�����Ƕ���������ss��", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg5 = new ChatMssage("С��", "�����µĶ���������wee������Ķ��Ǵ���", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg6 = new ChatMssage("С��", "��˵�����Ƕ���������ss��", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg7 = new ChatMssage("С��", "�����µĶ���������������Ⱦ������ΰ��Ϊ����������", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg8 = new ChatMssage("С��", "��˵�����Ƕ�����������������ss��", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg9 = new ChatMssage("С��", "�����µĶ��������Ƿ�����������������", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg10 = new ChatMssage("С��", "��˵�����Ƕ�vvvvvv��������ss��", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg11 = new ChatMssage("С��", "�����µĶ����������������ⶥ������", R.drawable.lpa, ChatDirection.Left);
		lists.add(msg1);
		lists.add(msg2);
		lists.add(msg3);
		lists.add(msg4);
		lists.add(msg5);
		lists.add(msg6);
		lists.add(msg7);
		lists.add(msg8);
		lists.add(msg9);
		lists.add(msg10);
		lists.add(msg11);
		
		TextChatMsgAdapter adapter = new TextChatMsgAdapter(this, lists);
		malistview.setAdapter(adapter);
		
	}
}
