package com.test.utilsBackUp.other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	public static final DateFormat FORMAT_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");
	public static final Map<String, DateFormat> DATE_FORMAT_MAP = new HashMap<String, DateFormat>();
	static {
		DATE_FORMAT_MAP.put("yyyy-MM-dd HH:mm:ss", FORMAT_YMDHMS);
		DATE_FORMAT_MAP.put("yyyy-MM-dd", FORMAT_YMD);
	}
	
	/**
	 * 时间转换为字符串yyyy-MM-dd HH:mm:ss
	 * @param date 要转换的时间
	 * @return 转换后的字符串
	 */
	public static String formatYMDHMS(Date date) {
		if(date == null){
			return null;
		}
		return FORMAT_YMDHMS.format(date);
	}

	/**
	 * 时间转换为字符串yyyy-MM-dd
	 * @param date 要转换的时间
	 * @return 转换后的字符串
	 */
	public static String formatYMD(Date date) {
		if(date == null){
			return null;
		}
		return FORMAT_YMD.format(date);
	}
	
	/**
	 * 将日期格式化成指定时间
	 * @param date 要格式化的时间
	 * @param format 例如yyyy-MM-dd
	 * @return	转换后的字符串
	 */
	public static final String format(Date date, String format) {
		if (date == null)
			return null;
		DateFormat dateFormat = DATE_FORMAT_MAP.get(format);
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(format);
			DATE_FORMAT_MAP.put(format, dateFormat);
		}
		return dateFormat.format(date);
	}
	/**
	 * 在当前时间加减指定天
	 * @param nowDate 当前时间
	 * @param day 加减天数
	 * @return
	 */
	public static final Date getAfterDay(Date nowDate, int day){
		if (nowDate == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		Date date = DateUtil.getAfterDay(new Date(), 5);
		System.out.println(DateUtil.formatYMDHMS(date));
				
	}
}
