package com.wisdomregulation.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import com.github.promeg.pinyinhelper.Pinyin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util_String {
	/**
	 * 对不可变得数组进行包装 实现可以增加
	 * @param stringorg
	 * @param target
	 * @return
	 */
	public static String[] add(String[] stringorg, String target) {
		String[] result = null;
		if (stringorg == null) {
			result = new String[] { target };

		} else {
			result = new String[stringorg.length + 1];
			for (int i = 0; i < stringorg.length; i++) {
				result[i] = stringorg[i];
			}
			result[result.length - 1] = target;
		}
		return result;
	}
	/**
	 * 对不可变得数组进行包装 实现可以增加
	 * @param stringorg
	 * @param target
	 * @return
	 */
	public static String[] add(String[] stringorg, String[] target) {
		String[] result = null;
		if (stringorg == null) {
			if(target!=null){
				result = target;
			}else{
				
			}
			

		} else {
			result = new String[stringorg.length + target.length];
			for (int i = 0; i < stringorg.length; i++) {
				result[i] = stringorg[i];
			}
			for (int i = stringorg.length; i < result.length; i++) {
				result[i] = target[i-stringorg.length];
			}
			
		}
		return result;
	}
	/**
	 * 判断是否包含在list里
	 * @param checklist
	 * @param key
	 * @return
	 */
	public static boolean isInList(List<String> checklist,String key){
		boolean result=false;
		for (int i = 0; i < checklist.size(); i++) {
			if(checklist.get(i).equals(key)){
				result=true;
				break;
			}
		}
		return result;
	}
	/**
	 * 判断是不是都编辑了
	 * @param needinserteditlist
	 * @return
	 */
	public static boolean isInsertAll(List<EditText> needinserteditlist){
		boolean isallinsert=true;
		if(needinserteditlist!=null){
			for (int i = 0; i < needinserteditlist.size(); i++) {
				if(needinserteditlist.get(i).getText().toString().trim().equals("")){
					isallinsert=false;
					break;
				}
			}
		}

		return isallinsert;
	}

	/**
	 * 高亮关键字
	 * @param text
	 * @param target
	 * @return
	 */
	public static SpannableStringBuilder highlight(String text, String target) {
		text = text == null ? "" : text;
		target = target == null ? "" : target;
		SpannableStringBuilder spannable = new SpannableStringBuilder(text);
		if (!text.equals("") && !target.equals("")) {
			CharacterStyle span = null;

			Pattern p = Pattern.compile(target);
			Matcher m = p.matcher(text);
			while (m.find()) {
				span = new ForegroundColorSpan(Color.RED);// 需要重复！
				spannable.setSpan(span, m.start(), m.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		} else {
			
		}

		return spannable;
	}

	/**
	 * 获得今天的日期
	 * @return
	 */
	public static String getDate() {
		String datestring = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		datestring = sdf.format(date);
		return datestring;
	}
	
	/**
	 * 获得今天的Long型
	 * @return
	 */
	public static long getDateLong() {
		Date date = new Date();
		return date.getTime();
		
	}


	/**
	 * 对选项字段进行处理
	 * @param nosmooth
	 * @return
	 */
	public static String getSmoothString(String nosmooth) {
		nosmooth = nosmooth.replace("_", "");
		if (nosmooth.matches("(.*)[0-9]{3}")) {
			nosmooth = nosmooth.replaceAll("[0-9]{3}", "");
		}
		return nosmooth;
	}
	
	/**
	 * 转义特殊字符
	 * @param keyword
	 * @return
	 */
	public static String washString(String keyword) {
		if (keyword != null && !keyword.equals("")) {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]",
					"?", "^", "{", "}", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}

	/**
	 * 获得16位的uuid
	 * @return
	 */
	public static String get16Uuid() {
		String leastbits = UUID.randomUUID().getLeastSignificantBits() + "";
		return leastbits;
	}

	/**
	 * 获得32位的uuid
	 * @return
	 */
	public static String get32Uuid() {
		String leastbits = UUID.randomUUID().toString().replaceAll("-", "");
		return leastbits;
	}

	/**
	 * 数字变成竖直的中国字
	 * @param d
	 * @return
	 */
	public static String int2String(int d) {
		String result = "";
		StringBuffer sb = new StringBuffer();
		String[] str = { "零", "一", "二", "三", "四", "伍", "六", "七", "八", "九" };
		String ss[] = new String[] { "", "十", "百", "千", "万", "十", "百", "千", "亿" };
		String s = String.valueOf(d);

		for (int i = 0; i < s.length(); i++) {
			String index = String.valueOf(s.charAt(i));
			sb = sb.append(str[Integer.parseInt(index)]);
		}
		String sss = String.valueOf(sb);
		
		int i = 0;
		for (int j = sss.length(); j > 0; j--) {
			sb = sb.insert(j, ss[i++]);
		}
		char[] tmp = sb.toString().toCharArray();
		String[] tmp2 = new String[tmp.length];
		for (int j = 0; j < tmp2.length; j++) {
			if (j != tmp2.length - 1) {
				tmp2[j] = tmp[j] + "\n";
			} else {
				tmp2[j] = tmp[j] + "";
			}
			result = result + tmp2[j];
		}
		if(s.length()==2){
			if(s.substring(1, 2).equals("0")){
				result=result.substring(2,4);
			}else{
				result=result.substring(2,result.length());
			}
			
		}
		return result;
	}

	/**
	 * 中文字符转拼音
	 * @param chinesestr
	 * @return
	 */
	public static  String getchinese2pinyin(String chinesestr) {
		long old=System.currentTimeMillis();
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        format.setVCharType(HanyuPinyinVCharType.WITH_V);
		String targetchinese = Pinyin.toPinyin(chinesestr, "");

//        char[] chinesechar = chinesestr.toCharArray();
//        try {
//            for (int i = 0; i < chinesechar.length; i++) {
//
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
		if (targetchinese.length() > 60) {
			targetchinese = targetchinese.substring(0, 60);
		}

		return targetchinese.toLowerCase();
	}

	/**
	 * 输入流转string
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (Exception e) {

		}
		return inputStream2String(is);
	}

	public static String inputStream2String(InputStream is) {
		InputStreamReader reader = null;
		StringWriter writer = new StringWriter();
		if (null == is) {
			return null;
		}
		StringBuilder resultSb = null;
		try {
			reader = new InputStreamReader(is, "UTF-8");
			char[] buffer = new char[1024];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}
		} catch (Exception ex) {
		} finally {
			closeIO(is);
		}
		return null == writer ? null : writer.toString();
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

	public static String getdeUnicode(String content) {// 将16进制数转换为汉字
		String enUnicode = null;
		String deUnicode = null;
		for (int i = 0; i < content.length(); i++) {
			if (enUnicode == null) {
				enUnicode = String.valueOf(content.charAt(i));
			} else {
				enUnicode = enUnicode + content.charAt(i);
			}
			if (i % 4 == 3) {
				if (enUnicode != null) {
					if (deUnicode == null) {
						deUnicode = String.valueOf((char) Integer.valueOf(
								enUnicode, 16).intValue());
					} else {
						deUnicode = deUnicode
								+ String.valueOf((char) Integer.valueOf(
										enUnicode, 16).intValue());
					}
				}
				enUnicode = null;
			}

		}
		return deUnicode;
	}

	public static String getenUnicode(String content) {// 将汉字转换为16进制数
		String enUnicode = null;
		for (int i = 0; i < content.length(); i++) {
			if (i == 0) {
				enUnicode = hexString(Integer.toHexString(content.charAt(i))
						.toUpperCase());
			} else {
				enUnicode = enUnicode
						+ hexString(Integer.toHexString(content.charAt(i))
								.toUpperCase());
			}
		}
		return enUnicode;
	}

	private static String hexString(String hexString) {
		String hexStr = "";
		for (int i = hexString.length(); i < 4; i++) {
			if (i == hexString.length())
				hexStr = "0";
			else
				hexStr = hexStr + "0";
		}
		return hexStr + hexString;
	}

	public static String getClassLastName(Object object) {
		String lastname = object.getClass().getSimpleName();
		lastname = lastname.toLowerCase();
		return lastname;
	}





	public static String getNoString(String string) {
		string = string.replaceAll("\"", "“");
		return string;
	}
	public static boolean isjson(String t){
		boolean isjson=true;
		try {
			JSONObject test=new JSONObject(t);
		} catch (JSONException e) {
			isjson=false;
			e.printStackTrace();
		}
		return isjson;
		
	}
	public static boolean isjsonArray(String t){
		boolean isjson=true;
		try {
			JSONArray test=new JSONArray(t);
		} catch (JSONException e) {
			isjson=false;
			e.printStackTrace();
		}
		return isjson;
		
	}
}
