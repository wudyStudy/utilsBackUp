package com.test.utilsBackUp.other;

public class StringUtil {
	/**
	 * 判断字符串是空，包括null、""
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return (null == str || str.length() < 1);
	}

	/**
	 * 判断字符串不是空，包括null、""
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}


}
