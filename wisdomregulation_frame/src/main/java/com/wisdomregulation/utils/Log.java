/*
 * Copyright (C) 2012 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wisdomregulation.utils;

import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.MissingFormatArgumentException;

/**
 * log类 可以阻止打log
 * @author King2016s
 *
 */
public class Log {
	public static final String TAG = "SRXLOG";
	private static boolean needlog=true;
	public static void i(String msg, Object args) {
		if(needlog){
			try {
				android.util.Log.i(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.i(msg, msg);
			}
		}

	}

	public static void d(String msg, Object args) {
		if(needlog){
			try {

				android.util.Log.d(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}

	public static void e(String msg, Object args) {
		if(needlog){
			try {
				android.util.Log.i(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.i(msg, msg);
			}
		}

	}

	public static void v(String msg, Object args) {
		if(needlog){
			try {
				android.util.Log.i(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}

	public static void w(String msg, Object args) {
		if(needlog){
			try {
				android.util.Log.w(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}
	public static void i(String msg) {
		if(needlog){
			try {
				android.util.Log.i(msg, ":");
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.i(msg, msg);
			}
		}

	}

	public static void d(String msg) {
		if(needlog){
			try {

				android.util.Log.d(msg,":");
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}

	public static void e(String msg) {
		if(needlog){
			try {
				android.util.Log.i(msg, ":");
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.i(msg, msg);
			}
		}

	}

	public static void v(String msg) {
		if(needlog){
			try {
				android.util.Log.i(msg, ":");
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}

	public static void w(String msg) {
		if(needlog){
			try {
				android.util.Log.w(msg, ":");
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}
	public static void i(String msg, Object args,boolean bool) {
		if(bool){
			try {
				android.util.Log.i(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.i(msg, msg);
			}
		}

	}

	public static void d(String msg, Object args,boolean bool) {
		if(bool){
			try {

				android.util.Log.d(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}

	public static void e(String msg, Object args,boolean bool) {
		if(bool){
			try {
				android.util.Log.i(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.i(msg, msg);
			}
		}

	}

	public static void v(String msg, Object args,boolean bool) {
		if(bool){
			try {
				android.util.Log.i(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}

	public static void w(String msg, Object args,boolean bool) {
		if(bool){
			try {
				android.util.Log.w(msg, args.toString());
			} catch (MissingFormatArgumentException e) {
				android.util.Log.i(msg, "vitamio.Log", e);
				android.util.Log.d(msg, msg);
			}
		}

	}
	/**
	 * 将log写入文件
	 * @param string
	 */
	public static void writeLogSd(final String string){
		new Thread(){public void run(){
			try {
				File file=new File(Static_InfoApp.create().getPath()+"/srxlog.txt");
				FileOutputStream os=new FileOutputStream(file);
				os.write(string.getBytes());
				os.close();
				Log.v("AllT", "finish");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}}.start();

		
	}


}
