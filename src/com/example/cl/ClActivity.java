package com.example.cl;

import com.example.util.Constants;
import com.example.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ClActivity extends Activity {

	private ListView mListView;
	private Myadapter adapter;
	private ImageView photo;
	private ImageView icon_back;
	private int serializableExtra;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cl);
		Intent intent = getIntent();
		serializableExtra = (Integer) intent.getSerializableExtra(Constants.KEY.QZ_REQUEST);
		android.util.Log.e("serializableExtra", serializableExtra+"");
		initUI();
		adapter = new Myadapter();
		mListView.setAdapter(adapter);
	}
		private void initUI() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		mListView = (ListView) findViewById(R.id.listView1);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(ClActivity.this, DetailActivity.class));
			}
		});
		switch (serializableExtra) {
		case 0:
			tv_title.setText(Util.TITALMSG[serializableExtra]);
			break;
		case 1:
			tv_title.setText(Util.TITALMSG[serializableExtra]);
			break;
		case 2:
			tv_title.setText(Util.TITALMSG[serializableExtra]);
			break;
		case 3:
			tv_title.setText(Util.TITALMSG[serializableExtra]);
			break;
		case 4:
			tv_title.setText(Util.TITALMSG[serializableExtra]);
			break;
		case 5:
			tv_title.setText(Util.TITALMSG[serializableExtra]);
			break;

		default:
			break;
		}
		icon_back = (ImageView) findViewById(R.id.imageView1);
		icon_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
		class Myadapter extends BaseAdapter{


			@Override
			public int getCount() {
				switch (serializableExtra) {
				case 1:
					return Util.QZIMG.length;
				case 0:
					return Util.ALLIMGS.length;
				case 2:
					return Util.QZIMG.length;
				case 3:
					return Util.QZIMG.length;
				case 4:
					return Util.QZIMG.length;
				case 5:
					return Util.QZIMG.length;
				default:
					return 0;
				}
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
				View inflate = getLayoutInflater().inflate(R.layout.item_cl, null);
				photo = (ImageView) inflate.findViewById(R.id.imageView1);
				switch (serializableExtra) {
				case 0:
					photo.setImageResource(Util.ALLIMGS[position]);
					break;
				case 1:
					photo.setImageResource(Util.QZIMG[position]);
					break;
				case 2:
					photo.setImageResource(Util.QZIMG[position]);
					break;
				case 3:
					photo.setImageResource(Util.QZIMG[position]);
					break;
				case 4:
					photo.setImageResource(Util.QZIMG[position]);
					break;
				case 5:
					photo.setImageResource(Util.QZIMG[position]);
					break;
					

				default:
					break;
				}
				
				return inflate;
			}
		
		
	}
	
}
