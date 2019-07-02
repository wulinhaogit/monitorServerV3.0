/*
package com.kedong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentProjectServiceImpl implements EquipmentProjectService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    SimpleDateFormat myFmt=new SimpleDateFormat("yyyyMMdd");
    // String time = myFmt.format(new Date());
    String  time = "20190513";

    @Override
    public  List<Map<String,Object>> netConcentTop10() {
        //查询所有节点时间最大时间的 name IP data
       String sql="SELECT B.NAME,B.IP,A.DATA FROM" +
               " (SELECT TOP 10 DATA,NODEID FROM SYSDBA.NET_CONNECT_NUM_"+time+" WHERE TIME =" +
               " (SELECT MAX(TIME) TIME FROM SYSDBA.NET_CONNECT_NUM_"+time+") ORDER BY DATA DESC) A" +
               " LEFT JOIN SYSDBA.SERVER B ON A.NODEID = B.ID";
       //sql  参数集合
      List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
      return list;
    }

    @Override
    public  List<Map<String,Object>> memoryUtilizationTop10() {
        String sql="SELECT B.NAME,B.IP,A.DATA FROM" +
                " (SELECT TOP 10 DATA,NODEID FROM SYSDBA.MEM_PERCENT_"+time+" WHERE TIME =" +
                " (SELECT MAX(TIME) TIME FROM SYSDBA.MEM_PERCENT_"+time+") ORDER BY DATA DESC) A" +
                " LEFT JOIN SYSDBA.SERVER B ON A.NODEID = B.ID";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> cpuLoadFactorTop5() {
        String sql="SELECT B.NAME,B.IP,A.DATA FROM" +
                " (SELECT TOP 5 DATA,NODEID FROM SYSDBA.CPU_PERCENT_"+time+" WHERE TIME =" +
                " (SELECT MAX(TIME) TIME FROM SYSDBA.CPU_PERCENT_"+time+") ORDER BY DATA DESC) A" +
                " LEFT JOIN SYSDBA.SERVER B ON A.NODEID = B.ID";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> diskUsageTop5() {
        String sql="SELECT B.NAME,B.IP,A.DATA FROM" +
                " (SELECT TOP 5 DATA ,NODEID FROM SYSDBA.DISK_TOTAL_PERCENT_"+time+" WHERE TIME =" +
                " (SELECT MAX(TIME) TIME FROM SYSDBA.DISK_TOTAL_PERCENT_"+time+") ORDER BY DATA DESC) A" +
                " LEFT JOIN SYSDBA.SERVER B ON A.NODEID = B.ID";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> netFlowIOTop10() {
        String sql="SELECT B.NAME,B.IP,ROUND(A.DATA,2) DATA FROM" +
                " (SELECT TOP 10 NODEID,TX_KBYTES+RX_KBYTES DATA FROM SYSDBA.NETSTAT_INFO_"+time+" WHERE TIME =" +
                " (SELECT MAX(TIME) TIME FROM SYSDBA.NETSTAT_INFO_"+time+") ORDER BY DATA DESC) A" +
                " LEFT JOIN SYSDBA.SERVER B ON A.NODEID = B.ID";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public Map<String, Object> importEquipmentDetails(Long nodeid) {
        //参数容器
         List<Long> params = new ArrayList<Long>();
        params.add(nodeid);
        String sql="SELECT * FROM SYSDBA.SERVER WHERE ID=?";
        //sql  参数集合
        Map<String,Object> map = jdbcTemplate.queryForMap(sql,params.toArray());
        return map;
    }

    @Override
    public List<Map<String, Object>> memoryUsageCurveOfWeek(Long nodeid) {
        List<Long> params = new ArrayList<Long>();
        Integer date =Integer.parseInt(time);
        //查询一周每天的最大date
        String sql="SELECT * FROM (SELECT TOP 1 DATA,TIME FROM SYSDBA.MEM_PERCENT_"+date+
                " WHERE NODEID =? ORDER BY DATA DESC)";
        params.add(nodeid);
        for (int i=1;i<7;i++){
            //int num=date-i;
            //测试
            int num=date;

            sql += " UNION ALL SELECT * FROM (SELECT TOP 1 DATA,TIME FROM SYSDBA.MEM_PERCENT_"+num+
                    " WHERE NODEID =? ORDER BY DATA DESC)";
            params.add(nodeid);
        }
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> memoryUsageCurveOfDay(Long nodeid, String date) {
        List<Long> params = new ArrayList<>();
        //查询每天的每个时间的最大data
        String sql="SELECT MAX(DATA) DATA,TIME FROM SYSDBA.MEM_PERCENT_"+date+
                " WHERE NODEID =?"+
                " GROUP BY TIME";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> cpuUsageCurveOfWeek(Long nodeid) {
        List<Long> params = new ArrayList<Long>();
        Integer date =Integer.parseInt(time);
        //查询一周每天的最大date
        String sql="SELECT * FROM (SELECT TOP 1 DATA,TIME FROM SYSDBA.CPU_PERCENT_"+time+
                " WHERE NODEID =? ORDER BY DATA DESC)";
        params.add(nodeid);
        for (int i=1;i<7;i++){
            //int num=date-i;
            //测试
            int num=date;

            sql += " UNION ALL SELECT * FROM (SELECT TOP 1 DATA,TIME FROM SYSDBA.CPU_PERCENT_"+num+
                    " WHERE NODEID =? ORDER BY DATA DESC)";
            params.add(nodeid);
        }
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> cpuUsageCurveOfDay(Long nodeid, String date) {
        List<Long> params = new ArrayList<>();
        //查询每天的每个时间的最大data
        String sql="SELECT MAX(DATA) DATA,TIME FROM SYSDBA.CPU_PERCENT_"+date+
                " WHERE NODEID =?"+
                " GROUP BY TIME";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> diskUsageCurveOfWeek(Long nodeid) {
        List<Long> params = new ArrayList<Long>();
        Integer date =Integer.parseInt(time);
        //查询一周每天的最大date
        String sql="SELECT * FROM (SELECT TOP 1 DATA,TIME FROM SYSDBA.DISK_TOTAL_PERCENT_"+time+
                " WHERE NODEID =? ORDER BY DATA DESC)";
        params.add(nodeid);
        for (int i=1;i<7;i++){
            //int num=date-i;
            //测试
            int num=date;

            sql += " UNION ALL SELECT * FROM (SELECT TOP 1 DATA,TIME FROM SYSDBA.DISK_TOTAL_PERCENT_"+ num +
                    " WHERE NODEID =? ORDER BY DATA DESC)";
            params.add(nodeid);
        }
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> diskUsageCurveOfDay(Long nodeid, String date) {
        List<Long> params = new ArrayList<>();
        //查询每天的每个时间的最大data
        String sql="SELECT MAX(DATA) DATA,TIME FROM SYSDBA.DISK_TOTAL_PERCENT_"+date+
                " WHERE NODEID =?"+
                " GROUP BY TIME";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }


}
*/
