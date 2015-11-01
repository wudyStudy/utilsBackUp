package com.test.utilsBackUp.pinying;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/** 通过输入汉语拼音可以获取到所有拼音的首字符然后拼接返回的字符串
 * 支持单独汉语拼音，单独英文字符，已经汉语拼音加上英文字符还可以使用其他复杂的字符哟
 *  
 *  
 */
public class PinyinUtil {
	//将中文字符转为拼音字符
	public static String getPinYin(char ch) throws Exception{
		HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
		pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		if(Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")){
			return PinyinHelper.toHanyuPinyinStringArray(ch, pyFormat)[0].substring(0, 1);
		}else{
			return Character.toString(ch);
		}
	}
	
	public static String getEachFirstChar(String str) throws Exception{
		char[] strs = str.toCharArray();
		StringBuffer searchCode = new StringBuffer();;
		for(int i=0;i<strs.length;i++){
			searchCode.append(getPinYin(strs[i]));
		}	
		return searchCode.toString();
	}
}
