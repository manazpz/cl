package com.example.Fragment;

import com.example.cl.R;
import com.example.cl.R.layout;
import com.example.util.Util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ClassifyFragment extends Fragment implements OnClickListener{

	private View view;
	private LayoutInflater inflater;
	private Button mNewBtn;
	private Button mHotBtn;
	private ImageView photo;
	public ClassifyFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			this.inflater = inflater;
			view = inflater.inflate(R.layout.fragment_classify, container, false);
			initUI(view);
		}
		return view;
	}

	private void initUI(View view) {
		mNewBtn = (Button) view.findViewById(R.id.btn_newgools);
		mNewBtn.setOnClickListener(this);
		mHotBtn = (Button) view.findViewById(R.id.btn_hotgools);
		mHotBtn.setOnClickListener(this);
		ListView goolslist = (ListView) view.findViewById(R.id.lv_gools);
		goolsadapter goolsadapter = new goolsadapter();
		goolslist.setAdapter(goolsadapter);
		
	}
	
	class goolsadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Util.ALLIMGS.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = inflater.inflate(R.layout.item_cl, null);
			photo = (ImageView) inflate.findViewById(R.id.imageView1);
			photo.setImageResource(Util.ALLIMGS[position]);
			return inflate;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_newgools:
			mNewBtn.setBackgroundColor(getResources().getColor(R.color.yello));
			mHotBtn.setBackgroundColor(getResources().getColor(R.color.white));
			break;
		case R.id.btn_hotgools:
			mNewBtn.setBackgroundColor(getResources().getColor(R.color.white));
			mHotBtn.setBackgroundColor(getResources().getColor(R.color.yello));
			
			break;

		default:
			break;
		}
	}
	
	

}
