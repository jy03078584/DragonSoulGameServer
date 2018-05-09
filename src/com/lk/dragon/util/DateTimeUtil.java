 /** 
 *
 * @Title: DateTimeUtil.java 
 * @Package com.lk.dragon.util 
 * @Description: 日期时间工具类
 * @author XiangMZh   
 * @date 2014-9-25 上午10:07:37 
 * @version V1.0   
 */
package com.lk.dragon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**  
 * @Description:日期时间工具类
 */
public class DateTimeUtil {
	
	/** 时间格式 **/
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	
	public static Date praseStringToDate(String dateStr) throws Exception{
		return sdf.parse(dateStr);
	}
	
	/**
	 * 得到结束时间
	 * @param startTime 任务开始时间
	 * @param useSeconds 任务持续时间(秒)
	 * @return
	 */
	public static String getEndTime(Date startTime,long useSeconds){
		Date endTime = new Date(startTime.getTime()+useSeconds * 1000);
		
		return sdf.format(endTime);
	}
	
	/**
	 * 当前时间
	 * @return
	 */
	public static String getNowTimeByFormat(){
		return sdf.format(new Date());
	}
	/**
	 * 判断日期相差天数
	 * @param leftTime 
	 * @param rightTime
	 * @return flag: <0则rightTime在leftTime后； =0则在同一天；>0则 rightTime在leftTime前
	 * @throws ParseException 
	 */
	public static int compareDay(String leftTime,String rightTime) throws ParseException{
		int days = 0;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		days = (int) ((sf.parse(leftTime).getTime() - sf.parse(rightTime).getTime())/(1000*3600*24));
		return days;
	}
	
	/**
	 * 取得两时间差
	 * @param leftTime
	 * @param rightTime
	 * @param unit 1：秒  2：分  3：时  4：天
	 * @return
	 */
	public static long getDiffer(Date leftTime,Date rightTime,int unit){
		long deff = leftTime.getTime() - rightTime.getTime();
		if(unit == 1)//秒
			return deff/1000;
		if(unit == 2)//分
			return deff/(1000 * 60);
		if(unit == 3)//h
			return deff/(1000 * 60 * 60);
		if(unit ==4)//day
			return deff/(1000 * 60 * 60 * 24);
		return 0;
	}
	
	/**
	 * 判断指定时间与当前时间相差天数
	 * @param compareTime
	 * @return
	 * @throws ParseException 
	 */
	public static int compareDayWithNow(String compareTime) throws ParseException{
		int days = 0;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		days = (int) ((new Date().getTime() - sf.parse(compareTime).getTime())/(1000*3600*24));
		System.out.println("diffDay:"+days);
		return days;
	}
	
}
