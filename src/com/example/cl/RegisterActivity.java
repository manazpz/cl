package com.example.cl;

import static cn.smssdk.framework.utils.R.getStringRes;
import java.lang.annotation.Annotation;

import com.example.util.User;
import com.xinbo.utils.RegexValidateUtil;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.OnCheckedChanged;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends Activity implements OnClickListener {

	public static String APPID = "4b3bfdd480260505557181340e6666cb";
	// 填写从短信SDK应用后台注册得到的APPKEY 
	private static String APPKEY = "d0657e79c3bc";
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "84e7f3dfd7d0a65a5eb12ce461739b06";
	private EditText et_phone;
	private ImageView iv_delete_mobile;
	private Button bt_get_sms_code;
	private String mEditphone;
	private boolean hasPhone;
	private boolean hasPassword;
	private boolean hasverify;
	private EditText et_pwd;
	private EditText et_repwd;
	private CheckBox cb_show_pwd;
	private Button bt_get_check_code;
	private String mEditpwd;
	private String mEditrepwd;
	private User user;
	private EditText et_check_code;
	public static String TAG = "bmob";
	private String mEditverify;
	private Handler handler=new Handler(){

		private String userphone;
		private String password;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event="+event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				//短信注册成功后，返回MainActivity,然后提示新好友
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
					Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
					Register(userphone, password);
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
				}
			} else {
				((Throwable) data).printStackTrace();
				int resId = getStringRes(RegisterActivity.this, "smssdk_network_error");
				Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
				et_check_code.setError("请输入正确的验证码");
				if (resId > 0) {
					Toast.makeText(RegisterActivity.this, resId, Toast.LENGTH_SHORT).show();
				}
			}
			
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Bmob.initialize(getApplicationContext(),APPID);
		initUI();
		SMSSDK.initSDK(this,APPKEY,APPSECRET);
		EventHandler eh=new EventHandler(){

			@Override
			public void afterEvent(int event, int result, Object data) {
				
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eh);
	}

	private void initUI() {
		et_phone = (EditText) findViewById(R.id.et_phone);
		iv_delete_mobile = (ImageView) findViewById(R.id.iv_delete_mobile);
		bt_get_sms_code = (Button) findViewById(R.id.bt_get_sms_code);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		et_repwd = (EditText) findViewById(R.id.et_repwd);
		et_check_code = (EditText) findViewById(R.id.et_check_code);
		cb_show_pwd = (CheckBox) findViewById(R.id.cb_show_pwd);
		bt_get_check_code = (Button) findViewById(R.id.bt_get_check_code);
		
		mEditrepwd = et_repwd.getText().toString();
		mEditverify = et_check_code.getText().toString();
		bt_get_sms_code.setOnClickListener(this);
		bt_get_check_code.setOnClickListener(this);
		iv_delete_mobile.setOnClickListener(this);
		et_phone.addTextChangedListener(phonechanged());
		et_pwd.addTextChangedListener(pwdchanged());
		et_repwd.addTextChangedListener(repwdchanged());
		et_check_code.addTextChangedListener(verifychanged());
		cb_show_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else {
					et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});
	}

	private TextWatcher verifychanged() {
		return new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ("".equals(s.toString())) {
					hasverify = false;
				} else {
					hasverify = true;
				}
				if (hasPassword && hasPhone && hasverify) {
					bt_get_check_code.setEnabled(true);
				} else {
					bt_get_check_code.setEnabled(false);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		};
	}

	private TextWatcher repwdchanged() {
		return new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length()<6) {
					hasPassword = false;
				} else {
					hasPassword = true;
				}
				if (hasPassword && hasPhone && hasverify) {
					bt_get_check_code.setEnabled(true);
				} else {
					bt_get_check_code.setEnabled(false);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		};
	}

	private TextWatcher pwdchanged() {
		return new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length()<6) {
					hasPassword = false;
				} else {
					hasPassword = true;
				}
				if (hasPassword && hasPhone && hasverify) {
					bt_get_check_code.setEnabled(true);
				} else {
					bt_get_check_code.setEnabled(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		};
	}

	

	private TextWatcher phonechanged() {
		return new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
				if ("".equals(s.toString())) {
					hasPhone = false;
					iv_delete_mobile.setVisibility(View.GONE);
					bt_get_sms_code.setEnabled(false);
				} else {
					hasPhone = true;
					iv_delete_mobile.setVisibility(View.VISIBLE);
					if(s.toString().length()==11){
						bt_get_sms_code.setEnabled(true);
					}
				}
				if (hasPassword && hasPhone && hasverify) {
					bt_get_check_code.setEnabled(true);
				} else {
					bt_get_check_code.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};
	}
	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		Log.d(TAG, msg);
	}

	public void Register(String userphone, String password) {
		final User myUser = new User();
		myUser.setUsername(mEditphone);
		myUser.setPassword(mEditpwd);
		myUser.signUp(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				toast("注册成功:" + myUser.getUsername() + "-"
						+ myUser.getObjectId() + "-" + myUser.getCreatedAt()
						+ "-" + myUser.getSessionToken()+",是否验证："+myUser.getEmailVerified());
				finish();
			}
			@Override
			public void onFailure(int code, String msg) {
				toast("注册失败:" + msg);
			}
		});
	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_get_sms_code:
			Log.e("msg", ""+et_phone.getText().toString());
			if(!TextUtils.isEmpty(et_phone.getText().toString())){
				SMSSDK.getVerificationCode("86",et_phone.getText().toString());
				
			}else {
				Toast.makeText(this, "号码不能为空", 1).show();
			}
			break;
		case R.id.iv_delete_mobile:
			et_phone.setText("");
			break;
		case R.id.bt_get_check_code:
			mEditphone = et_phone.getText().toString();
			mEditpwd = et_pwd.getText().toString();
			// 判断是否输入
			if ("".equals(mEditphone)) {
				et_phone.setError("请输入用户名");
				return;
			}
			if ("".equals(mEditpwd)) {
				et_pwd.setError("请输入密码");
				return;
			}
			if (!et_repwd.getText().toString().equals(mEditpwd)) {
				et_repwd.setError("密码不匹配，请重输");
				return;
			}
			// 校验用户名是否合法
			if (!RegexValidateUtil.checkCharacter(mEditphone) && RegexValidateUtil.checkEmail(mEditphone)
					&& RegexValidateUtil.checkMobileNumber(mEditphone)) {
				et_phone.setError("手机不合法");
				return;
			}
			// 校验密码是否合法
			if (!RegexValidateUtil.checkCharacter(mEditpwd)){
				et_pwd.setError("请输入6-16位密码");
				return;
			}
			if(!TextUtils.isEmpty(et_check_code.getText().toString())){
				SMSSDK.submitVerificationCode("86", mEditphone, et_check_code.getText().toString());
			}else {
				et_check_code.setError("验证码不能为空");
				return;
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}
}
