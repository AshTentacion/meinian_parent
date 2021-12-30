package com.atguigu.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class TestJob {

    public static void main(String[] args) throws SchedulerException {
        //使用JobDetail调用job
        //创建一个job
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).build();
        //创建一个Trigger 具体调用的时间
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)
                        .repeatForever()).build();
        //创建容器 注册并管理JobDetail Trigger
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


        //注册trigger并启动scheduler
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduler.shutdown();
    }
}
