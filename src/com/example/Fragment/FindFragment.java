package com.example.Fragment;

import java.io.File;

import com.example.Files.FilesActivity;
import com.example.baiduditu.DituActivity;
import com.example.cl.R;
import com.example.cl.R.layout;
import com.example.zxing.CaptureActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class FindFragment extends Fragment implements OnClickListener{
	private LayoutInflater inflater;
	private View layout;
	private TextView phone;

	public FindFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			this.inflater = inflater;
			layout = inflater.inflate(R.layout.fragment_find, container, false);
			// 初始化静态UI
			initUI(layout);
		}
		return layout;
	}

	private void initUI(View layout) {
		layout.findViewById(R.id.rl_Files).setOnClickListener(this);
		layout.findViewById(R.id.rl_Kefu).setOnClickListener(this);
		layout.findViewById(R.id.rl_zxing).setOnClickListener(this);
		layout.findViewById(R.id.rl_Ditu).setOnClickListener(this);
		phone = (TextView) layout.findViewById(R.id.textView7);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_Files:
			Intent fintent = new Intent();
			fintent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
			fintent.setClass(getContext(), FilesActivity.class);
			startActivity(fintent);
			break;
		case R.id.rl_Kefu:
			Uri uri = Uri.parse("tel:"+phone.getText().toString());
			Intent pintent = new Intent(Intent.ACTION_DIAL, uri);
			startActivity(pintent);
			break;
		case R.id.rl_zxing:
			Intent zintent = new Intent(getContext(), CaptureActivity.class);
			startActivity(zintent);
			break;
		case R.id.rl_Ditu:
			Intent dintent = new Intent(getContext(), DituActivity.class);
			startActivity(dintent);
			break;

		default:
			break;
		}
	}

}
