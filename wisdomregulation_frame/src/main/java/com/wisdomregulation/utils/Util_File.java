package com.wisdomregulation.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Util_File {
	private static final  String TAG = "FileUtil";
	private static final File parentPath = Environment.getExternalStorageDirectory();
	private static   String storagePath = "";
	private static final String DST_FOLDER_NAME = "PlayCamera";

	/**初始化保存路径
	 * @return
	 */
	private static String initPath(){
		if(storagePath.equals("")){
			storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
			File f = new File(storagePath);
			if(!f.exists()){
				f.mkdir();
			}
		}
		return storagePath;
	}

	/**保存Bitmap到sdcard
	 * @param b
	 */
	public static List<File> getFileSort(String path) {
		 
        List<File> list = getFiles(path, new ArrayList<File>());
 
        if (list != null && list.size() > 0) {
 
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }
 
                }
            });
 
        }
        
 
        return list;
    }
	/**
	 * 对文件列表排序
	 * @param path
	 * @return
	 */
	public static List<File> getFileSort(File path) {
		 
        List<File> list = getFiles(path.getAbsolutePath(), new ArrayList<File>());
 
        if (list != null && list.size() > 0) {
 
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }
 
                }
            });
 
        }
        return list;
    }
	/**
	 * 获得排序
	 * @param baselist
	 * @return
	 */
	public static List<Base_Entity> getSortEntityList(List<Base_Entity> baselist) {
		 if(baselist!=null&&baselist.size()>0){
			 Collections.sort(baselist, new Comparator<Base_Entity>() {

				@Override
				public int compare(Base_Entity lhs, Base_Entity rhs) {
                    if (Long.parseLong(lhs.getUpDatadate()) < Long.parseLong(rhs.getUpDatadate()) ) {
                        return 1;
                    } else if (Long.parseLong(lhs.getUpDatadate()) == Long.parseLong(rhs.getUpDatadate())) {
                        return 0;
                    } else {
                        return -1;
                    }
				}
			});
		 }
 

        
 
        return baselist;
    }
    /**
     * 
     * 获取目录下所有文件
     * 
     * @param realpath
     * @param files
     * @return
     */
    public static List<File> getFiles(String realpath, List<File> files) {
 
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }
	/**
	 * 保存bitmap
	 * @param savehead
	 * @param b
	 */
	public static void saveBitmap(String savehead,Bitmap b){

		String path = initPath();
		long dataTake = System.currentTimeMillis();
		String jpegName = savehead + "/" + dataTake +".jpg";
		Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			Log.i(TAG, "saveBitmap成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "saveBitmap:失败");
			e.printStackTrace();
		}

	}
	/**
	 * 检查文件类型  比如mp3 mp4 jpg
	 * @param file
	 * @return
	 */
	public static int checkFileType(File file){
		int result=0;
		String fileend="";
		if(file.isFile()){
			fileend=file.getName().split("\\.")[1].trim();
		}
		if (fileend.equalsIgnoreCase("mp3")||fileend.equalsIgnoreCase("wav")||fileend.equalsIgnoreCase("wma")||fileend.equalsIgnoreCase("ogg")) {
			result=1;
		}else if (fileend.equalsIgnoreCase("mp4")||fileend.equalsIgnoreCase("rmvb")||fileend.equalsIgnoreCase("mkv")||fileend.equalsIgnoreCase("flv")) {
			result=2;
		}else if (fileend.equalsIgnoreCase("jpg")||fileend.equalsIgnoreCase("bmp")||fileend.equalsIgnoreCase("jpeg")||fileend.equalsIgnoreCase("png")) {
			result=3;
		}
		return result;
	}
    /**
     * 输入流变成2进制
     * @param inStream
     * @return
     */
    public static final byte[] input2byte(InputStream inStream) {
        if (inStream == null) {
            return null;
        }
        byte[] in2b = null;
        BufferedInputStream in = new BufferedInputStream(inStream);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int rc = 0;
        try {
            while ((rc = in.read()) != -1) {
                swapStream.write(rc);
            }
            in2b = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(inStream, in, swapStream);
        }
        return in2b;
    }
	public static void closeIO(Closeable... closeables) {
		if (null == closeables || closeables.length <= 0) {
			return;
		}
		for (Closeable cb : closeables) {
			try {
				if (null == cb) {
					continue;
				}
				cb.close();
			} catch (IOException e) {

			}
		}
	}
	/**
	 * 删除指定文件夹下的空文件夹 为了清除没有证据采集的文件夹 减少上传文件夹数量
	 */
	public static void checkisEmptyFile2delete(String targetparentfile){
		List<File> dirlist=new ArrayList<File>();
		File[] underfile=new File(targetparentfile).listFiles();
		if(underfile!=null){
			for (int i = 0; i < underfile.length; i++) {
				if(underfile[i].isDirectory()){
					dirlist.add(underfile[i]);
				}
			}
			for (int i = 0; i < dirlist.size(); i++) {
				if(dirlist.get(i).listFiles().length<=0){
					dirlist.get(i).delete();
				}
			}
		}


	}
	/**
	 * 循环删除
	 * @param filename
	 */
	public static void deleteFile(String filename) {
		String newname=filename+ System.currentTimeMillis();
		new File(filename).renameTo(new File(newname));
		File file=new File(newname);
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				boolean tmp=file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
			boolean tmp=file.delete();
		} else {
			//
		}
		new File(filename).mkdirs();
	}
	/**
	 * 删除文件夹所有内容
	 *
	 */
	public static void deleteFile(File file) {
		String newname=file.getAbsolutePath()+System.currentTimeMillis();
		file.renameTo(new File(newname));
		file=new File(newname);
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			//
		}
	}
	/**
	 * 上传除了某文件之外的文件夹下所有文件
	 * @param parent
	 * @param expname
	 * @param type
	 */
	public static void getFileList2upload(String parent,String expname){
		//System.out.println("开始上传数据");
		List<File> dirlist=new ArrayList<File>();
		File[] underfile=new File(parent).listFiles();
		for (int i = 0; i < underfile.length; i++) {
			if(underfile[i].getName().equals(expname)){
				
			}else{
				dirlist.add(underfile[i]);
			}
		}
		uploadFileMul(dirlist);
		
	}
	/**
	 * 批量上传
	 * @param dirlist
	 * @param type
	 */
	public static void uploadFileMul(List<File> dirlist){
		for (int i = 0; i < dirlist.size(); i++) {
			//System.out.println("数据上传文件");
			uploadFileMul(dirlist.get(i));
		}
	}
	
	
	public static void uploadFileTest(final File dir){
		//System.out.println("开始上传:");
		KJHttp kjh = new KJHttp();
		HttpParams params = new HttpParams();
		File[] underfile=dir.listFiles();
		for (int i = 0; i < underfile.length; i++) {
			try {
				params.put(underfile[i].getName(), underfile[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Static_InfoApp.create().showToast("视频过大");
			}
		}
		kjh.post(Static_InfoApp.create().getiphead()+Static_ConstantLib.upload, params, new HttpCallBack() {

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				//System.out.println("成功返回数据:"+t);
				//System.out.println("成功上传数据:"+dir.getName());
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(errorNo, strMsg);
			}
			
		});
		
	}
	public static void uploadFileSingleTest(final File file){
		//System.out.println("开始上传:");
		KJHttp kjh = new KJHttp();
		HttpParams params = new HttpParams();

//		File[] underfile=file.listFiles();
		String tuid= UUID.randomUUID().toString().replace("-","");
		params.put("tuid", tuid);
//		params.putHeaders("cookie", HttpConfig.sCookie);
//		for (int i = 0; i < underfile.length; i++) {
//			try {
				params.put(file.getName(), file);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				Static_InfoApp.create().showToast("视频过大");
//			}
//		}
		kjh.post(Static_InfoApp.create().getiphead()+Static_ConstantLib.upload, params, new HttpCallBack() {

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
//				System.out.println("成功返回数据:"+t);
//				System.out.println("成功上传数据:"+file.getName());
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(errorNo, strMsg);
			}

		});

	}
	public static void uploadFileMul(final File dir){
		//System.out.println("开始上传:");
		KJHttp kjh = new KJHttp();
		HttpParams params = new HttpParams();
		File[] underfile=dir.listFiles();
		String tuid=dir.getName().split("@")[1].trim();
		params.put("tuid", tuid);
		for (int i = 0; i < underfile.length; i++) {
			try {
				params.put(underfile[i].getName(), underfile[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Static_InfoApp.create().showToast("视频过大");
			}
		}
		kjh.post(Static_InfoApp.create().getiphead()+Static_ConstantLib.upload, params, new HttpCallBack() {

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				//System.out.println("成功返回数据:"+t);
				//System.out.println("成功上传数据:"+dir.getName());
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(errorNo, strMsg);
			}
			
		});
		
	}

}
