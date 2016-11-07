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
		//名字更换
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
		ChatMssage msg1 = new ChatMssage("小明", "阿萨德的顶顶顶顶顶撒打算", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg2 = new ChatMssage("小花", "的说法都是都水电费等都是快来为都是ss的", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg3 = new ChatMssage("小明", "阿萨德的顶顶顶顶顶撒打eqweqwewqewqe算", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg4 = new ChatMssage("小花", "的说法都是都快来都是ss的", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg5 = new ChatMssage("小明", "阿萨德的顶顶顶顶顶wee撒设防的都是打算", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg6 = new ChatMssage("小花", "的说法都是都快来都是ss的", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg7 = new ChatMssage("小明", "阿萨德的顶顶顶额外温热污染日王二伟认为顶顶撒打算", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg8 = new ChatMssage("小花", "的说法都是都辅导辅导快来都是ss的", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg9 = new ChatMssage("小明", "阿萨德的顶顶顶都是反复反复顶顶撒打算", R.drawable.lpa, ChatDirection.Left);
		ChatMssage msg10 = new ChatMssage("小花", "的说法都是都vvvvvv快来都是ss的", R.drawable.lpd, ChatDirection.Right);
		ChatMssage msg11 = new ChatMssage("小明", "阿萨德的顶顶顶顶而威尔额外顶撒打算", R.drawable.lpa, ChatDirection.Left);
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
