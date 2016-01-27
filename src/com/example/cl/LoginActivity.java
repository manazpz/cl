package com.example.cl;

import com.xinbo.utils.RegexValidateUtil;
import static cn.smssdk.framework.utils.R.getStringRes;
import com.example.util.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends Activity implements OnClickListener{

//	private Oauth2AccessToken mAccessToken;
//	private AuthInfo mAuthInfo;
//	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
//	private SsoHandler mSsoHandler;
	private TextView mTvToken;
	private TextView tv_email_register;
	private TextView tv_register;
	private LinearLayout pwdlogin;
	private LinearLayout quicklogin;
	private TextView tv_phone_register;
	public static String TAG = "bmob";
	private EditText et_quick_phone;
	private EditText et_quick_code;
	private Button bt_get_code;
	private String userphone;
	private String password;
	private boolean hasPassword;
	private boolean hasUserName;
	private boolean hasphone;
	private boolean hasverify;
	private Button login_btn;
	private EditText username;
	private EditText pwd;
	private Button normallogin_btn;
	private String quickphone;
	private String code;
	public static String APPID = "4b3bfdd480260505557181340e6666cb";
	// 填写从短信SDK应用后台注册得到的APPKEY 
	private static String APPKEY = "d0657e79c3bc";
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "84e7f3dfd7d0a65a5eb12ce461739b06";
	private Handler handler=new Handler(){

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
					quicklog(userphone, password);
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
				}
			} else {
				((Throwable) data).printStackTrace();
				int resId = getStringRes(LoginActivity.this, "smssdk_network_error");
				Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
				et_quick_code.setError("请输入正确的验证码");
				if (resId > 0) {
					Toast.makeText(LoginActivity.this, resId, Toast.LENGTH_SHORT).show();
				}
			}
			
		}
	};
	private View view_line_right;
	private View view_line_left;
	private TextView sina_weibo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
//		Bmob.initialize(getApplicationContext(), APPID);
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
//		initWeiboSDK();
	}

//	private void initWeiboSDK() {
//		mAuthInfo = new AuthInfo(this, SinaConstants.APP_KEY, SinaConstants.REDIRECT_URL, SinaConstants.SCOPE);
//		mSsoHandler = new SsoHandler(this, mAuthInfo);
//		// 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
//		// 第一次启动本应用，AccessToken 不可用
//		mAccessToken = AccessTokenKeeper.readAccessToken(this);
//		if (mAccessToken.isSessionValid()) {
//			updateTokenView(true);
//		}
//	}

	/**
	 * 显示当前 Token 信息。
	 * 
	 * @param hasExisted
	 *  配置文件中是否已存在 token 信息并且合法
	 */
