/*
package com.kedong.quartz;


import com.kedong.common.vo.ParamEntry;
import com.kedong.controller.EquipmentProjectController;
import com.kedong.controller.PlatformProjectController;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;


*/
/**
 * 石英钟定时执行任务的类
 * @author JTT
 *
 *//*

@Configuration//交给springBoot 管理
public class TimeQuartzTwo extends QuartzJobBean{

	 @Autowired
	// private EquipmentProjectController equipmentProjectController;



	*/
/**
	 * 执行任务的方法
	 *//*

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		ParamEntry paramEntry1 = (ParamEntry) dataMap.get("IEDParam");
		if (paramEntry1!=null) {
			System.out.println("获取参数" + paramEntry1.getNodeId());
			equipmentProjectController.importEquipmentDetails(paramEntry1.getNodeId());
		}
		ParamEntry paramEntry2 = (ParamEntry) dataMap.get("MUCWParam");
		if (paramEntry2!=null)
		   equipmentProjectController.memoryUsageCurveOfWeek(paramEntry2.getNodeId());
		ParamEntry paramEntry3 = (ParamEntry) dataMap.get("MUCDParam");
		if (paramEntry3!=null)
		   equipmentProjectController.memoryUsageCurveOfDay(paramEntry3.getNodeId(),paramEntry3.getDate());
		ParamEntry paramEntry4 = (ParamEntry) dataMap.get("CUCWParam");
		if (paramEntry4!=null)
		   equipmentProjectController.cpuUsageCurveOfWeek(paramEntry4.getNodeId());
		ParamEntry paramEntry5 = (ParamEntry) dataMap.get("CUCDParam");
		if (paramEntry5!=null)
		   equipmentProjectController.cpuUsageCurveOfDay(paramEntry5.getNodeId(),paramEntry5.getDate());
		ParamEntry paramEntry6 = (ParamEntry) dataMap.get("DUCWParam");
		if (paramEntry6!=null)
		   equipmentProjectController.diskUsageCurveOfWeek(paramEntry6.getNodeId());
		ParamEntry paramEntry7 = (ParamEntry) dataMap.get("DUCDParam");
		if (paramEntry7!=null)
		   equipmentProjectController.diskUsageCurveOfDay(paramEntry7.getNodeId(),paramEntry7.getDate());


		System.out.println("定时任务2的开始！！！！！！");
	}



}
*/
