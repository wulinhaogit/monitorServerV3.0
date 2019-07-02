package com.kedong.quartz;

import com.kedong.common.vo.ParamEntry;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class QuartzJobManager {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;



    public JobDetail addJob(Class jobClass,String jobName,String jobGroupName,String paramKey, ParamEntry paramEntry) throws SchedulerException {


        String cronExpression= "0 */1 * * * ?";
        Scheduler scheduled =  schedulerFactoryBean.getScheduler();
        Date runTime = DateBuilder.evenSecondDate(new Date());
        //job定义： // 任务名，任务组，任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).storeDurably().build();
        jobDetail.getJobDataMap().put(paramKey, paramEntry);
        //触发器构建
        Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobName, jobGroupName)
                .startAt(runTime)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
             scheduled.scheduleJob(jobDetail, trigger);
        return jobDetail;

    }

    public void deleteJob(String jobClassName, String jobGroupName) throws Exception {
        Scheduler scheduled =  schedulerFactoryBean.getScheduler();
        scheduled.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduled.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduled.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


}
