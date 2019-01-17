package com.parkho.camerasample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *     공용으로 쓰는 기능 모음
 * </p>
 *
 * @author parkho79
 * @date 2019-01-17
 */
public class PhUtil
{
	/**
	 * 해당 action 을 지원하는 app 이 있는지 확인
	 */
	public static boolean isValidIntent(Context a_context, String a_strAction) {
		final PackageManager packageManager = a_context.getPackageManager();
		final Intent intent = new Intent(a_strAction);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * 현재 날짜, 시간을 기준으로 unique file name 생성
	 */
	public static String getTempFileName() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}

	/**
	 * Scaled decode 후 scaled 된 bitmap 반환
	 * @param a_strImage image path
	 * @param a_reqWidth scaled image width
	 * @param a_reqHeight scaled image height
	 * @return scaled bitmap
	 * @throws FileNotFoundException
	 */
	public static Bitmap decodeSampledBitmapFromFile(String a_strImage, int a_reqWidth, int a_reqHeight) throws FileNotFoundException {
		// Get the dimensions of the bitmap
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(a_strImage, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, a_reqWidth, a_reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(a_strImage, options);
	}

	/**
	 * Scaled bitmap 생성 시 scaled 할 비율 계산
	 */
	private static int calculateInSampleSize(BitmapFactory.Options a_options, int a_reqWidth, int a_reqHeight) {
		// Raw height and width of image
		final int height = a_options.outHeight;
		final int width = a_options.outWidth;
		int inSampleSize = 1;

		if (height > a_reqHeight || width > a_reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) >= a_reqHeight
					&& (halfWidth / inSampleSize) >= a_reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
}
