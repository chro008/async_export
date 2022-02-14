package com.aispeech.aistore.tp.admin.export.po;

import com.aispeech.smart.common.db.annotation.SmartTable;
import com.aispeech.smart.common.db.mysql.po.BaseEntity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "async_export_task")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SmartTable(value = "离线下载")
public class AsyncExportTaskPo extends BaseEntity {

    private static final long serialVersionUID = -6132996289396659072L;

    /**
     * 下载任务名称
     */
    private String name;

    /**
     * 下载人唯一标识
     */
    private String userId;

    /**
     * 任务状态
     */
    private int status;

    /**
     * 任务描述
     */
    private String message;

    /**
     * 下载任务文件地址
     */
    private String fileUrl;

    /**
     * 任务创建时间
     */
    private Date createTime;

    /**
     * 任务执行时间
     */
    private Date executeTime;

    /**
     * 任务完成时间
     */
    private Date finishTime;

    @Override
    public Date getCreateTime() {
        return Objects.isNull(createTime) ? null : (Date) createTime.clone();
    }

    @Override
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
