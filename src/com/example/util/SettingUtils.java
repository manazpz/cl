package com.example.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingUtils {
	
	public static void SaveOnlyWIFE(Context context, boolean isCheck){
		SharedPreferences sp = context.getSharedPreferences(Constants.Result.SETTINGS, context.MODE_PRIVATE);
		sp.edit().putBoolean(Constants.Result.KEY, isCheck).commit();
	}
	public static boolean readOnlyWIFI(Context context){
		SharedPreferences sp = context.getSharedPreferences(Constants.Result.SETTINGS, Context.MODE_PRIVATE);
		boolean wifiOnly = sp.getBoolean(Constants.Result.KEY, false);
		return wifiOnly;
	}
	public static void LoginMode(Context context, String ischeck) {
		SharedPreferences sp = context.getSharedPreferences(Constants.Result.LOGINKEY, 0);
		sp.edit().putString("denglu", ischeck).commit();
	}
	public static String ReadLoginMode(Context context) {
		SharedPreferences sp = context.getSharedPreferences(Constants.Result.LOGINKEY, 0);
		String ischeck = sp.getString("denglu", "error");
		return ischeck;
	}
	
}
