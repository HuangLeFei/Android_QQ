package com.ht.qq.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//SQLite它是一个文本数据库
public class AccountPwdSqlite extends SQLiteOpenHelper {

	public AccountPwdSqlite(Context context) {
		super(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION);
	}

	public AccountPwdSqlite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// 数据库的创建
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 它是创建一个数据库表，在这里创建
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
		// 数据库升级
		db.execSQL("drop table  if exists loginaccoundpwd");
		onCreate(db);
	}

}
