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

public class QqZhuCeActivity extends Activity {// ע��ҳ��

	private final static String TAG = QqZhuCeActivity.class.getSimpleName();

	private ImageView ZCtouxiang;// ע��ͷ��
	private EditText ZCphone;// ע���ֻ�����
	private EditText ZCname;// ע���ǳ�
	private EditText ZCpwd;// ע������
	private EditText ZCquerenpwd;// ע��ȷ������
	private RadioGroup ZCgrpoup;// ע���Ա���Ů
	private RadioButton ZCsexnan;// ע���Ա���
	private EditText ZCaddress;// ע���ַ
	private EditText ZCqianming;// ע��ǩ��
	private ImageView aggree;// ͬ��ע��
	private Button qqzhuce;// ���ע��
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
	private String path;// ͼƬ��ַ
	private File file = null;
	/* ͷ������ */
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
		Log.v(TAG, "�˺�" + zhanghao);
		findView();
		touxiang();// ע��ͷ��
		sexpanduan();// �Ԅe
		qqzhuces();// ����
	}

	private void findView() {
		qqzhuce = (Button) this.findViewById(R.id.qq_zhuceonclick);// ���ע��
		ZCtouxiang = (ImageView) this.findViewById(R.id.list_zhuce_touxiang);
		ZCphone = (EditText) this.findViewById(R.id.list_zhuce_phone);
		ZCname = (EditText) this.findViewById(R.id.list_zhuce_name);
		ZCpwd = (EditText) this.findViewById(R.id.list_zhuce_pwd);
		ZCquerenpwd = (EditText) this.findViewById(R.id.list_zhuce_querenpwd);
		ZCgrpoup = (RadioGroup) this.findViewById(R.id.radio_group);
		ZCaddress = (EditText) this.findViewById(R.id.list_zhuce_address);
		ZCqianming = (EditText) this.findViewById(R.id.list_zhuce_qianming);
		aggree = (ImageView) this.findViewById(R.id.qq_zhucedui);// ͬ��ע��

		aggree.setOnClickListener(new OnClickListener() {// ͬ��ע��
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

	public void zhucefanhui(View view) {// ����
		finish();
	}

	private void sexpanduan() {
		// �ձ�
		ZCgrpoup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonId = group.getCheckedRadioButtonId();
				ZCsexnan = (RadioButton) QqZhuCeActivity.this.findViewById(radioButtonId);
				groupstr = ZCsexnan.getText().toString();
				Log.v(TAG, "�Ա�Ϊ" + groupstr);
			}
		});
	}

	private void qqzhuces() {
		qqzhuce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v(TAG, "�ֻ�" + file);
				phonestr = ZCphone.getText().toString();
				unamestr = ZCname.getText().toString();
				pwdstr = ZCpwd.getText().toString();
				qpwdstr = ZCquerenpwd.getText().toString();
				addrstr = ZCaddress.getText().toString();
				qianmingstr = ZCqianming.getText().toString();

				Thread thread = new Thread(new Runnable() {
					// ��ȡ�Լ�������˺ź�����
					@Override
					public void run() {
						if (file == null) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "��ѡ��ͷ��", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (phonestr.equals("")) {// ע����֤�������жϻ�ȡֵ��
							Log.v(TAG, "phonestr" + phonestr);
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "�������ֻ����룡", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (phonestr.length() < 11 || phonestr.length() > 11) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "�ֻ�����Ϊ11λ����", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (unamestr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "�������ǳƣ�", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (unamestr.length() < 2 || unamestr.length() > 6) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "�ǳ�������2-6λ��", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (pwdstr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "���������룡", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (qpwdstr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "��ȷ�����룡", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (!pwdstr.equals(qpwdstr)) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "�������벻һ�£�", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (addrstr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "�������ַ��", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else if (qianmingstr.equals("")) {
							Looper.prepare();
							Toast toast = Toast.makeText(QqZhuCeActivity.this, "���������ǩ����", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.TOP, 0, 0);
							toast.show();
							Looper.loop();
						} else {
							Log.v(TAG, "�˺�   " + zhanghao);

							// RequestParams params = new RequestParams();
							// try {
							// Log.v(TAG, "�ļ�ͼƬ " + file);
							// params.put("ZCtouxiang", file);// ͼƬ
							// params.put("getzcaccount", zhanghao + "");//
							// params.put("getzcphone", phonestr + "");//
							// params.put("getzcname",
							// URLEncoder.encode(unamestr, "utf-8"));//
							// params.put("getzcpwd", pwdstr);//
							// params.put("getzcquerenpwd", qpwdstr);//
							// if (groupstr == null) {
							// groupstr = "��";
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
								Log.v(TAG, "����ɹ�");
								// �������ɹ� �ͰѶ���ת��json����
								if (connection.getResponseCode() == 200) {
									InputStream is = connection.getInputStream();
									String jsonStr = IOUtils.toString(is);
									Log.v(TAG, "jsonStr" + jsonStr);
								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// ��ת����ʾҳ��
							Intent intent = new Intent(QqZhuCeActivity.this, QqTiShiActivity.class);
							intent.putExtra("friendaccount", zhanghao + "");// ����account
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
			Log.v(TAG, "�ļ�ͼƬ " + file);
			params.put("ZCtouxiang", file);// ͼƬ
			params.put("getzcaccount", zhanghao + "");//
			params.put("getzcphone", phonestr + "");//
			params.put("getzcname", URLEncoder.encode(unamestr, "utf-8"));//
			params.put("getzcpwd", pwdstr);//
			params.put("getzcquerenpwd", qpwdstr);//
			if (groupstr == null) {
				groupstr = "��";
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

	private void touxiang() {// ע��ͷ��
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

				// �������ѡȡ
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

				// ������ѡȡ ��ͼ��
				bt2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						getPhoto();
						pop.dismiss();
					}
				});

				// �����
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

	public void zhucetouxiangexit(View view) {// ȡ��-ͷ��
		pop.dismiss();
	}

	private void getPhoto() {// ������ѡȡ ��ͼ��
		Intent intentFromGallery = new Intent();
		intentFromGallery.setType("image/*"); // �����ļ�����
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, TAKE_PHOTO_REQUEST_CODE);// 0
	}

	private void openPhoto() {// �����
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// �жϴ洢���Ƿ�����ã����ý��д洢
		if (Tools.hasSdcard()) {

			// �ü�ͼƬ��ַ��file:///storage/sdcard1/QQ/ .jpg
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(IMAGE_FILE_NAME_1, zhanghao + IMAGE_FILE_NAME)));
			Log.v(TAG, "eee21312e" + IMAGE_FILE_NAME_1);
		}

		startActivityForResult(intentFromCapture, CHOOSE_IMAGE_REQUEST_CODE);
	}

	// ͼƬ ���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ����벻����ȡ��ʱ��
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case TAKE_PHOTO_REQUEST_CODE:// 0 ��ͼ��
				Log.v(TAG, "��ͼ��");
				Uri uri = data.getData();
				try {
					ContentResolver resolver = this.getContentResolver();
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
					Log.v(TAG, "a" + bitmap + " s " + resolver);
					ZCtouxiang.setImageBitmap(bitmap);
					// ͨ��uri��ѯͼ���б����ͼƬ��·����
					String columnName = MediaStore.Images.Media.DATA;
					Cursor cursor = resolver.query(uri, new String[] { columnName }, "", new String[] {}, "");
					cursor.moveToNext();
					path = cursor.getString(cursor.getColumnIndex(columnName));
					Log.v(TAG, "��ַ��" + path);
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
//				startPhotoZoom(Uri.fromFile(file));// �ü�ͼƬ����ʵ��
				break;
			case CHOOSE_IMAGE_REQUEST_CODE:// 1 �����
				if (Tools.hasSdcard()) {
					file = new File(IMAGE_FILE_NAME_1 + zhanghao + IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(file));// �ü�ͼƬ����ʵ��
					Log.v(TAG, "ss" + file);// �ֻ����պ� ��ȡ��·��
				} else {
					Toast.makeText(QqZhuCeActivity.this, "δ�ҵ��洢�����޷��洢��Ƭ��", Toast.LENGTH_LONG).show();
				}
				// file2 /storage/sdcard1/QQ/faceImage.jpg
				break;
			case RESULT_REQUEST_CODE:// 2
				if (data != null) {
					getImageToView(data);// ����ü�֮���ͼƬ����
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		Log.v(TAG, "�ü�ͼƬ��ַ��" + uri);
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			Log.v(TAG, "����ü�ͼƬ��ַ��" + photo + " sss " + drawable);
			ZCtouxiang.setImageDrawable(drawable);
		}
	}

}
