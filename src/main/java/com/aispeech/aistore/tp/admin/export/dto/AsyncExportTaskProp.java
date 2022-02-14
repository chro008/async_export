package com.aispeech.aistore.tp.admin.export.dto;

import com.aispeech.aistore.tp.admin.export.enums.ExportStatusEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import lombok.Data;

@Data
public class AsyncExportTaskProp implements Serializable {

    private static final long serialVersionUID = -3504880135241442990L;
    /**
     * 任务ID
     */
    private long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务发起人ID
     */
    private String userId;


    /**
     * 任务创建时间
     */
    private Date createTime;

    /**
     * 任务开始执行时间
     */
    private Date executeTime;

    /**
     * 任务完成时间
     */
    private Date finishTime;

    /**
     * 任务执行状态
     */
    private int status;

    /**
     * 任务执行描述
     */
    private String message;

    public AsyncExportTaskProp(String userId, String taskName) {
        this.createTime = new Date();
        this.userId = userId;
        this.name = taskName;
        this.status = ExportStatusEnum.IN_THE_LINE.getCode();
    }

    public void begin() {
        this.executeTime = new Date();
        this.status = ExportStatusEnum.GENERATING.getCode();
    }

    public void finish(Object value) {
        this.finishTime = new Date();
        if (Objects.isNull(value)) {
            this.status = ExportStatusEnum.BUILD_FAILED.getCode();
        }
        this.status = ExportStatusEnum.BUILD_COMPLETE.getCode();

        if (value instanceof ExportResultDto) {
            boolean succeed = ((ExportResultDto) value).isSucceed();
            this.status = succeed ? ExportStatusEnum.BUILD_COMPLETE.getCode() : ExportStatusEnum.BUILD_FAILED.getCode();
            this.message = ((ExportResultDto) value).getMessage();
        }
    }

    public Date getCreateTime() {
        return Objects.isNull(createTime) ? null : (Date) createTime.clone();
    }

    public void setCreateTime(Date time) {
        this.createTime = Objects.isNull(time) ? null : (Date) time.clone();
    }

    public Date getExecuteTime() {
        return Objects.isNull(executeTime) ? null : (Date) executeTime.clone();
    }

    public void setExecuteTime(Date time) {
        this.executeTime = Objects.isNull(time) ? null : (Date) time.clone();
    }

    public Date getFinishTime() {
        return Objects.isNull(finishTime) ? null : (Date) finishTime.clone();
    }

    public void setFinishTime(Date time) {
        this.finishTime = Objects.isNull(time) ? null : (Date) time.clone();
    }
}