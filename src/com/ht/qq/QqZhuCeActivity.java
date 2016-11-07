package com.ht.qq;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.ht.qq.sqllite.Constants;
import com.ht.qq.utils.Tools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class QqZhuCeActivity extends Activity {// 注册页面

	private final static String TAG = QqZhuCeActivity.class.getSimpleName();

	private ImageView ZCtouxiang;// 注册头像
	private EditText ZCphone;// 注册手机号码
	private EditText ZCname;// 注册昵称
	private EditText ZCpwd;// 注册密码
	private EditText ZCquerenpwd;// 注册确认密码
	private RadioGroup ZCgrpoup;// 注册性别男女
	private RadioButton ZCsexnan;// 注册性别男
	private EditText ZCaddress;// 注册地址
	private EditText ZCqianming;// 注册签名
	private ImageView aggree;// 同意注册
	private Button qqzhuce;// 点击注册
	boolean isChanged = false;
	private String phonestr;
	private String unamestr;
	private String pwdstr;
	private String qpwdstr;
	private String addrstr;
	private String qianmingstr;
	private String groupstr;
	private int zhanghao;
	private final static int TAKE_PHOTO_REQUEST_CODE = 0;
	private final static int CHOOSE_IMAGE_REQUEST_CODE = 1;
	private final static int RESULT_REQUEST_CODE = 2;
	private String path;// 图片地址
	private File file = null;
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME_1 = "/storage/sdcard1/QQ/";
	private static final String IMAGE_FILE_NAME = ".jpg";
	private PopupWindow pop = null;// popup

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhuce);
		Random ran = new Random();
		zhanghao = ran.nextInt(1000000000);
		Log.v(TAG, "账号" + zhanghao);
		findView();
		touxiang();// 注册头像
		sexpanduan();// 性別
		qqzhuces();// 主册
	}

	private void findView() {
		qqzhuce = (Button) this.findViewById(R.id.qq_zhuceonclick);// 点击注册
		ZCtouxiang = (ImageView) this.findViewById(R.id.list_zhuce_touxiang);
		ZCphone = (EditText) this.findViewById(R.id.list_zhuce_phone);
		ZCname = (EditText) this.findViewById(R.id.list_zhuce_name);
		ZCpwd = (EditText) this.findViewById(R.id.list_zhuce_pwd);
		ZCquerenpwd = (EditText) this.findViewById(R.id.list_zhuce_querenpwd);
		ZCgrpoup = (RadioGroup) this.findViewById(R.id.radio_group);
		ZCaddress = (EditText) this.findViewById(R.id.list_zhuce_address);
		ZCqianming = (EditText) this.findViewById(R.id.list_zhuce_qianming);
		aggree = (ImageView) this.findViewById(R.id.qq_zhucedui);// 同意注册

		aggree.setOnClickListener(new OnClickListener() {// 同意注册
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v == aggree) {
					if (isChanged) {
						aggree.setImageResource(R.drawable.gqp);

					} else {
						aggree.setImageResource(R.drawable.gqq);
					}
					isChanged = !isChanged;
				}
			}
		});

	}

	public void zhucefanhui(View view) {// 返回
		finish();
	}

	private void sexpanduan() {
		// 姓别
		ZCgrpoup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonId = group.getCheckedRadioButtonId();
				ZCsexnan = (RadioButton) QqZhuCeActivity.this.findViewById(radioButtonId);
				groupstr = ZCsexnan.getText().toString();
				Log.v(TAG, "性别为" + groupstr);
			}
		});
	}

	private void qqzhuces() {
		qqzhuce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v(TAG, "手机" + file);
				phonestr = ZCphone.getText().toString();
				unamestr = ZCname.getText().toString();
				pwdstr = ZCpwd.getText().toString();
				qpwdstr = ZCquerenpwd.getText().toString();
				addrstr = ZCaddress.getText().toString();
				qianmingstr = ZCqianming.getText().toString();

				Thread thread = new Thread(new Runnable() {
					// 获取自己输入的账号和密码
					@Override
					public void run() {
						if (file == null) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "请选择头像！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (phonestr.equals("")) {// 注册验证（输入判断获取值）
							Log.v(TAG, "phonestr" + phonestr);
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "请输入手机号码！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (phonestr.length() < 11 || phonestr.length() > 11) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "手机号码为11位数！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (unamestr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "请输入昵称！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (unamestr.length() < 2 || unamestr.length() > 6) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "昵称请输入2-6位！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (pwdstr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "请输入密码！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (qpwdstr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "请确认密码！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (!pwdstr.equals(qpwdstr)) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "两次密码不一致！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (addrstr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "请输入地址！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (qianmingstr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "请输入个性签名！", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else {
							Log.v(TAG, "账号   " + zhanghao);

							// RequestParams params = new RequestParams();
							// try {
							// Log.v(TAG, "文件图片 " + file);
							// params.put("ZCtouxiang", file);// 图片
							// params.put("getzcaccount", zhanghao + "");//
							// params.put("getzcphone", phonestr + "");//
							// params.put("getzcname",
							// URLEncoder.encode(unamestr, "utf-8"));//
							// params.put("getzcpwd", pwdstr);//
							// params.put("getzcquerenpwd", qpwdstr);//
							// if (groupstr == null) {
							// groupstr = "男";
							// }
							// params.put("getzcsex",
							// URLEncoder.encode(groupstr, "utf-8"));//
							// params.put("getzcaddress",
							// URLEncoder.encode(addrstr, "utf-8"));//
							// params.put("getzcqianming",
							// URLEncoder.encode(qianmingstr, "utf-8"));//
							// } catch (Exception e) {
							// e.printStackTrace();
							// }
							//
							// AsyncHttpClient httpClient = new
							// AsyncHttpClient();
							// httpClient.post("http://" + Constants.URL +
							// "/Android/HLF!adduser", params, new
							// JsonHttpResponseHandler() {
							// @Override
							// public void onFailure(Throwable arg0,
							// org.json.JSONObject arg1) {
							// // TODO Auto-generated method stub
							// super.onFailure(arg0, arg1);
							// }
							//
							// @Override
							// public void onSuccess(int arg0, JSONObject arg1)
							// {
							//
							// }
							//
							// @Override
							// public void onFinish() {
							// // TODO Auto-generated method stub
							// super.onFinish();
							// }
							//
							// @Override
							// public void onStart() {
							// // TODO Auto-generated method stub
							// super.onStart();
							// }
							//
							// });

							try {
								String urlStr = "http://" + Constants.URL + "/Android/HLF!adduser?getzctouxiang=" + file
										+ "&getzcaccount=" + zhanghao + "&getzcphone=" + phonestr + "&getzcname="
										+ URLEncoder.encode(unamestr, "utf-8") + "&getzcpwd=" + pwdstr
										+ "&getzcquerenpwd=" + URLEncoder.encode(qpwdstr, "utf-8") + "&getzcsex="
										+ URLEncoder.encode(groupstr, "utf-8") + "&getzcaddress="
										+ URLEncoder.encode(addrstr, "utf-8") + "&getzcqianming="
										+ URLEncoder.encode(qianmingstr, "utf-8") + "";
								URL url = new URL(urlStr);
								HttpURLConnection connection = (HttpURLConnection) url.openConnection();
								connection.setDoOutput(true);
								connection.setDoInput(true);
								connection.setRequestMethod("GET");
								Log.v(TAG, "主册成功");
								// 如果请求成功 就把对象转化json对象
								if (connection.getResponseCode() == 200) {
									InputStream is = connection.getInputStream();
									String jsonStr = IOUtils.toString(is);
									Log.v(TAG, "jsonStr" + jsonStr);
								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// 跳转到提示页面
							Intent intent = new Intent(QqZhuCeActivity.this, QqTiShiActivity.class);
							intent.putExtra("friendaccount", zhanghao + "");// 朋友account
							startActivity(intent);
							finish();
						}
					}
				});
				thread.start();

			}
		});
	}

	private void upload(File file) {
		RequestParams params = new RequestParams();
		try {
			Log.v(TAG, "文件图片 " + file);
			params.put("ZCtouxiang", file);// 图片
			params.put("getzcaccount", zhanghao + "");//
			params.put("getzcphone", phonestr + "");//
			params.put("getzcname", URLEncoder.encode(unamestr, "utf-8"));//
			params.put("getzcpwd", pwdstr);//
			params.put("getzcquerenpwd", qpwdstr);//
			if (groupstr == null) {
				groupstr = "男";
			}
			params.put("getzcsex", URLEncoder.encode(groupstr, "utf-8"));//
			params.put("getzcaddress", URLEncoder.encode(addrstr, "utf-8"));//
			params.put("getzcqianming", URLEncoder.encode(qianmingstr, "utf-8"));//
		} catch (Exception e) {
			e.printStackTrace();
		}

		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.post("http://" + Constants.URL + "/Android/HLF!adduser", params, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, org.json.JSONObject arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(int arg0, JSONObject arg1) {

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

		});
	}

	private void touxiang() {// 注册头像
		ZCtouxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pop = new PopupWindow(QqZhuCeActivity.this);
				View view = getLayoutInflater().inflate(R.layout.activity_popupwindowzhuceimg, null);

				pop.setWidth(LayoutParams.MATCH_PARENT);
				pop.setHeight(LayoutParams.WRAP_CONTENT);
				pop.setBackgroundDrawable(new BitmapDrawable());
				pop.setFocusable(true);
				pop.setOutsideTouchable(true);
				pop.setContentView(view);
				pop.showAsDropDown(view);

				// 从相册中选取
				Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
				Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
				RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
				parent.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						pop.dismiss();
						// ll_popup.clearAnimation();
					}
				});

				// 点击相册选取 打开图库
				bt2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						getPhoto();
						pop.dismiss();
					}
				});

				// 打开相机
				bt1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						openPhoto();
						pop.dismiss();
					}
				});
			}
		});

	}

	public void zhucetouxiangexit(View view) {// 取消-头像
		pop.dismiss();
	}

	private void getPhoto() {// 点击相册选取 打开图库
		Intent intentFromGallery = new Intent();
		intentFromGallery.setType("image/*"); // 设置文件类型
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, TAKE_PHOTO_REQUEST_CODE);// 0
	}

	private void openPhoto() {// 打开相机
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 判断存储卡是否可以用，可用进行存储
		if (Tools.hasSdcard()) {

			// 裁剪图片地址：file:///storage/sdcard1/QQ/ .jpg
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(IMAGE_FILE_NAME_1, zhanghao + IMAGE_FILE_NAME)));
			Log.v(TAG, "eee21312e" + IMAGE_FILE_NAME_1);
		}

		startActivityForResult(intentFromCapture, CHOOSE_IMAGE_REQUEST_CODE);
	}

	// 图片 相机
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case TAKE_PHOTO_REQUEST_CODE:// 0 打开图库
				Log.v(TAG, "打开图库");
				Uri uri = data.getData();
				try {
					ContentResolver resolver = this.getContentResolver();
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
					Log.v(TAG, "a" + bitmap + " s " + resolver);
					ZCtouxiang.setImageBitmap(bitmap);
					// 通过uri查询图库中保存的图片的路径，
					String columnName = MediaStore.Images.Media.DATA;
					Cursor cursor = resolver.query(uri, new String[] { columnName }, "", new String[] {}, "");
					cursor.moveToNext();
					path = cursor.getString(cursor.getColumnIndex(columnName));
					Log.v(TAG, "地址：" + path);
				} catch (Exception e) {
					e.printStackTrace();
				}
				path="/Android/images/then.jpg";
				file = new File(path);
				// if (file.exists()) {
				// Bitmap bm = BitmapFactory.decodeFile(path);
				// ZCtouxiang.setImageBitmap(bm);
				// } else {
				// Log.v(TAG, "File Not Found!");
				// }
				// file1
				// /storage/emulated/0/Tencent/MicroMsg/WeiXin/mmexport1462609973677.jpg
				Log.v(TAG, "file1  " + file);
//				startPhotoZoom(Uri.fromFile(file));// 裁剪图片方法实现
				break;
			case CHOOSE_IMAGE_REQUEST_CODE:// 1 打开相机
				if (Tools.hasSdcard()) {
					file = new File(IMAGE_FILE_NAME_1 + zhanghao + IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(file));// 裁剪图片方法实现
					Log.v(TAG, "ss" + file);// 手机拍照后 获取的路径
				} else {
					Toast.makeText(QqZhuCeActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
				}
				// file2 /storage/sdcard1/QQ/faceImage.jpg
				break;
			case RESULT_REQUEST_CODE:// 2
				if (data != null) {
					getImageToView(data);// 保存裁剪之后的图片数据
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		Log.v(TAG, "裁剪图片地址：" + uri);
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			Log.v(TAG, "保存裁剪图片地址：" + photo + " sss " + drawable);
			ZCtouxiang.setImageDrawable(drawable);
		}
	}

}
