package com.kedong.quartz;


import com.kedong.common.vo.ParamEntry;
import com.kedong.controller.PlatformProjectController;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * 石英钟定时执行任务的类
 * @author JTT
 *
 */
@Configuration//交给springBoot 管理
public class TimeQuartzThree extends QuartzJobBean{

	// @Autowired
	// private EquipmentProjectController equipmentProjectController;
	 @Autowired
	 private PlatformProjectController platformProjectController;


	/**
	 * 执行任务的方法
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

		ParamEntry paramEntry1 = (ParamEntry) dataMap.get("DCUCDParam");
		if (paramEntry1!=null)
			platformProjectController.dataCPUUtilCurveDay(paramEntry1.getNodeId());
	
		ParamEntry paramEntry2 = (ParamEntry) dataMap.get("DMUCDParam");
		if (paramEntry2!=null)
			platformProjectController.dataMemoryUtilCurveDay(paramEntry2.getNodeId());
	
		ParamEntry paramEntry3 = (ParamEntry) dataMap.get("DIOCDParam");
		if (paramEntry3!=null)
			platformProjectController.dataIOCurveDay(paramEntry3.getNodeId());
	
		ParamEntry paramEntry4= (ParamEntry) dataMap.get("DDRIOCParam");
		if (paramEntry4!=null)
			platformProjectController.dataDiskReadIOCurve(paramEntry4.getNodeId());
		
		ParamEntry paramEntry5= (ParamEntry) dataMap.get("DDWIOCParam");
		if (paramEntry5!=null)
			platformProjectController.dataDiskWriteIOCurve(paramEntry5.getNodeId());
		
		ParamEntry paramEntry6 = (ParamEntry) dataMap.get("DCUCWParam");
		if (paramEntry6!=null)
			platformProjectController.dataCPUUtilCurveWeek(paramEntry6.getNodeId());
		
		ParamEntry paramEntry7 = (ParamEntry) dataMap.get("DMUCWParam");
		if (paramEntry7!=null)
			platformProjectController.dataMemoryUtilCurveWeek(paramEntry7.getNodeId());
		
		ParamEntry paramEntry8 = (ParamEntry) dataMap.get("DIOCWParam");
		if (paramEntry8!=null)
	    	platformProjectController.dataIOCurveWeek(paramEntry8.getNodeId());
		
		 ParamEntry paramEntry9= (ParamEntry) dataMap.get("DDCWParam");
			if (paramEntry9!=null)
				platformProjectController.dataDiskCurveWeek(paramEntry9.getNodeId());
	}



}
