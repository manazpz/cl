package com.example.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import java.util.LinkedList;
import java.util.List;
import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;
import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class DemoApplication extends Application {
	private List<Activity> activityList = new LinkedList();//用于保留所有的activity界面
	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		ActiveAndroid.initialize(this);
		initConfig(getApplicationContext());
		initImageLoader(getApplicationContext());
	}
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}
	public void exit() {
		for(Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}
	//出事花网络下载图片
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		ImageLoader.getInstance().init(config);
	}
	public static void initConfig(Context context) {
		BmobConfiguration config = new BmobConfiguration.Builder(context).customExternalCacheDir("Smile").build();
		BmobPro.getInstance(context).initConfig(config);
	}
}
