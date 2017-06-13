package com.wisdomregulation.utils;

import com.github.promeg.pinyinhelper.Pinyin;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_ConstantLib;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Util_ServerString {
	public static final String  filedfinalhead="id,hashid,datastate,updatadate,createdatadate,tableid,isadd,created,status";
	public static String[] add(String[] stringorg, String target) {
		String[] result = null;
		if (stringorg == null) {
			result = new String[] { target };

		} else {
			result = new String[stringorg.length + 1];
			for (int i = 0; i < result.length - 1; i++) {
				result[i] = stringorg[i];
			}
			result[result.length - 1] = target;
		}
		return result;
	}

	public static String getDate() {
		String datestring = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		datestring = sdf.format(date);
		return datestring;
	}

	public static File[] getPathFileWith(String path, final String what) {
		File[] result = null;
		File file = new File(path);
		result = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.matches("(.*)" + what + "(.*)")) {
					return true;
				} else {
					return false;
				}
			}
		});
		return result;
	}

	public static String getSmoothString(String nosmooth) {
		nosmooth = nosmooth.replace("_", "");
		if (nosmooth.matches("(.*)[0-9]{3}")) {
			nosmooth = nosmooth.replaceAll("[0-9]{3}", "");
		}
		return nosmooth;
	}

	public static String get16Uuid() {
		String leastbits = UUID.randomUUID().toString().replaceAll("-", "");
		return leastbits;
//		String leastbits = UUID.randomUUID().getLeastSignificantBits() + "";
//		return leastbits;
	}

	public static String get32Uuid() {
		String leastbits = UUID.randomUUID().toString().replaceAll("-", "");
		return leastbits;
	}

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

	private String chinese2pinyin(String chinesestr) {
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

	/**
	 * 生成数据列
	 * @param object
	 * @return
	 */
	private static String field2sql(Object object) {
		if (object instanceof Base_Entity) {
			Base_Entity be = (Base_Entity) object;
			String str1 = ""+filedfinalhead+",";
			String str2 = "";
			for (int i = 0; i < be.size(); i++) {
				str2 = str2 + be.getField(i) + ",";
			}
			str2 = str2.substring(0, str2.length() - 1);
			return "(" + str1 + str2 + ")";
		} else {
			return "";
		}

	}
	/**
	 * 生成带default的数据列
	 * @param object
	 * @return
	 */
	private static String field2sqlhasdefault(Object object) {
		if (object instanceof Base_Entity) {
			Base_Entity be = (Base_Entity) object;
			String str1 = ""+filedfinalhead+",";
			String str2 = "";
			for (int i = 0; i < be.size(); i++) {
				str2 = str2 + be.getField(i) + " DEFAULT ' ',";
			}
			str2 = str2.substring(0, str2.length() - 1);
			return "(" + str1 + str2 + ")";
		} else {
			return "";
		}

	}

	/**
	 * 生成一条value的sql
	 * @param object
	 * @param index
	 * @return
	 */
	private static String value2sqlSingle(Object object) {
		if (object instanceof Base_Entity) {
			Base_Entity be = (Base_Entity) object;
			String idstr = Util_ServerString.get16Uuid();
			if (be.getId() != null && !be.getId().equals("")) {
				idstr = be.getId();
			}
			String str1 = "'" + idstr + "'" + "," 
					+ "'" + Util_ServerString.get32Uuid()+ "'" + ","
					+ "'" + "server" + "'" + ","
					+ "'" + (be.getUpDatadate().equals("")?new Date().getTime():be.getUpDatadate()) + "'"+ ","
					+ "'" + (be.getCreatedatadate().equals("")?new Date().getTime():be.getCreatedatadate()) + "'"+ "," 
					+ "'" + Util_ServerString.getClassLastName(object) + "'"+ ","
					+ "'" + "0" + "'" + ","
					+ "'" + (be.getCreated().equals("")?"":be.getCreated()) + "'" + ","
					+ "'" + (be.getStatus().equals("")?"":be.getStatus()) + "'" + ","
					;
			String str2 = "";
			for (int i = 0; i < be.size(); i++) {
				str2 = str2 + "'" + be.getValue(i).replace("\"", "“").replace("'", "‘")+ "'" + ",";
			}
			str2 = str2.substring(0, str2.length() - 1);
			return "(" + str1 + str2 + ")";
		} else {
			return "";
		}

	}
	
	
	
	
	/**
	 * 批量获得value语句
	 * @param objlist
	 * @return
	 */
	private static String value2sqlMul(List objlist) {
		String result="";
		for (int i = 0; i < objlist.size(); i++) {
			Base_Entity be = (Base_Entity) (objlist.get(i));
			result=result+value2sqlSingle(be)+",";
					
		}
		result = result.substring(0, result.length() - 1);
		return result;

	}
	
	/**防止字符串超过的优化方法
	 * @param objlist
	 * @return
	 */
	private static List<String> value2sqlMulList(List objlist) {
		List<String> resultList=new ArrayList<String>();
		String result="";
		for (int i = 0; i < objlist.size(); i++) {
			Base_Entity be = (Base_Entity) (objlist.get(i));
			result=result+value2sqlSingle(be)+",";
			if(result.length()>Static_ConstantLib.sqllengthlimit){
				resultList.add(result);
				result="";
			}else{
				
			}
		}
		if(result.length()>1){
			resultList.add(result);
		}
		
//		result = result.substring(0, result.length() - 1);
		return resultList;

	}


	/**
	 * 建立view语句
	 * @param object
	 * @return
	 */
	public static String createViewSql(Object object) {
		String head = "create view ";
		String head2 = "view_" + Util_ServerString.getClassLastName(object) + " as ";
		String field = "select "+filedfinalhead+" from ";
		String field2 = Util_ServerString.getClassLastName(object);
		String result = head + head2 + field + field2;
		return result;
	}

	/**
	 * 建立表语句
	 * @param object
	 * @return
	 */
	public static String createTableSql(Object object) {
		String head = "create table ";
		String head2 = Util_ServerString.getClassLastName(object);
		String field = Util_ServerString.field2sqlhasdefault(object);
		String result = head + head2 + field;
		return result;
	}
	/**
	 * 防止一条语句过大的优化办法
	 * @param objlist
	 * @return
	 */
	public static List<String> insertValueSqlMulList(List objlist) {
		List<String> resultSql=new ArrayList<String>();
		String head = "insert into ";
		String head2 = Util_ServerString.getClassLastName(objlist.get(0));
		String field = Util_ServerString.field2sql(objlist.get(0));
		String value = "values";
		String finish = ";";
		List<String> valuevalue = Util_ServerString.value2sqlMulList(objlist);
		for (int i = 0; i < valuevalue.size(); i++) {
			String valuevaluestring=valuevalue.get(i);
			if(valuevaluestring.length()>0){
				valuevaluestring=valuevaluestring.substring(0, valuevaluestring.length()-1);
			}
			String result = head + head2 + field + value + valuevaluestring + finish;
			
		}
		
		return resultSql;
	}
	public static void insertValueSqlMulListtest(List objlist) {
		String head = "insert into ";
		String head2 = Util_ServerString.getClassLastName(objlist.get(0));
		String field = Util_ServerString.field2sql(objlist.get(0));
		String value = "values";
		String valuevalue = Util_ServerString.value2sqlMul(objlist);
		String finish = ";";
		String result = head + head2 + field + value + valuevalue + finish;
		Help_DB.create().executebeginTransaction(result);
	}
	/**
	 * 将list数据转化为插入语句 实现批量插入的优化
	 * @param objlist
	 * @return
	 */
	public static String insertValueSqlMul(List objlist) {
		String head = "insert into ";
		String head2 = Util_ServerString.getClassLastName(objlist.get(0));
		String field = Util_ServerString.field2sql(objlist.get(0));
		String value = "values";
		String valuevalue = Util_ServerString.value2sqlMul(objlist);
		String finish = ";";
		String result = head + head2 + field + value + valuevalue + finish;
		return result;
	}
	/**
	 * 单项插入
	 * @param object
	 * @return
	 */
	public static String insertValueSqlSingle(Object object) {
		String head = "insert into ";
		String head2 = Util_ServerString.getClassLastName(object);
		String field = Util_ServerString.field2sql(object);
		String value = "values";
		String valuevalue = Util_ServerString.value2sqlSingle(object);
		String finish = ";";
		String result = head + head2 + field + value + valuevalue + finish;
		return result;
	}
	
	public static void insertValueSqlSingleTransaction(Object object) {
	if(object==null){
		Help_DB.create().executebeginTransaction("");
	}else{
		String head = "insert into ";
		String head2 = Util_ServerString.getClassLastName(object);
		String field = Util_ServerString.field2sql(object);
		String value = "values";
		String valuevalue = Util_ServerString.value2sqlSingle(object);
		String finish = ";";
		String  result = head + head2 + field + value + valuevalue + finish;
		Help_DB.create().executebeginTransaction(result);
	}
	
	
		
	}
	public static void deleteValueSqlSingleTransaction(Object object) {
	if(object==null){
		Help_DB.create().executebeginTransaction("");
	}else{
		//System.out.println("事务删除");
		Base_Entity entity=(Base_Entity) object;
		String head = "delete from ";
		String head2 = Util_ServerString.getClassLastName(object);
		String field = " where id = '"+entity.getId()+"'";
		String finish = ";";
		String  result = head + head2 + field  + finish;
		Help_DB.create().executebeginTransaction(result);
	}
	

		
	}
//	public static String createValueSqlMul(List objlist) {
//		String result = "";
//		String string1 = "";
//		if(objlist!=null&&objlist.size()>0){
//			if (objlist.get(0) instanceof Base_Entity) {
//				for (int i = 0; i < objlist.size(); i++) {
//					Base_Entity be = (Base_Entity) (objlist.get(i));
//					string1=string1+value2sqlSingle(be);
//							
//				}
//				string1 = string1.substring(0, string1.length() - 1);
//				result = "insert into " + Util_String.getClassLastName(objlist.get(0))
//						+ Util_String.field2sql(objlist) + "values" + string1
//						+   ";";
//			}
//		}
//
//		return result;
//	}
	
	
//	public static String createValueSqlSingle(Object object) {
//		String result = "";
//		String string1 = "";
//		String string2 = "";
//		if (object instanceof Base_Entity) {
//			Base_Entity be = (Base_Entity) object;
//			String idstr = Util_String.get16Uuid();
//			if (be.getId() != null && !be.getId().equals("")) {
//				idstr = be.getId();
//			}
//			string1 = "'" + idstr + "'" + "," 
//					+ "'" + Util_String.get32Uuid()+ "'" + ","
//					+ "'" + "server" + "'" + ","
//					+ "'" + new Date().getTime() + "'"+ "," 
//					+ "'" + Util_String.getClassLastName(object) + "'"+ ","
//					+ "'" + "0" + "'" + ","
//					;
//					
//			for (int i = 0; i < be.size(); i++) {
//				string2 = string2 + "'" + getNoString(be.getValue(i)) + "'"
//						+ ",";
//			}
//			string2 = string2.substring(0, string2.length() - 1);
//			result = "insert into " + Util_String.getClassLastName(object)
//					+ Util_String.field2sql(object) + "values" + " (" + string1
//					+ string2 + ") " + ";";
//		}
//		return result;
//	}



	public static String getNoString(String string) {
		string = string.replaceAll("\"", "“");
		return string;
	}
}