//	private void updateTokenView(boolean hasExisted) {
//		Toast.makeText(LoginActivity.this, mAccessToken.getToken(), Toast.LENGTH_LONG).show();
//		setResult(Constants.Result.WBLOGIN_FH);
//		finish();
//	}

	private void initUI() {
		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_email_register = (TextView) findViewById(R.id.tv_email_register);
		tv_phone_register = (TextView) findViewById(R.id.tv_phone_register);
		pwdlogin = (LinearLayout) findViewById(R.id.ll_login);
		et_quick_phone = (EditText) findViewById(R.id.et_quick_phone);
		et_quick_code = (EditText) findViewById(R.id.et_quick_code);
		bt_get_code = (Button) findViewById(R.id.bt_get_code);
		quicklogin = (LinearLayout) findViewById(R.id.ll_quick_login);
		view_line_right = findViewById(R.id.view_line_right);
		view_line_left = findViewById(R.id.view_line_left);
		sina_weibo = (TextView) findViewById(R.id.sina_weibo);
		
		
		//快速登录 登录按钮
		login_btn = (Button) findViewById(R.id.quick_login_btn);
		//正常登入 登录按钮
		normallogin_btn = (Button) findViewById(R.id.login_btn);
		normallogin_btn.setOnClickListener(this);
		login_btn.setOnClickListener(this);
		username = (EditText) findViewById(R.id.username);
		pwd = (EditText) findViewById(R.id.password);
		sina_weibo.setOnClickListener(this);
		tv_phone_register.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		tv_email_register.setOnClickListener(this);
		et_quick_phone.addTextChangedListener(quickphonechanged());
		et_quick_code.addTextChangedListener(quickcodechanged());
		username.addTextChangedListener(usernamechanged());
		pwd.addTextChangedListener(pwdchanged());
		CheckBox showpwd = (CheckBox) findViewById(R.id.cb_show_pwd);
		//设置密码显隐
		showpwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else {
					pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});
	}

	private TextWatcher pwdchanged() {
		return new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ("".equals(s.toString())) {
					hasPassword = false;
				} else {
					hasPassword = true;
				}
				if (hasPassword && hasUserName) {
					normallogin_btn.setEnabled(true);
				} else {
					normallogin_btn.setEnabled(false);
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

	private TextWatcher usernamechanged() {
		return new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ("".equals(s.toString())) {
					hasUserName = false;
				} else {
					hasUserName = true;
				}
				if (hasPassword && hasUserName) {
					normallogin_btn.setEnabled(true);
				} else {
					normallogin_btn.setEnabled(false);
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

	private TextWatcher quickcodechanged() {
		return new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ("".equals(s.toString())) {
					hasphone = false;
				} else {
					hasphone = true;
				}
				if (hasverify && hasphone) {
					login_btn.setEnabled(true);
				} else {
					login_btn.setEnabled(false);
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

	private TextWatcher quickphonechanged() {
		return new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ("".equals(s.toString())) {
					hasphone = false;
				} else {
					hasphone = true;
				}
				if (hasverify && hasphone) {
					login_btn.setEnabled(true);
				} else {
					login_btn.setEnabled(false);
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


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_register:
			startActivity(new Intent(this, RegisterActivity.class));
			break;
		case R.id.tv_email_register:
			quicklogin.setVisibility(View.GONE);
			pwdlogin.setVisibility(View.VISIBLE);
			normallogin_btn.setVisibility(View.VISIBLE);
			login_btn.setVisibility(View.GONE);
			view_line_left.setVisibility(View.INVISIBLE);
			view_line_right.setVisibility(View.VISIBLE);
			
			break;
		case R.id.tv_phone_register:
			quicklogin.setVisibility(View.VISIBLE);
			pwdlogin.setVisibility(View.GONE);
			login_btn.setVisibility(View.VISIBLE);
			normallogin_btn.setVisibility(View.GONE);
			view_line_left.setVisibility(View.VISIBLE);
			view_line_right.setVisibility(View.INVISIBLE);
			break;
		case R.id.login_btn:
			normallogin();
			break;
		case R.id.quick_login_btn:
			quicklogin();
			break;
		case R.id.bt_get_code:
			getcode();
			break;
		case R.id.sina_weibo:
//			mSsoHandler.authorize(new AuthListener());
			break;
			
			
		default:
			break;
		}
	}

	

	private void getcode() {
		if(!TextUtils.isEmpty(et_quick_phone.getText().toString())){
			SMSSDK.getVerificationCode("86",et_quick_phone.getText().toString());
		}else {
			Toast.makeText(this, "电话不能为空", 1).show();
		}
	}

	private void normallogin() {
		final BmobUser User = new BmobUser();
		User.setUsername(username.getText().toString());
		User.setPassword(pwd.getText().toString());
		User.login(this, new SaveListener() {

			@Override
			public void onSuccess() {
				toast(User.getUsername() + "登陆成功");
//				testGetCurrentUser();
				setResult(Constants.Result.DL_FH);
				finish();
				
			}

			@Override
			public void onFailure(int code, String msg) {
				toast("登陆失败:" + msg);
			}
		});
	}

	private void quicklogin() {
			quickphone = et_quick_phone.getText().toString();
			code = et_quick_code.getText().toString();
			// 判断是否输入
			if ("".equals(quickphone)) {
				et_quick_phone.setError("请输入用户名");
				return;
			}
			if ("".equals(code)) {
				et_quick_code.setError("请输入手机验证码");
				return;
			}
			// 校验用户名是否合法
			// 用户名规则：6-16 [a-zA-Z0-9._$%^&#@!]
			// 邮箱：
			// 手机号
			if (!RegexValidateUtil.checkCharacter(quickphone) && RegexValidateUtil.checkEmail(quickphone)
					&& RegexValidateUtil.checkMobileNumber(quickphone)) {
				et_quick_phone.setError("用户名不合法");
				return;
			}
			// 校验密码是否合法
			if (!RegexValidateUtil.checkCharacter(code)){
				et_quick_code.setError("请输入4位手机验证码");
				return;
			}
			// 将用户输入的账号密码发送到服务端
			if(!TextUtils.isEmpty(et_quick_code.getText().toString())){
				SMSSDK.submitVerificationCode("86", quickphone, et_quick_code.getText().toString());
			}else {
				et_quick_code.setError("验证码不能为空");
				return;
			}
	}

	public void toast(String msg) {
		Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
		Log.d(TAG, msg);
	}

	private void quicklog(String userphone, String password) {
		final BmobUser buser = new BmobUser();
		buser.setUsername(et_quick_phone.getText().toString());
		buser.login(LoginActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				toast(buser.getUsername() + "登陆成功");
//				testGetCurrentUser();
				setResult(Constants.Result.DL_FH);
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				toast("登陆失败:" + msg);
			}
		});
	}
	
//	private void testGetCurrentUser() {
//		User myUser = BmobUser.getCurrentUser(LoginActivity.this, User.class);
//		if (myUser != null) {
//			Log.i("life","本地用户信息:objectId = " + myUser.getObjectId() + ",name = " + myUser.getUsername());
//		} else {
//			toast("本地用户为null,请登录。");
//		}
//		
//	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}
	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
//		if (mSsoHandler != null) {
//			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
//		}

	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
//	class AuthListener implements WeiboAuthListener {
//
//		@Override
//		public void onComplete(Bundle values) {
//			// 从 Bundle 中解析 Token
//			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
//			// 从这里获取用户输入的 电话号码信息
//			String phoneNum = mAccessToken.getPhoneNum();
//			if (mAccessToken.isSessionValid()) {
//				// 显示 Token
//
//				// 保存 Token 到 SharedPreferences
//				AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
//				Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
//				updateTokenView(false);
//			} else {
//				// 以下几种情况，您会收到 Code：
//				// 1. 当您未在平台上注册的应用程序的包名与签名时；
//				// 2. 当您注册的应用程序包名与签名不正确时；
//				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
//				String code = values.getString("code");
//				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
//				if (!TextUtils.isEmpty(code)) {
//					message = message + "\nObtained the code: " + code;
//				}
//				Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
//			}
//		}
//
//		@Override
//		public void onCancel() {
//			Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
//		}
//
//		@Override
//		public void onWeiboException(WeiboException e) {
//			Toast.makeText(LoginActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
//		}
//		
//	}
	
	
}
