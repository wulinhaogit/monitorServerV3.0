/*
package com.kedong.controller;

import com.kedong.common.vo.AJAXJson;
import com.kedong.common.vo.ParamEntry;
import com.kedong.common.vo.XYUtil;
import com.kedong.quartz.QuartzJobManager;
import com.kedong.quartz.TimeQuartzTwo;
import com.kedong.service.EquipmentProjectService;
import com.kedong.webSocket.WebSocketOne;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

*/
/**
 * 设备专题
 *//*

@Controller
@RequestMapping("/equipment")
public class EquipmentProjectController {
    @Autowired
    private EquipmentProjectService equipmentProjectService;
    @Autowired
    private WebSocketOne webSocketOne;
    @Autowired
    private QuartzJobManager quartzJobManager;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    @RequestMapping("/hello/{name}")
    @ResponseBody
    public String hello(@PathVariable("name") String name){
        return "HelloWorld"+name;
    }

    */
/**
     * 网络连接数Top10
     *//*

    @RequestMapping("/netConcentTop10")
    @ResponseBody
    public void netConcentTop10(){

        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            List<Map<String,Object>> list=equipmentProjectService.netConcentTop10();
            Map<String,Object> map = new HashMap<>();
            map.put("网络连接数Top10",list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data= ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    */
/**
     * 内存利用率Top10
     *//*

    @RequestMapping("/memoryUtilizationTop10")
    @ResponseBody
    public void memoryUtilizationTop10(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            List<Map<String,Object>> list=
                   equipmentProjectService.memoryUtilizationTop10();
            Map<String,Object> map = new HashMap<>();
            map.put("内存利用率Top10",list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

    */
/**
     * CPU负载率 Top5
     *//*

    @RequestMapping("/cpuLoadFactorTop5")
    @ResponseBody
    public void cpuLoadFactorTop5(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            List<Map<String,Object>> list=
                   equipmentProjectService.cpuLoadFactorTop5();
            Map<String,Object> map = new HashMap<>();
            map.put("CPU负载率Top5",list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    */
/**
     *磁盘使用Top5
     *//*

    @RequestMapping("/diskUsageTop5")
    @ResponseBody
    public void diskUsageTop5(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            List<Map<String,Object>> list=
                   equipmentProjectService.diskUsageTop5();
            Map<String,Object> map = new HashMap<>();
            map.put("磁盘使用Top5",list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    */
/**
     *n网络流量IO Top10
     *//*

    @RequestMapping("/netFlowIOTop10")
    @ResponseBody
    public void netFlowIOTop10(){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            List<Map<String,Object>> list=
                   equipmentProjectService.netFlowIOTop10();
            Map<String,Object> map = new HashMap<>();
            map.put("网络流量IOTop10",list);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

    */
/**
     *重要设备详情
     *//*

    @RequestMapping("/importEquipmentDetails/{nodeid}")
    @ResponseBody
    public void importEquipmentDetails(@PathVariable Long nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("IEDJob", "IEDJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("IEDJob", "IEDJobGroup");
            }

            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzTwo.class,"IEDJob","IEDJobGroup","IEDParam",paramEntry);
            Map<String,Object> m=equipmentProjectService.importEquipmentDetails(nodeid);
            Map<String,Object> map = new HashMap<>();
            map.put("重要设备详情",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    */
/**
     *内存使用率曲线 一周的K图
     *//*

    @RequestMapping("/memoryUsageCurveOfWeek/{nodeid}")
    @ResponseBody
    public void memoryUsageCurveOfWeek(@PathVariable Long nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("MUCWJob", "MUCWJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("MUCWJob", "MUCWJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzTwo.class,"MUCWJob","MUCWJobGroup","MUCWParam",paramEntry);
            List<Map<String,Object>> list=
                    equipmentProjectService.memoryUsageCurveOfWeek(nodeid);
            Map<String, LinkedList> m= XYUtil.getXYMap(list,"TIME","DATA");

            Map<String,Object> map = new HashMap<>();
            map.put("内存使用率曲线K图",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    */
