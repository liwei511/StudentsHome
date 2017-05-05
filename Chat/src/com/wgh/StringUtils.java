package com.wgh;

public class StringUtils {
	public String toGBK(String strvalue) {
		try {
			if (strvalue == null) {			//������strvalueΪnullʱ
				return "";					//���ؿյ��ַ���
			} else {
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "GBK");//���ַ���ת��ΪGBK����
				return strvalue;			//����ת������������strvalue
			}
		} catch (Exception e) {
			return "";
		}
	}

	public String toUTF8(String strvalue) {
		try {
			if (strvalue == null) {			//������strvalueΪnullʱ
				return "";					//���ؿյ��ַ���
			} else {
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "UTF-8");//���ַ���ת��ΪUTF8����
				return strvalue;		//����ת������������strvalue
			}
		} catch (Exception e) {
			return "";
		}
	}
}
