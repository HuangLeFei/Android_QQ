package com.ht.qq.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//SQLite����һ���ı����ݿ�
public class AccountPwdSqlite extends SQLiteOpenHelper {

	public AccountPwdSqlite(Context context) {
		super(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION);
	}

	public AccountPwdSqlite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// ���ݿ�Ĵ���
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ���Ǵ���һ�����ݿ�������ﴴ��
		/**
		 * create table t_student ( _id integer primary key autoincrement, name
		 * text, age integer, class text )
		 **/
		db.execSQL("create table " + Constants.TABLE_LOGIN_NAME + " (" + Constants.LOGIN_ID
				+ " integer primary key autoincrement, " + Constants.LOGIN_ACCOUNT + " integer," + Constants.LOGIN_PWD
				+ " text," + Constants.LOGIN_IMG + " text)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ���ݿ�����
		db.execSQL("drop table  if exists loginaccoundpwd");
		onCreate(db);
	}

}
