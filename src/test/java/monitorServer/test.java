package monitorServer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class test {
public static void main(String[] args) {
	List<Integer> list1= new ArrayList<Integer>();
    list1.add(1);
    list1.add(2);
    list1.add(3);
    list1.add(4);
	List<Integer> list2= new ArrayList<Integer>();
	  list2.add(5);
	    list2.add(6);
	    list2.add(7);
	    list2.add(8);
	List<Integer> list3= new ArrayList<Integer>();
	list3.add(5);
    list3.add(6);
    list3.add(7);
    list3.add(8);
	List<Integer> list= new ArrayList<Integer>();
	list.addAll(list1);
	list.addAll(list2);
	list.addAll(list3);
	System.out.println(list);
	
}


/**
 * 获取现在数据库同步日期时分秒
 * 
 * @return
 */
public static String getNowDate() {
	Date nowDate = new Date();
	SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	myFmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	String time = myFmt.format(nowDate);
	return time;
}
/**
 * 获取现在数据库同步日期时分秒（推迟5分钟）
 * 
 * @return
 */
public static String getNowDateBefor() {
	Date nowDate = new Date();
	nowDate.setTime(nowDate.getTime()-5*60*1000);
	SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	myFmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	String time = myFmt.format(nowDate);
	return time;
}
}
