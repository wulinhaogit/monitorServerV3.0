package com.kedong.service;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface PlatformProjectService {


    List<Map<String, Object>> midhsAccessNumCurveToday();

    List<Map<String, Object>> midhsAccessNumCurveYesterday();
    //List<Map<String, Object>> midhsAccessNumCurveWeek();

  //  List<Map<String, Object>> midhsAccessNumCurveMonth();
    List<Map<String, Object>> procStatusMonitor();

   // List<Map<String, Object>> procStateCPUnowTop3();

   // List<Map<String, Object>> procStateCPUDay(LinkedList<String> nList, LinkedList<String> sList);

   // List<Map<String, Object>> procStateCPUWeek(LinkedList<String> nList, LinkedList<String> sList);

   // List<Map<String, Object>> procSateMemoryFotTop3();

   // List<Map<String, Object>> procSateMemoryFotDay(LinkedList<String> nList, LinkedList<String> sList);

   // List<Map<String, Object>> procSateMemoryFotWeek(LinkedList<String> list);

   // List<Map<String, Object>> numOfOrocessStateThreadsTop3();

   // List<Map<String, Object>> numOfOrocessStateThreadsDay( LinkedList<String> nList, LinkedList<String> sList);

   // List<Map<String, Object>> numOfOrocessStateThreadsWeek(LinkedList<String> list);

   // List<Map<String, Object>> processStatusDiskIOReadIncrementTop3();

   // List<Map<String, Object>> processStatusDiskIOReadIncrementDay(LinkedList<String> nList, LinkedList<String> sList);

   // List<Map<String, Object>> processStatusDiskIOWriteIncrementWeek(LinkedList<String> nList, LinkedList<String> sList);
   // List<Map<String, Object>> processStatusDiskIOReadIncrementWeek(LinkedList<String> nList, LinkedList<String> sList);

    List<Map<String, Object>> dataCPUUtilCurveWeek(String nodeid);

    List<Map<String, Object>> dataCPUUtilCurveToday(String nodeid);
    List<Map<String, Object>> dataCPUUtilCurveYesterday(String nodeid);

    List<Map<String, Object>> dataMemoryUtilCurveWeek(String nodeid);

    List<Map<String, Object>> dataMemoryUtilCurveToday(String nodeid);
    List<Map<String, Object>> dataMemoryUtilCurveYesterDay(String nodeid);

    List<Map<String, Object>> dataIOCurveWeek(String nodeid);

    List<Map<String, Object>> dataIOCurveToday(String nodeid);
    List<Map<String, Object>> dataIOCurveYesterDay(String nodeid);

    List<Map<String, Object>> dataDiskCurveWeek(String nodeid);

    List<Map<String, Object>> dataDiskReadCurveToday(String nodeid);
    List<Map<String, Object>> dataDiskReadCurveYesterday(String nodeid);
    List<Map<String, Object>> dataDiskWriteCurveToday(String nodeid);
    List<Map<String, Object>> dataDiskWriteCurveYesterday(String nodeid);

    List<Map<String, Object>> diskPartSpaceList(String nodeid);

    List<Map<String, Object>> databaseDetails(String nodeid);

    List<Map<String, Object>> DataRealtimeConnectNumber(String nodeid);

    List<Map<String, Object>> oneHourAccessOf(String nodeid);

    List<Map<String, Object>> dataSessionNumberTodayCurve(String nodeid);
    List<Map<String, Object>> dataSessionNumberYesterdayCurve(String nodeid);

    List<Map<String, Object>> dataMemoryChangeTodayCurve(String nodeid);
    List<Map<String, Object>> dataMemoryChangeYesterdayCurve(String nodeid);

    List<Map<String, Object>> databaseNetworkIOToday(String nodeid);
    List<Map<String, Object>> databaseNetworkIOYesterday(String nodeid);
    List<Map<String, Object>> databaseNetworkIOYesterdayCount(String nodeid);

   // List<Map<String, Object>> dataTableSpaceUsageCurve(String nodeid);

    //List<Map<String, Object>> alarmStatistics();
   List<Map<String, Object>> getUserName(String nodeid);

    List<Map<String, Object>> appCountList1();
    List<Map<String, Object>> appCountList2();
    List<Map<String, Object>> appCountList3();

    List<Map<String, Object>> appPrimaryAndStaSwitchSCA();

    List<Map<String, Object>> appPrimaryAndStaSwitchData();

    List<Map<String, Object>> appPrimaryAndStaSwitchFes();
    List<Map<String, Object>> appPrimaryAndStaSwitchPas();
    List<Map<String, Object>> appPrimaryAndStaSwitchPublic();
    List<Map<String, Object>> appPrimaryAndStaSwitchWams();

   // List<Map<String, Object>> procStateCPUAvgHour( LinkedList<String> nList, LinkedList<String> sList) throws ParseException;

   // List<Map<String, Object>> procSateMemoryAvgHour(LinkedList<String> nList, LinkedList<String> sList) throws ParseException;

    List<Map<String, Object>> midhsDelayCurveToday();
    List<Map<String, Object>> midhsDelayCurveYesterday();

	List<Map<String, Object>> getNodeId();

	List<Map<String, Object>> appMonitorList();

	List<Map<String, Object>> dataDiskWriteCurveWeek(String nodeid);

	List<Map<String, Object>> getAppCount(String id);
	List<Map<String, Object>> getServerCount(String id);


	

   // List<Map<String, Object>> processStatusDiskIOWriteIncrementDay(LinkedList<String> nList, LinkedList<String> sList);

   // List<Map<String, Object>> processStatusDiskIOWriteIncrementTop3();

	//List<Map<String, Object>> numOfOrocessStateThreadAvgHour(LinkedList<String> nList);

    // List<Map<String, Object>> midhsDelayCurveWeek();

   // List<Map<String, Object>> midhsDelayCurveMonth();

    // List<Map<String, Object>> midhsDelayCurveDay();
}
