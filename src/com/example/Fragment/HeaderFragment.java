package com.example.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.activeandroid.util.Log;
import com.example.cl.ClActivity;
import com.example.cl.R;
import com.example.util.Constants;
import com.example.util.Util;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HeaderFragment extends Fragment {
	
	private GridView mGridView;
	private MyAdapter adapter;
	private int pageNum;
	
	public HeaderFragment(int position) {
		this.pageNum = position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_header, container, false);
		mGridView = (GridView) layout.findViewById(R.id.gridView1);
		initUI();
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getActivity(),ClActivity.class);
					intent.putExtra(Constants.KEY.QZ_REQUEST, position);
					startActivity(intent);
				
			}
		});
		adapter.notifyDataSetChanged();
		return layout;
	}
	

	private void initUI() {
		adapter = new MyAdapter();
		mGridView.setAdapter(adapter);
	}

	class MyAdapter extends BaseAdapter{

		private ImageView mImageView;
		private TextView mTextView;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_gridview, null);
			mImageView = (ImageView) inflate.findViewById(R.id.imageView1);
			mTextView = (TextView) inflate.findViewById(R.id.textView1);
			mTextView.setText(Util.GIRDMSG[position]);
			mImageView.setImageResource(Util.GIRDIMAG[position]);
//			if (Util.GIRDIMAG.length > 0) {
//				if (pageNum == 1) {
//					position += 6;
//				}
//				mTextView.setText(Util.GIRDMSG[position]);
//				mImageView.setImageResource(Util.GIRDIMAG[position]);
//			}
			return inflate;
		}
		
		@Override
		public int getCount() {
			return 6;
		}

		@Override
		public Object getItem(int position) {
//			if(position==0){
//				return new ClFragment(position);
//			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		
		
	}


	
}
