package com.example.Fragment;

import com.example.cl.LoginActivity;
import com.example.cl.R;
import com.example.util.Constants;
import com.example.util.SettingUtils;
import com.example.util.User;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MineFragment extends Fragment  implements OnClickListener{

	private Button bt_login;
	private RelativeLayout rl_logined;
	private RelativeLayout rl_unlogin;
	private TextView tv_name;
	private ImageView iv_user;
	private User myUser;
	public MineFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_mine, container, false);
		initUI(layout); 
		return layout;
	}
	private void initUI(View layout) {
		bt_login = (Button) layout.findViewById(R.id.bt_login);
		//登录后的UI
		rl_logined = (RelativeLayout) layout.findViewById(R.id.rl_logined);
		//未登录UI
		rl_unlogin = (RelativeLayout) layout.findViewById(R.id.rl_unlogin);
		tv_name = (TextView) layout.findViewById(R.id.tv_name);
		iv_user = (ImageView) layout.findViewById(R.id.iv_user);
		bt_login.setOnClickListener(this);
		String readloginMode = SettingUtils.ReadLoginMode(getContext());
//		if (readloginMode.equals("weibo")) {}
		if (readloginMode.equals("zhanghu")) {
		initDL();
		}
	}
	private void initDL() {
		myUser = User.getCurrentUser(getActivity(), User.class);
		if (myUser == null) {
			// 显示未登录UI
			rl_unlogin.setVisibility(View.VISIBLE);
			rl_logined.setVisibility(View.GONE);
		} else {
			// 显示已登录UI
			rl_unlogin.setVisibility(View.GONE);
			rl_logined.setVisibility(View.VISIBLE);
			tv_name.setText(myUser.getUsername());
		}
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Constants.Result.WBLOGIN_FH) {
//			initweiboDL();
			SettingUtils.LoginMode(getContext(), "weibo");
		}else if(resultCode == Constants.Result.DL_FH){
			initDL();
			SettingUtils.LoginMode(getContext(), "zhanghu");
		}else {
			String readDlMode = SettingUtils.ReadLoginMode(getContext());
			if (readDlMode.equals("weibo")) {
//				initweiboDL();
			}else if (readDlMode.equals("zhanghu")) {
				initDL();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:
			startActivityForResult(new Intent(getActivity(), LoginActivity.class), 0);
			break;

		default:
			break;
		}
	}
}