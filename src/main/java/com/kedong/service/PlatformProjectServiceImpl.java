package com.kedong.service;

import com.kedong.common.vo.MainUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlatformProjectServiceImpl implements PlatformProjectService{
	public static final Logger logger= LoggerFactory.getLogger(PlatformProjectServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //String  time = "20190620";



    @Override
    public List<Map<String, Object>> midhsAccessNumCurveToday() {
        String time= MainUtil.getNowDateF();
        System.out.println("midhs访问次数---------------------------曲线日时分秒：--"+MainUtil.getNowDate());
    	System.out.println("midhs访问次数曲线日：--"+time);
        String sql="SELECT SUM(ACCESSNUM) ACCESSNUM,TIME FROM SERVICE_INFO_"+time+
                " WHERE NAME = 'midhs' AND ADDR IN (SELECT ADDR FROM SERVICE_INFO_"+time+
                " WHERE NAME = 'midhs' GROUP BY ADDR)" +
                " GROUP BY TIME ORDER BY TIME";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    @Override
    public List<Map<String, Object>> midhsAccessNumCurveYesterday() {
    	String time= MainUtil.getPastDate(1);//获取一天或  前一天
    	System.out.println("midhs访问次数---------------------------曲线日时分秒：--"+MainUtil.getNowDate());
    	System.out.println("midhs访问次数曲线日：--"+time);
    	String sql="SELECT SUM(ACCESSNUM) ACCESSNUM,TIME FROM SERVICE_INFO_"+time+
    			" WHERE NAME = 'midhs' AND ADDR IN (SELECT ADDR FROM SERVICE_INFO_"+time+
    			" WHERE NAME = 'midhs' GROUP BY ADDR)" +
    			" GROUP BY TIME ORDER BY TIME";
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
    	return list;
    }

 /*   @Override
    public List<Map<String, Object>> midhsAccessNumCurveWeek() {
        String time= MainUtil.getNowDateF();
    	System.out.println("midhs访问次数周曲线：--"+time);

        String sql="SELECT SUM(ACCESSNUM) ACCESSNUM,'"+time+"' AS TIME FROM SERVICE_INFO_"+time+
                " WHERE NAME = 'midhs' AND ADDR in (SELECT ADDR FROM SERVICE_INFO_"+time+
                " WHERE NAME = 'midhs' GROUP BY ADDR)";
       ArrayList<String> dateList= MainUtil.getEveryDate(7);
        for (int i=1;i<7;i++){
           String num=dateList.get(i);
           System.out.println("midhs访问次数周曲线 num：--"+num);
            //测试
            //String num=time;
            sql += " UNION ALL SELECT SUM(ACCESSNUM) ACCESSNUM,'"+num+"' AS TIME FROM SERVICE_INFO_"+num+
                    " WHERE NAME = 'midhs' AND ADDR in (SELECT ADDR FROM SERVICE_INFO_"+num+
                    " WHERE NAME = 'midhs' GROUP BY ADDR)";
        }
        //sql
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        return list;
    }

    @Override
    public List<Map<String, Object>> midhsAccessNumCurveMonth() {
        String time= MainUtil.getNowDateF();
        System.out.println("midhs访问次数月曲线 ：--"+time);
        String sql="SELECT SUM(ACCESSNUM) ACCESSNUM,'"+time+"' AS TIME FROM SERVICE_INFO_"+time+
                " WHERE NAME = 'midhs' AND ADDR in (SELECT ADDR FROM SERVICE_INFO_"+time+
                " WHERE NAME = 'midhs' GROUP BY ADDR)";
        ArrayList<String> dateList= MainUtil.getEveryDate(30);
        for (int i=1;i<30;i++){
           String num=dateList.get(i);
           System.out.println("midhs访问次数月曲线 num：--"+num);
           // String num=time;
            sql += " UNION ALL SELECT SUM(ACCESSNUM) ACCESSNUM,'"+num+"' AS TIME FROM SERVICE_INFO_"+num+
                    " WHERE NAME = 'midhs' AND ADDR in (SELECT ADDR FROM SERVICE_INFO_"+num+
                    " WHERE NAME = 'midhs' GROUP BY ADDR)";
        }
        //sql
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        return list;
    }*/

    @Override
    public List<Map<String, Object>> midhsDelayCurveToday() {
        String time= MainUtil.getNowDateF();
        String sql="SELECT COUNT(*) ACCESSNUM,TIME FROM SYSDBA.MIDHS_STATUS_"+time+" GROUP BY TIME";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

   @Override
    public List<Map<String, Object>> midhsDelayCurveYesterday() {
	   String time= MainUtil.getPastDate(1);
       String sql="SELECT COUNT(*) ACCESSNUM,TIME FROM SYSDBA.MIDHS_STATUS_"+time+" GROUP BY TIME";
       //sql  参数集合
       List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
       return list;
    }
  /* @Override
   public List<Map<String, Object>> midhsDelayCurveTodayVag() {
	   String time= MainUtil.getNowDateF();
	   String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.MIDHS_STATUS_"+time;
       String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
       maxTime=MainUtil.getMaxTimeMinute(maxTime);
       String beforTime= MainUtil.getBeforHourDate(maxTime);
	   String sql="SELECT COUNT(*) FROM (SELECT COUNT(*) ACCESSNUM,TIME FROM SYSDBA.MIDHS_STATUS_"+time+" GROUP BY TIME)"
	   		+ " WHERE TIME TIME BETWEEN '"+beforTime+"' AND '"+maxTime+"'";
	   //sql  参数集合
	   List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
	   return list;
   }*/
   

   /*  @Override
    public List<Map<String, Object>> midhsDelayCurveMonth() {
        return null;
    }*/

    @Override
    public List<Map<String, Object>> appCountList1() {
        String time= MainUtil.getNowDateF();
        System.out.println("平台应用统计列表---"+time);
        String sqlTime="SELECT MAX(TIME)FROM SYSDBA.SHOWSERVICE_INFO_"+time+ " WHERE NODEID LIKE '1%'" ;
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String sql="SELECT * FROM ( SELECT DISTINCT A.APP_NAME,A.NUM SERVER_NUM,B.NODENAME,B.STATUS,B.TIME  FROM ("
        		+ " (SELECT APP_NAME,COUNT(*) NUM FROM (SELECT NODENAME,CONTEXT,APP_NAME FROM SYSDBA.SHOWSERVICE_INFO_"+time
        		+ " WHERE TIME='"+maxTime+"' AND NODEID LIKE '1%' GROUP BY CONTEXT,NODENAME,APP_NAME )"
        		+ " GROUP BY APP_NAME) ) A LEFT JOIN ( SELECT * FROM SYSDBA.SHOWSERVICE_INFO_"+time
        		+ " WHERE TIME='"+maxTime+"' AND NODEID LIKE '1%' "
        		+ " AND STATUS='主机') B ON A.APP_NAME=B.APP_NAME ) WHERE NODENAME IS NOT NULL ";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    @Override
    public List<Map<String, Object>> appCountList2() {
    	String time= MainUtil.getNowDateF();
    	System.out.println("平台应用统计列表---"+time);
    	String sqlTime="SELECT MAX(TIME)FROM SYSDBA.SHOWSERVICE_INFO_"+time+ " WHERE NODEID LIKE '2%'";
    	String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
    	maxTime=MainUtil.getMaxTimeMinute(maxTime);
    	String sql="SELECT * FROM ( SELECT DISTINCT A.APP_NAME,A.NUM SERVER_NUM,B.NODENAME,B.STATUS,B.TIME  FROM ("
    			+ " (SELECT APP_NAME,COUNT(*) NUM FROM (SELECT NODENAME,CONTEXT,APP_NAME FROM SYSDBA.SHOWSERVICE_INFO_"+time
    			+ " WHERE TIME='"+maxTime+"' AND NODEID LIKE '2%' GROUP BY CONTEXT,NODENAME,APP_NAME )"
    			+ " GROUP BY APP_NAME) ) A LEFT JOIN ( SELECT * FROM SYSDBA.SHOWSERVICE_INFO_"+time
    			+ " WHERE TIME='"+maxTime+"' AND NODEID LIKE '2%' "
    			+ " AND STATUS='主机') B ON A.APP_NAME=B.APP_NAME ) WHERE NODENAME IS NOT NULL ";
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
    	return list;
    }
    @Override
    public List<Map<String, Object>> appCountList3() {
    	String time= MainUtil.getNowDateF();
    	System.out.println("平台应用统计列表---"+time);
    	String sqlTime="SELECT MAX(TIME)FROM SYSDBA.SHOWSERVICE_INFO_"+time+ " WHERE NODEID LIKE '3%'";
    	String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
    	maxTime=MainUtil.getMaxTimeMinute(maxTime);
    	String sql="SELECT * FROM ( SELECT DISTINCT A.APP_NAME,A.NUM SERVER_NUM,B.NODENAME,B.STATUS,B.TIME  FROM ("
    			+ " (SELECT APP_NAME,COUNT(*) NUM FROM (SELECT NODENAME,CONTEXT,APP_NAME FROM SYSDBA.SHOWSERVICE_INFO_"+time
    			+ " WHERE TIME='"+maxTime+"' AND NODEID LIKE '3%' GROUP BY CONTEXT,NODENAME,APP_NAME )"
    			+ " GROUP BY APP_NAME) ) A LEFT JOIN ( SELECT * FROM SYSDBA.SHOWSERVICE_INFO_"+time
    			+ " WHERE TIME='"+maxTime+"' AND NODEID LIKE '3%' "
    			+ " AND STATUS='主机') B ON A.APP_NAME=B.APP_NAME ) WHERE NODENAME IS NOT NULL ";
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
    	return list;
    }
    
    @Override
	public List<Map<String, Object>> appMonitorList() {
    	 String time= MainUtil.getNowDateF();
    	  System.out.println("应用监视列表---"+time);
          String sqlTime="SELECT MAX(TIME)FROM SYSDBA.SHOWSERVICE_INFO_"+time;
          String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
          maxTime=MainUtil.getMaxTimeMinute(maxTime);
          String sql="SELECT D.NAME AS AC_NAME,C.*,D.\"LEVEL\" FROM (SELECT B.NAME CON_TEXT,A.APP_NAME,A.NODENAME,A.STATUS,A.TIME,A.REFRESH_STATUS FROM "
          		+ " (SELECT APP_NAME,NODENAME,STATUS,CONTEXT,TIME,REFRESH_STATUS FROM SYSDBA.SHOWSERVICE_INFO_"+time+" WHERE TIME ='"+maxTime+"') A "
          		+ " LEFT JOIN CONTEXT_INFO B ON A.CONTEXT=B.VALUE ) C LEFT JOIN APP_INFO D ON C.APP_NAME=D.ENG_NAME WHERE D.DESCRIPTION"
          		+ " IS NOT NULL AND D.\"LEVEL\" IS NOT NULL";
          List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
          String app_name = "";
          for (int i = 0; i < list.size(); i++) {//0大应用  1小应用
        	  Map<String, Object> map = list.get(i);
        	  if(i>=1){
        		  if(app_name.equals(map.get("APP_NAME"))){
        			  map.put("KIND_APP_NAME", 0);
        		  }else if((map.get("APP_NAME")+"").indexOf(app_name+"_")>=0){
        			  map.put("KIND_APP_NAME", app_name);
        		  }else{
        			  app_name = map.get("APP_NAME")+"";
                	  map.put("KIND_APP_NAME", 0);
        		  }
        	  }else {
        		  app_name = map.get("APP_NAME")+"";
            	  map.put("KIND_APP_NAME", 0);
			}
        	  
		}
          
         Collections.sort(list,new Comparator<Map<String, Object>>(){

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int l1=Integer.valueOf(o1.get("LEVEL")+"");
				int l2=Integer.valueOf(o2.get("LEVEL")+"");
				if (l1>l2) {
					return 1;
					
				}
				return -1;
			}
        	 
         });
          
          return list;
	}


    @Override
    public List<Map<String, Object>> appPrimaryAndStaSwitchSCA() {
        String time= MainUtil.getNowDateF();
        System.out.println("主备机-scada--"+time);
        List<Map<String, Object>> resultList = new LinkedList<>();
        //  以scada应用为例：
        //  1）获取当天scada应用主机情况：
        String sql1="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+
                " WHERE APP_NAME = 'scada' AND STATUS = '主机' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> map1=jdbcTemplate.queryForList(sql1);
        if(map1.size()==0)
            return resultList;
       String nodeName1=(String)map1.get(0).get("NODENAME");
        //放入集合
        resultList.add(map1.get(0));
        //  返回结果：
        // NODENAME:jbs2-gw01
        //  Time:2019-05-27 16:27:50
        // 2）	查新当天public 有没有第一次切机
        String sql2=" SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'scada'" +
                " AND STATUS = '主机' AND NODENAME != '"+nodeName1+"' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> ListMap2=jdbcTemplate.queryForList(sql2);

        if (ListMap2.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
            String nodeName2=(String) ListMap2.get(0).get("NODENAME");
            String time2=(String) ListMap2.get(0).get("TIME");
            resultList.add(ListMap2.get(0));

            // 3）	查新当天public 有没有第二次切机
            String sql3="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'scada' AND STATUS = '主机'"+
                    " AND NODENAME != '"+nodeName2+"' AND NODEID LIKE '1%' AND TIME BETWEEN '"+MainUtil.getNowDateT()+" 00:00:00' AND '"+time2+"' ORDER BY TIME DESC";
            List<Map<String,Object>> ListMap3=jdbcTemplate.queryForList(sql3);
            if (ListMap3.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
                resultList.add(ListMap3.get(0));
                //三次都有数据 返回结果集
                return resultList;
            }else{
                //执行下一天

                selectData("scada",nodeName2,resultList,0, MainUtil.getEveryDate(30));
            }
        }else{
            selectData("scada",nodeName1,resultList,0, MainUtil.getEveryDate(30));
        }
        return resultList;
    }

    @Override//data_srv
    public List<Map<String, Object>> appPrimaryAndStaSwitchData() {
        String time= MainUtil.getNowDateF();
        System.out.println("主备机-data_srv--"+time);
        List<Map<String, Object>> resultList = new LinkedList<>();
        //  以scada应用为例：
        //  1）获取当天scada应用主机情况：
        String sql1="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+
                " WHERE APP_NAME = 'data_srv' AND STATUS = '主机' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> map1=jdbcTemplate.queryForList(sql1);
        if(map1.size()==0)
            return resultList;
        String nodeName1=(String)map1.get(0).get("NODENAME");
        //放入集合
        resultList.add(map1.get(0));
        //  返回结果：
        // NODENAME:jbs2-gw01
        //  Time:2019-05-27 16:27:50
        // 2）	查新当天public 有没有第一次切机
        String sql2=" SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'data_srv'" +
                " AND STATUS = '主机' AND NODENAME != '"+nodeName1+"' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> ListMap2=jdbcTemplate.queryForList(sql2);

        if (ListMap2.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
            String nodeName2=(String) ListMap2.get(0).get("NODENAME");
            String time2=(String) ListMap2.get(0).get("TIME");
            resultList.add(ListMap2.get(0));

            // 3）	查新当天public 有没有第二次切机
            String sql3="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'data_srv' AND STATUS = '主机'"+
                    " AND NODENAME != '"+nodeName2+"' AND NODEID LIKE '1%' AND TIME BETWEEN '"+MainUtil.getNowDateT()+" 00:00:00' AND '"+time2+"' ORDER BY TIME DESC";
            List<Map<String,Object>> ListMap3=jdbcTemplate.queryForList(sql3);
            if (ListMap3.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
                resultList.add(ListMap3.get(0));
                //三次都有数据 返回结果集
                return resultList;
            }else{
                //执行下一天
                selectData("data_srv",nodeName2,resultList,0, MainUtil.getEveryDate(30));
            }
        }else{
            selectData("data_srv",nodeName1,resultList,0, MainUtil.getEveryDate(30));
        }
        return resultList;
    }

    @Override//fes
    public List<Map<String, Object>> appPrimaryAndStaSwitchFes() {
        String time= MainUtil.getNowDateF();
        System.out.println("主备机-fes--"+time);
        List<Map<String, Object>> resultList = new LinkedList<>();
        //  以scada应用为例：
        //  1）获取当天scada应用主机情况：
        String sql1="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+
                " WHERE APP_NAME = 'fes' AND STATUS = '主机' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> map1=jdbcTemplate.queryForList(sql1);
        if(map1.size()==0)
            return resultList;
        String nodeName1=(String)map1.get(0).get("NODENAME");
        //放入集合
        resultList.add(map1.get(0));
        //  返回结果：
        // NODENAME:jbs2-gw01
        //  Time:2019-05-27 16:27:50
        // 2）	查新当天public 有没有第一次切机
        String sql2=" SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'fes'" +
                " AND STATUS = '主机' AND NODENAME != '"+nodeName1+"' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> ListMap2=jdbcTemplate.queryForList(sql2);
        if (ListMap2.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
            String nodeName2=(String) ListMap2.get(0).get("NODENAME");
            String time2=(String) ListMap2.get(0).get("TIME");
            resultList.add(ListMap2.get(0));

            // 3）	查新当天public 有没有第二次切机
            String sql3="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'fes' AND STATUS = '主机'"+
                    " AND NODENAME != '"+nodeName2+"' AND NODEID LIKE '1%' AND TIME BETWEEN '"+MainUtil.getNowDateT()+" 00:00:00' AND '"+time2+"' ORDER BY TIME DESC";
            List<Map<String,Object>> ListMap3=jdbcTemplate.queryForList(sql3);
            if (ListMap3.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
                resultList.add(ListMap3.get(0));
                //三次都有数据 返回结果集
                return resultList;
            }else{
                //执行下一天
                selectData("fes",nodeName2,resultList,0, MainUtil.getEveryDate(30));
            }
        }else{
            selectData("fes",nodeName1,resultList,0, MainUtil.getEveryDate(30));
        }
        return resultList;
    }

    @Override//pas
    public List<Map<String, Object>> appPrimaryAndStaSwitchPas() {
        String time= MainUtil.getNowDateF();
        System.out.println("主备机-pas--"+time);
        List<Map<String, Object>> resultList = new LinkedList<>();
        //  以scada应用为例：
        //  1）获取当天scada应用主机情况：
        String sql1="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+
                " WHERE APP_NAME = 'pas_rtnet' AND STATUS = '主机' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> map1=jdbcTemplate.queryForList(sql1);
       if(map1.size()==0)
           return resultList;
        String nodeName1=(String)map1.get(0).get("NODENAME");
        //放入集合
        resultList.add(map1.get(0));
        //  返回结果：
        // NODENAME:jbs2-gw01
        //  Time:2019-05-27 16:27:50
        // 2）	查新当天public 有没有第一次切机
        String sql2=" SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'pas_rtnet'" +
                " AND STATUS = '主机' AND NODENAME != '"+nodeName1+"' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> ListMap2=jdbcTemplate.queryForList(sql2);
        if (ListMap2.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
            String nodeName2=(String) ListMap2.get(0).get("NODENAME");
            String time2=(String) ListMap2.get(0).get("TIME");
            resultList.add(ListMap2.get(0));
            // 3）	查新当天public 有没有第二次切机
            String sql3="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'pas_rtnet' AND STATUS = '主机'"+
                    " AND NODENAME != '"+nodeName2+"' AND NODEID LIKE '1%' AND TIME BETWEEN '"+MainUtil.getNowDateT()+" 00:00:00' AND '"+time2+"' ORDER BY TIME DESC";
            List<Map<String,Object>> ListMap3=jdbcTemplate.queryForList(sql3);
            if (ListMap3.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
                resultList.add(ListMap3.get(0));
                //三次都有数据 返回结果集
                return resultList;
            }else{
                //执行下一天
                selectData("pas_rtnet",nodeName2,resultList,0, MainUtil.getEveryDate(30));
            }
        }else{
            selectData("pas_rtnet",nodeName1,resultList,0, MainUtil.getEveryDate(30));
        }
        return resultList;
    }
    @Override//pas
    public List<Map<String, Object>> appPrimaryAndStaSwitchPublic() {
        String time= MainUtil.getNowDateF();
        System.out.println("主备机-pas--"+time);
        List<Map<String, Object>> resultList = new LinkedList<>();
        //  以scada应用为例：
        //  1）获取当天scada应用主机情况：
        String sql1="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+
                " WHERE APP_NAME = 'public' AND STATUS = '主机' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> map1=jdbcTemplate.queryForList(sql1);
       if(map1.size()==0)
           return resultList;
        String nodeName1=(String)map1.get(0).get("NODENAME");
        //放入集合
        resultList.add(map1.get(0));
        //  返回结果：
        // NODENAME:jbs2-gw01
        //  Time:2019-05-27 16:27:50
        // 2）	查新当天public 有没有第一次切机
        String sql2=" SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'public'" +
                " AND STATUS = '主机' AND NODENAME != '"+nodeName1+"' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> ListMap2=jdbcTemplate.queryForList(sql2);
        if (ListMap2.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
            String nodeName2=(String) ListMap2.get(0).get("NODENAME");
            String time2=(String) ListMap2.get(0).get("TIME");
            resultList.add(ListMap2.get(0));
            // 3）	查新当天public 有没有第二次切机
            String sql3="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'public' AND STATUS = '主机'"+
                    " AND NODENAME != '"+nodeName2+"' AND NODEID LIKE '1%' AND TIME BETWEEN '"+MainUtil.getNowDateT()+" 00:00:00' AND '"+time2+"' ORDER BY TIME DESC";
            List<Map<String,Object>> ListMap3=jdbcTemplate.queryForList(sql3);
            if (ListMap3.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
                resultList.add(ListMap3.get(0));
                //三次都有数据 返回结果集
                return resultList;
            }else{
                //执行下一天
                selectData("public",nodeName2,resultList,0, MainUtil.getEveryDate(30));
            }
        }else{
            selectData("public",nodeName1,resultList,0, MainUtil.getEveryDate(30));
        }
        return resultList;
    }
    @Override//pas
    public List<Map<String, Object>> appPrimaryAndStaSwitchWams() {
        String time= MainUtil.getNowDateF();
        System.out.println("主备机-pas--"+time);
        List<Map<String, Object>> resultList = new LinkedList<>();
        //  以scada应用为例：
        //  1）获取当天scada应用主机情况：
        String sql1="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+
                " WHERE APP_NAME = 'wams' AND STATUS = '主机' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> map1=jdbcTemplate.queryForList(sql1);
       if(map1.size()==0)
           return resultList;
        String nodeName1=(String)map1.get(0).get("NODENAME");
        //放入集合
        resultList.add(map1.get(0));
        //  返回结果：
        // NODENAME:jbs2-gw01
        //  Time:2019-05-27 16:27:50
        // 2）	查新当天public 有没有第一次切机
        String sql2=" SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'wams'" +
                " AND STATUS = '主机' AND NODENAME != '"+nodeName1+"' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> ListMap2=jdbcTemplate.queryForList(sql2);
        if (ListMap2.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
            String nodeName2=(String) ListMap2.get(0).get("NODENAME");
            String time2=(String) ListMap2.get(0).get("TIME");
            resultList.add(ListMap2.get(0));
            // 3）	查新当天public 有没有第二次切机
            String sql3="SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+time+" WHERE APP_NAME = 'wams' AND STATUS = '主机'"+
                    " AND NODENAME != '"+nodeName2+"' AND NODEID LIKE '1%' AND TIME BETWEEN '"+MainUtil.getNowDateT()+" 00:00:00' AND '"+time2+"' ORDER BY TIME DESC";
            List<Map<String,Object>> ListMap3=jdbcTemplate.queryForList(sql3);
            if (ListMap3.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
                resultList.add(ListMap3.get(0));
                //三次都有数据 返回结果集
                return resultList;
            }else{
                //执行下一天
                selectData("wams",nodeName2,resultList,0, MainUtil.getEveryDate(30));
            }
        }else{
            selectData("wams",nodeName1,resultList,0, MainUtil.getEveryDate(30));
        }
        return resultList;
    }
    @Override
    public List<Map<String, Object>> procStatusMonitor() {
        String time= MainUtil.getNowDateF();
        System.out.println(" 进程状态监视列表---"+time);
        String sqlTime="SELECT MAX(TIME) TIME FROM  SYSDBA.PROCESS_DYNAMIC_INFO_"+time +" WHERE PID != -1";
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        System.out.println("---******-------------**---"+ maxTime);
        
        String sql="SELECT DISTINCT TOP 20 APP_INFO.NAME AS AC_NAME,A.*,B.NAME,C.STATUS FROM (SELECT PI.CONTEXT_NAME,PI.APP_NAME,SR.NAME NODENAME,PI.PROC_NAME"
        		+ " ,PI.PID,PI.CPU_PRECENT,PI.MEM_OCCUPY,PI.THREAD_NUM,PI.STATUS,PI.START_TIME,PI.RUN_TIME,PI.TIME FROM (SELECT NODEID,"
        		+ " CONTEXT_NAME,APP_NAME,PROC_NAME,PID,MEM_OCCUPY,CPU_PRECENT,THREAD_NUM,STATUS,COREDUMP_NUM,START_TIME,RUN_TIME,"
        		+ " COREDUMP_SUM,DISK_READ,DISK_WRITE,TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time+" WHERE TIME='"+maxTime+"' AND PID != -1) "
        		+ " PI LEFT JOIN SERVER SR ON PI.NODEID=SR.ID ORDER BY CPU_PRECENT DESC,MEM_OCCUPY DESC) A"
        		+ " LEFT JOIN  CONFIG_PROCESS_INFO B ON A.PROC_NAME=B.ENG_NAME INNER JOIN "
        		+ " (SELECT * FROM SHOWSERVICE_INFO_"+time+" WHERE TIME ='"+maxTime+"' AND STATUS = '主机') C "
        		+ " ON  A.NODENAME = C.NODENAME LEFT JOIN APP_INFO ON A.APP_NAME=APP_INFO.ENG_NAME WHERE B.NAME "
                + " IS NOT NULL AND APP_INFO.NAME IS NOT NULL  ORDER BY A.CPU_PRECENT DESC";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

 /*   @Override
    public List<Map<String, Object>> procStateCPUnowTop3() {
        String time= MainUtil.getNowDateF();
        System.out.println("当前进程状态CPU使用率Top3---"+time);
        String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        
        String sql="SELECT A.PROC_NAME,B.NAME,A.CPU_PRECENT,A.NODEID FROM (SELECT TOP 3 PROC_NAME,MAX(CPU_PRECENT) CPU_PRECENT,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time
        		+" WHERE TIME ='"+maxTime+"' GROUP BY PROC_NAME,CONTEXT_NAME,APP_NAME,NODEID ORDER BY CPU_PRECENT DESC) A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
      @Override
     public List<Map<String, Object>> procStateCPUAvgHour( LinkedList<String> nList, LinkedList<String> sList) {
          String time= MainUtil.getNowDateF();
        String procName1=nList.get(0);
        String procName2=nList.get(1);
        String procName3=nList.get(2);
        String nodeId1=sList.get(0);
        String nodeId2=sList.get(1);
        String nodeId3=sList.get(2);
          System.out.println("fafdfdasfaaa---"+procName1+procName2+procName3);
          System.out.println("当前进程状态CPU使用率Top3---"+time);
          String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
          String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
          maxTime=MainUtil.getMaxTimeMinute(maxTime);
          String beforTime= MainUtil.getBeforHourDate(maxTime);
          List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
          String sql1="SELECT A.PROC_NAME,B.NAME,A.AVG_PRECENT FROM (SELECT PROC_NAME,ROUND(AVG(CPU_PRECENT),2) AVG_PRECENT,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time+
        " WHERE TIME BETWEEN '"+beforTime+"' AND '"+maxTime+"' AND PROC_NAME ='"+procName1+"' AND NODEID='"+nodeId1+"' GROUP BY PROC_NAME,NODEID ORDER BY AVG_PRECENT DESC)"
       +" A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
          //sql  参数集合
          List<Map<String,Object>> resultList1 = jdbcTemplate.queryForList(sql1);
          String sql2="SELECT A.PROC_NAME,B.NAME,A.AVG_PRECENT FROM (SELECT PROC_NAME,ROUND(AVG(CPU_PRECENT),2) AVG_PRECENT,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time+
        	        " WHERE TIME BETWEEN '"+beforTime+"' AND '"+maxTime+"' AND PROC_NAME ='"+procName2+"' AND NODEID='"+nodeId2+"' GROUP BY PROC_NAME,NODEID ORDER BY AVG_PRECENT DESC)"
        	       +" A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
          //sql  参数集合
          List<Map<String,Object>> resultList2 = jdbcTemplate.queryForList(sql2);
          String sql3="SELECT A.PROC_NAME,B.NAME,A.AVG_PRECENT FROM (SELECT PROC_NAME,ROUND(AVG(CPU_PRECENT),2) AVG_PRECENT,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time+
        	        " WHERE TIME BETWEEN '"+beforTime+"' AND '"+maxTime+"' AND PROC_NAME ='"+procName3+"' AND NODEID='"+nodeId3+"' GROUP BY PROC_NAME,NODEID ORDER BY AVG_PRECENT DESC)"
        	       +" A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
          //sql  参数集合
          List<Map<String,Object>> resultList3 = jdbcTemplate.queryForList(sql3);
          resultList.addAll(resultList1);
          resultList.addAll(resultList2);
          resultList.addAll(resultList3);
          
          return resultList;
     }
    @Override
    public List<Map<String, Object>> procStateCPUDay(LinkedList<String> nList, LinkedList<String> sList) {
        String time= MainUtil.getNowDateF();
        String procName1=nList.get(0);
        String procName2=nList.get(1);
        String procName3=nList.get(2);
        String nodeId1=sList.get(0);
        String nodeId2=sList.get(1);
        String nodeId3=sList.get(2);
        System.out.println("当前进程状态CPU使用率Top3天---"+time);
        String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        //查询当天 此三个 进程在所有节点上 每个时间的 信息
        String sql1="SELECT DISTINCT PROC_NAME,NODEID,TIME,CPU_PRECENT FROM PROCESS_DYNAMIC_INFO_"+time+""
        		+ " WHERE PROC_NAME = '"+procName1+"' AND NODEID = '"+nodeId1+"' ORDER BY TIME";
        List<Map<String,Object>> result1 = jdbcTemplate.queryForList(sql1);
        //查询当天 此三个 进程在所有节点上 每个时间的 信息
        String sql2="SELECT DISTINCT PROC_NAME,NODEID,TIME,CPU_PRECENT FROM PROCESS_DYNAMIC_INFO_"+time+""
        		+ " WHERE PROC_NAME = '"+procName2+"' AND NODEID = '"+nodeId2+"' ORDER BY TIME";
        List<Map<String,Object>> result2 = jdbcTemplate.queryForList(sql2);
        //查询当天 此三个 进程在所有节点上 每个时间的 信息
        String sql3="SELECT DISTINCT PROC_NAME,NODEID,TIME,CPU_PRECENT FROM PROCESS_DYNAMIC_INFO_"+time+""
        		+ " WHERE PROC_NAME = '"+procName3+"' AND NODEID = '"+nodeId3+"' ORDER BY TIME";
        List<Map<String,Object>> result3 = jdbcTemplate.queryForList(sql3);
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        resultList.addAll(result1);
        resultList.addAll(result2);
        resultList.addAll(result3);
        return resultList;
    }*/

/*    @Override
    public List<Map<String, Object>> procStateCPUWeek(LinkedList<String> nList, LinkedList<String> sList) {
    	 String time= MainUtil.getNowDateF();
    	 ArrayList<String> dateList= MainUtil.getEveryDate(7);
         String procName1=nList.get(0);
         String procName2=nList.get(1);
         String procName3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
         System.out.println("当前进程状态CPU使用率Top3天---"+time);
         String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
         String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
         maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String sql ="SELECT PROC_NAME,MAX(CPU_PRECENT) CPU_PRECENT,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE PROC_NAME = '"+procName1+"' AND NODEID ='"+nodeId1+"' GROUP BY PROC_NAME ";
        for (int i=1;i<7;i++){
        	   String num=dateList.get(i);
        	   System.out.println("当前进程状态CPU使用率Top3天--num-"+num);
            //String num=time;
              sql += " UNION ALL SELECT PROC_NAME,MAX(CPU_PRECENT) CPU_PRECENT,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE PROC_NAME = '"+procName1+"' AND NODEID ='"+nodeId1+"' GROUP BY PROC_NAME";
        }
       
        //sql  参数集合
        List<Map<String,Object>> result = jdbcTemplate.queryForList(sql);
        return result;
    }*/

  /*  @Override
    public List<Map<String, Object>> procSateMemoryFotTop3() {
        String time= MainUtil.getNowDateF();
        System.out.println("procSateMemoryFotTop3---"+time);
        String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String sql="SELECT A.PROC_NAME,ROUND(A.MEM_OCCUPY/1024,2) MEM_OCCUPY,B.NAME,A.NODEID FROM (SELECT TOP 3 PROC_NAME,MAX(MEM_OCCUPY) MEM_OCCUPY,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE TIME ='"+maxTime+"' GROUP BY PROC_NAME,CONTEXT_NAME,APP_NAME,NODEID ORDER BY MEM_OCCUPY DESC) A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    @Override
    public List<Map<String, Object>> procSateMemoryAvgHour(LinkedList<String> nList, LinkedList<String> sList) {
    	 String time= MainUtil.getNowDateF();
         String procName1=nList.get(0);
         String procName2=nList.get(1);
         String procName3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
           System.out.println("fafdfdasfaaa---"+procName1+procName2+procName3);
           System.out.println("procSateMemoryAvgHour---"+time);
           String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
           String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
           maxTime=MainUtil.getMaxTimeMinute(maxTime);
           String beforTime= MainUtil.getBeforHourDate(maxTime);
           List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
           String sql1="SELECT A.PROC_NAME,ROUND(A.AVG_OCCUPY/1024,2) AVG_OCCUPY,B.NAME FROM (SELECT TOP 3 PROC_NAME,ROUND(AVG(MEM_OCCUPY),2) AVG_OCCUPY,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time+
         " WHERE TIME BETWEEN '"+beforTime+"' AND '"+maxTime+"' AND PROC_NAME ='"+procName1+"' AND NODEID='"+nodeId1+"' GROUP BY PROC_NAME,NODEID ORDER BY AVG_OCCUPY DESC)"+
         " A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
           //sql  参数集合
           List<Map<String,Object>> resultList1 = jdbcTemplate.queryForList(sql1);
           String sql2="SELECT A.PROC_NAME,ROUND(A.AVG_OCCUPY/1024,2) AVG_OCCUPY,B.NAME FROM (SELECT TOP 3 PROC_NAME,ROUND(AVG(MEM_OCCUPY),2) AVG_OCCUPY,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time+
        	         " WHERE TIME BETWEEN '"+beforTime+"' AND '"+maxTime+"' AND PROC_NAME ='"+procName2+"' AND NODEID='"+nodeId2+"' GROUP BY PROC_NAME,NODEID ORDER BY AVG_OCCUPY DESC)"+
        	         " A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
           //sql  参数集合
           List<Map<String,Object>> resultList2 = jdbcTemplate.queryForList(sql2);
           String sql3="SELECT A.PROC_NAME,ROUND(A.AVG_OCCUPY/1024,2) AVG_OCCUPY,B.NAME FROM (SELECT TOP 3 PROC_NAME,ROUND(AVG(MEM_OCCUPY),2) AVG_OCCUPY,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time+
        	         " WHERE TIME BETWEEN '"+beforTime+"' AND '"+maxTime+"' AND PROC_NAME ='"+procName3+"' AND NODEID='"+nodeId3+"' GROUP BY PROC_NAME,NODEID ORDER BY AVG_OCCUPY DESC)"+
        	         " A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
           //sql  参数集合
           List<Map<String,Object>> resultList3 = jdbcTemplate.queryForList(sql3);
           resultList.addAll(resultList1);
           resultList.addAll(resultList2);
           resultList.addAll(resultList3);
           
           return resultList;
    }

    @Override
    public List<Map<String, Object>> procSateMemoryFotDay(LinkedList<String> nList, LinkedList<String> sList) {
    	 String time= MainUtil.getNowDateF();
         String procName1=nList.get(0);
         String procName2=nList.get(1);
         String procName3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
         System.out.println("procSateMemoryFotDay---"+time);
         String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
         String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
         maxTime=MainUtil.getMaxTimeMinute(maxTime);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql1="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(MEM_OCCUPY/1024,2) MEM_OCCUPY FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName1+"' AND NODEID = '"+nodeId1+"' ORDER BY TIME";
         List<Map<String,Object>> result1 = jdbcTemplate.queryForList(sql1);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql2="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(MEM_OCCUPY/1024,2) MEM_OCCUPY FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName2+"' AND NODEID = '"+nodeId2+"' ORDER BY TIME";
         List<Map<String,Object>> result2 = jdbcTemplate.queryForList(sql2);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql3="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(MEM_OCCUPY/1024,2) MEM_OCCUPY FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName3+"' AND NODEID = '"+nodeId3+"' ORDER BY TIME";
         List<Map<String,Object>> result3 = jdbcTemplate.queryForList(sql3);
         List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
         resultList.addAll(result1);
         resultList.addAll(result2);
         resultList.addAll(result3);
         return resultList;
    }*/

   /* @Override
    public List<Map<String, Object>> procSateMemoryFotWeek(LinkedList<String> list) {
        String time= MainUtil.getNowDateF();
        System.out.println("procSateMemoryFotWeek---"+time);
        List<String> params = new ArrayList<>();
        for (int i=0;i<7;i++){
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                params.add((String)iterator.next());
            }
        }
        String sql ="SELECT PROC_NAME,MAX(MEM_OCCUPY) MEM_OCCUPY,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE PROC_NAME IN (?,?,?)" +
                " GROUP BY PROC_NAME ";
        ArrayList<String> dateList= MainUtil.getEveryDate(7);
        for (int i=1;i<7;i++){
        	  String num=dateList.get(i);
              //String num=time;
            sql += " UNION ALL SELECT PROC_NAME,MAX(MEM_OCCUPY) MEM_OCCUPY,'"+num+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+num +
                    " WHERE PROC_NAME IN (?,?,?)" +
                    " GROUP BY PROC_NAME ";
        }
        sql +=" ORDER BY PROC_NAME";
        //sql  参数集合
        List<Map<String,Object>> result = jdbcTemplate.queryForList(sql,params.toArray());
        return result;
    }
*/
  /*  @Override
    public List<Map<String, Object>> numOfOrocessStateThreadsTop3() {
        String time= MainUtil.getNowDateF();
        System.out.println("numOfOrocessStateThreadsTop3---"+time);
        String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String sql="SELECT A.PROC_NAME,A.THREAD_NUM,B.NAME,A.NODEID FROM (SELECT TOP 3 PROC_NAME,MAX(THREAD_NUM) THREAD_NUM,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE TIME ='"+maxTime+"' GROUP BY PROC_NAME,CONTEXT_NAME,APP_NAME,NODEID ORDER BY THREAD_NUM DESC) A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID ";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }*/
    
  /*  @Override
    public List<Map<String, Object>> numOfOrocessStateThreadAvgHour( LinkedList<String> list) {
        String time= MainUtil.getNowDateF();
        String procName1=list.get(0);
        String procName2=list.get(1);
        String procName3=list.get(2);
        System.out.println("numOfOrocessStateThreadAvgHour---"+time);
        String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String beforTime= MainUtil.getBeforHourDate(maxTime);
        String sql="SELECT A.PROC_NAME,A.AVG_NUM,B.NAME FROM (SELECT TOP 3 PROC_NAME,AVG(THREAD_NUM) AVG_NUM,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE TIME BETWEEN '"+beforTime+"' AND '"+maxTime+"' AND PROC_NAME IN ('"+procName1+"','"+procName2+"','"+procName3+"')" +
                " GROUP BY PROC_NAME,CONTEXT_NAME,APP_NAME,NODEID ORDER BY MEM_OCCUPY DESC) A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
        //sql  参数集合
        List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }*/

  /*  @Override
    public List<Map<String, Object>> numOfOrocessStateThreadsDay( LinkedList<String> nList, LinkedList<String> sList) {
    	 String time= MainUtil.getNowDateF();
         String procName1=nList.get(0);
         String procName2=nList.get(1);
         String procName3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
         System.out.println("procSateMemoryFotDay---"+time);
         String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
         String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
         maxTime=MainUtil.getMaxTimeMinute(maxTime);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql1="SELECT DISTINCT PROC_NAME,NODEID,TIME,THREAD_NUM FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName1+"' AND NODEID = '"+nodeId1+"' ORDER BY TIME";
         List<Map<String,Object>> result1 = jdbcTemplate.queryForList(sql1);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql2="SELECT DISTINCT PROC_NAME,NODEID,TIME,THREAD_NUM FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName2+"' AND NODEID = '"+nodeId2+"' ORDER BY TIME";
         List<Map<String,Object>> result2 = jdbcTemplate.queryForList(sql2);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql3="SELECT DISTINCT PROC_NAME,NODEID,TIME,THREAD_NUM FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName3+"' AND NODEID = '"+nodeId3+"' ORDER BY TIME";
         List<Map<String,Object>> result3 = jdbcTemplate.queryForList(sql3);
         List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
         resultList.addAll(result1);
         resultList.addAll(result2);
         resultList.addAll(result3);
         return resultList;
    }
*/
    /*@Override
    public List<Map<String, Object>> numOfOrocessStateThreadsWeek(LinkedList<String> list) {
        String time= MainUtil.getNowDateF();
        System.out.println("numOfOrocessStateThreadsWeek---"+time);
        List<String> params = new ArrayList<>();
        for (int i=0;i<7;i++){
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                params.add((String)iterator.next());
            }
        }
        String sql ="SELECT PROC_NAME,MAX(THREAD_NUM) THREAD_NUM,'"+time+" 'AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE PROC_NAME IN (?,?,?)" +
                " GROUP BY PROC_NAME ";
        ArrayList<String> dateList= MainUtil.getEveryDate(7);
        for (int i=1;i<7;i++){
        	  String num=dateList.get(i);
              //String num=time;
            sql += " UNION ALL SELECT PROC_NAME,MAX(THREAD_NUM) THREAD_NUM,'"+num+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+num +
                    " WHERE PROC_NAME IN (?,?,?)" +
                    " GROUP BY PROC_NAME";
        }
        sql +=" ORDER BY PROC_NAME";
        //sql  参数集合

        List<Map<String,Object>> result = jdbcTemplate.queryForList(sql,params.toArray());
        return result;
    }*/

   /* @Override
    public List<Map<String, Object>> processStatusDiskIOReadIncrementTop3() {
        String time= MainUtil.getNowDateF();
        System.out.println("processStatusDiskIOReadWriteIncrementTop3---"+time);
        String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String sql="SELECT A.PROC_NAME,ROUND(A.DISK_READ/1024) DISK_READ,B.NAME,A.NODEID FROM (SELECT TOP 3 PROC_NAME,MAX(DISK_READ) DISK_READ,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE TIME ='"+maxTime+"' GROUP BY PROC_NAME,CONTEXT_NAME,APP_NAME,NODEID ORDER BY MAX(DISK_READ) DESC) A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }*/

 /*   @Override
    public List<Map<String, Object>> processStatusDiskIOReadIncrementDay(LinkedList<String> nList, LinkedList<String> sList) {
    	 String time= MainUtil.getNowDateF();
         String procName1=nList.get(0);
         String procName2=nList.get(1);
         String procName3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
         System.out.println("procSateMemoryFotDay---"+time);
         String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
         String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
         maxTime=MainUtil.getMaxTimeMinute(maxTime);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql1="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(DISK_READ/1024) DISK_READ FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName1+"' AND NODEID = '"+nodeId1+"' ORDER BY TIME";
         List<Map<String,Object>> result1 = jdbcTemplate.queryForList(sql1);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql2="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(DISK_READ/1024) DISK_READ FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName2+"' AND NODEID = '"+nodeId2+"' ORDER BY TIME";
         List<Map<String,Object>> result2 = jdbcTemplate.queryForList(sql2);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql3="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(DISK_READ/1024) DISK_READ FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName3+"' AND NODEID = '"+nodeId3+"' ORDER BY TIME";
         List<Map<String,Object>> result3 = jdbcTemplate.queryForList(sql3);
         List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
         resultList.addAll(result1);
         resultList.addAll(result2);
         resultList.addAll(result3);
         return resultList;
    }
*/
 /*   @Override
    public List<Map<String, Object>> processStatusDiskIOWriteIncrementTop3() {
        String time= MainUtil.getNowDateF();
        System.out.println("processStatusDiskIOReadWriteIncrementTop3---"+time);
        String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String sql="SELECT A.PROC_NAME,ROUND(A.DISK_WRITE/1024) DISK_WRITE,B.NAME,A.NODEID FROM (SELECT TOP 3 PROC_NAME,MAX(DISK_WRITE) DISK_WRITE,NODEID FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                " WHERE TIME ='"+maxTime+"' GROUP BY PROC_NAME,CONTEXT_NAME,APP_NAME,NODEID ORDER BY MAX(DISK_WRITE) DESC) A LEFT JOIN SYSDBA.SERVER B ON A.NODEID =B.ID";;
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }*/

  /*  @Override
    public List<Map<String, Object>> processStatusDiskIOWriteIncrementDay(LinkedList<String> nList, LinkedList<String> sList) {
    	 String time= MainUtil.getNowDateF();
         String procName1=nList.get(0);
         String procName2=nList.get(1);
         String procName3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
         System.out.println("procSateMemoryFotDay---"+time);
         String sqlTime="SELECT MAX(TIME) TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time;
         String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
         maxTime=MainUtil.getMaxTimeMinute(maxTime);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql1="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(DISK_WRITE/1024) DISK_WRITE FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName1+"' AND NODEID = '"+nodeId1+"' ORDER BY TIME";
         List<Map<String,Object>> result1 = jdbcTemplate.queryForList(sql1);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql2="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(DISK_WRITE/1024) DISK_WRITE FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName2+"' AND NODEID = '"+nodeId2+"' ORDER BY TIME";
         List<Map<String,Object>> result2 = jdbcTemplate.queryForList(sql2);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql3="SELECT DISTINCT PROC_NAME,NODEID,TIME,ROUND(DISK_WRITE/1024) DISK_WRITE FROM PROCESS_DYNAMIC_INFO_"+time+""
         		+ " WHERE PROC_NAME = '"+procName3+"' AND NODEID = '"+nodeId3+"' ORDER BY TIME";
         List<Map<String,Object>> result3 = jdbcTemplate.queryForList(sql3);
         List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
         resultList.addAll(result1);
         resultList.addAll(result2);
         resultList.addAll(result3);
         return resultList;
    }
*/
  /*  @Override
    public List<Map<String, Object>> processStatusDiskIOReadIncrementWeek(LinkedList<String> nList, LinkedList<String> sList) {
    	 String time= MainUtil.getNowDateF();
         String procName1=nList.get(0);
         String procName2=nList.get(1);
         String procName3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
         System.out.println("processStatusDiskIOReadWriteIncrementWeek---"+time);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql1="SELECT * FROM (SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_READ)/1024) DISK_READ,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                      " WHERE PROC_NAME ='"+procName1+"' AND NODEID ='"+nodeId1+"' GROUP BY PROC_NAME,NODEID";
         ArrayList<String> dateList= MainUtil.getEveryDate(7);
         for (int i=1;i<7;i++){
         	  String num=dateList.get(i);
               //String num=time;
             sql1 += " UNION ALL SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_READ)/1024) DISK_READ,'"+num+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+num +
                      " WHERE PROC_NAME ='"+procName1+"' AND NODEID ='"+nodeId1+"' GROUP BY PROC_NAME,NODEID";
         }
         sql1 +=") ORDER BY TIME";
         List<Map<String,Object>> result1 = jdbcTemplate.queryForList(sql1);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql2="SELECT * FROM (SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_READ)/1024) DISK_READ,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
        		 " WHERE PROC_NAME ='"+procName2+"' AND NODEID ='"+nodeId2+"' GROUP BY PROC_NAME,NODEID";
         for (int i=1;i<7;i++){
        	 String num=dateList.get(i);
        	 //String num=time;
        	 sql2 += " UNION ALL SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_READ)/1024) DISK_READ,'"+num+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+num +
        			 " WHERE PROC_NAME ='"+procName2+"' AND NODEID ='"+nodeId2+"' GROUP BY PROC_NAME,NODEID";
         }
         sql2 +=") ORDER BY TIME";
         List<Map<String,Object>> result2 = jdbcTemplate.queryForList(sql2);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql3="SELECT * FROM (SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_READ)/1024) DISK_READ,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
        		 " WHERE PROC_NAME ='"+procName3+"' AND NODEID ='"+nodeId3+"' GROUP BY PROC_NAME,NODEID";
         for (int i=1;i<7;i++){
        	 String num=dateList.get(i);
        	 //String num=time;
        	 sql3 += " UNION ALL SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_READ)/1024) DISK_READ,'"+num+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+num +
        			 " WHERE PROC_NAME ='"+procName3+"' AND NODEID ='"+nodeId3+"' GROUP BY PROC_NAME,NODEID";
         }
         sql3 +=") ORDER BY TIME";
         //sql  参数集合
         List<Map<String,Object>> result3 = jdbcTemplate.queryForList(sql3);
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
         resultList.addAll(result1);
         resultList.addAll(result2);
         resultList.addAll(result3);
         return resultList;
    }
*/
  /*  @Override
    public List<Map<String, Object>> processStatusDiskIOWriteIncrementWeek(LinkedList<String> nList, LinkedList<String> sList) {
    	 String time= MainUtil.getNowDateF();
         String procName1=nList.get(0);
         String procName2=nList.get(1);
         String procName3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
         System.out.println("processStatusDiskIOReadWriteIncrementWeek---"+time);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         
         String sql1="SELECT * FROM (SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_WRITE)/1024) DISK_WRITE,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
                      " WHERE PROC_NAME ='"+procName1+"' AND NODEID ='"+nodeId1+"' GROUP BY PROC_NAME,NODEID";
         ArrayList<String> dateList= MainUtil.getEveryDate(7);
         for (int i=1;i<7;i++){
         	  String num=dateList.get(i);
               //String num=time;
             sql1 += " UNION ALL SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_WRITE)/1024) DISK_WRITE,'"+num+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+num +
                      " WHERE PROC_NAME ='"+procName1+"' AND NODEID ='"+nodeId1+"' GROUP BY PROC_NAME,NODEID";
         }
         sql1 +=") ORDER BY TIME";
         List<Map<String,Object>> result1 = jdbcTemplate.queryForList(sql1);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql2="SELECT * FROM (SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_WRITE)/1024) DISK_WRITE,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
        		 " WHERE PROC_NAME ='"+procName2+"' AND NODEID ='"+nodeId2+"' GROUP BY PROC_NAME,NODEID";
         for (int i=1;i<7;i++){
        	 String num=dateList.get(i);
        	 //String num=time;
        	 sql2 += " UNION ALL SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_WRITE)/1024) DISK_WRITE,'"+num+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+num +
        			 " WHERE PROC_NAME ='"+procName2+"' AND NODEID ='"+nodeId2+"' GROUP BY PROC_NAME,NODEID";
         }
         sql2 +=") ORDER BY TIME";
         List<Map<String,Object>> result2 = jdbcTemplate.queryForList(sql2);
         //查询当天 此三个 进程在所有节点上 每个时间的 信息
         String sql3="SELECT * FROM (SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_WRITE)/1024) DISK_WRITE,'"+time+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+time +
        		 " WHERE PROC_NAME ='"+procName3+"' AND NODEID ='"+nodeId3+"' GROUP BY PROC_NAME,NODEID";
         for (int i=1;i<7;i++){
        	 String num=dateList.get(i);
        	 //String num=time;
        	 sql3 += " UNION ALL SELECT PROC_NAME,NODEID,ROUND(MAX(DISK_WRITE)/1024) DISK_WRITE,'"+num+"' AS TIME FROM SYSDBA.PROCESS_DYNAMIC_INFO_"+num +
        			 " WHERE PROC_NAME ='"+procName3+"' AND NODEID ='"+nodeId3+"' GROUP BY PROC_NAME,NODEID";
         }
         sql3 +=") ORDER BY TIME";
         //sql  参数集合
         List<Map<String,Object>> result3 = jdbcTemplate.queryForList(sql3);
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
         resultList.addAll(result1);
         resultList.addAll(result2);
         resultList.addAll(result3);
         return resultList;
    }
*/
    @Override
    public List<Map<String, Object>> dataCPUUtilCurveWeek(String nodeid) {//单位%
        String time= MainUtil.getNowDateF();
        System.out.println("dataCPUUtilCurveWeek---"+time);
        List<String> params = new ArrayList<>();
        //查询一周每天的最大date
        String sql="SELECT * FROM (SELECT * FROM (SELECT TOP 1 DATA,SUBSTR(TIME,0,10) AS TIME FROM SYSDBA.CPU_PERCENT_"+time+
                " WHERE NODEID =? ORDER BY DATA DESC)";
        params.add(nodeid);
        ArrayList<String> dateList= MainUtil.getEveryDate(7);
        for (int i=1;i<7;i++){
        	  String num=dateList.get(i);
              //String num=time;
            sql += " UNION ALL SELECT * FROM (SELECT TOP 1 DATA,SUBSTR(TIME,0,10) AS TIME FROM SYSDBA.CPU_PERCENT_"+num+
                    " WHERE NODEID =? ORDER BY DATA DESC)";
            params.add(nodeid);
        }
        sql += " ) ORDER BY TIME";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }


    @Override
    public List<Map<String, Object>> dataCPUUtilCurveToday(String nodeid) {// 单位%
        String time= MainUtil.getNowDateF();
        System.out.println("dataCPUUtilCurveDay---"+time);
        List<String> params = new ArrayList<>();
        //查询每天的每个时间的最大data
        String sql="SELECT DATA,TIME FROM SYSDBA.CPU_PERCENT_"+time+" WHERE NODEID =?";
        //sql  参数集合
        params.add(nodeid);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }
    @Override
    public List<Map<String, Object>> dataCPUUtilCurveYesterday(String nodeid) {// 单位%
    	String time= MainUtil.getPastDate(1);  //昨天日期
    	System.out.println("dataCPUUtilCurveDay---"+time);
    	List<String> params = new ArrayList<>();
    	//查询每天的每个时间的最大data
    	String sql="SELECT DATA,TIME FROM SYSDBA.CPU_PERCENT_"+time+" WHERE NODEID =?";
    	//sql  参数集合
    	params.add(nodeid);
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }

    @Override
    public List<Map<String, Object>> dataMemoryUtilCurveWeek(String nodeid) {
        String time= MainUtil.getNowDateF();
        System.out.println("dataMemoryUtilCurveWeek---"+time);
        List<String> params = new ArrayList<String>();
        //查询一周每天的最大date
        String sql="SELECT * FROM (SELECT ROUND(((1-A.DATA/100)*B.MEMORYTOTAL)/1024/1024,0) DATA,SUBSTR(A.TIME,0,10) AS TIME FROM" +
                " (SELECT TOP 1 NODEID,DATA,TIME FROM SYSDBA.MEM_PERCENT_" +time+
                " WHERE NODEID =? ORDER BY DATA DESC) A LEFT JOIN SERVER B ON A.NODEID=B.ID";
        params.add(nodeid);
        ArrayList<String> dateList= MainUtil.getEveryDate(7);
        for (int i=1;i<7;i++){
        	  String num=dateList.get(i);
              //String num=time;
            sql += " UNION ALL SELECT ROUND(((1-A.DATA/100)*B.MEMORYTOTAL)/1024/1024,0) DATA,SUBSTR(A.TIME,0,10) AS TIME FROM" +
                    " (SELECT TOP 1 NODEID,DATA,TIME FROM SYSDBA.MEM_PERCENT_" +num+
                    " WHERE NODEID =? ORDER BY DATA DESC) A LEFT JOIN SERVER B ON A.NODEID=B.ID";
            params.add(nodeid);
        }
        sql += " ) ORDER BY TIME";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> dataMemoryUtilCurveToday(String nodeid) {
        String time= MainUtil.getNowDateF();
        System.out.println("dataMemoryUtilCurveToday---"+time);
        List<String> params = new ArrayList<>();
        //查询每天的每个时间的最大data
        String sql="SELECT ROUND(((1-A.DATA/100)*B.MEMORYTOTAL)/1024/1024,0) DATA,SUBSTR(A.TIME,0,10) AS TIME FROM" +
                " (SELECT NODEID,MAX(DATA) DATA,TIME FROM SYSDBA.MEM_PERCENT_" +time+
                " WHERE NODEID =? GROUP BY TIME,NODEID) A LEFT JOIN" +
                " SERVER B ON A.NODEID=B.ID ORDER BY TIME";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }
    @Override
    public List<Map<String, Object>> dataMemoryUtilCurveYesterDay(String nodeid) {
    	String time= MainUtil.getPastDate(1); //昨天时间
    	System.out.println("dataMemoryUtilCurveDay---"+time);
    	List<String> params = new ArrayList<>();
    	//查询每天的每个时间的最大data
    	String sql="SELECT ROUND(((1-A.DATA/100)*B.MEMORYTOTAL)/1024/1024,0) DATA,A.TIME FROM" +
    			" (SELECT NODEID,MAX(DATA) DATA,TIME FROM SYSDBA.MEM_PERCENT_" +time+
    			" WHERE NODEID =? GROUP BY TIME,NODEID) A LEFT JOIN" +
    			" SERVER B ON A.NODEID=B.ID ORDER BY TIME ";
    	params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }

    @Override
    public List<Map<String, Object>> dataIOCurveWeek(String nodeid) {//单位  b
        String time= MainUtil.getNowDateF();
        System.out.println("dataIOCurveWeek---"+time);
        List<String> params = new ArrayList<String>();
        //查询一周每天的最大date
        String pTime=time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);
        String sql="SELECT * FROM ( SELECT * FROM (SELECT ROUND(MAX(READ_BYTES)/1024/1024,0) READ_BYTES,ROUND(MAX(WRITE_BYTES)/1024/1024,0) WRITE_BYTES,'"+pTime+"' STAT_TIME FROM SYSDBA.TB_DB_IO_STAT_"+time+
                " WHERE NODEID =? )";
        params.add(nodeid);
        ArrayList<String> dateList= MainUtil.getEveryDate(7);
        for (int i=1;i<7;i++){
        	  String num=dateList.get(i);
              pTime=num.substring(0,4)+"-"+num.substring(4,6)+"-"+num.substring(6,8);
              //String num=time;
            sql += " UNION ALL SELECT * FROM (SELECT ROUND(MAX(READ_BYTES)/1024/1024,0) READ_BYTES,ROUND(MAX(WRITE_BYTES)/1024/1024,0) WRITE_BYTES,'"+pTime+"' STAT_TIME FROM SYSDBA.TB_DB_IO_STAT_"+num+
                    " WHERE NODEID =? )";
            params.add(nodeid);
        }
        sql += " ) ORDER BY STAT_TIME";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> dataIOCurveToday(String nodeid) {//单位  b
       String time= MainUtil.getNowDateF();
    	System.out.println("dataIOCurveToday---"+time);
        List<String> params = new ArrayList<>();
        //查询每天的每个时间的最大data
        String sql="SELECT ROUND(MAX(READ_BYTES)/1024/1024,0) READ_BYTES,ROUND(MAX(WRITE_BYTES)/1024/1024,0) WRITE_BYTES ,STAT_TIME FROM SYSDBA.TB_DB_IO_STAT_"+time+
                " WHERE NODEID =? GROUP BY STAT_TIME";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }
    @Override
    public List<Map<String, Object>> dataIOCurveYesterDay(String nodeid) {//单位  b
    	 String time= MainUtil.getPastDate(1);
    	System.out.println("dataIOCurveYesterDay---"+time+"---"+nodeid);
    	List<String> params = new ArrayList<>();
    	//查询每天的每个时间的最大data
    	String sql="SELECT ROUND(MAX(READ_BYTES)/1024/1024,0) READ_BYTES,ROUND(MAX(WRITE_BYTES)/1024/1024,0) WRITE_BYTES ,STAT_TIME FROM SYSDBA.TB_DB_IO_STAT_"+time+
    			" WHERE NODEID =? GROUP BY STAT_TIME";
    	params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }

    @Override
    public List<Map<String, Object>> dataDiskCurveWeek(String nodeid) {
        String time= MainUtil.getNowDateF();
        System.out.println("dataIOCurveDay---"+time+"--"+nodeid);
        List<String> params = new ArrayList<String>();
        //查询一周每天的最大date
        String sql="SELECT * FROM (SELECT * FROM (SELECT TOP 1 ROUND(MAX(DATA)/30,0) DATA,SUBSTR(TIME,0,10) AS TIME FROM SYSDBA.DISK_TOTAL_PERCENT_"+time+" WHERE NODEID =? ORDER BY DATA DESC)";
        params.add(nodeid);
        ArrayList<String> dateList= MainUtil.getEveryDate(7);
        for (int i=1;i<7;i++){
        	  String num=dateList.get(i);
              //String num=time;
            sql += " UNION ALL SELECT * FROM (SELECT TOP 1 ROUND(MAX(DATA)/30,0) DATA,SUBSTR(TIME,0,10) AS TIME FROM SYSDBA.DISK_TOTAL_PERCENT_"+ num +" WHERE NODEID =? ORDER BY DATA DESC)";
            params.add(nodeid);
        }
        sql += " ) ORDER BY TIME";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> dataDiskReadCurveToday(String nodeid) {
    	  String time= MainUtil.getNowDateF();
    	System.out.println("dataDiskCurveToday---"+time+"--"+nodeid);
        List<String> params = new ArrayList<>();
        //查询每天的每个时间的最大data
        String sql="SELECT ROUND(MAX(DATA)/30,0) DATA,TIME FROM SYSDBA.DISK_READ_TOTAL_SPEED_"+time+" WHERE NODEID =? ORDER BY TIME";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }
    @Override
    public List<Map<String, Object>> dataDiskReadCurveYesterday(String nodeid) {
    	 String time= MainUtil.getPastDate(1);
    	System.out.println("dataDiskCurveYesterday---"+time+"--"+nodeid);
    	List<String> params = new ArrayList<>();
    	//查询每天的每个时间的最大data
    	String sql="SELECT ROUND(MAX(DATA)/30,0) DATA,TIME FROM SYSDBA.DISK_READ_TOTAL_SPEED_"+time+" WHERE NODEID =? GROUP BY TIME ORDER BY TIME";
    	params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }
    @Override
    public List<Map<String, Object>> dataDiskWriteCurveToday(String nodeid) {
    	String time= MainUtil.getNowDateF();
    	System.out.println("dataDiskCurveToday---"+time+"--"+nodeid);
    	List<String> params = new ArrayList<>();
    	//查询每天的每个时间的最大data
    	String sql="SELECT ROUND(MAX(DATA)/30,0) DATA,TIME FROM SYSDBA.DISK_WRITE_TOTAL_SPEED_"+time+" WHERE NODEID =? GROUP BY TIME ORDER BY TIME";
    	params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }
    @Override
    public List<Map<String, Object>> dataDiskWriteCurveYesterday(String nodeid) {
    	String time= MainUtil.getPastDate(1);
    	System.out.println("dataDiskCurveYesterday---"+time+"--"+nodeid);
    	List<String> params = new ArrayList<>();
    	//查询每天的每个时间的最大data
    	String sql="SELECT ROUND(MAX(DATA)/30,0) DATA,TIME FROM SYSDBA.DISK_WRITE_TOTAL_SPEED_"+time+" WHERE NODEID =? GROUP BY TIME ORDER BY TIME";
    	params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }
    @Override
	public List<Map<String, Object>> dataDiskWriteCurveWeek(String nodeid) {
    	 String time= MainUtil.getNowDateF();
         System.out.println("dataIOCurveDay---"+time+"--"+nodeid);
         List<String> params = new ArrayList<String>();
         //查询一周每天的最大date
         String pTime=time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);
         String sql=" SELECT * FROM ( SELECT ROUND(MAX(DATA)/30,0) DATA,'"+time+"' AS TIME FROM SYSDBA.DISK_WRITE_TOTAL_SPEED_"+time+" WHERE NODEID =? ";
         params.add(nodeid);
         ArrayList<String> dateList= MainUtil.getEveryDate(7);
         for (int i=1;i<7;i++){
         	  String num=dateList.get(i);
         	 String  pTimeS=num.substring(0,4)+"-"+num.substring(4,6)+"-"+num.substring(6,8);
               //String num=time;
             sql += " UNION ALL SELECT 	ROUND(MAX(DATA)/30,0) DATA,'"+pTimeS+"' AS TIME FROM SYSDBA.DISK_WRITE_TOTAL_SPEED_"+num+" WHERE NODEID =? ";
             params.add(nodeid);
         }
         sql += " ) ORDER BY TIME";
         //sql  参数集合
         List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
         return list;
	}

    @Override
    public List<Map<String, Object>> diskPartSpaceList(String nodeid) {
        String time= MainUtil.getNowDateF();
        System.out.println("filePartSpaceList---"+time+"--"+nodeid);
        String sqlTime="SELECT MAX(TIME) FROM SYSDBA.DISK_PERCENT_"+time+" WHERE SUBSTR(NODEID,0,10) ='"+nodeid+"'";
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        List<String> params = new ArrayList<>();
       //存在 nodeid 存在  id  不存在数据
        String sql="SELECT DISTINCT B.DISKNAME,B.DISKCONTENT,B.CN_NAME,A.DATA,A.TIME,A.NODEID FROM SYSDBA.DISK_PERCENT_"+time
        		+ " A INNER JOIN DISK_INFO B ON A.NODEID=B.ID WHERE SUBSTR(A.NODEID,0,10) =? AND TIME='"+maxTime+"' ORDER BY A.TIME DESC";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> databaseDetails(String nodeid) {
        String time= MainUtil.getNowDateF();
        System.out.println("databaseDetails---"+time+"--"+nodeid);
        List<String> params = new ArrayList<>();
      //全
       String sql="SELECT TOP 1 A.*,B.NAME,B.IP,C.VERSION,C.PORT FROM SYSDBA.TB_DB_STAT_"+time +
               " A LEFT JOIN SERVER B ON A.NODEID=B.ID LEFT JOIN SYSDBA.TB_DB_CONST_STAT" +
               " C ON A.NODEID=C.NODEID WHERE A.NODEID=? ORDER BY DATEDIFF(hh,to_date(A.STAT_TIME,'yyyy-mm-dd hh:mi:ss')" +
               ",TO_DATE(A.STAT_TIME,'yyyy-mm-dd hh:mi:ss')) ASC";


        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }
    @Override
    public List<Map<String, Object>> getUserName(String nodeid) {
    	nodeid = nodeid.charAt(0)+"";
    	  String time= MainUtil.getNowDateF();
          System.out.println("dataSessionNumberCurve---"+time+"--"+nodeid);
          String sqlTime="SELECT MAX(TIME) FROM SYSDBA.TB_DB_SESSION_STAT_"+time;
          String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
          maxTime=MainUtil.getMaxTimeMinute(maxTime);
          String sqlName="SELECT top 3 * FROM (SELECT \"USER\",SUM(SESSION_COUNT) AS SESSION FROM SYSDBA.TB_DB_SESSION_STAT_"+time
           +" WHERE time ='"+maxTime+"' AND NODEID LIKE '"+nodeid+"%' GROUP BY \"USER\",NODEID ) ORDER BY SESSION DESC";
          List<Map<String,Object>> listName = jdbcTemplate.queryForList(sqlName);
          return listName;
    }
    

 
    @Override
    public List<Map<String, Object>> dataSessionNumberTodayCurve(String nodeid) {
    	nodeid = nodeid.charAt(0)+"";
        String time= MainUtil.getNowDateF();
        System.out.println("dataSessionNumberCurve---"+time+"--"+nodeid);
        
        List<Map<String,Object>> listName = getUserName(nodeid);
        String user1=listName.get(0).get("USER")+"";
        String user2=listName.get(1).get("USER")+"";
        String user3=listName.get(2).get("USER")+"";
        
        String sqlInfo ="SELECT \"USER\",SUM(SESSION_COUNT) SESSION,TIME FROM SYSDBA.TB_DB_SESSION_STAT_"+time
        		+ " WHERE NODEID LIKE '"+nodeid+"%' AND \"USER\" IN ('"+user1+"','"+user2+"','"+user3+"')"
        				+ " GROUP BY \"USER\",TIME ORDER BY \"USER\",TIME";
        List<Map<String,Object>> listInfo = jdbcTemplate.queryForList(sqlInfo);
        String sqlOther="SELECT '其它' AS \"USER\",SUM(SESSION) SESSION,TIME FROM ("
        		+ " SELECT \"USER\",SUM(SESSION_COUNT) SESSION,TIME FROM SYSDBA.TB_DB_SESSION_STAT_"+time
        		+ " WHERE NODEID LIKE '"+nodeid+"%' AND \"USER\" NOT IN ('"+user1+"','"+user2+"','"+user3+"')"
        		+ " GROUP BY \"USER\",TIME  ORDER BY \"USER\",TIME) GROUP BY TIME ";
        List<Map<String,Object>> listOther = jdbcTemplate.queryForList(sqlOther);
        listInfo.addAll(listOther);
        return listInfo;
    }
    @Override
    public List<Map<String, Object>> dataSessionNumberYesterdayCurve(String nodeid) {
    	String time= MainUtil.getPastDate(1);
    	System.out.println("dataSessionNumberCurve---"+time+"--"+nodeid);
    	List<Object> params = new ArrayList<>();
    	String sql=" SELECT STAT_TIME,SESSION_COUNT FROM SYSDBA.TB_DB_STAT_"+time+" WHERE NODEID=? ORDER BY STAT_TIME ASC";
    	
    	params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }

    @Override
    public List<Map<String, Object>> dataMemoryChangeTodayCurve(String nodeid) {
        String time= MainUtil.getNowDateF();
        System.out.println("dataMemoryChangeCurve---"+time+"--"+nodeid);
        List<Object> params = new ArrayList<>();
        String sql="SELECT ROUND(MEM_POOL_USED_BYTES/1024/1024,0) DATA,STAT_TIME TIME FROM TB_DB_MEM_STAT_"+time+" WHERE NODEID =? ORDER BY STAT_TIME";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }
    @Override
    public List<Map<String, Object>> dataMemoryChangeYesterdayCurve(String nodeid) {
    	String time= MainUtil.getPastDate(1);
    	System.out.println("dataMemoryChangeCurve---"+time+"--"+nodeid);    	
    	List<Object> params = new ArrayList<>();
    	String sql="SELECT ROUND(MEM_POOL_USED_BYTES/1024/1024,0) DATA,STAT_TIME TIME FROM TB_DB_MEM_STAT_"+time+" WHERE NODEID =? ORDER BY STAT_TIME";
        params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }

    @Override
    public List<Map<String, Object>> databaseNetworkIOToday(String nodeid) {//  单位  kb
        String time= MainUtil.getNowDateF();
        System.out.println("databaseNetworkIO---"+time+"--"+nodeid);
        List<Object> params = new ArrayList<>();
        String sql="SELECT TIME STAT_TIME,ROUND(SUM(RX_KBYTES)/30,0) RX_KBYTES,ROUND(SUM(TX_KBYTES)/30,0) TX_KBYTES"
    			+ " FROM SYSDBA.NETSTAT_INFO_"+time+" WHERE NODEID=? GROUP BY TIME ORDER BY TIME ASC";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }
    @Override
    public List<Map<String, Object>> databaseNetworkIOYesterday(String nodeid) {//  单位  kb
    	String time= MainUtil.getPastDate(1);
    	System.out.println("databaseNetworkIO---"+time+"--"+nodeid);
    	List<Object> params = new ArrayList<>();
    	String sql="SELECT TIME STAT_TIME,ROUND(SUM(RX_KBYTES)/30,0) RX_KBYTES,ROUND(SUM(TX_KBYTES)/30,0) TX_KBYTES"
    			+ " FROM SYSDBA.NETSTAT_INFO_"+time+" WHERE NODEID=? GROUP BY TIME ORDER BY TIME ASC";
    	params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }
    @Override
    public List<Map<String, Object>> databaseNetworkIOYesterdayCount(String nodeid) {//  单位  kb
    	String time= MainUtil.getPastDate(1);
    	System.out.println("databaseNetworkIO---"+time+"--"+nodeid);
    	List<Object> params = new ArrayList<>();
    	String sql="SELECT SUM(RX_KBYTES) AS 读总量,SUM(TX_KBYTES) AS 写总量  FROM ("
    			+ " SELECT TIME STAT_TIME,ROUND(SUM(RX_KBYTES),0) RX_KBYTES,ROUND(SUM(TX_KBYTES),0) TX_KBYTES"
    			+ " FROM SYSDBA.NETSTAT_INFO_"+time+" WHERE NODEID=? GROUP BY TIME ORDER BY TIME ASC)";
    	params.add(nodeid);
    	//sql  参数集合
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
    	return list;
    }

 /*   @Override
    public List<Map<String, Object>> dataTableSpaceUsageCurve(String nodeid) {
        String time= MainUtil.getNowDateF();
        System.out.println("dataTableSpaceUsageCurve---"+time+"--"+nodeid);
        String sqlTime="SELECT MAX (STAT_TIME) FROM SYSDBA.TB_DB_TABLESPACE_STAT_"+time+" WHERE NODEID='"+nodeid+"'";
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        List<String> params = new ArrayList<>();
        String sql="SELECT TOP 5 USE_RATIO,TP_NAME FROM SYSDBA.TB_DB_TABLESPACE_STAT_"+time +
                " WHERE STAT_TIME ='"+maxTime+"' AND NODEID=? ORDER BY USE_RATIO DESC";
        params.add(nodeid);
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,params.toArray());
        return list;
    }

    @Override
    public List<Map<String, Object>> alarmStatistics() {
        String time= MainUtil.getNowDateF();
        System.out.println("alarmStatistics---"+time);
    	String timeSub=time.substring(0,6);
       
        String sql="SElECT A.TYPE AS NAME,CASE WHEN NUM IS NULL  THEN 0 ELSE NUM END AS NUM FROM (" + 
        		" SELECT '数据库' as TYPE union all SELECT '实时库' as TYPE union all SELECT '消息总线' as TYPE" + 
        		" union all SELECT '服务总线' as TYPE union all SELECT '应用' as TYPE union all SELECT '进程' as TYPE) A" + 
        		" LEFT JOIN (" + 
        		" SELECT SC.TYPE NAME,COUNT (*) AS NUM FROM SYSDBA.CONFIG_ALARM_INFO SC,ALARM_HISINFO_"+timeSub+"  SA" + 
        		" WHERE SA.ITEMID LIKE '0002%' AND SA.TIME LIKE '"+MainUtil.getNowDateT()+"%' AND SC.ITEMID=SA.ITEMID" + 
        		" AND (SC.TYPE='数据库' OR SC.TYPE='实时库' OR SC.TYPE='消息总线' OR SC.TYPE='服务总线' OR SC.TYPE='应用' OR SC.TYPE='进程') " + 
        		" GROUP BY SC.TYPE) B  ON  A.TYPE =B.NAME";
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
*/
    @Override
    public List<Map<String, Object>> DataRealtimeConnectNumber(String nodeid) {
    	nodeid = nodeid.charAt(0)+"";
        String time= MainUtil.getNowDateF();
        System.out.println("DataRealtimeConnectNumber---"+time+"--"+nodeid);
        String sqlTime="SELECT MAX(TIME) FROM SYSDBA.SERVICE_INFO_"+time+" WHERE SUBSTR(NODEID,0,1) like '"+nodeid+"'";
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String sql="SELECT TOP 3 (S.NAME+'/' +SI.NAME) NAME, SI.CONNUM,SI.TIME,SI.NODEID FROM" +
                " SYSDBA.SERVICE_INFO_"+time+" SI ,SYSDBA.SERVER S WHERE TIME='"+maxTime+"' AND  NODEID LIKE '"+nodeid+"%'" +
                " AND CONNUM!=0  AND SI.NODEID=S.ID ORDER BY CONNUM DESC";     
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> oneHourAccessOf(String nodeid) {
    	nodeid = nodeid.charAt(0)+"";
        String time= MainUtil.getNowDateF();
        System.out.println("oneHourAccessOf---"+time+"--"+nodeid);
        String sqlTime=" SELECT MAX(TIME) FROM SYSDBA.SERVICE_INFO_"+time + " WHERE SUBSTR(NODEID,0,1)='"+nodeid+"'";
        String maxTime=jdbcTemplate.queryForObject(sqlTime, String.class);
        maxTime=MainUtil.getMaxTimeMinute(maxTime);
        String sql="SELECT TOP 3 (S.NAME+'/'+A.NAME) NAME,A.ACCESSNUM NUM FROM SYSDBA.SERVICE_INFO_"+time+" A"
        		+ " LEFT JOIN SYSDBA.SERVER S"
        		+ " ON A.NODEID=S.ID WHERE A.TIME='"+maxTime+"' AND NODEID LIKE '"+nodeid+"%' AND A.ACCESSNUM !=0"
        		+ " ORDER BY ACCESSNUM DESC";
  
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * 最近一个月内主备机切换情况
     */
    public List<Map<String,Object>> selectData(String appName,String nodeName,List<Map<String,Object>> list,int index,ArrayList<String> everDateList){

        if (list.size()>=3){
             return list;
        }else if(index>everDateList.size()-2) {//14
            return list;
        }
        index ++;
        System.out.println("gg-/-/-/---/="+everDateList.get(index));

        //  返回结果：
        // NODENAME:jbs2-gw01
        //  Time:2019-05-27 16:27:50
        // 2）	查新一天public 有没有第一次切机
        String sql2=" SELECT TOP 1 NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+everDateList.get(index)+" WHERE APP_NAME = '"+appName+"'" +
                " AND STATUS = '主机' AND NODENAME != '"+nodeName+"' AND NODEID LIKE '1%' ORDER BY TIME DESC";
        List<Map<String,Object>> ListMap2=jdbcTemplate.queryForList(sql2);

        if (ListMap2.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
            String nodeName2=(String) ListMap2.get(0).get("NODENAME");
            String time2=(String) ListMap2.get(0).get("TIME");
            list.add(ListMap2.get(0));
            String pTime=everDateList.get(index).substring(0,4)+"-"+everDateList.get(index).substring(4,6)+"-"+everDateList.get(index).substring(6,8);
            // 3）	查新当天public 有没有第二次切机
            String sql3="SELECT  NODENAME,STATUS,APP_NAME,TIME FROM SHOWSERVICE_INFO_"+everDateList.get(index)+" WHERE APP_NAME = '"+appName+"' AND STATUS = '主机'"+
                    " AND NODENAME != '"+nodeName2+"' AND NODEID LIKE '1%' AND TIME BETWEEN '"+pTime+" 00:00:00' AND '"+time2+"' ORDER BY TIME DESC";
            List<Map<String,Object>> ListMap3=jdbcTemplate.queryForList(sql3);
            if (ListMap3.size()!=0){//有切机  把当时主机 的 获取切机后的主机名和时间
                list.add(ListMap3.get(0));
                //三次都有数据 返回结果集
                return list;
            }else{
                //执行下一天
                selectData(appName,nodeName2,list,index,everDateList);
            }
        }else{
            selectData(appName,nodeName,list,index,everDateList);
        }
        return list;

    }
    
	@Override
	public List<Map<String, Object>> getNodeId() {
		String time = MainUtil.getNowDateF();
		String sql="SELECT ID,NAME FROM SYSDBA.SERVER WHERE ID IN (SELECT NODEID FROM SYSDBA.TB_DB_STAT_"+time+" GROUP BY NODEID) ORDER BY ID";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	@Override
	public List<Map<String, Object>> getAppCount(String id) {
        String time= MainUtil.getNowDateF();
        System.out.println("getAppCount---"+time);
        String sql="SELECT APP_NAME FROM SHOWSERVICE_INFO_"+time+" WHERE NODEID LIKE '"+id+"%' GROUP BY APP_NAME";
  
        //sql  参数集合
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
	}
	
	@Override
	public List<Map<String, Object>> getServerCount(String id) {
		String time= MainUtil.getNowDateF();
		System.out.println("getServerCount---"+time);
		String sql="SELECT NODENAME FROM SHOWSERVICE_INFO_"+time+" WHERE NODEID LIKE '"+id+"%' GROUP BY NODENAME";
		
		//sql  参数集合
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	

	
}
