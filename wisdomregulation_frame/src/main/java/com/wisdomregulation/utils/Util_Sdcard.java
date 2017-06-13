package com.wisdomregulation.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.Method;

public class Util_Sdcard {

	/**
	 * 在sd卡变化时 判断之前存储的路径 
	 * @param context
	 * @param dir
	 * @param flag
	 * @return
	 */
	public static String getBigSdCardPath(Context context,String dir,boolean flag) {
		String result = "";
		Context mActivity = context;
		String[] paths = null;
		if (mActivity != null) {
			StorageManager mStorageManager = (StorageManager) mActivity
					.getSystemService(Activity.STORAGE_SERVICE);
			try {
				Method mMethodGetPaths = mStorageManager.getClass().getMethod(
						"getVolumePaths");
				paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
				long size = 0;
				for (int i = 0; i < paths.length; i++) {
					if(new File(paths[i]+dir+"/").exists()){
						result=paths[i];
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 获得除此目录以外的存储路劲大容量sd卡  大容量
	 * @param context
	 * @return
	 */
	public static String getBigSdCardPath(Context context,File exfile) {
		String result = "";
		Context mActivity = context;
		String[] paths = null;
		if (mActivity != null) {
			StorageManager mStorageManager = (StorageManager) mActivity
					.getSystemService(Activity.STORAGE_SERVICE);
			try {
				Method mMethodGetPaths = mStorageManager.getClass().getMethod(
						"getVolumePaths");
				paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
				long size = 0;
				for (int i = 0; i < paths.length; i++) {
					if(!new File(paths[i]).getAbsolutePath().equals(exfile.getAbsolutePath())){
						result=paths[i];
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 获得大容量sd卡  大容量
	 * @param context
	 * @return
	 */
	public static String getBigSdCardPath(Context context) {
		String result = "";
		Context mActivity = context;
		String[] paths = null;
		if (mActivity != null) {
			StorageManager mStorageManager = (StorageManager) mActivity
					.getSystemService(Activity.STORAGE_SERVICE);
			try {
				Method mMethodGetPaths = mStorageManager.getClass().getMethod(
						"getVolumePaths");
				paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
				long size = 0;
				for (int i = 0; i < paths.length; i++) {
					if (getAvailableSize(paths[i]) > size) {
						size = getAvailableSize(paths[i]);
						result = paths[i];
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("返回的存储目录"+result);
		return result;
	}

	/**
	 * 获得可用大小
	 * @param path
	 * @return
	 */
	@SuppressLint("NewApi")
	public static long getAvailableSize(String path) {
		try {
			File base = new File(path);
			StatFs stat = new StatFs(base.getPath());
			long nAvailableCount =  stat.getBlockSize()
					* ((long) stat.getAvailableBlocks());
			return nAvailableCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}




}
