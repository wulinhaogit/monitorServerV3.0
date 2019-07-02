package com.kedong.controller;

import com.kedong.common.vo.AJAXJson;
import com.kedong.common.vo.ParamEntry;
import com.kedong.common.vo.XYUtil;
import com.kedong.quartz.QuartzJobManager;
import com.kedong.quartz.TimeQuartzFour;
import com.kedong.quartz.TimeQuartzOne;
import com.kedong.quartz.TimeQuartzThree;
import com.kedong.service.PlatformProjectService;
import com.kedong.service.PlatformProjectServiceImpl;
import com.kedong.webSocket.WebSocketOne;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 平台专题
 */
@Controller
@RequestMapping("/platform")
@CrossOrigin
public class PlatformProjectController {
	
	public static final Logger logger= LoggerFactory.getLogger(PlatformProjectServiceImpl.class);
	
    @Autowired
    private PlatformProjectService platformProjectService;
    @Autowired
    private WebSocketOne webSocketOne;
    @Autowired
    private QuartzJobManager quartzJobManager;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    
    
    /**
     * 获取数据库节点id
     */
    @RequestMapping("/getNodeId")
    @ResponseBody
    public String getNodeId(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //今天
            List<Map<String,Object>> list=platformProjectService.getNodeId();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("数据库所在节点", list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
            return data;
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            return data;
        }
    }
   
