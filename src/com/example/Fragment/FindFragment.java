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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class FindFragment extends Fragment implements OnClickListener{
	private LayoutInflater inflater;
	private View layout;
	private TextView phone;
	private TextView cachesize;
	private File externalCacheDir;
	private File cacheDir;

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
		externalCacheDir = getActivity().getExternalCacheDir();
		cacheDir = getActivity().getCacheDir();
		String size = size(externalCacheDir, cacheDir);
		cachesize = (TextView) layout.findViewById(R.id.cache_size);
		cachesize.setVisibility(View.VISIBLE);
		cachesize.setText(size+"");
		layout.findViewById(R.id.rl_Files).setOnClickListener(this);
		layout.findViewById(R.id.rl_Kefu).setOnClickListener(this);
		layout.findViewById(R.id.rl_zxing).setOnClickListener(this);
		layout.findViewById(R.id.rl_Ditu).setOnClickListener(this);
		layout.findViewById(R.id.rl_Clean).setOnClickListener(this);
		phone = (TextView) layout.findViewById(R.id.textView7);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_Clean:
			com.xinbo.utils.FileUtils.delFilesFromPath(externalCacheDir);
			com.xinbo.utils.FileUtils.delFilesFromPath(cacheDir);
			Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
			cachesize.setVisibility(View.GONE);
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
		case R.id.rl_Files:
			Intent fintent = new Intent();
			fintent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
			fintent.setClass(getContext(), FilesActivity.class);
			startActivity(fintent);


		default:
			break;
		}
	}
	
	public static String size(File filePath1, File filePath2) {
		if (filePath1 == null && filePath2 == null){
			return "0字节";
		}
		if (!filePath1.exists() && !filePath2.exists()){
			return "0字节";
		}
		long fileLen3 = com.xinbo.utils.FileUtils.getFileLen(filePath1);
		long fileLen4 = com.xinbo.utils.FileUtils.getFileLen(filePath2);
		long fileLen = fileLen3 + fileLen4;
		String size = com.xinbo.utils.FileUtils.size(fileLen);
		return size;
	}

}
