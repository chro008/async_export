package com.aispeech.aistore.tp.admin.export;

import com.aispeech.aistore.tp.admin.export.dto.ExportResultDto;
import com.aispeech.ba.logger.LoggerUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

/**
 * 离线下载管理器
 */
@Component
public class ExportManager {

    private static final ExecutorService EXECUTORS = new ThreadPoolExecutor(2, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new CustomizableThreadFactory("export-thread-"));

    @Autowired
    DefaultExportAround exportAround;

    /**
     * 新增下载任务
     *
     * @param task   下载任务执行逻辑
     * @param userId 下载发起人用户ID
     * @param name   下载任务名称
     */
    public boolean addExportTask(Callable<ExportResultDto> task, String userId, String name) {
        return addExportTask(task, userId, name, exportAround);
    }

    public boolean addExportTask(Callable<ExportResultDto> task, String userId, String name, ExportAround around) {
        ExportTask<ExportResultDto> exportTask = new ExportTask<>(task, userId, name, around);
        final Future<ExportResultDto> submit = EXECUTORS.submit(() -> customerFunction(exportTask));
        // 防止findbugs提示 RV_RETURN_VALUE_IGNORED_BAD_PRACTICE
        LoggerUtils.debug(submit.hashCode());
        return true;
    }

    /**
     * 自定义导出业务逻辑方法，增加业务逻辑执行前后的方法
     *
     * @param exportTask
     * @return
     */
    private ExportResultDto customerFunction(ExportTask<ExportResultDto> exportTask) {
        exportTask.getBefore().ifPresent(before -> before.accept(exportTask));
        ExportResultDto value;
        try {
            value = exportTask.start();
        } catch (Exception e) {
            LoggerUtils.error("export task error:" + e.getMessage(), e);
            value = ExportResultDto.failed(e.getMessage());
        }
        exportTask.setValue(value);
        exportTask.getAfter().ifPresent(after -> after.accept(exportTask));
        return value;
    }

}
