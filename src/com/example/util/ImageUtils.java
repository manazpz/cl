package com.example.util;

import com.xinbo.utils.ConnectionUtils;
import com.xinbo.utils.UILUtils;

import android.content.Context;
import android.widget.ImageView;

public class ImageUtils {
	public static void displayImageNoAnim(Context context, String imageUrls, ImageView mImageView) {
		
			UILUtils.displayImageNoAnim(imageUrls, mImageView);
		
	}
}