    /**
     * midhs访问次数曲线
     *   MidhsAccessNumCurve
     */
    @RequestMapping("/midhsAccessNumCurve")
    @ResponseBody
    public void midhsAccessNumCurve(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{

            //今天
            List<Map<String,Object>> tdList=platformProjectService.midhsAccessNumCurveToday();
            Map<String, LinkedList> tdm=XYUtil.getXYMap(tdList,"TIME","ACCESSNUM");
            Map<String,Object> m = new HashMap<>();
            m.put("midhs访问次数今日曲线",tdm);
            //今天
            List<Map<String,Object>> ydList=platformProjectService.midhsAccessNumCurveYesterday();
            Map<String, LinkedList> ydm=XYUtil.getXYMap(ydList,"TIME","ACCESSNUM");
            m.put("midhs访问次数昨日曲线",ydm);
            
           /*//周
            List<Map<String,Object>> wList=platformProjectService.midhsAccessNumCurveWeek();
            Map<String, LinkedList> wm=XYUtil.getXYMap(wList,"TIME","ACCESSNUM");
            m.put("midhs访问次数周曲线",wm);
            //月
            List<Map<String,Object>> mList=platformProjectService.midhsAccessNumCurveMonth();
            Map<String, LinkedList> mm=XYUtil.getXYMap(mList,"TIME","ACCESSNUM");
            m.put("midhs访问次数月曲线",mm);*/
            Map<String,Object> map = new HashMap<>();
            map.put("midhs访问次数曲线",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

      /**
     * midhs延时曲线
     * delayCurve
     */
    @RequestMapping("/midhsDelayCurve")
    @ResponseBody
    public void midhsDelayCurve(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //天
        List<Map<String,Object>> dList=platformProjectService.midhsDelayCurveToday();
        Map<String, LinkedList> dm=XYUtil.getXYMap(dList,"TIME","ACCESSNUM");
        Map<String,Object> m = new HashMap<>();
        m.put("midhs延时今日日曲线",dm);
        List<Map<String,Object>> yList=platformProjectService.midhsDelayCurveYesterday();
        Map<String, LinkedList> wm=XYUtil.getXYMap(yList,"TIME","ACCESSNUM");
        m.put("midhs延时昨日线",wm);
        /* //月
        List<Map<String,Object>> mList=platformProjectService.midhsDelayCurveMonth();
        Map<String, LinkedList> mm=XYUtil.getXYMap(mList,"TIME","ACCESSNUM");
        m.put("midhs延时月曲线",mm);*/
        Map<String,Object> map = new HashMap<>();
        map.put("midhs延时曲线",m);
        ajaxJson.setMsg("success");
        ajaxJson.setObj(map);
        data =  ajaxJson.getJsonStr();
        webSocketOne.onMessage(data);
        }catch (Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

    /**
     *
             * 应用主备切换情况
     * appOfPrimaryAndStandbySwitch
     */
    @RequestMapping("/appPrimaryAndStaSwitch")
    @ResponseBody
    public void appPrimaryAndStaSwitch(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //scada
            List<Map<String,Object>> listSca=platformProjectService.appPrimaryAndStaSwitchSCA();
            List<Map<String,Object>> listData=platformProjectService.appPrimaryAndStaSwitchData();
            List<Map<String,Object>> listFes=platformProjectService.appPrimaryAndStaSwitchFes();
            List<Map<String,Object>> listPas=platformProjectService.appPrimaryAndStaSwitchPas();
            List<Map<String,Object>> listPublic=platformProjectService.appPrimaryAndStaSwitchPublic();
            List<Map<String,Object>> listWams=platformProjectService.appPrimaryAndStaSwitchWams();
            Map<String,Object> resultMap= new HashMap<>();
            resultMap.put("scada",listSca);
            resultMap.put("data_srv",listData);
            resultMap.put("fes",listFes);
            resultMap.put("pas_rtnet",listPas);
            resultMap.put("public",listPublic);
            resultMap.put("wams",listWams);
            List<String> appName=new ArrayList<>();
            appName.add("scada");
            appName.add("data_srv");
            appName.add("fes");
            appName.add("pas_rtnet");
            appName.add("public");
            appName.add("wams");
            resultMap.put("appName",appName);
            Map<String,Object> map = new HashMap<>();
            map.put("应用主备切换情况",resultMap);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            System.out.println("应用主备切换情况++++:"+data);
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 平台应用统计
     * appMonitorList  
     * id 1 2 3
     */
    @RequestMapping("/appCountList")
    @ResponseBody
    public void appCountList(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            Map<String,Object> m = new HashMap<>();
            List<Map<String,Object>> list1=platformProjectService.appCountList1();
            m.put("平台应用统计1",list1);
            List<Map<String,Object>> list2=platformProjectService.appCountList2();
            m.put("平台应用统计2",list2);
            List<Map<String,Object>> list3=platformProjectService.appCountList3();
            m.put("平台应用统计3",list3);
            //list.subList(0,200);
            List<Map<String,Object>> listAppNum1=platformProjectService.getAppCount("1");
    		m.put("当天监视应用个数1", listAppNum1.size());
    		List<Map<String,Object>> listAppNum2=platformProjectService.getAppCount("2");
    		m.put("当天监视应用个数2", listAppNum2.size());
    		List<Map<String,Object>> listAppNum3=platformProjectService.getAppCount("3");
    		m.put("当天监视应用个数3", listAppNum3.size());
    		//list.subList(0,200);
    		List<Map<String,Object>> listServerNum1=platformProjectService.getServerCount("1");
    		m.put("当天监视服务主机个数1", listServerNum1.size());
    		List<Map<String,Object>> listServerNum2=platformProjectService.getServerCount("2");
    		m.put("当天监视服务主机个数2", listServerNum2.size());
    		List<Map<String,Object>> listServerNum3=platformProjectService.getServerCount("3");
    		m.put("当天监视服务主机个数3", listServerNum3.size());
    		
            Map<String,Object> map = new HashMap<>();
            map.put("平台应用统计",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            System.out.println("平台统计----"+data);
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 应用监视列表
     * appMonitorList  
     * id 1 2 3
     */
    @RequestMapping("/appMonitorList")
    @ResponseBody
    public void appMonitorList(){
    	AJAXJson ajaxJson=new AJAXJson();
    	String data;
    	try{
    		List<Map<String,Object>> list=platformProjectService.appMonitorList();
    		//list.subList(0,200);
    		Map<String,Object> map = new HashMap<>();
    		map.put("应用监视列表",list);
    		ajaxJson.setMsg("success");
    		ajaxJson.setObj(map);
    		data=  ajaxJson.getJsonStr();
    		System.out.println(data);
    		webSocketOne.onMessage(data);
    	}catch(Exception e){
    		logger.debug(e.getMessage());
    		e.printStackTrace();
    		ajaxJson.setMsg("操作失败");
    		ajaxJson.setSuccess(false);
    		data=  ajaxJson.getJsonStr();
    		webSocketOne.onMessage(data);
    	}
    }
/**
 * 进程状态监视列表
 * processStatusMonitor
 */
  @RequestMapping("/procStatusMonitor")
  @ResponseBody
   public void procStatusMonitor(){
       AJAXJson ajaxJson=new AJAXJson();
       String data;
     try{
         List<Map<String,Object>> list=platformProjectService.procStatusMonitor();
         //list =list.subList(0,200);
         Map<String,Object> map = new HashMap<>();
         map.put("进程状态监视列表",list);
         ajaxJson.setMsg("success");
         ajaxJson.setObj(map);
         data=  ajaxJson.getJsonStr();
         System.out.println(data);
         webSocketOne.onMessage(data);
     }catch(Exception e){
         logger.debug(e.getMessage());
         e.printStackTrace();
         ajaxJson.setMsg("操作失败");
         ajaxJson.setSuccess(false);
         data=  ajaxJson.getJsonStr();
         webSocketOne.onMessage(data);
     }
   }

  /*  *//**
      * 当前进程状态CPU使用率Top3
     *//*
    @RequestMapping("/procStateCPUnowTop3")
    @ResponseBody
    public void procStateCPUTop3(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            List<Map<String,Object>> tList=platformProjectService.procStateCPUnowTop3();
            //3个进程名
            LinkedList<String> nList =  XYUtil.getProcName(tList); //进程名
            LinkedList<String> sList =  XYUtil.getNodeName(tList);//节点ID
            Map<String, LinkedList> tm= XYUtil.getXXYMap(tList,"PROC_NAME","NAME","CPU_PRECENT");
            Map<String,Object> totalMap = new HashMap<>();
            totalMap.put("当前进程状态CPU使用率Top3",tm);

            //最近一小时进程状态CPU使用率平均值
            List<Map<String,Object>> hList=platformProjectService.procStateCPUAvgHour(nList,sList);
            Map<String, LinkedList> hm=XYUtil.getXXYMap(hList,"PROC_NAME","NAME","AVG_PRECENT");
            totalMap.put("最近一小时进程状态CPU使用率平均值",hm);
            //日
            List<Map<String,Object>> dList=platformProjectService.procStateCPUDay(nList,sList);
            Map<String,Object> dm=XYUtil.getTreeXYMap(dList,nList,sList,"TIME","CPU_PRECENT");
            totalMap.put("当前进程状态CPU使用率日曲线",dm);
            //周
           // List<Map<String,Object>> wList=platformProjectService.procStateCPUWeek(nList,sList);
           // Map<String,Object> wMap=XYUtil.getTreeXYMap(wList,nList,"TIME","CPU_PRECENT");
           // totalMap.put("当前进程状态CPU使用率周曲线",wMap);
            //总封装
           Map<String,Object> map=new HashMap<>();
           map.put("当前进程状态CPU使用率",totalMap);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            System.out.println(" 当前进程状态CPU使用率Top3-----------"+data );
            webSocketOne.onMessage(data);
        }catch(Exception e){
              logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }


    *//**
     * 进程状态内存占用数Top3
     * ProcSateMemoryFotTop3
     *//*
    @RequestMapping("/procSateMemoryFotTop3")
    @ResponseBody
    public void procSateMemoryFotTop3(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            List<Map<String,Object>> tList=platformProjectService.procSateMemoryFotTop3();
            //3个进程名
            LinkedList<String> nList =  XYUtil.getProcName(tList);
            LinkedList<String> sList =  XYUtil.getNodeName(tList);//节点ID
            Map<String, LinkedList> tm= XYUtil.getXXYMap(tList,"PROC_NAME","NAME","MEM_OCCUPY");
            Map<String,Object> totalMap = new HashMap<>();
            totalMap.put("进程状态内存占用数Top3",tm);
            //最近一小时进程状态内存占用数平均值
            List<Map<String,Object>> hList=platformProjectService.procSateMemoryAvgHour(nList,sList);
            Map<String, LinkedList> hm=XYUtil.getXXYMap(hList,"PROC_NAME","NAME","AVG_OCCUPY");
            totalMap.put("最近一小时进程状态内存占用数平均值",hm);
            //日
            List<Map<String,Object>> dList=platformProjectService.procSateMemoryFotDay(nList,sList);
            Map<String, Object> dm=XYUtil.getTreeXYMap(dList,nList,sList,"TIME","MEM_OCCUPY");
            totalMap.put("进程状态内存占用数Top3日曲线",dm);
            //周
            //List<Map<String,Object>> wList=platformProjectService.procSateMemoryFotWeek(nList);
            //Map<String,Object> wMap=XYUtil.getTreeXYMap(wList,nList,"TIME","MEM_OCCUPY");
            //totalMap.put("进程状态内存占用数Top3周曲线",wMap);
            //总封装
            Map<String,Object> map=new HashMap<>();
            map.put("进程状态内存占用数",totalMap);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
              logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

    *//**
     * 进程状态线程个数Top3
     * numOfOrocessStateThreadsTop3
     *//*
    @RequestMapping("/numOfOrocessStateThreadsTop3")
    @ResponseBody
    public void numOfOrocessStateThreadsTop3(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            List<Map<String,Object>> tList=platformProjectService.numOfOrocessStateThreadsTop3();
            //3个进程名
            LinkedList<String> nList =  XYUtil.getProcName(tList);
            LinkedList<String> sList =  XYUtil.getNodeName(tList);//节点ID
            Map<String, LinkedList> tm= XYUtil.getXXYMap(tList,"PROC_NAME","NAME","THREAD_NUM");
            Map<String,Object> totalMap = new HashMap<>();
            totalMap.put("进程状态线程个数Top3",tm);
            //最近一小时进程状态内存占用数平均值
           // List<Map<String,Object>> hList=platformProjectService.numOfOrocessStateThreadAvgHour(nList);
           // Map<String, LinkedList> hm=XYUtil.getXXYMap(hList,"PROC_NAME","NAME","AVG_NUM");
          //  totalMap.put("最近一小时进程状态线程个数平均值",hm);
            //日
            List<Map<String,Object>> dList=platformProjectService.numOfOrocessStateThreadsDay(nList,sList);
            Map<String, Object> dm= XYUtil.getTreeXYMap(dList,nList,sList,"TIME","THREAD_NUM");
            totalMap.put("进程状态线程个数Top3日曲线",dm);
            //周
          //  List<Map<String,Object>> wList=platformProjectService.numOfOrocessStateThreadsWeek(nList);
          //  Map<String,Object> wMap=XYUtil.getTreeXYMap(wList,nList,"TIME","THREAD_NUM");
          //  totalMap.put("进程状态线程个数Top3周曲线",wMap);
            //总封装
            Map<String,Object> map=new HashMap<>();
            map.put("进程状态线程个数",totalMap);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data = ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
              logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

    *//**
     * 进程状态磁盘IO读增量Top3
     * Process Status Disk IO Read-Write Increment Top3
     * 不知道怎么封装
     *//*
    @RequestMapping("/procStatusDiskIOReadIncrementTop3")
    @ResponseBody
    public void procStatusDiskIOReadIncrementTop3() {
        AJAXJson ajaxJson = new AJAXJson();
        String data ;
        try {
            List<Map<String, Object>> tList = platformProjectService.processStatusDiskIOReadIncrementTop3();
            //3个进程名
            LinkedList<String> nList = XYUtil.getProcName(tList);
            LinkedList<String> sList =  XYUtil.getNodeName(tList);//节点ID
            Map<String, LinkedList> tm=XYUtil.getXXYMap(tList,"PROC_NAME","NAME","DISK_READ");
            Map<String, Object> totalMap = new HashMap<>();
            totalMap.put("进程状态磁盘IO读增量Top3", tm);
            //日
            List<Map<String, Object>> dList = platformProjectService.processStatusDiskIOReadIncrementDay(nList,sList);
            Map<String, Object> dm=XYUtil.getTreeXYMap(dList,nList,sList,"TIME","DISK_READ");
            totalMap.put("进程状态磁盘IO读增量Top3日曲线", dm);
            //周
            List<Map<String, Object>> wList = platformProjectService.processStatusDiskIOReadIncrementWeek(nList,sList);
            Map<String,Object> wm=XYUtil.getTreeXYMap(wList,nList,sList,"TIME","DISK_READ");
            totalMap.put("进程状态磁盘IO读增量Top3周曲线", wm);
            //总封装
            Map<String, Object> map =new HashMap<>() ;
            map.put("进程状态磁盘IO读增量",totalMap);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data = ajaxJson.getJsonStr();
            System.out.println("进程状态磁盘IO读增量Top3日曲线"+data);
            webSocketOne.onMessage(data);
        } catch (Exception e) {
              logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data = ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

    *//**
     * 进程状态磁盘IO写增量Top3
     * Process Status Disk IO Read-Write Increment Top3
     * 不知道怎么封装
     *//*
    @RequestMapping("/procStatusDiskIOWriteIncrementTop3")
    @ResponseBody
    public void procStatusDiskIOWriteIncrementTop3() {
        AJAXJson ajaxJson = new AJAXJson();
        String data ;
        try {
            List<Map<String, Object>> tList = platformProjectService.processStatusDiskIOWriteIncrementTop3();
            //3个进程名
            LinkedList<String> nList = XYUtil.getProcName(tList);
            LinkedList<String> sList =  XYUtil.getNodeName(tList);//节点ID
            Map<String, LinkedList> tm=XYUtil.getXXYMap(tList,"PROC_NAME","NAME","DISK_WRITE");
            Map<String, Object> totalMap = new HashMap<>();
            totalMap.put("进程状态磁盘IO写增量Top3", tm);
            //日
            List<Map<String, Object>> dList = platformProjectService.processStatusDiskIOWriteIncrementDay(nList,sList);
            Map<String, Object> dm=XYUtil.getTreeXYMap(dList,nList,sList,"TIME","DISK_WRITE");
            totalMap.put("进程状态磁盘IO写增量Top3日曲线", dm);
            //周
            List<Map<String, Object>> wList = platformProjectService.processStatusDiskIOWriteIncrementWeek(nList,sList);
            Map<String,Object> wm=XYUtil.getTreeXYMap(wList,nList,sList,"TIME","DISK_WRITE");
            totalMap.put("进程状态磁盘IO写增量Top3周曲线", wm);
            //总封装
            Map<String, Object> map =new HashMap<>() ;
            map.put("进程状态磁盘IO写增量",totalMap);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data = ajaxJson.getJsonStr();
            System.out.println("进程状态磁盘IO写增量Top3日曲线"+data);
            webSocketOne.onMessage(data);
        } catch (Exception e) {
              logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data = ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }*/
    /**
     * 数据库所在节点CPU利用率曲线  K图 周
     * databaseCPUUtilizationCurve
     */
    @RequestMapping("/dataCPUUtilCurveWeek/{nodeid}")
    @ResponseBody
    public void dataCPUUtilCurveWeek(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DCUCWJob", "DCUCWJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DCUCWJob", "DCUCWJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzThree.class,"DCUCWJob","DCUCWJobGroup","DCUCWParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.dataCPUUtilCurveWeek(nodeid);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
            Map<String,Object> map = new HashMap<>();
            map.put("数据库节点CPU利用率曲线K图",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库所在节点的CPU利用率曲线  
     * databaseCPUUtilizationCurve
     */
    @RequestMapping("/dataCPUUtilCurveDay/{nodeid}")
    @ResponseBody
    public void dataCPUUtilCurveDay(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data=null;
        try{
            //获取定时任务 判断 有无  有删除  无创建
        	Map<String,Object> m = new HashMap<>();
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DCUCDJob", "DCUCDJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DCUCDJob", "DCUCDJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzThree.class,"DCUCDJob","DCUCDJobGroup","DCUCDParam",paramEntry);
            List<Map<String,Object>> tList= platformProjectService.dataCPUUtilCurveToday(nodeid);
            Map<String, LinkedList> tm=XYUtil.getXYMap(tList,"TIME","DATA");
            m.put("数据库节点CPU利用率今日曲线",tm);
            List<Map<String,Object>> yList= platformProjectService.dataCPUUtilCurveYesterday(nodeid);
            Map<String, LinkedList> ym=XYUtil.getXYMap(yList,"TIME","DATA");
            m.put("数据库节点CPU利用率昨日曲线",ym);
            Map<String,Object> map = new HashMap<>();
            map.put("数据库节点CPU利用率曲线",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库所在节点内存利用率  K图 周
     * DataMemoryUtilCurve
     */
    @RequestMapping("/dataMemoryUtilCurveWeek/{nodeid}")
    @ResponseBody
    public void dataMemoryUtilCurveWeek(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DMUCWJob", "DMUCWJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DMUCWJob", "DMUCWJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzThree.class,"DMUCWJob","DMUCWJobGroup","DMUCWParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.dataMemoryUtilCurveWeek(nodeid);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
            Map<String,Object> map = new HashMap<>();
            map.put("数据库节点内存利用率曲线K图",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库所在节点内存利用率曲线  
     * dataMemoryUtilCurveDay
     */
    @RequestMapping("/dataMemoryUtilCurveDay/{nodeid}")
    @ResponseBody
    public void dataMemoryUtilCurveDay(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DMUCDJob", "DMUCDJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DMUCDJob", "DMUCDJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            Map<String,Object> m = new HashMap<>();
            quartzJobManager.addJob(TimeQuartzThree.class,"DMUCDJob","DMUCDJobGroup","DMUCDParam",paramEntry);
            List<Map<String,Object>> tList= platformProjectService.dataMemoryUtilCurveToday(nodeid);
            Map<String, LinkedList> tm=XYUtil.getXYMap(tList,"TIME","DATA");
            m.put("数据库节点内存利用率今日曲线",tm);
            List<Map<String,Object>> yList= platformProjectService.dataMemoryUtilCurveYesterDay(nodeid);
            Map<String, LinkedList> ym=XYUtil.getXYMap(yList,"TIME","DATA");
            m.put("数据库节点内存利用率昨日曲线",ym);
            Map<String,Object> map = new HashMap<>();
            map.put("数据库节点内存利用率曲线  ", m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库IO曲线  K图 周
     * DataMemoryUtilCurve
     */
    @RequestMapping("dataIOCurveWeek/{nodeid}")
    @ResponseBody
    public void dataIOCurveWeek(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DIOCWJob", "DIOCWJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DIOCWJob", "DIOCWJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzThree.class,"DIOCWJob","DIOCWJobGroup","DIOCWParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.dataIOCurveWeek(nodeid);
            Map<String, Object> m=XYUtil.getXYYMap(list,"STAT_TIME","READ_BYTES","WRITE_BYTES");
            Map<String,Object> map = new HashMap<>();
            map.put("数据库IO曲线K图",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     *数据库IO曲线 
     * dataIOCurveDay
     */
    @RequestMapping("/dataIOCurveDay/{nodeid}")
    @ResponseBody
    public void dataIOCurveDay(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
           
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DIOCDJob", "DIOCDJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DIOCDJob", "DIOCDJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            Map<String,Object> m = new HashMap<>();
            quartzJobManager.addJob(TimeQuartzThree.class,"DIOCDJob","DIOCDJobGroup","DIOCDParam",paramEntry);
            List<Map<String,Object>> tList= platformProjectService.dataIOCurveToday(nodeid);
            Map<String, Object> tm=XYUtil.getXYYMap(tList,"STAT_TIME","READ_BYTES","WRITE_BYTES");
            m.put("数据库IO今日曲线",tm);
            List<Map<String,Object>> yList= platformProjectService.dataIOCurveYesterDay(nodeid);
            Map<String, Object> ym=XYUtil.getXYYMap(yList,"STAT_TIME","READ_BYTES","WRITE_BYTES");
            m.put("数据库IO昨日曲线",ym);
            Map<String,Object> map = new HashMap<>();
            map.put("数据库IO曲线", m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库所在节点的磁盘利用率曲线  K图 周
     * dataDiskCurveWeek
     */
    @RequestMapping("dataDiskCurveWeek/{nodeid}")
    @ResponseBody
    public void dataDiskCurveWeek(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DDCWJob", "DDCWJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DDCWJob", "DDCWJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzThree.class,"DDCWJob","DDCWJobGroup","DDCWParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.dataDiskCurveWeek(nodeid);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
            Map<String,Object> map = new HashMap<>();
            map.put("数据库所在节点的磁盘利用率K图",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库说在节点的磁盘IO读曲线
     * dataDiskCurveDay
     */
    @RequestMapping("/dataDiskReadIOCurve/{nodeid}")
    @ResponseBody
    public void dataDiskReadIOCurve(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DDRIOCJob", "DDRIOCJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DDRIOCJob", "DDRIOCJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzThree.class,"DDRIOCJob","DDRIOCJobGroup","DDRIOCParam",paramEntry);
            Map<String,Object> m = new HashMap<>();
            List<Map<String,Object>> tList= platformProjectService.dataDiskReadCurveToday(nodeid);
            Map<String, LinkedList> tm=XYUtil.getXYMap(tList,"TIME","DATA");
            m.put("数据库节点磁盘IO读今日曲线",tm);
            List<Map<String,Object>> yList= platformProjectService.dataDiskReadCurveYesterday(nodeid);
            Map<String, LinkedList> ym=XYUtil.getXYMap(yList,"TIME","DATA");
            m.put("数据库节点磁盘IO读昨日曲线",ym);
            Map<String,Object> map = new HashMap<>();
            map.put("数据库节点磁盘IO读曲线", m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库说在节点的磁盘IO写曲线
     * dataDiskCurveDay
     */
    @RequestMapping("/dataDiskWriteIOCurve/{nodeid}")
    @ResponseBody
    public void dataDiskWriteIOCurve(@PathVariable String nodeid){
    	AJAXJson ajaxJson=new AJAXJson();
    	String data;
    	try{
    		//获取定时任务 判断 有无  有删除  无创建
    		Scheduler scheduled =  schedulerFactoryBean.getScheduler();
    		JobKey jobKey= JobKey.jobKey("DDWIOCJob", "DDWIOCJobGroup");
    		JobDetail jobDetail= scheduled.getJobDetail(jobKey);
    		if(jobDetail!=null){
    			quartzJobManager.deleteJob("DDWIOCJob", "DDWIOCJobGroup");
    		}
    		//传递定时器参数
    		ParamEntry paramEntry = new ParamEntry();
    		paramEntry.setNodeId(nodeid);
    		quartzJobManager.addJob(TimeQuartzThree.class,"DDWIOCJob","DDWIOCJobGroup","DDWIOCParam",paramEntry);
    		Map<String,Object> m = new HashMap<>();
    		List<Map<String,Object>> tList= platformProjectService.dataDiskWriteCurveToday(nodeid);
    		Map<String, LinkedList> tm=XYUtil.getXYMap(tList,"TIME","DATA");
    		m.put("数据库节点磁盘IO写今日曲线",tm);
    		List<Map<String,Object>> yList= platformProjectService.dataDiskWriteCurveYesterday(nodeid);
    		Map<String, LinkedList> ym=XYUtil.getXYMap(yList,"TIME","DATA");
    		m.put("数据库节点磁盘IO写昨日曲线",ym);
    		List<Map<String,Object>> wList= platformProjectService.dataDiskWriteCurveWeek(nodeid);
    		Map<String, LinkedList> wm=XYUtil.getXYMap(wList,"TIME","DATA");
    		m.put("数据库节点磁盘IO写周曲线",wm);
    		Map<String,Object> map = new HashMap<>();
    		map.put("数据库节点磁盘IO写曲线", m);
    		ajaxJson.setMsg("success");
    		ajaxJson.setObj(map);
    		data=  ajaxJson.getJsonStr();
    		webSocketOne.onMessage(data);
    	}catch(Exception e){
    		logger.debug(e.getMessage());
    		 e.printStackTrace();
    		ajaxJson.setMsg("操作失败");
    		ajaxJson.setSuccess(false);
    		data=  ajaxJson.getJsonStr();
    		webSocketOne.onMessage(data);
    	}
    }
    /**
     * 数据库说在节点的磁盘IO写曲线 K图
     * dataDiskCurveDay
     */
    @RequestMapping("/dataDiskWriteIOCurveWeek/{nodeid}")
    @ResponseBody
    public void dataDiskWriteIOCurveWeek(@PathVariable String nodeid){
    	AJAXJson ajaxJson=new AJAXJson();
    	String data;
    	try{
    		//获取定时任务 判断 有无  有删除  无创建
    		Scheduler scheduled =  schedulerFactoryBean.getScheduler();
    		JobKey jobKey= JobKey.jobKey("DDWIOCWJob", "DDWIOCWJobGroup");
    		JobDetail jobDetail= scheduled.getJobDetail(jobKey);
    		if(jobDetail!=null){
    			quartzJobManager.deleteJob("DDWIOCWJob", "DDWIOCWJobGroup");
    		}
    		//传递定时器参数
    		ParamEntry paramEntry = new ParamEntry();
    		paramEntry.setNodeId(nodeid);
    		quartzJobManager.addJob(TimeQuartzFour.class,"DDWIOCWJob","DDWIOCWJobGroup","DDWIOCWParam",paramEntry);
    		List<Map<String,Object>> list= platformProjectService.dataDiskWriteCurveWeek(nodeid);
    		Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
    		Map<String,Object> map = new HashMap<>();
    		map.put("数据库节点磁盘IO写曲线K图", m);
    		ajaxJson.setMsg("success");
    		ajaxJson.setObj(map);
    		data=  ajaxJson.getJsonStr();
    		webSocketOne.onMessage(data);
    	}catch(Exception e){
    		logger.debug(e.getMessage());
    		e.printStackTrace();
    		ajaxJson.setMsg("操作失败");
    		ajaxJson.setSuccess(false);
    		data=  ajaxJson.getJsonStr();
    		webSocketOne.onMessage(data);
    	}
    }


    /**
     *磁盘分区空间列表
     * diskPartSpaceList
     */
    @RequestMapping("diskPartSpaceList/{nodeid}")
    @ResponseBody
    public void diskPartSpaceList(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("FPSLJob", "FPSLJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("FPSLJob", "FPSLJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzFour.class,"FPSLJob","FPSLJobGroup","FPSLParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.diskPartSpaceList(nodeid);
            Map<String,Object> map = new HashMap<>();
            map.put("磁盘分区空间列表",list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
  /**
     * 数据库详情
     * databaseDetails
     */
    @RequestMapping("databaseDetails/{nodeid}")
    @ResponseBody
    public void databaseDetails(@PathVariable String nodeid){
        System.out.println(nodeid);
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DDJob", "DDJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DDJob", "DDJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzFour.class,"DDJob","DDJobGroup","DDParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.databaseDetails(nodeid);
            Map<String,Object> map = new HashMap<>();
            map.put("数据库详情",list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
  
    /**
     * 数据库模式会话数曲线
     *  dataSessionNumberCurve
     */
    @RequestMapping("dataSessionNumberCurve/{nodeid}")
    @ResponseBody
    public void dataSessionNumberCurve(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DSNCJob", "DSNCJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DSNCJob", "DSNCJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzFour.class,"DSNCJob","DSNCJobGroup","DSNCParam",paramEntry);
            List< Map<String,Object>> listName= platformProjectService.getUserName(nodeid);
            Map<String,Object> m = new HashMap<>();
            List<Map<String,Object>> tList=platformProjectService.dataSessionNumberTodayCurve(nodeid);
            Map<String, Object> tm=XYUtil.getTreeXYMap(tList, listName, "TIME","SESSION");
           // m.put("数据库模式会话数今日曲线",tm);
          /*  List<Map<String,Object>> yList=platformProjectService.dataSessionNumberYesterdayCurve(nodeid);
            Map<String, LinkedList> ym=XYUtil.getXYMap(yList,"STAT_TIME","SESSION_COUNT");
            m.put("数据库会话数昨日曲线",ym);*/
            Map<String,Object> map = new HashMap<>();
            map.put("数据库模式会话数曲线",tm);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库内存变化曲线  
     *  oneHourAccessOf atabase
     */
    @RequestMapping("dataMemoryChangeCurve/{nodeid}")
    @ResponseBody
    public void dataMemoryChangeCurve(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DMCCJob", "DMCCJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DMCCJob", "DMCCJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzFour.class,"DMCCJob","DMCCJobGroup","DMCCParam",paramEntry);
            Map<String,Object> m = new HashMap<>();
            List<Map<String,Object>> tList=platformProjectService.dataMemoryChangeTodayCurve(nodeid);
            Map<String, LinkedList> tm=XYUtil.getXYMap(tList,"TIME","DATA");
            m.put("数据库内存变化今日曲线",tm);
            List<Map<String,Object>> yList=platformProjectService.dataMemoryChangeYesterdayCurve(nodeid);
            Map<String, LinkedList> ym=XYUtil.getXYMap(yList,"TIME","DATA");
            m.put("数据库内存变化昨日曲线",ym);
            Map<String,Object> map = new HashMap<>();
            map.put("数据库内存变化曲线",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库所在节点网络IO
     *  databaseNetworkIO
     */
    @RequestMapping("databaseNetworkIO/{nodeid}")
    @ResponseBody
    public void databaseNetworkIO(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DNIOJob", "DNIOJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DNIOJob", "DNIOJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzFour.class,"DNIOJob","DNIOJobGroup","DNIOParam",paramEntry);
            Map<String,Object> m = new HashMap<>();
            List<Map<String,Object>> tList=platformProjectService.databaseNetworkIOToday(nodeid);
            Map<String, Object> tm=XYUtil.getXYYMap(tList,"STAT_TIME","RX_KBYTES","TX_KBYTES");
            m.put("数据库节点网络IO今日曲线", tm);
            List<Map<String,Object>> yList=platformProjectService.databaseNetworkIOYesterday(nodeid);
            Map<String, Object> ym=XYUtil.getXYYMap(yList,"STAT_TIME","RX_KBYTES","TX_KBYTES");
            m.put("数据库节点网络IO昨日曲线", ym);
           // List<Map<String,Object>> yCountList=platformProjectService.databaseNetworkIOYesterdayCount(nodeid);
            //m.put("数据库节点网络IO昨日总量", yCountList);
            Map<String,Object> map = new HashMap<>();
            map.put("数据库节点网络IO",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库表空间使用率曲线
     *   dataTableSpaceUsageCurve
     */
  /*  @RequestMapping("dataTableSpaceUsageCurve/{nodeid}")
    @ResponseBody
    public void dataTableSpaceUsageCurve(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DTSUCJob", "DTSUCJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DTSUCJob", "DTSUCJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzFour.class,"DTSUCJob","DTSUCJobGroup","DTSUCParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.dataTableSpaceUsageCurve(nodeid);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TP_NAME","USE_RATIO");
            Map<String,Object> map = new HashMap<>();
            map.put("数据库表空间使用率曲线",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
        *//**
         * 告警统计
         *  Alarm Statistics
         *//*
        @RequestMapping("alarmStatistics")
        @ResponseBody
        public void alarmStatistics(){
            AJAXJson ajaxJson=new AJAXJson();
            String data;
            try{
                List<Map<String,Object>> list=platformProjectService.alarmStatistics();
                Map<String, LinkedList> m=XYUtil.getXYMap(list,"NUM","NAME");
                Map<String,Object> map = new HashMap<>();
                map.put("告警统计",m);
                ajaxJson.setMsg("success");
                ajaxJson.setObj(map);
                data=  ajaxJson.getJsonStr();
                webSocketOne.onMessage(data);
            }catch(Exception e){
                logger.debug(e.getMessage());
                ajaxJson.setMsg("操作失败");
                ajaxJson.setSuccess(false);
                data=  ajaxJson.getJsonStr();
                webSocketOne.onMessage(data);
            }
        }
*/
    
    /**
     * 数据库实时连接数柱图
     * DataRealtimeConnectNumber
     * nodeid  1,2,3
     */
    @RequestMapping("/dataRealtimeConnectNumber/{nodeid}")
    @ResponseBody
    public void DataRealtimeConnectNumber(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DRCNJob", "DRCNJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DRCNJob", "DRCNJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzFour.class,"DRCNJob","DRCNJobGroup","DRCNParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.DataRealtimeConnectNumber(nodeid);
            Map<String,Object> map = new HashMap<>();
            map.put("数据库实时连接数柱图",list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    /**
     * 数据库客户端一小时访问量
     *  oneHourAccessOf atabase
     *  nodeid  1,2,3
     */
    @RequestMapping("/oneHourAccessOf/{nodeid}")
    @ResponseBody
    public void oneHourAccessOf(@PathVariable String nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("OHAOJob", "OHAOJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("OHAOJob", "OHAOJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzFour.class,"OHAOJob","OHAOJobGroup","OHAOParam",paramEntry);
            List<Map<String,Object>> list=platformProjectService.oneHourAccessOf(nodeid);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"NAME","NUM");
            Map<String,Object> map = new HashMap<>();
            map.put("数据库客户端一小时访问量",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            logger.debug(e.getMessage());
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

}
