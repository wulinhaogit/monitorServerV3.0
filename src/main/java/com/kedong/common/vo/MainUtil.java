package com.kedong.common.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainUtil {

	public static ArrayList<String> getEveryDate(int intervals) {
		ArrayList<String> pastDaysList = new ArrayList<>();
		// ArrayList<String> fetureDaysList = new ArrayList<>();
		for (int i = 0; i < intervals; i++) {
			pastDaysList.add(getPastDate(i));
			// fetureDaysList.add(getFetureDate(i));
		}

		return pastDaysList;
	}

	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		today.setTime(today.getTime()-5*60*1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String result = format.format(today);
		return result;
	}
	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getPastDateT(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		today.setTime(today.getTime()-5*60*1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取未来 第 past 天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		today.setTime(today.getTime()-5*60*1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取现在是本月的第几天
	 * 
	 * @return
	 */
	public static int getDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		int day = calendar.get(Calendar.DAY_OF_MONTH);// 本月第几天
		return day;
	}

	/**
	 * 获取现在数据库同步日期
	 * 
	 * @return
	 */
	public static String getNowDateF() {
		Date nowDate = new Date();
		nowDate.setTime(nowDate.getTime()-5*60*1000);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMdd");
		myFmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String time = myFmt.format(nowDate);
		
		return time;
	}

	/**
	 * 获取现在数据库同步日期 时间 格式
	 * 
	 * @return
	 */
	public static String getNowDateT() {
		Date nowDate = new Date();
		nowDate.setTime(nowDate.getTime()-5*60*1000);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		myFmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String time = myFmt.format(nowDate);
		return time;
	}

	/**
	 * 获取现在数据库同步日期时分秒（推迟5分钟）
	 * 
	 * @return
	 */
	public static String getNowDate() {
		Date nowDate = new Date();
		nowDate.setTime(nowDate.getTime()-5*60*1000);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		myFmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String time = myFmt.format(nowDate);
		return time;
	}
	/**
	 * 获取现在同步日期时分秒 制定时间一小时之间的日期
	 *
	 * @return
	 */
	public static String getBeforHourDate(String maxTimeStr) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date maxTime= null;
		try {
			maxTime = myFmt.parse(maxTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		maxTime.setHours(maxTime.getHours()-1);
		String beforTime= myFmt.format(maxTime);
		return beforTime;
	}
	
	/**
	 * 获取数据库最大的当前时间 前置5分钟  （等待数据更新完毕）
	 * @return
	 */
	public static String getMaxTimeMinute(String maxTimeStr) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate= null;
		try {
			nowDate = myFmt.parse(maxTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		nowDate.setTime(nowDate.getTime()-5*60*1000);
		String beforTime= myFmt.format(nowDate);
		return beforTime;
	}

	public static List<Map<String, Object>> getResultList(List<Map<String, Object>> list, Map<String, Object> map) {
		List<Map<String, Object>> resultList = new LinkedList<>();
		if (list.size()>=2) {
			resultList.add(list.get(0));
			resultList.add(list.get(1));
			resultList.add(map);
		} else if (list.size()>=1) {
			resultList.add(list.get(0));
			resultList.add(map);
		} else {
			// list.add(list.get(0));
			resultList.add(map);
		}
		return resultList;
	}

}
