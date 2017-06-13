package com.wisdomregulation.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Images;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wisdomregulation.frame.R;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;
import java.io.InputStream;

public class Util_Image {
/**
 * gridview的对图片进行尺寸设置
 * @param context
 * @param image
 * @param drawableId
 * @param screesplit
 */
public static void scaleImage(Context context,ImageView image,int drawableId,int screesplit){
	BitmapFactory.Options options = new BitmapFactory.Options();  
    options.inJustDecodeBounds = true;  
	BitmapFactory.decodeResource(context.getResources(), drawableId, options);
	int width=options.outWidth;
	int high=options.outHeight;
	double screenwidth= Static_InfoApp.create().getAppScreenWidth()/screesplit;
	double scalesize=screenwidth/width;
	double screenhigh=scalesize*high;
	image.setLayoutParams(new LinearLayout.LayoutParams((int)screenwidth, (int)screenhigh));
	image.setImageResource(drawableId);
}
/**
 * 获得规定尺寸的图片
 * @param res
 * @param resId
 * @param reqWidth
 * @param reqHeight
 * @return
 */
public static Bitmap bitmapFromResource(Resources res, int resId,
		int reqWidth, int reqHeight) {
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	// BitmapFactory.decodeResource(res, resId, options);
	// options = BitmapHelper.calculateInSampleSize(options, reqWidth,
	// reqHeight);
	// return BitmapFactory.decodeResource(res, resId, options);

	// 通过JNI的形式读取本地图片达到节省内存的目的
	InputStream is = res.openRawResource(resId);
	return bitmapFromStream(is, null, reqWidth, reqHeight);
}

/**
 * 获取一个指定大小的bitmap
 * 
 * @param reqWidth
 *            目标宽度
 * @param reqHeight
 *            目标高度
 */
public static Bitmap bitmapFromFile(String pathName, int reqWidth,
		int reqHeight) {
	if (reqHeight == 0 || reqWidth == 0) {
		try {
			return BitmapFactory.decodeFile(pathName);
		} catch (OutOfMemoryError e) {
		}
	}

	BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	BitmapFactory.decodeFile(pathName, options);
	options = calculateInSampleSize2(options, reqWidth, reqHeight);
	return BitmapFactory.decodeFile(pathName, options);
}

/**
 * 获取一个指定大小的bitmap
 * 
 * @param data
 *            Bitmap的byte数组
 * @param offset
 *            image从byte数组创建的起始位置
 * @param length
 *            the number of bytes, 从offset处开始的长度
 * @param reqWidth
 *            目标宽度
 * @param reqHeight
 *            目标高度
 */
public static Bitmap bitmapFromByteArray(byte[] data, int offset,
		int length, int reqWidth, int reqHeight) {
	if (reqHeight == 0 || reqWidth == 0) {
		try {
			return BitmapFactory.decodeByteArray(data, offset, length);
		} catch (OutOfMemoryError e) {
		}
	}
	BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	options.inPurgeable = true;
	BitmapFactory.decodeByteArray(data, offset, length, options);
	options = calculateInSampleSize(options, reqWidth, reqHeight);
	return BitmapFactory.decodeByteArray(data, offset, length, options);
}

/**
 * 获取一个指定大小的bitmap<br>
 * 实际调用的方法是bitmapFromByteArray(data, 0, data.length, w, h);
 * 
 * @param is
 *            从输入流中读取Bitmap
 * @param reqWidth
 *            目标宽度
 * @param reqHeight
 *            目标高度
 */
public static Bitmap bitmapFromStream(InputStream is, int reqWidth,
		int reqHeight) {
	if (reqHeight == 0 || reqWidth == 0) {
		try {
			return BitmapFactory.decodeStream(is);
		} catch (OutOfMemoryError e) {
		}
	}
	byte[] data = Util_File.input2byte(is);
	return bitmapFromByteArray(data, 0, data.length, reqWidth, reqHeight);
}

// public static Bitmap bitmapFromStream(File file, int reqWidth, int
// reqHeight) {
// try {
// return bitmapFromStream(new FileInputStream(file), reqWidth, reqHeight);
// } catch (FileNotFoundException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// return null;
// }
/**
 * 获得缩略图 
 * @param context
 * @param path
 * @param width
 * @param height
 * @return
 */
public static Bitmap getThumbnail(Context context,String path, int width,
		int height){
	int sacle=6;
	width=width/sacle;
	height=height/sacle;
	int filetype=Util_File.checkFileType(new File(path));
	switch (filetype) {
	case 1:
		return decodeSampledBitmapFromResource(context.getResources(), R.drawable.flag_15, width, height);
	case 2:
		return getVideoThumbnail(path,width,height,Images.Thumbnails.MICRO_KIND);
	case 3:
		
		return getImageThumbnail(path,width,height);
	default:
		break;
	}
	return null;
}
/**
 * 获得图片缩略图
 * @param imagePath
 * @param width
 * @param height
 * @return
 */
public static Bitmap getImageThumbnail(String imagePath, int width,
		int height) {
	Bitmap bitmap = null;
	BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	// 获取这个图片的宽和高，注意此处的bitmap为null
	bitmap = BitmapFactory.decodeFile(imagePath, options);
	options.inJustDecodeBounds = false; // 设为 false
	// 计算缩放比
	int h = options.outHeight;
	int w = options.outWidth;
	int beWidth = w / width;
	int beHeight = h / height;
	int be = 1;
	if (beWidth < beHeight) {
		be = beWidth;
	} else {
		be = beHeight;
	}
	if (be <= 0) {
		be = 1;
	}
	options.inSampleSize = be;
	// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
	bitmap = BitmapFactory.decodeFile(imagePath, options);
	// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
	bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
			ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	return bitmap;
}

/**
 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
 * 
 * @param videoPath
 *            视频的路径
 * @param width
 *            指定输出视频缩略图的宽度
 * @param height
 *            指定输出视频缩略图的高度度
 * @param kind
 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
 * @return 指定大小的视频缩略图
 */
public static Bitmap getVideoThumbnail(String videoPath, int width,
		int height, int kind) {
	Bitmap bitmap = null;
	// 获取视频的缩略图
	bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);

	bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
			ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	return bitmap;
}

/**
 * 获取一个指定大小的bitmap
 * 
 * @param is
 *            从输入流中读取Bitmap
 * @param outPadding
 *            If not null, return the padding rect for the bitmap if it
 *            exists, otherwise set padding to [-1,-1,-1,-1]. If no bitmap
 *            is returned (null) then padding is unchanged.
 * @param reqWidth
 *            目标宽度
 * @param reqHeight
 *            目标高度
 */
public static Bitmap bitmapFromStream(InputStream is, Rect outPadding,
		int reqWidth, int reqHeight) {
	Bitmap bmp = null;
	if (reqHeight == 0 || reqWidth == 0) {
		try {
			return BitmapFactory.decodeStream(is);
		} catch (OutOfMemoryError e) {
		}
	}
	BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	options.inPurgeable = true;
	BitmapFactory.decodeStream(is, outPadding, options);
	options = calculateInSampleSize(options, reqWidth, reqHeight);
	bmp = BitmapFactory.decodeStream(is, outPadding, options);
	return bmp;
}

/**
 * 图片压缩处理（使用Options的方法） <br>
 * <b>说明</b> 使用方法：
 * 首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片 。
 * 然后将Options连同期望的宽度和高度一起传递到到本方法中。
 * 之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。 <br>
 * <b>说明</b> BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
 * ，这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数
 * ，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
 * ，返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、
 * outHeight和outMimeType属性都会被赋值。
 * 
 * @param reqWidth
 *            目标宽度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
 * @param reqHeight
 *            目标高度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
 */
public static BitmapFactory.Options calculateInSampleSize(
		final BitmapFactory.Options options, final int reqWidth,
		final int reqHeight) {
	// 源图片的高度和宽度
	final int height = options.outHeight;
	final int width = options.outWidth;
	int inSampleSize = 1;
	if (height > reqHeight || width > reqWidth) {
		// 计算出实际宽高和目标宽高的比率
		final int heightRatio = Math.round((float) height
				/ (float) reqHeight);
		final int widthRatio = Math.round((float) width / (float) reqWidth);
		// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
		// 一定都会大于等于目标的宽和高。
		inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	}
	// 设置压缩比例
	options.inSampleSize = inSampleSize;
	options.inJustDecodeBounds = false;
	return options;
}

public static Bitmap decodeSampledBitmapFromResource(Resources res,
		int resId, int imageViewWidth, int imageViewHeight) {

	// First decode with inJustDecodeBounds=true to check dimensions
	BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	BitmapFactory.decodeResource(res, resId, options);
	
	// Calculate inSampleSize
	options = calculateInSampleSize2(options, imageViewWidth,
			imageViewHeight);

	// Decode bitmap with inSampleSize set
	options.inJustDecodeBounds = false;
	return BitmapFactory.decodeResource(res, resId, options);
}

public static BitmapFactory.Options calculateInSampleSize2(
		BitmapFactory.Options options, int imageViewWidth,
		int imageViewHeight) {

	// Raw height and width of image
	 int imageHeight = options.outHeight;
	 int imageWidth = options.outWidth;
	// double imageScaleSize = maxMemory/imageMemory;
	// double imageScaleSize = 0.15;
	// final int reqWidth = (int)(imageViewWidth * imageScaleSize);
	// final int reqHeight = (int)(imageViewHeight * imageScaleSize);

	 int reqWidth = (int) (imageViewWidth);
	 int reqHeight = (int) (imageViewHeight);
	if(imageWidth>imageHeight){
		imageWidth=(imageHeight/reqHeight)*reqWidth;
	}else{
		imageHeight=(imageWidth/reqWidth)*reqHeight;
	}
	int inSampleSize = 1;

	if (imageHeight > reqHeight || imageWidth > reqWidth) {

		// Calculate ratios of height and width to requested height and
		// width
		final int heightRatio = Math.round((float) imageHeight
				/ (float) imageViewHeight);
		final int widthRatio = Math.round((float) imageWidth
				/ (float) imageViewWidth);

		// Choose the smallest ratio as inSampleSize value, this will
		// guarantee
		// a final image with both dimensions larger than or equal to the
		// requested height and width.
		inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	}

	options.inSampleSize = inSampleSize;
	options.inJustDecodeBounds = false;
	options.inPreferredConfig = Bitmap.Config.RGB_565;
	return options;
}

//public static double getMemoryScaleSize(ImageView imageview, int memoryScale) {
//	double imageMemory = (imageview.getWidth() * imageview.getHeight()) * 32 / 1024;
//	double maxMemory = (Runtime.getRuntime().maxMemory() / 1024 / memoryScale);
//	double imageScaleSize = maxMemory / imageMemory;
//	return imageScaleSize;
//}
}
