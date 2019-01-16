package com.example.spring_boot_mybatis_multidatasouce.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerTask1 {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    //上一次开始执行时间点之后6秒再执行
    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        System.out.println("开始执行，现在时间：" + dateFormat.format(new Date()));
    }
    //上一次执行完毕时间点之后6秒再执行
    @Scheduled(fixedDelay = 6000)
    public void reportCurrentTime2() {
        System.out.println("执行完毕，现在时间：" + dateFormat.format(new Date()));
    }
    //第一次延迟1秒后执行，
    @Scheduled(initialDelay = 1000,fixedRate = 6000)
    public void reportCurrentTime1() {
        System.out.println("延迟1秒后执行，现在时间：" + dateFormat.format(new Date()));
    }
}
