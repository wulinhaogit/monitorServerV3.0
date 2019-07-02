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
public class TimeQuartzFour extends QuartzJobBean{


	 @Autowired
	 private PlatformProjectController platformProjectController;


	/**
	 * 执行任务的方法
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		/*
		ParamEntry paramEntry8= (ParamEntry) dataMap.get("DTSUCParam");
		if (paramEntry8!=null)
			platformProjectController.dataTableSpaceUsageCurve(paramEntry8.getNodeId());		
		
			*/

		ParamEntry paramEntry1 = (ParamEntry) dataMap.get("FPSLParam");
		if (paramEntry1!=null){
			System.out.println("获取参数" + paramEntry1.getNodeId());
			platformProjectController.diskPartSpaceList(paramEntry1.getNodeId());
		}		
		
		ParamEntry paramEntry2 = (ParamEntry) dataMap.get("DDParam");
		if (paramEntry2!=null)
			platformProjectController.databaseDetails(paramEntry2.getNodeId());
		
		ParamEntry paramEntry3 = (ParamEntry) dataMap.get("DRCNParam");
		if (paramEntry3!=null)
			platformProjectController.DataRealtimeConnectNumber(paramEntry3.getNodeId());
		
		ParamEntry paramEntry4 = (ParamEntry) dataMap.get("OHAOParam");
		if (paramEntry4!=null)
			platformProjectController.oneHourAccessOf(paramEntry4.getNodeId());
		
		ParamEntry paramEntry5 = (ParamEntry) dataMap.get("DSNCParam");
		if (paramEntry5!=null)
			platformProjectController.dataSessionNumberCurve(paramEntry5.getNodeId());
		
		ParamEntry paramEntry6 = (ParamEntry) dataMap.get("DMCCParam");
		if (paramEntry6!=null)
			platformProjectController.dataMemoryChangeCurve(paramEntry6.getNodeId());
		
		ParamEntry paramEntry7= (ParamEntry) dataMap.get("DNIOParam");
		if (paramEntry7!=null)
			platformProjectController.databaseNetworkIO(paramEntry7.getNodeId());
		
		ParamEntry paramEntry8= (ParamEntry) dataMap.get("DDWIOCWParam");
		if (paramEntry8!=null)
			platformProjectController.dataDiskWriteIOCurveWeek(paramEntry8.getNodeId());
		


	}



}