/**
     *内存使用率曲线 日曲线图  y：data   x：time
     * @param  nodeid 节点  date 某一天
     *//*

    @RequestMapping("/memoryUsageCurveOfDay/{nodeid}/{date}")
    @ResponseBody
    public void memoryUsageCurveOfDay(@PathVariable Long nodeid,@PathVariable String date){

        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("MUCDJob", "MUCDJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("MUCDJob", "MUCDJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            paramEntry.setDate(date);
            quartzJobManager.addJob(TimeQuartzTwo.class,"MUCDJob","MUCDJobGroup","MUCDParam",paramEntry);
            List<Map<String,Object>> list= equipmentProjectService.memoryUsageCurveOfDay(nodeid,date);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
            Map<String,Object> map = new HashMap<>();
            map.put("内存使用率日曲线",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }


    */
/**
     *CPU使用率曲线 一周的K图
     *//*

    @RequestMapping("/cpuUsageCurveOfWeek/{nodeid}")
    @ResponseBody
    public void cpuUsageCurveOfWeek(@PathVariable Long nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("CUCWJob", "CUCWJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("CUCWJob", "CUCWJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzTwo.class,"CUCWJob","CUCWJobGroup","CUCWParam",paramEntry);
            List<Map<String,Object>> list= equipmentProjectService.cpuUsageCurveOfWeek(nodeid);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
            Map<String,Object> map = new HashMap<>();
            map.put("CPU使用率曲线K图",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

    */
/**
     * CPU使用率曲线 日曲线图
     * @param nodeid 节点
     * @param date 日期
     *//*

    @RequestMapping("/cpuUsageCurveOfDay/{nodeid}/{date}")
    @ResponseBody
    public void cpuUsageCurveOfDay(@PathVariable Long nodeid,@PathVariable String date){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("CUCDJob", "CUCDJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("CUCDJob", "CUCDJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            paramEntry.setDate(date);
            quartzJobManager.addJob(TimeQuartzTwo.class,"CUCDJob","CUCDJobGroup","CUCDParam",paramEntry);
            List<Map<String,Object>> list= equipmentProjectService.cpuUsageCurveOfDay(nodeid,date);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
            Map<String,Object> map = new HashMap<>();
            map.put("CPU使用率日曲线",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }
    */
/**
     *磁盘使用率曲线 一周的K图
     *//*

    @RequestMapping("/diskUsageCurveOfWeek/{nodeid}")
    @ResponseBody
    public void diskUsageCurveOfWeek(@PathVariable Long nodeid){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DUCWJob", "DUCWJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DUCWJob", "DUCWJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            quartzJobManager.addJob(TimeQuartzTwo.class,"DUCWJob","DUCWJobGroup","DUCWParam",paramEntry);
            List<Map<String,Object>> list= equipmentProjectService.diskUsageCurveOfWeek(nodeid);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
            Map<String,Object> map = new HashMap<>();
            map.put("磁盘使用率曲线K图",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

    */
/**
     * 磁盘使用率曲线 日曲线图
     * @param nodeid 节点
     * @param date 日期
     *//*

    @RequestMapping("/diskUsageCurveOfDay/{nodeid}/{date}")
    @ResponseBody
    public void diskUsageCurveOfDay(@PathVariable Long nodeid,@PathVariable String date){
        AJAXJson ajaxJson=new AJAXJson();
        String data;
        try{
            //获取定时任务 判断 有无  有删除  无创建
            Scheduler scheduled =  schedulerFactoryBean.getScheduler();
            JobKey jobKey= JobKey.jobKey("DUCDJob", "DUCDJobGroup");
            JobDetail jobDetail= scheduled.getJobDetail(jobKey);
            if(jobDetail!=null){
                quartzJobManager.deleteJob("DUCDJob", "DUCDJobGroup");
            }
            //传递定时器参数
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setNodeId(nodeid);
            paramEntry.setDate(date);
            quartzJobManager.addJob(TimeQuartzTwo.class,"DUCDJob","DUCDJobGroup","DUCDParam",paramEntry);
            List<Map<String,Object>> list= equipmentProjectService.diskUsageCurveOfDay(nodeid,date);
            Map<String, LinkedList> m=XYUtil.getXYMap(list,"TIME","DATA");
            Map<String,Object> map = new HashMap<>();
            map.put("磁盘使用率日曲线",m);
            ajaxJson.setMsg("success");
            ajaxJson.setObj(map);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("操作失败");
            ajaxJson.setSuccess(false);
            data=  ajaxJson.getJsonStr();
            webSocketOne.onMessage(data);
        }
    }

}
*/
