package com.example.Fragment;

import java.util.ArrayList;
import java.util.List;
import com.example.cl.ClActivity;
import com.example.cl.DetailActivity;
import com.example.cl.R;
import com.example.util.Constants;
import com.example.util.Util;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class HomeFragment extends Fragment {

	private LayoutInflater inflater;
	private View layout;
	private View headerView;
	private RecyclerView hotsale;
	private MyHotAdapter hotsaleAdapter;
	// private MyViewHolder holder;
	private List<Util> hotlist = new ArrayList<Util>();
	private ViewPager vp_type;
	private boolean isDrag;
	private int BANNER_COUNT = 4 * 100000;
	private ImageView photo;
	private GoolsAdapter goolsadapter;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			this.inflater = inflater;
			layout = inflater.inflate(R.layout.fragment_home, container, false);
			// 初始化静态UI
			initUI(layout);
		}
		return layout;
	}

	private void initUI(View layout) {
		initVP(layout);
		initRV(layout);
		initGL(layout);
	}

	private void initGL(View layout) {
		ListView gools_list = (ListView) layout.findViewById(R.id.gools_listview).findViewById(R.id.gools_list);
		gools_list.setOnItemClickListener(itemclick());
		goolsadapter = new GoolsAdapter();
		gools_list.setAdapter(goolsadapter);
	}

	private OnItemClickListener itemclick() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(getActivity(), com.example.cl.DetailActivity.class));
			}
		};
	}

	private void initRV(View layout) {
		// headerView = inflater.inflate(R.layout.headerbanner,// null);//这个是用在子布局里的，但是没有父布局就显示不出来
		headerView = layout.findViewById(R.id.headerbanner);// 要先找到该布局，再来加载数据
		hotsale = (RecyclerView) headerView.findViewById(R.id.recyclerView1);
		hotsale.setHasFixedSize(true);// 优化
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		hotsale.setLayoutManager(layoutManager);
		hotsaleAdapter = new MyHotAdapter();
		hotsale.setAdapter(hotsaleAdapter);
		hotsaleAdapter.setOnItemClickLitener(new OnItemClickLitener() {

			@Override
			public void onItemClick(View view, int position) {
				//监听recycleview
//				Intent intent = new Intent(getActivity(),ClActivity.class);
//				intent.putExtra(Constants.KEY.QZ_REQUEST, position);
//				startActivity(intent);
				startActivity(new Intent(getContext(), DetailActivity.class));
				
			}
		});
	}

	private void initVP(View layout) {
		vp_type = (ViewPager) layout.findViewById(R.id.vp_type);
		FragmentManager fm = getChildFragmentManager();
		vp_type.setAdapter(new PagerBannerAdapter(fm));
		vp_type.setCurrentItem(BANNER_COUNT / 2);
		vp_type.setOnPageChangeListener(new MyPageChangeListener());
	}

	class MyHotAdapter extends RecyclerView.Adapter<MyViewHolder> {

		private OnItemClickLitener mOnItemClickLitener;

		public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
			this.mOnItemClickLitener = mOnItemClickLitener;
		}

		@Override
		public int getItemCount() {
			return 8;
		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
			View itemView = inflater.inflate(R.layout.recycler_item, null);
			MyViewHolder holder = new MyViewHolder(itemView);
			return holder;
		}

		@Override
		public void onBindViewHolder(final MyViewHolder holder, int position) {

			holder.setTitle(Util.TITLESALL[position]);
			holder.setGrade("￥"+Util.TITLEPRICE[position]);
			holder.setImageUrl(Util.IMGS[position]);
			if (mOnItemClickLitener != null) {
				holder.itemView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int pos = holder.getLayoutPosition();
						mOnItemClickLitener.onItemClick(holder.itemView, pos);
					}
				});
			}
		}
	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		public MyViewHolder(View itemView) {

			super(itemView);
		}

		public void setTitle(String title) {
			TextView tv_name = (TextView) itemView.findViewById(R.id.tv_name);
			tv_name.setText(title);
		}

		public void setImageUrl(int imageUrl) {
			ImageView img_film = (ImageView) itemView
					.findViewById(R.id.img_film);
			img_film.setImageResource(imageUrl);
		}

		public void setGrade(String grade) {
			TextView tv_num = (TextView) itemView.findViewById(R.id.tv_num);
			tv_num.setText(grade);
		}

	}

	class PagerBannerAdapter extends FragmentPagerAdapter {

		public PagerBannerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			position %= 2;
			return new HeaderFragment(position);
		}

		@Override
		public int getCount() {
			return BANNER_COUNT;
		}

	}

	class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			// 拖拽
			case ViewPager.SCROLL_STATE_DRAGGING:
				isDrag = true;
				break;
			// 空闲
			case ViewPager.SCROLL_STATE_IDLE:
				isDrag = false;
				break;
			// 拖拽松开恢复到空闲
			case ViewPager.SCROLL_STATE_SETTLING:
				isDrag = false;
				break;

			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
		}

	}
	
	class GoolsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Util.ALLIMGS.length;
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
			View inflate = inflater.inflate(R.layout.item_cl, null);
			photo = (ImageView) inflate.findViewById(R.id.imageView1);
			photo.setImageResource(Util.ALLIMGS[position]);
			return inflate;
		}
		
	}

}
