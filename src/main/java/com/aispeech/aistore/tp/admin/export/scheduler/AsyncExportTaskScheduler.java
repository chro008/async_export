package com.aispeech.aistore.tp.admin.export.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class AsyncExportTaskScheduler {

    /**
     * 删除过期任务
     */
    //@Scheduled(cron = "*/10 * * * * ?")
    public void deleteExpiredTask() {
        // todo
    }

    /**
     * 刷新超时任务
     */
    //@Scheduled(cron = "*/10 * * * * ?")
    public void freshTimeOutTask() {
        // todo
    }
}
