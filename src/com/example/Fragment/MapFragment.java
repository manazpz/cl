package com.example.Fragment;

import com.example.cl.R;
import com.example.util.Util;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MapFragment extends Fragment {
	private LayoutInflater inflater;
	private View layout;
	private GridView mGridView;
	private MyGridAdapter adapter;

	public MapFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			this.inflater = inflater;
			layout = inflater.inflate(R.layout.fragment_map, container, false);
			// 初始化静态UI
			initUI(layout);
		}
		return layout;
	}

	private void initUI(View layout) {
		mGridView = (GridView) layout.findViewById(R.id.gridView1);
		adapter = new MyGridAdapter();
		mGridView.setAdapter(adapter);
	}
	class MyGridAdapter extends BaseAdapter{

		private ImageView tuji;
		private TextView tv_content;

		@Override
		public int getCount() {
			return Util.TJIMG.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_tuji, null);
			tuji = (ImageView) inflate.findViewById(R.id.imageView1);
			tv_content = (TextView) inflate.findViewById(R.id.textView1);
			tuji.setImageResource(Util.TJIMG[position]);
			return inflate;
		}
		
	}

}