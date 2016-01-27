package com.example.cl;

import com.example.Fragment.ClassifyFragment;
import com.example.Fragment.FindFragment;
import com.example.Fragment.HomeFragment;
import com.example.Fragment.MapFragment;
import com.example.Fragment.MineFragment;
import com.xinbo.utils.TextViewUtils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
	private LayoutInflater inflater;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        inflater = getLayoutInflater();
        addTab("首页", R.drawable.ic_tab_albums, HomeFragment.class);
		addTab("分类", R.drawable.ic_tab_fenlei, ClassifyFragment.class);
		addTab("图集", R.drawable.ic_tab_tuji, MapFragment.class);
		addTab("我的", R.drawable.ic_tab_songs, MineFragment.class);
		addTab("发现", R.drawable.ic_tab_faxian, FindFragment.class);
    }
	
	private void addTab(String title, int drawableRes, Class fragmentClass) {
		View tabItem1 = inflater.inflate(R.layout.tab_item, null);
		TextView textView = (TextView) tabItem1.findViewById(R.id.item_name);
		textView.setText(title);
		TextViewUtils.setTextDrawable(this, drawableRes, textView);
		mTabHost.addTab(mTabHost.newTabSpec(title).setIndicator(tabItem1), fragmentClass, null);
		
	}
   
}
