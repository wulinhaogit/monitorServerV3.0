package com.kedong.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JTT
 *    定时石英钟的配置类
 */
@Configuration
public class QuartzConfig {

	//配置定时器
	@Bean
	public JobDetail teatQuartzDetail(){
		return JobBuilder.newJob(TimeQuartzOne.class).withIdentity("jobName","jobGroupName").storeDurably().build();
	}

	@Bean
	public Trigger testQuartzTrigger(){
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(60)  //设置时间周期单位秒 先设置为10秒用于测试
				.repeatForever();
		return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
				.withIdentity("triggerName","triggerGroupName")
				.withSchedule(scheduleBuilder)
				.build();
	}

}
