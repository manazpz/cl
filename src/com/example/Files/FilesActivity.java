package com.example.Files;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.cl.R;
import com.example.cl.R.drawable;
import com.example.cl.R.id;
import com.example.cl.R.layout;
import com.example.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FilesActivity extends Activity {
	private String mDir = "/sdcard";
	private List<Map<String, Object>> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fils);
		initUI();
	}
	
	private void initData() {
		Intent intent = this.getIntent();
		Uri uri = intent.getData();
		mDir = uri.getPath();
		mData = getData();
	}

	private void initUI() {
		ExpandableListView mtuku = (ExpandableListView) findViewById(R.id.lv_tuku);
		initData();
		final TukuAdapter tukuadapter = new TukuAdapter();
		mtuku.setAdapter(tukuadapter);
		mtuku.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if ((Integer) mData.get(childPosition).get("img") == R.drawable.ex_folder) {
					mDir = (String) mData.get(childPosition).get("info");
					mData = getData();
					tukuadapter.notifyDataSetChanged();
				}
				return true;
			}
		});
	}
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		File f = new File(mDir);
		File[] files = f.listFiles();

		if (!mDir.equals("/sdcard")) {
			map = new HashMap<String, Object>();
			map.put("title", "Back to ../");
			map.put("info", f.getParent());
			map.put("img", R.drawable.ex_folder);
			list.add(map);
		}
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				map = new HashMap<String, Object>();
				map.put("title", files[i].getName());
				map.put("info", files[i].getPath());
				if (files[i].isDirectory())
					map.put("img", R.drawable.ex_folder);
				else
					map.put("img", R.drawable.ex_doc);
				list.add(map);
			}
		}
		return list;
	}
	
	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView info;
	}

	class TukuAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return Constants.FILENAME.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.item_tk1, null);
			TextView title = (TextView) view.findViewById(R.id.tv_title);
			title.setText(Constants.FILENAME[groupPosition]);
			return view;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = getLayoutInflater().inflate(R.layout.listview, null);
			ViewHolder holder = new ViewHolder();
			holder.img = (ImageView) view.findViewById(R.id.img);
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.info = (TextView) view.findViewById(R.id.info);
			if (groupPosition == 1) {
				holder.img.setBackgroundResource((Integer) mData.get(childPosition).get(
						"img"));
				holder.title.setText((String) mData.get(childPosition).get("title"));
				holder.info.setText((String) mData.get(childPosition).get("info"));
			}
			return view;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}

}
