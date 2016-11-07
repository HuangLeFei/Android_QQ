package com.ht.qq;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class QqLoginActivity extends Activity{
	private int mShowContentView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		
		//方法一：通过id来设置页面
		setContentView(R.layout.activity_qqlogin);
		
		
		
		//尝试把布局文件转换成View用于显示页面
		//Context它是上下文的意思，那些是上下文呢？Activity,Service，Application
//		LayoutInflater inflater = LayoutInflater.from(this);
//				
//		this.getLayoutInflater();
//		
//		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
//		View view=null;
//		if(mShowContentView==1){
//			view=inflater.inflate(R.layout.activity_main, null);
//		}else{
//			view=inflater.inflate(R.layout.activity_qqlogin, null);
//		}
//		
//		setContentView(view);//执行
		
//		testTextView();
		
		
		new Handler().postDelayed(new Runnable()  
        {  
            @Override  
            public void run()  
            {  
                Intent intent = new Intent(QqLoginActivity.this, MainActivity.class);  
                startActivity(intent);  
                QqLoginActivity.this.finish();  
  
            }  
        }, 1000);  
		
		
	}
	
	
//	private void testTextView(){
//		TextView loginTextTv = new TextView(this);
//		loginTextTv.setText("我是在Java代码里的文本");
//		setContentView(loginTextTv);
//		
//	}
}
