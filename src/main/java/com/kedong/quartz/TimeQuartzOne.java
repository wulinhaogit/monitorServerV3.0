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
public class TimeQuartzOne extends QuartzJobBean{

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
		/*equipmentProjectController.netConcentTop10();
		equipmentProjectController.memoryUtilizationTop10();
		equipmentProjectController.cpuLoadFactorTop5();
		equipmentProjectController.diskUsageTop5();
		equipmentProjectController.netFlowIOTop10();
		//platformProjectController.procStateCPUTop3();
		//platformProjectController.procSateMemoryFotTop3();
		//platformProjectController.numOfOrocessStateThreadsTop3();
		//platformProjectController.procStatusDiskIOWriteIncrementTop3();
		platformProjectController.alarmStatistics();
		//platformProjectController.procStatusDiskIOReadIncrementTop3();*/
		platformProjectController.procStatusMonitor();
		platformProjectController.appMonitorList();
		platformProjectController.midhsAccessNumCurve();
		platformProjectController.appPrimaryAndStaSwitch();
		platformProjectController.midhsDelayCurve();
		platformProjectController.appCountList();
		platformProjectController.getNodeId();
		
		
	}



}
