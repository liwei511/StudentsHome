package com.wgh;

public class StringUtils {
	public String toGBK(String strvalue) {
		try {
			if (strvalue == null) {			//当变量strvalue为null时
				return "";					//返回空的字符串
			} else {
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "GBK");//将字符串转换为GBK编码
				return strvalue;			//返回转换后的输入变量strvalue
			}
		} catch (Exception e) {
			return "";
		}
	}

	public String toUTF8(String strvalue) {
		try {
			if (strvalue == null) {			//当变量strvalue为null时
				return "";					//返回空的字符串
			} else {
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "UTF-8");//将字符串转换为UTF8编码
				return strvalue;		//返回转换后的输入变量strvalue
			}
		} catch (Exception e) {
			return "";
		}
	}
}
