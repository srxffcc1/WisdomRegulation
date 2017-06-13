package com.wisdomregulation.entityfile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.wisdomregulation.utils.Util_File;

import java.io.File;

public class EntityS_File {
	private Context context;
	private String filename;
	private int fileType;
	private File org;
	private String fileallpath;
	private String parentpath;
	private String suffix;

	public EntityS_File(Context context, File file){
		this.context=context;
		this.org=file;
		this.parentpath=file.getParent();
		this.suffix=file.getName().split("\\.")[1].trim();
		this.fileType=Util_File.checkFileType(file);
		this.filename=file.getName().split("\\.")[0].trim();
		this.fileallpath=file.getAbsolutePath();
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public File getOrg() {
		return org;
	}

	public void setOrg(File org) {
		this.org = org;
	}

	public String getFileallpath() {
		return fileallpath;
	}

	public void setFileallpath(String fileallpath) {
		this.fileallpath = fileallpath;
	}

	public String getParentpath() {
		return parentpath;
	}

	public void setParentpath(String parentpath) {
		this.parentpath = parentpath;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public int delete(){
		if(org!=null){
			org.delete();
		}
		return 0;
	}
	public int rename(String rename){
		File renamefile=new File(parentpath+"/"+rename+"."+suffix);
		org.renameTo(renamefile);
		return 0;
	}
	public void open(){
		context.startActivity(openFile(fileallpath));
	}
	private  Intent openFile(String filePath){  
		  
        File file = new File(filePath);  
        if(!file.exists()) return null;  
        /* 取得扩展名 */  
        String end=file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length()).toLowerCase();   
        /* 依扩展名的类型决定MimeType */  
        if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||  
                end.equals("xmf")||end.equals("ogg")||end.equals("wav")){  
            return getAudioFileIntent(filePath);  
        }else if(end.equals("3gp")||end.equals("mp4")){  
            return getVideoFileIntent(filePath);  
        }else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||  
                end.equals("jpeg")||end.equals("bmp")){  
            return getImageFileIntent(filePath);  
        }else if(end.equals("apk")){  
            return getApkFileIntent(filePath);  
        }else if(end.equals("ppt")){  
            return getPptFileIntent(filePath);  
        }else if(end.equals("xls")){  
            return getExcelFileIntent(filePath);  
        }else if(end.equals("doc")){  
            return getWordFileIntent(filePath);  
        }else if(end.equals("pdf")){  
            return getPdfFileIntent(filePath);  
        }else if(end.equals("chm")){  
            return getChmFileIntent(filePath);  
        }else if(end.equals("txt")){  
            return getTextFileIntent(filePath,false);  
        }else{  
            return getAllIntent(filePath);  
        }  
    }  
      
    //Android获取一个用于打开APK文件的intent  
	private  Intent getAllIntent( String param ) {  
  
        Intent intent = new Intent();    
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri,"*/*");
        return intent;
    }
    //Android获取一个用于打开APK文件的intent
	private  Intent getApkFileIntent( String param ) {

		Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param ));  
        intent.setDataAndType(uri,"application/vnd.android.package-archive");   
        return intent;  
    }  
  
    //Android获取一个用于打开VIDEO文件的intent  
	private  Intent getVideoFileIntent( String param ) {  
		Intent intent = new Intent("android.intent.action.VIEW");  
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
        intent.putExtra("oneshot", 0);  
        intent.putExtra("configchange", 0);  
        Uri uri = Uri.fromFile(new File(param ));  
        intent.setDataAndType(uri, "video/*");  
        return intent;  
    }  
  
    //Android获取一个用于打开AUDIO文件的intent  
	private  Intent getAudioFileIntent( String param ){  
		Intent intent = new Intent("android.intent.action.VIEW");  
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
        intent.putExtra("oneshot", 0);  
        intent.putExtra("configchange", 0);  
        Uri uri = Uri.fromFile(new File(param ));  
        intent.setDataAndType(uri, "audio/*"); 
        return intent;  
    }  
  
    //Android获取一个用于打开Html文件的intent     
	private  Intent getHtmlFileIntent( String param ){  
  
        Uri uri = Uri.parse(param ).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param ).build();  
        Intent intent = new Intent("android.intent.action.VIEW");  
        intent.setDataAndType(uri, "text/html");  
        return intent;  
    }  
  
    //Android获取一个用于打开图片文件的intent  
	private  Intent getImageFileIntent( String param ) {  
  
		Intent intent = new Intent("android.intent.action.VIEW");  
        intent.addCategory("android.intent.category.DEFAULT");  
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        Uri uri = Uri.fromFile(new File(param ));  
        intent.setDataAndType(uri, "image/*");  
        return intent;  
    }  
  
    //Android获取一个用于打开PPT文件的intent     
	private  Intent getPptFileIntent( String param ){    
  
		Intent intent = new Intent("android.intent.action.VIEW");     
        intent.addCategory("android.intent.category.DEFAULT");     
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
        Uri uri = Uri.fromFile(new File(param ));     
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");     
        return intent;     
    }     
  
    //Android获取一个用于打开Excel文件的intent     
	private  Intent getExcelFileIntent( String param ){    
  
		Intent intent = new Intent("android.intent.action.VIEW");     
        intent.addCategory("android.intent.category.DEFAULT");     
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
        Uri uri = Uri.fromFile(new File(param ));     
        intent.setDataAndType(uri, "application/vnd.ms-excel");     
        return intent;     
    }     
  
    //Android获取一个用于打开Word文件的intent     
	private  Intent getWordFileIntent( String param ){    
  
		Intent intent = new Intent("android.intent.action.VIEW");     
        intent.addCategory("android.intent.category.DEFAULT");     
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
        Uri uri = Uri.fromFile(new File(param ));     
        intent.setDataAndType(uri, "application/msword");     
        return intent;     
    }     
  
    //Android获取一个用于打开CHM文件的intent     
	private  Intent getChmFileIntent( String param ){     
  
		Intent intent = new Intent("android.intent.action.VIEW");     
        intent.addCategory("android.intent.category.DEFAULT");     
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
        Uri uri = Uri.fromFile(new File(param ));     
        intent.setDataAndType(uri, "application/x-chm");     
        return intent;     
    }     
  
    //Android获取一个用于打开文本文件的intent     
	private  Intent getTextFileIntent( String param, boolean paramBoolean){     
  
		Intent intent = new Intent("android.intent.action.VIEW");     
        intent.addCategory("android.intent.category.DEFAULT");     
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
        if (paramBoolean){     
            Uri uri1 = Uri.parse(param );     
            intent.setDataAndType(uri1, "text/plain");     
        }else{     
            Uri uri2 = Uri.fromFile(new File(param ));     
            intent.setDataAndType(uri2, "text/plain");     
        }     
        return intent;     
    }    
    //Android获取一个用于打开PDF文件的intent     
	private  Intent getPdfFileIntent( String param ){     
  
		Intent intent = new Intent("android.intent.action.VIEW");     
        intent.addCategory("android.intent.category.DEFAULT");     
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
        Uri uri = Uri.fromFile(new File(param ));     
        intent.setDataAndType(uri, "application/pdf");     
        return intent;     
    }  
}
